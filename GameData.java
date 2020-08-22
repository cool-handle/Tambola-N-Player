package Tambola;

import java.util.ArrayList;

public class GameData {
	public int n;
	public ArrayList<Integer> Modgeneratedno;
	private static GameData inst ;
	public boolean gameEnd;	
	public boolean playerCanRead;
	public boolean[] idWon;
	public boolean[] idTurn;
	public Object sema;
	private GameData(int n){
		this.n=n;
		gameEnd = false;
		playerCanRead = false;
		idWon = new boolean[this.n];
		idTurn = new boolean[this.n];
		sema = new Object();
		Modgeneratedno = new ArrayList<Integer>();
	}
	public static GameData getObj(int n) {
		if(inst==null) {
			inst= new GameData(n);
		}
		return inst;
	}
	
}
