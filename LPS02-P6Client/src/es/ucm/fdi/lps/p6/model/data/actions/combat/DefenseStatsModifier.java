package es.ucm.fdi.lps.p6.model.data.actions.combat;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class DefenseStatsModifier implements CombatAction ,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Element elem;
	private int att;
	private int def;
	private int count = 0;
	private boolean done = false;
	private Card self = null;
	private String text ;
	private String orText; 
	private int auxAt = 0;
	private int auxDef = 0;
	
	public DefenseStatsModifier(Element elem,int at, int def){
		this.elem = elem;
		this.att = at;
		this.def = def;
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
	}
	@Override
	public ErrorMen done(TreeCard combat, LinkedCard lin, Warlock w2, Card self) {
		(Console.getInstance()).writeLog("<done DefenseStatsModifier");
		if(done || lin == null)
			return new ErrorMen(ErrorEnum.allOk);
		if(this.self == null)
			this.self = self;
		
		text = this.self.getName() + " : " + orText;
		if(lin.getCard().getElement() == elem){
			self.addBuff(text);
			
			if(-att > self.getAttack() && att < 0){
				auxAt += -att - self.getAttack();
			}
			if(-def > self.getDefense() && def < 0){
				auxDef += -def - self.getDefense();
			}
			
			self.modStats(att, def);
			count++;
			done = true;
		}
		
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone DefenseStatsModifier");
		if(done)
			self.modStats((-att*count)-auxAt, (-def*count)-auxDef);
		done = false;
		auxAt = 0;
		auxDef = 0;
			
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
