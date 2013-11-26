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
	
	public void move(String from, String to){
		byte byteFromCol  = -1;
		byte byteToCol    = -1;
		
		byte byteFromRow   = (byte) (8 - Integer.parseInt(from.substring(1, 2)));
		byte byteToRow     = (byte) (8 - Integer.parseInt(to.substring(1, 2)));;
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
		
		byte piece = this.board[byteFromRow][byteFromCol];
		this.board[byteFromRow][byteFromCol] = 0;
		this.board[byteToRow][byteToCol] = piece;
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
			this.move(from, to);
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
