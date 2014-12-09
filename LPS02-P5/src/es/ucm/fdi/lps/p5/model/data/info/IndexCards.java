package es.ucm.fdi.lps.p5.model.data.info;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.cards.Card.PlayCard;




public class IndexCards implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, LinkedCard> index = new HashMap<String, LinkedCard>();
	/**
	 * default builder
	 */
	public IndexCards() {
	}
	/**
	 * @param key , key of the card
	 * @return the card by code
	 */
	public Card getCard(String key) {
		LinkedCard lin = index.get(key);
		if(lin != null){	
			return lin.getCard();
		}
		return null;
	}
	/**
	 * add card in the hand
	 * @param c , card to insert
	 */
	public void insert(Card c) {
		LinkedCard aux = index.get(c.getCode());
		if(aux  == null){
			aux = new LinkedCard();
			aux.insert(c);
			index.put(c.getCode(), aux);
		}
		else
			index.get(c.getCode()).insert(c);
	}
	/**
	 * insert the card at first element
	 * @param c
	 */
	public void insertFirst(Card c) {
		LinkedCard aux = index.get(c.getCode());
		if(aux  == null){
			aux = new LinkedCard();
			aux.insert(c);
			index.put(c.getCode(), aux);
		}
		else
			index.get(c.getCode()).insertFirst(c);
	}
	/**
	 * @param pos , position of the card
	 * @param key , key of the card
	 * @return the card the card by key into poasition
	 */
	public Card getCard(int pos,String key){
		LinkedCard link = index.get(key);
		if(link != null && link.size() > pos)
			return link.getCard(pos);
		return null;
	}
	/**
	 * 
	 * @return the iterator of al elements
	 */
	public Iterator<LinkedCard> getAll(){
		return index.values().iterator();
	}
	/**
	 * @return return the Index into string
	 */
	public String toString() {
		String info = "";
		for (LinkedCard lin : index.values()) {
			info += "\r\n-----------------------------------------------------";
			info = info + "\r\n" + lin.toString();
		}
		return info;
	}
	/**
	 * @return return the index into string by game format
	 */
	public String toStringGame() {
		String info = "";
		for (LinkedCard lin : index.values()) {
			info += "\r\n-----------------------------------------------------";
			info = info + "\r\n" + lin.toStringGame();
		}
		return info;
	}
	/**
	 * remove the card by key
	 * @param key , key of the card
	 * @return the card
	 */
	public Card removeCard(String key) {
		LinkedCard stack = index.get(key);
		if(stack == null)
			return null;
		Card c = stack.removeTop();
		if(stack.isEmpty())
			index.remove(key);
		return c;
	}
	/**
	 * remove the card by id
	 * @param c , the card to remove
	 * @return the card or null if not found
	 */
	public Card removeCard(Card c){
		LinkedCard lin = index.get(c.getCode());
		if(lin != null){
			lin.found(c);
			if(!lin.found(c)){
				return null;
			}
			lin.removeCard(c);
			if(lin.isEmpty())
				index.remove(c.getCode());
		}
		else{
			return null;
		}
		return c;
	}
	/**
	 * remove the card into the position
	 * @param pos , the position of the card
	 * @return the card into position
	 */
	public Card removeCard(int pos){
		if(pos < 0 || pos > index.size())
			return null;
		if(index.isEmpty())
			return null;
		Iterator<LinkedCard> it = index.values().iterator();
		Card c = null;
		int aux = 0;
		while(it.hasNext() && aux < pos){
			it.next();
			aux++;
		}
		LinkedCard lin = it.next();
		c = lin.removeTop();
		if(lin.isEmpty())
			index.remove(c.getCode());
		return c;
	}
	/**
	 * 
	 * @return the size of index card
	 */
	public int getNumCards(){
		int num = 0;
		for(LinkedCard lin : index.values()){
			num += lin.size();
		}
		return num;
	}
	/**
	 * 
	 * @return the num of card which same code
	 */
	public int getNumCardsEquals(){
		return index.size();
	}
	/**
	 * 
	 * @param key , the key
	 * @return the numbers of cards with equals key
	 */
	public int getNumCardsEquals(String key){
		return index.get(key).size();
	}
	/**
	 * turn off all card into index
	 */
	public void turnOff(){
		for(LinkedCard lin : index.values()) {
			lin.iniCards();
		}
	}
	/**
	 * turn off all card into index
	 */
	public void iniBuff(){
		for(LinkedCard lin : index.values()) {
			lin.iniBuff();
		}
	}
	/**
	 * unDo all action of the cards into index
	 */
	public void unDone(){
		for(LinkedCard lin : index.values()) {
			lin.unDone();
		}
	}
	/**
	 * rotate the card of the link by key
	 * @param key
	 */
	public void rotate(String key){
		LinkedCard lin = index.get(key);
		Card c = lin.removeTop();
		lin.insert(c);
	}
	/**
	 * modifiers all stats of cards in hashmap
	 * @param at attack value
	 * @param def defense value
	 * @return false if is empty or true if not
	 */
	public boolean modStatsAllCards(int at,int def){
		if(index.isEmpty())
			return false;
		ArrayList<Card> aux = getAllCards();
		
		for(Card c : aux){
			/*if(die){
				c.loseLife(def);
				if(c.isDie()){
					w.dieInGame(c);
				}
			}*/
			//else
				c.modStats(at, def);
		}
		
		return true;
	}
	/**
	 * 
	 * @return the numbers of card which is turn
	 */
	public int getNumTurnCard(){
		int count = 0;
		for(LinkedCard lin : index.values()){
			count += lin.getNumTurnCard();
		}
		return count;
	}
	/**
	 * 
	 * @param elem the element
	 * @return all card which element is elem
	 */
	public ArrayList<Card> getCardsElement(Element elem){
		ArrayList<Card> cards =  new ArrayList<Card>();
		for(LinkedCard lin : index.values()){
			if(lin.getCard().getElement() == elem){
				cards.addAll(lin.getValues());
			}
		}
		return cards;
	}
	/** 
	 * 
	 * @return the values of hashmap in Collection
	 */
	public Collection<LinkedCard> getValues(){
		return index.values();
	}
	/**
	 * 
	 * @return the values by copy of hashmap in ArrayList
	 */
	public ArrayList<Card> getValuesCopy(){
		ArrayList<Card> aux =  new ArrayList<Card>();
		LinkedList<Card> auxLink;
		for(LinkedCard link : index.values()){
			auxLink = link.getValues();
			for(Card c : auxLink){
				aux.add(c);
			}
		}
		return aux;
	}
	/**
	 * 
	 * @return return all entities into arraylist of linkedlist
	 */
	public ArrayList<LinkedCard> getAllEntities(){
		ArrayList<LinkedCard> cards = new ArrayList<LinkedCard>();
		for(LinkedCard link : index.values()){
			if(link.getCard().insert(false, false) == PlayCard.goGame){
				cards.add(link);
			}
		}
		return cards;
	}
	/**
	 * 
	 * @return return all cards into arraylist of linkedlist
	 */
	public ArrayList<Card> getAllCards(){
		ArrayList<Card> cards = new ArrayList<Card>();
		for(LinkedCard link : index.values()){
			cards.addAll(link.getValues());
		}
		return cards;
	}
	/**
	 * remove all card which code equals to key
	 * @param key the key of the cards
	 * @return the linkedCard which all cards
	 */
	public Collection<Card> removeAllCard(String key){
		return index.remove(key).getValues();
	}
	/**
	 * insert all card in link into hasmap
	 * @param link the linkedlist with card
	 */
	public void insertAllCardsCode(LinkedCard link){
		String key = link.getCard().getCode();
		LinkedCard auxLink = index.get(key);
		if(auxLink == null){
			index.put(key, link);
		}
		else{
			LinkedList<Card> aux = link.getValues();
			for(Card c : aux){
				auxLink.insert(c);
			}
		}
	}
	/**
	 * 
	 * @param c the card by id
	 * @return if there is in hashmap or not
	 */
	public boolean found(Card c){
		if(c == null){
			return false;
		}
		LinkedCard link = index.get(c.getCode());
		if(link == null){
			return false;
		}
		return link.found(c);
	}
}
