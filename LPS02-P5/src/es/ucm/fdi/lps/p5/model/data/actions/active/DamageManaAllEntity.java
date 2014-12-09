package es.ucm.fdi.lps.p5.model.data.actions.active;

import java.util.ArrayList;
import java.util.Collection;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class DamageManaAllEntity implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock w1 = null;
	private Warlock w2 = null;
	private Collection<LinkedCard> allCard ;
	private int damage = 0;
	private TreeCard combat = null;
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done DamageManaAllEntity");
		damage = this.w1.getNumEnviromentTurn();
		if(damage == 0)
			return false;
		allCard = w2.getInGameCards();
		//boolean value = w2.modStatsAllGameCard(0, damage,true);
		boolean value = false;
		ArrayList<Card> aux = new ArrayList<Card>();
		if(combat != null){
			for(LinkedCard lin : allCard){
				for(Card c : lin.getValues()){
					c.loseLife(damage);
					if(c.isDie()){
						value = true;
						combat.removeCard(c);
						//w2.dieInGame(c);
						aux.add(c);
					}
				}
			}
		}
		for(Card c : aux){
			w2.dieInGame(c);
		}
		
		
		allCard = null;
		return value;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2,Warlock wObj,StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk DamageManaAllEntity");
		this.w2 = w2;
		this.w1 = w1;
		this.combat = combat;
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone DamageManaAllEntity");
		/*if(allCard != null){
			for(Card c : allCard){
				c.modStats(0, damage);
			}
		}*/
	}

}
