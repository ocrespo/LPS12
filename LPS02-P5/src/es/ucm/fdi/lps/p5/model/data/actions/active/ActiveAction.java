/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.active;

import java.io.Serializable;

import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public interface ActiveAction extends Serializable {
	/**
	 * do the active action
	 * @param w1 warlock
	 * @return if success or not
	 */
	public boolean done( Warlock w1);
	/**
	 * 
	 * @param combat info about combat
	 * @param tar target 
	 * @param self self card who have the action
	 * @param w1 warlock who launch action
	 * @param w2 warlock enemy
	 * @param wObj warlock target
	 * @param interventions the stack of interventions
	 * @return if there are all necessary arguments
	 */
	public ErrorMen allOk(TreeCard combat,Card tar ,Card self, Warlock w1,Warlock w2,Warlock wObj, StackCard interventions);
	/**
	 * un do the action
	 */
	public void unDone();
}
