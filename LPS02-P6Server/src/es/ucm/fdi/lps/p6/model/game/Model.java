package es.ucm.fdi.lps.p6.model.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.TreeMap;

import es.ucm.fdi.lps.p6.DAO.DAO;
import es.ucm.fdi.lps.p6.DAO.DAOError;
import es.ucm.fdi.lps.p6.DAO.DTOCard;
import es.ucm.fdi.lps.p6.controller.Client;
import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.FileError;
import es.ucm.fdi.lps.p6.model.data.FileObject;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.cards.Card.PlayCard;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard.CombatError;

public class Model extends Observed {
	//public enum WarlockTurn{A,B};
	public enum Phase{ini,combatInterventionAt,combatInterventionDef,combatAttack,combatDefense,combatIntervention2At,
		combatIntervention2Def,combatSolve,finaly,interventionLoop,solveIntervention}
	
	private Warlock wa;
	private Warlock wb;
	private Warlock wAt;
	private Warlock wDef;
	private Warlock wTurn = null;
	private Warlock preInterventionTurn = null;
	private boolean enviroment = false;
	private boolean isCombat = false;
	private TreeCard combat = null;
	private StackCard interventions = null;
	private boolean onlyPay = false;
	private Phase phase;
	private Phase inInterventionLoop;
	private int passIntervention = 0;
	private Warlock wTarget;
	private int countNext = 0;
	private boolean isPay = false;
	
	private Client client1;
	private Client client2;
	
	/**
	 * default builder
	 */
	public Model(){
		wa = new Warlock((Configuration.getInstance()).getLife());
		wb = new Warlock((Configuration.getInstance()).getLife());
		
	}
	
	/**
	 * @param client1 the client1 to set
	 */
	public void setClient1(Client client1) {
		this.client1 = client1;
	}

	/**
	 * @param client2 the client2 to set
	 */
	public void setClient2(Client client2) {
		this.client2 = client2;
	}

	/**
	 * @param wTarget the wTarget to set
	 */
	public void setwTarget(Warlock wTarget) {
		if(wTarget.getId() == wa.getId()){
			this.wTarget = wa;
		}
		else{
			this.wTarget = wb;
		}
	}
	public void iniModel(){
		wa = null;
		wb = null;
		wAt = null;
		wDef = null;
		preInterventionTurn = null;
		enviroment = false;
		isCombat = false;
		combat = null;
		interventions = null;
		onlyPay = false;
		passIntervention = 0;
		wTarget = null;
		phase = Phase.ini;
	}
	/**
	 * initialize warlocks
	 * @throws Exception 
	 * @throws IOException 
	 */
	public void iniWarlock() throws IOException, Exception,FileError,DAOError{
		wa.setImage((Configuration.getInstance()).getImageA());
		wb.setImage((Configuration.getInstance()).getImageB());
		wa.setName((Configuration.getInstance()).getNameA());
		wb.setName((Configuration.getInstance()).getNameB());

		wa.add(getObservers());
		wb.add(getObservers());
		
		setChanged();
		notifyNewWarlocks(wa, wb);
		
		
		if((Configuration.getInstance()).readDeck((Configuration.getInstance()).getDirA(), wa)){
			if((Configuration.getInstance()).readDeck((Configuration.getInstance()).getDirB(), wb)){
			
			}	
		}
		(DAO.getInstance()).close();
		
		setChanged();
		notifyRefreshStatsWarlock((Warlock)wa.clone());
		
		if(!(Configuration.getInstance()).isDebug()){
			wa.fillDeck();
			wb.fillDeck();
		}
				
		
		boolean canDrawA = wa.drawHand();
		boolean canDrawB = wb.drawHand();
		if(!canDrawA && !canDrawB){
			setChanged();
			notifyFinishPlay("", "");
		}
		else if(canDrawA && !canDrawB){
			setChanged();
			notifyFinishPlay(wa.getName(), wa.getPhrase());
		}
		else if(!canDrawA && canDrawB){
			setChanged();
			notifyFinishPlay(wb.getName(), wb.getPhrase());
		}
		
		setChanged();
		notifyRotateAll(false, false, 1);
		
		if(!(Configuration.getInstance()).isDebug()){
			Random random = new Random();
			wAt = wa;
			wTurn = wa;
			wDef = wb;
			if(random.nextInt(2) == 0){
				setChanged();
				rotateTurn(true);
			}
		}
		else{
			wAt = wa;
			wTurn = wa;
			wDef = wb;

		}
		notifyAct(0,wa);
		notifyAct(1, wb);
		/*if(wTurn == wa){
			setChanged();
			notifyRotateAll(false, false, 1);
		}
		else{
			setChanged();
			notifyRotateAll(false, false, 0);
		}*/
		
		phase = Phase.ini;
		setChanged();
		
		notifyPhase(phase);
		iniPhase();
		
		//(FileObject.getInstance()).setWa(wa);
		//(FileObject.getInstance()).setWb(wb);
	}
	/**
	 * do a initial phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param c
	 */
	private void iniPhase(){
		(Console.getInstance()).writeLog("<iniPhase");
		//(Console.getInstance()).writeTurn(c);
		wAt.iniCards();
		wDef.iniCardsBuff();
		wDef.unDone();
		wAt.doPermanent();
		//wDef.doPermanent();
		
		(Judge.getInstance()).solveNext();
		//(Console.getInstance()).read();
		(Console.getInstance()).writeLog("iniPhase/>");
		if(!wAt.draw()){
			//(Console.getInstance()).write("Te has queddo sin cartas en el mazo, moriras!");
			setChanged();
			notifyNotCardsDeck(wAt.getName());
			wAt.loseLife(wAt.getLife());
		}
		
		onlyPay = false;
		isCombat = false;
		enviroment = false;
		
	}
	/**
	 * play card
	 * @param w , warlock who have the turn
	 * @param code , the code of the card
	 */
	
