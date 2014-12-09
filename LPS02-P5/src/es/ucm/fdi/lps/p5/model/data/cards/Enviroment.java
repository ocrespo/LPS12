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
import es.ucm.fdi.lps.p5.model.game.Judge;



/**
 * @author Roni
 *
 */
public class Enviroment extends Card{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean turn = false;	
	private ActiveAction actionActive = null;
	
	public Enviroment(String code, String name,Element element ,ActiveAction actionActive,int max,String description,String dirImg){
		super(code, name,element ,max,description,dirImg);
		this.actionActive = actionActive;
	}
	public Enviroment(String code,String name,Element element,int max,String description,String dirImg){
		super(code, name, element, max, description, dirImg);
	}
	@Override
	public String toString(){
		String aux = "";
		if(turn)
			aux = "Si";
		else
			aux="No";
		String type = "\r\nSin coste \r\nDescargado: "+aux;
		
		String elem = null;
		if(element == Element.earth)
			elem = "tierra";
		else if(element == Element.sea)
			elem = "mar";
		else if(element == Element.air)
			elem = "aire";
		else if(element == Element.spirit)
			elem = "espiritu";
					
		return type+"\r\nCodigo: "+code+"\r\nNombre: "+name+"\r\nElemento: "+elem+"\r\nTipo: Entorno\r\nDescripcion: "+description+type;
	}
	public void ini(){
		turn = false;
		buff = "";
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	public void iniBuff(){
		buff = "";
		(Judge.getInstance()).setChanged();
		(Judge.getInstance()).notifyRefreshStatsCard(this);
	}
	@Override
	public Cost getCost() {
		return null;
	}
	@Override
	public ErrorMen doActive(Warlock w) {
		(Console.getInstance()).writeLog("doActive Enviroment");
		if(actionActive == null)
			return new ErrorMen(ErrorEnum.noAction);
		if(turn)
			return new ErrorMen(ErrorEnum.dischargedCard);
		actionActive.done(w);
		turn = true;
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public PlayCard insert(boolean canEnvi,boolean canInCombat) {
		(Console.getInstance()).writeLog("insert Enviroment");
		if(canInCombat){
			return PlayCard.noEnviromentNow;
		}
		else if(!canEnvi && !canInCombat)
			return PlayCard.noMoreEnviroment;
		return PlayCard.goEnviroment;
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
	public boolean haveActiveAction(){
		return actionActive != null;
	}
	@Override
	public String toStringType() {
		return "Entorno";
	}
	/**
	 * @return the turn
	 */
	@Override
	public boolean canUse() {
		return !turn;
	}
}
