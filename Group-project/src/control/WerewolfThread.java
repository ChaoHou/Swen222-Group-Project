package control;

import gameworld.Werewolf;

import java.util.HashSet;

import ui.Board;

	

public class WerewolfThread extends Thread{

	//this could be collection of werewolves later on, if we want more in the game.
	private Werewolf werewolf;
	private long prowlPeriod=2000;
	
	
	public WerewolfThread(Werewolf werewolf){
		this.werewolf=werewolf;
	}
	

	@Override
	public void run(){
		try{
			while(true){
				Thread.sleep(prowlPeriod);
				System.out.println("wolf slept half a second");
				this.werewolf.prowl();
				System.out.println("wolf has prowled ");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getClass().getName());
			System.out.println("error in WerewolfThread");
		}
	}

	
}
