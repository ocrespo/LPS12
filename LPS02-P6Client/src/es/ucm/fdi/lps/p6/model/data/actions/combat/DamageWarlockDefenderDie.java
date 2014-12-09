package es.ucm.fdi.lps.p6.model.data.actions.combat;

import java.util.LinkedList;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class DamageWarlockDefenderDie implements CombatAction ,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean done = false;
	private LinkedList<Card> lastLink = null;
	private LinkedList<Card> actuLink = null;
	private Warlock w2 = null;
	private int value ;
	
	public DamageWarlockDefenderDie(int value){
		this.value = value;
	}
	@Override
	public ErrorMen done(TreeCard combat, LinkedCard lin, Warlock w2, Card self) {
		(Console.getInstance()).writeLog("<done DamageWarlockDefenderDie");
		if(lin == null)
			return new ErrorMen(ErrorEnum.needCombat);
		Card c = lin.getLastCard();
		if(c == self){
			return new ErrorMen(ErrorEnum.allOk);
		}
		if(actuLink == null)
			actuLink = lin.getValues();
		if(lastLink == null){
			lastLink = new LinkedList<Card>();
		}
		lastLink.add(c);
		this.w2 = w2;
		done = true;
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone DamageWarlockDefenderDie");
		if(done){
			Card aux = actuLink.removeFirst();
			int actNum = actuLink.size();		
			int lastNum = lastLink.size();
			if(actNum < lastNum){
				for(Card c : lastLink){
					if(!actuLink.remove(c)){
						w2.removeGraveyard(c);
						(Console.getInstance()).write("El brujo "+w2.getName()+" ha perdido "+value+" de vida");
						w2.loseLife(value);
					}
				}
			}
			actuLink.addFirst(aux);
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
