package es.ucm.fdi.lps.p5.model.data.actions.active;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class DamageAttackStat implements ActiveAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card tar = null;
	private Warlock wObjt = null;
	private TreeCard combat = null;
	
	
	@Override
	public boolean done(Warlock w1) {
		(Console.getInstance()).writeLog("done DamageAttackStat");
		if(!wObjt.foundInGame(tar) || !tar.canTargetIntervention()){
			return false;
		}
		int damage = tar.getAttack();
		tar.loseLife(damage);
		if(tar.isDie() && combat != null){
			combat.removeCard(tar);
		}
		//(Judge.getInstance()).killEntity(tar, wObjt, lin, damage);
		wObjt.dieInGame(tar);
		
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar, Card self, Warlock w1,
			Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk DamageAttackStat");
		if(tar ==  null){
			return new ErrorMen(ErrorEnum.needTarget);
		}
		if(!wObj.foundInGame(tar)){
			return new ErrorMen(ErrorEnum.needEntityTarget);
		}
		this.tar = tar;
		this.wObjt = wObj;
		/*if(combat != null){
			lin = combat.getCombatDefense(tar);
		}*/
		this.combat = combat;
		self.setBuff(tar.getName());
		return new ErrorMen(ErrorEnum.allOk);
	}

	@Override
	public void unDone() {

	}

}
