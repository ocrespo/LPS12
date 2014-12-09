package es.ucm.fdi.lps.p6.model.data.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.cards.Card.PlayCard;




// CLASS DECK
public class LinkedCard implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Card> link = new LinkedList<Card>();
	
	public LinkedCard(){
	}
	/**
	 * @return the first card
	 */
	public Card getCard() {
		return link.peekFirst();
	}
	/**
	 * 
	 * @return the last card
	 */
	public Card getLastCard(){
		return link.getLast();
	}
	/**
	 * 
	 * @return if is empty
	 */
	public boolean isEmpty(){
		return link.isEmpty();
	}
	/**
	 * 
	 * @return the size
	 */
	public int size(){
		return link.size();
	}
	/**
	 * remove the first card
	 */
	public Card removeTop() {
		if(!link.isEmpty())
			return link.remove();
		return null;
	}
	/**
	 * remove card by id
	 * @param c , the card to remove
	 * @return the card
	 */
	public boolean removeCard(Card c){
		for(Card aux : link){
			if(c.getId() == aux.getId()){
				link.remove(aux);
				return true;
			}
		}
		return false;
		//return link.remove(c);
	}
	/**
	 * 
	 * @param pos , position
	 * @return card into the position pos
	 */
	public Card getCard(int pos){
		return link.get(pos);
	}
	/**
	 * 
	 * @param key
	 * @return the card by key.
	 */
	public Card getCard(String key){
		Iterator<Card> it = link.iterator();
		Card c = null;
		while(it.hasNext()){
			c = it.next();
			if(c.getCode().equals(key))
				return c;
		}
		return null;		
		
	}
	/**
	 * 
	 * @param key the key of card
	 * @param pos the position of the equals card
	 * @return the card or null if not exist
	 */
	public Card getCard(String key,int pos){
		Iterator<Card> it = link.iterator();
		Card c = null;
		int acu = 1;
		while(it.hasNext()){
			c = it.next();
			if(c.getCode().equals(key) && acu == pos)
				return c;
			else if(c.getCode().equals(key))
				acu++;
		}
		return null;		
		
	}
	
	/**
	 * insert card of the last position
	 * @param c , card to insert
	 */
	public boolean insert(Card c) {
		if(link.size()==(Configuration.getInstance()).getMaxDeck())
			return false;
		link.add(c);
		return true;
	}
	/**
	 * insert card in first position
	 * @param c card
	 */
	public void insertFirst(Card c){
		link.addFirst(c);
	}
	/**
	 * fill the linkedList
	 */
	public void fill(){
		Collections.shuffle(link);
	}
	/**
	 * 
	 * @return the link into string in combat format
	 */
	public String toStringCombat(){
		String acu = "";
		Iterator<Card> it = link.iterator();
		acu += "-------ATACA------------\r\n";
		acu += it.next();
		if(it.hasNext()){
			acu += "\r\n--------VS------------\r\n";
			acu += "------DEFIENDE------------\r\n";
			while(it.hasNext()){
				acu += "\r\n--------------------------\r\n";
				acu += it.next().toString();
			}
		}
		else
			acu += "\r\n\r\nNadie le defiende va directo al brujo";
		return acu;
	}
	/**
	 * return the link into string
	 */
	public String toString(){
		return link.size()+"x---\r\n"+link.peek().toString() ;
	}
	/**
	 * 
	 * @return the link into string by game format
	 */
	public String toStringGame(){
		String acu = "";
		int num = 1;
		if(link.size() > 1){
			for(Card c: link){
				acu += "\r\n-----\r\n"+num+"-->"+c.toString() ;
				num++;
			}
		}
		else
			return link.peek().toString();
		return acu;
	}
	/**
	 * turn off all cards in link
	 */
	public void iniCards(){
		for(Card c : link)
			c.ini();
	}
	/**
	 * turn off all cards in link
	 */
	public void iniBuff(){
		for(Card c : link)
			c.iniBuff();
	}
	/**
	 * undo all action of the card in link
	 */
	public void unDone(){
		for(Card c : link)
			c.unDone();
	}
	/**
	 * 
	 * @param c card
	 * @return the card if there is the card by id, or null if not
	 */
	public Card foundCard(Card c){
		Iterator<Card> it = link.iterator();
		boolean found = false;
		Card aux = null;
		while(it.hasNext() && !found){
			aux = it.next();
			if(aux.getId() == c.getId())
				found = true;
		}
		if(!found)
			return null;
		return aux;
			
	}
	/**
	 * 
	 * @param c card
	 * @return the card if there is the card by id, or null if not
	 */
	public boolean found(Card c){
		Iterator<Card> it = link.iterator();
		boolean found = false;
		while(it.hasNext() && !found){
			if(it.next() == c)
				found = true;
		}
		if(!found)
			return false;
		return true;
			
	}
	/**
	 * 
	 * @param key the key
	 * @return the card if there is the card by key, or null if not
	 */
	public Card found(String key){
		Iterator<Card> it = link.iterator();
		boolean found = false;
		Card aux = null;
		while(it.hasNext() && !found){
			aux = it.next();
			if(aux.getCode().equals(key))
				found = true;
		}
		if(!found)
			return null;
		return aux;
	}
	/**
	 * 
	 * @param elem the element which searcj
	 * @return the numbers of card which especific element
	 */
	public int numCardsElements(Element elem){
		int count = 0;
		for(Card c : link){
			if(c.getElement() == elem )
				count++;
		}
		return count;
	}
	/**
	 * 
	 * @return the values into collection
	 */
	public LinkedList<Card> getValues(){
		return link;
	}
	/**
	 * modifiers stats of all card in linkedlist
	 * @param at value of attack
	 * @param def value of defense
	 * @return true if affect anyone entity or false if is empty
	 */
	public boolean modStatsAllCard(int at,int def,boolean die,Warlock w){
		if(link.isEmpty())
			return false;
		for(Card c : link){
			if(die){
				c.loseLife(def);
				if(c.isDie()){
					w.dieInGame(c);
				}
			}
			else
				c.modStats(at, def);
			
		}
		return true;
	}
	/**
	 * 
	 * @return the numbers of card which  is turn
	 */
	public int getNumTurnCard(){
		int count = 0;
		for(Card c : link){
			if(!c.canUse())
				count++;
		}
		return count;
	}
	/**
	 * 
	 * @return ArrayList which all entities in linkedlist
	 */
	public ArrayList<Card> getAllEntities(){
		ArrayList<Card> cards =  new ArrayList<Card>();
		for(Card c : link){
			if(c.insert(false, false) == PlayCard.goGame){
				cards.add(c);
			}
		}
		return cards;
	}
	/**
	 * 
	 * @return ArrayList which all entities in linkedlist and remove this
	 */
	public ArrayList<Card> removeAllEntities(){
		ArrayList<Card> cards =  new ArrayList<Card>();
		for(Card c : link){
			if(c.insert(false, false) == PlayCard.goGame){
				cards.add(c);
				link.remove(c);
			}
		}
		return cards;
	}
	/**
	 * clone
	 */
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
            ((LinkedCard)obj).link = ((LinkedList<Card>) link.clone());
        }catch(CloneNotSupportedException ex){
            //System.out.println(" no se puede duplicar");
        }
        return obj;
    }
	
}// Deck