	public void playCard(Card c,Card tar){
		(Console.getInstance()).writeLog("<playCard");
		Card auxC = wAt.foundInHandCard(c);
		if(auxC != null){
			PlayCard aux = wAt.playCard(auxC,!enviroment,isCombat);
			if(aux == PlayCard.goEnviroment){
				enviroment = true;
				
			}
			/*else if(aux == PlayCard.goGame){
				
			}*/
			
			
			else if(aux == PlayCard.noMoreEnviroment){
				notifyErrorCard("<html>No puedes jugar mas entornos</html>",whoTurn());
			}
			else if(aux == PlayCard.noEnviromentNow){
				notifyErrorCard("<html>No puedes jugar entornos ahora</html>",whoTurn());
			}
			else if(aux == PlayCard.noInterventionNow){
				notifyErrorCard("<html>No puedes jugar intervenciones ahora</html>",whoTurn());
			}
			else if(aux == PlayCard.noEntityNow){
				notifyErrorCard("<html>No puedes jugar seres ahora</html>",whoTurn());
			}
			else if(aux == PlayCard.needMoreMana){
				notifyErrorCard("<html>No tienes suficiente coloro</html>",whoTurn());
			}
			(Console.getInstance()).writeLog("playCard/>");						
			if(aux == PlayCard.goIntervention && (phase == Phase.combatIntervention2At || phase == Phase.combatIntervention2Def || phase == Phase.combatInterventionAt
					|| phase == Phase.combatInterventionDef || phase == Phase.interventionLoop))	
				playIntervention(c, tar);
			else if (aux == PlayCard.goIntervention){
				notifyErrorCard("<html>No puedes jugar intervenciones ahora</html>",whoTurn());
			}
		}
		//return PlayCard.noError;
	}

