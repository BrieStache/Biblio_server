package biblio_server.messageBuilder;

import java.util.ArrayList;
import java.util.List;


import biblio_server.hibernate.com.DataManager;
import biblio_server.hibernate.entity.Book;
import biblio_server.hibernate.entity.BookReservation;
import biblio_server.messageBuilder.reader.msgReader;
import biblio_server.messageBuilder.writer.msgWriter;
import biblio_server.mqtt.sender.MqttSender;

public class MessageManager {

	public void processData(String receivedMessage) {
		
		//Data that's received and sent
		String toSendMessage = "";
		
		
		//Gets from Client
		String userName = "null";
		String password = "null";
		String tempClientId = "null";
		String searchString = "null";
		int searchType = 0;
		int requestId = 0;
		int bookId = 0;
		int fromReservation = 0;
		
		
		//To return to Client
		int accountId = 0;
		boolean reservationOk = false;
		boolean returnOk = false;
		boolean borrowOk = false;
		List<Book> reservationBookList = null;
		List<Book> bookList = new ArrayList<Book>();
		
		msgReader reader = new msgReader();
		msgWriter writer = new msgWriter();
		DataManager dM = new DataManager();
		
		//requestId Reader
		requestId = reader.getRequestId(receivedMessage);
		receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
		receivedMessage.trim();
		
		
		System.out.println(receivedMessage);
		switch (requestId) {
		case 1://Connection Request
			
			tempClientId = reader.getTempId(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			userName = reader.getUsername(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			password = reader.getPassword(receivedMessage);
			
			accountId = dM.getConn(userName,password);
			break;
		case 2://Get reservation list
			
			accountId = reader.getAccountId(receivedMessage);
			reservationBookList = dM.getReservationList(accountId);
			break;
		case 3 ://borrow a book
			
			accountId = reader.getAccountId(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			bookId = reader.getBookId(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			fromReservation = reader.getFromReservation(receivedMessage);

			borrowOk = dM.borrowBook(accountId, bookId, fromReservation);
			break;
		case 4 ://return a book
			
			accountId = reader.getAccountId(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			bookId = reader.getBookId(receivedMessage);
			
			returnOk = dM.returnBook(accountId, bookId);
			
			MqttSender sender = new MqttSender();
			sender.sendMessage("Book available ;"+Integer.toString(bookId));
			break;
		case 5://create a reservation for a book
			accountId = reader.getAccountId(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			bookId = reader.getBookId(receivedMessage);
			
			reservationOk = dM.createReservation(accountId, bookId);
			break;
		case 6://Get book or book List
			accountId = reader.getAccountId(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			searchType = reader.getSearchType(receivedMessage);
			receivedMessage = receivedMessage.substring(receivedMessage.indexOf(";")+1);
			receivedMessage.trim();
			
			searchString = reader.getSearchString(receivedMessage);
			
			bookList = dM.getBook(searchType, searchString);
			break;
		default:
			//Not a request for us
			break;
		}
		
		toSendMessage = writer.getMessage(requestId,tempClientId,accountId,reservationBookList,borrowOk,reservationOk,bookList);
		
		MqttSender sender = new MqttSender();
		sender.sendMessage(toSendMessage);
	}
}
