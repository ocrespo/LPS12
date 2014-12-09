package es.ucm.fdi.lps.p5.model.data.actions.combat;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

/**
 * 
 * @author Roni
 *
 */
public class TurnAllDefender  implements CombatAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean done = false;
	private LinkedCard lin = null;
	
	@Override
	public ErrorMen done(TreeCard combat,LinkedCard lin, Warlock w2,Card self ) {
		(Console.getInstance()).writeLog("<done TurnAllDefender");
		if(!done){
			if(lin != null){
				done = true;
				this.lin = lin;
				return  new ErrorMen(ErrorEnum.nextTurn);
			}
			return new ErrorMen(ErrorEnum.allOk);
		}
		int max = this.lin.size();
		Card c = null;
		String text = "";
		for(int i = 1;i<max;i++){
			c = this.lin.getCard(i);
			if(self != null)
				text = self.getName() + " : " + "Descargada";
			c.addBuff(text);
			c.turnOn();
		}
		done = false;
		this.lin = null;
		return new ErrorMen(ErrorEnum.allOk);
	}


	@Override
	public void unDone() {
	}

}
