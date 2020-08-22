package Tambola;

import java.util.*;

public class GameApp {

	public static void main(String[] args) {
		
		int Player_no=2;
		GameData game = GameData.getObj(Player_no);
		//GameData game  = new GameData(Player_no);
		Moderator mod  = Moderator.getObjmod(game);
		ArrayList<Player> players_list= new ArrayList<Player> ();
		ArrayList<Thread> players_thread = new ArrayList<Thread> ();
		for(int i=0; i< Player_no; i++) {
			Player temp = new Player(game, i);
			players_list.add(temp);
		}
		
		Thread modthread  = new Thread(mod);

		for(int i=0; i<Player_no ;i++) {
			Thread temp = new Thread(players_list.get(i));
			players_thread.add(temp);
		}
		for(int i=0; i<Player_no ;i++) {
			players_thread.get(i).start();
		}
		
		modthread.start();
		
		System.out.print("dijkfl");
		modthread.interrupt();
	}
}