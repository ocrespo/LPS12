/**
 * 
 */
package es.ucm.fdi.lps.p6.controller;

/**
 * @author Roni
 *
 */
public interface ObserverNet{

	public void updatePlayerLobby(String nameA, String dirA, String nameB, String dirB);

	public void updateStart();

	public void updateError(String string);
	
}
