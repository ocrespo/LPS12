/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.PlayCard;




/**
 * @author Roni
 *
 */
public class StackCard implements Serializable,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack<Card> stack = new Stack<Card>();
	
	/**
	 * Create a new stack
	 */
	public StackCard(){
	}
	/**
	 * add card into stack
	 * @param c , a card
	 */
	public void insert(Card c){
		stack.push(c);
	}
	/**
	 * insert all info into stack
	 * @param info the info to add
	 */
	public void insertAll(Collection<Card> info){
		stack.addAll(info);
	}
	/**
	 * 
	 * @return the first card in stack
	 */
	public Card getTop(){
		if(stack.isEmpty())
			return null;
		return stack.peek();
	}
	/**
	 * 
	 * @param key , code of the card
	 * @param pos , position of the equals cards
	 * @return the card
	 */
	public Card getCard(String key,int pos){
		Iterator<Card> it = stack.iterator();
		ArrayList<Card> cardsEquals = new ArrayList<Card>();
		Card aux = null;
		while(it.hasNext()){
			aux = it.next();
			if(aux.getCode().equals(key))
				cardsEquals.add(aux);
		}
		return cardsEquals.get(pos);
	}
	/**
	 * 
	 * @param c the card
	 * @return if the card is into stack
	 */
	public boolean found(Card c){
		boolean found = false;
		Iterator<Card> it = stack.iterator();
		while(!found && it.hasNext()){
			if(it.next() == c){
				found = true;
			}
		}
		return found;
	}
	/**
	 * 
	 * @param c the card
	 * @return if the card is into stack
	 */
	public Card foundCard(Card c){
		boolean found = false;
		Iterator<Card> it = stack.iterator();
		Card aux = null;
		while(!found && it.hasNext()){
			aux = it.next();
			if(aux.getId() == c.getId()){
				found = true;
			}
		}
		if(!found){
			return null;
		}
		return aux;
	}
	/**
	 * 
	 * @param key , the code of the card
	 * @return numbers of equals cards by the key
	 */
	public int getNumCardsEquals(String key){
		Iterator<Card> it = stack.iterator();
		int count = 0;
		Card aux = null;
		while(it.hasNext()){
			aux = it.next();
			if(aux.getCode().equals(key))
				count++;
		}
		
		return count;
	}
	/**
	 * return if the stack is empty ir not
	 * @return boolean
	 */
	public boolean isEmpty(){
		return stack.isEmpty();
	}
	/**
	 * remove the card in top of the stack
	 * @return the card
	 */
	public Card removeTop(){
		if(stack.isEmpty())
			return null;
		return stack.pop();
	}
	/**
	 * the stack into string
	 */
	public String toString(){
		String acu = "";
		for(Card c : stack){
			acu += c.toString()+"\r\n----------------------------------";
		}
		return acu;
	}
	/**
	 * 
	 * @return Arraylist of card which all entities in stack
	 */
	public ArrayList<Card> getAllEntities(){
		ArrayList<Card> cards =  new ArrayList<Card>();
		for(Card c : stack){
			if(c.insert(false, false) == PlayCard.goGame){
				cards.add(c);
			}
		}
		
		return cards;
	}
	/**
	 * fill the stack
	 */
	public void fill(){
		Collections.shuffle(stack);
	}
	/**
	 * remove the card by id
	 * @param c the card
	 * @return if it was remove
	 */
	public boolean removeCard(Card c){
		return stack.remove(c);
	}
	/**
	 * 
	 * @return the size of stack
	 */
	public int size(){
		return stack.size();
	}
	/**
	 * 
	 * @return all values
	 */
	public Stack<Card> getValues(){
		return stack;
	}
	/**
	 * clone
	 */
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
            ((StackCard)obj).stack = (Stack<Card>) this.stack.clone();
        }catch(CloneNotSupportedException ex){
            //System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}