	/**
	 * use the action of card
	 * @param c the card which use the action
	 * @param tar the target
	 */
	public void useAction(Card c,Card tar){
		(Console.getInstance()).writeLog("<useAction");
		Card auxC = wAt.foundInEnviromentCard(c);
		if(auxC == null){
			auxC = wAt.foundInGameCard(c);
		}
		Card auxTar = wDef.foundInEnviromentCard(tar);
		if(auxTar == null){
			auxTar = wDef.foundInGameCard(tar);
		}
		
		ErrorMen er = wAt.useActiveActionId(auxC, combat, auxTar, wDef, onlyPay);
		
		if(er.allOK()){
			setChanged();
			notifyUseActive(auxC,wAt);
		}
		
		else if(!er.success())
			notifyErrorCard("<html>No puede usar esa habilidad ahora</html>",whoTurn());

		else if(er.dischargedCard())
			notifyErrorCard("<html>Esa carta esta descargada y no se puede usar</html>",whoTurn());
		else if(er.noAction())
			notifyErrorCard("<html>Esa carta no tiene ninguna accion activa</html>",whoTurn());
		else if(er.needAttackTarget())
			notifyErrorCard("<html>El objetivo debe estar atacando</html>",whoTurn());
		else if(er.needCombat())
			notifyErrorCard("<html>Esa carta necesita aplica sus efectos mientras haya un combate</html>",whoTurn());
		else if(er.needSameCombatTarget())
			notifyErrorCard("<html>El objetivo debe estar en la misma batalla</html>",whoTurn());
		else if(er.needTarget()){
			notifyInfoInCard("<html>Seleccione un objetivo para usar la habilidad</html>",whoTurn());
		}
		else if(er.needEntityTarget()){
			notifyErrorCard("<html>El objetivo debe ser un Ser</html>",whoTurn());
		}
		else if(!er.allOK())
			(Console.getInstance()).write("Esa carta necesita un objetivo sobre el que realizar la accion (recuerde '--usar' 'codigo_carta' 'codigo_carta_objetivo')");
		
		(Console.getInstance()).writeLog("useAction/>");
	}
	
