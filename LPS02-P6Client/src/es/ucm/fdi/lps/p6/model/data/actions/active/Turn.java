package es.ucm.fdi.lps.p6.model.data.actions.active;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

/**
 * 
 * @author Roni
 *
 */

public class Turn implements ActiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Card tar = null;
	private  String text;
	private String orText ="Descargada" ;
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done Turn");
		if(tar != null && tar.canTargetIntervention()){
			if(!tar.canUse()){
				return false;
			}
			tar.addBuff(text);
			tar.turnOn();
			
			return true;
		}
		return false;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk Turn");
		if(tar == null)
			return new ErrorMen(ErrorEnum.needTarget);
		if(!wObj.foundInGame(tar)){
			return new ErrorMen(ErrorEnum.needEntityTarget);
		}
		this.tar = tar;
		text = self.getName() + " : " + orText;
		self.setBuff(tar.getName());
		return new ErrorMen(ErrorEnum.allOk);
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
