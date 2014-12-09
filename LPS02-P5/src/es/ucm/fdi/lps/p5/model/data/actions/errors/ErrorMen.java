/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.errors;


/**
 * @author Roni
 *
 */
public class ErrorMen {
	public enum ErrorEnum{allOk,cantDefense,cantTargetIntervention,needCombat,needTarget,needAttackTarget,dischargedCard,noAction,noSuccess,targetNoExist,nextTurn
		,needInterventionTarget,needIsAttack,needDefenderChoose,cantDrawCardDeck,needWarlockTarget,needSameCombatTarget,goToCombatPermanent
		,needDefenseOther,needTargetEnviroment,needAttackerChoose,needEntityTarget,needHaveEnviroment,goExchangeEnviroment,canExchangeEnviroment,
		cantExchangeMoreEnviroment,targetAlreadySelect,onlyTargetWarlock,cantInitialized};
	private ErrorEnum error = null;
	
	/**
	 * parametrizer builder
	 * @param er ErrorMen
	 */
	public ErrorMen(ErrorEnum er){
		error = er;
	}
	/**
	 * 
	 * @return if all ok
	 */
	public boolean allOK(){
		return error == ErrorEnum.allOk;
	}
	/**
	 * 
	 * @return if need a target card
	 */
	public boolean needTarget(){
		return error == ErrorEnum.needTarget;
	}
	/**
	 * 
	 * @return if can defense
	 */
	public boolean canDefense(){
		return error != ErrorEnum.cantDefense;
	}
	/**
	 * 
	 * @return if can target
	 */
	public boolean canTargetIntervention(){
		return error != ErrorEnum.cantTargetIntervention;
	}
	/**
	 * 
	 * @return if need combat
	 */
	public boolean needCombat(){
		return error == ErrorEnum.needCombat;
	}
	/**
	 * 
	 * @return if need attack target
	 */
	public boolean needAttackTarget(){
		return error == ErrorEnum.needAttackTarget;
	}
	/**
	 * 
	 * @return if card is discharged
	 */
	public boolean dischargedCard(){
		return error == ErrorEnum.dischargedCard;
	}
	/**
	 * 
	 * @return if the card dont have action
	 */
	public boolean noAction(){
		return error == ErrorEnum.noAction;
	}
	/**
	 * 
	 * @return if the action success
	 */
	public boolean success(){
		return error != ErrorEnum.noSuccess;
	}
	/**
	 * 
	 * @return if target not exist
	 */
	public boolean targetNoExist(){
		return error == ErrorEnum.targetNoExist;
	}
	/**
	 * 
	 * @return if the action is for next turn
	 */
	public boolean nextTurn(){
		return error == ErrorEnum.nextTurn;
	}
	/**
	 * 
	 * @return if need intervention target
	 */
	public boolean needInterventionTarget(){
		return error == ErrorEnum.needInterventionTarget;
	}
	/**
	 * 
	 * @return if need that card is in attack
	 */
	public boolean needIsAttack(){
		return error == ErrorEnum.needIsAttack;
	}
	/**
	 * 
	 * @return if its necessary a defenders are choosen
	 */
	public boolean needDefenderChoose(){
		return error == ErrorEnum.needDefenderChoose;
	}
	/**
	 * 
	 * @return if can draw card
	 */
	public boolean cantDrawCardDeck(){
		return error == ErrorEnum.cantDrawCardDeck;
	}
	/**
	 * 
	 * @return if need warlock target
	 */
	public boolean needWarlockTarget(){
		return error == ErrorEnum.needWarlockTarget;
	}
	/**
	 * 
	 * @return if need that card there is in the same combat that target
	 */
	public boolean needSameCombatTarget(){
		return error == ErrorEnum.needSameCombatTarget;
	}
	/**
	 * 
	 * @return if this action need do a permanent action in combat
	 */
	public boolean goToCombatPermanent(){
		return error == ErrorEnum.goToCombatPermanent;
	}
	/**
	 * 
	 * @return if the card have to defender other card
	 */
	public boolean needDefenseOther(){
		return error == ErrorEnum.needDefenseOther;
	}
	/**
	 * 
	 * @return if need enviroment target
	 */
	public boolean needTargetEnviroment(){
		return error == ErrorEnum.needTargetEnviroment;
	}
	/**
	 * 
	 * @return if need attackers are choosen
	 */
	public boolean needAttackerChoose(){
		return error == ErrorEnum.needAttackerChoose;
	}
	/**
	 * 
	 * @return if need entity target
	 */
	public boolean needEntityTarget(){
		return error == ErrorEnum.needEntityTarget;
	}
	/**
	 * 
	 * @return if need have any enviroment card
	 */
	public boolean needHaveEnviroment(){
		return error == ErrorEnum.needHaveEnviroment;
	}
	/**
	 * 
	 * @return if needgot o exchange enviroments
	 */
	public boolean goExchangeEnviroment(){
		return error == ErrorEnum.goExchangeEnviroment;
	}
	/**
	 * 
	 * @return if cand exchange enviroments
	 */
	public boolean canExchangeEnviroment(){
		return error == ErrorEnum.canExchangeEnviroment;
	}
	/**
	 * 
	 * @return if cant exchange more enviroments
	 */
	public boolean cantExchangeMoreEnviroment(){
		return error == ErrorEnum.cantExchangeMoreEnviroment;
	}
	/**
	 * 
	 * @return if this target already was selected
	 */
	public boolean targetAlreadySelect(){
		return error == ErrorEnum.targetAlreadySelect;
	}
	/**
	 * 
	 * @return if this target  need be warlock
	 */
	public boolean onlyTargetWarlock(){
		return error == ErrorEnum.onlyTargetWarlock;
	}
	/**
	 * 
	 * @return if this target  cant inicialitedk
	 */
	public boolean cantInitialized(){
		return error == ErrorEnum.cantInitialized;
	}
	
}
