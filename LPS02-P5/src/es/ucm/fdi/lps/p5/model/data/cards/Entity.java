/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.cards;

import java.util.ArrayList;
import java.util.Iterator;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Cost;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p5.model.data.actions.combat.CombatAction;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.actions.passive.PassiveAction;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;
import es.ucm.fdi.lps.p5.model.game.Judge;



/**
 * @author Roni
 *
 */
public class Entity extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int attack = 0;
	private int defense = 0;
	private int life = 0;
	private boolean turn = false;
	private Cost cost = null;
	
	private ArrayList<CombatAction> actionAttack = null;
	private ArrayList<CombatAction> actionDefense = null;
	private CombatAction actionFinally = null;
	private ActiveAction actionActive = null;

	private ArrayList<PassiveAction> actionPassive = null;
	
	public Entity(String code, String name,Element element ,int attack,int defense,ArrayList<CombatAction> actionAttack,ArrayList<CombatAction> actionDefense,CombatAction actionFinally,ActiveAction actionActive,ArrayList<PassiveAction> actionPassive,
			Cost cost,int max,String description,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.attack = attack;
		this.defense = defense;
		this.actionAttack = actionAttack;
		this.actionDefense = actionDefense;
		this.actionFinally = actionFinally;
		this.actionActive = actionActive;
		this.actionPassive = actionPassive;
		this.cost = cost;
		this.life = defense;
	}
	public Entity(String code, String name,Element element  ,int attack,int defense,Cost cost,int max,String description,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.attack = attack;
		this.defense = defense;
		this.life = defense;
		this.cost = cost;
	}
	public Entity(String code, String name,Element element ,int attack,int defense,ArrayList<PassiveAction> actionPassive,Cost cost
			,int max,String description,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.attack = attack;
		this.defense = defense;
		this.life = defense;
		this.cost = cost;
		this.actionPassive = actionPassive;
	}
	public Entity(String code, String name,Element element  ,int attack,int defense,ActiveAction actionActive,Cost cost,int max,String description
			,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.attack = attack;
		this.defense = defense;
		this.life = defense;
		this.cost = cost;
		this.actionActive = actionActive;
	}
	public Entity(String code, String name,Element element,int attack,int defense,ArrayList<CombatAction> actionAttack,ArrayList<CombatAction> actionDefense,CombatAction actionFinally,Cost cost
			,int max,String description,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.attack = attack;
		this.defense = defense;
		this.life = defense;
		this.cost = cost;
		this.actionAttack = actionAttack;
		this.actionDefense = actionDefense;
		this.actionFinally = actionFinally;
	}
	public Entity(String code,String name,Element element,int max,String description,String dirImg){
		super(code, name, element, max, description, dirImg);
	}
	@Override
	public int getAttack() {
		return attack;
	}
	@Override
	public void setAttack(int at) {
		this.attack = at;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	/**
	 * @return the defense
	 */
	@Override
	public int getDefense() {
		return defense;
	}
	/**
	 * @param def the defense to set
	 */
	@Override
	public void setDefense(int def) {
		this.defense = def;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	/**
	 * @return the turn
	 */
	@Override
	public boolean canUse() {
		return !turn;
	}
	/**
	 * @return the life
	 */
	@Override
	public int getLife() {
		return life;
	}
	/**
	 * @param life the life to set
	 */
	@Override
	public void setLife(int life) {
		this.life = life;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	/**
	 * @return the cost
	 */
	@Override
	public Cost getCost() {
		return cost;
	}
	
	@Override
	public String toString(){
		String aux = "";
		if(turn)
			aux = "Si";
		else
			aux="No";
		String type = "\r\nCoste: "+cost.toString()+"\r\nA: "+attack+"  D: "+defense+"    Vida: "+life+"  Descargado: "+aux;
		
		String elem = null;
		if(element == Element.earth)
			elem = "tierra";
		else if(element == Element.sea)
			elem = "mar";
		else if(element == Element.air)
			elem = "aire";
		else if(element == Element.spirit)
			elem = "espiritu";
			
		return type+"\r\nCodigo: "+code+"\r\nNombre: "+name+"\r\nElemento: "+elem+"\r\nTipo: Ser\r\nDescripcion: "+description+type;
	}
	@Override
	public void ini(){
		(Console.getInstance()).writeLog("<ini entity");
		turn = false;
		unDone();
		(Console.getInstance()).writeLog("ini entity/>");
		buff = "";
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	public void iniBuff(){
		buff = "";
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	@Override
	public void doAttack(TreeCard combat,LinkedCard lin,Warlock w2){
		(Console.getInstance()).writeLog("<doAttack entity");
		if(actionAttack != null){	
			for(CombatAction action : actionAttack){
				action.done(combat,lin,w2,this);
			}
		}
		(Console.getInstance()).writeLog("<doAttack entity/>");
	}
	@Override
	public void doDefense(TreeCard combat,LinkedCard c,Warlock w2){
		(Console.getInstance()).writeLog("<doDefense entity");
		if(actionDefense != null)
			for(CombatAction action : actionDefense){
				action.done(combat,c,w2,this);
			}
		(Console.getInstance()).writeLog("<doDefense entity/>");
	}
	@Override
	public ErrorMen doFinally(TreeCard combat,LinkedCard c,Warlock w2){
		(Console.getInstance()).writeLog("<doFinally entity");
		if(actionFinally != null)
			return actionFinally.done(combat,c,w2,this);
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public ErrorMen doActive(Warlock w1){
		(Console.getInstance()).writeLog("<doActive entity");
		if(actionActive == null)
			return new ErrorMen(ErrorEnum.noAction);
		if(turn)
			return new ErrorMen(ErrorEnum.dischargedCard);
		actionActive.done(w1);
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public PlayCard insert(boolean canEnvi,boolean canInCombat) {
		(Console.getInstance()).writeLog("<insert entity");
		if(canInCombat){
			return PlayCard.noEntityNow;
		}
		return PlayCard.goGame;
		
	}
	@Override
	public void turnOn() {
		turn = true;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
		
	}
	@Override
	public void loseLife(int value) {
		life -= value;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	@Override
	public void modStats(int at, int def) {
		attack += at;
		life += def;
		defense += def;
		if(life < 0){
			life = 0;
		}
		if(defense < 0){
			defense = 0;
		}
		if(attack < 0 ){
			attack = 0;
		}
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	@Override
	public ErrorMen allOK(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj,StackCard interventions) {
		(Console.getInstance()).writeLog("<allOK entity");
		if(actionActive != null)
			return actionActive.allOk(combat, tar,self, w1, w2,wObj,interventions);
		return new ErrorMen(ErrorEnum.noAction);
	}
	@Override
	public ArrayList<ErrorMen> doPassive(LinkedCard link,Warlock w1) {
		(Console.getInstance()).writeLog("<doPassive entity");
		ArrayList<ErrorMen> er = new ArrayList<ErrorMen>();
		if(actionPassive == null){
			er.add(new ErrorMen(ErrorEnum.allOk));
			return er;
		}
		
		for(PassiveAction pas : actionPassive)
			er.add(pas.done(link,this,w1));
		(Console.getInstance()).writeLog("<doPassive entity/>");
		return er;
	}
	@Override
	public void setActionPassive(ArrayList<PassiveAction> pas) {
		actionPassive = pas;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	@Override
	public ArrayList<PassiveAction> getActionPassive() {
		return actionPassive;
	}
	@Override
	public ActiveAction getActionActive() {
		return actionActive;
	}
	@Override
	public ArrayList<CombatAction> getActionAttack() {
		return actionAttack;
	}
	@Override
	public ArrayList<CombatAction> getActionDefense() {
		return actionDefense;
	}
	@Override
	public CombatAction getActionFinally() {
		return actionFinally;
	}
	@Override
	public void setActionActive(ActiveAction act) {
		actionActive = act;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
		
	}
	@Override
	public void setActionAttack(ArrayList<CombatAction> att) {
		actionAttack = att;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
		
	}
	@Override
	public void setActionDefense(ArrayList<CombatAction> def) {
		actionDefense = def;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
		
	}
	@Override
	public void setActionFinally(CombatAction fin) {
		actionFinally = fin;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
		
	}
	@Override
	public void unDone() {
		(Console.getInstance()).writeLog("<unDone entity");
		if(actionActive != null)
			actionActive.unDone();
		if(actionAttack != null){
			for(CombatAction action : actionAttack){
				action.unDone();
			}
		}
		if(actionDefense !=  null){
			for(CombatAction action : actionDefense){
				action.unDone();
			}
		}
		if(actionFinally != null)
			actionFinally.unDone();
		if(actionPassive != null){
			for(PassiveAction pas : actionPassive)
				pas.unDone();
		}
		if(canInicialited()){
			life = defense;
		}
		
		(Console.getInstance()).writeLog("unDone entity/>");
	}
	@Override
	public boolean canTargetIntervention(){
		if(actionPassive == null){
			return true;
		}
		
		Iterator<PassiveAction> it = actionPassive.iterator();
		boolean can = true;
		while(it.hasNext() && can){
			if(!it.next().done(null, null,null).canTargetIntervention())
				can = false;
		}
		return can;
	}
	@Override
	public boolean haveActiveAction(){
		return actionActive != null;
	}
	@Override
	public String toStringType() {
		return "Ser";
	}
	@Override
	public boolean canInicialited(){
		if(actionPassive == null){
			return true;
		}
		
		Iterator<PassiveAction> it = actionPassive.iterator();
		boolean can = true;
		while(it.hasNext() && can){
			if(it.next().done(null, null,null).cantInitialized())
				can = false;
		}
		return can;
	}

}
