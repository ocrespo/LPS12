package es.ucm.fdi.lps.p6.controller;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.game.Model.Phase;

public interface ObserverGUI{
	
	
	public void updateNewHandCard(Card id,final Warlock w);
	public void updatePlayCardMana(Warlock w);
	public void updatePlayCardEntity(Warlock w);
	public void updatePlayCardMana(Card idCard,Warlock idW);
	public void updatePlayCardEntity(Card idCard,Warlock idW);
	public void updatePlayCardIntervention();
	public void updateErrorCard(String er);
	public void updateInfoInCard(String er);
	public void updatePhase(Phase phase);
	public void updateRotate(boolean allTurn,boolean show,boolean changeWarlock);
	public void updateFinishIntervention();
	public void updateRemoveEntity(final Card id,final Warlock w);
	public void updateDieEntity(final Card id,final Warlock w);
	public void updateStatsCard(final Card id);
	public void updateNewWarlocks(final Warlock wa,final Warlock wb);
	public void updateAddGameEntity(Card c,Warlock w);
	public void updateAddManaCard(Card c,Warlock w);
	public void updateRemoveManaCard(Card c,Warlock w);
	public void updateAddGraveyard(Card c,Warlock w);
	public void updateRemoveGraveyard(Card c,Warlock w);
	public void updateRemoveHandCard(Card c,Warlock w);
	public void updateRefreshStatsWarlock(Warlock w);
	public void updateDiscar(Card c,Warlock w);
	public void updateDealMana(int count);
	public void updateFinishDealMana();
	public void updateDecreDealMana();
	public void updateInsertNewCombat();
	public void updateInsertInCombat();
	public void updateInsertNewCombat(Card c,Warlock w);
	public void updateInsertInCombat(Card at,Card c,Warlock w);
	public void updateRemoveCombat(Card at);
	public void updateRemoveFromCombat(Card at,Card c);
	public void updateRemoveDefenders(Card at);
	public void updateDealDamage(int count);
	public void updateSolveCombat();
	public void updateFinishSolveCombat();
	public void updateInfoSolveCombat(String text);
	public void updateFinishDealDamage();
	public void updateErrorSolveCombat(String text);
	public void updateDecreaseSolvecombat();
	public void updateGoDiscard(int count);
	public void updateFinishDiscard();
	public void updateGoExchangeEnviroment();
	public void updateCanExchangeEnviroment();
	public void updateSolveIntervention();
	public void updateFinishSolveIntervention();
	public void updateInfoSolveIntervention(String str);
	public void updateUseActive();
	public void updateFinishPlay(String winner,String text);
	public void updateManafire(String warlock, int damage);
	public void updateNotCardsDeck(String warlock);
	public void updateRemoveIntervention(Card c);
	public void updateAll(final Warlock wa,final Warlock wb,final Warlock wTurn);
	public void updateAddCardIntervention(final Card c);
	public void updateErrorFinish(String text);
	public void updatePlayCardIntervention(Card idC);
	public void updateInsertNewCombat(Card idC);
	public void updateInsertInCombat(Card at,Card def);
	public void updateChangePhase();
	public void updateChangeWarlockTurn(int wIdTurn);
	public void updateSolveCombatNet();
	public void updateNextCombat();
	public void updateNextIntervention();
	public void updateSolveInterventionNet();
	public void updateFinishLoad();
	public void updateFinishSolveCombatNet();
	public void updateUseActive(Card c,Warlock w);
	public void updateDecreDealMana(Card c);
	public void updateDealMana(int count, Card c);
	public void updateAct(Warlock w);
	public void updateShowEnemyHand(Warlock w);
	public void updateError(String text);
	public void updateChatText(String text);
}
