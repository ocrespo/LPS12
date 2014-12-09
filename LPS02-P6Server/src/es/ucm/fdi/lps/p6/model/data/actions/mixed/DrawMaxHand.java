package es.ucm.fdi.lps.p6.model.data.actions.mixed;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.actions.permanent.PermanentAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class DrawMaxHand implements ActiveAction, PermanentAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock wObj = null;
	private  String text = "Siempre robara cartas hasta tener el máximo en mano";
	@Override
	public ErrorMen doPermanent(Card tar) {
		(Console.getInstance()).writeLog("<doPermanent DrawMaxHand");
		boolean can = wObj.drawToMaxHand();
		
		if(can){
			return new ErrorMen(ErrorEnum.allOk);
		}
		return new ErrorMen(ErrorEnum.cantDrawCardDeck);
	}

	@Override
	public void unDoPermanent() {
		

	}

	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done DrawMaxHand");
		this.wObj.setActionPermanent(this);
		this.wObj.addBuff(text);
		
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk DrawMaxHand");
		if(wObj == null  ){
			return new ErrorMen(ErrorEnum.needWarlockTarget);
		}
		if(wObj != null && tar != null){
			return new ErrorMen(ErrorEnum.onlyTargetWarlock);
		}
		this.wObj = wObj;
		text = self.getName() + " : " + text;
		self.setBuff(wObj.getName());
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {

	}

}
