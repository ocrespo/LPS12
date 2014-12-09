/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.combat;

import java.io.Serializable;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public interface CombatAction extends Serializable  {
	/**
	 * Do the combat action
	 * @param lin info about combat
	 * @param w2 warlock enemy
	 * @param self self card who use action
	 * @return if success
	 */
	public ErrorMen done(TreeCard combat,LinkedCard lin, Warlock w2,Card self );
	/**
	 * undo de combat action
	 */
	void unDone();
}
