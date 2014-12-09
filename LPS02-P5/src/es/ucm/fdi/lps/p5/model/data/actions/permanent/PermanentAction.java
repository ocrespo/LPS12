/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.permanent;

import java.io.Serializable;

import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.cards.Card;

/**
 * @author Roni
 *
 */
public interface PermanentAction extends Serializable  {
	/**
	 * do a permanent action
	 * @return the error about execute
	 */
	public ErrorMen doPermanent(Card tar);
	/**
	 * undo a permanent action if its necessary
	 */
	public void unDoPermanent();

}