	/**
	 * do a attack phase
	 * @param c the card who go attack
	 */
	public void attack(Card c){
		(Console.getInstance()).writeLog("<attackPhase");
		Card aux = wAt.foundInGameCard(c);
		if(!aux.canUse()){
			if(combat.isHereAt(aux)){
				notifyErrorCard("<html>Esa carta ya esta luchando</html>",whoTurn());
			}
			else
				notifyErrorCard("<html>Esa carta esta descargada<br>no puede atacar</html>",whoTurn());
		}
		else if(phase != Phase.combatAttack){
			notifyErrorCard("<html>Ahora no puedes atacar</html>",whoTurn());
		}
		else{
			if(combat.insert(aux)){
				//(Console.getInstance()).write("Al ataque mis valientes, ¡LOK'TAR OGAR!");
				aux.doAttack(combat,null, wDef);
				aux.turnOn();
				setChanged();
				notifyInsertNewCombat(aux);
				//notyfyGoAttack(c);
			}
			else{
				notifyErrorCard("<html>Esa carta ya esta luchando</html>",whoTurn());
			}
			
		}
	}
	/**
	 * assign defenders to combat
	 * @param tar the attacker of the combat
	 * @param c the card who go defense
	 */
	public void defense(Card tar,Card c){
		Card auxC = wAt.foundInGameCard(c);
		boolean found = combat.isHere(auxC);
		if(phase != Phase.combatDefense){
			notifyErrorCard("<html>Ahora no puedes asignar defensores</html>",whoTurn());
		}
		else if(!auxC.canUse()){
			notifyErrorCard("<html>Ese Ser esta descargado</html>",whoTurn());
		}
		else if(tar == null && !found){
			notifyInfoInCard("<html>Seleccione un objetivo al que defender</html>",whoTurn());
		}
		else if(found){
			notifyErrorCard("<html>Ese Ser ya esta defendiendo</html>",whoTurn());
		}
		
		else{
			Card auxAt = wDef.foundInGameCard(tar);
			if(auxAt == null){
				notifyErrorCard("<html>El objetivo debe ser una carta enemiga que ataque</html>",whoTurn());
			}
			else{
				CombatError er = combat.insertCard(auxAt, auxC);
				if(er == CombatError.allOk){
 					LinkedCard lin = combat.getCombat(auxAt);
					lin.getCard().doAttack(combat,lin, wAt);
					auxC.doDefense(combat,lin, wDef);
						
					setChanged();
					notifyInsertInCombat(auxAt,auxC);
					//notyfyDefense(tar,c);
				}
				else if(er == CombatError.cantDefense){
					notifyErrorCard("<html>El objetivo no puede ser defendido</html>",whoTurn());
				}
				else if(er == CombatError.needDefenseOther){
					notifyErrorCard("<html>Tienes que defender a otro Ser</html>",whoTurn());			
				}
				else if(er == CombatError.noAttack){
					notifyErrorCard("<html>Ese Ser no esta atacando</html>",whoTurn());
				}
			}
		}
	}
	/**
	 * configurtaion the attack phase
	 */
	private void configAttackPhase(){
		onlyPay = true;
		isCombat = true;
		combat = new TreeCard();
	}
	/**
	 * change phase
	 */
	public void next(){
		if(phase == Phase.ini){
			configAttackPhase();
			interventions = new StackCard();
			phase = Phase.combatInterventionAt;
			inInterventionLoop = phase;
			preInterventionTurn = wAt;
		}
		else if(phase == Phase.combatInterventionAt){
			rotateTurn(false);
			phase = Phase.combatInterventionDef;
			interventions = new StackCard();
			inInterventionLoop = phase;
			preInterventionTurn = wAt;
			
		}
		else if(phase == Phase.combatInterventionDef){
			wAt.doAllPassiveAction();
			wDef.doAllPassiveAction();
			rotateTurn(false);
			phase = Phase.combatAttack;
			onlyPay = false;
			
			
		}
		else if(phase == Phase.combatAttack){
			if(combat.isEmpty()){
				phase = Phase.finaly;
				finalPhase();
			}
			else{
				phase = Phase.combatDefense;
				rotateTurn(false);
			}
		}
		else if(phase == Phase.combatDefense){
			rotateTurn(false);
			phase = Phase.combatIntervention2At;
			inInterventionLoop = phase;
			interventions = new StackCard();
			preInterventionTurn = wAt;
			onlyPay = true;
			
		}
		else if(phase == Phase.combatIntervention2At){
			rotateTurn(false);
			phase = Phase.combatIntervention2Def;
			interventions = new StackCard();
			inInterventionLoop = phase;
			preInterventionTurn = wAt;
			
		}
		else if(phase == Phase.combatIntervention2Def){
			//(Judge.getInstance()).solveCombat(combat, wAt, wDef);
			rotateTurn(false);
			
			
			if(!combat.isEmpty()){
				phase = Phase.combatSolve;
				setChanged();
				notifySolveCombat();
				
			}
			else{
				countNext = 0;
				phase = Phase.finaly;
				finalPhase();
			}
			//combat = null;
		}
		else if(phase == Phase.combatSolve){
			countNext = 0;
			phase = Phase.finaly;
			finalPhase();
		}
		else if(phase == Phase.finaly){
			countNext = 0;
			phase = Phase.ini;
			rotateTurn(true);
			iniPhase();
		}
		else if(phase == Phase.interventionLoop){
			if(passIntervention == 1){
				notifySolveIntervention();
				phase = Phase.solveIntervention;
				//(Judge.getInstance()).solveIntervention(interventions);
				
				
				//setChanged();
				//notifyFinishIntervention();
			}
			else{
				passIntervention++;
				rotateTurn(false);
			}
		}
		else if(phase == Phase.solveIntervention){
			phase = inInterventionLoop;
			interventions = null;
			if(preInterventionTurn != wAt){
				rotateTurn(false);
			}
			setChanged();
			notifyFinishIntervention();
			next();
		}
		setChanged();
		notifyPhase(phase);
	}
	/**
	 * 
	 * @param c the card
	 * @return the warlock who have the card
	 */
	private Warlock whoIsProperty(Card c){
		if(wTarget != null){
			return wTarget;
		}
		else{
			if(wa.found(c))
				return wa;
			else if(wb.found(c))
				return wb;
		}
		return null;
	}
	/**
	 * play intervention card
	 * @param c the card
	 * @param tar the target of the card
	 */
	private void playIntervention(Card c,Card tar){
		Card auxC = wAt.foundInHandCard(c);
 		ErrorMen er = auxC.allOK(combat, tar,auxC, wAt,wDef,whoIsProperty(tar),interventions);
 		(Console.getInstance()).writeLog("checkIntervention/>");
		if(er.allOK()){
			PlayCard canPlay = wAt.payCard(auxC);
			if(canPlay 	== PlayCard.goIntervention || isPay){
				interventions.insert(auxC);
				
				setChanged();
				notifyPlayCardIntervention(auxC);	
				wAt.discard(auxC);
				phase = Phase.interventionLoop;
				passIntervention = 0;
				
				isPay = false;
				
				rotateTurn(false);
			}
			else if(canPlay == PlayCard.needMoreMana){
				notifyErrorCard("<html>No tienes suficiente cloro</html>",whoTurn());
			}
		}
		else if(er.needTarget()){
			notifyInfoInCard("<html>Seleccione un objetivo</html>",whoTurn());
		}
		else if(er.needAttackTarget())
			notifyErrorCard("<html>Debes seleccionar un objetivo que este atacando</html>",whoTurn());
		else if(er.needDefenderChoose()){
			notifyErrorCard("<html>Deben estar asignados los defensores para jugar esa accion</html>",whoTurn());
		}
		else if(er.needAttackerChoose()){
			notifyErrorCard("<html>Deben estar asignados los atacantes para jugar esa accion</html>",whoTurn());
		}
		else if(er.needEntityTarget())
			notifyErrorCard("<html>El objetivo debe ser un Ser en juego</html>",whoTurn());
		else if(er.needInterventionTarget()){
			notifyErrorCard("<html>El objetivo debe ser una Intervencion usada</html>",whoTurn());
		}
		else if(er.goExchangeEnviroment()){
			if(isPay){
				notifyGoExchangeEnviroment(whoTurn());
			}
			else{
				PlayCard canPlay = wAt.payCard(auxC);
				if(canPlay 	== PlayCard.goIntervention){
					isPay = true;
					notifyGoExchangeEnviroment(whoTurn());
				}
				else if(canPlay == PlayCard.needMoreMana){
					notifyErrorCard("<html>No tienes suficiente cloro</html>",whoTurn());
				}
			}
		}
		else if(er.needHaveEnviroment()){
			notifyErrorCard("<html>Tu y tu oponente debeis tener entornos en juego</html>",whoTurn());
		}
		else if(er.needTargetEnviroment()){
			notifyErrorCard("<html>El objetivo deben ser entornos</html>",whoTurn());
		}
		else if(er.canExchangeEnviroment()){
			notifyCanExchangeEnviroment(whoTurn());
		}
		else if(er.cantExchangeMoreEnviroment()){
			notifyErrorCard("<html>No puedes intercambiar mas entornos</html>",whoTurn());
		}
		else if(er.targetAlreadySelect()){
			notifyErrorCard("<html>Ya has seleccionado esa carta</html>",whoTurn());
		}
		else if(er.needWarlockTarget()){
			notifyInfoInCard("<html>Seleccion el brujo(pulse sobre su nombre)</html>",whoTurn());
		}
		else if(er.onlyTargetWarlock()){
			notifyErrorCard("<html>El objetivo debe ser un brujo</html>",whoTurn());
		}
		wTarget = null;
	}
	/**
	 * solve the next intervention
	 */
	public void nextSolveIntervention(){
		countNext++;
		if(countNext%2 == 0 && countNext <= 2){
			if(!(Judge.getInstance()).nextSolveIntervention(interventions)){
				next();
			}
			
		}
		else if(countNext%4 == 0){
			countNext = 2;					
			notifyNextIntervention();
			if(!(Judge.getInstance()).nextSolveIntervention(interventions)){
				next();
				countNext = 0;
			}
			
		}
		
	}
	/**
	 * rotate the turn
	 * @param allTurn if finish the complete turn
	 */
	private void rotateTurn(boolean allTurn){
		if(wAt == wa){
			wAt = wb;
			wDef = wa;
			
		}
		else{
 			wAt = wa;
			wDef = wb;
		}
		if(allTurn){
			wTurn = wAt;
			setChanged();
			notifyRotateTurn();
		}
		setChanged();
		notifyChangeWarlockTurn(wAt.getId());
	}
	/**
	 * deal the generic mana
	 * @param e the element choose
	 * @param c the card
	 */
	public void payDeal(Element e,Card c){
		Card auxC = wAt.foundInHandCard(c);
		boolean finish = wAt.payDeal(e,auxC,phase == Phase.ini);
		if(finish && phase != Phase.ini){
			interventions.insert(auxC);
			
			setChanged();
			notifyPlayCardIntervention(auxC);
			wAt.discard(auxC);
			phase = Phase.interventionLoop;
			passIntervention = 0;
			
			
			rotateTurn(false);
		}
		if(finish) {
			notifyFinishDealMana();
		}
	}
	/**
	 * solve the next combat
	 */
	public void nextSolveCombat(){
		countNext++;
		if(countNext%2 == 0 && countNext <= 2){
			if(!(Judge.getInstance()).nextSolveCombat(combat, wAt, wDef)){
				next();
			}
			
		}
		else if(countNext%4 == 0){
			countNext = 2;					
			notifyNextCombat();
			if(!(Judge.getInstance()).nextSolveCombat(combat, wAt, wDef)){
				next();
			}
			
		}
	}
	/**
	 * dea damage between entities in combat
	 * @param c
	 */
	public void dealDamage(Card c){
		Card auxC = wDef.foundInGameCard(c);
		if(auxC == null){
			auxC = wAt.foundInGameCard(c);
		}
		(Judge.getInstance()).dealDamage(auxC, wDef);
	}
	/**
	 * pass to deal damage and go to warlock
	 */
	public void passDeal(){
		(Judge.getInstance()).passDeal(wDef);
	}
	/**
	 * final phase
	 */
	private void finalPhase(){
		(Console.getInstance()).writeLog("<theEnd phase");
		(Judge.getInstance()).undoIntervention();
		//if(combat == null || combat.isEmpty()){
			(Judge.getInstance()).unDoneAct();
		//}
		int value = wAt.manaFire();
		if(value != 0){
			setChanged();
			notifyManafire(wAt.getName(), value);
		}
			//(Console.getInstance()).write("El brujo "+wAt.getName()+ " ha perdido "+value+" puntos de vida por quemadura de cloro");
		int winner = (Judge.getInstance()).winner(wAt, wDef);
		if(winner == -1){
			setChanged();
			notifyFinishPlay("", "");
			//(Console.getInstance()).write("Ambos jugadores han muerto ¡Empate!");


		}
		else if(winner == 1){
			setChanged();
			notifyFinishPlay(wDef.getName(), wDef.getPhrase());
			//win(wAt);

		}
		else if(winner == 2){
			setChanged();
			notifyFinishPlay(wAt.getName(), wAt.getPhrase());
			//win(wDef);
		}
		if(!(Configuration.getInstance()).isAdmin())	
			wAt.iniMana();
		int num = wAt.getNumHand();
		int max = (Configuration.getInstance()).getMaxHand();
		if(num > max){
			notifyGoDiscard(num - max,whoTurn());
		}
		else{
			next();
		}
		(Console.getInstance()).writeLog("theEnd phase/>");
	}
	/**
	 * discard a card
	 * @param c the card
	 */
	public void discar(Card c){
		wAt.discard(c);
		if(wAt.getNumHand() == (Configuration.getInstance()).getMaxHand()){
			setChanged();
			notifyFinishDiscard(whoTurn());
			next();
		}
	}
	
