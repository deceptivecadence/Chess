import java.util.Arrays;

import org.json.JSONArray;

public class Board {
	private byte K = 6;
	private byte Q = 5;
	private byte R = 4;
	private byte B = 3;
	private byte N = 2;
	private byte P = 1;
	public byte[][] board = new byte[8][8];
	//JSONArray pieceMapping = new JSONArray();
			
	public Board(){
		//Set up top row
		for(byte i=0; i<2; i++){
			if(i==0){
				board[i][0] = R;
				board[i][1] = N;
				board[i][2] = B;
				board[i][3] = Q;
				board[i][4] = K;
				board[i][5] = B;
				board[i][6] = N;
				board[i][7] = R;
			}
			else{
				for(byte j=0; j<8; j++){
					board[i][j] = P;
				}
			}
		}
		
		//Set up bottom row
		for(byte j=6; j<8; j++){
			if(j==6){
				for(byte k=0; k<8; k++){
					board[j][k] = (byte) (P + 10);
				}
			}
			else{
				board[j][0] = (byte) (R + 10);
				board[j][1] = (byte) (N + 10);
				board[j][2] = (byte) (B + 10);
				board[j][3] = (byte) (Q + 10);
				board[j][4] = (byte) (K + 10);
				board[j][5] = (byte) (B + 10);
				board[j][6] = (byte) (N + 10);
				board[j][7] = (byte) (R + 10);
				
			}
		}
	}
	
	public Boolean move(String piece, String from, String to){
		int intPiece = -1;
		int intFrom  = -1;
		int intTo    = -1;
		switch(piece){
			case "K": intPiece = 6; break;
			case "Q": intPiece = 5; break;
			case "R": intPiece = 4; break;
			case "B": intPiece = 3; break;
			case "N": intPiece = 2; break;
			case "P": intPiece = 1; break;
		}
		
		switch(from.substring(0, 1)){
			case "a": intFrom = 0; break;
			case "b": intFrom = 1; break;
			case "c": intFrom = 2; break;
			case "d": intFrom = 3; break;
			case "e": intFrom = 4; break;
			case "f": intFrom = 5; break;
			case "g": intFrom = 6; break;
			case "h": intFrom = 7; break;
		}
		
		switch(to.substring(0, 1)){
			case "a": intTo = 0; break;
			case "b": intTo = 1; break;
			case "c": intTo = 2; break;
			case "d": intTo = 3; break;
			case "e": intTo = 4; break;
			case "f": intTo = 5; break;
			case "g": intTo = 6; break;
			case "h": intTo = 7; break;
		}
		return false;
	}
	//move will be PieceFrom(Col1Row1)To(Col2Row2)[promotion]
	public Boolean moveFromInput(String move){
		String piece = "";
		String from = "";
		String to = "";
		String optional = "";
		//single piece move
		if (move.length() == 5){
			piece = move.substring(0, 1);
			from = move.substring(1, 3);
			to = move.substring(3,5);
		}
		return false;
	}
	
	public Board flipBoard() {
		Board newBoard = new Board();
		for (int i = 0; i < 8; i += 1) {
			for (int j = 0; j < 8; j += 1) {
				newBoard.board[7-i][7-j] = this.board[i][j];
			}
		}
		
		return newBoard;
	}
	
	@Override
	public String toString(){
		String output = "";
		for(byte i=0; i<8; i++){
			output += Arrays.toString(board[i]) + "\n";
		}
		return output;
	}
}
