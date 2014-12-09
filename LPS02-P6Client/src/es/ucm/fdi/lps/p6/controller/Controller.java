/**
 * 
 */
package es.ucm.fdi.lps.p6.controller;

import java.io.IOException;

import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.game.InterfazModel;
import es.ucm.fdi.lps.p6.model.game.Judge;
import es.ucm.fdi.lps.p6.model.game.Model;
import es.ucm.fdi.lps.p6.model.game.ModelNet;
import es.ucm.fdi.lps.p6.model.game.connection.Lobby;
import es.ucm.fdi.lps.p6.model.game.observed.ObservedGUI;

/**
 * @author Roni
 *
 */
public class Controller {
	private InterfazModel model;
	private Lobby lob;
	
	/**
	 * builder
	 * @param model
	 */
	public Controller(Model model){
		this.model = model;
		(Configuration.getInstance()).generic();
	}
	/**
	 * ini game
	 * @throws IOException
	 * @throws Exception
	 */
	public void iniGame() throws IOException, Exception{
		model.iniWarlock();
	}
	/**
	 * play card
	 * @param c the card
	 * @param tar target
	 */
	public void playCard(Card c,Card tar){
		model.playCard(c,tar);
	}
	/**
	 * play card over warlock target
	 * @param tar target
	 */
	public void playCardWarlock(Warlock tar){
		model.setwTarget(tar);
	}
	/**
	 * add Observer
	 * @param o
	 */
	public void addObserverGUI(ObserverGUI o){
		((ObservedGUI) model).add(o);
		(Judge.getInstance()).add(o);
	}
	/**
	 * remove Observer
	 * @param o
	 */
	public void removeObserverGUI(ObserverGUI o){
		((ObservedGUI) model).remove(o);
		(Judge.getInstance()).remove(o);
	}
	/**
	 * add Observer
	 * @param o
	 */
	public void addObserverNet(ObserverNet o){
		if(lob == null){
			lob = new Lobby();
		}
		lob.add(o);
	}
	/**
	 * remove Observer
	 * @param o
	 */
	public void removeObserverNet(ObserverNet o){
		lob.remove(o);
	}
	/**
	 * use active action
	 * @param c the card to use
	 * @param tar the target
	 */
	public void useAction(Card c,Card tar){
		model.useAction(c, tar);
	}
	/**
	 * attack
	 * @param c card who attack
	 */
	public void attack(Card c){
		model.attack(c);
	}
	/**
	 * defense
	 * @param tar the attacker card
	 * @param c the defense who go defender
	 */
	public void defense(Card tar,Card c){
		model.defense(tar,c);
	}
	/**
	 * go next phase
	 */
	public void nextPhase(){
		model.next();
	}
	/**
	 * pay mana to deal
	 * @param e the element who choose
	 * @param c the card
	 */
	public void payDealMana(Element e,Card c){
		model.payDeal(e, c);
	}
	/**
	 * solve next combat
	 */
	public void nextCombat(){
		model.nextSolveCombat();
	}
	/**
	 * deal damage over entity in combat
	 * @param c the card who take damage
	 */
	public void dealDamage(Card c){
		model.dealDamage(c);
	}
	/**
	 * pass deal damage
	 */
	public void passDeal(){
		model.passDeal();
	}
	/**
	 * discard card
	 * @param c the card
	 */
	public void discard(Card c){
		model.discar(c);
	}
	/**
	 * next solve intervention
	 */
	public void nextSolveIntervention(){
		model.nextSolveIntervention();
	}
	/**
	 * save the game
	 * @param dir file
	 */
	public void saveGame(String dir){
		model.saveGame(dir);
	}
	/**
	 * load the game
	 * @param dir file
	 */
	public void loadGame(String dir){
		model.loadGame(dir);
	}
	/**
	 * validate the configuration
	 * @param minDeck
	 * @param maxDeck
	 * @param hand
	 * @param lifes
	 * @param dirA
	 * @param dirB
	 * @param debug
	 * @param admin
	 * @param imageA
	 * @param imageB
	 * @return
	 * @throws Exception
	 */
	public boolean validateConfig(String minDeck,String maxDeck,int hand,int lifes,String dirA ,String dirB,boolean debug,boolean admin
			,String imageA,String imageB) throws Exception{
		(Configuration.getInstance()).setMinDeck(Integer.parseInt(minDeck)); 
		(Configuration.getInstance()).setMaxDeck(Integer.parseInt(maxDeck));
		(Configuration.getInstance()).setMaxHand(hand);
		(Configuration.getInstance()).setLife(lifes);
		(Configuration.getInstance()).setDirA(dirA);
		(Configuration.getInstance()).setDirB(dirB);
		(Configuration.getInstance()).setImageA(imageA);
		(Configuration.getInstance()).setImageB(imageB);
		(Configuration.getInstance()).setDebug(debug);
		(Configuration.getInstance()).setAdmin(admin);
		if(!(Configuration.getInstance()).validValues() || !dirA.endsWith("brujo") || !dirB.endsWith("brujo")){
			return false;
		}
		return true;
	}
	/**
	 * validate range of card deck
	 * @param text
	 * @throws Exception
	 */
	public void validRange(String text) throws Exception{
		int value = Integer.parseInt(text);
		if(value < 3 || value > 100){
			throw new Exception();
		}
	}
	
	public void modeNet(String ip,String name){
		if(lob == null){
			lob = new Lobby();
		}
		model = new ModelNet(lob);
		((ModelNet) model).openConection(ip,name);
	}
	public void ready(){
		((ModelNet) model).ready();
	}
	public void start(){
		((ModelNet) model).start();
	}
	public void goFinish() {
		((ModelNet) model).goFinish();
		
	}
	public void sendChat(String text){
		((ModelNet) model).sendChat(text);
	}
}
