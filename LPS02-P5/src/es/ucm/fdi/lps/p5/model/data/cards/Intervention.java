/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.cards;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Cost;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.info.StackCard;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;



/**
 * @author Roni
 *
 */
public class Intervention extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Cost cost = null;
	
	private ActiveAction actionActive = null;
	
	public Intervention(String code, String name,Element element ,ActiveAction actionActive,Cost cost
			,int max,String description,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.actionActive = actionActive;
		this.cost = cost;
	}
	public Intervention(String code,String name,Element element,int max,String description,String dirImg){
		super(code, name, element, max, description, dirImg);
	}
	/**
	 * @return the cost
	 */
	@Override
	public Cost getCost() {
		return cost;
	}
	@Override
	public String toString(){
		String type = "\r\nCoste: "+cost.toString();
		String elem = null;
		if(element == Element.earth)
			elem = "tierra";
		else if(element == Element.sea)
			elem = "mar";
		else if(element == Element.air)
			elem = "aire";
		else if(element == Element.spirit)
			elem = "espiritu";
					
		return type+"\r\nCodigo: "+code+"\r\nNombre: "+name+"\r\nElemento: "+elem+"\r\nTipo: Intervencion\r\nDescripcion: "+description+type;		
	}
	@Override
	public ErrorMen doActive(Warlock w){
		(Console.getInstance()).writeLog("doActive Intervention");
		if(actionActive.done(w))
			return new ErrorMen(ErrorEnum.allOk);
		return new ErrorMen(ErrorEnum.noSuccess);
	}
	@Override
	public PlayCard insert(boolean canEnvi,boolean canInCombat) {
		(Console.getInstance()).writeLog("insert Intervention");
		if(canInCombat)
			return PlayCard.goIntervention;
		return PlayCard.noInterventionNow;
	}
	@Override
	public ErrorMen allOK(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj,StackCard interventions) {
		return actionActive.allOk(combat, tar,self, w1, w2, wObj,interventions);
	}
	@Override
	public ActiveAction getActionActive() {
		return actionActive;
	}
	@Override
	public void setActionActive(ActiveAction act) {
		actionActive = act;
		
	}
	@Override
	public void unDone() {
		actionActive.unDone();
	}
	@Override
	public String toStringType() {
		return "Intervencion";
	}
}
