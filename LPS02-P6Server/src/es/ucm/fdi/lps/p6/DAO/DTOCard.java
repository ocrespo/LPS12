/**
 * 
 */
package es.ucm.fdi.lps.p6.DAO;

import java.awt.BorderLayout;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Cost;
import es.ucm.fdi.lps.p6.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p6.model.data.actions.combat.CombatAction;
import es.ucm.fdi.lps.p6.model.data.actions.passive.PassiveAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;

/**
 * @author Roni
 *
 */
public class DTOCard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String name;
	private String description = "<html>";
	private String dirImg;
	private String buff;
	private String type;
	private boolean turn;
	private Element element;
	
	private int max;
	
	
	private int attack = 0;
	private int defense = 0;
	private int life;
	private Cost cost = null;
	
	private ArrayList<CombatAction> actionAttack = null;
	private ArrayList<CombatAction> actionDefense = null;
	private CombatAction actionFinally = null;
	private ActiveAction actionActive = null;
	private ArrayList<PassiveAction> actionPassive = null;
	
	public DTOCard(){
		
	}
	public DTOCard(Card c){
		setId(c.getId());
		code = c.getCode();
		name = c.getName();
		description = c.getDescription();
		dirImg = c.getDirImg();
		element = c.getElement();
		attack = c.getAttack();
		defense = c.getDefense();
		actionAttack = c.getActionAttack();
		actionDefense = c.getActionDefense();
		actionFinally = c.getActionFinally();
		actionActive = c.getActionActive();
		actionPassive = c.getActionPassive();
		cost = c.getCost();
		setBuff(c.getBuff());
		//this.type = c.type;
		setTurn(!c.canUse());
		setLife(c.getLife());
		if(c.getLife() == -1 && c.getCost() == null){
			type = "enviroment";
		}
		else if(c.getLife() == -1){
			type = "intervention";

		}
		else{
			type = "entity";

		}
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		if(description.equals("")){
			this.description = description;
		}
		else{
			if(!Configuration.getInstance().isConsole()){
				StringTokenizer tok = new StringTokenizer(description, "\r\n");
				this.description += tok.nextToken();
				while(tok.hasMoreTokens()){
					this.description += "<br>"+tok.nextToken();
				}
			}
			else
				this.description = description;
			
		}
	}
	/**
	 * @return the dirImg
	 */
	public String getDirImg() {
		return dirImg;
	}
	/**
	 * @param dirImg the dirImg to set
	 */
	public void setDirImg(String dirImg) {
		this.dirImg = dirImg;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}
	/**
	 * @param string the element to set
	 */
	public void setElement(String element) {
		if(element.equals("earth"))
			this.element = Element.earth;
		else if(element.equals("air"))
			this.element = Element.air;
		else if(element.equals("spirit"))
			this.element = Element.spirit;
		else if(element.equals("sea"))
			this.element = Element.sea;
		else if(element.equals("generic"))
			this.element = Element.generic;
				
	}
	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}
	/**
	 * @param string the max to set
	 */
	public void setMax(String value)throws Exception{
		this.max = Integer.parseInt(value);
	}

	/**
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}
	/**
	 * @param string the attack to set
	 */
	public void setAttack(String value)throws Exception {
		if(value != null)
			this.attack = Integer.parseInt(value);
	}
	/**
	 * @return the defense
	 */
	public int getDefense() {
		return defense;
	}
	/**
	 * @param string the defense to set
	 */
	public void setDefense(String value)throws Exception {
		this.defense = Integer.parseInt(value);
	}

	/**
	 * @return the cost
	 */
	public Cost getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(Cost cost) {
		this.cost = cost;
	}
	/**
	 * @return the actionAttack
	 */
	public ArrayList<CombatAction> getActionAttack() {
		return actionAttack;
	}
	/**
	 * @param actionAttack the actionAttack to set
	 */
	public void setActionAttack(CombatAction actionAttack) {
		if(this.actionAttack == null)
			this.actionAttack = new ArrayList<CombatAction>();
		this.actionAttack.add(actionAttack);
	}
	/**
	 * @return the actionDefense
	 */
	public ArrayList<CombatAction> getActionDefense() {
		return actionDefense;
	}
	/**
	 * @param actionDefense the actionDefense to set
	 */
	public void setActionDefense(CombatAction actionDefense) {
		if(this.actionDefense == null)
			this.actionDefense = new ArrayList<CombatAction>();
		this.actionDefense.add(actionDefense);
	}
	/**
	 * @return the actionFinally
	 */
	public CombatAction getActionFinally() {
		return actionFinally;
	}
	/**
	 * @param actionFinally the actionFinally to set
	 */
	public void setActionFinally(CombatAction actionFinally) {
		this.actionFinally = actionFinally;
	}
	/**
	 * @return the actionActive
	 */
	public ActiveAction getActionActive() {
		return actionActive;
	}
	/**
	 * @param actionActive the actionActive to set
	 */
	public void setActionActive(ActiveAction actionActive) {
		this.actionActive = actionActive;
	}
	/**
	 * @return the actionPassive
	 */
	public ArrayList<PassiveAction> getActionPassive() {
		return actionPassive;
	}
	/**
	 * @param actionPassive the actionPassive to set
	 */
	public void setActionPassive(PassiveAction actionPassive) {
		if(this.actionPassive == null)
			this.actionPassive = new ArrayList<PassiveAction>();
		this.actionPassive.add(actionPassive);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuff() {
		return buff;
	}
	public void setBuff(String buff) {
		this.buff = buff;
	}
	public boolean isTurn() {
		return turn;
	}
	public void setTurn(boolean turn) {
		this.turn = turn;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	
}
