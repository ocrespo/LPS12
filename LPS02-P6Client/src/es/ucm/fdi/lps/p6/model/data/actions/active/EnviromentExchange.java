/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data.actions.active;

import java.util.StringTokenizer;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

/**
 * @author Roni
 *
 */
public class EnviromentExchange implements ActiveAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Warlock w1;
	private Warlock w2;
	private LinkedCard lin1 = null;
	private LinkedCard lin2 = null;
	@Override
	public boolean done(Warlock w){
		(Console.getInstance()).writeLog("<done EnviromentExchange");
		Card c = null;
		while(lin1 != null && !lin1.isEmpty()){
			c = lin1.removeTop();
			w2.addEnviroment(c);
			w1.removeEnviromentCard(c);
		}
		while(lin2 != null && !lin2.isEmpty()){
			c = lin2.removeTop();
			w1.addEnviroment(c);
			w2.removeEnviromentCard(c);
		}
		if(lin1 == null || lin2 == null)
			return false;
		return true;
	}
	private boolean parserChoose(){
		(Console.getInstance()).writeLog("<parserChoose EnviromentExchange");
		(Console.getInstance()).write("Señale cuantos entornos quieres intercambiar y, acontinuacion, cuales\n(Recuerde 'num' 'codigo' si hay mas de uno igual indique un numero con su posicion segun se muestra)");
		StringTokenizer text = new StringTokenizer((Console.getInstance()).read());
		if(text.hasMoreTokens()){
			int num = 0;
			try{
				num = Integer.parseInt(text.nextToken());
			}
			catch (Exception e) {
				(Console.getInstance()).write("El numero debe ser un entero");
				return false;
			}
			
			lin1 = new LinkedCard();
			lin2 = new LinkedCard();
			if(parseLoop(w1, lin1, text, num)){
				/*if(text.hasMoreElements()){
					(Console.getInstance()).write("Has introducido menos cartas de las que querias intercambiar");
					return false;
				}*/
				(Console.getInstance()).write("Señale ahora las de su enemigo");
				text = new StringTokenizer((Console.getInstance()).read());
				if(parseLoop(w2, lin2, text, num))
					return true;
				return false;
			}
			return false;		
			
		}
		return true;
	}
	private boolean parseLoop(Warlock w,LinkedCard lin,StringTokenizer text,int num){
		(Console.getInstance()).writeLog("parseLoop EnviromentExchange");
		int count = 0;
		String code = null;
		int pos = 0;
		Card c = null;
		int numCards = 0;
		while(text.hasMoreElements() && count<num){
			code = text.nextToken();
			c = w.getEnviroment(code);
			if(c != null){
				numCards = w.getNumEnviromentEquals(code);			
				if(numCards > 1){
					try{
						pos = Integer.parseInt(text.nextToken());
					}
					catch (Exception e) {
						(Console.getInstance()).write("El numero debe ser un entero o no has especificado cual de todas quieres cambiar");
						return false;
					}
					if(pos > numCards){
						(Console.getInstance()).write("No hay tantas cartas de "+code);
						return false;
					}
					lin.insert(w.getEnviromentCard(pos-1, code));
					count++;
				}
				else{
					lin.insert(c);
					count++;
				}
			}
			else{
				(Console.getInstance()).write("Esa carta no esta en juego o no existe");
				return false;
			}

		}
		if(count < num){
			(Console.getInstance()).write("Has introducido menos cartas de las que querias intercambiar");
			return false;	
		}
		else if(count == num && text.hasMoreElements()){
			(Console.getInstance()).write("Has introducido mas cartas de las que querias intercambiar");
			return false;	
		}
		return true;
	}
	@Override
	public ErrorMen allOk(TreeCard combat, Card tar,Card self, Warlock w1, Warlock w2,Warlock wObj, StackCard interventions) {
		(Console.getInstance()).writeLog("<allOk EnviromentExchange");
		if(lin1!= null && lin2 != null && tar == null && lin1.size() == lin2.size()){
			return new ErrorMen(ErrorEnum.allOk);
		}
	
		this.w1 = w1;
		this.w2 = w2;
		if((Configuration.getInstance()).isConsole()){
			if(parserChoose())
				return new ErrorMen(ErrorEnum.allOk);
			return new ErrorMen(ErrorEnum.needTargetEnviroment);
		}
		if(w1.getNumEnoviroments() <= 0 || w2.getNumEnoviroments() <= 0){
			return new ErrorMen(ErrorEnum.needHaveEnviroment);
		}
		if(tar == null){
			return new ErrorMen(ErrorEnum.goExchangeEnviroment);
		}
		boolean isInW1 = w1.foundInEnviroment(tar);
		boolean isInW2 =  w2.foundInEnviroment(tar);
		if(!isInW1 && !isInW2){
			return new ErrorMen(ErrorEnum.needTargetEnviroment);
		}
		if(lin1 == null){
			lin1 = new LinkedCard();
		}
		if(lin2 == null){
			lin2 = new LinkedCard();
		}
		if(lin1.found(tar) || lin2.found(tar)){
			return new ErrorMen(ErrorEnum.targetAlreadySelect);
		}
		if((lin1.size() == w2.getNumEnoviroments() && isInW1)|| ( lin2.size() ==  w1.getNumEnoviroments() && isInW2 )){
			return new ErrorMen(ErrorEnum.cantExchangeMoreEnviroment);
		}
		if(isInW1){
			lin1.insert(tar);
		}
		else if(isInW2){
			lin2.insert(tar);
		}
		if(lin1.size() == lin2.size()){
			return new ErrorMen(ErrorEnum.canExchangeEnviroment);
		}
		return new ErrorMen(ErrorEnum.goExchangeEnviroment);
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
