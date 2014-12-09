/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game;

import java.io.IOException;

import es.ucm.fdi.lps.p6.DAO.DAOError;
import es.ucm.fdi.lps.p6.model.data.FileError;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;

/**
 * @author Roni
 *
 */
public interface InterfazModel{

	/**
	 * @param wTarget the wTarget to set
	 */
	public void setwTarget(Warlock wTarget);
	public void iniModel();
	/**
	 * initialize warlocks
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void iniWarlock() throws IOException, Exception,FileError,DAOError;
	/**
	 * play card
	 * @param w , warlock who have the turn
	 * @param code , the code of the card
	 */
	
	public void playCard(Card c,Card tar);

	/**
	 * use the action of card
	 * @param c the card which use the action
	 * @param tar the target
	 */
	public void useAction(Card c,Card tar);
	/**
	 * do a attack phase
	 * @param c the card who go attack
	 */
	public void attack(Card c);
	/**
	 * assign defenders to combat
	 * @param tar the attacker of the combat
	 * @param c the card who go defense
	 */
	public void defense(Card tar,Card c);
	/**
	 * change phase
	 */
	public void next();
	/**
	 * solve the next intervention
	 */
	public void nextSolveIntervention();
	/**
	 * deal the generic mana
	 * @param e the element choose
	 * @param c the card
	 */
	public void payDeal(Element e,Card c);
	/**
	 * solve the next combat
	 */
	public void nextSolveCombat();
	/**
	 * dea damage between entities in combat
	 * @param c
	 */
	public void dealDamage(Card c);
	/**
	 * pass to deal damage and go to warlock
	 */
	public void passDeal();
	/**
	 * discard a card
	 * @param c the card
	 */
	public void discar(Card c);
	
	/**
	 * save present game
	 * @param dir the file
	 */
	public void saveGame(String dir);
	/**
	 * load the game 
	 * @param dir the file
	 */
	public void loadGame(String dir);

}
