/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.active;

import java.util.Iterator;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Cost;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;


/**
 * @author Roni
 *
 */
public class AddMana implements ActiveAction   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cost cost = null;
	private boolean allEquals;
	private Element selfElement;
	public AddMana(Cost cost,boolean allEquals,Element selfElement){
		this.cost = cost;
		this.allEquals = allEquals;
		this.selfElement = selfElement;
	}
	@Override
	public boolean done( Warlock w1) {
		(Console.getInstance()).writeLog("done AddMana");
		if(!allEquals){
			w1.addMana(cost);
		}
		else{
			Iterator<LinkedCard> it =  w1.getAllEnviroment();
			boolean ok = true;
			LinkedCard aux = null;
			int cont = 0;
			while(it.hasNext() && ok){
				aux = it.next();
				if(aux.getCard().getElement() != selfElement){
					ok = false;
				}
				cont += aux.size();
			}
			if(ok){
				cost.add(selfElement, cont-1);
				w1.addMana(cost);
			}
			else
				w1.addMana(cost);
		}
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj,StackCard interventions) {
		(Console.getInstance()).writeLog("allOk AddMana");
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {		
	}

}
