package biblio_server.hibernate.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * TABLE
 */

@Entity
@Table(name="book")
public class Book {
	
	/* *******
	 * FIELDS*
	 *********/
	
	// ID is AUTO INCREMENTED
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="bookId")
	private int bookId;

	@Column(name="isbn")
	private String isbn;
	
	@Column(name="title")
	private String title;
	
	@Column(name="lang")
	private String lang;
	
	@Column(name="page")
	private int page;
	
	/* *************************************
	 * Getter and Setter for Each Fields ( *
	 ***************************************/
	public int getBookId() {
		return bookId;
	}
	
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	/* *****************************************
	 * Default Constructor + Custom Constructor*
	 *******************************************/
	public Book() {

	}
	
	public Book(String isbn, String title, String lang, int page) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.lang = lang;
		this.page = page;
	}
	
	
	/* ********************
	 * Foreign Keys FIELDS*
	 **********************/
	
	//Be sure that those columns are created into table book
	@ManyToOne
	@JoinColumn(name="autorId")
	private Autor autor;

	@ManyToOne
	@JoinColumn(name="publisherId")
	private Publisher publisher;
	
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;
	
	/* *****************************************************
	 * Getter and Setter for Each Fields with foreign keys *
	 *******************************************************/
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAutor() {
		return account;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	
	/* *********************************************
	 * Borrowed date FIELDS + GETTER and UN/SETTER *
	 ***********************************************/
	
	@Column(name="borrowedDate")
	private Date borrowedDate;

	public Date getBorrowedDate() {
		return borrowedDate;
	}

	public void setBorrowedDate() {
		this.borrowedDate = new Date(System.currentTimeMillis());
	}
	
	public void unsetBorrowedDate() {
		this.borrowedDate = null;
	}
	
	/* *******************
	 * LINKED FIELDS (FK)*
	 *********************/
	
	//OneToMany
	@OneToMany(fetch = FetchType.EAGER, mappedBy="book", cascade = CascadeType.ALL)
	private Set<BookReservation> bookReservations = new HashSet<BookReservation>();
	
	public void addReservation(BookReservation bookReservation) {
		this.bookReservations.add(bookReservation);
		bookReservation.setBook(this); //Link the book to the reservation
	}
	
	public void removeReservation(BookReservation bookReservation) {
		this.bookReservations.remove(bookReservation);
		bookReservation.setBook(null); //Link the book to the reservation
	}	
}
