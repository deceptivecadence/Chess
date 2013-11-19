import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class URLSendReceive {
	String gameID = "344";
	String teamNumber = "1";
	String teamSecret = "32c68cae";
	String moveString = "";
	
	String pollURL = String.format("http://www.bencarle.com/chess/poll/%s/%s/%s/",gameID,teamNumber,teamSecret);
	String moveURL = String.format("http://www.bencarle.com/chess/move/%s/%s/%s/%s/",gameID,teamNumber,teamSecret,moveString);
	
	public String urlReceive(){
		try {
			URL url = new URL(pollURL);
			URLConnection urlConnection = url.openConnection();
			//urlConnection.connect();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			JSONObject json = new JSONObject(toJSONString(in));
			return json.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean urlSend(String move){
		this.moveString = move;
		try {
			URL url = new URL(moveURL);
			URLConnection connection = url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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
		System.out.println(test.urlReceive());
		
	}
	
}
