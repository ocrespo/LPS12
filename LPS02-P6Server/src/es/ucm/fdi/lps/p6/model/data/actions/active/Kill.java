package es.ucm.fdi.lps.p6.model.data.actions.active;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class Kill implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Warlock w1 = null;
	protected Warlock w2 = null;
	protected Card tar = null;
	protected TreeCard combat = null;
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("<done Kill");
		if(tar.canTargetIntervention()){
			if(w2.removeInGameCard(tar) == null){
				return false;
			}
			if(combat != null){
				combat.removeCard(tar);
			}
			(Console.getInstance()).write("\r\nHa muerto: \r\n"+tar.toString()+"\r\nAl parecer habia tormenta\r\n");
			this.w1.loseLife(-1);

			return true;
		}
		return false;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,Warlock w2,Warlock wObj,StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk Kill");
		if(tar == null)
			return new ErrorMen(ErrorEnum.needTarget);
		Card auxTar = wObj.foundInGameCard(tar);
		if(auxTar == null){
			return new ErrorMen(ErrorEnum.needEntityTarget);
		}
		this.w2 = wObj;
		this.w1 = w1;
		this.tar = auxTar;
		this.combat = combat;
		self.setBuff(tar.getName());
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {

	}

}
