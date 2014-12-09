package es.ucm.fdi.lps.p5.model.data.actions.active;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;
import es.ucm.fdi.lps.p5.model.game.Judge;

public class Counter implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Card  tar = null;
	protected StackCard interventions = null;
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("done Counter");
		if(interventions == null || !interventions.removeCard(tar))
			return false;
		
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRemoveIntervention(tar);
		return true;
		
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk Counter");
		if(interventions == null || tar == null)
			return new ErrorMen(ErrorEnum.needTarget);
		if(!interventions.found(tar)){
			return new ErrorMen(ErrorEnum.needInterventionTarget);
		}
		
		this.tar = tar;
		this.interventions = interventions;
		self.setBuff(tar.getName());
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {

	}

}
