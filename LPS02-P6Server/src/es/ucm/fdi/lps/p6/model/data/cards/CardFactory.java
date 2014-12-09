/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.cards;

import java.util.ArrayList;
import java.util.HashMap;

import es.ucm.fdi.lps.p6.DAO.DAOError;
import es.ucm.fdi.lps.p6.DAO.DTOCard;
import es.ucm.fdi.lps.p6.model.data.Cost;
import es.ucm.fdi.lps.p6.model.data.actions.active.AddMana;
import es.ucm.fdi.lps.p6.model.data.actions.active.AttackerAllStatsModifiers;
import es.ucm.fdi.lps.p6.model.data.actions.active.ChangeAllEntityElements;
import es.ucm.fdi.lps.p6.model.data.actions.active.ChangeEntity;
import es.ucm.fdi.lps.p6.model.data.actions.active.Copy;
import es.ucm.fdi.lps.p6.model.data.actions.active.Counter;
import es.ucm.fdi.lps.p6.model.data.actions.active.DamageAttackStat;
import es.ucm.fdi.lps.p6.model.data.actions.active.DamageManaAllEntity;
import es.ucm.fdi.lps.p6.model.data.actions.active.DamageWarlock;
import es.ucm.fdi.lps.p6.model.data.actions.active.DefenderAllStatsModifiers;
import es.ucm.fdi.lps.p6.model.data.actions.active.EnviromentExchange;
import es.ucm.fdi.lps.p6.model.data.actions.active.FinishGame;
import es.ucm.fdi.lps.p6.model.data.actions.active.Kill;
import es.ucm.fdi.lps.p6.model.data.actions.active.MultiplyStatsModifiers;
import es.ucm.fdi.lps.p6.model.data.actions.active.RotateAllEntities;
import es.ucm.fdi.lps.p6.model.data.actions.active.SeeHand;
import es.ucm.fdi.lps.p6.model.data.actions.active.StealHand;
import es.ucm.fdi.lps.p6.model.data.actions.active.TransformsAllMana;
import es.ucm.fdi.lps.p6.model.data.actions.active.Turn;
import es.ucm.fdi.lps.p6.model.data.actions.active.UnStopActive;
import es.ucm.fdi.lps.p6.model.data.actions.combat.AttackStatsModifier;
import es.ucm.fdi.lps.p6.model.data.actions.combat.DamageWarlockDefenderDie;
import es.ucm.fdi.lps.p6.model.data.actions.combat.DefenseStatsModifier;
import es.ucm.fdi.lps.p6.model.data.actions.combat.OverOneStatsModifier;
import es.ucm.fdi.lps.p6.model.data.actions.combat.TurnAllDefender;
import es.ucm.fdi.lps.p6.model.data.actions.combat.UpCard;
import es.ucm.fdi.lps.p6.model.data.actions.mixed.DrawMaxHand;
import es.ucm.fdi.lps.p6.model.data.actions.mixed.OnlyDefenseMe;
import es.ucm.fdi.lps.p6.model.data.actions.passive.CantTargetIntervention;
import es.ucm.fdi.lps.p6.model.data.actions.passive.MultiplyDamage;
import es.ucm.fdi.lps.p6.model.data.actions.passive.NoInitialized;
import es.ucm.fdi.lps.p6.model.data.actions.passive.StatsModifiersCardInGame;
import es.ucm.fdi.lps.p6.model.data.actions.passive.UnStopElem;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;

/** 
 * @author Roni
 *
 */
public class CardFactory {

