package biblio_server.messageBuilder;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class email_Sender {

	private static final String SMTP_SERVER = "smtp.gmail.com";
	private static final String USERNAME = "librarynoreply03@gmail.com";
	private static final String PASSWORD = "Aqwzsxedc19-";
	
	private static final String EMAIL_FROM = "librarynoreply03@gmail.com";
	
	private static final String EMAIL_SUBJECT = "Do not reply : Your book reservation has been confirmed";
	private static final String EMAIL_TEXT = "Hello, we confirmed you that you have reserved the book '";
	
	public static void send(String Email,String bookTitle) {
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", SMTP_SERVER);
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		prop.put("mail.smtp.socketFactory.port", "465");
		
		
		Session session  = Session.getInstance(prop,new Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(USERNAME,PASSWORD);
			}
		});
		Message msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress(EMAIL_FROM));
			
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(Email));
			msg.setSubject(EMAIL_SUBJECT);
			msg.setText(EMAIL_TEXT+bookTitle+"'. Good Lecture");
			msg.setSentDate(new Date());
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
			t.connect();
			
			t.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Response: " + t.getLastServerResponse());
			
			t.close();
			
		} catch (MessagingException e){
			e.printStackTrace();
		}
	}
}
