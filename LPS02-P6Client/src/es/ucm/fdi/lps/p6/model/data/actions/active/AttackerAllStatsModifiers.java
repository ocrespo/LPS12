/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.LinkedList;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public class AttackerAllStatsModifiers implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedCard lin = null;
	protected TreeCard combat = null;
	private int at = 0;
	private int def = 0;
	private String orText;
	private  String text;
	private LinkedList<Integer> auxAt;
	private LinkedList<Integer> auxDef;
	
	public AttackerAllStatsModifiers(int at,int def){
		this.at = at;
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
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("done AttackerAllStatsModifiers");
		if(combat == null)
			return false;
		lin = combat.takeAllFisrt();
		if(lin == null || lin.isEmpty()){
			return false;
		}
		int max = lin.size();
		Card c = null;
		auxAt = new LinkedList<Integer>();
		auxDef = new LinkedList<Integer>();
		for(int i=0;i<max;i++){
			c = lin.getCard(i);
			c.addBuff(text);
			if(-at > c.getAttack() && at < 0){
				auxAt.add(-at - c.getAttack());
			}
			else{
				auxAt.add(0);
			}
			if(-def > c.getDefense() && def < 0){
				auxDef.add(-def - c.getDefense());
			}
			else{
				auxDef.add(0);
			}
			c.modStats(at, def);
			
		}
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("allOk AttackerAllStatsModifiers");
		if(combat == null){
			return new ErrorMen(ErrorEnum.needCombat);
		}
		lin = combat.takeAllFisrt();
		if(lin == null || lin.isEmpty()){
			return new ErrorMen(ErrorEnum.needAttackerChoose);
		}
		this.combat = combat;
		lin = null;
		text = self.getName() + " : " + orText;
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone AttackerAllStatsModifiers");
		while(lin != null && !lin.isEmpty()){
			lin.removeTop().modStats(-at - auxAt.remove(), -def - auxDef.remove());
		}
		auxAt = null;
		auxDef = null;
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
