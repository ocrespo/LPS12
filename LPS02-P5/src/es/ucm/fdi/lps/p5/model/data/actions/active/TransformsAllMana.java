/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.active;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Cost;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public class TransformsAllMana implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cost cost = null;
	private Element element;
	public TransformsAllMana(Cost cost,Element element){
		this.cost = cost;
		this.element = element;
	}
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done TransformsAllMana");
		int num = w1.getNumMana();
		w1.iniMana();
		cost.add(element,num);
		w1.addMana(cost);
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,Warlock w2, Warlock wObj,StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk TransformsAllMana");
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
		
	}

}
