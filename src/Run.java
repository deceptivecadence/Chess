//Andrew made most of this file. copyright 2013 andrew. ^(tm) == false;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;


public class Run {

	public static void main(String[] args) {
		int pingRate = 1000;
		Board b = new Board();
		System.out.println(b.toString());
		//b.move("b1","c3");
		//System.out.println(b.toString());
		//System.out.println(b.flipBoard().toString());
		//b.moveKings(true);
		//b.moveQueens();\
		//b.moveKnights();
		URLSendReceive butler = new URLSendReceive();
		Date time = new Date();
		Board currentState = new Board();
		boolean weAreWhite = true;
		JSONObject json = butler.urlReceive(butler.pollURL);
		System.out.println(json.toString());
		if (json.get("ready").toString() == "true") {
			//we are white.
			weAreWhite = true;
			String ourMove = miniMaxFind(currentState, weAreWhite);
			System.out.println(ourMove);
			butler.urlSend(ourMove);

			System.out.println("~~~~~~ " + ourMove);
			currentState.moveFromInput(ourMove);
			//currentState = currentState.flipBoard();
		}
		else {
			weAreWhite = false;
			//currentState = currentState.flipBoard();
		}
		
		while (true) {
			Date currentTime = new Date();
			float secondsLeft = 0;
			int lastMoveNumber = 0;
			String lastMove = "";
			String ourMove = "";
			
			if (currentTime.getTime() - time.getTime() >= pingRate) {
				json = butler.urlReceive(butler.pollURL);
				System.out.println(json.toString());
				if (json.get("ready").toString() == "true") {
					secondsLeft = Float.parseFloat(json.get("secondsleft").toString());
					lastMoveNumber = Integer.parseInt(json.get("lastmovenumber").toString());
					lastMove = json.get("lastmove").toString();
					currentState.moveFromInput(lastMove);
					//currentState = currentState.flipBoard();
					//do a move

					ourMove = miniMaxFind(currentState, weAreWhite);
					
					JSONObject response = butler.urlSend(ourMove);
					System.out.println(ourMove);
					System.out.println(response.toString());
					try{
						if(!(boolean) response.get("result")){
							System.out.println("YOU ZUCK BALLS");
						}
						else {
							System.out.println(ourMove);
							currentState.moveFromInput(ourMove);
							//currentState = currentState.flipBoard();
							System.out.println(currentState);
						}
					}
					catch(JSONException e){
						
					}
				}
				
				time = new Date();
				System.out.println("Polled at " + time + ". ");
				if (json.get("ready").toString() == "true"){
					System.out.print("Made a move.");
				}
			}
		}
	}

	/*public static String miniMaxFind2(Board currentState, boolean white, boolean maxing, int depth) {
		if (depth > 3) {
			return currentState.value(white);
		}

		if (maxing) {
			int best = -99999;
			ArrayList<Board> branches = new ArrayList<Board>();
			branches.addAll(currentState.movePawns(white ^ maxing));
			branches.addAll(currentState.moveKings(white ^ maxing));
			branches.addAll(currentState.moveQueens(white ^ maxing));
			branches.addAll(currentState.moveBishops(white ^ maxing));
			branches.addAll(currentState.moveRooks(white ^ maxing));
			branches.addAll(currentState.moveKnights(white ^ maxing));
			for (Board move : ourMoves) {
				int val = miniMaxFind(move, white, false, depth + 1);
				best = Math.max(best, val);
			}
			return best;
		}
		else {

		}
		return 0;
	}*/

	public static String miniMaxFind(Board currentState, boolean white) {
		ArrayList<Board> ourMoves = new ArrayList<Board>();
		//ArrayList<ArrayList<Board>> tree = new ArrayList<ArrayList<Board>>();
		String bestMove = "nigga";

		//generate all possible states from current state, till depth 4
		ourMoves.addAll(currentState.movePawns(white));
		ourMoves.addAll(currentState.moveKings(white));
		ourMoves.addAll(currentState.moveQueens(white));
		ourMoves.addAll(currentState.moveBishops(white));
		ourMoves.addAll(currentState.moveRooks(white));
		ourMoves.addAll(currentState.moveKnights(white));

		//Board bestMove = new Board();
		int maximum = -99999;
		for (Board move : ourMoves) {
			int min = minValue(move, !white, 0);
			if (min > maximum) {
				bestMove = move.lastMove;
				maximum = min;
			}
		}

		return bestMove;
	}