	/**
	 * save present game
	 * @param dir the file
	 */
	public void saveGame(String dir){
		(FileObject.getInstance()).setWa(wa);
		(FileObject.getInstance()).setWb(wb);
		(FileObject.getInstance()).setPhase(phase);
		
		
		if(wAt == wa){
			(FileObject.getInstance()).setwAt("a");
		}
		else{
			(FileObject.getInstance()).setwAt("b");
		}
		
		if(wTurn == wa){
			(FileObject.getInstance()).setwTurn("a");
		}
		else{
			(FileObject.getInstance()).setwTurn("b");
		}
		
		
		if(wa == preInterventionTurn){
			(FileObject.getInstance()).setPreInterventionTurn("a");
		}
		else if(wb == preInterventionTurn){
			(FileObject.getInstance()).setPreInterventionTurn("b");
		}
		else{
			(FileObject.getInstance()).setPreInterventionTurn("");
		}
		
		(FileObject.getInstance()).setPhase(phase);
		(FileObject.getInstance()).setEnviroment(enviroment);
		(FileObject.getInstance()).setCombat(isCombat);
		(FileObject.getInstance()).setCombat(combat);
		(FileObject.getInstance()).setInterventions(interventions);
		(FileObject.getInstance()).setOnlyPay(onlyPay);

		(FileObject.getInstance()).setInInterventionLoop(inInterventionLoop);
		(FileObject.getInstance()).setPassIntervention(passIntervention);
		
		(Judge.getInstance()).addInfoToSave();
		
		try {
			(FileObject.getInstance()).save(dir);
		} catch (FileError e) {
			notifyErrorFinish(e.getInfo());
		}
	}
	/**
	 * load the game 
	 * @param dir the file
	 */
	public void loadGame(String dir){
		try {
			(FileObject.getInstance()).load(dir);
		} catch (FileError e) {
			notifyErrorFinish(e.getInfo());
		}
		wa = (FileObject.getInstance()).getWa();
	
		wb = (FileObject.getInstance()).getWb();
		phase = (FileObject.getInstance()).getPhase();
		inInterventionLoop = (FileObject.getInstance()).getInInterventionLoop();
		
		wa.add(getObservers());
		wb.add(getObservers());
		
		String aux = (FileObject.getInstance()).getwTurn();
		if(aux.equals("a")){
			wTurn = wa;
		}
		else{
			wTurn = wb;
		}
		
		setChanged();
		notifyAll(wa,wb,wTurn);
		
		notifyAllCardInHand(wa);
		notifyAllCardInHand(wb);
		
		notifyAllCardInGame(wa);
		notifyAllCardInGame(wb);
		
		notifyAllCardInEnviroment(wa);
		notifyAllCardInEnviroment(wb);
		
		setChanged();
		if(phase == Phase.interventionLoop){
			notifyPhase(inInterventionLoop);
		}
		else{
			notifyPhase(phase);
		}
		
		
		aux = (FileObject.getInstance()).getwAt();
		wAt = wa;
		wDef = wb;
		if(aux.equals("b")){
			setChanged();
			rotateTurn(false);
		}
		

		
		aux = (FileObject.getInstance()).getPreInterventionTurn();
		if(aux.equals("a")){
			preInterventionTurn = wa;
		}
		else if(aux.equals("b")){
			preInterventionTurn = wb;
		}
		else{
			preInterventionTurn = null;
		}
		
		enviroment = (FileObject.getInstance()).isEnviroment();
		isCombat = (FileObject.getInstance()).isCombat();
		combat = (FileObject.getInstance()).getCombat();
		interventions = (FileObject.getInstance()).getInterventions();
		onlyPay = (FileObject.getInstance()).isOnlyPay();

		
		passIntervention = (FileObject.getInstance()).getPassIntervention();
		
		notifyAllCombat();
		
		if(interventions != null && !interventions.isEmpty()){
			notifyAllIntervention();
		}
		
		(Judge.getInstance()).takeInfoLoad();
		
	}
	/**
	 * notify view all cards warlock have hand
	 */
	private void notifyAllCardInHand(Warlock w){
		ArrayList<Card> aux = w.getAllCardsInHand();
		for(Card c : aux){
			setChanged();
			notifyNewHandCard(c, w);
		}
	}
	/**
	 * notify view all cards warlock have inGame
	 */
	private void notifyAllCardInGame(Warlock w){
		ArrayList<Card> aux = w.getAllCardsInGame();
		for(Card c : aux){
			setChanged();
			notifyAddGameEntity(c, w);
		}
	}
	/**
	 * notify view all cards warlock have enviroment
	 */
	private void notifyAllCardInEnviroment(Warlock w){
		ArrayList<Card> aux = w.getAllCardsInEnviroment();
		for(Card c : aux){
			setChanged();
			notifyAddManaCard(c, w);
		}
	}
	/**
	 * notify all info in combat
	 */
	private void notifyAllCombat(){
		if(combat != null && !combat.isEmpty()){
			TreeMap<Card, LinkedCard> auxCombat = combat.getValues();
			Card at = null;
			for(LinkedCard lin : auxCombat.values()){
				at = lin.removeTop();
				setChanged();
				notifyInsertNewCombat(at,whoIsProperty(at));
				Warlock prop = null;
				for(Card c : lin.getValues()){
					if(prop == null){
						prop = whoIsProperty(c);
					}
					setChanged();
					notifyInsertInCombat(at,c);
				}
				lin.insertFirst(at);
			}
		}
	}
	/**
	 * notify all cards intervention
	 */
	private void notifyAllIntervention(){
		Stack<Card> aux = interventions.getValues();
		for(Card c : aux){
			setChanged();
			notifyAddCardIntervention(c);
		}
	}
	/**
	 * 
	 * @return if of warlock who have turn
	 */
	private int whoTurn(){
		if(wAt == wa){
			return 0;
		}
		else{
			return 1;
		}
	}
	/**
	 * a clients are ready to play
	 */
	private void ready() {
		countNext ++;
		if(countNext == 2){
			notifyStart();
			countNext = 0;
		}
	}
	/**
	 * start game
	 */
	private void start() {
		countNext ++;
		if(countNext == 2){
			try {
				iniWarlock();
			} catch (IOException e) {
				notifyError("Error con un problema en la apertura del fichero");
				
			} catch (FileError e) {
				notifyError(e.getInfo());
				client1.stop();
				client2.stop();
				System.exit(1);
			} catch (DAOError e) {
				notifyError(e.getInfo());
				client1.stop();
				client2.stop();
				System.exit(1);
			} catch (Exception e) {
				notifyError("Error leyendo los fichero,\n Porfavor verifíquelo");
				client1.stop();
				client2.stop();
				System.exit(1);
			}
			
			notifyFinisLoad();
			countNext = 0;
			
		}
	}
	/**
	 * finish game
	 */
	public void finish() {
		notifyError("Se desconecta de la partida");
		client1.stop();
		if(client2 != null){
			client2.stop();
		}
		notifyFinish();
		System.exit(1);
		
	}
	/**
	 * do action
	 * @param trans the transfer object
	 */
	public synchronized void doneActionClient(TransferObjectConection trans,int w){
		ArrayList<Object> arg = trans.getArg();
		Card c = null;
		Card tar = null;
		switch (trans.getAction()) {
		case playCard:			
			c = CardFactory.getCard((DTOCard)arg.get(0),true);
			tar = CardFactory.getCard((DTOCard)arg.get(1),true);
			playCard(c,tar);
			break;
			
		case playCardWarlock:
			
			setwTarget((Warlock) arg.get(0));
			break;
			
		case useAction:
			c = CardFactory.getCard((DTOCard)arg.get(0),true);
			tar = CardFactory.getCard((DTOCard)arg.get(1),true);
			useAction(c, tar);
			break;
			
		case attack:
			c = CardFactory.getCard((DTOCard)arg.get(0),true);
			attack(c);
			break;
			
		case defense:
			tar = CardFactory.getCard((DTOCard)arg.get(0),true);
			c = CardFactory.getCard((DTOCard)arg.get(1),true);
			defense(tar,c);
			break;
			
		case nextPhase:
			next();
			break;
			
		case payDealMana:
			c = CardFactory.getCard((DTOCard)arg.get(1),true);
			payDeal((Element)arg.get(0), c);
			break;
			
		case nextCombat:
			nextSolveCombat();
			break;
			
		case dealDamage:
			c = CardFactory.getCard((DTOCard)arg.get(0),true);
			dealDamage(c);
			break;
		
		case passDeal:
			passDeal();
			break;
			
		case discard:
			c = CardFactory.getCard((DTOCard)arg.get(0),true);
			discar(c);
			break;
			
		case nextSolveIntervention:
			nextSolveIntervention();
			break;
		case ready:
			ready();
			break;
		case start:
			start();
			break;
		case finish:
			finish();
			break;
		case sendText:
			String name;
			if(wa.getId() == w+1){
				name = wa.getName();
			}
			else{
				name = wb.getName();
			}
			notifiChatText(name+" : "+(String)arg.get(0));
			break;
			
		}
	}

	

	

	/**
	 * if can do a action
	 * @param w the warlock who try do
	 * @return if can
	 */

	public synchronized boolean canDo(int w) {
		if(wTurn == null){
			return true;
		}
		if(w == 0){
			if(wAt == wa){
				return true;
			}
			return false;
		}
		else{
			if(wAt == wb){
				return true;
			}
			return false;
		}
		
	}	
}
