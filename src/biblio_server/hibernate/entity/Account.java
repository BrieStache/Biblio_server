package biblio_server.hibernate.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * TABLE
 */

@Entity
@Table(name="account")
public class Account {

	/* *******
	 * FIELDS*
	 *********/
	
	// ID is AUTO INCREMENTED
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="accountId")
	private int accoundId;
	
	@Column(name="userName")
	private String userName;
	
	@Column(name="password")
	private String password;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	
	/* *************************************
	 * Getter and Setter for Each Fields ( *
	 ***************************************/
	public int getAccounId() {
		return accoundId;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	/* *****************************************
	 * Default Constructor + Custom Constructor*
	 *******************************************/
	public Account() {
		
	}
	
	public Account(String userName, String password, String firstName, String lastName, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	/* *******************
	 * LINKED FIELDS (FK)*
	 *********************/
	
	//OneToMany
	@OneToMany(fetch = FetchType.EAGER,mappedBy="account",cascade = CascadeType.ALL)
	private Set<Book> books = new HashSet<Book>();
	
	public void addBooks(Book book) {
		this.books.add(book);
		book.setAccount(this); //Link the book to this account
		book.setBorrowedDate();
	}
	
	public void removeBooks(Book book) {
		this.books.remove(book);
		book.setAccount(null); //Remove the book from this account
		book.unsetBorrowedDate();
	}
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="account",cascade = CascadeType.ALL)
	private Set<BookReservation> bookReservations = new HashSet<BookReservation>();

	public void addReservation(BookReservation bookReservation) {
		this.bookReservations.add(bookReservation);
		bookReservation.setAccount(this); //Link the account to the reservation
	}
	
	public void removeReservation(BookReservation bookReservation) {
		this.bookReservations.remove(bookReservation);
		bookReservation.setAccount(null); //Link the account to the reservation
	}	
	
	
}
