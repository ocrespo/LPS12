package es.ucm.fdi.lps.p6.model.data.actions.mixed;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.combat.CombatAction;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.actions.permanent.PermanentAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class OnlyDefenseMe implements CombatAction, PermanentAction  ,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card self = null;
	@Override
	public ErrorMen doPermanent(Card tar) {
		(Console.getInstance()).writeLog("<doPermanent OnlyDefenseMe");
		if(tar != self)
			return new ErrorMen(ErrorEnum.needDefenseOther);
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDoPermanent() {
	}

	@Override
	public ErrorMen done(TreeCard combat,LinkedCard lin, Warlock w2, Card self) {
		(Console.getInstance()).writeLog("done OnlyDefenseMe");
		this.self = self;
		combat.setActionPermanent(this);
		return new ErrorMen(ErrorEnum.goToCombatPermanent);
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
