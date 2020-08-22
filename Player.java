package Tambola;
import java.util.*;
public class Player implements Runnable {

	private int id; //unique Id of each player							
	private GameData gameData;	//common data for moderator and players		
	ArrayList<Integer> tokens= new ArrayList<Integer>(); // to store the tokens of each player
	
	
	public Player(GameData gameData, int id) { 
		
		this.id = id; 		
		this.gameData = gameData;	

		// Each player has 10 random no.
		for(int i = 0; i < 10; i++) {
			Random random = new Random();
			 int temp_no = random.nextInt(5)+1; //1 , 2 , 3 , 4 , 5
			 temp_no +=  5*i;
			 tokens.add(temp_no);
		}
		int display = id+1;
		System.out.println("Player " + display + " ticket " + tokens );
		
	}
	public void run() {
		
		synchronized(gameData.sema) {			

			while(!gameData.gameEnd) {
				
				while(!gameData.playerCanRead || gameData.idTurn[id]) {
					try {
						gameData.sema.wait();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
				
				if(!gameData.gameEnd) {										
					for(int i = 0; i < tokens.size(); i++) {						
						if(gameData.Modgeneratedno.get(gameData.Modgeneratedno.size()-1) == tokens.get(i)) {
							tokens.remove(i);
							System.out.println(tokens);					
							break;
						}
					}
					// checking for array size 					
					if(tokens.size() == 7) {
						gameData.idWon[this.id] = true;						
					}
					gameData.idTurn[id] = true;
					gameData.sema.notifyAll();
				}
				
			}
			
		}
		//System.out.print("4");
	}
}
