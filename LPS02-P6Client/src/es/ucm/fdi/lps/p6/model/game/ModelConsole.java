/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game;

import java.util.StringTokenizer;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Cost;
import es.ucm.fdi.lps.p6.model.data.FileError;
import es.ucm.fdi.lps.p6.model.data.FileObject;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.PlayCard;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard.CombatError;



/**
 * @author Roni
 *
 */
public class ModelConsole {
	enum GoPhase{exit,end,next};
	private Warlock wa;
	private Warlock wb;
	/**
	 * builder of Board
	 * @param wa ,warlock a
	 * @param wb , warlock b
	 */
	public ModelConsole(Warlock wa,Warlock wb){
		this.wa = wa;
		this.wb = wb;
	
	}
	/**
	 * function to play game and do the turns
	 */
	public void play(){
		(Console.getInstance()).writeLog("<ini play");
		boolean salir = false;	
		if((Console.getInstance()).welcomeMenu()){
			if(!(Configuration.getInstance()).isDebug()){
				wa.fillDeck();
				wb.fillDeck();
			}
			if((Configuration.getInstance()).isAdmin()){
				(Console.getInstance()).write("Estas en modo ADMIN");
				(Console.getInstance()).writeLog("ADMIN");
			}
			boolean canDrawA = wa.drawHand();
			boolean canDrawB = wb.drawHand();
			if(!canDrawA && !canDrawB){
				(Console.getInstance()).write("Ambos brujos os habeis quedado sin mazo, empate");
				salir = true;
			}
			else if(canDrawA && !canDrawB){
				(Console.getInstance()).write("El brujo B no puede robar");
				win(wa);
				salir = true;
			}
			else if(!canDrawA && canDrawB){
				(Console.getInstance()).write("El brujo A no puede robar");
				win(wb);
				salir = true;
			}
			if(!salir){
				Warlock w1=wa;
				Warlock w2=wb;
				mainPlay(w1, w2);
			}
			
		}
	}
	public void mainPlay(Warlock w1,Warlock w2){
		boolean salir = false;	
		while(!salir){
			salir = true;
			iniPhase(w1,w2, whoTurn(w1));
			GoPhase go = preCombat(w1,w2);
			if(go == GoPhase.next){
				(Console.getInstance()).writeLog("<Create combat estructure");
				TreeCard combat = new TreeCard();
				(Console.getInstance()).writeLog("Create combat estructure/>");
					
				if(combat(w1, w2, combat)){
					combat = null;
					if((Judge.getInstance()).lose(w2)){
						win(w1);
					}
					else if((Judge.getInstance()).lose(w1))
						win(w2);
					else
						salir =  false;
				}
					
			}
			else if(go == GoPhase.end){
				salir = false;
			}
			if(!salir){
				GoPhase finish = theEnd(w1,w2);
				if(finish == GoPhase.next){
					Warlock aux = w1;
					w1 = w2;
					w2 = aux;
				}
				else 
					salir = true;
			}
		}
		System.exit(0);
	}
	/**
	 * do a initial phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param c
	 */
	private void iniPhase(Warlock w1,Warlock w2,String c){
		(Console.getInstance()).writeLog("<iniPhase");
		(Console.getInstance()).writeTurn(c);
		w1.iniCards();
		w2.unDone();
		w1.doPermanent();
		w2.doPermanent();
		(Judge.getInstance()).solveNext();
		(Console.getInstance()).read();
		(Console.getInstance()).writeLog("iniPhase/>");
		if(!w1.draw()){
			(Console.getInstance()).write("Te has queddo sin cartas en el mazo, moriras!");
			w1.loseLife(w1.getLife());
		}
	}
	/**
	 * do a pre-combat phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @return the next step into game
	 */
	private GoPhase preCombat(Warlock w1,Warlock w2){
		(Console.getInstance()).writeLog("<preCombat");
		(Console.getInstance()).write("---------------Fase pre-trifulca---------------------------------");
		
		String text = chooseLoop(w1,w2,null,null);
		boolean enviroment = false;
		while(!text.equals("--salir") && !text.equals("--sig")&& !text.equals("--combate")&& !text.equals("--final")){
			if(text.startsWith("--usar")){
				useAction(w1, w2, text,null,false);
			}
			else if(text.startsWith("--atacar")){
				(Console.getInstance()).write("No puedes atacar ahora, para ir al ataque usa '--combate'");
			}
			else if(text.startsWith("--guardar") && !enviroment){
				StringTokenizer tok = new StringTokenizer(text);
				tok.nextToken();
				if(tok.hasMoreTokens()){
					save(w1, tok.nextToken());
				}
				else{
					(Console.getInstance()).write("Debes especificar el fichero donde deseas guardar");
				}
			}
			else if(text.startsWith("--guardar")){
				(Console.getInstance()).write("Ya has jugado una carta y no puedes guardar");
			}
			else if(text.startsWith("--cargar")){
				StringTokenizer tok = new StringTokenizer(text);
				tok.nextToken();
				if(tok.hasMoreTokens()){
					load(tok.nextToken());
				}
				else{
					(Console.getInstance()).write("Debes especificar el fichero donde deseas guardar");
				}
			}
			else{
				if(playCard(w1,text,enviroment,false) == PlayCard.goEnviroment)
					enviroment = true;
			}
			text = chooseLoop(w1,w2,null,null);
		}
		if(text.equals("--salir"))
			return GoPhase.exit;
		else if(text.equals("--final"))
			return GoPhase.end;
		(Console.getInstance()).writeLog("precombat/>");
		return GoPhase.next;
	}
	/**
	 * do combat phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param combat , the info about all battlefield
	 * @return if continue playing  or exit
	 */
	private boolean combat(Warlock w1,Warlock w2,TreeCard combat){
		(Console.getInstance()).writeLog("<combat");
		(Console.getInstance()).write("-------------Fase trifulca---------------------------------");
		if(interventionPhase(w1, w2, combat)){
			w1.doAllPassiveAction();
			w2.doAllPassiveAction();
			
			GoPhase at = attack(w1,w2,combat);	
			if(at == GoPhase.next){
				if(combat.isEmpty())
					return true;
				if(defense(w2,w1,combat)){
					if(interventionPhase(w1, w2, combat)){
						(Judge.getInstance()).solveCombat(combat, w1, w2);
						return true;
					}
					else
						return false;
				}
			}
			else if(at == GoPhase.end){
				return true;
			}
		}
		(Console.getInstance()).writeLog("combat/>");
		return false;	
	}
	/**
	 * write when any warlock win the game
	 * @param w , warlock who won the game
	 */
	private void win(Warlock w){
		(Console.getInstance()).writeLog("<win");
		(Console.getInstance()).write("El brujo "+whoTurn(w)+" ha ganado la partida, ¡Gloria al nuevo rey!");
		(Console.getInstance()).writeLifes(wa.getLife(), wb.getLife());
		(Console.getInstance()).write("\r\nPulse enter\r\n");
		(Console.getInstance()).read();
		(Console.getInstance()).write(w.getPhrase());
		(Console.getInstance()).read();
		(Console.getInstance()).writeLog("win/>");
	}
	/**
	 * do the end phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @return if continue playing  or exit
	 */
	private GoPhase theEnd(Warlock w1,Warlock w2){
		(Console.getInstance()).writeLog("<theEnd phase");
		(Judge.getInstance()).undoIntervention();
		int value = w1.manaFire();
		if(value != 0)
			(Console.getInstance()).write("El brujo "+whoTurn(w1)+ " ha perdido "+value+" puntos de vida por quemadura de cloro");
		
		if((Judge.getInstance()).winner(w1, w2) == -1){
			(Console.getInstance()).write("Ambos jugadores han muerto ¡Empate!");
			return GoPhase.exit;
		}
		else if((Judge.getInstance()).winner(w1, w2) == 1){
			win(w1);
			return GoPhase.exit;
		}
		else if((Judge.getInstance()).winner(w1, w2) == 2){
			win(w2);
			return GoPhase.exit;
		}
		
		w1.iniMana();
		int max = (Configuration.getInstance()).getMaxHand();
		int num = w1.getNumHand();
		if(num > max){
			(Console.getInstance()).write("Tienes mas cartas de las permitidas, debes descartarte de "+(num-max)+" cartas");
			String text = "";
			while(!text.equals("--salir") && num>max){
				text = chooseLoop(w1,w2,null,null);
				if(w1.discard(text)){
					(Console.getInstance()).write("Carta descartada");
					num--;
				}
				else 
					(Console.getInstance()).write("Esa carta no esta en la mano");
				(Console.getInstance()).write("Te quedan por descartar "+(num-max));
			}
			if(text.equals("--salir"))
				return GoPhase.exit;
		}
		(Console.getInstance()).write("Fin del turno del brujo "+whoTurn(w1));
		(Console.getInstance()).read();
		(Console.getInstance()).writeLog("theEnd phase/>");
		return GoPhase.next;
	}
	/**
	 * do a attack phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param combat , the info about all battlefield
	 * @return if go to next step of the game or exit
	 */
	public GoPhase attack(Warlock w1,Warlock w2,TreeCard combat){
		(Console.getInstance()).writeLog("<attackPhase");
		(Console.getInstance()).write("¡ATAQUE!Es momento de que asigne atacantes si lo considera oportuno");
		String text = chooseLoop(w1,w2,combat,null);
		while(!text.equals("--salir") && !text.equals("--sig")){
			if(text.startsWith("--usar")){
				useAction(w1, w2, text,combat,false);
			}
			else if(text.startsWith("--combate")){
				(Console.getInstance()).write("Ya estas en la fase de combate");
			}
			else if(text.startsWith("--final") && !combat.isEmpty()){
				(Console.getInstance()).write("¿Intentas huir? habertelo pensado antes de dartelas de duro, ahora combate o muere con dignidad");
			}
			else if(text.startsWith("--final") && combat.isEmpty()){
				return GoPhase.end;
			}
			else if(text.startsWith("--defenser")){
				(Console.getInstance()).write("Ahora no puedes asignar defensores");
			}
			else if(text.startsWith("--atacar")){
				Card c = null;
				StringTokenizer tok = new StringTokenizer(text);
				tok.nextToken();
				if((c = parserCombat(tok, w1,false)) != null){
					if(!c.canUse()){
						(Console.getInstance()).write("Esa carta esta descargada , no puede atacar o ya esta atacando");	
					}
					else{
						if(combat.insert(c)){
							(Console.getInstance()).write("Al ataque mis valientes, ¡LOK'TAR OGAR!");
							c.doAttack(combat,null, w2);
							c.turnOn();
						}
						else
							(Console.getInstance()).write("Esa carta ya esta luchando");
					}
				}
				
			}
			else{
				(Console.getInstance()).write("Opcion no valida, recuerde '--atacar codigo' ");
			}
			text = chooseLoop(w1,w2,combat,null);
		}
		(Console.getInstance()).writeLog("attackPhase/>");
		if(text.equals("--salir"))
			return GoPhase.exit;
		return GoPhase.next;
	}
	/**
	 * do the defense phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param combat , the info about all battlefield
	 * @return if go to next step of the game or exit
	 */
	public boolean defense(Warlock w1,Warlock w2,TreeCard combat){
		(Console.getInstance()).writeLog("<defensePhase");
		(Console.getInstance()).write("¡DEFIENDASE! asigne defensores  como guste");
		String text = chooseLoop(w1,w2,combat,null);
		while(!text.equals("--salir") && !text.equals("--sig")){
			if(text.startsWith("--usar")){
				useAction(w1, w2, text,combat,false);
			}
			else if(text.startsWith("--final")){
					(Console.getInstance()).write("Mirar como llora el brujo que va a morir ¡no puedes huir, a luchar!");
			}
			else if(text.startsWith("--atacar")){
				(Console.getInstance()).write("Ahora no puedes asignar atacantes");
			}
			else if(text.startsWith("--defender")){
				Card c = null;
				StringTokenizer tok = new StringTokenizer(text);
				tok.nextToken();
				if((c = parserCombat(tok, w1,false)) != null){
					if(!combat.isHere(c)){
						if(!c.canUse()){
							(Console.getInstance()).write("Esa carta esta descargada , no puede defender");	
						}
						else{
							(Console.getInstance()).write("Introduzca la carta que quiere defender ,y en caso de haber mas de una, el numero que sera su posicion partiendo del 1 segun se muestra por pantalla");
							text = chooseLoop(w1,w2,combat,null);
							tok = new StringTokenizer(text);
							if(tok.hasMoreTokens()){	
								String key = parserDefense(tok);
								CombatError er = combat.insert(key,c);
								if(er == CombatError.noAttack){
									(Console.getInstance()).write("Esa carta no esta atacando.");
								}
								else if(er == CombatError.cantDefense){
									(Console.getInstance()).write("Esa carta no puede ser defendida.");
								}
								else if(er == CombatError.needDefenseOther){
									(Console.getInstance()).write("No puedes defender a ese criatura, tienes que defender a otra.");
								}
								else{
									LinkedCard lin = combat.getCombat(key);
									lin.getCard().doAttack(combat,lin, w1);
									c.doDefense(combat,lin, w2);
									(Console.getInstance()).write("A la defensa, ¡Gloria o muerte!");
								}
							}
							else
								(Console.getInstance()).write("Debe introducir la carta a la que quiere defender");
						}
					}
					else
						(Console.getInstance()).write("Ese ser ya esta defendiendo");
				}
			}
			
			else{
				(Console.getInstance()).write("Opcion no valida, recuerde '--defender codigo' ");
			}
			text = chooseLoop(w1,w2,combat,null);
		}
		(Console.getInstance()).writeLog("defensePhase/>");
		if(text.equals("--salir"))
			return false;
		return true;
	}
	private String parserDefense(StringTokenizer tok){
		(Console.getInstance()).writeLog("<parseDefense");
		int pos = 0;
		String key = tok.nextToken();
		if(tok.hasMoreTokens()){
			try{
				pos = Integer.parseInt(tok.nextToken());
			}
			catch (Exception e) {
				(Console.getInstance()).write("El valor identificativo debe ser un numero, que sera su posicion partiendo de 1 segun se muestran");
			}
			if(pos != 1)
				key += " "+ pos;
		}
		(Console.getInstance()).writeLog("parseDefense/>");
		return key;
	}
	/**
	 * parser the code of the card. If there are repeated card
	 * @param tok , the line that user wrote
	 * @param w , warlock who wrote
	 * @param canEnviroment , if can use enviroment or not
	 * @return the card or null if not found
	 */
	private Card parserCombat(StringTokenizer tok,Warlock w,boolean canEnviroment){
		(Console.getInstance()).writeLog("<parserCombat");
		if(tok.hasMoreTokens()){
			String key = tok.nextToken();
			Card c =  w.getGameCard(key);
			if(c != null){
				int num = w.getNumCardEquals(key);
				if(num >1 && tok.hasMoreTokens()){
					int pos = 0;
					try{
						pos = Integer.parseInt(tok.nextToken());
					}
					catch (Exception e) {
						(Console.getInstance()).write("Tiene que indicar cual de todas las cartas iguales quiere utilizar, el valor identificativo debe ser un numero, que sera su posicion partiendo de 1 segun se muestran");
						(Console.getInstance()).writeLog("parserCombat/>");
						return null;
					}
					if(pos <= num && pos >= 0){
						try{
							c = w.getGameCard(pos-1, key);
						}catch (Exception e) {
							(Console.getInstance()).write("Se produjo un error humano, verifique que el valor de entrada es correcto");
						}
					}
					else{
						(Console.getInstance()).write("No tienes tantas cartas de ese tipo o el valor debe ser positivo");
						(Console.getInstance()).writeLog("parserCombat/>");
						return null;
					}
				}
				else if(num > 1){
					if(!(Console.getInstance()).optionTrue("¿Quieres asignar por defecto el 1?"))
						try{
							c = w.getGameCard(0, key);
						}catch (Exception e) {
							(Console.getInstance()).write("Se produjo un error humano, verifique que el valor de entrada es correcto");
						}
					else{
						(Console.getInstance()).write("Debes especificar cual de todos quieres asignar");
						(Console.getInstance()).writeLog("parserCombat/>");
						return null;
					}
				}
			}
			(Console.getInstance()).writeLog("parserCombat/>");
			if(c == null && !canEnviroment){
				(Console.getInstance()).write("Esa carta no existe o no esta en juego");
				return null;
			}
			return c;
			
		}
		else
			(Console.getInstance()).write("No ha introducido la carta con la que quiere hacer la accion");
		(Console.getInstance()).writeLog("parserCombat/>");
		return null;
	}
	/**
	 * do a intervention phase
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param combat , the info about all battlefield
	 * @return if go to next step of the game or exit
	 */
	private boolean interventionPhase(Warlock w1,Warlock w2,TreeCard combat){
		(Console.getInstance()).writeLog("<interventionPhase");
		if(InterventionLoop(w1, w2, combat)){
			if(InterventionLoop(w2, w1, combat)){
				(Console.getInstance()).writeLog("interventionPhase/>");
				return true;
			}
			else{
				(Console.getInstance()).writeLog("interventionPhase/>");
				return false;
			}
		}
		(Console.getInstance()).writeLog("interventionPhase/>");
		return false;
	}
	/**
	 * do a intervention phase of one player
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param combat , the info about all battlefield
	 * @return if go to next step of the game or exit
	 */
	private boolean InterventionLoop(Warlock w1,Warlock w2,TreeCard combat){
		(Console.getInstance()).writeLog("<interventionLoop");
		(Console.getInstance()).write("¡INTERVENCIONES! del brujo "+whoTurn(w1)+"(Recuerde que ahora puede jugar intervenciones 'codigo' 'propietario' 'objetivo' (depende de la intervencion))");
		String text = chooseLoop(w1,w2,combat,null);
		boolean finish = false;
		while(!text.equals("--salir") && !text.equals("--sig") && !finish){
			if(text.startsWith("--usar")){
				useAction(w1, w2, text,combat,true);
			}
			else if(text.startsWith("--combate")){
				(Console.getInstance()).write("Ya estas en la fase de combate");
			}
			else if(text.startsWith("--final")){
				(Console.getInstance()).write("¿Intentas huir? solo la muerte te sacara de aqui, cobarde");
			}
			else if(text.startsWith("--atacar")){
				(Console.getInstance()).write("Ahora no puedes asignar atacantes");
			}else if(text.startsWith("--defenser")){
				(Console.getInstance()).write("Ahora no puedes asignar defensores");
			}
			else{
				Card c = null;
				if((c = (checkIntervention(text, combat, w1, w2,null))) != null){
					if(!playIntervention(c,w1,w2, combat)){
						(Console.getInstance()).writeLog("interventionLoop/>");
						return false;
					}
					finish = true;
				}
			}
			if(!finish)
				text = chooseLoop(w1,w2,combat,null);
		}
		(Console.getInstance()).writeLog("interventionLoop/>");
		if(text.equals("--salir"))
			return false;
		return true;
	}
	/**
	 * do a loop of intervention until  warlocks pass
	 * @param c , the first intervention
	 * @param w1 , warlock who have the  turn
	 * @param w2 , enemy warlock
	 * @param combat , the info about all battlefield
	 * @return if go to next step of the game or exit
	 */
	private boolean playIntervention(Card c,Warlock w1,Warlock w2,TreeCard combat){
		(Console.getInstance()).writeLog("<playIntervention");
		StackCard inter = new StackCard();
		inter.insert(c);
		w1.discard(c.getCode());
		(Console.getInstance()).write("Intervencion \r\n"+c+"\nLe toca el turno al brujo "+whoTurn(w2));
		String text = chooseLoop(w2,w1,combat,inter);
		Warlock turn = w2;
		int acu = 0;
		while(!text.equals("--salir") && acu != 2){
			if(text.startsWith("--sig")){
				
				if(turn == w1)
					turn = w2;
				else
					turn = w1;
				(Console.getInstance()).write("Has decidido no jugar intervencion, se pasa el turno al otro brujo "+whoTurn(turn));
				acu++;
			}
			else{
				if(turn == w1)
					c = checkIntervention(text, combat, turn,w2,inter);
				else
					c = checkIntervention(text, combat, turn,w1,inter);
				if(c != null){
					inter.insert(c);
					turn.addGraveyard(c);
					turn.discard(c.getCode());
					(Console.getInstance()).write("Intervencion \r\n"+c+"\r\n jugada. \r\nLe toca el turno al otro brujo");
					if(turn == w1)
						turn = w2;
					else
						turn = w1;
					acu = 0;
				}

				
			}
			if(acu != 2){
				if(turn == w1)
					text = chooseLoop(turn,w2,combat,inter);
				else
					text = chooseLoop(turn,w1,combat,inter);	
			}
		}
		(Console.getInstance()).writeLog("playIntervention/>");
		if(text.equals("--salir"))
			return false;
		(Console.getInstance()).write("\r\nLos dos brujos habeis decidido dejar de jugar intervenciones, se resolvera la disputa \r\nPREPARADOS");
		(Judge.getInstance()).solveIntervention(inter);
		return true;
	}
	/**
	 * parse the line of the user wrote to take the info
	 * @param text , the line
	 * @param combat , the info about all battlefield
	 * @param w1 , warlock who have the  turn
	 * @param w2 , enemy warlock
	 * @param interventions , info about played interventions
	 * @return the intervention card or null if failed
	 */
	public Card checkIntervention(String text,TreeCard combat,Warlock w1,Warlock w2,StackCard interventions){
		(Console.getInstance()).writeLog("<checkIntervention");
		StringTokenizer tok = new StringTokenizer(text);
		if(!tok.hasMoreElements())
			return null;
		String key = tok.nextToken();
		String obj = null;
		if(playCard(w1,key,true,true) == PlayCard.goIntervention){
			Card tar = null;
			Warlock wObjt = null;
			if(tok.hasMoreElements()){
				obj = tok.nextToken();
				
				if(tok.hasMoreElements()){
					//StringTokenizer auxTok = new StringTokenizer(tok.toString());
					if(obj.equals("mio")){
						tar = parserCombat(tok, w1,false);
						wObjt = w1;
					}
					else if(obj.equals("enemigo")){
						tar = parserCombat(tok, w2,false);
						wObjt = w2;
					}
					/*else
						(Console.getInstance()).write("No has especificado el propietario sobre el que quieres realizar la accion.(recuerde 'mio' 'enemigo')");	*/
					
				}
				else{
					if(obj.equals("mio")){
						wObjt = w1;
					}
					else if(obj.equals("enemigo")){
						wObjt = w2;
					}
				}
			}
			Card c = w1.getHandCard(key);
			ErrorMen er = c.allOK(combat, tar,c, w1,w2,wObjt,interventions);
			(Console.getInstance()).writeLog("checkIntervention/>");
			
			if(er.allOK()){
				if(w1.payCard(c) == PlayCard.goIntervention)
					return c;
				else{
					(Console.getInstance()).write("No tienes suficiente cloro");	
				}
			}
			else if(er.needTarget()){
				(Console.getInstance()).write("Debes introducir el objetivo sobre quien quieras jugar la intervencion y su propietario");
			}
			else if(er.needAttackTarget())
				(Console.getInstance()).write("Debes introducir un objetivo que este atacando");
			else if(er.needWarlockTarget())
				(Console.getInstance()).write("Debes especificar el brujo sobre el que surge efecto ('mio', 'enemigo')");
			else if(er.needDefenderChoose()){
				(Console.getInstance()).write("Deben estar asignados los defensores para jugar esa accion");
			}
			else if(er.needAttackerChoose()){
				(Console.getInstance()).write("Deben estar asignados los atacantes para jugar esa accion");
			}
			else if(er.needInterventionTarget()){
				if(tar == null){	
					tar = foundInterventions(tok,obj,interventions);
				}
				else{
					 (Console.getInstance()).write("Debes introducir una intervencion como objetivo");
					 return null;
				}
				 er = c.allOK(combat, tar,c, w1,w2,wObjt,interventions);
				 if(er.needInterventionTarget()){
					 (Console.getInstance()).write("Debes introducir sobre que intervencion quieres lanzar la intervencion");
				 }
				 else if(er.allOK()){
					 return c;
				 }
			}
			return null;
			
		}
		(Console.getInstance()).writeLog("checkIntervention/>");
		return null;
		
	}
	/**
	 * 
	 * @param tok , text info
	 * @param interventions , the info about played interventions
	 * @return the cardor null if not found
	 */
	private Card foundInterventions(StringTokenizer tok,String obj,StackCard interventions){		
		(Console.getInstance()).writeLog("<foundInterventions");
		//if(tok.hasMoreTokens()){
			//String key = tok.nextToken();
		if(interventions != null && obj != null){
			Card c = null;
			int num = interventions.getNumCardsEquals(obj);
			if(num >1 && tok.hasMoreTokens()){
				int pos = 0;
				try{
					pos = Integer.parseInt(tok.nextToken());
				}
				catch (Exception e) {
					(Console.getInstance()).write("Tiene que indicar cual de todas las cartas iguales quieres, el valor identificativo debe ser un numero, que sera su posicion partiendo de 1 segun se muestran");
					return null;
				}
				if(pos <= num && pos >= 0){
					try{
						c = interventions.getCard(obj, pos-1); 
					}catch (Exception e) {
						(Console.getInstance()).write("Se produjo un error humano, verifique que el valor de entrada es correcto");
					}
				}
				else{
					(Console.getInstance()).write("No tienes tantas cartas de ese tipo o el valor debe ser positivo");
					(Console.getInstance()).writeLog("foundInterventions/>");
					return null;
				}
			}
			else if(num > 1){
				if(!(Console.getInstance()).optionTrue("¿Quieres asignar por defecto el 1?")){
					try{
						c = interventions.getCard(obj,0); 
					}catch (Exception e) {
						(Console.getInstance()).write("Se produjo un error humano, verifique que el valor de entrada es correcto");
					}
				}
				else{
					(Console.getInstance()).write("Debes especificar cual de todos quieres asignar");
					(Console.getInstance()).writeLog("foundInterventions/>");
					return null;
				}
			}
			(Console.getInstance()).writeLog("foundInterventions/>");
			if(c == null){
				(Console.getInstance()).write("Esa carta no existe o no esta en juego");
				return null;
			}
			return c;
		}
		return null;
		/*}
		else
			(Console.getInstance()).write("No ha introducido la carta con la que quiere hacer la accion");*/
	}
	/**
	 * 
	 * @param w , warlock who have the turn
	 * @return the name of the warlock who have the turn
	 */
	private String whoTurn(Warlock w){
		return w.getName();
	}
	/**
	 * parse the line and use a active action
	 * @param w1 , warlock who have the turn
	 * @param w2 , enemy warlock
	 * @param code , the line that user wrote
	 * @param combat , the info about all battlefield
	 * @param onlyPay , if only can use a action to add chlorine
	 */
	private void useAction(Warlock w1,Warlock w2,String code,TreeCard combat,boolean onlyPay){
		(Console.getInstance()).writeLog("<useAction");
		StringTokenizer tok = new StringTokenizer(code);
		tok.nextToken();
		if(tok.hasMoreTokens()){
			Card c = parserCombat(tok, w1, true);
			StringTokenizer key = new StringTokenizer(code);
			key.nextToken();
			//String self = tok.nextToken();
			String tar = null;
			int pos = 1;
			if(tok.hasMoreElements()){
				tar = tok.nextToken();
				if(tok.hasMoreElements())
					try{
						pos = Integer.parseInt(tok.nextToken());
					}catch (Exception e) {
					}
			}
			ErrorMen er = w1.useActiveAction(key.nextToken(),c,combat,tar,pos-1,w2,onlyPay);
			if(er == null)
				(Console.getInstance()).write("Esa carta no existe o no esta en juego");
			else if(!er.success())
				(Console.getInstance()).write("Esa carta no tiene ninguna habilidad que puedas activar ahora");

			else if(er.dischargedCard())
				(Console.getInstance()).write("Esa carta esta descargada y no se puede usar");
			else if(er.noAction())
				(Console.getInstance()).write("Esa carta no tiene ninguna accion activa");
			else if(er.needAttackTarget())
				(Console.getInstance()).write("Esa carta necesita un objetivo que este atacando");
			else if(er.targetNoExist())
				(Console.getInstance()).write("La carta sobre la que quieres aplicar la accion no existe");
			else if(er.needCombat())
				(Console.getInstance()).write("Esa carta necesita aplica sus efectos mientras haya un combate");
			else if(er.needSameCombatTarget())
				(Console.getInstance()).write("El objetivo debe estar en el mismo campo de batalla que la carta que usa la accion");
			else if(!er.allOK())
				(Console.getInstance()).write("Esa carta necesita un objetivo sobre el que realizar la accion (recuerde '--usar' 'codigo_carta' 'codigo_carta_objetivo')");
		}
		else
			(Console.getInstance()).write("No ha introducido la carta de la que quiere usar la habilidad (recuerde, '--usar codigo')");
		
		(Console.getInstance()).writeLog("useAction/>");
	}
	/**
	 * play card
	 * @param w , warlock who have the turn
	 * @param code , the code of the card
	 * @param enviroment , if you already use the environment or no
	 * @param isCombat , if we are in combat
	 * @return  that played
	 */
	private PlayCard playCard(Warlock w,String code,boolean enviroment,boolean isCombat){
		(Console.getInstance()).writeLog("<playCard");
		StringTokenizer tok = new StringTokenizer(code);
		if(tok.hasMoreTokens())
			code = tok.nextToken();
		Card c = w.getHandCard(code);
		if(c == null)
			return PlayCard.noFound;
		PlayCard aux = w.playCard(c,!enviroment,isCombat);
		if(aux == PlayCard.goEnviroment)
			return aux;
		else if(aux == PlayCard.noMoreEnviroment)
			(Console.getInstance()).write("Ya has jugado un entorno este turno");
		else if(aux == PlayCard.noEnviromentNow)
			(Console.getInstance()).write("No puedes jugar entornos ahora");
		else if(aux == PlayCard.noInterventionNow)
			(Console.getInstance()).write("No puedes jugar intervenciones ahora");
		else if(aux == PlayCard.noEntityNow)
			(Console.getInstance()).write("No puedes jugar seres ahora");
		else if(aux == PlayCard.noFound)
			(Console.getInstance()).write("Carta no existente, inserte otra");
		else if(aux == PlayCard.needMoreMana)
			(Console.getInstance()).write("No tienes suficiente cloro para jugar esa carta");
		
		(Console.getInstance()).writeLog("playCard/>");
		
		if(aux == PlayCard.goIntervention)			
			return PlayCard.goIntervention;
		return PlayCard.noError;
	}
	/**
	 * read the user line
	 * @param w1 , warlock who have the tur
	 * @param w2 , enemy warlock
	 * @param interventions , info about played interventions
	 * @return  user wrote
	 */
	private String chooseLoop(Warlock w1,Warlock w2,TreeCard combat,StackCard interventions){
		(Console.getInstance()).writeChose();
		String text = (Console.getInstance()).read();
		while(!text.equals("--salir") && !text.equals("--sig")&& !text.equals("--combate")&& !text.equals("--final")){	
			if(text.startsWith("--ayuda")){
				(Console.getInstance()).helpMenu();
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--vidas")){
				(Console.getInstance()).writeLifes(wa.getLife(), wb.getLife());
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--mano")){
				String aux = w1.handToString();
				if(aux.equals(""))
					(Console.getInstance()).write("No tienes mano");
				else
					(Console.getInstance()).write(aux);
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--entornos")){
				(Console.getInstance()).writeVs(w1.enviromentToString(), w2.enviromentToString());
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--cloro")){
				String aux = w1.manaToString();
				if(aux.equals(""))
					(Console.getInstance()).write("No tienes cloro, descarge un entorno para conseguirlo (recuerde --usar 'codigo del entorno')");
				else
					(Console.getInstance()).write(aux);
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--turno")){
				(Console.getInstance()).write("Es el turno del brujo "+whoTurn(w1));
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--limpiar")){
				(Console.getInstance()).clean();
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--juego")){
				(Console.getInstance()).writeVs(w1.inGameToString(), w2.inGameToString());
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--vercombate")){
				String watch =null;
				if(combat != null)
					watch = combat.toString();
				if(watch != null)
					(Console.getInstance()).write(watch);
				else
					(Console.getInstance()).write("Aun no hay cartas asignadas al combate (recuerde, --ataque 'codigo' para asignar un atacante)");
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--verintervenciones")){
				if(interventions == null){
					(Console.getInstance()).write("Aun no hay intervenciones en el limbo");
				}
				else{
					(Console.getInstance()).write(interventions.toString());	
				}
				(Console.getInstance()).writeChose();
			}
			else if(text.startsWith("--ververtedero")){
				(Console.getInstance()).writeVs(w1.graveyardToString(), w2.graveyardToString());
				(Console.getInstance()).writeChose();;
			}
			else if(text.startsWith("@cloro")){
				if((Configuration.getInstance()).isAdmin()){
					w1.addMana(new Cost(10, 10, 10, 10, 10));
				}
				else{
					(Console.getInstance()).write("No tienes permiso para hacer eso");
				}
			}
			else
			{
				return text;
			}
			text = (Console.getInstance()).read();
		}
		return text;
	}
	public void save(Warlock w1,String dir){
		(FileObject.getInstance()).setWa(wa);
		(FileObject.getInstance()).setWb(wb);
		
		if(w1 == wa){
			(FileObject.getInstance()).setwAt("a");
		}
		else{
			(FileObject.getInstance()).setwAt("b");
		}
		
		try {
			(FileObject.getInstance()).save(dir);
		} catch (FileError e) {
			(Console.getInstance()).write(e.getInfo());
			System.exit(-1);
		}
	}
	public void load(String dir){
		try {
			(FileObject.getInstance()).load(dir);
		} catch (FileError e) {
			(Console.getInstance()).write(e.getInfo());
			System.exit(-1);
		}
		wa = (FileObject.getInstance()).getWa();
		wb = (FileObject.getInstance()).getWb();
		
		Warlock w1;
		Warlock w2;
		String aux = (FileObject.getInstance()).getwAt();
		if(aux.equals("a")){
			w1 = wa;
			w2 = wb;
		}
		else{
			w1 = wb;
			w2 = wa;
		}
		(Console.getInstance()).clean();
		mainPlay(w1, w2);
	}
}
