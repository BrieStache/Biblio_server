package biblio_server.hibernate.com;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import biblio_server.hibernate.entity.Account;
import biblio_server.hibernate.entity.Autor;
import biblio_server.hibernate.entity.Book;
import biblio_server.hibernate.entity.BookReservation;
import biblio_server.hibernate.entity.Publisher;

public class DataManager {

	//Needed data to connect to the DB
	private SessionFactory factory;
	private Session session;
	
	//Data from the DB
	private List<Account> theAccounts;
	private List<Book> theBooks;
	private List<BookReservation> theReservation;	
	
	//Get the book asked
	public List<Book> getBook(int searchType, String requestString) {
		List<Book> tempBookList = null;
		factory = this.getSF();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			switch (searchType) {
			case 1 ://Title Search
					tempBookList = session.createQuery("from Book where title ='"+requestString+"'").list();
				break;
			case 2 ://ISBN Search
					tempBookList = session.createQuery("from Book where isbn ='"+requestString+"'").list();
				break;
			case 3 ://Search by publisher
					List<Publisher> publisherId = session.createQuery("from Publisher where name='"+requestString+"'").list();
					if(!publisherId.isEmpty()) {
						String pubId = Integer.toString(publisherId.get(0).getPublisherId());
						tempBookList = session.createQuery("from Book where publisherid ='"+pubId+"'").list();
					}
				break;
			case 4 ://Search by Autor
					List<Autor> autorId = session.createQuery("from Autor where firstName='"+requestString+"'").list();
					if(!autorId.isEmpty()) {
						String autId = Integer.toString(autorId.get(0).getAutorId());
						tempBookList = session.createQuery("from Book where autorid ='"+autId+"'").list();
					}
				break;
			}
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		
		return tempBookList;
	}
	//Get the reservation list
	public List<Book> getReservationList(int accountId) {
		List<BookReservation> tempResList;
		List<Book> tempBookList = new ArrayList<Book>();
		factory = this.getSF();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			loadData();
			
			tempResList = session.createQuery("from BookReservation where accountId='"+accountId+"'").list();
			for(BookReservation tempbook : tempResList) tempBookList.add(tempbook.getBook());
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		
		return tempBookList;
	}
	//Create a reservation
	public boolean createReservation(int accountId,int bookId) {
		boolean transOk;
		accountId--; //to get the actual position
		bookId--; //to get the actual position
		
		factory = this.getSF();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			loadData();
			
			theReservation.add(new BookReservation());
			theReservation.get(theReservation.size()-1).setRequestDate();
			theAccounts.get(accountId).addReservation(theReservation.get(theReservation.size()-1));
			theBooks.get(bookId).addReservation(theReservation.get(theReservation.size()-1));
			session.save(theBooks.get(bookId));
			session.save(theReservation.get(theReservation.size()-1));
			session.save(theAccounts.get(accountId));				
			transOk = true;
			
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		
		return transOk;
	}
	
	
	//Return a book
	public boolean returnBook(int accountId,int bookId) {
		boolean transOk;
		accountId--; //to get the actual position
		bookId--; //to get the actual position
		
		factory = this.getSF();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			loadData();
			theAccounts.get(accountId).removeBooks(theBooks.get(bookId));
			session.save(theAccounts.get(accountId));
			transOk = true;	
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		
		return transOk;
	}
	
	
	//Borrow a book
	public boolean borrowBook(int accountId,int bookId,int fromReservation) {
		boolean transOk;
		accountId--; //to get the actual position
		bookId--; //to get the actual position
		factory = this.getSF();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			loadData();
			
			if(theBooks.get(bookId).getBorrowedDate() == null) {
				theAccounts.get(accountId).addBooks(theBooks.get(bookId));
				session.save(theAccounts.get(accountId));
				if(fromReservation == 1) {
					session.createQuery("delete from BookReservation where accountId='"+(accountId+1)+"' AND bookId='"+(bookId+1)+"'").executeUpdate();	
				}
				transOk = true;
			} else transOk = false;
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		
		return transOk;
		
	}
	
	
	//Start the connection
	public int getConn(String userName, String password) {
		List<Account> accountVal;
		
		factory = this.getSF();
		session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			accountVal = session.createQuery("from Account where username ='"+userName+"' AND password='"+password+"'").list();
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
		if(!accountVal.isEmpty()) return accountVal.get(0).getAccounId();
		else return 0;		
	}
	
	
	//Start the session factory
	private SessionFactory getSF() {
		return new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Account.class)
				.addAnnotatedClass(Autor.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(BookReservation.class)
				.addAnnotatedClass(Publisher.class)
				.buildSessionFactory();
	}

	
	//load the data when needed
	private void loadData() {
		theAccounts = session.createQuery("from Account").list();
		theBooks = session.createQuery("from Book").list();
		theReservation = session.createQuery("from BookReservation").list();
	}
}