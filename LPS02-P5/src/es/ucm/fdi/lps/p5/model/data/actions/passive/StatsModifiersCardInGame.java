package es.ucm.fdi.lps.p5.model.data.actions.passive;

import java.util.Iterator;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;

public class StatsModifiersCardInGame implements PassiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int at = 0;
	private int def = 0;
	private boolean done = false;
	private String code;
	private Card self = null;
	private String text;
	
	
	public StatsModifiersCardInGame(int at,int def,String code){
		this.at = at;
		this.def = def;
		this.code = code;
		text =  "+"+at+"At/+"+def+"Def por "+code;
	}
	@Override
	public ErrorMen done(LinkedCard lin, Card self,Warlock w1) {
		(Console.getInstance()).writeLog("done StatsModifiersCardInGame");
		if(done || w1 == null)
			return new ErrorMen(ErrorEnum.allOk);
		this.self = self;
		
		Iterator<LinkedCard> it = w1.getInGameCards().iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			if(it.next().getCard().getCode().equals(code))
				found = true;
		}
		if(found){
			self.addBuff(text);
			self.modStats(at, def);
			done = true;
		}
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone StatsModifiersCardInGame");
		if(done){
			self.modStats(-at, -def);
		}
		done = false;

	}

}
