package es.ucm.fdi.lps.p6.model.game;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import es.ucm.fdi.lps.p6.DAO.DTOCard;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.game.Model.Phase;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection.Notify;


public class Observed {
	
	private transient ArrayList<ObjectOutputStream> observers = new ArrayList<ObjectOutputStream>();
	private boolean changed = false;
	
	/**
	 * add new observer
	 * @param o the observer
	 */
	public void add(ObjectOutputStream o){
		observers.add(o);
	}
	/**
	 * add all observers into collection
	 * @param o arraylist which observers
	 */
	public void add(ArrayList<ObjectOutputStream> o){
		observers.addAll(o);
	}
	/**
	 * remove a observer
	 * @param o the observer
	 */
	public void remove(ObjectOutputStream o){
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
	public ArrayList<ObjectOutputStream> getObservers(){
		return observers;
	}
	/**
	 * notify all observers that upsateNewCard
	 */
	public void notifyNewHandCard(Card c,Warlock w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){

				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateNewHandCard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//o.(c,w);
			}
			changed = false;
		}
	}
	/**
	 * notify that card go into mana zone
	 */
	public void notifyPlayCardMana(Card c,Warlock w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updatePlayCardMana, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
		
	}
	/**
	 * notifiy that card go into entity zone
	 */
	public void notifyPlayCardEntity(Card c,Warlock w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
			
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updatePlayCardEntity, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
		
	}
	/**
	 * notify error into card
	 * @param er the info
	 */
	public void notifyErrorCard(String er,int w){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(er);
		try {
			observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateErrorCard, aux));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * notify info into card
	 * @param er the info
	 */
	public void notifyInfoInCard(String er,int w){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(er);
		try {
			observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateInfoInCard, aux));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * update de actually phase
	 * @param phase the actually pahse
	 */
	public void notifyPhase(Phase phase){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(phase);
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updatePhase, aux));
				} catch (IOException e) {
				}
			}
			changed = false;
		}
	}
	/**
	 * notify play intervention
	 */
	public void notifyPlayCardIntervention(Card c) {
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){

				try {
					o.writeObject(new TransferObjectConection(null,Notify.updatePlayCardIntervention, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
		
	}
	/**
	 * notufy rotate game
	 * @param allTurn if change global turn
	 */
	public void notifyRotateTurn(){
		if(changed){
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRotate, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notufy rotate game
	 * @param allTurn if change global turn
	 */
	public void notifyRotateAll(boolean allTurn,boolean show,int w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(allTurn);
			aux.add(show);
			try {
				observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateRotateAll, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		changed = false;
		}
	}
	/**
	 * notufy rotate game
	 * @param allTurn if change global turn
	 */
	public void notifyAct(int w,Warlock wId){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add((Warlock)wId.clone());
		try {
			observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateAct, aux));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * notufy rotate game
	 * @param allTurn if change global turn
	 */
	public void notifyChangeWarlockTurn(int wIdTurn){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(wIdTurn);
			for(ObjectOutputStream o : observers){

				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateChangeWarlockTurn, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		changed = false;
		}
	}
	
	/**
	 * notify finish intervention
	 */
	public void notifyFinishIntervention(){
		if(changed){
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateFinishIntervention, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveEntity, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){

				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateDieEntity, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateStatsCard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add((Warlock)wa.clone());
			aux.add((Warlock)wb.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateNewWarlocks, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateAddGameEntity, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateAddManaCard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveManaCard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateAddGraveyard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
			
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveGraveyard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveHandCard, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify refresh warlock´s stats
	 * @param w warlock
	 */
	public void notifyRefreshStatsWarlock(Warlock w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRefreshStatsWarlock, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateDiscar, aux));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	
	/**
	 * notify insert new combat
	 */
	public void notifyInsertNewCombat(Card c ,Warlock w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateInsertNewCombatWarlock, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify insert new combat
	 */
	public void notifyInsertNewCombat(Card c){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateInsertNewCombat, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify insert card in exist combat, its going to add how defender
	 */
	/*public void notifyInsertInCombat(Card at,Card c ,Warlock w){
		if(changed){
			for(ObjectOutputStream o : observers){
				ArrayList<Object> aux = new ArrayList<Object>();
				aux.add(at);
				aux.add(c);
				aux.add(w);
				try {
					o.writeObject(new TransferObjectConection(Notify.updateInsertInCombatWarlock, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}*/
	/**
	 * notify insert card in exist combat, its going to add how defender
	 */
	public void notifyInsertInCombat(Card at,Card c){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(at));
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateInsertInCombat, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(at));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveCombat, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(at));
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveFromCombat, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(at));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveDefenders, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify need to deal mana
	 * @param count
	 */
	public void notifyDealMana(int count,Card c){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(count);
		aux.add(new DTOCard(c));
		for(ObjectOutputStream o : observers){
			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateDealMana, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify finish deal mana
	 */
	public void notifyFinishDealMana(){
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateFinishDealMana, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify decrease the count of mana to need deal
	 */
	public void notifyDecreDealMana(Card c){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateDecreDealMana, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify need to deal damage between defenders
	 * @param count the damana to deal
	 */
	public void notifyDealDamage(int count){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(count);
		for(ObjectOutputStream o : observers){
			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateDealDamage, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify solve the combat
	 */
	public void notifySolveCombat(){
		if(changed){
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateSolveCombat, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify solve the next combat
	 */
	public void notifyNextCombat(){
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateNextCombat, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify finished solve combat
	 */
	public void notifyFinishSolverCombat(){
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateFinishSolveCombat, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify information to show in solve combat
	 * @param text the information
	 */
	public void notifyInfoSolveCombat(String text){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(text);
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateInfoSolveCombat, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify finish to deal damanage
	 */
	public void notifyFinishDealDamage(){
		if(changed){
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateFinishDealDamage, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify a error yo show in solve combat
	 * @param text the info about error
	 */
	public void notifyErrorSolveCombat(String text){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(text);
		for(ObjectOutputStream o : observers){
			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateErrorSolveCombat, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify to decreade de count of damage which its necesarry to deal
	 */
	public void notifyDecreaseSolvecombat(){
		if(changed){
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateDecreaseSolvecombat, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify go to discard hand´s card
	 * @param count the number of card which its necesarry to discard
	 */
	public void notifyGoDiscard(int count,int w){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(count);
		try {
			observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateGoDiscard, aux));
		} catch (IOException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * notify finish discard hand´s card
	 */
	public void notifyFinishDiscard(int w){
		if(changed){
			try {
				observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateFinishDiscard, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		changed = false;
		}
	}
	/**
	 * notify go to exchange enviroment
	 */
	public void notifyGoExchangeEnviroment(int w){
		try {
			observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateGoExchangeEnviroment, null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * notify that now can exchange enviroments
	 */
	public void notifyCanExchangeEnviroment(int w){
		try {
			observers.get(w).writeObject(new TransferObjectConection(null,Notify.updateCanExchangeEnviroment, null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * notify solve interventions
	 */
	public void notifySolveIntervention(){
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateSolveIntervention, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify solve interventions
	 */
	public void notifyNextIntervention(){
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateNextIntervention, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * notify finish solve intervention
	 */
	public void notifyFinishSolveIntervention(){
		if(changed){
			for(ObjectOutputStream o : observers){
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateFinishSolveIntervention, null));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(text);
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateInfoSolveIntervention, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify use active action
	 */
	public void notifyUseActive(Card c,Warlock w){
		if(changed){
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			aux.add((Warlock)w.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateUseActive, aux));
				} catch (IOException e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(winner);
			aux.add(text);
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateFinishPlay, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(warlock);
			aux.add(damage);
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateManafire, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(warlock);
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateNotCardsDeck, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateRemoveIntervention, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add((Warlock)wa.clone());
			aux.add((Warlock)wb.clone());
			aux.add((Warlock)wTurn.clone());
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateAll, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(new DTOCard(c));
			for(ObjectOutputStream o : observers){
				
				try {
					o.writeObject(new TransferObjectConection(null,Notify.updateAddCardIntervention, aux));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			changed = false;
		}
	}
	/**
	 * notify a critical error
	 * @param text the information about error
	 */
	public void notifyErrorFinish(String text){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(text);
		for(ObjectOutputStream o : observers){
			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateErrorFinish, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * notify a critical error
	 * @param text the information about error
	 */
	public void notifyStart(){
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateStart, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void notifyPlayersLobby(String nameA,String dirA, String nameB, String dirB){
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(nameA);
		aux.add(dirA);
		aux.add(nameB);
		aux.add(dirB);
		for(ObjectOutputStream o : observers){
			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updatePlayersLobby, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void notifyFinisLoad() {	
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateFinishLoad, null));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void notifyShowEnemyHand(Warlock w) {	
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(w);
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateShowEnemyHand, aux));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void notifyFinish() {	
		for(ObjectOutputStream o : observers){
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateFinish, null));
			} catch (IOException e) {
			}
		}
	}
	public void notifyError(String text) {	
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(text);
		for(ObjectOutputStream o : observers){			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateError, aux));
			} catch (IOException e) {
			}
		}
	}
	public void notifiChatText(String text) {	
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(text);
		for(ObjectOutputStream o : observers){			
			try {
				o.writeObject(new TransferObjectConection(null,Notify.updateChatText, aux));
			} catch (IOException e) {
			}
		}
	}
}
