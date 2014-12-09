package es.ucm.fdi.lps.p5.model.game;

import java.util.ArrayList;

import es.ucm.fdi.lps.p5.controller.Observer;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.game.Model.Phase;


public class Observed {
	private transient ArrayList<Observer> observers = new ArrayList<Observer>();
	private boolean changed = false;
	
	/**
	 * add new observer
	 * @param o the observer
	 */
	public void add(Observer o){
		observers.add(o);
	}
	/**
	 * add all observers into collection
	 * @param o arraylist which observers
	 */
	public void add(ArrayList<Observer> o){
		observers.addAll(o);
	}
	/**
	 * remove a observer
	 * @param o the observer
	 */
	public void remove(Observer o){
		observers.remove(o);
	}
	/**
	 * changed the state of changed to true
	 */
	public void setChanged(){
		changed = true;
	}
	/**
	 * 
	 * @return all observers
	 */
	public ArrayList<Observer> getObservers(){
		return observers;
	}
	/**
	 * notify all observers that upsateNewCard
	 */
	public void notifyNewHandCard(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateNewHandCard(c,w);
			}
			changed = false;
		}
	}
	/**
	 * notify that card go into mana zone
	 */
	public void notifyPlayCardMana(Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updatePlayCardMana(w);
			}
			changed = false;
		}
		
	}
	/**
	 * notifiy that card go into entity zone
	 */
	public void notifyPlayCardEntity(Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updatePlayCardEntity(w);
			}
			changed = false;
		}
		
	}
	/**
	 * notify error into card
	 * @param er the info
	 */
	public void notifyErrorCard(String er){
		for(Observer o : observers){
			o.updateErrorCard(er);
		}	
	}
	/**
	 * notify info into card
	 * @param er the info
	 */
	public void notifyInfoInCard(String er){
		for(Observer o : observers){
			o.updateInfoInCard(er);
		}	
	}
	/**
	 * update de actually phase
	 * @param phase the actually pahse
	 */
	public void notifyPhase(Phase phase){
		if(changed){
			for(Observer o : observers){
				o.updatePhase(phase);
			}
			changed = false;
		}
	}
	/**
	 * notify play intervention
	 */
	public void notifyPlayCardIntervention() {
		if(changed){
			for(Observer o : observers){
				o.updatePlayCardIntervention();
			}
			changed = false;
		}
		
	}
	/**
	 * notufy rotate game
	 * @param allTurn if change global turn
	 */
	public void notifyRotateTurn(boolean allTurn,boolean show){
		if(changed){
			for(Observer o : observers){
				o.updateRotate(allTurn,show) ;
			}
			changed = false;
		}
	}
	/**
	 * notify finish intervention
	 */
	public void notifyFinishIntervention(){
		if(changed){
			for(Observer o : observers){
				o.updateFinishIntervention() ;
			}
			changed = false;
		}
	}
	/**
	 * notify remove entity into game
	 * @param c the id of card
	 */
	public void notifyRemovEntity(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveEntity(c,w) ;
			}
			changed = false;
		}
	}
	/**
	 * notify die entitu
	 * @param c the card
	 */
	public void notifyDieEntity(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateDieEntity(c,w) ;
			}
			changed = false;
		}
	}
	/**
	 * notify change in stats of card
	 * @param c card
	 */
	public void notifyRefreshStatsCard(Card c){
		if(changed){
			for(Observer o : observers){
				o.updateStatsCard(c) ;
			}
			changed = false;
		}
	}
	/**
	 * notify new warlocks
	 * @param wa warlock a
	 * @param wb warlock b
	 */
	public void notifyNewWarlocks(Warlock wa, Warlock wb){
		if(changed){
			for(Observer o : observers){
				o.updateNewWarlocks(wa, wb) ;
			}
			changed = false;
		}
	}
	/**
	 * notify new entity in game
	 * @param c the entity
	 * @param w warlock 
	 */
	public void notifyAddGameEntity(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateAddGameEntity(c, w) ;
			}
			changed = false;
		}
		
	}
	/**
	 * notify new enviroment
	 * @param c the card
	 * @param w warlock
	 */
	public void notifyAddManaCard(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateAddManaCard(c, w) ;
			}
			changed = false;
		}
		
	}
	/**
	 * notify remove mana card
	 * @param c the card
	 * @param w warlock
	 */
	public void notifyRemoveManaCard(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveManaCard(c, w );
			}
			changed = false;
		}
		
	}
	/**
	 * notify new card in graveyard
	 * @param c the card
	 * @param w warlock
	 */
	public void notifyAddGraveyard(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateAddGraveyard(c, w );
			}
			changed = false;
		}
		
	}
	/**
	 * notify remove card into graveyard
	 * @param c the card
	 * @param w the warlock
	 */
	public void notifyRemoveGraveyard(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveGraveyard(c, w );
			}
			changed = false;
		}
		
	}
	/**
	 * notify remove hand card
	 * @param c the card
	 * @param w the warlock
	 */
	public void notifyRemoveHandCard(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveHandCard(c, w) ;
			}
			changed = false;
		}
	}
	/**
	 * notify refresh warlock�s stats
	 * @param w warlock
	 */
	public void notifyRefreshStatsWarlock(Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateRefreshStatsWarlock(w );
			}
			changed = false;
		}
	}
	/**
	 * notify discard card and insert into graveyard
	 * @param c card
	 * @param w warlock
	 */
	public void notifyDiscar(Card c,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateDiscar(c, w) ;
			}
			changed = false;
		}
	}
	/**
	 * notify insert new combat
	 */
	public void notifyInsertNewCombat(){
		if(changed){
			for(Observer o : observers){
				o.updateInsertNewCombat() ;
			}
			changed = false;
		}
	}
	/**
	 * notify insert card in exist combat, its going to add how defender
	 */
	public void notifyInsertInCombat(){
		if(changed){
			for(Observer o : observers){
				o.updateInsertInCombat() ;
			}
			changed = false;
		}
	}
	
	/**
	 * notify insert new combat
	 */
	public void notifyInsertNewCombat(Card c ,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateInsertNewCombat(c,w) ;
			}
			changed = false;
		}
	}
	/**
	 * notify insert card in exist combat, its going to add how defender
	 */
	public void notifyInsertInCombat(Card at,Card c ,Warlock w){
		if(changed){
			for(Observer o : observers){
				o.updateInsertInCombat(at,c,w) ;
			}
			changed = false;
		}
	}
	
	/**
	 * notify remove combat
	 * @param at the attacker
	 */
	public void notifyRemoveCombat(Card at){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveCombat(at) ;
			}
			changed = false;
		}
	}
	/**
	 * notify remove card from combat, only for defender
	 * @param at the attacker
	 * @param c the card to remove
	 */
	public void notifyRemoveFromCombat(Card at, Card c){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveFromCombat(at, c) ;
			}
			changed = false;
		}
	}
	/**
	 * notify remove all defender at combat of at
	 * @param at the attack card
	 */
	public void notifyRemoveDefenders(Card at){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveDefenders(at) ;
			}
			changed = false;
		}
	}
	/**
	 * notify need to deal mana
	 * @param count
	 */
	public void notifyDealMana(int count){
		for(Observer o : observers){
			o.updateDealMana(count) ;
		}
	}
	/**
	 * notify finish deal mana
	 */
	public void notifyFinishDealMana(){
		for(Observer o : observers){
			o.updateFinishDealMana() ;
		}
	}
	/**
	 * notify decrease the count of mana to need deal
	 */
	public void notifyDecreDealMana(){
		if(changed){
			for(Observer o : observers){
				o.updateDecreDealMana() ;
			}
			changed = false;
		}
	}
	/**
	 * notify need to deal damage between defenders
	 * @param count the damana to deal
	 */
	public void notifyDealDamage(int count){
		for(Observer o : observers){
			o.updateDealDamage(count) ;
		}
	}
	/**
	 * notify solve the combat
	 */
	public void notifySolveCombat(){
		if(changed){
			for(Observer o : observers){
				o.updateSolveCombat() ;
			}
			changed = false;
		}
	}
	/**
	 * notify finished solve combat
	 */
	public void notifyFinishSolverCombat(){
		for(Observer o : observers){
			o.updateFinishSolveCombat() ;
		}
	}
	/**
	 * notify information to show in solve combat
	 * @param text the information
	 */
	public void notifyInfoSolveCombat(String text){
		if(changed){
			for(Observer o : observers){
				o.updateInfoSolveCombat(text) ;
			}
			changed = false;
		}
	}
	/**
	 * notify finish to deal damanage
	 */
	public void notifyFinishDealDamage(){
		if(changed){
			for(Observer o : observers){
				o.updateFinishDealDamage() ;
			}
			changed = false;
		}
	}
	/**
	 * notify a error yo show in solve combat
	 * @param text the info about error
	 */
	public void notifyErrorSolveCombat(String text){
		for(Observer o : observers){
			o.updateErrorSolveCombat(text) ;
		}
	}
	/**
	 * notify to decreade de count of damage which its necesarry to deal
	 */
	public void notifyDecreaseSolvecombat(){
		if(changed){
			for(Observer o : observers){
				o.updateDecreaseSolvecombat() ;
			}
			changed = false;
		}
	}
	/**
	 * notify go to discard hand�s card
	 * @param count the number of card which its necesarry to discard
	 */
	public void notifyGoDiscard(int count){
		for(Observer o : observers){
			o.updateGoDiscard(count) ;
		}
	}
	/**
	 * notify finish discard hand�s card
	 */
	public void notifyFinishDiscard(){
		if(changed){
			for(Observer o : observers){
				o.updateFinishDiscard() ;
			}
			changed = false;
		}
	}
	/**
	 * notify go to exchange enviroment
	 */
	public void notifyGoExchangeEnviroment(){
		for(Observer o : observers){
			o.updateGoExchangeEnviroment() ;
		}
	}
	/**
	 * notify that now can exchange enviroments
	 */
	public void notifyCanExchangeEnviroment(){
		for(Observer o : observers){
			o.updateCanExchangeEnviroment() ;
		}
	}
	/**
	 * notify solve interventions
	 */
	public void notifySolveIntervention(){
		for(Observer o : observers){
			o.updateSolveIntervention() ;
		}
	}
	/**
	 * notify finish solve intervention
	 */
	public void notifyFinishSolveIntervention(){
		if(changed){
			for(Observer o : observers){
				o.updateFinishSolveIntervention() ;
			}
			changed = false;
		}
	}
	/**
	 * notify info to show in solve intervention
	 * @param text the info
	 */
	public void notifyInfoSolveIntervention(String text){
		if(changed){
			for(Observer o : observers){
				o.updateInfoSolveIntervention(text) ;
			}
			changed = false;
		}
	}
	/**
	 * notify use active action
	 */
	public void notifyUseActive(){
		if(changed){
			for(Observer o : observers){
				o.updateUseActive() ;
			}
			changed = false;
		}
	}
	/**
	 * notify finish play
	 * @param winner the winner
	 * @param text the phase
	 */
	public void notifyFinishPlay(String winner,String text){
		if(changed){
			for(Observer o : observers){
				o.updateFinishPlay(winner,text) ;
			}
			changed = false;
		}
	}
	/**
	 * notify mana fire
	 * @param warlock the warlock
	 * @param damage the damage
	 */
	public void notifyManafire(String warlock,int damage){
		if(changed){
			for(Observer o : observers){
				o.updateManafire(warlock,damage) ;
			}
			changed = false;
		}
	}
	/**
	 * notify no cards into deck
	 * @param warlock the warlock
	 */
	public void notifyNotCardsDeck(String warlock){
		if(changed){
			for(Observer o : observers){
				o.updateNotCardsDeck(warlock) ;
			}
			changed = false;
		}
	}
	/**
	 * notify remove intervention
	 * @param c the intervention
	 */
	public void notifyRemoveIntervention(Card c){
		if(changed){
			for(Observer o : observers){
				o.updateRemoveIntervention(c) ;
			}
			changed = false;
		}
	}
	/**
	 * notify all game
	 * @param wa warlock A
	 * @param wb warlock B
	 */
	public void notifyAll(Warlock wa, Warlock wb,Warlock wTurn){
		if(changed){
			for(Observer o : observers){
				o.updateAll(wa,wb,wTurn) ;
			}
			changed = false;
		}
	}
	/**
	 * notify add card intervention
	 * @param c the card
	 */
	public void notifyAddCardIntervention(final Card c){
		if(changed){
			for(Observer o : observers){
				o.updateAddCardIntervention(c) ;
			}
			changed = false;
		}
	}
	/**
	 * notify a critical error
	 * @param text the information about error
	 */
	public void notifyErrorFinish(String text){
		for(Observer o : observers){
			o.updateErrorFinish(text) ;
		}
	}
	public void notifyShowEnemyHand(Warlock w){
		for(Observer o : observers){
			o.updateShowEnemyHand(w) ;
		}
	}
}
