import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class URLSendReceive {
	String gameID = "345";
	//String teamNumber = "1";
	//String teamSecret = "32c68cae";
	String teamNumber  ="2";
	String teamSecret = "1a77594c";
	
	
	String pollURL = String.format("http://www.bencarle.com/chess/poll/%s/%s/%s/",gameID,teamNumber,teamSecret);	
	public String urlReceive(String inUrl){
		try {
			URL url = new URL(inUrl);
			URLConnection urlConnection = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			JSONObject json = new JSONObject(toJSONString(in));
			return json.toString();
		} catch (IOException e) {
			return "Not our move or game over";
		}
		
	}
	
	public String urlSend(String move){
		String moveURL = String.format("http://www.bencarle.com/chess/move/%s/%s/%s/%s/",gameID,teamNumber,teamSecret,move);
		return this.urlReceive(moveURL);
	}
	
	private static String toJSONString(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
	
	public static void main(String[] args) {
		URLSendReceive test = new URLSendReceive();
		System.out.println(test.urlReceive(test.pollURL));
		System.out.println(test.urlSend("Nc6d4"));
		
	}
	
}
