package biblio_server.hibernate.fill_table;

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


public class AddData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SessionFactory factory = 	new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Account.class)
				.addAnnotatedClass(Autor.class)
				.addAnnotatedClass(Book.class)
				.addAnnotatedClass(BookReservation.class)
				.addAnnotatedClass(Publisher.class)
				.buildSessionFactory();
		
		Session session = factory.getCurrentSession();
		
			
		try {
			/*
			 * Create ALL accounts, autors, publishers, books
			 */
			
			List<Account> accounts = new ArrayList<Account>();
			accounts.add(new Account("BRC", "myPassWord", "Bryan", "Cristache", "bryancristache@hotmail.com"));
			accounts.add(new Account("PHV", "hisPassWord", "Philippe", "Vanderheyden", "PHV@gmail.com"));
			
			List<Autor> autors = new ArrayList<Autor>();
			autors.add(new Autor("Alain","Absire","ALA@gmail.com"));
			autors.add(new Autor("Guy","Abadia","GUA@gmail.com"));
			autors.add(new Autor("Yuval","Abramovitz","YUA@gmail.com"));
			
			List<Publisher> publishers = new ArrayList<Publisher>();
			publishers.add(new Publisher("Editions Beya","info@editionsbeya.com"));
			publishers.add(new Publisher("Editions De Boeck","info@deboecksuperieur.com"));
			
			List<Book> books = new ArrayList<Book>();
			books.add(new Book("9782702116258","Accusé","Français",294));
			books.add(new Book("9782840990529","Colombie","Français",164));
			books.add(new Book("9780151492503","Part ! Simonie","Français",230));
			books.add(new Book("2266056492","Xylophone","Français",320));
			books.add(new Book("2221459825","Lutte","Français",160));
			books.add(new Book("5619841954","Mouvement","Français",230));
			books.add(new Book("1548421365","Espace","Français",750));
			books.add(new Book("9782890529","Religion","Français",215));
			books.add(new Book("1582649455","Split","Français",750));
			books.add(new Book("7614302548","Victoire","Français",215));
			
			/*
			 * Adding all the relation between books, autors and publishers
			 */
			//Adding the books to the autors
			autors.get(0).addBooks(books.get(0));
			autors.get(0).addBooks(books.get(1));
			autors.get(0).addBooks(books.get(2));
			autors.get(1).addBooks(books.get(3));
			autors.get(1).addBooks(books.get(4));
			autors.get(1).addBooks(books.get(5));
			autors.get(1).addBooks(books.get(6));
			autors.get(1).addBooks(books.get(7));
			autors.get(2).addBooks(books.get(8));
			autors.get(2).addBooks(books.get(9));
			
			//Adding the books to the publishers
			publishers.get(0).addBooks(books.get(0));
			publishers.get(1).addBooks(books.get(1));
			publishers.get(1).addBooks(books.get(2));
			publishers.get(0).addBooks(books.get(3));
			publishers.get(1).addBooks(books.get(4));
			publishers.get(0).addBooks(books.get(5));
			publishers.get(1).addBooks(books.get(6));
			publishers.get(0).addBooks(books.get(7));
			publishers.get(1).addBooks(books.get(8));
			publishers.get(0).addBooks(books.get(9));
			
			//Start transaction
			session.beginTransaction();
			
			//Save the book,account,publisher,autor Object
			
			for(Account account : accounts)			session.save(account);
			for(Autor autor : autors)				session.save(autor);
			for(Publisher publisher : publishers)	session.save(publisher);
			for(Book book : books)					session.save(book);	

			//Commit the transaction
			
			session.getTransaction().commit();
		} finally {
			factory.close();
		}
	}

}
