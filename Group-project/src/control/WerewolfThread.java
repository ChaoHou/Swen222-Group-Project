package control;

import gameworld.Werewolf;

import java.util.HashSet;

import ui.Board;


public class WerewolfThread extends Thread{

	//this could be collection of werewolves later on, if we want more in the game.
	private Werewolf werewolf;
	
	public WerewolfThread(){
	}
	
	public void addWerewolf(Werewolf werewolf){
		this.werewolf=werewolf;
	}
	
	
	public void run(){
		try{
			Thread.sleep(1000);
			this.werewolf.prowl();
		}catch(Exception e){
			System.out.println("error in WerewolfThread");
		}
	}

	
}
