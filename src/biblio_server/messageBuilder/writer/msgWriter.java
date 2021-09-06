package biblio_server.messageBuilder.writer;

import java.util.List;

import biblio_server.hibernate.entity.Book;
import biblio_server.hibernate.entity.BookReservation;

public class msgWriter {

	public String getMessage(int requestId,String tempClientId,int accountId,List<Book> reservationList,boolean borrowOk,boolean reservationOk,List<Book> bookList) {
		String message = null;
		requestId +=1000;
		
		switch (requestId) {
		case 1001://Connection Request
			message = tempClientId+";"+requestId+";"+Integer.toString(accountId);
			break;
		case 1002://Get reservation list
			message = Integer.toString(accountId)+";"+requestId+";";
			for(Book tempBook : reservationList) message += tempBook.getTitle()+";"+tempBook.getBookId()+";";
			break;
		case 1003 ://borrow a book
			message = Integer.toString(accountId)+";"+requestId+";";
			if(borrowOk) message+= "true";
			else message += "false";
			break;
		case 1004 ://return a book
			message = Integer.toString(accountId)+";"+requestId+";true";
			break;
		case 1005://create a reservation for a book
			message = Integer.toString(accountId)+";"+requestId+";";
			if(reservationOk) message+= "true";
			else message += "false";
			break;
		case 1006://Get book or book List
			message = Integer.toString(accountId)+";"+requestId+";";
			for(Book tempBook : bookList) message += tempBook.getTitle()+";"+tempBook.getBookId()+";";
			break;
		}
		return message;
	}
}
