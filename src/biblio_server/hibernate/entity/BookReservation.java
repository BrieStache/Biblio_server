package biblio_server.hibernate.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/*
 * TABLE
 */
@Entity
@Table(name="bookReservation")
public class BookReservation {

	/* *******
	 * FIELDS*
	 *********/
	
	// ID is AUTO INCREMENTED
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="reservationId")
	private int reservationId;
	
	/* *************************************
	 * Getter and Setter for Each Fields ( *
	 ***************************************/
	
	public int getreservationId() {
		return reservationId;
	}
	
	/* *****************************************
	 * Default Constructor + Custom Constructor*
	 *******************************************/
	
	public BookReservation() {
		
	}
	
	/* ********************
	 * Foreign Keys FIELDS*
	 **********************/
	
	//Be sure that those columns are created into tables bookReservation
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="bookId")
	private Book book;

	/* *****************************************************
	 * Getter and Setter for Each Fields with foreign keys *
	 *******************************************************/
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	/* *********************************************
	 * request date FIELDS + GETTER and UN/SETTER *
	 ***********************************************/
	
	@Column(name="requestDate")
	private Date requestDate;

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate() {
		this.requestDate = new Date(System.currentTimeMillis());
	}
	
}
