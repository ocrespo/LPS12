/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game;

import java.io.Serializable;
import java.util.ArrayList;




/**
 * @author Roni
 *
 */
public class TransferObjectConection implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum Actions{playCard,playCardWarlock,useAction,attack,defense,nextPhase,payDealMana,nextCombat,dealDamage,
		passDeal,discard,nextSolveIntervention,start, ready,finish,sendText};
	public enum Notify{updateNewHandCard,updatePlayCardMana,updatePlayCardEntity,updatePlayCardIntervention,updateErrorCard,
		updateInfoInCard,updatePhase,updateRotate,updateFinishIntervention,updateRemoveEntity,updateDieEntity,updateStatsCard,updateNewWarlocks,
		updateAddGameEntity,updateAddManaCard,updateRemoveManaCard,updateAddGraveyard,updateRemoveGraveyard,updateRemoveHandCard,updateRefreshStatsWarlock,
		updateDiscar,updateDealMana,updateFinishDealMana,updateDecreDealMana,updateInsertNewCombat,updateInsertInCombat,updateRemoveCombat,updateRemoveFromCombat,
		updateRemoveDefenders,updateDealDamage,updateSolveCombat,updateFinishSolveCombat,updateInfoSolveCombat,updateFinishDealDamage,updateErrorSolveCombat,updateDecreaseSolvecombat,
		updateGoDiscard,updateFinishDiscard,updateGoExchangeEnviroment,updateCanExchangeEnviroment,updateSolveIntervention,updateFinishSolveIntervention,
		updateInfoSolveIntervention,updateUseActive,updateFinishPlay,updateManafire,updateNotCardsDeck,updateRemoveIntervention,updateAll,updateAddCardIntervention,
		updateErrorFinish,updateInsertInCombatWarlock,updateRotateAll,updateChangeWarlockTurn,updateStart,updateInsertNewCombatWarlock,updateNextCombat,updateNextIntervention,
		updatePlayersLobby, updateFinishLoad,updateAct, updateShowEnemyHand,updateFinish,updateError, updateChatText};
	
	private Notify notify;
	private Actions action;
	private ArrayList<Object> arg;
	
	public TransferObjectConection(Actions action,Notify notify,ArrayList<Object> arg){
		this.action = action;
		this.notify = notify;
		this.arg = arg;
	}
	
	/**
	 * @return the action
	 */
	public Actions getAction() {
		return action;
	}

	/**
	 * @return the notify
	 */
	public Notify getNotify() {
		return notify;
	}
	/**
	 * @return the arg
	 */
	public ArrayList<Object> getArg() {
		return arg;
	}
}
