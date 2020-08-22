package Tambola;

import java.util.*;
public class Moderator implements Runnable {

	private GameData gameData;
	private int countOfNo=0;
	
	private Moderator(GameData gameData){
		this.gameData=gameData;
		this.countOfNo=0;
	}
	public static Moderator getObjmod (GameData game) {
		
		Moderator mod = new Moderator(game);
		return mod;
	}
	boolean isSomeWon(){
		for(int i=0; i<gameData.n ; i++) {
			if(gameData.idWon[i])
				return true;
		}
		return false;
		
	}
	boolean Allhaveread(){
		for(int i=0; i<gameData.n ; i++) {
			if(gameData.idTurn[i]==false)
				return true;
		}
		return false;
		
	}
	public void run() {
		synchronized(gameData.sema) {			

			while ( !isSomeWon() && countOfNo<10) {
				gameData.playerCanRead = false;

				for(int i=0; i<gameData.n ; i++) {
					gameData.idTurn[i] = false;
				}
				
				Random rand = new Random();
				int randomno = rand.nextInt(50)+1;
				gameData.Modgeneratedno.add(randomno);
				System.out.println("Moderator Generated "+ gameData.Modgeneratedno.get(gameData.Modgeneratedno.size()-1));
				countOfNo++;
				gameData.playerCanRead = true;
				gameData.sema.notifyAll();
				try{
				    Thread.sleep(1000); // 1 sec delay
				} catch(InterruptedException e){
					System.out.println(e);
				}
				
				
				
				while(Allhaveread()) {
					try {
						gameData.sema.wait(); 
					} catch (InterruptedException e) {
						System.out.println(e);
					}
				}				
			}
			
			int flag=0;
			
			for( int i=0;  i< gameData.n ; i++) {
				if(gameData.idWon[i]) {
					int x= i+1;
					System.out.println("Player "+ x +" has won the game.");
					flag=1;
					break;
				}	
			}
			
			if(flag==0) {
				System.out.println("No one won the game.");
			}
			
			gameData.gameEnd = true; 
			
			gameData.sema.notifyAll();
			//System.out.print("1\n");
		}	
		//System.out.print("2\n");
	}
}



