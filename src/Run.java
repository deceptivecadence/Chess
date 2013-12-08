//Andrew made most of this file. copyright 2013 andrew. ^(tm)

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
		b.moveKnights();
		URLSendReceive butler = new URLSendReceive();
		Date time = new Date();
		Board currentState = new Board();
		boolean 

		JSONObject json = butler.urlReceive(butler.pollURL);
			if (json.get("ready").toString() == "true") {
				//we are white.
			}
			else {
				currentState = currentState.flipBoard();
			}
		
		while (true) {
			Date currentTime = new Date();
			float secondsLeft = 0;
			int lastMoveNumber = 0;
			String lastMove = "";
			String ourMove = "";
			
			if (currentTime.getTime() - time.getTime() >= pingRate) {
				json = butler.urlReceive(butler.pollURL);
				if (json.get("ready").toString() == "true") {
					secondsLeft = Float.parseFloat(json.get("secondsleft").toString());
					lastMoveNumber = Integer.parseInt(json.get("lastmovenumber").toString());
					lastMove = json.get("lastmove").toString();
					currentState.moveFromInput(lastMove);
					currentState = currentState.flipBoard();
					//do a move

					ArrayList<Board> ourMoves = new ArrayList<Board>();
					ourMoves.addAll(ourMoves.moveKings());
					ourMoves.addAll(ourMoves.moveQueens());
					ourMoves.addAll(ourMoves.moveBishops());
					ourMoves.addAll(ourMoves.moveRooks());
					ourMoves.addAll(ourMoves.moveKnights());
					ourMoves.addAll(ourMoves.movePawns());

					//ArrayList<Board> anorLondo = new ArrayList<Board>();
					String bestMove = "";
					int maxValue = -99999;
					for (Board ourState : ourMoves) {
						ArrayList<Board> theirMoves = new ArrayList<Board>();
						ourState = ourState.flipBoard();
						theirMoves.addAll(ourState.moveKings());
						theirMoves.addAll(ourState.moveQueens());
						theirMoves.addAll(ourState.moveBishops());
						theirMoves.addAll(ourState.moveRooks());
						theirMoves.addAll(ourState.moveKnights());
						theirMoves.addAll(ourState.movePawns());
						
						int minValue = 99999;
						for (Board theirState : theirMoves) {
							if (theirState.flipBoard().value() < minValue) {
								minValue = theirState.flipBoard().value();
							}
						}

						if (minValue > maxValue) {
							maxValue = minValue;
							bestMove = ourState.lastMove;
						}
					}

					ourMove = bestMove;//something
					butler.urlSend(ourMove);
					currentState.moveFromInput(ourMove);
					currentState = currentState.flipBoard();
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
