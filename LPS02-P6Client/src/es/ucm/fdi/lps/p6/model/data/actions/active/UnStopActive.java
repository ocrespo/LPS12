/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.ArrayList;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.actions.passive.PassiveAction;
import es.ucm.fdi.lps.p6.model.data.actions.passive.UnStop;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public class UnStopActive implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TreeCard combat = null;
	private Card tar = null;
	private Warlock wObj = null;
	private boolean done = false;
	private String text ="Imparable";
	
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done UnStopActive");
		if(wObj != null){
			if(!wObj.foundInGame(tar)){
				return false;
			}
		}
		if(tar == null || !tar.canTargetIntervention()){
			return false;
		}
		ArrayList<PassiveAction> pas = tar.getActionPassive();
		if(pas == null){
			pas = new ArrayList<PassiveAction>();
			tar.setActionPassive(pas);
		}
		pas.add(new UnStop());
		tar.addBuff(text);
		if(combat != null){
			combat.removeDefenders(tar);
		}
		done = true;
		
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk UnStopActive");
		this.combat = combat;
		if(tar == null)
			return new ErrorMen(ErrorEnum.needTarget);
		if(!wObj.foundInGame(tar)){
			return new ErrorMen(ErrorEnum.needEntityTarget);
		}
		this.tar = tar;
		this.wObj = wObj;
		text = self.getName() + " : " + text;
		self.setBuff(tar.getName());
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("<unDone UnStopActive");
		if(done){
			boolean can = true;
			ArrayList<PassiveAction> er = tar.getActionPassive();
			int count = 0;
			int max = er.size();
			while(can && er!=null && count<max){
				can = er.get(count).done(null,null,null).canDefense();
				count++;
			}
			if(!can){
				er.remove(count-1);
				if(er.isEmpty())
					tar.setActionPassive(null);
			}
		}
		done = false;
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
