package cs472;

import java.util.ArrayList;
import java.util.Scanner;

public class checkers<A> {
	
	
	
	//TODO
	/*
	 * doublejump
	 * forcejump
	 * second eval function
	 * 
	 * 
	 */
	
	
	
	
	
	static char[][] Startboard = {
			{'_', 'm', '_', 'm', '_', 'm', '_', 'm'},
			{'m', '_', 'm', '_', 'm', '_', 'm', '_'},
			{'_', 'm', '_', 'm', '_', 'm', '_', 'm'},
			{'o', '_', 'o', '_', 'o', '_', 'o', '_'},
			{'_', 'o', '_', 'o', '_', 'o', '_', 'o'},
			{'w', '_', 'w', '_', 'w', '_', 'w', '_'},
			{'_', 'w', '_', 'w', '_', 'w', '_', 'w'},
			{'w', '_', 'w', '_', 'w', '_', 'w', '_'}

//			{'_', 'o', '_', 'm', '_', 'o', '_', 'o'},
//			{'o', '_', 'o', '_', 'o', '_', 'o', '_'},
//			{'_', 'o', '_', 'm', '_', 'o', '_', 'o'},
//			{'o', '_', 'o', '_', 'o', '_', 'o', '_'},
//			{'_', 'm', '_', 'm', '_', 'o', '_', 'o'},
//			{'o', '_', 'm', '_', 'W', '_', 'o', '_'},
//			{'_', 'm', '_', 'o', '_', 'o', '_', 'o'},
//			{'o', '_', 'o', '_', 'o', '_', 'o', '_'}
//			
			
			
			
			
	};
	public static int maxDepth = 5;
	// 8 actions 
	/*
	 * forward left
	 * forward right
	 * backward left
	 * backward right
	 * jump forward left
	 * jump forward right
	 * jump backward left
	 * jump backward right
	 */
	public static void main(String[] args) {
		char[][] gameBoard = copy(Startboard);
		printMoves(Startboard);
		
		
			while(!isGoal(gameBoard)) {
			
			//user go
			System.out.println("User Turn");
			Scanner scan = new Scanner(System.in);
			String userMove = "";
			userMove = scan.nextLine();
			gameBoard = doMove(gameBoard, userMove);
			//AI 2
//			System.out.println(System.currentTimeMillis());
//			String move2 = absearch2(gameBoard);
//			System.out.println(System.currentTimeMillis());
//			gameBoard = doMove(gameBoard, move2);
//			System.out.println("--------------------------------------------");
//			System.out.println(move2);
//			printBoard(gameBoard);
//			System.out.println("--------------------------------------------");
//			
			// AI
			printBoard(gameBoard);
			//System.out.println(System.currentTimeMillis());
			String move = absearch(gameBoard);
			//System.out.println(System.currentTimeMillis());
			gameBoard = doMove(gameBoard, move);
			System.out.println("--------------------------------------------");
			System.out.println(move);
			printBoard(gameBoard);
			System.out.println("--------------------------------------------");
			//Move 2,1 to 3,0
			//Move 2,3 to 3,2
			//Jump 4,3 to 6,5
		}
			System.out.println("END GAME");
		//user move
		
		
	}
	
	
	
	
	private static void printBoard(char[][] gameBoard) {
		// TODO Auto-generated method stub
		
		for(int i =0; i < 8; i ++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(gameBoard[i][j]);
			}
			System.out.println();
		}
		
		
	}




	public static String absearch(char[][] state){
		String actionMaxVal = "";
		double alpha = Double.NEGATIVE_INFINITY;
		ArrayList<String> actions = printMoves(state);
		//force a jump move is possible
		ArrayList<String> newActions = new ArrayList<String>();
		for(String action: actions) {
			if (action.contains("Jump")) {
				newActions.add(action);
			}
		}
		if(!newActions.isEmpty()) {
			actions = newActions;
		}
		for(String action: actions) {
			double v = minVal(result(state,action), alpha, Double.POSITIVE_INFINITY, 0);
			if(v > alpha) {
				alpha = v;
				actionMaxVal = action;
			}
		}
			return actionMaxVal;
		
		
		
	}

	public static double maxVal(char[][] state, double alpha, double beta, int depth) {
		if(isGoal(state) || depth >= maxDepth) { 
			return eval1(state);
		}
		double v = Double.NEGATIVE_INFINITY;
		ArrayList<String> actions = printMoves(state);
		for(String action: actions) {
			v = Math.max(v,minVal(result(state,action),alpha,beta, depth +1));
			if (v >= beta) {
				return v;
			}
			alpha = Math.max(alpha,v);
		}
		
		return v;
		
	}
	
	public static double minVal(char[][] state, double alpha, double beta,int depth) {
		if(isGoal(state) || depth >= maxDepth) { 
			return eval1(state);
		}
		double v = Double.POSITIVE_INFINITY;
		ArrayList<String> actions = printMoves(state);
		for(String action: actions) {
			v = Math.min(v,maxVal(result(state,action),alpha,beta, depth+1));
			if(v <= alpha) {
				return v;
			}
			beta = Math.min(beta,v);
		}
		return v;
	}
	
	public static char[][] result(char[][] state, String action) {
		char[][] childState = copy(state);
		
		if(action.contains("Jump")){
			char hold = childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))];
			childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))] = childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))];
			childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))] = hold;	
			int i = (Character.getNumericValue(action.charAt(5)) + Character.getNumericValue(action.charAt(12)))/2;
			int j = (Character.getNumericValue(action.charAt(7)) + Character.getNumericValue(action.charAt(14)))/2;
			childState[i][j] ='o';
			//check for double jump
		}
		else if( action.contains("Move")) {
			char hold = childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))];
			childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))] = childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))];
			childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))] = hold;
		}
		return childState;
		
	}
	
	
	static char[][] doMove(char[][] currBoard, String action) {
		//check if jump available
			//must jump
		//pick best move otherwise
		char[][] childState = copy(currBoard);
		
		if(action.contains("Jump")){
//			char hold = childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))];
//			childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))] = childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))];
//			childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))] = hold;	
//			int i = (Character.getNumericValue(action.charAt(5)) + Character.getNumericValue(action.charAt(12)))/2;
//			int j = (Character.getNumericValue(action.charAt(7)) + Character.getNumericValue(action.charAt(14)))/2;
//			childState[i][j] ='o';
//			
			//Jump 4,3 to 6,5 to 6,5 to 6,5
			int numjumps = action.length()/7;
			for(int k = 1 ; k < numjumps; k++) {
				char hold = childState[Character.getNumericValue(action.charAt(k*7 -2))][Character.getNumericValue(action.charAt(k*7))];
				childState[Character.getNumericValue(action.charAt(k*7 -2))][Character.getNumericValue(action.charAt((k+1)*7))] = childState[Character.getNumericValue(action.charAt((k+1)*7 -2))][Character.getNumericValue(action.charAt((k+1)*7))];
				childState[Character.getNumericValue(action.charAt((k+1)*7 -2))][Character.getNumericValue(action.charAt((k+1)*7))] = hold;	
				int i = (Character.getNumericValue(action.charAt(k*7 -2)) + Character.getNumericValue(action.charAt((k+1)*7 -2)))/2;
				int j = (Character.getNumericValue(action.charAt(k*7 -2)) + Character.getNumericValue(action.charAt((k+1)*7)))/2;
				childState[i][j] ='o';
			}
			
		}
		else if( action.contains("Move")) {
			char hold = childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))];
			childState[Character.getNumericValue(action.charAt(5))][Character.getNumericValue(action.charAt(7))] = childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))];
			childState[Character.getNumericValue(action.charAt(12))][Character.getNumericValue(action.charAt(14))] = hold;
		}
		
		
		//turn pieces to king
		for(int i = 0; i < 8; i++) {
			if(childState[0][i] == 'w') {
				childState[0][i] = 'W';
			}
			if(childState[7][i] == 'm') {
				childState[7][i] = 'M';
			}
		}
		
		
		return childState;
	}
	
	
	
	
	
	
	static boolean isGoal(char[][] board) {
		// if no white pieces remain
		// if no black pieces remain
		// if no legal moves remain
		int black = 0;
		int white = 0;
		for (int i = 0; i < 8; i ++) {
			for(int j = 0; j < 8; j++) {
				if(board[i][j] == 'w' || board[i][j] == 'W') {
					white ++;
				}
				if(board[i][j] == 'm' || board[i][j] == 'M') {
					black++;
				}
			}
		}
		
		if(black > 0 && white > 0 && !haveValidMoves(board)) {
			return true;
		}

		return false;
	}
		
	static boolean haveValidMoves(char[][] currboard) {
		boolean haveMove = false;
		char[][] board = copy(currboard);
		for (int i =0; i < 8; i++) {
			for (int j =0; j < 8; j++) {
				
				if(board[i][j] == 'w' || board[i][j] == 'W') {
					if( (i-1) >=0 && (j-1) >= 0) {
						if(board[i-1][j-1] =='o') {
							haveMove = true;
						}
						else if((board[i][j] == 'w' || board[i][j] == 'W')&& (i-2) >=0 && (j-2) >=0 && (board[i-1][i-1] == 'm' || board[i-1][i-1] == 'M')) {
							haveMove = true;
						}
						else if((board[i][j] == 'M')&& (i-2) >=0 && (j-2) >=0 && (board[i-1][i-1] == 'w' || board[i-1][i-1] == 'W')) {
							haveMove = true;
						}
					}
					if( (i-1) >=0 && (j+1) < 8) {
						if(board[i-1][j+1] =='o') {
							haveMove = true;
						}
						else if( (board[i][j] == 'w' || board[i][j] == 'W') && (i-2) >=0 && (j+2) < 8 && (board[i-1][j+1] == 'm' || board[i-1][j+1] == 'M')) {
							haveMove = true;
						}
						else if(board[i][j] == 'M' && (i-2) >=0 && (j+2) < 8 && (board[i-1][j+1] == 'w' || board[i-1][j+1] == 'W')) {
							haveMove = true;
						}
					}
				}
				
				
				
				
				if(board[i][j] == 'm' || board[i][j] == 'M' || board[i][j] == 'W') {
					if( (i+1) <8 && (j-1) >= 0) {
						if(board[i+1][j-1] =='o') {
							haveMove = true;
						}
						else if((board[i][j] == 'm' || board[i][j] == 'M')&& (i+2) < 8 && (j-2) >= 0 && (board[i+1][j-1] == 'w' || board[i+1][j-1] == 'W')) {
							haveMove = true;
						}
						else if( board[i][j] == 'W' && (i+2) < 8 && (j-2) >=0 && (board[i+1][j-1] == 'm' || board[i+1][j-1] == 'M')) {
							haveMove = true;
						}
					}
					if( (i+1) < 8 && (j+1) < 8) {
						if(board[i+1][j+1] =='o') {
							haveMove = true;
						}
						else if( (board[i][j] == 'm' || board[i][j] == 'M')&& (i+2) < 8 && (j+2) < 8 && (board[i+1][i+1] == 'w' || board[i+1][i+1] == 'W')) {
							haveMove = true;
						}
						else if( (board[i][j] == 'W')&& (i+2) < 8 && (j+2) < 8 && (board[i+1][i+1] == 'm' || board[i+1][i+1] == 'M')) {
							haveMove = true;
						}
					}
				}
			}
		}
		
		
		
		return haveMove;
	}
	//prints moves and returns an ArrayList containing the possible moves
	//public static ArrayList<String> moves = new ArrayList<String>();
	static ArrayList<String> printMoves(char[][] currboard) {
		 char[][] board = copy(currboard);
		 ArrayList<String> moves = new ArrayList<String>();
		for (int i =0; i < 8; i++) {
			for (int j =0; j < 8; j++) {
				
				if(board[i][j] == 'w' || board[i][j] == 'W') {
					if( (i-1) >=0 && (j-1) >= 0) {
						if(board[i-1][j-1] =='o') {
							moves.add("Move "+ i +","+ j + " to " + (i-1) + "," + (j-1));
						}
						if((i-2) >=0 && (j-2) >= 0 && (board[i-1][j-1] == 'm' || board[i-1][j-1] == 'M')) {
							if(board[i-2][j-2] == 'o') {
								String move =("Jump "+ i +","+ j + " to " + (i-2) + "," + (j-2));
								ArrayList<String> moveList =checkforMoreJumps(board, i-2, j-2,move, board[i][j],moves);
								for(String s : moveList) {
									if(!moves.contains(s)) {
										moves.add(s);
									}
								}
								//moves.add(move);
						}
						}
					}
					if( (i-1) >=0 && (j+1) < 8) {
						if(board[i-1][j+1] =='o') {
							moves.add("Move "+ i +","+ j + " to " + (i-1) + "," + (j+1));
						}
						if( (i-2) >=0 && (j+2) < 8 && (board[i-1][j+1] == 'm' || board[i-1][j+1] == 'M')) {
							if(board[i-2][j+2] == 'o') {
								String move ="Jump "+ i +","+ j + " to " + (i-2) + "," + (j+2);
								ArrayList<String> moveList =(checkforMoreJumps(board, i-2, j+2,move, board[i][j], moves));
								//moves.add(move);
								for(String s : moveList) {
									if(!moves.contains(s)) {
										moves.add(s);
									}
								}
						}
						}
					}
				}
				
				
				
				
				if(board[i][j] == 'W') {
					if( (i+1) >=0 && (j-1) >= 0) {
						if(board[i+1][j-1] =='o') {
							moves.add("Move "+ i +","+ j + " to " + (i+1) + "," + (j-1));
						}
						if( board[i][j] == 'W' && (i+2) <8 && (j-2) >= 0 && (board[i+1][j-1] == 'm' || board[i+1][j-1] == 'M')) {
							if(board[i+2][j-2] == 'o') {
								String move ="Jump "+ i +","+ j + " to " + (i+2) + "," + (j-2);
								ArrayList<String> moveList =checkforMoreJumps(board, i+2, j-2,move, board[i][j], moves);
								//moves.add(move);
								for(String s : moveList) {
									if(!moves.contains(s)) {
										moves.add(s);
									}
								}
						}}
					}
					
					if( (i+1) >=0 && (j+1) < 8) {
						if(board[i+1][j+1] =='o') {
							moves.add("Move "+ i +","+ j + " to " + (i+1) + "," + (j+1));
						}
						
						if( (board[i][j] == 'W')&& (i+2) >=0 && (j+2) < 8 && (board[i+1][j+1] == 'm' || board[i+1][j+1] == 'M')) {
							if(board[i+2][j+2] == 'o') {
								String move ="Jump "+ i +","+ j + " to " + (i+2) + "," + (j+2);
								ArrayList<String> moveList =(checkforMoreJumps(board, i+2, j+2,move, board[i][j], moves));
								//moves.add(move);
								for(String s : moveList) {
									if(!moves.contains(s)) {
										moves.add(s);
									}
								}
							}
						}
					}
				}
			}
		}
//		
//		for(int y = 0; y < moves.size(); y++) {
//			System.out.println(moves.get(y));
//		}
		return moves;
	}
	
	
	static ArrayList<String> checkforMoreJumps(char[][] currboard, int i, int j, String move, char piece, ArrayList<String> moves) {
		String prevmoves = move;
		char[][] board = copy(currboard);
		doMove(currboard, move);
				if( (i-1) >=0 && (j-1) >= 0) {
					if((i-2) >=0 && (j-2) < 8 && (board[i-1][j-1] == 'm' || board[i-1][j-1] == 'M')) {
						String newMove = (" to " + (i-2) + "," + (j-2));
						if(!prevmoves.contains(newMove)) {
							String nextMove = prevmoves +newMove;
							moves.add(nextMove);
							ArrayList<String> moveList =(checkforMoreJumps(board, i-2, j-2, nextMove, piece, moves));
							for(String s : moveList) {
								if(!moves.contains(s)) {
									moves.add(s);
								}
							}
						}
					}
				}
				if( (i-1) >=0 && (j+1) < 8) {
					if( (i-2) >=0 && (j+2) < 8 && (board[i-1][j+1] == 'm' || board[i-1][j+1] == 'M')) {
						String newMove =(" to " + (i-2) + "," + (j+2));
						if(!prevmoves.contains(newMove)) {
							String nextMove = prevmoves +newMove;
							moves.add(nextMove);
							ArrayList<String> moveList =(checkforMoreJumps(board, i-2, j+2, nextMove, piece, moves));
							for(String s : moveList) {
								if(!moves.contains(s)) {
									moves.add(s);
								}
							}
						}
					}
				}
			
			
			if(piece == 'W') {
				if( (i+1) <8 && (j-1) >= 0) {
					
					if( (i+2) <8 && (j-2) >= 0 && (board[i+1][j-1] == 'm' || board[i+1][j-1] == 'M')) {
						String newMove =(" to " + (i+2) + "," + (j-2));
						if(!prevmoves.contains(newMove)) {
							String nextMove = prevmoves +newMove;
							moves.add(nextMove);
							ArrayList<String> moveList =(checkforMoreJumps(board, i+2, j-2, nextMove, piece,moves));
							for(String s : moveList) {
								if(!moves.contains(s)) {
									moves.add(s);
								}
							}
						}
					}
				}
				if( (i+1) <8 && (j+1) < 8) {
					if((i+2) <8 && (j+2) < 8 && (board[i+1][j+1] == 'm' || board[i+1][j+1] == 'M')) {
						String newMove =(" to " + (i+2) + "," + (j+2));
						if(!prevmoves.contains(newMove)) {
							String nextMove = prevmoves +newMove;
							moves.add(nextMove);
							ArrayList<String> moveList =(checkforMoreJumps(board, i+2, j+2, nextMove, piece, moves));
							for(String s : moveList) {
								if(!moves.contains(s)) {
									moves.add(s);
								}
							}
						}
					}
				}
			
			}
			return moves;
		
}
	
	
	static char[][] copy(char[][] board){
		char[][] copied = new char[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j< 8; j++) {
				copied[i][j] = board[i][j];
			}
		}
		
		
		
		return copied;
	}

	 // count the pieces of each side
	//for each black piece -1
	//for each white piece +1
	//kings worth double respectively as they are more powerful pieces
	
	static int eval1(char[][] currboard) {
		int val = 0;
		//white += 1     w ;
		//white king +=2 W;
		// black -=1     m;
		//black king -=2 M;
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(currboard[i][j] == 'w') {
					val+=1;
				}
				if(currboard[i][j] == 'W') {
					val+=2;
				}
				if(currboard[i][j] == 'm') {
					val-=1;
				}
				if(currboard[i][j] == 'M') {
					val-=2;
				}
				
			}
			
		}
		
		
		//if val positive, game favors white
		//if val negative, game favors black
		return val;
		
	}
	
	
	static int eval2(char[][] currboard) {
		int val = 0;
		//white += 1     w ;
		//white king +=2 W;
		// black -=1     m;
		//black king -=2 M;
		for(int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(currboard[i][j] == 'w') {
					val += 3;			// piece weight
					val += (i); 		//how far a piece is from becoming a king
				}
				if(currboard[i][j] == 'W') {
					val += 5;			// piece weight
				}
				if(currboard[i][j] == 'm') {
					val -= 3;			// piece weight
					val -= (8-i); 		//how far a piece is from becoming a king
				}
				if(currboard[i][j] == 'M') {
					val -= 5;			// piece weight
				}
				val += 0; 				//white piees - black pieces
				
			}
			
		}
		
		
		//if val positive, game favors white
		//if val negative, game favors black
		return val;
		
	}
	
	
	

	
