/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.combat;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public class OverOneStatsModifier implements CombatAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int at = 0;
	private int def = 0;
	private int acu = 0;
	private Card self = null;
	private String text;
	private String orText; 

	public OverOneStatsModifier(int at,int def){
		this.at = at;
		this.def = def;
		if(def >= 0 && at >= 0){
			orText = "+"+at+"At/+"+def+" ";
		}
		else if(def >= 0){
			orText = at+"At/+"+def+" ";
		}
		else if(at >= 0){
			orText = "+"+at+"At/"+def+" ";
		}
		else{
			orText = +at+"At/"+def+" ";
		}
	}
	@Override
	public ErrorMen done(TreeCard combat,LinkedCard lin, Warlock w2,Card self ) {
		(Console.getInstance()).writeLog("<done OverOneStatsModifier");
		acu++;
		this.self = self;
		
		if(acu >= 3){
			text = this.self.getName() + " : " + orText+" de "+lin.getLastCard().getCode();
			self.addBuff(text);
			self.modStats(at, def);
		}
		
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public void unDone(){
		(Console.getInstance()).writeLog("unDone OverOneStatsModifier");
		while(acu >= 2){
			self.modStats(-at, -def);
			acu--;
		}
		text = "";
		acu = 0;
	}
}
