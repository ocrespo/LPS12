/**
 * 
 */
package es.ucm.fdi.lps.p5.model.game;

import java.util.Stack;
import java.util.StringTokenizer;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Configuration;
import es.ucm.fdi.lps.p5.model.data.FileObject;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;



/**
 * @author ocrespo
 *
 */
public class Judge extends Observed   {
	private static Judge SINGLETON = null;
	private Stack<Card> interventions = null;
	private Stack<Card> next = null;
	private Stack<ActiveAction> undo = new Stack<ActiveAction>();
	private LinkedCard actLinCombat ;
	private int dealCount;
	/**
	 * builder of the judge
	 */
	private Judge() {} 
	@SuppressWarnings("unused")
	private synchronized static void createInstance() {
	    if (SINGLETON == null) { 
	    	SINGLETON = new Judge(); 
	    }
	  }
	/**
	 * 
	 * @return the instance of judge
	 */
	public final static Judge getInstance() { 
	    if (SINGLETON == null)
	    	SINGLETON = new Judge();
	    
	    return SINGLETON;
	  } 
	@Override
	/**
	 * 
	 */
	public Object clone() throws CloneNotSupportedException { 
		throw new CloneNotSupportedException();  
	}
	/**
	 * solve the combat
	 * @param combat 
	 * @param w1  , warlock who attack
	 * @param w2  , warlock who defense
	 */
	public void solveCombat(TreeCard combat,Warlock w1,Warlock w2){
		(Console.getInstance()).writeLog("solveCombat Judge");
		LinkedCard lin = null;
		Card c = null;
		while(!combat.isEmpty()){
			lin = combat.remove();
			setChanged();
			notifyRemoveCombat(lin.getCard());
			
			int at = 0;
			
			c = lin.getCard();
			c.doPassive(lin,w1);
			at = c.getAttack();

			int defValue = 0;
			int atValue = 0;
			int max = lin.size();
			if(max != 1){
				for(int i=1;i<max;i++){
					c = lin.getCard(i);
					defValue += c.getLife();
					if(c.canUse())
						atValue += c.getAttack();
				}
			}
			
			doDamage(lin,w1,w2,at,atValue,defValue);
			
			if(next == null)
				next = new Stack<Card>();
			
			c = lin.getCard();
			if(!c.isDie()){
				if(c.doFinally(combat,lin, w2).nextTurn())
					next.push(c);
				c.unDone();
			}
			max = lin.size();
			if(max != 1){
				for(int i=1;i<max;i++){
					c = lin.getCard(i);
					if(c.doFinally(combat,lin, w1).nextTurn())
						next.push(c);
					c.unDone();
				}
			}
					
		}
		unDoneAct();
		undoIntervention();
		
	}
	/**
	 * solve the next combat
	 * @param combat the combat
	 * @param w1 warlock who attack
	 * @param w2 warlock who defense
	 */
	public void nextSolveCombat(TreeCard combat,Warlock w1,Warlock w2){
		if(actLinCombat != null){
			finishActCombat(combat, w1, w2);
		}
		if(combat.isEmpty()){
			unDoneAct();
			undoIntervention();
			notifyFinishSolverCombat();
			actLinCombat = null;
		}
		else{
			LinkedCard lin = combat.remove();
			Card c = null;
			
			setChanged();
			notifyRemoveCombat(lin.getCard());
			
			int at = 0;
			
			c = lin.getCard();
			c.doPassive(lin,w1);
			at = c.getAttack();
	
			int defValue = 0;
			int atValue = 0;
			int max = lin.size();
			if(max != 1){
				for(int i=1;i<max;i++){
					c = lin.getCard(i);
					defValue += c.getLife();
					if(c.canUse())
						atValue += c.getAttack();
				}
			}
			
			doDamage(lin,w1,w2,at,atValue,defValue);
			actLinCombat = lin;
		}
		
	}
	/**
	 * solve the finish action
	 * @param combat the combat
	 * @param w1 warlock who attack
	 * @param w2 warlock who defense
	 */
	public void finishActCombat(TreeCard combat,Warlock w1,Warlock w2){
		if(next == null)
			next = new Stack<Card>();
		
		Card c = actLinCombat.getCard();
		if(!c.isDie()){
			if(c.doFinally(combat,actLinCombat, w2).nextTurn())
				next.push(c);
			c.unDone();
		}
		int max = actLinCombat.size();
		if(max != 1){
			for(int i=1;i<max;i++){
				c = actLinCombat.getCard(i);
				if(c.doFinally(combat,actLinCombat, w1).nextTurn())
					next.push(c);
				c.unDone();
			}
		}
				
	
		
	}
	/**
	 * do damage to entity and warlock
	 * @param lin , present combat
	 * @param w1 , warlock who attack
	 * @param w2  , warlock who defense
	 * @param at , value of attack of the attacker entity
	 * @param atValue , value of attack of the defenders entity
	 * @param defValue , value of defense of the defenders entity
	 */
	private void doDamage(LinkedCard lin,Warlock w1,Warlock w2,int at,int atValue,int defValue){
		(Console.getInstance()).writeLog("doDamage Judge");
		
		Card c = null;
		Card cAt = null;
		int num = lin.size();
		int value = at;
		if(num != 1){
			//c = lin.getCard();
			cAt = lin.removeTop();
			if(atValue > 0){
				cAt.loseLife(atValue);
				killEntity(cAt, w1,lin,atValue);
			}
			
			if(num == 2){		
				c = lin.getCard(0);
				if(c.canUse()){
					c.loseLife(at);
					killEntity(c, w2,lin,at);
					value -=  defValue+1;
				}
			}
			else if(at >= (defValue+num-1)){
				while(!lin.isEmpty()){
					c = lin.removeTop();
					if(c.canUse()){
						at = c.getDefense()+1;
						c.loseLife(at);
						killEntity(c, w2,lin,at);
						value -=  c.getDefense()+1;
					}
				}
				
				(Console.getInstance()).write("\r\nLos seres defensores han muerto todos");
				setChanged();
				notifyInfoSolveCombat("Los seres defensores han muerto todos");
				
			}
			else{
				if(at > 0){
					if((Configuration.getInstance()).isConsole()){
						value = askDamage(lin, at, w2);
					}
					else{
						notifyDealDamage(at);
						dealCount = at;
					}
				}
			}
			
		}
		if(value > 0){
			if((Configuration.getInstance()).isConsole() || dealCount == 0){
				w2.loseLife(value);
				(Console.getInstance()).write("\r\n\r\nEl brujo "+w2.getName()+" ha sufrido "+value+" puntos de vida");
				setChanged();
				notifyInfoSolveCombat("El brujo "+w2.getName()+" ha sufrido "+value+" puntos de vida");
				
				(Console.getInstance()).write("Pulse enter para seguir");
				(Console.getInstance()).read();
			}
		}
		if(cAt != null)
			lin.insertFirst(cAt);
	}
	/**
	 * deal damage between defenders, GUI deal
	 * @param c the card
	 * @param wDef warlock who defender
	 */
	public void dealDamage(Card c,Warlock wDef){
		if(dealCount > 0){
			if(!c.isDie()){
				c.loseLife(1);
				killEntity(c, wDef, actLinCombat, 1);
				dealCount--;
				
				setChanged();
				notifyDecreaseSolvecombat();
				
				if(dealCount == 0){
					setChanged();
					notifyFinishDealDamage();
				}
			}
			else{
				notifyErrorSolveCombat("Ese ser ya ha muerto");
			}
		}
		
	}
	/**
	 * pass to deal and take all damage to warlock
	 * @param wDef warlock
	 */
	public void passDeal(Warlock wDef){
		if(dealCount > 0){
			wDef.loseLife(dealCount);
			setChanged();
			notifyInfoSolveCombat("El brujo "+wDef.getName()+" ha sufrido "+dealCount+" puntos de vida");
			dealCount = 0;
		}
	}
	/**
	 * remove a entity from the game if his life is under 0
	 * @param c , card of the entity
	 * @param w , warlock owner
	 */
	public void killEntity(Card c,Warlock w,LinkedCard lin,int value){
		(Console.getInstance()).writeLog("killEntity Judge");
		(Console.getInstance()).write("\r\n\r\nEl ser\n"+c+"\r\nha sufrido "+value+" puntos de daño\r\n");
		
		setChanged();
		notifyInfoSolveCombat("El ser\n"+c.getName()+"\r\nha sufrido "+value+" puntos de daño");
		
		(Console.getInstance()).write("Pulse enter para seguir");
		(Console.getInstance()).read();
		
		if(c.isDie()){
			w.dieInGame(c);
			
			if(lin != null){
				lin.removeCard(c);			
				//notifyRemoveFromCombat(lin.getCard(), c);
			}
			(Console.getInstance()).write("Ha caido un heroes:\r\n"+c+"\r\ndel brujo "+w.getName()+"\r\n");
			
			setChanged();
			notifyInfoSolveCombat("Ha caido un heroes: "+c.getName()+" ha sufrido "+value+" puntos de daño");
			
			(Console.getInstance()).write("Pulse enter para seguir");
			(Console.getInstance()).read();
		}
	}
	/**
	 * ask player how he would like deal the damage
	 * @param lin , present combat
	 * @param damage , damge to deal
	 * @param w , warlock who defender
	 * @return the rest of damage
	 */
	private int askDamage(LinkedCard lin,int damage,Warlock w){
		(Console.getInstance()).writeLog("askDamage Judge");
		(Console.getInstance()).write("---------------\r\nReparta "+damage+" entre tus defensores\r\n"+ lin.toStringGame()+"\r\nIntroduce --daño 'cantidad' 'codigo'(si hubiera mas de uno especificar mediante un entero su posicion" +
				", contando solo las que sean iguales)\r\n" +
				" para asignar daño\r\nSi desea no repartir mas daño introduzca '--sig'.\r\nSi desea volver a ver la criaturas use '--ver'");
		StringTokenizer tok = null;
		StringTokenizer value = null;
		int count = 0;
		Card c = null;
		String text = "";
		while(damage !=0 && !text.equals("--sig")){
			(Console.getInstance()).write("Le falta por repatir "+damage+" daño.\r\nIntroduzca datos:");
			text = (Console.getInstance()).read();
			if(text.startsWith("--daño")){
				tok = new StringTokenizer(text);
				value = new StringTokenizer(text);
				tok.nextToken();
				if((c=parserDamage(lin, tok, damage)) != null){
					value.nextToken();
					count = Integer.parseInt(value.nextToken());
					c.loseLife(count);
					killEntity(c, w,lin,count);
					damage -= count;
				}
			}
			if(text.startsWith("--ver"))
				(Console.getInstance()).write("---------------\r\nReparta "+damage+" entre tus defensores\r\n"+ lin.toStringGame()+"\r\nIntroduce --daño 'cantidad' 'codigo'(si hubiera mas de uno especificar mediante un entero su posicion" +
						", contando solo las que sean iguales)\r\n" +
						" para asignar daño\r\nSi desea no repartir mas daño introduzca '--sig'.\r\nSi desea volver a ver la criaturas use '--ver'");
			else if(!text.startsWith("--daño") && !text.equals("--sig") && !text.equals("--ver") && !text.equals(""))
				(Console.getInstance()).write("No ha introducido una opcion válida");
		}
		return damage;
	}
	/**
	 * 
	 * @param lin , present combat
	 * @param tok the user line
	 * @param damage , damge to deal
	 * @return the card or null if fail
	 */
	private Card parserDamage(LinkedCard lin,StringTokenizer tok,int damage){
		(Console.getInstance()).writeLog("parserDamage Judge");
		int count = 0;
		Card c = null;
		String code = null;
		int pos = 1;
		if(tok.hasMoreTokens()){
			try{
				count = Integer.parseInt(tok.nextToken());
				if(count <= damage){
					if(tok.hasMoreTokens()){
						code = tok.nextToken();
						if(tok.hasMoreTokens())
							pos = Integer.parseInt(tok.nextToken());
						c = lin.getCard(code,pos);
						if(c!= null){
							if(count <= c.getLife()+1){
								
								return c;
							}
							else
								(Console.getInstance()).write("Esa entidad no puede sufrir tanto daño");
						}
						else
							(Console.getInstance()).write("Esa carta no esta en el combate o ya ha muerto o no hay tantas cartas iguales");	
					}
					else 
						(Console.getInstance()).write("Formato no valido, le falta especificar la carta que va a sufrir el daño");
				}
				else 
					(Console.getInstance()).write("No hay tanto daño para repartir");
				 
			}catch (Exception e) {
				(Console.getInstance()).write("El valor debe ser un numero entero");
			}
		}
		return null;
	}
	/**
	 * 
	 * @param w1
	 * @param w2
	 * @return who winner
	 */
	public int winner(Warlock w1,Warlock w2){
		(Console.getInstance()).writeLog("winner Judge");
		if(w1.getLife() <= 0 && w2.getLife() <= 0)
			return -1;
		if(w1.getLife() <= 0)
			return 1;
		if(w2.getLife() <= 0)
			return 2;
		return 0;
	}
	/**
	 * solve the intervention
	 * @param inter , all interventions to solve
	 */
	public void solveIntervention(StackCard inter){
		(Console.getInstance()).writeLog("solveIntervention Judge");
		Card c = null;
		if(interventions == null)
			interventions = new Stack<Card>();
		while(!inter.isEmpty()){
			c = inter.removeTop();
			interventions.push(c);
			(Console.getInstance()).write("Se realiza:\n"+c);
			if(c.doActive(null).success())
				(Console.getInstance()).write("\r\nEs super efectivo\r\n");
			else
				(Console.getInstance()).write("\r\nNo es efectivo\r\n");
			(Console.getInstance()).read();
		}
	}
	public void nextSolveIntervention(StackCard inter){
		(Console.getInstance()).writeLog("solveIntervention Judge");
		Card c = null;
		if(interventions == null)
			interventions = new Stack<Card>();
		if(!inter.isEmpty()){
			c = inter.removeTop();
			interventions.push(c);		
			if(c.doActive(null).success()){
				setChanged();
				notifyInfoSolveIntervention("<html>Es super efectivo</html>");
			}
			else{
				setChanged();
				notifyInfoSolveIntervention("<html>No es efectivo</html>");
			}
		}
		else{
			setChanged();
			notifyFinishSolveIntervention();
		}
	}
	/**
	 * undo the effects from interventions
	 */
	public void undoIntervention(){
		(Console.getInstance()).writeLog("undoIntervention Judge");
		if(interventions != null){
			while(!interventions.isEmpty()){
				interventions.pop().unDone();
			}
		}
		interventions = null;
	}
	/**
	 * solve the next turn effect
	 */
	public void solveNext(){
		(Console.getInstance()).writeLog("solveNext Judge");
		if(next != null){
			while(!next.isEmpty()){
				next.pop().doFinally(null,null, null);
			}
		}
		interventions = null;
	}
	/**
	 * 
	 * @param w , warlock
	 * @return if warlock is die 
	 */
	public boolean lose(Warlock w){
		return w.getLife() <= 0;
	}
	/**
	 * add active action to undo later
	 * @param act active action to undo
	 */
	public void addUndo(ActiveAction act){
		(Console.getInstance()).writeLog("addUndo Judge");
		undo.add(act);
	}
	/**
	 * undo the stack with activeaction
	 */
	public void unDoneAct(){
		(Console.getInstance()).writeLog("unDoneAct Judge");
		if(undo != null){
			while(!undo.isEmpty()){
				undo.pop().unDone();
			}
		}
	}
	/**
	 * clear all info
	 */
	private void clear(){
		interventions = null;
		next = null;
		undo = null;
		actLinCombat = null;
		dealCount = 0;
	}
	/**
	 * save info to need save
	 */
	public void addInfoToSave(){
		(FileObject.getInstance()).setUndo(undo);
		(FileObject.getInstance()).setUnDoInterventions(interventions);
	}
	/**
	 * take load information
	 */
	public void takeInfoLoad(){
		clear();
		undo = (FileObject.getInstance()).getUndo();
		interventions = (FileObject.getInstance()).getUnDoInterventions();
		if(undo == null){
			undo = new Stack<ActiveAction>();
		}
	}
}
