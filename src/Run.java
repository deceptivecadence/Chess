import java.util.Date;

import org.json.JSONObject;


public class Run {

	public static void main(String[] args) {
		int pingRate = 1000;
		Board b = new Board();
		System.out.println(b.toString());
		//b.move("b1","c3");
		//System.out.println(b.toString());
		//System.out.println(b.flipBoard().toString());
		//b.moveKings();
		//b.moveQueens();\
		b.moveBishops();
		URLSendReceive butler = new URLSendReceive();
		Date time = new Date();
		
		while (true) {
			Date currentTime = new Date();
			float secondsLeft = 0;
			int lastMoveNumber = 0;
			String lastMove = "";
			
			if (currentTime.getTime() - time.getTime() >= pingRate) {
				JSONObject json = butler.urlReceive(butler.pollURL);
				if (json.get("ready").toString() == "true") {
					secondsLeft = Float.parseFloat(json.get("secondsleft").toString());
					lastMoveNumber = Integer.parseInt(json.get("lastmovenumber").toString());
					lastMove = json.get("lastmove").toString();
					//do a move
				}
				
				time = new Date();
				System.out.println("Polled at " + time + ". ");
				if (json.get("ready").toString() == "true"){
					System.out.print("Made a move.");
				}
			}
		}
	}

}
