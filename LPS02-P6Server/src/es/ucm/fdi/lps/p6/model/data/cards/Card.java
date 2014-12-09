package es.ucm.fdi.lps.p6.model.data.cards;

import java.io.Serializable;
import java.util.ArrayList;

import es.ucm.fdi.lps.p6.model.data.Cost;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p6.model.data.actions.combat.CombatAction;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.actions.passive.PassiveAction;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.game.Judge;


/**
 * @author Roni
 * 
 */


public abstract class Card implements Comparable<Card> , Serializable , Cloneable{
	
	/**
	 * 
	 */
	private static int count = 1;
	private  int id ;
	private static final long serialVersionUID = 1L;
	public static enum Element{earth,sea,air,spirit,generic};
	public static enum PlayCard{goEnviroment,goGame,goIntervention,noMoreEnviroment,noEnviromentNow,noEntityNow,noInterventionNow,noFound,needMoreMana,noError,
								needDealMana};
	
	protected final String code;
	protected final String name;
	protected  String description;
	protected String dirImg;

	protected final Element element;

	protected final int max;
	protected String buff = "";
	
	protected ArrayList<ActiveAction> activeEffects = new ArrayList<ActiveAction>();
	/**
	 * card builder
	 * @param code
	 * @param name
	 * @param element
	 * @param max
	 * @param description
	 */
	public Card(String code, String name,Element element ,int max,String description,String dirImg){
		this.code = code;
		this.name = name;
		//this.type = type;
		this.max = max;
		this.element = element;
		this.description = description;
		this.dirImg = dirImg;
		id = count;
		count++;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * 
	 * @return the cost
	 */
	public Cost getCost(){
		return null;
	}
	/**
	 * inicialize card
	 */
	public void ini(){
	}
	/**
	 * inicialize buff card
	 */
	public void iniBuff(){
	}
	/**
	 * undo effect of the actions
	 */
	public void unDone(){
		
	}
	/**
	 * do the active actions
	 * @param w , warlock
	 * @return ErrorMen info about success
	 */
	public abstract ErrorMen doActive( Warlock w);
	/**
	 * do the attack action
	 * @param link , information of the combat
	 * @param wb , warlock enemy
	 */
	public void doAttack(TreeCard combat,LinkedCard link,Warlock wb){
	}
	/**
	 * do the defense action
	 * @param link , information of the combat
	 * @param wb , warlock enemy
	 */
	public void doDefense(TreeCard combat,LinkedCard link,Warlock wb){
	}
	/**
	 * do the finally action
	 * @param link , information of the combat
	 * @param wb , warlock enemy
	 */
	public ErrorMen doFinally(TreeCard combat,LinkedCard link,Warlock wb){
		return new ErrorMen(ErrorEnum.allOk);
	}
	/**
	 * do the passive action
	 * @param link , information of the combat
	 * @return info about success
	 */
	public ArrayList<ErrorMen> doPassive(LinkedCard link,Warlock w1){
		return null;
	}
	/**
	 * @param canEnvi , if can play more enviroment
	 * @param canInCombat , if can play card in combat
	 * @return where insert and if can 
	 */
	public abstract PlayCard insert(boolean canEnvi,boolean canInCombat);

	/**
	 * @return boolean , if the card is charge
	 */
	public  boolean canUse(){
		return true;
	}
	/**
	 * 
	 * @return the attack
	 */
	public int getAttack(){
		return 0;
	}
	/**
	 * 
	 * @return the defense
	 */
	public int getDefense(){
		return 0;
	}
	/**
	 * set attack
	 * @param att
	 */
	public void setAttack(int att){
	}
	/**
	 * set defense
	 * @param def
	 */
	public void setDefense(int def){
	}
	/**
	 * 
	 * @return the life
	 */
	public int getLife(){
		return -1;
	}
	/**
	 * set the life
	 * @param life
	 */
	public void setLife(int life){
	}
	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}
	/**
	 * 
	 * @return the element
	 */
	public Element getElement(){
		return element;
	}
	/**
	 * summon loseLife
	 * @param value 
	 */
	public void loseLife(int value){
	}
	/**
	 * 
	 * @return if the life is under 0
	 */
	public boolean isDie(){
		return getLife() < 0;
	}
	/**
	 * charge the card
	 */
	public void turnOn(){
	}
	/**
	 * modifiers the attack and defense values
	 * @param at , value of attack
	 * @param lif , value of defense
	 */
	public void modStats(int at,int lif){
	}
	/**
	 * 
	 * @param combat the combat
	 * @param tar the target
	 * @param self the self card
	 * @param w1 warlock that use the card
	 * @param w2 enemy warlock
	 * @return if is all values okay
	 */
	public  ErrorMen allOK(TreeCard combat,Card tar ,Card self, Warlock w1,Warlock w2,Warlock wObj,StackCard interventions){
		//return type.allOK(combat, tar,self, w1, w2);
		return new ErrorMen(ErrorEnum.allOk);
	}
	/**
	 * set passive actions
	 * @param pas arraylist of passive actions
	 */
	public void setActionPassive(ArrayList<PassiveAction> pas){
	}
	/**
	 * set active action
	 * @param act the active action
	 */
	public abstract void setActionActive(ActiveAction act);
	/**
	 * set the attack action
	 * @param att the attack action
	 */
	public void  setActionAttack(ArrayList<CombatAction> att){
	}
	/**
	 * set the defense action
	 * @param def the defense action
	 */
	public void setActionDefense(ArrayList<CombatAction> def){
	}
	/**
	 * set the finally action
	 * @param fin the finally action
	 */
	public void setActionFinally(CombatAction fin){
	}
	/**
	 * @return the passive action
	 */
	public ArrayList<PassiveAction> getActionPassive(){
		return null;
	}
	/**
	 * 
	 * @return the active action
	 */
	public abstract ActiveAction getActionActive();
	/**
	 * 
	 * @return the action attack
	 */
	public ArrayList<CombatAction> getActionAttack(){
		return null;
	}
	/**
	 * 
	 * @return the defense action
	 */
	public ArrayList<CombatAction> getActionDefense(){
		return null;
	}
	/**
	 * 
	 * @return the finally action
	 */
	public CombatAction getActionFinally(){
		return null;
	}
	/**
	 * set the description
	 * @param text the description
	 */
	public void setDescription(String text){
		description = text;
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	/**
	 * 
	 * @return the description
	 */
	public  String getDescription(){
		return description;
	}
	/**
	 * 
	 * @return if card can target by intervention
	 */
	public boolean canTargetIntervention() {
		return true;
	}
	/**
	 * 
	 * @return is card have active action
	 */
	public boolean haveActiveAction(){
		return false;
	}
	/**
	 * 
	 * @return the type of card
	 */
	public abstract String toStringType();
	public String toStringElement(){
		if(element == Element.air){
			return "Aire";
		}
		else if(element == Element.earth){
			return "Tierra";
		}
		else if(element == Element.spirit){
			return "Espiritu";
		}
		return "Mar";
	}
	@Override
	public int compareTo(Card o) {
		if(this.id == o.id)
			return 0;
		if(this.id < o.id){
			return -1;
		}
		return 1;
		/*if(this == o)
			return 0;
		if(!code.equals(o.code)){
			return code.compareTo(o.code);
		}
		return 1;*/
	}
	/**
	 * 
	 * @return the buff
	 */
	public String getBuff(){
		return buff;
	}
	/**
	 * set the buff
	 * @param buff text
	 */
	public void setBuff(String buff){
		this.buff = buff;
	}
	/**
	 * add buff
	 * @param buff
	 */
	public void addBuff(String buff){
		if(this.buff.equals("")){
			this.buff = "<html>" + buff;
		}
		else
			this.buff = this.buff + "<br>" + buff;
		//this.buff = this.buff + "<br>sasadas</html>";
	}
	/**
	 * 
	 * @return the dir image
	 */
	public String getDirImg(){
		return dirImg;
	}
	/**
	 * clone
	 */
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            //System.out.println(" no se puede duplicar");
        }
        return obj;
    }
	public boolean canInicialited() {
		return true;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param turn the turn to set
	 */
	public void setTurn(boolean turn) {
	}
}
