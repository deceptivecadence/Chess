
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
	
	public void moveKings(){
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 1;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == K){
					byte operationX;
					byte operationY;
					
					for(byte x=-1; x<2; x++){
						for(byte y=-1; y<2; y++){
							for(byte k=1; k<=moveNumber; k++){
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								try{
									if ((isWhite(this.board[i + operationY][j + operationX]) || 
										(i + operationY < 0) || (i + operationY > 7) ||
										(j + operationX < 0) || (j + operationX > 7))){
										break;
									}
									else {
										Board board2 = new Board();
										board2.board[i + operationY][j + operationX] = board2.board[i][j];
										board2.board[i][j] = 0;
										System.out.println(board2.toString());

										if (isBlack(this.board[i + operationY][j + operationX])) {
											break;
										}
									}
									//add board to "frontier" or wat do
								}catch(IndexOutOfBoundsException e){
									System.out.println("You dun fugged up");
								}

								if (i == 7 && j == 4 && this.board[7][7] == R &&
									this.board[7][5] == 0 && this.board[7][6] == 0) {

									//castle
									Board board2 = new Board();
									board2.board[7][6] = K;
									board2.board[7][4] = 0;
									board2.board[7][5] = R;
									board2.board[7][7] = 0;
									boards.add(board2);
									System.out.println(board2.toString());
								}
							}
						}
					}
				}
			}
		}

		return boards;
	} 
	
	
	public void moveQueens(){
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 7;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == Q){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
												
						for(byte y=-1; y<2; y++){
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								try{
									if (isWhite(this.board[i + operationY][j + operationX]) || 
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
										boards.add(board2);
										System.out.println(board2.toString());
										System.out.print(j);
										System.out.print(" +"+operationX);
										System.out.print(", "+i);
										System.out.println(" +"+operationY);
										System.out.println("");

										//we can still do the move if there is a black piece here, but we can go no further.
										if (isBlack(this.board[i + operationY][j + operationX])) {
											break;
										}
										//}
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

		return boards;
	}
	
	
	public void moveRooks(){
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 7;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == R){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
											
						for(byte y=-1; y<2; y++){
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								if(operationX==0||operationY==0){
									try{
										if (isWhite(this.board[i + operationY][j + operationX]) || 
											(i + operationY < 0) || (i + operationY > 7) ||
											(j + operationX < 0) || (j + operationX > 7)) {
											break;
										}
										else{

											Board board2 = new Board();
											board2.board[i + operationY][j + operationX] = board2.board[i][j];
											board2.board[i][j] = 0;
											boards.add(board2);
											System.out.println(board2.toString());
											System.out.print(j);
											System.out.print(" +"+operationX);
											System.out.print(", "+i);
											System.out.println(" +"+operationY);
											System.out.println("");
	
											//we can still do the move if there is a black piece here, but we can go no further.
											if (isBlack(this.board[i + operationY][j + operationX])) {
												break;
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

		return boards;
	}
	
	public void moveBishops(){
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 7;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == B){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
											
						for(byte y=-1; y<2; y++){
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);
								if(operationX!=0 && operationY!=0){
									try{
										if (isWhite(this.board[i + operationY][j + operationX]) || 
											(i + operationY < 0) || (i + operationY > 7) ||
											(j + operationX < 0) || (j + operationX > 7)) {
											break;
										}
										else{

											Board board2 = new Board();
											board2.board[i + operationY][j + operationX] = board2.board[i][j];
											board2.board[i][j] = 0;
											boards.add(board2);
											System.out.println(board2.toString());
											System.out.print(j);
											System.out.print(" +"+operationX);
											System.out.print(", "+i);
											System.out.println(" +"+operationY);
											System.out.println("");
	
											//we can still do the move if there is a black piece here, but we can go no further.
											if (isBlack(this.board[i + operationY][j + operationX])) {
												break;
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

		return boards;
	}

	public void moveKnights(){
		ArrayList<Board> boards = new ArrayList<Board>();
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == N){
					byte operationX;
					byte operationY;

					byte[][] wombo = {{-1,-2}, {1,-2}, {-2,-1}, {2,-1},
										{-2,1}, {2,1}, {-1,2}, {1,2}};
					
					for(byte[] coords : wombo) {

						operationX = coords[0];
						operationY = coords[1];
						try{
							if (isWhite(this.board[i + operationY][j + operationX]) || 
								(i + operationY < 0) || (i + operationY > 7) ||
								(j + operationX < 0) || (j + operationX > 7)){
								continue;
							}
							else {
								Board board2 = new Board();
								board2.board[i + operationY][j + operationX] = board2.board[i][j];
								board2.board[i][j] = 0;
								boards.add(board2);
								System.out.println(board2.toString());

								if (isBlack(this.board[i + operationY][j + operationX])) {
									continue;
								}
							}
							//add board to "frontier" or wat do
						}catch(IndexOutOfBoundsException e){
							System.out.println("You dun fugged up");
						}
					}
				}
			}
		}

		return boards;
	}
	
	
	public void movePawns(){
		ArrayList<Board> boards = new ArrayList<Board>();
		byte moveNumber = 1;
		for (byte i=0; i<8; i++){
			for (byte j=0; j<8; j++){//search entire board
				if(this.board[i][j] == P){
					byte operationX;
					byte operationY;
					for(byte x=-1; x<2; x++){
											
						for(byte y=-1; y<0; y++){ 
							
							for(byte k=1; k<=moveNumber; k++){//degree of movement (spaces away from original spot)
								operationX = (byte) (x*k);
								operationY = (byte) (y*k);

								try{
									if (isWhite(this.board[i + operationY][j + operationX]) || 
										(i + operationY < 0) || (i + operationY > 7) ||
										(j + operationX < 0) || (j + operationX > 7)) {
										break;
									}
									else{
										Board board2 = new Board();
										if(operationX==0 && !isBlack(this.board[i + operationY][j + operationX])){
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
												if((i + operationY) == 0){
													board2.board[i + operationY][j + operationX] = Q;
												}
												boards.add(board2);
												System.out.println(board2.toString());
												System.out.print(j);
												System.out.print(" +"+operationX);
												System.out.print(", "+i);
												System.out.println(" +"+operationY);
												System.out.println("");
											}
										}
										else if (operationX !=0 && isBlack(this.board[i + operationY][j + operationX])){
											board2 = new Board();
											if(isBlack(this.board[i + operationY][j + operationX])){
												board2.board[i + operationY][j + operationX] = board2.board[i][j];
												board2.board[i][j] = 0;
												if((i + operationY) == 0){
													board2.board[i + operationY][j + operationX] = Q;
												}
												boards.add(board2);
												System.out.println(board2.toString());
												System.out.print(j);
												System.out.print(" +"+operationX);
												System.out.print(", "+i);
												System.out.println(" +"+operationY);
												System.out.println("");
											}
										}
										//we can still do the move if there is a black piece here, but we can go no further.
										if (isBlack(this.board[i + operationY][j + operationX])) {
											break;
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
			}ArrayList<Board> boards = new ArrayList<Board>();
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
		this.move(from, to, promotion);
		return false;
	}
	
	public Board flipBoard() {
		Board newBoard = new Board();
		for (int i = 0; i < 8; i += 1) {
			for (int j = 0; j < 8; j += 1) {
				if (this.board[7-i][7-j] >= 10)
					newBoard.board[i][j] = (byte) (this.board[7-i][7-j] - 10);
				else
					newBoard.board[i][j] = (byte) (this.board[7-i][7-j] + 10);
			}
		}
		
		return newBoard;
	}

	public boolean isWhite(byte piece) {
		return (piece < 10 && piece != 0);
	}

	public boolean isBlack(byte piece) {
		return (piece > 10);
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
