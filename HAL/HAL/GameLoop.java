package HAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameLoop {
	private boolean legal;
	private boolean humanSide;
	private boolean aiSide;
	private int [][] board;
	private int[] moveIndex;
	private int[] aiMoveIndex;
	private int[] testAIMove;
	private Scanner kb;
	private static MoveGenerator mg;
	private int[][] playerMobilePieces;
	private int[][] aiMobilePieces;
	private int maxDepth;
	private String moveString;
	private String[] listOfMoves;
	private long startTime;
	private int[] bork;
	private String[][] moveConversion;
	private boolean aiFirst;
	
	public void start(){
		createMoveConversionTable();
		legal=false;
		bork = new int[]{-1,-1,-1,-1};
		humanSide=true;
		aiSide=true;
		board = new int[7][7];
		moveIndex = new int[4];
		mg = new MoveGenerator();
		aiMobilePieces = new int[8][3];
		playerMobilePieces = new int[8][3];
		setupInitialPositions();
		//checkPieces();
		checkFirst();
		printBoard();
		loop();
		kb.close();
	}
	public void loop(){
		while(true){
			if(!aiFirst){
				while(!legal){
					promptForMove();
					testLegal();
				}
				legal=false;
				updateHumanMove();
				printBoard();
				//printPieces();
				if(checkAIGameover()){	//check gameover
					break;
				}
			}
			aiFirst=false;
			maxDepth=1;	//initial maxDepth
			startTime=System.currentTimeMillis();	//searches current time
			while(System.currentTimeMillis()-startTime<5000){	//checks if over 5 seconds
				System.out.println(maxDepth);
				testAIMove=minimax();
				//System.out.println(convertMoveFromInt(testAIMove[0], testAIMove[1], testAIMove[2], testAIMove[3]));
				if(!Arrays.equals(testAIMove, bork)){
					aiMoveIndex=testAIMove;
				}
				maxDepth++;
			}
			updateAIMove();
			//printPieces();
			printBoard();
			if(checkPlayerGameover()){	//check gameover
				break;
			}
		}
		displayResult();
		try{
			TimeUnit.SECONDS.sleep(10);
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	//Initializes Positions
	public void setupInitialPositions(){
		int aiIndex=0;
		int pIndex=0;
		for(int i=0; i<7; i++){
			for(int j=0; j<7; j++){		
				//Place AI Tie Fighters
				if((i==0 && (j==1 || j==2 || j==4 || j==5))){
					board[i][j]=1;
					aiMobilePieces[aiIndex][0]=board[i][j];
					aiMobilePieces[aiIndex][1]=i;
					aiMobilePieces[aiIndex][2]=j;
					aiIndex++;
				}
				//Place AI Walls
				else if((i==1 && (j==2 || j==4))){
					board[i][j]=2;
				}
				//Place AI Deathstar
				else if(i==1 && j==3){
					board[i][j]=3;
				}
				//Place AI X-Wings
				else if(i==2 && (j==0 || j==6)){
					board[i][j]=4;
					aiMobilePieces[aiIndex][0]=board[i][j];
					aiMobilePieces[aiIndex][1]=i;
					aiMobilePieces[aiIndex][2]=j;
					aiIndex++;
				}
				//Place AI E X-Wings
				else if(i==2 && (j==1 || j==5)){
					board[i][j]=5;
					aiMobilePieces[aiIndex][0]=board[i][j];
					aiMobilePieces[aiIndex][1]=i;
					aiMobilePieces[aiIndex][2]=j;
					aiIndex++;
				} 
				//Place player Tie Fighters
				else if(i==6 &&(j==1 || j==2 || j==4 || j==5)){
					board[i][j]=6;
					playerMobilePieces[pIndex][0]=board[i][j];
					playerMobilePieces[pIndex][1]=i;
					playerMobilePieces[pIndex][2]=j;
					pIndex++;
				}
				//Place player Walls
				else if(i==5 && (j==2 || j==4)){
					board[i][j]=7;
				}
				//Place player Deathstar
				else if(i==5 && j==3){
					board[i][j]=8;
				}
				//Place player X-Wings
				else if(i==4 && (j==0 || j==6)){
					board[i][j]=9;
					playerMobilePieces[pIndex][0]=board[i][j];
					playerMobilePieces[pIndex][1]=i;
					playerMobilePieces[pIndex][2]=j;
					pIndex++;
				}
				//Place player E X-Wings
				else if(i==4 && (j==1 || j==5)){
					board[i][j]=10;
					playerMobilePieces[pIndex][0]=board[i][j];
					playerMobilePieces[pIndex][1]=i;
					playerMobilePieces[pIndex][2]=j;
					pIndex++;
				}
				//Place empty spots
				else{
					board[i][j] = 0;
				}
			}
		}
	}
	//Prints Board
	public void printBoard(){
		char letter = 'A';
		int num = 7;
		for(int i=0; i<7; i++){
			for(int j=0; j<7; j++){
				if(j==0){
					System.out.print(num+"  ");
					num--;
				}
				switch(board[i][j]){
				case 1:
					System.out.print("T ");
					break;
				case 2:
					System.out.print("~ ");
					break;
				case 3:
					System.out.print("* ");
					break;
				case 4:
					System.out.print("X ");
					break;
				case 5:
					System.out.print("X ");
					break;
				case 6:
					System.out.print("t ");
					break;
				case 7:
					System.out.print("+ ");
					break;
				case 8:
					System.out.print("@ ");
					break;
				case 9:
					System.out.print("x ");
					break;
				case 10:
					System.out.print("x ");
					break;
				default:
					System.out.print("- ");
					break;
				}
				if(j==6){
					System.out.println();
				}
			}
		}
		System.out.print("\n   ");
		for(int i=0; i<7; i++){
			System.out.print(letter+" ");
			letter++;
		}
	}
	public void printPieces(){
		System.out.println("Player Pieces:");
		for(int i=0; i<playerMobilePieces.length;i++){
			System.out.println(playerMobilePieces[i][0]+" "+playerMobilePieces[i][1]+" "+playerMobilePieces[i][2]);
		}
		System.out.println("AI Pieces:");
		for(int i=0; i<aiMobilePieces.length; i++){
			System.out.println(aiMobilePieces[i][0]+" "+aiMobilePieces[i][1]+" "+aiMobilePieces[i][2]);
		}
	}
	//Prompts for move
	public void promptForMove(){
		kb = new Scanner(System.in);
		//Lists possible human moves
		listOfMoves=convertMoveFromIntList(mg.findAvailableHumanMoves(board, playerMobilePieces, humanSide));
		System.out.println();
		for(int i=0; i<listOfMoves.length; i++){
			System.out.print(listOfMoves[i]+" ");
		}
		moveString = kb.next();
		moveString = moveString.toUpperCase();
		System.out.println(moveString);	//prints move entered
	}
	//Print AI moves available
	public void printAIMoves(ArrayList<Integer> moves){
		String[] moveList = convertMoveFromIntList(moves);
		System.out.println();
		for(int i=0; i<moveList.length; i++){
			System.out.print(moveList[i]+" ");
		}
	}
	//convert string move to int array
	public int[] convertMoveFromString(String move){
		char[] tmp = move.toCharArray();
		int[] intMove = new int[4];
		for(int i=0; i<4; i++){
			if(i==0 || i==2){
				intMove[i+1]=tmp[i]-65;	//flipped over
			}
			else if (i==1 || i==3){
				intMove[i-1]=55-tmp[i];	//flipped over
			}
		}
		
		return intMove;
	}
	//takes in 4 ints and converts to appropriate string for easy reading
	public String convertMoveFromInt(int msi, int msj, int mi, int mj){
		String str;
		str= ((char)(msj+65))+""+((char)(55-msi))+((char)(mj+65))+""+((char)(55-mi));
		return str;
	}
	//convert int array to string
	public String[] convertMoveFromIntList(ArrayList<Integer> moves){
		char[] tmpArr = new char[moves.size()-moves.size()/5];
		int ind=0;
		for(int i=0; i<(moves.size()-moves.size()/5); i+=4){
			tmpArr[i] = (char) (moves.get(ind+2)+65);	//Letter
			tmpArr[i+1] = (char) (55-moves.get(ind+1));	//Number
			tmpArr[i+2] = (char) (moves.get(ind+4)+65);	//Letter
			tmpArr[i+3] = (char) (55-moves.get(ind+3));	//Number
			ind+=5;
		}
		//puts into string array
		String[] moveList = new String[tmpArr.length/4];
		ind=0;
		for(int x=0; x<tmpArr.length; x+=4){
			moveList[ind]= new StringBuilder().append(tmpArr[x]).append(tmpArr[x+1]).append(tmpArr[x+2]).append(tmpArr[x+3]).toString();
			ind++;
		}
		return moveList;
	}
	
	//Test if legal
	public void testLegal(){
		for(String s : listOfMoves){
			if(s.equals(moveString)){
				moveIndex = convertMoveFromString(moveString);
				legal=true;
			}
		}
		if(!legal){
			System.out.println("Not a legal move!");
		}
	}
	//Make Move
	public void updateHumanMove(){
		humanSide=true;
		for(int j=0; j<playerMobilePieces.length; j++){
			if(moveIndex[0]==playerMobilePieces[j][1]&&moveIndex[1]==playerMobilePieces[j][2]){
				playerMobilePieces[j][1]=moveIndex[2];
				playerMobilePieces[j][2]=moveIndex[3];
			}
		}
		//update enemy pieces list in case of capture
		for(int i=0; i<aiMobilePieces.length; i++){
			if(moveIndex[2]==aiMobilePieces[i][1] && moveIndex[3]==aiMobilePieces[i][2]){
				aiMobilePieces[i][0]=0; //capture makes int not used in move generation
				aiMobilePieces[i][1]=-1;
				aiMobilePieces[i][2]=-1;
				System.out.println("CAPTURE");
			}
		}
		if(moveIndex[0]==moveIndex[2]){
			humanSide=false;
		}
		//System.out.println(humanSide);
		//update board
		board[moveIndex[2]][moveIndex[3]]=board[moveIndex[0]][moveIndex[1]];
		board[moveIndex[0]][moveIndex[1]]=0;
	}
	
	//Make AI Move
	public void updateAIMove(){
		aiSide=true;	//resets sideways move for next turn
		String reverseMove=convertMoveToHuman(convertMoveFromInt(aiMoveIndex[0],aiMoveIndex[1],aiMoveIndex[2],aiMoveIndex[3]));
		System.out.println("\n My move is "+convertMoveFromInt(aiMoveIndex[0],aiMoveIndex[1],aiMoveIndex[2],aiMoveIndex[3])+" ("+reverseMove+")");
		int msi=aiMoveIndex[0];
		int msj=aiMoveIndex[1];
		int mi=aiMoveIndex[2];
		int mj=aiMoveIndex[3];
		board[mi][mj]=board[msi][msj];	//Make move
		board[msi][msj]=0;				//Make move
		//update pieces list
		for(int i=0; i<aiMobilePieces.length; i++){
			if(aiMobilePieces[i][1]==msi && aiMobilePieces[i][2]==msj){
				aiMobilePieces[i][1]=mi;
				aiMobilePieces[i][2]=mj;
			}
		}
		//update enemy pieces list incase of captures
		for(int i=0; i<playerMobilePieces.length; i++){
			if(playerMobilePieces[i][1]==mi && playerMobilePieces[i][2]==mj){
				playerMobilePieces[i][0]=0; //capture makes int not used in move generation
				playerMobilePieces[i][1]=-1;
				playerMobilePieces[i][2]=-1;
				System.out.println("CAPTURE");
			}
		}
		if(msi==mi){
			aiSide=false;
		}
		//System.out.println(aiSide);
	}
	/*
	//timer method for minimax + minimax
	public void threadedMinimax(){
		maxDepth=1;
		ExecutorService exe = Executors.newSingleThreadExecutor();
		Future f = exe.submit(new Runnable(){
			@Override
			public void run() {
				startTime = System.currentTimeMillis();
				while(!Thread.interrupted()){
					aiMoveIndex=minimax();
					maxDepth++;
				}
			}
			
		});
		try{
			f.get(5, TimeUnit.SECONDS);
		}catch (TimeoutException e){
			f.cancel(true);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		exe.shutdown();
	}
	*/
	//MINIMAX
	public int[] minimax(){
		int best=-9999;
		int depth=0;
		int score;
		int msi=-1;
		int msj=-1;
		int mi=-1;
		int mj=-1;
		
		ArrayList<Integer> moves=mg.findAvailableAIMoves(board, aiMobilePieces, aiSide);
		//printAIMoves(moves);
		for(int index=0; index<moves.size(); index+=5){
			if(best==10000 || best==-10000){
				break;
			}
			int prevStart= board[moves.get(index+1).intValue()][moves.get(index+2).intValue()];	//Gets piece thats moving
			int prevEnd = board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]; //Gets what used to be at that location. In case of captures
			board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]=prevStart;	//Make move
			board[moves.get(index+1).intValue()][moves.get(index+2).intValue()]=0;												//
			aiMobilePieces[moves.get(index).intValue()][1]=moves.get(index+3).intValue();											//
			aiMobilePieces[moves.get(index).intValue()][2]=moves.get(index+4).intValue();											//
			//change enemy pieces list in case of capture
			int prevPieceIndex=-1;
			int prevPieceI=-1;
			int prevPieceJ=-1;
			if(prevEnd != 0 && prevEnd!=3 && prevEnd!=8){
				for(int piece=0; piece<playerMobilePieces.length; piece++){
					if(playerMobilePieces[piece][1]==moves.get(index+3).intValue() && playerMobilePieces[piece][2]==moves.get(index+4).intValue()){
						prevPieceIndex=piece;
						prevPieceI=playerMobilePieces[piece][1];
						prevPieceJ=playerMobilePieces[piece][2];
						playerMobilePieces[piece][0]=0;	//made into int not used in move generation
						playerMobilePieces[piece][1]=-1;
						playerMobilePieces[piece][2]=-1;
						break;
					}
				}
			}
			//MIN CALL
			if(moves.get(index+1).intValue()==moves.get(index+3).intValue()){
				score=min(depth+1, humanSide, false, best);
			}
			else{
				score=min(depth+1, humanSide, true, best);
			}
			if(score==10000 || score==-10000){	//Timeout set
				msi=-1;
				msj=-1;
				mi=-1;
				mj=-1;
				best=score;
			}
			else if(score>best){		//Replace score
				msi=moves.get(index+1).intValue();
				msj=moves.get(index+2).intValue();
				mi=moves.get(index+3).intValue();
				mj=moves.get(index+4).intValue();
				best=score;
			}
			board[moves.get(index+1).intValue()][moves.get(index+2).intValue()]=prevStart;	//Undo move
			board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]=prevEnd;		//
			aiMobilePieces[moves.get(index).intValue()][1]=moves.get(index+1).intValue();		//
			aiMobilePieces[moves.get(index).intValue()][2]=moves.get(index+2).intValue();		//
			//Undo captures
			if(prevEnd != 0 && prevEnd!=3 && prevEnd!=8){
				playerMobilePieces[prevPieceIndex][0]=prevEnd;	//replaced with int used in move generation
				playerMobilePieces[prevPieceIndex][1]=prevPieceI;
				playerMobilePieces[prevPieceIndex][2]=prevPieceJ;
			}
		}
		return new int[]{msi,msj,mi,mj};
	}
	//MIN
	public int min(int depth, boolean humanSide, boolean aiSide, int curBest){
		int best = 9999;
		if(System.currentTimeMillis()-startTime>5000){
			return -10000;	//special int for timeout
		}
		else if(checkAIGameover()){
			return -9998+depth;
		}
		else if(checkPlayerGameover()){
			return 9998-depth;
		}
		else if(depth==maxDepth){
			return eval()+depth;
		}
		else{
			int score;
			ArrayList<Integer> moves = mg.findAvailableHumanMoves(board, playerMobilePieces,humanSide);
			for(int index=0; index<moves.size(); index+=5){
				int prevStart= board[moves.get(index+1).intValue()][moves.get(index+2).intValue()];	//Gets piece thats moving
				int prevEnd = board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]; //Gets what used to be at that location. In case of captures
				board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]=prevStart;	//Make move
				board[moves.get(index+1).intValue()][moves.get(index+2).intValue()]=0;												//
				playerMobilePieces[moves.get(index).intValue()][1]=moves.get(index+3).intValue();										//
				playerMobilePieces[moves.get(index).intValue()][2]=moves.get(index+4).intValue();										//
				//captures
				int prevPieceIndex=-1;
				int prevPieceI=-1;
				int prevPieceJ=-1;
				if(prevEnd != 0 && prevEnd!=3 && prevEnd!=8){
					for(int piece=0; piece<aiMobilePieces.length; piece++){
						if(aiMobilePieces[piece][1]==moves.get(index+3).intValue() && aiMobilePieces[piece][2]==moves.get(index+4).intValue()){
							prevPieceIndex=piece;
							prevPieceI=aiMobilePieces[piece][1];
							prevPieceJ=aiMobilePieces[piece][2];
							aiMobilePieces[piece][0]=0;	//made into int not used in move generation
							aiMobilePieces[piece][1]=-1;
							aiMobilePieces[piece][2]=-1;
							break;
						}
					}
				}
				//MAX CALL
				if(moves.get(index+1).intValue()==moves.get(index+3).intValue()){
					score=max(depth+1, false, aiSide, best)+depth;
				}
				else{
					score=max(depth+1, true, aiSide, best)+depth;
				}
				if(score<best){
					best=score;
				}
				board[moves.get(index+1).intValue()][moves.get(index+2).intValue()]=prevStart;	//Undo move
				board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]=prevEnd;		//
				playerMobilePieces[moves.get(index).intValue()][1]=moves.get(index+1).intValue();	//
				playerMobilePieces[moves.get(index).intValue()][2]=moves.get(index+2).intValue();	//
				//undo captures
				if(prevEnd != 0 && prevEnd!=3 && prevEnd!=8){
					aiMobilePieces[prevPieceIndex][0]=prevEnd;	//replaced with int used in move generation
					aiMobilePieces[prevPieceIndex][1]=prevPieceI;
					aiMobilePieces[prevPieceIndex][2]=prevPieceJ;
				}
				//Alpha beta prune
				if(best<=curBest){
					break;
				}
			}
			
		}
		return best;
	}
	//MAX
	public int max(int depth, boolean humanSide, boolean aiSide, int curBest){
		int best=-9999;
		if(System.currentTimeMillis()-startTime>5000){
			return 10000;	//special int for timeout
		}
		else if(checkAIGameover()){
			return -9998+depth;
		}
		else if(checkPlayerGameover()){
			return 9998-depth;
		}
		else if(depth==maxDepth){
			return eval()-depth;
		}
		else{
			int score;
			ArrayList<Integer> moves = mg.findAvailableAIMoves(board, aiMobilePieces, aiSide);
			for(int index=0; index<moves.size(); index+=5){
				int prevStart= board[moves.get(index+1).intValue()][moves.get(index+2).intValue()];	//Gets piece thats moving
				int prevEnd = board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]; //Gets what used to be at that location. In case of captures
				
				board[moves.get(index+3)][moves.get(index+4).intValue()]=prevStart;	//Make move
				board[moves.get(index+1)][moves.get(index+2).intValue()]=0;												//
				aiMobilePieces[moves.get(index).intValue()][1]=moves.get(index+3).intValue();											//
				aiMobilePieces[moves.get(index).intValue()][2]=moves.get(index+4).intValue();											//
				//change enemy pieces list if capture
				int prevPieceIndex=-1;
				int prevPieceI=-1;
				int prevPieceJ=-1;
				if(prevEnd != 0 && prevEnd!=3 && prevEnd!=8){
					for(int piece=0; piece<playerMobilePieces.length; piece++){
						if(playerMobilePieces[piece][1]==moves.get(index+3).intValue() && playerMobilePieces[piece][2]==moves.get(index+4).intValue()){
							prevPieceIndex=piece;
							prevPieceI=playerMobilePieces[piece][1];
							prevPieceJ=playerMobilePieces[piece][2];
							playerMobilePieces[piece][0]=0;	//made into int not used in move generation
							playerMobilePieces[piece][1]=-1;
							playerMobilePieces[piece][2]=-1;
							break;
						}
					}
				}
				//MIN CALL
				if(moves.get(index+1).intValue()==moves.get(index+3).intValue()){
					score=min(depth+1, humanSide, false, best)-depth;
				}
				else{
					score=min(depth+1, humanSide, true, best)-depth;
				}
				if(score>best){
					best=score;
				}
				board[moves.get(index+1).intValue()][moves.get(index+2).intValue()]=prevStart;	//Undo move
				board[moves.get(index+3).intValue()][moves.get(index+4).intValue()]=prevEnd;		//
				aiMobilePieces[moves.get(index).intValue()][1]=moves.get(index+1).intValue();		//
				aiMobilePieces[moves.get(index).intValue()][2]=moves.get(index+2).intValue();		//
				//Undo captures
				if(prevEnd != 0 && prevEnd!=3 && prevEnd!=8){
					playerMobilePieces[prevPieceIndex][0]=prevEnd;	//replaced with int used in move generation
					playerMobilePieces[prevPieceIndex][1]=prevPieceI;
					playerMobilePieces[prevPieceIndex][2]=prevPieceJ;
				}
				//Alpha beta prune
				if(best>=curBest){
					break;
				}
			}
		}
		return best;
	}
	//Eval
	public int eval(){
		int eval=0;
		int aiXWings=0;
		int aiEXWings=0;
		int aiTieFighters=0;
		int pXWings=0;
		int pEXWings=0;
		int pTieFighters=0;
		int piecesLeft=0;
		int enemyPiecesLeft=0;
		
		//numbers from AI Mobile Pieces left
		for(int pieceNum=0; pieceNum<aiMobilePieces.length;pieceNum++){
			if(aiMobilePieces[pieceNum][0]!=0){
				piecesLeft++;
			}
			if(aiMobilePieces[pieceNum][0]==4){ //AIXWings
				aiXWings++;
			}
			else if(aiMobilePieces[pieceNum][0]==5){	//AIEXWings
				aiEXWings++;
			}
			else if(aiMobilePieces[pieceNum][0]==1){	//AITiefighters
				aiTieFighters++;
			}
		}
		//Numbers from Player Mobile Pieces left
		for(int pieceNum=0; pieceNum<playerMobilePieces.length; pieceNum++){
			if(playerMobilePieces[pieceNum][0]!=0){
				enemyPiecesLeft++;
			}
			if(playerMobilePieces[pieceNum][0]==9){ //pXWings
				pXWings++;
			}
			else if(playerMobilePieces[pieceNum][0]==10){	//pEXWings
				pEXWings++;
			}
			else if(playerMobilePieces[pieceNum][0]==6){	//pTiefighters
				pTieFighters++;
			}
		}
		
		eval=(2*piecesLeft+2*aiXWings+1*aiEXWings+3*aiTieFighters)-(2*enemyPiecesLeft+2*pXWings+1*pEXWings+3*pTieFighters);
		return eval;
	}
	//Check Gameover
	public boolean checkAIGameover(){
		//RULES:
		//Game is over when:
		//if it is his/her opponent's turn but the opponent has no legal moves
		//One or the other deathstar is destroyed
		
		//check AI deathstar
		if(board[1][3] !=3){
			return true;
		}
		//check if no AI moves left
		else if(mg.findAvailableAIMoves(board, aiMobilePieces, aiSide).isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean checkPlayerGameover(){
		//RULES:
		//Game is over when:
		//if it is his/her opponent's turn but the opponent has no legal moves
		//One or the other deathstar is destroyed
		
		//check Player deathstar
		if(board[5][3]!=8){
			return true;
		}
		//check if no Player moves left
		else if(mg.findAvailableHumanMoves(board, playerMobilePieces, humanSide).isEmpty()){
			return true;
		}
		else{
			return false;
		}
	}
	//Display result
	public void displayResult(){
		System.out.println();
		//check player deathstar
		if(board[5][3]!=8){
			System.out.println("The AI has won by destruction of the Player's Deathstar!");
		}
		//check AI deathstar
		else if(board[1][3]!=3){
			System.out.println("The Player has won by destruction of the AI Deathstar!");
		}
		//check Player moves
		else if(mg.findAvailableHumanMoves(board, playerMobilePieces, humanSide).isEmpty()){
			System.out.println("The AI has won since the Player has no moves left!");
		}
		//check AI moves
		else if(mg.findAvailableAIMoves(board, aiMobilePieces, aiSide).isEmpty()){
			System.out.println("The Player has won since the AI has no moves left!");
		}
	}
	//creates the move conversion table
	public void createMoveConversionTable(){
		moveConversion = new String[49][2];
		char l = 'A';
		int c = 7;
		//first half
		for(int i=0; i<moveConversion.length;i++){
			moveConversion[i][0]= new StringBuilder().append(l).append(c).toString();
			c--;
			if(c<1){
				c=7;
				l++;
			}
		}
		l = 'G';
		c = 1;
		//second half
		for(int i=0; i<moveConversion.length;i++){
			moveConversion[i][1]= new StringBuilder().append(l).append(c).toString();
			c++;
			if(c>7){
				c=1;
				l--;
			}
		}
		/*
		//print
		for(int i=0; i<moveConversion.length;i++){
			for(int j=0; j<moveConversion[i].length;j++){
				System.out.println(moveConversion[i][j]);
			}
		}
		*/
	}
	public String convertMoveToHuman(String moveString){
		String s1 = moveString.substring(0, moveString.length()/2);
		String s2 = moveString.substring(moveString.length()/2);
		String reverseMove="";
		for(int i=0; i<moveConversion.length;i++){
			if(moveConversion[i][0].equals(s1)){
				reverseMove+=moveConversion[i][1];
			}
		}
		for(int i=0; i<moveConversion.length;i++){
			if(moveConversion[i][0].equals(s2)){
				reverseMove+=moveConversion[i][1];
			}
		}
		return reverseMove;
	}
	//check who goes first
	public void checkFirst(){
		kb = new Scanner(System.in);
		String firstString;
		System.out.println("Would the AI go first? Y/N");
		firstString = kb.next();
		firstString = firstString.toUpperCase();
		if(firstString.equals("Y")){
			aiFirst=true;
		}
		else{
			aiFirst=false;
		}
	}
}