	public static int maxValue(Board state, boolean white, int depth) {
		if (depth > 3) {
			return state.value(white);
		}

		int maximum = -99999;
		ArrayList<Board> branches = new ArrayList<Board>();
		branches.addAll(state.movePawns(white));
		branches.addAll(state.moveKings(white));
		branches.addAll(state.moveQueens(white));
		branches.addAll(state.moveBishops(white));
		branches.addAll(state.moveRooks(white));
		branches.addAll(state.moveKnights(white));
		for (Board move : branches) {
			maximum = Math.max(maximum, minValue(move, white, depth + 1));
		}

		return maximum;
	}

	public static int minValue(Board state, boolean white, int depth) {
		if (depth > 3) {
			return state.value(white);
		}

		int minimum = 99999;
		ArrayList<Board> branches = new ArrayList<Board>();
		branches.addAll(state.movePawns(!white));
		branches.addAll(state.moveKings(!white));
		branches.addAll(state.moveQueens(!white));
		branches.addAll(state.moveBishops(!white));
		branches.addAll(state.moveRooks(!white));
		branches.addAll(state.moveKnights(!white));
		for (Board move : branches) {
			minimum = Math.min(minimum, maxValue(move, white, depth + 1));
		}

		return minimum;
	}

	public static String findMove(Board currentState, boolean white) {
		ArrayList<Board> ourMoves = new ArrayList<Board>();
		//ArrayList<ArrayList<Board>> tree = new ArrayList<ArrayList<Board>>();
		String bestMove = "nigga";
		int maxValue = -99999;
		int minValue = 99999;

		//generate all possible states from current state, till depth 4
		ourMoves.addAll(currentState.movePawns(white));
		ourMoves.addAll(currentState.moveKings(white));
		ourMoves.addAll(currentState.moveQueens(white));
		ourMoves.addAll(currentState.moveBishops(white));
		ourMoves.addAll(currentState.moveRooks(white));
		ourMoves.addAll(currentState.moveKnights(white));

		for (Board ourState : ourMoves) {
			ArrayList<ArrayList<Board>> tree = new ArrayList<ArrayList<Board>>();
			tree.add(new ArrayList<Board>());
			tree.get(0).add(ourState);
			for (int i = 1; i < 5; i += 1) {
				tree.add(new ArrayList<Board>());
				boolean ww = white;
				if (i % 2 == 1)
					ww = !white;
				
				for (Board state : tree.get(i-1)) {
					tree.get(i).addAll(state.movePawns(ww));
					tree.get(i).addAll(state.moveKings(ww));
					tree.get(i).addAll(state.moveQueens(ww));
					tree.get(i).addAll(state.moveBishops(ww));
					tree.get(i).addAll(state.moveRooks(ww));
					tree.get(i).addAll(state.moveKnights(ww));
				}
			}

			minValue = 99999;
			//check scores from bottom of tree
			for (Board state : tree.get(4)) {
				if (state.value(white) < minValue) {
					minValue = state.value(white);
					/*if (minValue < maxValue) {
						prune = true;
						break;
					}*/
				}
			}

			if (minValue > maxValue) {
				maxValue = minValue;
				bestMove = ourState.lastMove;
				//besty = ourState.flipBoard();
			}
		}

		System.out.println("maxValue was " + maxValue);
		return bestMove;

		/*
		ourMoves.addAll(currentState.moveKings(white));
		ourMoves.addAll(currentState.moveQueens(white));
		ourMoves.addAll(currentState.moveBishops(white));
		ourMoves.addAll(currentState.moveRooks(white));
		ourMoves.addAll(currentState.moveKnights(white));
		ourMoves.addAll(currentState.movePawns(white));

		//ArrayList<Board> anorLondo = new ArrayList<Board>();
		
		//Board besty = new Board();
		int maxValue = -99999;
		for (Board ourState : ourMoves) {
			ArrayList<Board> theirMoves = new ArrayList<Board>();
			//String temp = ourState.lastMove;
			//ourState = ourState.flipBoard();
			//ourState.lastMove = temp;
			theirMoves.addAll(ourState.moveKings(!white));
			theirMoves.addAll(ourState.moveQueens(!white));
			theirMoves.addAll(ourState.moveBishops(!white));
			theirMoves.addAll(ourState.moveRooks(!white));
			theirMoves.addAll(ourState.moveKnights(!white));
			theirMoves.addAll(ourState.movePawns(!white));

			//boolean prune = false;
			int minValue = 99999;
			for (Board theirState : theirMoves) {
				if (theirState.value(true) < minValue) {
					minValue = theirState.value(true);
					/*if (minValue < maxValue) {
						prune = true;
						break;
					}
				}
			}

			/*if (prune) {
				continue;
			}

			if (minValue > maxValue) {
				maxValue = minValue;
				bestMove = ourState.lastMove;
				//besty = ourState.flipBoard();
			}
		}

		//System.out.println(besty);
		return bestMove;*/
		
	}

}


