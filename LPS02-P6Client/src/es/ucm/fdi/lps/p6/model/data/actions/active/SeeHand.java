package es.ucm.fdi.lps.p6.model.data.actions.active;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.game.Judge;

/**
 * 
 * @author Roni
 *
 */

public class SeeHand implements ActiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock w = null;
	@Override
	public boolean done(Warlock w) {
		(Console.getInstance()).writeLog("<done SeeHand");
		(Console.getInstance()).write("La mano de su enemido es\r\n"+this.w.handToString()+"\r\n\r\npulse una tecla para continuar");
		(Console.getInstance()).read();
		(Judge.getInstance()).notifyShowEnemyHand(this.w);
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk SeeHand");
		this.w = w2;
		return new ErrorMen(ErrorEnum.allOk); 
	}

	@Override
	public void unDone() {		
	}

	/**
	 * clone
	 */
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
        }
        return obj;
    }

}