	/*public static Card getCard(String code) {
		if(code.equals( "desierto")){	
			return new Enviroment(code, "Desierto Austral",Element.earth,new AddMana(new Cost(1, 0, 0, 0, 0),false,Element.earth),0,"<html>Permite recolectar 1 cloro de Tierra","desierto.jpg");
		}
		if(code.equals( "prado")){
			return new Enviroment(code, "Prado Infantil",Element.earth,new AddMana(new Cost(1, 0, 0, 0, 1),false,Element.earth),3,"<html>Permite recolectar 1 cloro de Tierra y 1 cloro generico", "prado.jpg");
		}
		
		if(code.equals( "cucaracha")){
			ArrayList<PassiveAction> pas = new  ArrayList<PassiveAction>();
			pas.add(new CantTargetIntervention());
			return new Entity(code, "Cucaracha Indomita",Element.earth,1,2,pas,new Cost(0, 0, 0, 0, 2),0,"No pueden jugarse intervenciones directamente sobre ella","cucaracha.jpg");
		}
		if(code.equals("lobo")){
			return new Entity(code, "Lobo Estepario",Element.earth,2,1,null,null,new TurnAllDefender(),new Cost(2, 0, 0, 0, 0),3,"Al final del turno se descargan los seres que se hayan defendido de él","lobo.jpg");
		}
		if(code.equals( "caballero")){
			CombatAction combat = new AttackStatsModifier(Element.spirit,true, 1, 1, true);
			return new Entity(code, "Caballero Tremebundo",Element.earth,3,2,combat,null,null,new Cost(2, 0, 0, 0, 1),1,"Cuando ataca gana +N/+0 siendo N el numero de seres de espiritu que le defienden","caballero.jpg");
			
		}
		
		if(code.equals( "furia")){
			return new Intervention(code, "Furia Parda",Element.earth,new MultiplyStatsModifiers(2,1),new Cost(1, 0, 0, 0, 0),3,"Multiplica por 2 el ataque de un ser durante una trifulca","furia.jpg");
		}
		if(code.equals( "raices")){
			return new Intervention(code, "Raices de Asfixia",Element.earth,new Turn(),new Cost(1, 0, 0, 0, 1),3,"Descarga por completo a un ser","raices.jpg");
		}
		
		
		
		if(code.equals( "fosa")){		
			return new Enviroment(code, "Fosa Septica",Element.sea,new AddMana(new Cost(0, 1, 0, 0, 0),false,Element.sea),0,"Permite recolectar 1 cloro de Mar","fosa.jpg");
		}
		if(code.equals( "peninsula")){
			return new Enviroment(code, "Peninsula Emerita",Element.sea,new AddMana(new Cost(0, 1, 0, 0, 1),false,Element.sea),3,"Permite recolectar 1 cloro de Mar y 1 cloro generico","peninsula.jpg");
		}
		
		if(code.equals( "lenguado")){
			return new Entity(code, "Lenguado Sibilino",Element.sea,2,0,new Cost(0, 1, 0, 0, 0),0,"","lenguado.jpg");
		}
		if(code.equals( "ballena")){
			CombatAction combat = new DefenseStatsModifier(Element.air, 0, -2);
			return new Entity(code, "Ballena Monje",Element.sea,1,5,null,combat,null,new Cost(0, 2, 0, 0, 2),3,"Pierde un -0/-2 al defenderse de seres de clase Aire", "ballena.jpg");
			
		}
		if(code.equals( "elemental")){
			CombatAction combat = new UpCard();
			return new Entity(code, "Elemental de Lluvia",Element.sea,3,3,null,null,combat,new Cost(0, 3, 0, 0, 1),2,"<html>[pasiva]Tras una trifulca, el brujo atacante debe<br> devolver a su mano al ser que haya atacado al Elemental</html>","elemental.jpg");
		}
		
		if(code.equals( "salitre")){
			return new Intervention(code,"Astucia del Salitre",Element.sea,new AttackerAllStatsModifiers(-2,0),new Cost(0, 1, 0, 0, 1),3,"Todos los seres atacantes de una trifulca reciben un -2/-0","salitre.jpg");
		}
		if(code.equals( "resaca")){
			return new Intervention(code, "Resaca Vespertina",Element.sea,new ChangeEntity(),new Cost(0, 2, 0, 0, 1),3,"Hace que un ser cambie de bando, yendo bajo el control del otro brujo","resaca.jpg");
		}
		
		
		
		if(code.equals( "nube")){
			return new Enviroment(code, "Nube Borrascosa",Element.air,new AddMana(new Cost(0, 0, 1, 0, 0),false,Element.air),0,"Permite recolectar 1 cloro de Aire","nube.jpg");
		}
		if(code.equals( "corriente")){
			return new Enviroment(code, "Corriente de Témpano",Element.air,new AddMana(new Cost(0, 0, 1, 0, 1),false,Element.air),3,"Permite recolectar 1 cloro de Aire y 1 cloro generico","corriente.jpg");
		}
		
		if(code.equals( "pajaro")){
			return new Entity(code, "Pajaro Ebanista",Element.air,0,1,new Cost(),0,"","pajaro.jpg");
		}
		if(code.equals( "albatros")){
			 ArrayList<PassiveAction> pas = new  ArrayList<PassiveAction>();
			 pas.add(new UnStopElem(Element.air));
			return new Entity(code, "Albatros Legendario",Element.air,2,3,pas,new Cost(0, 0, 1, 0, 2),1,"Cuando ataca no puede ser defendido con ningun ser de Aire","albatros.jpg");
		}
		if(code.equals( "tornado")){
			ArrayList<PassiveAction> pas = new  ArrayList<PassiveAction>();
			pas.add(new MultiplyDamage(2,1));
			return new Entity(code, "Tornado de Oriente",Element.air,4,0,pas,new Cost(0, 0, 2, 0, 1),0,"Multiplica por 2 su daño cuando daña directamente a un brujo","tornado.jpg");
		}
		
		if(code.equals( "vientos")){
			return new Intervention(code, "Vientos de Cambio",Element.air,new EnviromentExchange(),new Cost(0, 0, 3, 0, 0),1,"<html>Permite elegir uno o varios entornos del oponente<br>e intercambiarlos por otros tantos entornos tuyos que puedes elegir como quieras" +
					"\r\n[aclaracion] para usar esta carta simplemente usela (introduzca su codigo) y despues se le pediran los datos","vientos.jpg");
		}
		if(code.equals( "fluir")){		
			return new Intervention(code, "Fluir Gaseoso",Element.air,new UnStopActive(),new Cost(0, 0, 1, 0, 1),3,"Permite que un ser atacante no puede ser defendido en esta trifulca","fluir.jpg");
		}
		
		
		if(code.equals( "nave")){
			return new Enviroment(code, "Nave del Misterio",Element.spirit,new AddMana(new Cost(0, 0, 0, 1, 0),false,Element.spirit),0,"Permite recolectar 1 cloro de Espiritu","nave.jpg");
		}
		if(code.equals( "portal")){
			return new Enviroment(code, "Portal Intra-Mundos",Element.spirit,new AddMana(new Cost(0, 0, 0, 3, 0),false,Element.spirit),1,"Permite recolectar 3 cloros de Espiritu","portal.jpg");
		}
		
		if(code.equals( "espectro")){
			CombatAction combat = new AttackStatsModifier(Element.earth,true, 1, 0, true);
			CombatAction defense = new DefenseStatsModifier(Element.earth, 1, 0);
			
			return  new Entity(code, "Espectro Vampirico",Element.spirit,1,1,combat,defense
					,null,new Cost(0, 0, 0, 1, 0),3,"<html>Gana un +1/+0 al enfrentarse a seres de Tierra,<br>ademas si es atacante +N/+0 siendo N el numero de seres de tierra que le defienden","espectro.jpg");
		}
		if(code.equals( "aberracion")){
			String des = "<html>Si el brujo lo desea, en una trifulca la Aberración puede<br>copiar los valores de ataque, defensa y comportamiento del ser atacante.<br>\r\nuna vez usado, no se puede usar hasta el turno siguiente.<br>(aparecera el texto a modo informativo pero el uso no estara activo)" +
					"\r\n";
			return new Entity(code, "Aberracion Poltergeist",Element.spirit,2,2,new Copy(des,2,2,null,null,null,null),new Cost(0, 0, 0, 1, 2),3,des,"aberracion.jpg");
		}
		if(code.equals( "angel")){	
			CombatAction combat = new OverOneStatsModifier(0,1);
			return new Entity(code, "Angel de la Debacle",Element.spirit,6,2,combat,null,null,new Cost(0, 0, 0, 3, 2),2,"<html>Gana +0/+1 por cada ser que se defienda de él <br>a partir del primero","angel.jpg");
		}
		
		if(code.equals( "euforia")){
			return new Intervention(code, "Euforia Cleptomana",Element.spirit,new StealHand(),new Cost(0, 0, 0, 0, 3),3,"El brujo coge 2 cartas al azar de la mano de su oponente (ó 1 si no hay 2)", "euforia.jpg");
		}
		if(code.equals( "psicofonia")){
			return new Intervention(code, "Psicofonia Zen",Element.spirit,new SeeHand(),new Cost(0, 0, 0, 1, 1),3,"El brujo puede ver todas las cartas de la mano de su oponente","psicofonia.jpg");
		}
		
		
		
		
		//-----------1º exp---------------------------
		
		if(code.equals( "erial")){	
			return new Enviroment(code, "Erial Glamuroso",Element.earth,new AddMana(new Cost(1, 0, 0, 0, 0),false,Element.earth),0,"Permite recolectar 1 cloro de Tierra","erial.jpg");
		}
		
		if(code.equals( "alabardero")){	
			CombatAction combat = new AttackStatsModifier(Element.spirit, true,1, 1, true);
			PassiveAction passive = new StatsModifiersCardInGame(0, 2, "caballero");
			 ArrayList<PassiveAction> pas = new  ArrayList<PassiveAction>();
			 pas.add(passive);
			 String descriptionAttack = "<html>Cuando ataca gana +N/+N siendo N el numero de seres de espiritu que le defienden";
			 String decriptionPas = "<br>Si en la fase trifulca, el brujo que lo controla controla tambien la carta 'Caballero Tremebundo' suma +0/+2";
			return new Entity(code, "Alabardero Cimmerio",Element.earth,3,1,combat,null,null,null,pas,new Cost(2, 0, 0, 0, 3),0,descriptionAttack+decriptionPas,"alabardero.jpg");
		}
		if(code.equals("morlaco")){
			return new Entity(code, "Morlaco Vetusto",Element.earth,0,5,new OnlyDefenseMe(),null,null,new Cost(2, 0, 0, 0, 2),3,"<html>Cuando se aigna al combate para atacar,<br>los seres enemigos solo pueden defenderle a el(si quieren defender)","morlaco.jpg");
		}
		if(code.equals("felino")){
			return new Entity(code, "Felino Lascivo",Element.earth,1,1,new Cost(1, 0, 0, 0, 0),0,"","felino.jpg");
		}
		
		
		if(code.equals( "colleja")){
			return new Intervention(code, "Colleja Mnemotecnica",Element.earth,new DamageAttackStat(),new Cost(1, 0, 0, 0, 1),3,"El ser objetivo sufre tantos puntos de vida como ataque tiene","colleja.jpg");
		}
		if(code.equals( "resquemor")){
			return new Intervention(code, "Resquemor del Labriego",Element.earth,new DefenderAllStatsModifiers(0, -2),new Cost(3, 0, 0, 0, 0),3,"<html>Esta intervencion debe jugarse cuando se hayan asignado defensores.<br>\r\nResta 2 puntos de defensa a todos los defensores","resquemor.jpg");
		}
		
		

		if(code.equals( "isla")){		
			return new Enviroment(code, "Isla de Lesbos",Element.sea,new AddMana(new Cost(0, 1, 0, 0, 0),true,Element.sea),1,"<html>Permite recolectar 1 cloro de Mar,<br>ademas si solo controla entornos del tipo Mar\r\nse añaden n Mares adiciones,<br>siendo n el numero de entornos"
					,"isla.jpg");
		}
		
		if(code.equals( "trucha")){
			CombatAction combat = new AttackStatsModifier(Element.sea, false,0, 1, true);//new AttackStatsModifierElement(0, 1, Element.sea);
			return new Entity(code, "Hombre-Trucha",Element.sea,2,1,combat,null,null,new Cost(0, 1, 0, 0, 2),3,"Aumenta N puntos su defensa donde N es el numero de seres que le defiende que no sean de tipo Mar","trucha.jpg");
			
		}
		
		if(code.equals( "tsunami")){
			return new Intervention(code,"Tsunami Tres Delicias",Element.sea,new DamageManaAllEntity(),new Cost(0, 2, 0, 0, 1),3,"<html>Todos los seres del brujo enemigo sufren tanto daño<br>como entornos descargados por el brujo que lanza la intervencion",
					"tsunami.jpg");
		}
		if(code.equals( "marejadilla")){
			ArrayList<Element> elem =  new ArrayList<Element>();
			elem.add(Element.sea);
			elem.add(Element.earth);
			return new Intervention(code,"Fuerte Marejadilla",Element.sea,new ChangeAllEntityElements(elem),new Cost(0, 4, 0, 0, 0),3,"<html>Todas los seres de tierra o mar(cualquiera de las dos) del brujo enemigo<br> pasan al control del quien juegue la inervencion","marejadilla.jpg");
		}
		
		
		
		if(code.equals( "odin")){
			 ArrayList<PassiveAction> pas = new  ArrayList<PassiveAction>();
			 pas.add(new CantTargetIntervention());
			return new Entity(code, "Odin el Mismisimo",Element.air,6,3,new DamageWarlockDefenderDie(1),null,null,null,pas,new Cost(0, 0, 4, 0, 2),1,"No se puede jugar intervenciones directamente sobre el.\r\n[pasiva]Cuando ataca, las criaturas que se hayan defendido de el se retiran del juego\r\ny el brujo propietario pierde 1 punto de vida",
					"odin.jpg");
		}
		
		if(code.equals( "rayos")){		
			return new Intervention(code, "Rayos y Centellas",Element.air,new Kill(),new Cost(0, 0, 3, 0, 0),3,"Mata el ser objetivo enviandolo al vertedero","rayos.jpg");
		}
		if(code.equals( "ansia")){		
			return new Intervention(code, "Ansia Viva",Element.air,new DrawMaxHand(),new Cost(0, 0, 1, 0, 4),1,"<html>Esta carta se lanza con un brujo como objetivo y permanece activa durante toda la partida.<br>Al principio del mantenimiento, cuando el brujo robe cartas, roba hasta tener el maximo de cartas en mano",
					 "ansia.jpg");
		}
		
		
		if(code.equals( "nodo")){
			return new Enviroment(code, "Nodo necromental",Element.spirit,new TransformsAllMana(new Cost(0, 0, 0, 1, 0),Element.spirit),3,"<html>Permite recolectar 1 cloro de Espiritu,<br>además todos tus cloros en tu banco de cloro pasaran a ser de tipo Espiritu","nodo.jpg");
		}
		
		if(code.equals( "impugnacion")){
			return new Intervention(code, "Impugnación Grisacea",Element.spirit,new Counter(),new Cost(0, 0, 0, 2, 0),0,"Anula una intervencion jugada pero no resuelta.","impugnacion.jpg");
		}
		if(code.equals( "emulsion")){
			return new Intervention(code, "Emulsión Telecrata",Element.spirit,new RotateAllEntities(),new Cost(1, 1, 1, 1, 0),3,"<html>Todos los seres de la partida hacen una rotacion de la siguiente forma.<br>\r\n-Los seres de la zona de juego pasan al vertedero.<br>\r\n-Los seres del vertedero al mazo.\nLos seres del mazo a la mano.<br>\r\n-Los seres de la mano pasan al juego sin coste\r\n<br>Odin el mismisimo no se vera afectado por esta carta",
					"emulsion.jpg");
		}
		return null;
	}*/
	public static Card getCard(DTOCard dto,boolean act){
		if(dto == null){
			return null;
		}
		String type = dto.getType();
		Card aux = null;
		if(type.equals("entity")){
			aux = new Entity(dto.getCode(), dto.getName(), dto.getElement(), dto.getAttack(), dto.getDefense(),
					dto.getActionAttack(),dto.getActionDefense(),dto.getActionFinally(),dto.getActionActive(),dto.getActionPassive(), dto.getCost(),
					dto.getMax(), dto.getDescription(), dto.getDirImg());
			if(act){
				//aux.setBuff(dto.getBuff());
				//aux.setTurn(dto.isTurn());
				aux.setId(dto.getId());
				//aux.setLife(dto.getLife());
			}
			return aux;
		}
		else if(type.equals("enviroment")){
			aux =  new Enviroment(dto.getCode(), dto.getName(), dto.getElement(), dto.getActionActive(), dto.getMax(), dto.getDescription(), dto.getDirImg());		
			if(act){
				//aux.setBuff(dto.getBuff());
				//aux.setTurn(dto.isTurn());
				aux.setId(dto.getId());
			}
			return aux;
		}
		else if(type.equals("intervention")){
			aux =  new Intervention(dto.getCode(), dto.getName(), dto.getElement(), dto.getActionActive(), dto.getCost(), dto.getMax(), dto.getDescription(), dto.getDirImg());
			if(act){
			//	aux.setBuff(dto.getBuff());
				aux.setId(dto.getId());
			}
			return aux;
		}
		return null;
	}
	public static Object getAction(String name,HashMap<String, Object> param ) throws DAOError{
		//Active
		try{
			
		if(name.equals("addMana")){	
			boolean equals =  Boolean.parseBoolean((String) param.get("allEquals"));
			
			Element e = Element.valueOf((String) param.get("selfElement"));
			
			return new AddMana((Cost) param.get("mana"),equals, e);
		}
		else if(name.equals("AttackerAllStatsModifiers")){
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			
			return new AttackerAllStatsModifiers(at, def);
		}
		else if(name.equals("ChangeAllEntityElements")){
			return new ChangeAllEntityElements((ArrayList<Element>) param.get("elements"));	
		}
		else if(name.equals("ChangeEntity")){
			return new ChangeEntity();
		}
		else if(name.equals("Copy")){
			return new Copy();
		}
		else if(name.equals("Counter")){
			return new Counter();
		}
		else if(name.equals("DamageAttackStat")){
			return new DamageAttackStat();
		}
		else if(name.equals("DamageManaAllEntity")){
			return new DamageManaAllEntity();
		}
		else if(name.equals("DefenderAllStatsModifiers")){
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			return new DefenderAllStatsModifiers(at, def);
		}
		else if(name.equals("EnviromentExchange")){
			return new EnviromentExchange();
		}
		else if(name.equals("Kill")){
			return new Kill();
		}
		else if(name.equals("MultiplyStatsModifiers")){
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			boolean permanent = Boolean.parseBoolean((String) param.get("permanent"));
			boolean needCombat = Boolean.parseBoolean((String) param.get("needCombat"));
			return new MultiplyStatsModifiers(at, def,permanent,needCombat);
		}
		else if(name.equals("RotateAllEntities")){
			return new RotateAllEntities();
		}
		else if(name.equals("SeeHand")){
			return new SeeHand();
		}
		else if(name.equals("StealHand")){
			int num = Integer.parseInt((String) param.get("number"));
			return new StealHand(num);
		}
		else if(name.equals("TransformsAllMana")){
			Element e = Element.valueOf((String) param.get("element"));
			return new TransformsAllMana((Cost) param.get("mana"), e);
		}
		else if(name.equals("Turn")){
			return new Turn();
		}
		else if(name.equals("UnStopActive")){
			return new UnStopActive();
		}
		
		//combat
		else if(name.equals("AttackStatsModifier")){
			boolean isPlus =  Boolean.parseBoolean((String) param.get("isElementPlus"));
			boolean canAcu =  Boolean.parseBoolean((String) param.get("canAcu"));
			Element e = Element.valueOf((String) param.get("element"));
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			
			return new AttackStatsModifier(e, isPlus, at, def, canAcu);
		}
		else if(name.equals("DamageWarlockDefenderDie")){
			int value = Integer.parseInt((String) param.get("damage"));
			return new DamageWarlockDefenderDie(value);
		}
		else if(name.equals("DefenseStatsModifier")){
			Element e = Element.valueOf((String) param.get("element"));
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			
			return new DefenseStatsModifier(e, at, def);
		}
		else if(name.equals("OverOneStatsModifier")){
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			
			return new OverOneStatsModifier(at, def);
		}
		else if(name.equals("TurnAllDefender")){
			return new TurnAllDefender();
		}
		else if(name.equals("UpCard")){
			return new UpCard();
		}
		//passive
		else if(name.equals("CantTargetIntervention")){
			return new CantTargetIntervention();
		}
		else if(name.equals("MultiplyDamage")){
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			return new MultiplyDamage(at,def);
		}
		else if(name.equals("StatsModifiersCardInGame")){
			int at = Integer.parseInt((String) param.get("attack"));
			int def = Integer.parseInt((String) param.get("defense"));
			return new StatsModifiersCardInGame(at, def, (String) param.get("code"));
		}
		else if(name.equals("UnStopElem")){
			Element e = Element.valueOf((String) param.get("element"));
			return new UnStopElem(e);
		}
		
		//mixed
		else if(name.equals("DrawMaxHand")){
			return new DrawMaxHand();
		}
		else if(name.equals("OnlyDefenseMe")){
			return new OnlyDefenseMe();
		}
		else if(name.equals("FinishGame")){
			return new FinishGame();
		}
		else if(name.equals("DamageWarlock")){
			int pro = Integer.parseInt((String) param.get("probability"));
			int value = Integer.parseInt((String) param.get("number"));
			return new DamageWarlock(pro,value);
		}
		else if(name.equals("NoInitialized")){
			return new NoInitialized();
		}
		
		
		}catch (Exception e) {
			throw new DAOError("Se produjo un error leyendo el comportamiento "+name+" tiene valores invalidos o faltan valores");
		}
		throw new DAOError("La intervencion "+name+" no se reconoce como válida");
	}
}
