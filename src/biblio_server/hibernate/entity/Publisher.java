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
@Table(name="publisher")
public class Publisher {

	/* *******
	 * FIELDS*
	 *********/
	
	// ID is AUTO INCREMENTED
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="publisherId")
	private int publisherId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	
	/* *************************************
	 * Getter and Setter for Each Fields ( *
	 ***************************************/
	
	public int getPublisherId() {
		return publisherId;
	}

	public String getName() {
		return name;
	}

	public void setLastName(String name) {
		this.name = name;
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
	public Publisher() {
		
	}
	
	public Publisher(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	
	/* *******************
	 * LINKED FIELDS (FK)*
	 *********************/
	
	//OneToMany
	@OneToMany(fetch = FetchType.EAGER,mappedBy="publisher",cascade = CascadeType.ALL)
	private Set<Book> books = new HashSet<Book>();
	
	public void addBooks(Book book) {
		this.books.add(book);
		book.setPublisher(this); //Link the book to this publisher
	}
	
	public void removeBooks(Book book) {
		this.books.remove(book);
		book.setPublisher(null); //Remove the book from this Publisher
	}	
	
}
