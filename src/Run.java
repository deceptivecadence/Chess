import java.util.Date;


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
				//do a move
				time = new Date();
				System.out.println("We made a move at " + time);
			}
		}
	}

}
