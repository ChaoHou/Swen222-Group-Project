package control;

import gameworld.Werewolf;

import java.util.HashSet;

import ui.Board;

	

public class WerewolfThread extends Thread{

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
				this.werewolf.prowl();
				Thread.sleep(prowlPeriod);
			}			
		}catch(Exception e){
			System.out.println("error in WerewolfThread");
		}
	}

	
}
