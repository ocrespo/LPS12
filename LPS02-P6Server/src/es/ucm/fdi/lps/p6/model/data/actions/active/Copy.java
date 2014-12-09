/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.ArrayList;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.combat.CombatAction;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.actions.passive.PassiveAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.game.Judge;

/**
 * @author Roni
 *
 */
public class Copy implements ActiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Card self = null;
	private Card tar = null;
	private String description = null;
	private int at = 0;
	private int  def = 0;
	private TreeCard combat = null;
	private Warlock w1 = null;
	private Warlock w2 = null;
	private ArrayList<CombatAction> attAction =null,defAction = null;
	private CombatAction finAction = null;
	private ArrayList<PassiveAction> passAction = null;
	private ActiveAction actAction = this;
	private  String text;
	private String orText = "Copiado a: ";;
	
	public Copy(){	
	}
	@Override
	public boolean done( Warlock w1){
		(Console.getInstance()).writeLog("<done Copy");
		self.setBuff("");
		self.addBuff(text);
		//Card aux = CardFactory.getCard(tar.getCode());
		Card aux = (Card)tar.clone();
		self.setActionAttack(aux.getActionAttack());
		self.setActionDefense(aux.getActionDefense());
		self.setActionFinally(aux.getActionFinally());
		self.setActionPassive(aux.getActionPassive());
		self.setDescription(aux.getDescription()+"\n"+description);
		self.setAttack(tar.getAttack());
		self.setDefense(tar.getDefense());
		self.setLife(tar.getLife());
		(Judge.getInstance()).addUndo(actAction);
		self.setActionActive(aux.getActionActive());
		
		LinkedCard link = null;
		if(combat != null)
			link = this.combat.getCombatDefense(self);
		self.doPassive(link, this.w1);
		self.doDefense(combat, link, w2);
		
		return true;
	}

	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("allOk Copy");
		if(tar == null)
			return new ErrorMen(ErrorEnum.needTarget);
		
		if(combat == null)
			return new ErrorMen(ErrorEnum.needCombat);
		
		if(combat.isEmpty()){
			return new ErrorMen(ErrorEnum.needAttackTarget);
		}
		Card auxTar =  combat.takeAllFisrt().foundCard(tar);
		if(auxTar == null)
			return new ErrorMen(ErrorEnum.needAttackTarget);
		Card auxSelf = combat.getCombat(auxTar).foundCard(self);
		if(auxSelf == null){
			return new ErrorMen(ErrorEnum.needSameCombatTarget);
		}
		
		if(w2.foundInGameCard(tar) == null){
			return new ErrorMen(ErrorEnum.needEntityTarget);
		}
		
		this.tar = auxTar;
		this.self = auxSelf;
		text = orText + tar.getCode(); 
		this.w1 = w1;
		this.w2 = w2;
		this.combat = combat;
		
		this.at = self.getAttack();
		this.def = self.getDefense();
		this.description = self.getDescription();
		this.attAction = self.getActionAttack();
		this.defAction = self.getActionDefense();
		this.finAction = self.getActionFinally();
		this.passAction = self.getActionPassive();
		return new ErrorMen(ErrorEnum.allOk); 
	}

	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("unDone Copy");
		if(self != null){
			self.setActionAttack(attAction);
			self.setActionDefense(defAction);
			self.setActionFinally(finAction);
			self.setActionPassive(passAction);
			self.setDescription(description);
			self.setActionActive(this);
			self.setAttack(at);
			self.setDefense(def);
		}
		tar = null;
	}

}
