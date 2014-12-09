/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.Random;
import java.util.StringTokenizer;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Configuration;
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
public class StealHand implements ActiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock w1 = null;
	private Warlock w2 = null;
	private int num;
	
	public StealHand(int num){
		this.num = num;
	}
	@Override
	public boolean done(Warlock w){		
		(Console.getInstance()).writeLog("<done StealHand");
		if((Configuration.getInstance()).isConsole()  && (Configuration.getInstance()).isDebug()){
			(Console.getInstance()).write(w2.handToString());
			(Console.getInstance()).write("(modo debug)Que dos cartas quiere coger:");
		 	StringTokenizer tok = new StringTokenizer((Console.getInstance()).read());
		 	Card c = w2.discard(Integer.parseInt(tok.nextToken()));
		 	w1.addInHand(c);
		 	c = w2.discard(Integer.parseInt(tok.nextToken()));
		 	w1.addInHand(c);
		 	return true;
		}
		Random random = new Random();
		Card c = null;
		boolean exit = false;
		int i = 0;
		while(!exit && i<num){
			c  = w2.discard(random.nextInt(w2.getNumHandEquals()));
			if(c != null){
				w1.addInHand(c);
			}
			else{
				exit = true;
			}
			i++;
		}
		return !exit;
		/*
		Card c  = w2.discard(random.nextInt(w2.getNumHandEquals()));
		if(c != null){
			w1.addInHand(c);
			c = w2.discard(random.nextInt(w2.getNumHandEquals()));
			if(c != null)
				w1.addInHand(c);
			return true;
		}
		return false;*/
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2, Warlock wObj,StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk StealHand");
		this.w1 =w1;
		this.w2 = w2;
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {		
	}

}
