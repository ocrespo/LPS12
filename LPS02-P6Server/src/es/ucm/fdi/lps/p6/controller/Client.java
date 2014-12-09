/**
 * 
 */
package es.ucm.fdi.lps.p6.controller;

import java.io.IOException;
import java.io.ObjectInputStream;

import es.ucm.fdi.lps.p6.model.game.Model;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection.Actions;

/**
 * @author Roni
 *
 */
public class Client implements Runnable {
	private ObjectInputStream inObject ;//= new ObjectInputStream (in);
	private Model game;
	private int w;
	private boolean stop = false;
	public Client(ObjectInputStream inObject,Model game,int w){
		this.inObject = inObject;
		this.game = game;
		this.w = w;
	}
	public void stop(){
		stop = true;
	}
	@Override
	public void run() {
		TransferObjectConection trans = null;
		while(!stop){
			try {
				trans = (TransferObjectConection) inObject.readObject();
				if(game.canDo(w) || trans.getAction() == Actions.nextCombat || trans.getAction() == Actions.nextSolveIntervention
						|| trans.getAction() == Actions.finish || trans.getAction() == Actions.sendText){
					game.doneActionClient(trans,w);
				}
				else{
					game.notifyErrorCard("<html>Tienes que tener el turno para poder hacer eso", w);
				}
				//trans.done(game);
			} catch (ClassNotFoundException e) {
			} catch (IOException e) {
				game.finish();
				//e.printStackTrace();
			}
		}
		
	}
}
