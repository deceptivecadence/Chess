import java.util.Date;

import org.json.JSONObject;


public class Run {

	public static void main(String[] args) {
		int pingRate = 1000;
		Board b = new Board();
		System.out.println(b.toString());
		URLSendReceive butler = new URLSendReceive();
		Date time = new Date();
		
		while (true) {
			Date currentTime = new Date();
			if (currentTime.getTime() - time.getTime() >= pingRate) {
				JSONObject json = butler.urlReceive(butler.pollURL);
				if (json.get("ready").toString() == "true") {
					//do a move
				}
				
				time = new Date();
				System.out.println("Polled at " + time + ". ");
				if (json.get("ready").toString() == "true")
					System.out.print("Made a move.");
			}
		}
	}

}
