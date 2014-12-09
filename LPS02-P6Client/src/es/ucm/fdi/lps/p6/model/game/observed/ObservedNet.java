/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game.observed;

import java.util.ArrayList;

import es.ucm.fdi.lps.p6.controller.ObserverNet;

/**
 * @author Roni
 *
 */
public class ObservedNet {

	private transient ArrayList<ObserverNet> observers = new ArrayList<ObserverNet>();
	private boolean changed = false;
	
	/**
	 * add new observer
	 * @param o the observer
	 */
	public void add(ObserverNet o){
		observers.add(o);
	}
	/**
	 * add all observers into collection
	 * @param o arraylist which observers
	 */
	public void add(ArrayList<ObserverNet> o){
		observers.addAll(o);
	}
	/**
	 * remove a observer
	 * @param o the observer
	 */
	public void remove(ObserverNet o){
		observers.remove(o);
	}
	/**
	 * changed the state of changed to true
	 */
	public void setChanged(){
		changed = true;
	}
	/**
	 * 
	 * @return all observers
	 */
	public ArrayList<ObserverNet> getObservers(){
		return observers;
	}
	
	public void notifyPlayerLobby(String nameA,String dirA, String nameB, String dirB){
		for(ObserverNet o : observers){
			o.updatePlayerLobby(nameA,dirA,nameB,dirB) ;
		}
	}
	public void notifyStart(){
		for(ObserverNet o : observers){
			o.updateStart() ;
		}
	}
	public void notifyError(String string) {
		for(ObserverNet o : observers){
			o.updateError(string) ;
		}
		
	}
}
