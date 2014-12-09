/**
 * 
 */
package es.ucm.fdi.lps.p5.model.data.actions.passive;

import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;


/**
 * @author Roni
 *
 */
public class CantTargetIntervention implements PassiveAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public ErrorMen done(LinkedCard lin,Card self,Warlock w1) {
		(Console.getInstance()).writeLog("done CantTargetIntervention");
		return new ErrorMen(ErrorEnum.cantTargetIntervention);
	}

	@Override
	public void unDone() {
	}

}
