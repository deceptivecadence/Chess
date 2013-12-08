import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class URLSendReceive {
//<<<<<<< HEAD
	String gameID = "502";
	String teamNumber = "1";
	String teamSecret = "32c68cae";
	//String teamNumber  ="2";
	//String teamSecret = "1a77594c";
/*=======

	String gameID = "502";
	//String teamNumber = "1";
	//String teamSecret = "32c68cae";
	String teamNumber  ="2";
	String teamSecret = "1a77594c";

>>>>>>> 8126d251889c3b347f659ef09d61820503103b5d*/
	
	String pollURL = String.format("http://www.bencarle.com/chess/poll/%s/%s/%s/",this.gameID,this.teamNumber,this.teamSecret);	
	public JSONObject urlReceive(String inUrl){
		try {
			URL url = new URL(inUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			JSONObject json = new JSONObject(toJSONString(in));
			return json;
		} catch (IOException e) {
			return new JSONObject();
		}
		
	}
	
	public JSONObject urlSend(String move){
		String moveURL = String.format("http://www.bencarle.com/chess/move/%s/%s/%s/%s/",this.gameID,this.teamNumber,this.teamSecret,move);
		return this.urlReceive(moveURL);
	}
	
	private static String toJSONString(BufferedReader br) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int intChar;
	    while ((intChar = br.read()) != -1) {
	      sb.append((char) intChar);
	    }
	    return sb.toString();
	  }
	
	
	public static void main(String[] args) {
		URLSendReceive test = new URLSendReceive();
		System.out.println(test.urlReceive(test.pollURL));
		System.out.println(test.urlSend("Nc6d4"));
		
	}
	
}