//	ai 2
//	
//	public static String absearch2(char[][] state){
//		String actionMaxVal = "";
//		double alpha = Double.NEGATIVE_INFINITY;
//		ArrayList<String> actions = printMoves2(state);
//		//force a jump move is possible
//		ArrayList<String> newActions = new ArrayList<String>();
//		for(String action: actions) {
//			if (action.contains("Jump")) {
//				newActions.add(action);
//			}
//		}
//		if(!newActions.isEmpty()) {
//			actions = newActions;
//		}
//		for(String action: actions) {
//			double v = minVal2(result(state,action), alpha, Double.POSITIVE_INFINITY, 0);
//			if(v > alpha) {
//				alpha = v;
//				actionMaxVal = action;
//			}
//		}
//			return actionMaxVal;
//		
//		
//		
//	}
//
//	public static double maxVal2(char[][] state, double alpha, double beta, int depth) {
//		if(isGoal(state) || depth >= maxDepth) { 
//			return eval2(state);
//		}
//		double v = Double.NEGATIVE_INFINITY;
//		ArrayList<String> actions = printMoves(state);
//		for(String action: actions) {
//			v = Math.max(v,minVal2(result(state,action),alpha,beta, depth +1));
//			if (v >= beta) {
//				return v;
//			}
//			alpha = Math.max(alpha,v);
//		}
//		
//		return v;
//		
//	}
//	
//	public static double minVal2(char[][] state, double alpha, double beta,int depth) {
//		if(isGoal(state) || depth >= maxDepth) { 
//			return eval2(state);
//		}
//		double v = Double.POSITIVE_INFINITY;
//		ArrayList<String> actions = printMoves(state);
//		for(String action: actions) {
//			v = Math.min(v,maxVal2(result(state,action),alpha,beta, depth+1));
//			if(v <= alpha) {
//				return v;
//			}
//			beta = Math.min(beta,v);
//		}
//		return v;
//	}
//	static ArrayList<String> printMoves2(char[][] currboard) {
//		 char[][] board = copy(currboard);
//		 ArrayList<String> moves = new ArrayList<String>();
//		for (int i =0; i < 8; i++) {
//			for (int j =0; j < 8; j++) {
//				if(board[i][j] == 'm' || board[i][j] == 'M') {
//				if( (i+1) >=0 && (j-1) >= 0) {
//					if(board[i+1][j-1] =='o') {
//						moves.add("Move "+ i +","+ j + " to " + (i+1) + "," + (j-1));
//					}
//					if( board[i][j] == 'M' && (i+2) <8 && (j-2) >= 0 && (board[i+1][j-1] == 'w' || board[i+1][j-1] == 'W')) {
//						if(board[i+2][j-2] == 'o') {
//							String move ="Jump "+ i +","+ j + " to " + (i+2) + "," + (j-2);
//							ArrayList<String> moveList =checkforMoreJumps2(board, i+2, j-2,move, board[i][j], moves);
//							//moves.add(move);
//							for(String s : moveList) {
//								if(!moves.contains(s)) {
//									moves.add(s);
//								}
//							}
//					}}
//				}
//				
//				if( (i+1) >=0 && (j+1) < 8) {
//					if(board[i+1][j+1] =='o') {
//						moves.add("Move "+ i +","+ j + " to " + (i+1) + "," + (j+1));
//					}
//					
//					if( (board[i][j] == 'M')&& (i+2) >=0 && (j+2) < 8 && (board[i+1][j+1] == 'w' || board[i+1][j+1] == 'W')) {
//						if(board[i+2][j+2] == 'o') {
//							String move ="Jump "+ i +","+ j + " to " + (i+2) + "," + (j+2);
//							ArrayList<String> moveList =(checkforMoreJumps2(board, i+2, j+2,move, board[i][j], moves));
//							//moves.add(move);
//							for(String s : moveList) {
//								if(!moves.contains(s)) {
//									moves.add(s);
//								}
//							}
//						}
//					}
//				}
//				
//				}
//				
//				if(board[i][j] == 'M') {
//
//					
//						if( (i+1) < 8 && (j+1) >8) {
//							if(board[i+1][j+1] =='o') {
//								moves.add("Move "+ i +","+ j + " to " + (i+1) + "," + (j+1));
//							}
//							if((i+2) >=0 && (j+2) >= 0 && (board[i+1][j+1] == 'w' || board[i+1][j+1] == 'W')) {
//								if(board[i-2][j-2] == 'o') {
//									String move =("Jump "+ i +","+ j + " to " + (i-2) + "," + (j-2));
//									ArrayList<String> moveList =checkforMoreJumps(board, i-2, j-2,move, board[i][j],moves);
//									for(String s : moveList) {
//										if(!moves.contains(s)) {
//											moves.add(s);
//										}
//									}
//									//moves.add(move);
//							}
//							}
//						}
//						if( (i-1) >=0 && (j+1) < 8) {
//							if(board[i-1][j+1] =='o') {
//								moves.add("Move "+ i +","+ j + " to " + (i-1) + "," + (j+1));
//							}
//							if( (i-2) >=0 && (j+2) < 8 && (board[i-1][j+1] == 'w' || board[i-1][j+1] == 'W')) {
//								if(board[i-2][j+2] == 'o') {
//									String move ="Jump "+ i +","+ j + " to " + (i-2) + "," + (j+2);
//									ArrayList<String> moveList =(checkforMoreJumps(board, i-2, j+2,move, board[i][j], moves));
//									//moves.add(move);
//									for(String s : moveList) {
//										if(!moves.contains(s)) {
//											moves.add(s);
//										}
//									}
//							}
//							
//						}
//					}
//					
//				}
//			}
//		}
////		
////		for(int y = 0; y < moves.size(); y++) {
////			System.out.println(moves.get(y));
////		}
//		return moves;
//	}
//	
//	
//	static ArrayList<String> checkforMoreJumps2(char[][] currboard, int i, int j, String move, char piece, ArrayList<String> moves) {
//		String prevmoves = move;
//		char[][] board = copy(currboard);
//		doMove(currboard, move);
//				if( (i-1) >=0 && (j-1) >= 0) {
//					if((i-2) >=0 && (j-2) < 8 && (board[i-1][j-1] == 'w' || board[i-1][j-1] == 'W')) {
//						String newMove = (" to " + (i-2) + "," + (j-2));
//						if(!prevmoves.contains(newMove)) {
//							String nextMove = prevmoves +newMove;
//							moves.add(nextMove);
//							ArrayList<String> moveList =(checkforMoreJumps(board, i-2, j-2, nextMove, piece, moves));
//							for(String s : moveList) {
//								if(!moves.contains(s)) {
//									moves.add(s);
//								}
//							}
//						}
//					}
//				}
//				if( (i-1) >=0 && (j+1) < 8) {
//					if( (i-2) >=0 && (j+2) < 8 && (board[i-1][j+1] == 'w' || board[i-1][j+1] == 'W')) {
//						String newMove =(" to " + (i-2) + "," + (j+2));
//						if(!prevmoves.contains(newMove)) {
//							String nextMove = prevmoves +newMove;
//							moves.add(nextMove);
//							ArrayList<String> moveList =(checkforMoreJumps(board, i-2, j+2, nextMove, piece, moves));
//							for(String s : moveList) {
//								if(!moves.contains(s)) {
//									moves.add(s);
//								}
//							}
//						}
//					}
//				}
//			
//			
//			if(piece == 'M') {
//				if( (i+1) <8 && (j-1) >= 0) {
//					
//					if( (i+2) <8 && (j-2) >= 0 && (board[i+1][j-1] == 'w' || board[i+1][j-1] == 'W')) {
//						String newMove =(" to " + (i+2) + "," + (j-2));
//						if(!prevmoves.contains(newMove)) {
//							String nextMove = prevmoves +newMove;
//							moves.add(nextMove);
//							ArrayList<String> moveList =(checkforMoreJumps(board, i+2, j-2, nextMove, piece,moves));
//							for(String s : moveList) {
//								if(!moves.contains(s)) {
//									moves.add(s);
//								}
//							}
//						}
//					}
//				}
//				if( (i+1) <8 && (j+1) < 8) {
//					if((i+2) <8 && (j+2) < 8 && (board[i+1][j+1] == 'w' || board[i+1][j+1] == 'W')) {
//						String newMove =(" to " + (i+2) + "," + (j+2));
//						if(!prevmoves.contains(newMove)) {
//							String nextMove = prevmoves +newMove;
//							moves.add(nextMove);
//							ArrayList<String> moveList =(checkforMoreJumps(board, i+2, j+2, nextMove, piece, moves));
//							for(String s : moveList) {
//								if(!moves.contains(s)) {
//									moves.add(s);
//								}
//							}
//						}
//					}
//				}
//			
//			}
//			return moves;
//		
//}
//	
//	
	
}