/*if (weAreBlack) {
	String piece = "";
	String from = "";

	String to = "";
	String promotion = "";

	piece = ourMove.substring(0, 1);
	from = ourMove.substring(1, 3);
	to = ourMove.substring(3,5);

	if (ourMove.length() > 5) {
		promotion = ourMove.substring(5,6);
	}

	byte byteFromCol  = -1;
	byte byteToCol    = -1;


	byte byteFromRow   = (byte) (8 - Integer.parseInt(from.substring(1, 2)));
	byte byteToRow     = (byte) (8 - Integer.parseInt(to.substring(1, 2)));
	
	switch(from.substring(0, 1)){
		case "a": byteFromCol = 0; break;
		case "b": byteFromCol = 1; break;

		case "c": byteFromCol = 2; break;
		case "d": byteFromCol = 3; break;
		case "e": byteFromCol = 4; break;
		case "f": byteFromCol = 5; break;
		case "g": byteFromCol = 6; break;
		case "h": byteFromCol = 7; break;
	}


	switch(to.substring(0, 1)){
		case "a": byteToCol = 0; break;
		case "b": byteToCol = 1; break;
		case "c": byteToCol = 2; break;
		case "d": byteToCol = 3; break;

		case "e": byteToCol = 4; break;
		case "f": byteToCol = 5; break;
		case "g": byteToCol = 6; break;
		case "h": byteToCol = 7; break;
	}


	byteFromCol = (byte) (7 - byteFromCol);
	byteFromRow = (byte) (7 - byteFromRow);
	byteToCol = (byte) (7 - byteToCol);
	byteToRow = (byte) (7 - byteToRow);

	String fY = "" + (8 - byteFromRow);
	String tY = "" + (8 - byteToRow);


	String fX = "";
	String tX = "";

	switch (byteFromCol) {
		case (0): fX = "a"; break;

		case (1): fX = "b"; break;
		case (2): fX = "c"; break;
		case (3): fX = "d"; break;
		case (4): fX = "e"; break;
		case (5): fX = "f"; break;
		case (6): fX = "g"; break;
		case (7): fX = "h"; break;

	}

	switch (byteToCol) {
		case (0): tX = "a"; break;
		case (1): tX = "b"; break;
		case (2): tX = "c"; break;

		case (3): tX = "d"; break;
		case (4): tX = "e"; break;
		case (5): tX = "f"; break;
		case (6): tX = "g"; break;
		case (7): tX = "h"; break;
	}


	//System.out.println(piece + fX + fY + tX + tY + promotion);
	ourMove = piece + fX + fY + tX + tY + promotion;
}
*/
