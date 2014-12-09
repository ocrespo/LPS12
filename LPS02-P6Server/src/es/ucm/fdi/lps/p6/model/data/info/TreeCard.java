/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.TreeMap;

import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.permanent.PermanentAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.game.Judge;



/**
 * @author Roni
 *
 */
public class TreeCard implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum CombatError{allOk,noAttack,cantDefense,needDefenseOther};
	
	private TreeMap<Card, LinkedCard> tree = new TreeMap<Card, LinkedCard>();
	private PermanentAction actionPermanent = null;
	
	public TreeCard(){
		
	}
	/**
	 * Insert card c into linkedlist from key.
	 * @param key 
	 * @param c
	 * @return info about execute
	 */
	public CombatError insert(String key,Card c){
		if(tree != null){
			LinkedCard link = getByKey(key);
			if(link != null){
				return insertCard(link.getCard(), c);
			}			
		}
		return CombatError.noAttack;
	}
	/**
	 * Insert card c into linkedlist from key.
	 * @param key 
	 * @param c
	 * @return info about execute
	 */
	public CombatError insertCard(Card tar,Card c){
		LinkedCard link = tree.get(tar);
		if(link == null){
			return CombatError.noAttack;
		}
		//Card attacker = link.getCard();
		if(actionPermanent != null){
			ErrorMen er = actionPermanent.doPermanent(tar);
			if(er.needDefenseOther()){
				return CombatError.needDefenseOther;
			}
		}
		LinkedCard aux = new LinkedCard();
		aux.insert(c);
		boolean can = true;
		ArrayList<ErrorMen> er = tar.doPassive(aux,null);
		int count = 0;
		while(can && er!=null && !er.isEmpty() ){
			can = er.remove(count).canDefense();
			//count++;
		}
		if(!can)
			return CombatError.cantDefense;
		link.insert(c);
		return CombatError.allOk;
	}
	
	/**
	 * Insert new card into tree
	 * @param c
	 */
	public boolean insert(Card c){
		LinkedCard link = tree.get(c);
		if(link != null){
			/*if(link.getCard() == c)
				return false;
			
			int acu = 2;
			String aux = null;
			while(link != null){
				aux = c.getCode()+acu;
				link = tree.get(aux);
				if(link != null)
					if(link.getCard() == c)
						return false;
				acu++;
			}	
			link = new LinkedCard();
			link.insert(c);
			tree.put(aux,link);
			return true;*/
			return false;
		}
		link = new LinkedCard();
		link.insert(c);

		tree.put(c,link);
		return true;
	}
	/**
	 * 
	 * @return if is empty
	 */
	public boolean isEmpty(){
		return tree.isEmpty();
	}
	/**
	 * 
	 * @return the linkedlist in the first position
	 */
	public LinkedCard remove(){
		Card c = tree.firstKey();		
		LinkedCard lin = tree.get(c);
		tree.remove(c);
		return lin;
	
	}
	/**
	 * 
	 * @param c
	 * @return if there is the card by id 
	 */
	public boolean isHere(Card c){
		Iterator<LinkedCard> it = tree.values().iterator();
		boolean here = false;
		while(it.hasNext() && !here){
			if(it.next().foundCard(c) != null)
				here = true;
		}
		return here;
	}
	public boolean isHereAt(Card c){
		return tree.get(c) != null;
	}
	/**
	 * tree into string
	 */
	public String toString(){
		if(!tree.isEmpty()){
			String acu = "";
			for(LinkedCard lin : tree.values()){
				acu += "\r\n--Combate----------------\r\n";
				acu += lin.toStringCombat();
			}
			return acu;
		}
		return null;		
	}
	/**
	 * 
	 * @return the link with all card into the first position
	 */
	public LinkedCard takeAllFisrt(){
		if(tree.isEmpty())
			return null;
		LinkedCard link = new LinkedCard();
		for(LinkedCard aux : tree.values())
			link.insert(aux.getCard());
		
		return link;	
	}
	/**
	 * 
	 * @return all defenders in arraylist
	 */
	public ArrayList<Card> takeAllDefenders(){
		if(tree.isEmpty())
			return null;
		ArrayList<Card> def = new ArrayList<Card>();
		int attacker = 0;
		for(LinkedCard aux : tree.values()){
			def.addAll(aux.getValues());
			def.remove(attacker);
			attacker =  def.size();
		}	
		return def;	
	}
	/**
	 * 
	 * @param c the card who attack
	 * @return if can remove all deffenders at combat or not
	 */
	public boolean removeDefenders(Card c){
		LinkedCard link = getCombat(c);
		if(link == null){
			return false;
		}
		while(!link.isEmpty()){
			link.removeTop();
		}
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRemoveDefenders(c);
		link.insertFirst(c);
		return true;
	}
	/**
	 * 
	 * @param key the key of the combat
	 * @return the combat by key in console system
	 */
	public LinkedCard getByKey(String key){
		Iterator<LinkedCard> it = tree.values().iterator();
		boolean found = false;
		StringTokenizer tok = new StringTokenizer(key);
		String value = tok.nextToken();
		int pos = 1;
		if(tok.hasMoreElements())
			pos = Integer.parseInt(tok.nextToken());
		LinkedCard link = null;
		while(it.hasNext() && !found){
			link = it.next();
			if(link.getCard().getCode().equals(value) ){
				if(pos == 1){
					found = true;
				}
				else{
					pos--;
				}
			}
		}
		if(found){
			return link;
		}
		return null;
	}
	/**
	 * 
	 * @param c
	 * @return the link by  id
	 */
	public LinkedCard getCombat(Card c){
		return tree.get(c);
	}
	/**
	 * 
	 * @param key
	 * @return the link by code key
	 */
	public LinkedCard getCombat(String key){
		return getByKey(key);
	}
	/**
	 * 
	 * @param c , the card who do attack
	 * @return the battle of the combat
	 */
	/*public LinkedCard getCombatAttack(Card c){
		return tree.get(c);
		Iterator<LinkedCard> it = tree.values().iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			field = it.next();
			if(field.getCard() == c)
				found = true;
		}
		if(!found)
			return null;
		return field;
	}*/
	/**
	 * 
	 * @param c , the card
	 * @return the battle where there is the card
	 */
	public LinkedCard getCombatDefense(Card c){
		LinkedCard field = null;
		Iterator<LinkedCard> it = tree.values().iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			field = it.next();
			if(field.foundCard(c) != null)
				found = true;
		}
		if(!found)
			return null;
		return field;
	}
	/**
	 * @param actionPermanent the actionPermanent to set
	 */
	public void setActionPermanent(PermanentAction actionPermanent) {
		this.actionPermanent = actionPermanent;
	}
	/**
	 * 
	 * @param c the card
	 * @return if can remove the card by id
	 */
	public boolean removeCard(Card c){
		Iterator<LinkedCard> it = tree.values().iterator();
		boolean found = false;
		LinkedCard lin = null;
		while(!found && it.hasNext()){
			lin = it.next();
			if(lin.getCard() == c){
				tree.values().remove(lin);
				found = true;
				
				
				(Judge.getInstance()).setChanged();
				(Judge.getInstance()).notifyRemoveCombat(c);
			}
			else{
				found = lin.removeCard(c);
				if(found){
					(Judge.getInstance()).setChanged();
					(Judge.getInstance()).notifyRemoveFromCombat(lin.getCard(),c);
				}
			}
		}
		return found;
	}
	/**
	 * 
	 * @return values
	 */
	public TreeMap<Card, LinkedCard> getValues(){
		return tree;
	}
}
