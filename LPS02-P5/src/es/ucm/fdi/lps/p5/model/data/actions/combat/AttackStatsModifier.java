package es.ucm.fdi.lps.p5.model.data.actions.combat;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class AttackStatsModifier implements CombatAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Element elem;
	private int att;
	private int def;
	private int count = 0;
	private boolean canAcu;
	private boolean done = false;
	private Card self = null;
	private boolean isPlus = false;
	private String text ;
	private String orText; 
	private int auxAt = 0;
	private int auxDef = 0;
	
	public AttackStatsModifier(Element elem,boolean isPlus,int at, int def,boolean canAcu){
		this.elem = elem;
		this.att = at;
		this.def = def;
		this.canAcu = canAcu;
		this.isPlus = isPlus;
		if(def >= 0 && at >= 0){
			orText = "+"+at+"At/+"+def+"Def";
		}
		else if(def >= 0){
			orText = at+"At/+"+def+"Def";
		}
		else if(at >= 0){
			orText = "+"+at+"At/"+def+"Def";
		}
		else{
			orText = +at+"At/"+def+"Def";
		}
		if(canAcu){
			orText += " acumulable";
		}
		//text = "*"+att+"At/*"+def+"Def";
	}
	@Override
	public ErrorMen done(TreeCard combat, LinkedCard lin, Warlock w2, Card self) {
		(Console.getInstance()).writeLog("<done AttackStatsModifier");
		if(done && !canAcu || lin == null)
			return new ErrorMen(ErrorEnum.allOk);
		if(this.self == null)
			this.self = self;
		Card last = lin.getLastCard();
		text = this.self.getName() + " : " + orText;
		boolean ok = false;
		if(last != self && isPlus && last.getElement() == elem){
			ok = true;
		}
		else if(last != self && !isPlus && last.getElement() != elem){
			ok = true;
		}
		if(ok){
			self.addBuff(text);
			
			if(-att > self.getAttack() && att < 0){
				auxAt += -att - self.getAttack();
			}
			if(-def > self.getDefense() && def < 0){
				auxDef += -def - self.getDefense();
			}
			
			self.modStats(att, def);
			done = true;
			count++;
		}
		return new ErrorMen(ErrorEnum.allOk);	
		
	}
	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone AttackStatsModifier");
		if(done)
			self.modStats((-att*count)-auxAt, (-def*count)-auxDef);
		done = false;
		auxAt = 0;
		auxDef = 0;
			
	}

}
