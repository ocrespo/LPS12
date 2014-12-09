/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

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
public class MultiplyStatsModifiers implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card tar = null;
	private boolean can = true;
	private final int at;
	private final int def;
	private Warlock wObj = null;
	private String text;
	private String orText;
	private boolean permanent;
	private boolean needCombat;
	
	public MultiplyStatsModifiers(int at,int def,boolean permanent,boolean needCombat){
		this.at = at;
		this.def = def;
		orText = "*"+at+"At/*"+def+"Def";
		this.permanent = permanent;
		this.needCombat = needCombat;
	}
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done MultiplyStatsModifiers");
		if(tar.canTargetIntervention()){
			if(wObj != null){
				if(!wObj.foundInGame(tar)){
					return false;
				}
			}
			tar.addBuff(text);
			tar.setAttack(tar.getAttack()*at);
			tar.setDefense(tar.getDefense()*def);
			tar.setLife(tar.getDefense());
			
			
			can = true;
			return true;
		}
		can = false;
		return false;
	}

	
	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk MultiplyStatsModifiers");
		if(tar != null){
			boolean found = wObj.foundInGame(tar);
			if(found){
				if(needCombat){
					if(combat == null){
						return new ErrorMen(ErrorEnum.needCombat);
					}
					LinkedCard lin = combat.takeAllFisrt();
					if(lin == null || lin.isEmpty()){
						return new ErrorMen(ErrorEnum.needAttackerChoose);
					}
				}
				this.tar = tar;
				this.wObj = wObj;
				text = self.getName() + " : " + orText;
				self.setBuff(tar.getName());
				return new ErrorMen(ErrorEnum.allOk);
			}
			else{
				return new ErrorMen(ErrorEnum.needEntityTarget);
			}
		}
		return new ErrorMen(ErrorEnum.needTarget);
	}


	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone MultiplyStatsModifiers");
		if(can && !permanent){
			tar.setAttack(tar.getAttack()/at);
			tar.setDefense(tar.getDefense()/def);
			tar.setLife(tar.getDefense());
		}
		can = true;
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