//
//
//
//char[][] childState = state;
//boolean isking = false;
//if (state[i][j] == 'W' || state[i][j] == 'M') {
//	isking = true;
//}
//else if(action == 0 ) {// forward left
//	char hold = childState[i-1][j-1];
//	childState[i-1][j-1] = childState[i][j];
//	childState[i][j] = hold;
//}
//else if(action == 1 ) {//forward right
//	char hold = childState[i-1][j+1];
//	childState[i-1][j+1] = childState[i][j];
//	childState[i][j] = hold;
//}
//else if(action == 2 && isking ) {//backward left
//	char hold = childState[i+1][j-1];
//	childState[i+1][j-1] = childState[i][j];
//	childState[i][j] = hold;
//}
//else if(action == 3 && isking) {//backward right
//	char hold = childState[i+1][j+1];
//	childState[i+1][j+1] = childState[i][j];
//	childState[i][j] = hold;
//	
//}		
//else if(action == 4 && isking ) {//forward jump left
//	char hold = childState[i-2][j-2];
//	childState[i-2][j-2] = childState[i][j];
//	childState[i][j] = hold;
//	childState[i-1][j-1] = 'o';
//}		
//else if(action == 5 &&isking) {//forward jump right
//	char hold = childState[i-1][j+1];
//	childState[i-2][j+2] = childState[i][j];
//	childState[i][j] = hold;
//}		
//else if(action == 6&&isking ) {//backward jump left
//	char hold = childState[i+2][j-2];
//	childState[i+2][j-2] = childState[i][j];
//	childState[i][j] = hold;
//	childState[i+1][j-1] = 'o';
//}		
//else if(action == 7&&isking ) {//backward jump right
//	char hold = childState[i+2][j+2];
//	childState[i+2][j+2] = childState[i][j];
//	childState[i][j] = hold;
//	childState[i+1][j+1] ='o'; 
//}		
//

