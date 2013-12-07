import java.util.Arrays;

public class Board {
	private byte K = 6;
	private byte Q = 5;
	private byte R = 4;
	private byte B = 3;
	private byte N = 2;
	private byte P = 1;
	
	private byte bK = 6 + 10;
	private byte bQ = 5 + 10;
	private byte bR = 4 + 10;
	private byte bB = 3 + 10;
	private byte bN = 2 + 10;
	private byte bP = 1 + 10;
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
				board[j][0] = bR;
				board[j][1] = bN;
				board[j][2] = bB;
				board[j][3] = bQ;
				board[j][4] = bK;
				board[j][5] = bB;
				board[j][6] = bN;
				board[j][7] = bR;
				
			}
		}
		//board[6][3] = Q;
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
	
	public void moveKings(){
		byte moveNumber = 1;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == K||this.board[i][j] == bK){
					for(byte k=1; k<=moveNumber; k++){//need to use this
						byte operationX = (byte) (-1*k);
						for(byte x=0; x<3; x++){
							byte operationY = (byte) (-1*k);
							for(byte y=0; y<3; y++){
								try{
									if (this.board[i + operationY][j + operationX] == 0){
										Board board2 = new Board();
										board2.board[i + operationY][j + operationX] = board2.board[i][j];
										board2.board[i][j] = 0;
										System.out.println(board2.toString());
									}
									//add board to "frontier" or wat do
								}catch(IndexOutOfBoundsException e){
									System.out.println("You dun fugged up");
								}
								operationY += 1;
							}
							operationX += 1;
						}
					}
				}
			}
		}
	}
	
	
	public void moveQueens(){
		byte moveNumber = 7;//0-7
		byte boundsCounter = 0;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == Q||this.board[i][j] == bQ){
					boundsCounter = 0;
					byte[][] bounds = new byte[8][2];
					for(byte b=0;b<bounds.length;b++){
						bounds[b] = new byte[] {-1,-1};
					}
					for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
						byte operationX;
						byte operationY;
						for(byte x=-1; x<2; x++){
							operationX = (byte) x*k;
							
							for(byte y=-1; y<2; y++){
								operationY = (byte) y*k;
								try{
									/*if(this.board[i][j] == Q||this.board[i][j] == bQ){
										System.out.println(i+" "+j);
										System.out.println(operationX+" "+operationY);
										System.out.println(Arrays.deepToString(bounds));
									}*/
									if (this.board[i + operationY][j + operationX] != 0){
										//this square is as far as you can go
										bounds[boundsCounter] = new byte[]{(byte) (j + operationX),(byte) (i + operationY)};
										boundsCounter++;
									}
									else{
										boolean conflict = true;
										for (byte[] bound : bounds){
											if(check(operationX, j, operationY, i, bound)){
												conflict = false;
												System.out.println("***"+Arrays.toString(bound)+"***");
											}else{
												conflict = true;
											}
											
										}
										if(!conflict){
											Board board2 = new Board();
											board2.board[i + operationY][j + operationX] = board2.board[i][j];
											board2.board[i][j] = 0;
											System.out.println(board2.toString());
											System.out.print(j);
											System.out.print(" +"+operationX);
											System.out.print(", "+i);
											System.out.println(" +"+operationY);
											System.out.println(Arrays.deepToString(bounds));
											System.out.println("");
										}
									}
								}catch(IndexOutOfBoundsException e){
									System.out.println("You dun fugged up");
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * Checks if piece can move to the location
	 * @param oprX -  + or - or 0 modifier for the x direction
	 * @param x - current x coord
	 * @param opr2-  + or - or 0 modifier for the y direction
	 * @param y - current y coord
	 * @param bound - coords of where other piece is found [1,5] = (1,5)
	 * @return - true if move is valid
	 */
	public boolean check(byte oprX, byte x, byte oprY, byte y,byte[] bound){
		//x is decreasing
		if(oprX<0){
			//y is decreasing
			if(oprY<0){
				return bound[0] <= (x + oprX) && bound[1] <= (byte) (y + oprY);
			}
			//y is increasing
			else if(oprY>0){
				return bound[0] <= (x + oprX) && bound[1] >= (byte) (y + oprY);
			}
			//y isn't changing
			else{
				return bound[0] <= (x + oprX);
			}
		}
		//x is increasing
		else if(oprX>0){
			//y is decreasing
			if(oprY<0){
				return bound[0] >= (x + oprX) && bound[1] <= (byte) (y + oprY);
			}
			//y is increasing
			else if(oprY>0){
				return bound[0] >= (x + oprX) && bound[1] >= (byte) (y + oprY);
			}
			//y isn't changing
			else{
				return bound[1] <= (byte) (y + oprY);
			}
		}
		//x isn't changing
		else{
			//y is decreasing
			if(oprY<0){
				return bound[1] <= (byte) (y + oprY);
			}
			//y is increasing
			else if(oprY>0){
				return bound[1] >= (byte) (y + oprY);
			}
			else{
				return false;
			}
		}
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
