/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.passive;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;

/**
 * @author Roni
 *
 */
public class MultiplyDamage implements PassiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean done = false;
	private Card self = null;
	private int at = 1;
	private int def = 1;
	public MultiplyDamage(int at, int def){
		this.at = at;
		this.def = def;
	}
	@Override
	public ErrorMen done(LinkedCard lin,Card self,Warlock w1) {		
		(Console.getInstance()).writeLog("done MultiplyDamage");
		if(lin != null && lin.size() == 1 && lin.getCard() == self && !done){
			this.self = self;
			int auxAt = this.self.getAttack()*at;
			int auxDef = this.self.getDefense()*def;
			this.self.setAttack(auxAt);
			this.self.setDefense(auxDef);
			this.self.setLife(auxDef);
			done = true;
		}
		
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone MultiplyDamage");
		if(done){
			int auxAt = self.getAttack()/at;
			int auxDef = self.getDefense()/def;
			self.setAttack(auxAt);
			self.setDefense(auxDef);
			self.setLife(auxDef);
		}
		done = false;
	}

}
