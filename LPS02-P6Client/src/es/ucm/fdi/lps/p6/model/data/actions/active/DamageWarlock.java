package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.Random;

import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class DamageWarlock implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock wObjt;
	private int probability;
	private int value;
	
	public DamageWarlock(int probability,int value){
		this.probability = probability;
		this.value = value;
	}
	@Override
	public boolean done(Warlock w1) {
		Random random = new Random();
		if((Configuration.getInstance()).isDebug()){
			wObjt.loseLife(value);
			return true;
		}
		if(random.nextInt(100) < probability){
			wObjt.loseLife(value);
			return true;
		}
		return false;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2, Warlock wObj, StackCard interventions) {
		this.wObjt = w2;
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {
		// TODO Auto-generated method stub

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
