import java.util.ArrayList;


public class Board {
	private final byte K = 6;
	private final byte Q = 5;
	private final byte R = 4;
	private final byte B = 3;
	private final byte N = 2;
	private final byte P = 1;
	
	private final byte bK = 6 + 10;
	private final byte bQ = 5 + 10;
	private final byte bR = 4 + 10;
	private final byte bB = 3 + 10;
	private final byte bN = 2 + 10;
	private final byte bP = 1 + 10;
	public byte[][] board = new byte[8][8];
	//JSONArray pieceMapping = new JSONArray();

	public String lastMove = "";
			
	public Board(){
		//Set up top row
		for(byte i=0; i<2; i++){
			if(i==0){
				board[i][0] = bR;
				board[i][1] = bN;
				board[i][2] = bB;
				board[i][3] = bQ;
				board[i][4] = bK;
				board[i][5] = bB;
				board[i][6] = bN;
				board[i][7] = bR;
			}
			else{
				for(byte j=0; j<8; j++){
					board[i][j] = bP;
				}
			}
		}
		
		//Set up bottom row
		for(byte j=6; j<8; j++){
			if(j==6){
				for(byte k=0; k<8; k++){
					board[j][k] = (byte) P;
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
		//board[3][3] = N;
	}
	
	public void move(String from, String to, String promotion){
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
		
		byte piece = this.board[byteFromRow][byteFromCol];
		this.board[byteFromRow][byteFromCol] = 0;
		this.board[byteToRow][byteToCol] = piece;
		if(!promotion.equals("")){
			switch(promotion){
				case "Q": this.board[byteToRow][byteToCol] = bQ; 
				case "R": this.board[byteToRow][byteToCol] = bQ;
				case "B": this.board[byteToRow][byteToCol] = bB;
				case "N": this.board[byteToRow][byteToCol] = bN;
			}
		}
	}

	public String moveString(String piece, int fromX, int fromY, int toX, int toY, String promotion) {
		String fY = "" + (8 - fromY);
		String tY = "" + (8 - toY);

		String fX = "";
		String tX = "";

		switch (fromX) {
			case (0): fX = "a"; break;
			case (1): fX = "b"; break;
			case (2): fX = "c"; break;
			case (3): fX = "d"; break;
			case (4): fX = "e"; break;
			case (5): fX = "f"; break;
			case (6): fX = "g"; break;
			case (7): fX = "h"; break;
		}

		switch (toX) {
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
		return piece + fX + fY + tX + tY + promotion;
	}
	
	public ArrayList<Board> moveKings(boolean white){
		byte piece = K;
		if (!white) {
			piece = bK;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 1;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == piece){
					byte operationX;
					byte operationY;
					
					for(byte x=-1; x<2; x++){
						for(byte y=-1; y<2; y++){
							for(byte k=1; k<=moveNumber; k++){
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								try{
									if ((isOurs(this.board[i + operationY][j + operationX], white) || 
										(i + operationY < 0) || (i + operationY > 7) ||
										(j + operationX < 0) || (j + operationX > 7))){
										break;
									}
									else {
										Board board2 = new Board();
										board2.board[i + operationY][j + operationX] = board2.board[i][j];
										board2.board[i][j] = 0;
										board2.lastMove = moveString("K", j, i, j + operationX, i + operationY, "");
										//System.out.println(board2.toString());

										if (isTheirs(this.board[i + operationY][j + operationX], white)) {
											break;
										}
									}
									//add board to "frontier" or wat do
								}catch(IndexOutOfBoundsException e){
									//System.out.println("You dun fugged up");
								}

								//PUT DIS MUTHAFUCKIN CASTLAN BAK IN.
								//////////////////////////
								/////////////////////////
								/////////////////////////
								////////////////////////////
								
								/*if (i == 7 && j == 4 && this.board[7][7] == R &&
									this.board[7][5] == 0 && this.board[7][6] == 0) {

									//castle
									Board board2 = new Board();
									board2.board[7][4] = 0;
									board2.board[7][5] = R;
									board2.board[7][6] = K;
									board2.board[7][7] = 0;
									board2.lastMove = "Ke1g1";
									boards.add(board2);
									//System.out.println(board2.toString());
								}*/
							}
						}
					}
				} 
			}
		}
		return boards;
	} 
	
	
	public ArrayList<Board> moveQueens(boolean white){
		byte piece = Q;
		if (!white) {
			piece = bQ;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 7;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == piece){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
												
						for(byte y=-1; y<2; y++){
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								try{
									if (isOurs(this.board[i + operationY][j + operationX], white) || 
										(i + operationY < 0) || (i + operationY > 7) ||
										(j + operationX < 0) || (j + operationX > 7)) {
										//this square is as far as you can go
										break;
										//bounds[boundsCounter] = new byte[]{(byte) (j + operationX),(byte) (i + operationY)};
										//boundsCounter++;
									}
									else{
										/*boolean conflict = true;
										for (byte[] bound : bounds){
											if(check(operationX, j, operationY, i, bound)){
												conflict = false;
												System.out.println("***"+Arrays.toString(bound)+"***");
											}else{
												conflict = true;
											}
										
										}
										if(!conflict){*/
										Board board2 = new Board();
										board2.board[i + operationY][j + operationX] = board2.board[i][j];
										board2.board[i][j] = 0;
										board2.lastMove = moveString("Q", j, i, j + operationX, i + operationY, "");
										boards.add(board2);
										/*System.out.println(board2.toString());
										System.out.print(j);
										System.out.print(" +"+operationX);
										System.out.print(", "+i);
										System.out.println(" +"+operationY);
										System.out.println("");*/

										//we can still do the move if there is a black piece here, but we can go no further.
										if (isTheirs(this.board[i + operationY][j + operationX], white)) {
											break;
										}
										//}
									}
								}catch(IndexOutOfBoundsException e){
									//System.out.println("You dun fugged up");
								}
							}
						}
					}
				}
			}
		}

		return boards;
	}
	
	
	public ArrayList<Board> moveRooks(boolean white){
		byte piece = R;
		if (!white) {
			piece = bR;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 7;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == piece){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
											
						for(byte y=-1; y<2; y++){
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								if(operationX==0||operationY==0){
									try{
										if (isOurs(this.board[i + operationY][j + operationX], white) || 
											(i + operationY < 0) || (i + operationY > 7) ||
											(j + operationX < 0) || (j + operationX > 7)) {
											break;
										}
										else{

											Board board2 = new Board();
											board2.board[i + operationY][j + operationX] = board2.board[i][j];
											board2.board[i][j] = 0;
											board2.lastMove = moveString("R", j, i, j + operationX, i + operationY, "");
											boards.add(board2);
											/*System.out.println(board2.toString());
											System.out.print(j);
											System.out.print(" +"+operationX);
											System.out.print(", "+i);
											System.out.println(" +"+operationY);
											System.out.println("");*/
	
											//we can still do the move if there is a black piece here, but we can go no further.
											if (isTheirs(this.board[i + operationY][j + operationX], white)) {
												break;
											}
										}
									}catch(IndexOutOfBoundsException e){
										//System.out.println("You dun fugged up");
									}
								}
							}
						}
					}
				}
			}
		}

		return boards;
	}
	
	public ArrayList<Board> moveBishops(boolean white){
		byte piece = B;
		if (!white) {
			piece = bB;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 7;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == piece){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
											
						for(byte y=-1; y<2; y++){
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								if(operationX!=0 && operationY!=0){
									try{
										if (isOurs(this.board[i + operationY][j + operationX], white) || 
											(i + operationY < 0) || (i + operationY > 7) ||
											(j + operationX < 0) || (j + operationX > 7)) {
											break;
										}
										else{

											Board board2 = new Board();
											board2.board[i + operationY][j + operationX] = board2.board[i][j];
											board2.board[i][j] = 0;
											board2.lastMove = moveString("B", j, i, j + operationX, i + operationY, "");
											boards.add(board2);
											/*System.out.println(board2.toString());
											System.out.print(j);
											System.out.print(" +"+operationX);
											System.out.print(", "+i);
											System.out.println(" +"+operationY);
											System.out.println("");*/
	
											//we can still do the move if there is a black piece here, but we can go no further.
											if (isTheirs(this.board[i + operationY][j + operationX], white)) {
												break;
											}
										}
									}catch(IndexOutOfBoundsException e){
										//System.out.println("You dun fugged up");
									}
								}
							}
						}
					}
				}
			}
		}

		return boards;
	}

	public ArrayList<Board> moveKnights(boolean white){
		byte piece = N;
		if (!white) {
			piece = bN;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == piece){
					byte operationX;
					byte operationY;

					byte[][] wombo = {{-1,-2}, {1,-2}, {-2,-1}, {2,-1},
										{-2,1}, {2,1}, {-1,2}, {1,2}};
					
					for(byte[] coords : wombo) {

						operationX = coords[0];
						operationY = coords[1];
						try{
							if (isOurs(this.board[i + operationY][j + operationX], white) || 
								(i + operationY < 0) || (i + operationY > 7) ||
								(j + operationX < 0) || (j + operationX > 7)){
								continue;
							}
							else {
								Board board2 = new Board();
								board2.board[i + operationY][j + operationX] = board2.board[i][j];
								board2.board[i][j] = 0;
								board2.lastMove = moveString("N", j, i, j + operationX, i + operationY, "");
								boards.add(board2);
								//System.out.println(board2.toString());

								if (isTheirs(this.board[i + operationY][j + operationX], white)) {
									continue;
								}
							}
							//add board to "frontier" or wat do
						}catch(IndexOutOfBoundsException e){
							//System.out.println("You dun fugged up");
						}
					}
				}
			}
		}

		return boards;
	}
	
	
	public ArrayList<Board> movePawns(boolean white){
		byte piece = P;
		if (!white) {
			piece = bP;
		}
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 1;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == piece){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
											
						for(byte y=-1; y<0; y++){ 
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);

								try{
									if (isOurs(this.board[i + operationY][j + operationX], white) || 
										(i + operationY < 0) || (i + operationY > 7) ||
										(j + operationX < 0) || (j + operationX > 7)) {
										break;
									}
									else{
										Board board2 = new Board();
										if(operationX==0 && !isTheirs(this.board[i + operationY][j + operationX], white)){
											for (byte p=0;p<2;p++){
												board2 = new Board();
												if(i==6 && operationY != -2){
													operationY = -2;
												}
												else{
													operationY = -1;
												}
												board2.board[i + operationY][j + operationX] = board2.board[i][j];
												board2.board[i][j] = 0;
												board2.lastMove = moveString("P", j, i, j + operationX, i + operationY, "");
												if((i + operationY) == 0){
													board2.board[i + operationY][j + operationX] = Q;
													if (!white) {
														board2.board[i + operationY][j + operationX] = bQ;
													}
													board2.lastMove = moveString("P", j, i, j + operationX, i + operationY, "Q");
												}
												
												boards.add(board2);
												/*System.out.println(board2.toString());
												System.out.print(j);
												System.out.print(" +"+operationX);
												System.out.print(", "+i);
												System.out.println(" +"+operationY);
												System.out.println("");*/
											}
										}
										else if (operationX !=0 && isTheirs(this.board[i + operationY][j + operationX], white)){
											board2 = new Board();
											if(isTheirs(this.board[i + operationY][j + operationX], white)){
												board2.board[i + operationY][j + operationX] = board2.board[i][j];
												board2.board[i][j] = 0;
												board2.lastMove = moveString("P", j, i, j + operationX, i + operationY, "");
												if((i + operationY) == 0){
													board2.board[i + operationY][j + operationX] = Q;
													if (!white) {
														board2.board[i + operationY][j + operationX] = bQ;
													}
													board2.lastMove = moveString("P", j, i, j + operationX, i + operationY, "Q");
												}
												
												boards.add(board2);
												/*System.out.println(board2.toString());
												System.out.print(j);
												System.out.print(" +"+operationX);
												System.out.print(", "+i);
												System.out.println(" +"+operationY);
												System.out.println("");*/
											}
										}
									}
								}catch(IndexOutOfBoundsException e){
									//System.out.println("You dun fugged up");
								}
							}
						}
					}
				}
			}
		}

		return boards;
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
		String from = "";
		String to = "";
		String promotion = "";

		from = move.substring(1, 3);
		to = move.substring(3,5);
		if (move.length() > 5) {
			promotion = move.substring(5,6);
		}
		this.move(from, to, promotion);
		return false;
	}
	
	public Board flipBoard() {
		Board newBoard = new Board();
		for (int i = 0; i < 8; i += 1) {
			for (int j = 0; j < 8; j += 1) {
				if (this.board[7-i][7-j] >= 10)
					newBoard.board[i][j] = (byte) (this.board[7-i][7-j] - 10);
				else if (this.board[7-i][7-j] != 0)
					newBoard.board[i][j] = (byte) (this.board[7-i][7-j] + 10);
				else
					newBoard.board[i][j] = (byte) 0;
			}
		}
		
		return newBoard;
	}

	public boolean isOurs(byte piece, boolean white) {
		if (white)
			return isWhite(piece);
		return isBlack(piece);
	}

	public boolean isTheirs(byte piece, boolean white) {
		if (white)
			return isBlack(piece);
		return isWhite(piece);
	}

	public boolean isWhite(byte piece) {
		return (piece < 10 && piece != 0);
	}

	public boolean isBlack(byte piece) {
		return (piece > 10);
	}

	public int value(boolean white) {
		int wkings, wqueens, wrooks, wbishops, wknights, wpawns;
		int bkings, bqueens, brooks, bbishops, bknights, bpawns;
		
		wkings = wqueens = wrooks = wbishops = wknights = wpawns = 0;
		bkings = bqueens = brooks = bbishops = bknights = bpawns = 0;
		
		for (int i = 0; i < 7; i += 1) {
			for (int j = 0; j < 7; j += 1) {
				switch (this.board[i][j]) {
					case (K): wkings++; break;
					case (Q): wqueens++; break;
					case (R): wrooks++; break;
					case (B): wbishops++; break;
					case (N): wknights++; break;
					case (P): wpawns++; break;

					case (bK): bkings++; break;
					case (bQ): bqueens++; break;
					case (bR): brooks++; break;
					case (bB): bbishops++; break;
					case (bN): bknights++; break;
					case (bP): bpawns++; break;
				}
			}
		}

		int val = 1000 * (wkings - bkings) + 100 * (wqueens - bqueens) + 20 * (wrooks - brooks) +
			12 * (wbishops - bbishops) + 12 * (wknights - bknights) + 4 * (wpawns - bpawns);
		
		if (white)
			return val;
		return -val;
	}
	
	@Override
	public String toString(){
		String output = "";
		for(byte i=0; i<8; i++){
			if(i<8){
				output += "[";
				for(byte j=0; j<8; j++){
					if(board[i][j]<10){
						output += "0"+board[i][j]+", ";
					}
					else{
						output += board[i][j]+", ";
					}
				}
				output += "]"+"\n";
			}
		}
		return output;
	}
}
