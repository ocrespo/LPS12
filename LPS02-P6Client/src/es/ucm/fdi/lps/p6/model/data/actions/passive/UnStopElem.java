package es.ucm.fdi.lps.p6.model.data.actions.passive;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;

/**
 * 
 * @author Roni
 *
 */

public class UnStopElem implements PassiveAction ,Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Element elem;
	
	public UnStopElem(Element elem){
		this.elem = elem;
	}
	@Override
	public ErrorMen done(LinkedCard lin,Card self,Warlock w1) {
		(Console.getInstance()).writeLog("done UnStopElem");
		if(lin != null && lin.getCard().getElement() == elem)
			return new ErrorMen(ErrorEnum.cantDefense);
		return new ErrorMen(ErrorEnum.allOk);
	}
	@Override
	public void unDone() {
	}

	/**
	 * clone
	 */
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
        }
        return obj;
    }
}
