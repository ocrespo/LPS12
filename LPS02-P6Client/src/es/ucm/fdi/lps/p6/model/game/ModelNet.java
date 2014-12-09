/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import es.ucm.fdi.lps.p6.DAO.DAOError;
import es.ucm.fdi.lps.p6.DAO.DTOCard;
import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.FileError;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.game.Model.Phase;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection.Actions;
import es.ucm.fdi.lps.p6.model.game.connection.Lobby;
import es.ucm.fdi.lps.p6.model.game.connection.Server;
import es.ucm.fdi.lps.p6.model.game.observed.ObservedGUI;

/**
 * @author Roni
 *
 */
public class ModelNet extends ObservedGUI implements  InterfazModel{
	
	private ObjectOutputStream out;
	private Socket socket;
	private Lobby lob;
	private Server server;
	
	public ModelNet(Lobby lob){
		this.lob = lob;
	}
	/**
	 * ini conection
	 * @param ip the i
	 * @param name`the name of player
	 */
	public void openConection (String ip ,String name){
		try {
			String file = (Configuration.getInstance()).readAllFile((Configuration.getInstance()).getDirA());
			socket = new Socket(ip, 5557);
			 
			InputStream entrada = socket.getInputStream();
			OutputStream salida = socket.getOutputStream();
				
			out = new ObjectOutputStream (salida);
			ObjectInputStream in = new ObjectInputStream (entrada);
			server = new Server(in,this);
			
			
			out.writeObject(name);
			out.writeObject((Configuration.getInstance()).getImageA());
		
			out.writeObject(file);
			 /*try {
				String a = (String) in.readObject();
			 } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			 }*/
			 
			new Thread(server).start();
			 
		} catch (UnknownHostException ex) {
			lob.notifyError("Error de conexion: no se puedo establecer conexión con el servidor");
			System.exit(-1);
		} catch (IOException ex) {
			lob.notifyError("Error de conexion: se produjo un error en la conexión");
			System.exit(-1);
		} catch (FileError e) {
			lob.notifyError(e.getInfo());
			System.exit(-1);
		}
	}
	
