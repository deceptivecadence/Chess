
public class Run {

	public static void main(String[] args) {
		int pingRate = 1000;
		Board b = new Board();
		System.out.println(b.toString());
		URLSendReceive butler = new URLSendReceive();
		Date time = new Date();
		
		while (true) {
			Date currentTime = new Date();
			if (currentTime.UTC() - time.UTC() >= pingRate) {
				JSONObject json = butler.urlReceive();
				if (json.get("ready")) {
				//do a move
				}
				
				time = new Date();
				System.out.println("Polled at " + time ". ");
				if (json.get("ready"))
					System.out.print("Made a move.");
			}
		}
	}

}
