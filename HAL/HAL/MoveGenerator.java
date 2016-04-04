package HAL;

import java.util.ArrayList;

public class MoveGenerator {
	//private ArrayList<Integer> moves;
	public ArrayList<Integer> findAvailableAIMoves(int[][] boardState, int[][] aiPieces, boolean side){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int i=0; i<aiPieces.length; i++){
			//AI tiefighter
			if(aiPieces[i][0]==1){
				int incTie=0;
				boolean forward=true;
				boolean backward=true;
				boolean left=true;
				boolean right=true;
				while(forward || backward || (left && side) || (right && side)){
					incTie++;
					//FORWARD
					if(forward){
						//check if out of bounds forward
						if(aiPieces[i][1]+incTie>6){
							forward=false;
						}
						//check empty spaces forward
						else if(boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]==0){
							moves.add(i);	//for indexing
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]+incTie);
							moves.add(aiPieces[i][2]);
						}
						//check if hit wall or ai pieces or human deathstar forward
						else if(boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 2 ||	//AI wall
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 1 ||	//AI tiefigher
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 7 ||	//wall
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 8 ||	//deathstar
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 4 ||	//AI X-wing
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 5){		//AI e X-wing
							forward=false;
						}
						//check if human capture forward
						else if(boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 6 ||	//tiefighter
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 9 ||	//x-wings
								boardState[aiPieces[i][1]+incTie][aiPieces[i][2]]== 10){		//e x-wings
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]+incTie);
							moves.add(aiPieces[i][2]);
							forward=false;
						}
					}
					//BACKWARD
					if(backward){
						//check if out of bounds backward
						if(aiPieces[i][1]-incTie<0){
							backward=false;
						}
						//check for captures backward
						else if(boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 8 ||	//deathstar
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 9 ||	//X-wing
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 10 ||	//e X-wing
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 6){		// tiefighter
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]-incTie);
							moves.add(aiPieces[i][2]);
							backward=false;
						}
						//check if hitting ally or walls backward
						else if(boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 2 ||	//AI wall
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 1 ||	//AI tiefighter
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 7 ||	//wall
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 4 ||	//AI x-wing
								boardState[aiPieces[i][1]-incTie][aiPieces[i][2]]== 5){		//AI e x-wing
							backward=false;
						}
					}
					//LEFT
					if(left && side){
						//check for out of bounds left
						if(aiPieces[i][2]-incTie<0){
							left=false;
						}
						//check for empty spaces left
						else if(boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]==0){
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]-incTie);
						}
						//check if hit wall or friendly player or enemy deathstar left
						else if(boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 2 ||	//AI wall
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 1 ||	//AI tiefigher
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 7 ||	//wall
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 8 ||	//deathstar
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 4 ||	//AI X-wing
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 5){		//AI e X-wing
							left=false;
						}
						//check if enemy capture left
						else if(boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 6 ||	//tiefighter
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 9 ||	//x-wings
								boardState[aiPieces[i][1]][aiPieces[i][2]-incTie]== 10){	//e x-wings
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]-incTie);
							left=false;
						}
					}
					//RIGHT
					if(right && side){
						//check if out of bounds right
						if(aiPieces[i][2]+incTie>6){
							right=false;
						}
						//check for empty spaces right
						else if(boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]==0){
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]+incTie);
						}
						//check if hit wall or friendly player or enemy deathstar right
						else if(boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 2 ||	//AI wall
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 1 ||	//AI tiefigher
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 7 ||	//wall
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 8 ||	//deathstar
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 4 ||	//AI X-wing
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 5){		//AI e X-wing
							right=false;
						}
						//check if enemy capture right
						else if(boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 6 ||	//tiefighter
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 9 ||	//x-wings
								boardState[aiPieces[i][1]][aiPieces[i][2]+incTie]== 10){	//e x-wings
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]+incTie);
							right=false;
						}
					}
				}
			}
			//AI x-wing or AI e x-wing
			if(aiPieces[i][0]==4 || aiPieces[i][0]==5){
				int incX=0;
				boolean rightUp=true;
				boolean leftUp=true;
				boolean rightDown=true;
				boolean leftDown=true;
				while(rightUp||leftUp||rightDown||leftDown){
					incX++;
					//RIGHT UP
					if(rightUp){
						//check if out of bounds rightUp
						if(aiPieces[i][1]-incX<0 || aiPieces[i][2]+incX>6){
							rightUp=false;
						}
						//check if capture rightUp
						else if(boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 8 ||		//deathstar
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 9 ||		// X-wing
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 10 ||	// e X-wing
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 6){		//tiefighter
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]-incX);
							moves.add(aiPieces[i][2]+incX);
							rightUp=false;
						}
						//check if friendly or enemy wall
						else if(boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 2 ||	//AI wall
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 1 ||	//AI tiefighter
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 7 ||	//wall
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 4 ||	//AI x-wing
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]+incX]== 5){	//AI e x-wing
							rightUp=false;
						}
					}
					//LEFT UP
					if(leftUp){
						//check if out of bounds leftUp
						if(aiPieces[i][1]-incX<0 || aiPieces[i][2]-incX<0){
							leftUp=false;
						}
						//check if capture leftup
						else if(boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 8 ||		//deathstar
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 9 ||		// X-wing
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 10 ||	// e X-wing
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 6){		//tiefighter
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]-incX);
							moves.add(aiPieces[i][2]-incX);
							leftUp=false;
						}
						//check if friendly or enemy wall leftup
						else if(boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 2 ||	//AI wall
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 1 ||	//AI tiefighter
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 7 ||	//wall
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 4 ||	//AI x-wing
								boardState[aiPieces[i][1]-incX][aiPieces[i][2]-incX]== 5){	//AI e x-wing
							leftUp=false;
						}
					}
					//RIGHTDOWN
					if(rightDown){
						//check if out of bounds rightdown
						if(aiPieces[i][1]+incX>6 || aiPieces[i][2]+incX>6){
							rightDown=false;
						}
						//check for empty spaces rightdown
						else if(boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]==0){
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]+incX);
							moves.add(aiPieces[i][2]+incX);
						}
						//check if hit wall or friendly player or enemy deathstar rightdown
						else if(boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 2 ||	//AI wall
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 1 ||	//AI tiefigher
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 7 ||	//wall
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 8 ||	//deathstar
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 4 ||	//AI X-wing
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 5){	//AI e X-wing
							rightDown=false;
						}
						//check if enemy capture rightdown
						else if(boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 6 ||	//tiefighter
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 9 ||	//x-wings
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]+incX]== 10){	//e x-wings
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]+incX);
							moves.add(aiPieces[i][2]+incX);
							rightDown=false;
						}
					}
					//LEFTDOWN
					if(leftDown){
						//check if out of bounds leftDown
						if(aiPieces[i][1]+incX>6 || aiPieces[i][2]-incX<0){
							leftDown=false;
						}
						//check for empty spaces leftDown
						else if(boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]==0){
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]+incX);
							moves.add(aiPieces[i][2]-incX);
						}
						//check if hit wall or friendly player or enemy deathstar leftDown
						else if(boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 2 ||	//AI wall
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 3 ||	//AI deathstar
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 1 ||	//AI tiefigher
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 7 ||	//wall
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 8 ||	//deathstar
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 4 ||	//AI X-wing
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 5){	//AI e X-wing
							leftDown=false;
						}
						//check if enemy capture leftDown
						else if(boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 6 ||	//tiefighter
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 9 ||	//x-wings
								boardState[aiPieces[i][1]+incX][aiPieces[i][2]-incX]== 10){	//e x-wings
							moves.add(i);
							moves.add(aiPieces[i][1]);
							moves.add(aiPieces[i][2]);
							moves.add(aiPieces[i][1]+incX);
							moves.add(aiPieces[i][2]-incX);
							leftDown=false;
						}
					}
				}
			}
		}
		return moves;
	}
	
	public ArrayList<Integer> findAvailableHumanMoves(int[][] boardState, int[][] playerPieces, boolean side){
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for(int i=0; i<playerPieces.length;i++){
			//tie fighter
			if(playerPieces[i][0]==6){
				int incTie = 0;
				boolean backward=true;
				boolean forward=true;
				boolean left=true;
				boolean right=true;
				while(backward || forward || (left && side) || (right && side)){
					incTie++;
					//FORWARD
					if(forward){
						//check if out of bounds forward
						if(playerPieces[i][1]-incTie<0){
							forward=false;
						}
						//check empty spaces forward
						else if(boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]==0){
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]-incTie);
							moves.add(playerPieces[i][2]);
						}
						//check if hit wall or friendly player or enemy deathstar forward
						else if(boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 2 ||	//AI wall
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 6 ||	//tiefigher
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 7 ||	//wall
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 8 ||	//deathstar
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 9 ||	//X-wing
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 10){	//e X-wing
							forward=false;
						}
						//check if enemy capture forward
						else if(boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 1 ||	//AI tiefighter
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 4 ||	//AI x-wings
								boardState[playerPieces[i][1]-incTie][playerPieces[i][2]]== 5){		//AI e x-wings
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]-incTie);
							moves.add(playerPieces[i][2]);
							forward=false;
						}
					}
					//BACKWARD
					if(backward){
						//check if out of bounds backward
						if(playerPieces[i][1]+incTie>6){
							backward=false;
						}
						//check for captures backward
						else if(boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 4 ||	//AI X-wing
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 5 ||	//AI e X-wing
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 1){		//AI tiefighter
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]+incTie);
							moves.add(playerPieces[i][2]);
							backward=false;
						}
						//check if hitting ally or walls. Not sure if faster than just letting the loop hit the edge.
						else if(boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 2 ||	//AI wall
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 6 ||	//tiefighter
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 7 ||	//wall
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 8 ||	//deathstar
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 9 ||	//x-wing
								boardState[playerPieces[i][1]+incTie][playerPieces[i][2]]== 10){	//e x-wing
							backward=false;
						}
					}
					//LEFT
					if(left && side){
						//check if out of bounds left
						if(playerPieces[i][2]-incTie<0){
							left=false;
						}
						//check for empty spaces left
						else if(boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]==0){
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]-incTie);
						}
						//check if hit wall or friendly player or enemy deathstar left
						else if(boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 2 ||	//AI wall
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 6 ||	//tiefigher
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 7 ||	//wall
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 8 ||	//deathstar
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 9 ||	//X-wing
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 10){	//e X-wing
							left=false;
						}
						//check if enemy capture left
						else if(boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 1 ||	//AI tiefighter
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 4 ||	//AI x-wings
								boardState[playerPieces[i][1]][playerPieces[i][2]-incTie]== 5){		//AI e x-wings
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]-incTie);
							left=false;
						}
					}
					//RIGHT
					if(right && side){
						//check if out of bounds right
						if(playerPieces[i][2]+incTie>6){
							right=false;
						}
						//check for empty spaces right
						else if(boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]==0){
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]+incTie);
						}
						//check if hit wall or friendly player or enemy deathstar right
						else if(boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 2 ||	//AI wall
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 6 ||	//tiefigher
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 7 ||	//wall
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 8 ||	//deathstar
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 9 ||	//X-wing
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 10){	//e X-wing
							right=false;
						}
						//check if enemy capture right
						else if(boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 1 ||	//AI tiefighter
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 4 ||	//AI x-wings
								boardState[playerPieces[i][1]][playerPieces[i][2]+incTie]== 5){		//AI e x-wings
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]+incTie);
							right=false;
						}
					}
				}
			}
			//X-wing or e X-wing
			else if(playerPieces[i][0]==9 || playerPieces[i][0]==10){
				int incX=0;
				boolean rightUp=true;
				boolean leftUp=true;
				boolean rightDown=true;
				boolean leftDown=true;
				while(rightUp || leftUp || rightDown){
					incX++;
					//RIGHTUP
					if(rightUp){
						//check if out of bounds rightup
						if(playerPieces[i][1]-incX<0 || playerPieces[i][2]+incX>6){
							rightUp=false;
						}
						//check for empty spaces rightup
						else if(boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]==0){
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]-incX);
							moves.add(playerPieces[i][2]+incX);
						}
						//check if hit wall or friendly player or enemy deathstar rightup
						else if(boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 2 ||	//AI wall
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 6 ||	//tiefigher
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 7 ||	//wall
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 8 ||	//deathstar
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 9 ||	//X-wing
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 10){	//e X-wing
							rightUp=false;
						}
						//check if enemy capture rightup
						else if(boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 1 ||	//AI tiefighter
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 4 ||	//AI x-wings
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]+incX]== 5){	//AI e x-wings
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]-incX);
							moves.add(playerPieces[i][2]+incX);
							rightUp=false;
						}
					}
					//LEFTUP
					if(leftUp){
						//check if out of bounds leftUp
						if(playerPieces[i][1]-incX<0 || playerPieces[i][2]-incX<0){
							leftUp=false;
						}
						//check for empty spaces leftUp
						else if(boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]==0){
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]-incX);
							moves.add(playerPieces[i][2]-incX);
						}
						//check if hit wall or friendly player or enemy deathstar leftUp
						else if(boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 2 ||	//AI wall
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 6 ||	//tiefigher
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 7 ||	//wall
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 8 ||	//deathstar
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 9 ||	//X-wing
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 10){	//e X-wing
							leftUp=false;
						}
						//check if enemy capture leftUp
						else if(boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 1 ||	//AI tiefighter
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 4 ||	//AI x-wings
								boardState[playerPieces[i][1]-incX][playerPieces[i][2]-incX]== 5){	//AI e x-wings
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]-incX);
							moves.add(playerPieces[i][2]-incX);
							leftUp=false;
						}
					}
					//RIGHTDOWN
					if(rightDown){
						//check if out of bounds rightDown
						if(playerPieces[i][1]+incX>6 || playerPieces[i][2]+incX>6){
							rightDown=false;
						}
						//check if capture rightDown
						else if(boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 4 ||	//AI X-wing
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 5 ||	//AI e X-wing
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 1){		//AI tiefighter
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]+incX);
							moves.add(playerPieces[i][2]+incX);
							rightDown=false;
						}
						//check if friendly or enemy wall
						else if(boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 2 ||	//AI wall
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 6 ||	//tiefighter
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 7 ||	//wall
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 8 ||	//deathstar
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 9 ||	//x-wing
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]+incX]== 10){	//e x-wing
							rightDown=false;
						}
					}
					//LEFTDOWN
					if(leftDown){
						//check if out of bounds leftDown
						if(playerPieces[i][1]+incX>6 || playerPieces[i][2]-incX<0){
							leftDown=false;
						}
						//check if capture leftDown
						else if(boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 3 ||	//AI deathstar
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 4 ||	//AI X-wing
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 5 ||	//AI e X-wing
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 1){		//AI tiefighter
							moves.add(i);
							moves.add(playerPieces[i][1]);
							moves.add(playerPieces[i][2]);
							moves.add(playerPieces[i][1]+incX);
							moves.add(playerPieces[i][2]-incX);
							leftDown=false;
						}
						//check if friendly or enemy wall
						else if(boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 2 ||	//AI wall
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 6 ||	//tiefighter
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 7 ||	//wall
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 8 ||	//deathstar
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 9 ||	//x-wing
								boardState[playerPieces[i][1]+incX][playerPieces[i][2]-incX]== 10){	//e x-wing
							leftDown=false;
						}
					}
				}
			}
		}
		return moves;
	}
}
