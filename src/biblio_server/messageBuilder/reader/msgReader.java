package biblio_server.messageBuilder.reader;

public class msgReader {
		
	//Get the requestId out of the String
	public int getRequestId(String tempString) {
		String[] id = tempString.split(";");
		return Integer.parseInt(id[0]);
	}
	//Get temp Client ID
	public String getTempId(String tempString) {
		String[] temp = tempString.split(";");
		return temp[0];
	}
	
	public String getUsername(String tempString) {
		String[] temp = tempString.split(";");
		return temp[0];
	}
	
	public String getPassword(String tempString) {
		String[] temp = tempString.split(";");
		return temp[0];
	}
	
	public int getAccountId(String tempString) {
		String[] id = tempString.split(";");
		return Integer.parseInt(id[0]);
	}
	
	public int getBookId(String tempString) {
		String[] id = tempString.split(";");
		return Integer.parseInt(id[0]);
	}
	
	public int getFromReservation(String tempString) {
		String[] id = tempString.split(";");
		return Integer.parseInt(id[0]);
	}
	
	public int getSearchType(String tempString) {
		String[] id = tempString.split(";");
		return Integer.parseInt(id[0]);
	}
	
	public String getSearchString(String tempString) {
		String[] temp = tempString.split(";");
		return temp[0];
	}
}
