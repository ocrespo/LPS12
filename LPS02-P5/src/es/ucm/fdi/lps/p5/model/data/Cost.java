/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data;

import java.io.Serializable;
import java.util.StringTokenizer;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.game.Judge;




/**
 * @author usuario_local
 *
 */
public class Cost implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum PayCost{allOk,needDeal,needMana};
	
	private int earth = 0;
	private int sea = 0;
	private int air = 0;
	private int spirit = 0;
	private int generic = 0;
	
	
	private int dealGeneric = 0;
	/**
	 * parametrized builder
	 * @param earth
	 * @param sea
	 * @param air
	 * @param spirit
	 * @param generic
	 */
	public Cost(int earth,int sea,int air,int spirit,int generic){
		this.earth = earth;
		this.sea = sea;
		this.air = air;
		this.spirit = spirit;
		this.generic = generic;
	}
	/**
	 * default builder
	 */
	public Cost() {
	}
	/**
	 * @return the land
	 */
	public int getEarth() {
		return earth;
	}
	/**
 	* 
 	* @param earth the earth to set
 	*/
	public void setEarth(int earth) {
		this.earth = earth;
	}
	/**
	 * @return the sea
	 */
	public int getSea() {
		return sea;
	}
	/**
	 * @param sea the sea to set
	 */
	public void setSea(int sea) {
		this.sea = sea;
	}
	/**
	 * @return the air
	 */
	public int getAir() {
		return air;
	}
	/**
	 * @param air the air to set
	 */
	public void setAir(int air) {
		this.air = air;
	}
	/**
	 * @return the spirit
	 */
	public int getSpirit() {
		return spirit;
	}
	/**
	 * @param spirit the spirit to set
	 */
	public void setSpirit(int spirit) {
		this.spirit = spirit;
	}
	/**
	 * @return the generic
	 */
	public int getGeneric() {
		return generic;
	}
	/**
	 * @param generic the generic to set
	 */
	public void setGeneric(int generic) {
		this.generic = generic;
	}
	/**
	 * @return the dealGeneric
	 */
	public int getDealGeneric() {
		return dealGeneric;
	}
	/**
	 * 
	 * @return plus of all cost
	 */
	public int value(){
		return earth+air+sea+spirit+generic;
	}
	/**
	 * inicialize cost
	 */
	public void iniCost(){
		(Console.getInstance()).writeLog("iniCost Cost");
		air = 0;
		earth = 0;
		sea = 0;
		spirit = 0;
		generic = 0;
	}
	/**
	 * add cost
	 * @param c the cost to add
	 */
	public void add(Cost c){
		(Console.getInstance()).writeLog("add Cost");
		earth += c.earth;
		sea += c.sea;
		spirit += c.spirit;
		air += c.air;
		generic += c.generic;
	}
	/**
	 * add cost
	 * @param elem the element to add
	 * @param num how much 
	 */
	public void add(Element elem,int num){
		(Console.getInstance()).writeLog("add elem num Cost");
		switch (elem) {
		
		case earth:
			earth += num;
			break;

		case sea:
			sea += num;
			break;
			
		case air:
			air += num;
			break;
			
		case spirit:
			spirit += num;
			break;
			
		case generic:
			generic += num;
			break;
		}		
	}
	/**
	 * use a mana to pay a card
	 * @param c the card
	 * @return boolean if there is enough mana
	 */
	public PayCost pay(Cost c){
		(Console.getInstance()).writeLog("pay Cost");
		if((c.earth == earth) && (c.sea == sea) && (c.air == air) && (c.spirit == spirit) && (generic == c.generic)){
			generic = 0;
			earth = 0;
			sea = 0;
			air = 0;
			spirit = 0;
			return PayCost.allOk;
		}
		if((c.earth <= earth) && (c.sea <= sea) && (c.air <= air) && (c.spirit <= spirit)
				&&((c.earth+c.sea+c.air+c.spirit+c.generic) <= (earth+sea+air+spirit+generic))){
			earth -= c.earth;
			sea -= c.sea;
			air -= c.air;
			spirit -= c.spirit;
			if(c.generic <= generic){
				generic -= c.generic;
				return PayCost.allOk;
			}
			int gen = c.generic;
			gen -= generic;
			generic = 0;
			if((earth + sea +air +spirit) == gen){
				earth = 0;
				sea = 0;
				air = 0;
				spirit = 0;
			}
			else if(earth>=gen &&(air==0 && sea == 0 && spirit == 0)){
				earth -= gen;
			}
			else if(air>=gen &&(earth==0 && sea == 0 && spirit == 0)){
				earth -= gen;
			}
			else if(sea>=gen &&(air==0 && earth == 0 && spirit == 0)){
				earth -= gen;
			}
			else if(spirit>=gen &&(air==0 && sea == 0 && earth == 0)){
				earth -= gen;
			}
			else{ 		
				if((Configuration.getInstance()).isConsole()){
					askGeneric(gen);	
				}
				else{
					(Judge.getInstance()).notifyDealMana(gen);
					dealGeneric = gen;
					return PayCost.needDeal;
				}
			}
			return PayCost.allOk;
		}
		return PayCost.needMana;
	}
	/**
	 * 
	 * @param e the element
	 * @return
	 */
	public boolean payDealMana(Element e){
		if(e == Element.earth){
			if(earth > 0){
				earth--;
				dealGeneric--;
			}
			else{
				return false;
			}
		}
		else if(e == Element.air){
			if(air > 0){
				air--;
				dealGeneric--;
			}
			else{
				return false;
			}
		}
		else if(e == Element.sea){
			if(sea > 0){
				sea--;
				dealGeneric--;
			}
			else{
				return false;
			}
		}
		else if(e == Element.spirit){
			if(spirit > 0){
				spirit--;
				dealGeneric--;
			}
			else{
				return false;
			}
		}
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyDecreDealMana();					
		return dealGeneric == 0;
	}
	/**
	 * ask user how he would like pay the rest generic element
	 * @param gen generic mana which is necessary
	 */
	private void askGeneric(int gen){
		(Console.getInstance()).writeLog("askGeneric Cost");
		String aux = null;
		while(gen > 0 ){
			aux = (Console.getInstance()).askMana(gen,this.toString());
			if(aux.endsWith("t") && earth>0){
				StringTokenizer tok = new StringTokenizer(aux,"x");
				String value = tok.nextToken();
				if(value.equals("t")){
					earth --;
					gen--;
				}
				else{
					try{
						int num = Integer.parseInt(value);
						if(num<=earth){
							earth -= num;
							gen -= num;
						}
						else
							(Console.getInstance()).write("No tienes tantos cloros de tierra");
					}catch (Exception e) {
						(Console.getInstance()).write("Valor no valido");
					}
				}
			}		
			else if(aux.endsWith("m") && sea>0){
				StringTokenizer tok = new StringTokenizer(aux,"x");
				String value = tok.nextToken();
				if(value.equals("m")){
					sea --;
					gen--;
				}
				else{
					try{
						int num = Integer.parseInt(value);
						if(num<=sea){
							sea -= num;
							gen -= num;
						}
						else
							(Console.getInstance()).write("No tienes tantos cloros de mar");
					}catch (Exception e) {
						(Console.getInstance()).write("Valor no valido");
					}
				}
			}
			else if(aux.endsWith("a") && air>0){
				StringTokenizer tok = new StringTokenizer(aux,"x");
				String value = tok.nextToken();
				if(value.equals("a")){
					air --;
					gen--;
				}
				else{
					try{
						int num = Integer.parseInt(value);
						if(num<=air){
							air -= num;
							gen -= num;
						}
						else
							(Console.getInstance()).write("No tienes tantos cloros de aire");
					}catch (Exception e) {
						(Console.getInstance()).write("Valor no valido");
					}
				}
			}
			else if(aux.endsWith("e") && spirit>0){
				StringTokenizer tok = new StringTokenizer(aux,"x");
				String value = tok.nextToken();
				if(value.equals("e")){
					spirit --;
					gen--;
				}
				else{
					try{
						int num = Integer.parseInt(value);
						if(num<=spirit){
							spirit -= num;
							gen -= num;
						}
						else
							(Console.getInstance()).write("No tienes tantos cloros de espiritu");
					}catch (Exception e) {
						(Console.getInstance()).write("Valor no valido");
					}
				}
			}
			else if(aux.endsWith("g")){
				(Console.getInstance()).write("Tus cloros genericos ya se usaron y no te quedan");
			}
			else if(aux.equals("t") || aux.equals("m") || aux.equals("a") || aux.equals("e"))
				(Console.getInstance()).write("No tienes cloro de ese tipo");
			else 
				(Console.getInstance()).write("Valores no valido (recuerde Tierra='T',Mar='M', Aire='A'),Espiritu='E'");
		}//while
	}
	/**
	 * @return String , the information of the Cost 
	 */
	public String toString(){
		String cost = "";
		if(earth != 0)
			cost += earth+"T ";
		if((sea != 0) && (!cost.equals("")))
			cost += "+ "+sea+"M ";
		else if(sea != 0)
			cost += sea+"M ";
		if((air != 0) && (!cost.equals("")))
			cost += "+ "+air+"A ";
		else if(air != 0)
			cost += air+"A ";
		if((spirit != 0) && (!cost.equals("")))
			cost += "+ "+spirit+"E ";
		else if(spirit != 0)
			cost += spirit+"E ";
		if((generic != 0) && (!cost.equals("")))
			cost += "+ "+generic+"G ";
		else if(generic != 0)
			cost += generic+"G ";		
		
		return cost;
	}
	
	
}
