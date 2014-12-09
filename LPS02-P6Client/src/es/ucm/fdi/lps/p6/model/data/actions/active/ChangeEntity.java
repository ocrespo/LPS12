/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import es.ucm.fdi.lps.p6.model.console.Console;
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
public class ChangeEntity implements ActiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Warlock w1 = null;
	protected Warlock w2 = null;
	protected Card tar = null;
	protected TreeCard combat = null;
	@Override 
	public boolean done(Warlock w1){
		(Console.getInstance()).writeLog("<done ChangeEntity");
		if(tar == null)
			return false;
		if(tar.canTargetIntervention()){
			if(this.w1 != w2){
				if(w2.removeInGameCard(tar) == null){
					return false;
				}
				this.w1.addInGame(tar);
				if(combat != null){
					combat.removeCard(tar);
				}
					
				return true;
			}
			return false;
		}
		return false;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("allOk ChangeEntity");
		this.w1 = w1;
		this.w2 = wObj;
		this.combat = combat;
		if(tar != null){
			if(!wObj.foundInGame(tar)){
				return new ErrorMen(ErrorEnum.needEntityTarget);
			}
			this.tar = tar;
			self.setBuff(tar.getName());
			return new ErrorMen(ErrorEnum.allOk);
		}
		return new ErrorMen(ErrorEnum.needTarget);
	}

	@Override
	public void unDone() {
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
