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
public class UpCard implements CombatAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	public ErrorMen done(TreeCard combat,LinkedCard lin,Warlock w2,Card self ) {
		(Console.getInstance()).writeLog("<done UpCard");
		if(lin == null){
			return new ErrorMen(ErrorEnum.allOk);
		}
		Card c = lin.getCard();
		if(!c.isDie() && c != self){
			c.ini();
			w2.upInGame(c);
		}
		return new ErrorMen(ErrorEnum.allOk);
	}


	@Override
	public void unDone() {		
	}
	


}
