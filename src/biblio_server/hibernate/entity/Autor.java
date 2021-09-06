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
@Table(name="autor")
public class Autor {

	/* *******
	 * FIELDS*
	 *********/
	
	// ID is AUTO INCREMENTED
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="autorId")
	private int autorId;
	
	@Column(name="firstName")
	private String firstName;
	
	@Column(name="lastName")
	private String lastName;
	
	@Column(name="email")
	private String email;
	
	
	/* *************************************
	 * Getter and Setter for Each Fields ( *
	 ***************************************/
	
	public int getAutorId() {
		return autorId;
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
	public Autor() {
		
	}
	
	public Autor(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	/* *******************
	 * LINKED FIELDS (FK)*
	 *********************/
	
	//OneToMany
	@OneToMany(fetch = FetchType.EAGER, mappedBy="autor", cascade = CascadeType.ALL)
	private Set<Book> books = new HashSet<Book>();
	
	public void addBooks(Book book) {
		this.books.add(book);
		book.setAutor(this); //Link the book to this autor
	}
	
	public void removeBooks(Book book) {
		this.books.remove(book);
		book.setAutor(null); //Remove the book from this autor
	}	
	
}
