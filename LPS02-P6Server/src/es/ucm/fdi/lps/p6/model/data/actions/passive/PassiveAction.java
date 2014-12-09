package es.ucm.fdi.lps.p6.model.data.actions.passive;

import java.io.Serializable;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
/**
 * 
 * @author Roni
 *
 */
public interface PassiveAction extends Serializable  {
	/**
	 * do passive action
	 * @param lin the info about combat or a single target card
	 * @param self self card who use action
	 * @return if success the action
	 */
	public ErrorMen done(LinkedCard lin,Card self,Warlock w1);
	/**
	 * undo the passive action
	 */
	public void unDone();
}
