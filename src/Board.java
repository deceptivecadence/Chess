import java.util.Arrays;

public class Board {
	private byte K = 6;
	private byte Q = 5;
	private byte R = 4;
	private byte B = 3;
	private byte N = 2;
	private byte P = 1;
	public byte[][] board = new byte[8][8];

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
					board[j][k] = P;
				}
			}
			else{
				board[j][0] = R;
				board[j][1] = N;
				board[j][2] = B;
				board[j][3] = Q;
				board[j][4] = K;
				board[j][5] = B;
				board[j][6] = N;
				board[j][7] = R;
				
			}
		}
	}
	//move will be PieceFrom(Col1Row1)To(Col2Row2)
	public Boolean makeMove(String move){
		
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
