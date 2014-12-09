package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.ArrayList;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class ChangeAllEntityElements implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Warlock w1 = null;
	protected Warlock w2 =  null;
	private ArrayList<Element> elems =  null;
	private TreeCard combat = null;
	
	
	public ChangeAllEntityElements(ArrayList<Element> elems){
		this.elems =  elems;
	}
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done ChangeAllEntityElements");
		ArrayList<Card> cards = new ArrayList<Card>();
		for(Element elem : elems){
			cards.addAll(w2.getInGameCardElement(elem));
		}
		if(cards.isEmpty()){
			return false;
		}
		for(Card c : cards){
			if(combat != null){
				combat.removeCard(c);
			}
			w2.removeInGameCard(c);
			
			this.w1.addInGame(c);
		}
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("allOk ChangeAllEntityElements");
		this.w1 = w1;
		this.w2 =  w2;
		this.combat = combat;
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
	}

}