	@Override
	public void setwTarget(Warlock wTarget) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(wTarget);
		try {
			out.writeObject(new TransferObjectConection(Actions.playCardWarlock,null,aux));
		} catch (IOException e) {
		}
	}

	@Override
	public void iniModel() {	
	}

	@Override
	public void iniWarlock() throws IOException, Exception, FileError, DAOError {	
	}

	@Override
	public void playCard(Card c, Card tar) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(new DTOCard(c));
		if(tar != null)
			aux.add(new DTOCard(tar));
		else
			aux.add(tar);
		try {
			out.writeObject(new TransferObjectConection(Actions.playCard,null, aux));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void useAction(Card c, Card tar) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(new DTOCard(c));
		if(tar != null)
			aux.add(new DTOCard(tar));
		else
			aux.add(tar);
		try {
			out.writeObject(new TransferObjectConection(Actions.useAction,null, aux));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void attack(Card c) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(new DTOCard(c));
		try {
			out.writeObject(new TransferObjectConection(Actions.attack,null, aux));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void defense(Card tar, Card c) {
		ArrayList<Object> aux = new ArrayList<Object>();
		if(tar != null)
			aux.add(new DTOCard(tar));
		else
			aux.add(tar);
		aux.add(new DTOCard(c));
		try {
			out.writeObject(new TransferObjectConection(Actions.defense,null, aux));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void next() {
		try {
			out.writeObject(new TransferObjectConection(Actions.nextPhase,null, null));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void nextSolveIntervention() {
		try {
			out.writeObject(new TransferObjectConection(Actions.nextSolveIntervention,null, null));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void payDeal(Element e, Card c) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(e);
		aux.add(new DTOCard(c));
		try {
			out.writeObject(new TransferObjectConection(Actions.payDealMana,null, aux));
		} catch (IOException a) {
		}
		
	}

	@Override
	public void nextSolveCombat() {
		try {
			out.writeObject(new TransferObjectConection(Actions.nextCombat,null, null));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void dealDamage(Card c) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(new DTOCard(c));
		try {
			out.writeObject(new TransferObjectConection(Actions.dealDamage,null, aux));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void passDeal() {
		try {
			out.writeObject(new TransferObjectConection(Actions.passDeal,null, null));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void discar(Card c) {
		ArrayList<Object> aux = new ArrayList<Object>();
		aux.add(new DTOCard(c));
		try {
			out.writeObject(new TransferObjectConection(Actions.discard,null, aux));
		} catch (IOException e) {
		}
		
	}

	@Override
	public void saveGame(String dir) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadGame(String dir) {
		// TODO Auto-generated method stub
		
	}
	public void ready() {
		try {
			out.writeObject(new TransferObjectConection(Actions.ready,null, null));
		} catch (IOException e) {
		}
		
	}
	public void start() {
		try {
			out.writeObject(new TransferObjectConection(Actions.start,null, null));
		} catch (IOException e) {
		}
		
	}
	/**
	 * go to finish played
	 */
	public void goFinish() {
		try {
			out.writeObject(new TransferObjectConection(Actions.finish,null, null));
		} catch (IOException e) {
		}
		
	}
	/**
	 * send text to chat
	 * @param text
	 */
	public void sendChat(String text) {
		try {
			ArrayList<Object> aux = new ArrayList<Object>();
			aux.add(text);
			out.writeObject(new TransferObjectConection(Actions.sendText,null, aux));
		} catch (IOException e) {
		}
		
		
	}
	/**
	 * finish play
	 */
	public void finish(){
		try {
			server.stop();
			socket.close();		
		} catch (IOException e) {
		}
		System.exit(0);
	}
	/**
	 * do action by server
	 * @param trans the info about action
	 */
	 public void doneActionServer(TransferObjectConection trans){
		 ArrayList<Object> arg = trans.getArg();
		 setChanged();
		 Card c = null;
		 Card at = null;
		 switch (trans.getNotify()) {
			case updateNewHandCard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyNewHandCard(c,(Warlock) arg.get(1));
				break;
				
			case updatePlayCardMana:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyPlayCardMana(c,(Warlock) arg.get(1));
				break;
				
			case updatePlayCardEntity:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyPlayCardEntity(c,(Warlock) arg.get(1));
				break;
				
			case updatePlayCardIntervention:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyPlayCardIntervention(c);
				break;
				
			case updateErrorCard:
				notifyErrorCard((String)arg.get(0));
				break;
				
			case updateInfoInCard:
				notifyInfoInCard((String)arg.get(0));
				break;
				
			case updatePhase:
				notifyPhase((Phase)arg.get(0));
				break;
				
			case updateRotate:
				notifyChangePhase();
				break;
				
			case updateFinishIntervention:
				notifyFinishIntervention();
				break;
			
			case updateRemoveEntity:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveEntity(c,(Warlock) arg.get(1));
				break;
				
			case updateDieEntity:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyDieEntity(c,(Warlock) arg.get(1));
				break;
				
			case updateStatsCard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRefreshStatsCard(c);
				break;
				
			case updateNewWarlocks:
				notifyNewWarlocks((Warlock)arg.get(0),(Warlock) arg.get(1));
				break;
			
			case updateAddGameEntity:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyAddGameEntity(c,(Warlock) arg.get(1));
				break;
				
			case updateAddManaCard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyAddManaCard(c,(Warlock) arg.get(1));
				break;
			case updateRemoveManaCard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveManaCard(c,(Warlock) arg.get(1));
				break;
			case updateAddGraveyard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyAddGraveyard(c,(Warlock) arg.get(1));
				break;
			case updateRemoveGraveyard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveGraveyard(c,(Warlock) arg.get(1));
				break;
			case updateRemoveHandCard:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveHandCard(c,(Warlock) arg.get(1));
				break;
			case updateRefreshStatsWarlock:
				notifyRefreshStatsWarlock((Warlock) arg.get(0));
				break;
			case updateDiscar:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyDiscar(c,(Warlock) arg.get(1));
				break;
			case updateDealMana:
				c = CardFactory.getCard((DTOCard)arg.get(1),true);
				notifyDealMana((Integer)arg.get(0),c);
				break;
			case updateFinishDealMana:
				notifyFinishDealMana();
				break;
			case updateDecreDealMana:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyDecreDealMana(c);
				break;
			case updateInsertNewCombat:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyInsertNewCombat(c);
				break;
			case updateInsertNewCombatWarlock:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyInsertNewCombat(c,(Warlock)arg.get(1));
				break;
			case updateInsertInCombat:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				at = CardFactory.getCard((DTOCard)arg.get(1),true);
				notifyInsertInCombat(c,at);
				break;
			case updateRemoveCombat:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveCombat(c);
				break;
			case updateRemoveFromCombat:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				at = CardFactory.getCard((DTOCard)arg.get(1),true);
				notifyRemoveFromCombat(c,at);
				break;

			case updateRemoveDefenders:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveDefenders(c);
				break;
			case updateDealDamage:
				notifyDealDamage((Integer)arg.get(0));
				break;
			case updateSolveCombat:
				notifySolveCombatNet();
				break;
			case updateNextCombat:
				notifyNextCombat();
				break;
			case updateFinishSolveCombat:
				notifyFinishSolverCombatNet();
				break;
			case updateInfoSolveCombat:
				notifyInfoSolveCombat((String)arg.get(0));
				break;
			case updateFinishDealDamage:
				notifyFinishDealDamage();
				break;
			case updateErrorSolveCombat:
				notifyErrorSolveCombat((String)arg.get(0));
				break;
			case updateDecreaseSolvecombat:
				notifyDecreaseSolvecombat();
				break;
			case updateGoDiscard:
				notifyGoDiscard((Integer)arg.get(0));
				break;
			case updateFinishDiscard:
				notifyFinishDiscard();
				break;
			case updateGoExchangeEnviroment:
				notifyGoExchangeEnviroment();
				break;
			case updateCanExchangeEnviroment:
				notifyCanExchangeEnviroment();
				break;
			case updateSolveIntervention:
				notifySolveInterventionNet();
				break;
			case updateNextIntervention:
				notifyNextIntervention();
				break;
			case updateFinishSolveIntervention:
				notifyFinishSolveIntervention();
				break;
			case updateInfoSolveIntervention:
				notifyInfoSolveIntervention((String)arg.get(0));
				break;
			case updateUseActive:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyUseActive(c,(Warlock)arg.get(1));
				break;
			case updateFinishPlay:
				notifyFinishPlay((String)arg.get(0), (String)arg.get(1));
				break;
			case updateManafire:
				notifyManafire((String)arg.get(0), (Integer)arg.get(1));
				break;
			case updateNotCardsDeck:
				notifyNotCardsDeck((String)arg.get(0));
				break;
			case updateRemoveIntervention:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyRemoveIntervention(c);
				break;
			case updateAll:
				notifyAll((Warlock) arg.get(0), (Warlock) arg.get(1), (Warlock) arg.get(2));
				break;
			case updateAddCardIntervention:
				c = CardFactory.getCard((DTOCard)arg.get(0),true);
				notifyAddCardIntervention(c);
				break;
			case updateErrorFinish:
				notifyErrorFinish((String)arg.get(0));
				break;
			case updateRotateAll:
				notifyRotateTurn((Boolean)arg.get(0), (Boolean)arg.get(1),false);
				break;
			case updateChangeWarlockTurn:
				notifyChangeWarlockTurn((Integer)arg.get(0));
				break;
			case updateStart:
				lob.notifyStart();
				break;
			case updatePlayersLobby:
				lob.notifyPlayerLobby((String)arg.get(0), (String)arg.get(1),(String)arg.get(2),(String)arg.get(3));
				break;
			case updateFinishLoad:
				notifyFinishLoad();
				break;
			case updateAct:
				notifyAct((Warlock)arg.get(0));
				break;
			case updateShowEnemyHand:
				notifyShowEnemyHand((Warlock)arg.get(0));
				break;	
			case updateFinish:
				finish();
				break;
			case updateError:
				lob.notifyError((String)arg.get(0));
				notifyError((String)arg.get(0));
				finish();
				break;
				
			case updateChatText:
				notifyChatText((String)arg.get(0));
				break;
				
				
			/*case updateInsertInCombatWarlock:
				m.notifyInsertIn
				break;*/
				
				

			}
	 }
	


	
}
