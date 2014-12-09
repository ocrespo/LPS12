package es.ucm.fdi.lps.p5.model.data.actions.active;

import java.util.ArrayList;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Configuration;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class RotateAllEntities implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock w1 = null;
	private Warlock w2 = null;
	private TreeCard combat = null;
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done RotateAllEntities");
		rotateAll(this.w1);
		rotateAll(w2);
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk RotateAllEntities");
		this.w1 = w1;
		this.w2 = w2;
		this.combat = combat;
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {

	}
	/**
	 * do the rotate
	 * @param w the warlock
	 */
	private void rotateAll(Warlock w){
		(Console.getInstance()).writeLog("<rotateAll RotateAllEntities");
		ArrayList<LinkedCard> hand =  w.getEntitiesIntoHand();
		ArrayList<Card> deck = w.getEntitiesIntoDeck();
		ArrayList<Card> grave = w.getEntitiesIntoGraveyard();
		ArrayList<Card> inGame = w.getInGameCardsByCopy();

		for(Card c : inGame){
			if(!c.getCode().equals("odin")){
				if(combat != null){
					combat.removeCard(c);
				}
				w.removeInGameCard(c);	
				w.addGraveyard(c);
			}
		}
		
		for(Card c : grave){
			w.addInDeck(c);
			w.removeGraveyard(c);
			c.ini();
		}
		
		for(LinkedCard link : hand){
			w.addAllCardsInGame(link);
			w.removeAllCardsInHand(link.getCard().getCode());
		}
		for(Card c : deck){
			w.addInHand(c);
			w.removeDeck(c);
		}
		
		
		if(!(Configuration.getInstance()).isDebug()){
			w.fillDeck();
			w.fillGraveyard();
		}
	}

}
