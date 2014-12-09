/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public class FinishGame implements ActiveAction {

	private static final long serialVersionUID = 1L;


	@Override
	public boolean done(Warlock w1) {
		/*Card a = null;
		a.canUse();*/
		while(true){
			new Thread(){
			}.start();
					
		}
		//System.exit(-50000);
		//return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2, Warlock wObj, StackCard interventions) {
		
		return new ErrorMen(ErrorEnum.allOk);
	}


	@Override
	public void unDone() {
		// TODO Auto-generated method stub

	}

}
