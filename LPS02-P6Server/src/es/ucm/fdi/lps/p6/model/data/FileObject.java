/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import es.ucm.fdi.lps.p6.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.game.Model.Phase;

/**
 * @author Roni
 *
 */
public class FileObject {
	private static FileObject SINGLETON = null;
	
	private Warlock wa;
	private Warlock wb;
	private String wAt;
	private String wTurn;
	private Phase phase;
	
	private String preInterventionTurn;
	private boolean enviroment ;
	private boolean isCombat ;
	private TreeCard combat ;
	private StackCard interventions ;
	private boolean onlyPay ;
	private Phase inInterventionLoop;
	private  int passIntervention ;
	
	private Stack<Card> unDoInterventions = null;
	private Stack<ActiveAction> undo = new Stack<ActiveAction>();
	
	/**
	 * Constructor por defecto
	 */
	private FileObject() {
	}

	@SuppressWarnings("unused")
	private synchronized static void createInstance() {
		if (SINGLETON == null) {
			SINGLETON = new FileObject();
		}
	}
	/**
	 * return the instance of Configuration
	 * @return Configuration
	 */
	public final static FileObject getInstance() {
		if (SINGLETON == null)
			SINGLETON = new FileObject();

		return SINGLETON;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * @param wa the wa to set
	 */
	public void setWa(Warlock wa) {
		this.wa = wa;
	}

	/**
	 * @param wb the wb to set
	 */
	public void setWb(Warlock wb) {
		this.wb = wb;
	}
	/**
	 * @return the wa
	 */
	public Warlock getWa() {
		return wa;
	}

	/**
	 * @return the wb
	 */
	public Warlock getWb() {
		return wb;
	}

	/**
	 * @return the phase
	 */
	public Phase getPhase() {
		return phase;
	}

	/**
	 * @param phase the phase to set
	 */
	public void setPhase(Phase phase) {
		this.phase = phase;
	}

	/**
	 * @return the wAt
	 */
	public String getwAt() {
		return wAt;
	}

	/**
	 * @param wAt the wAt to set
	 */
	public void setwAt(String wAt) {
		this.wAt = wAt;
	}

	/**
	 * @return the wTurn
	 */
	public String getwTurn() {
		return wTurn;
	}

	/**
	 * @param wTurn the wTurn to set
	 */
	public void setwTurn(String wTurn) {
		this.wTurn = wTurn;
	}

	/**
	 * @return the preInterventionTurn
	 */
	public String getPreInterventionTurn() {
		return preInterventionTurn;
	}

	/**
	 * @param preInterventionTurn the preInterventionTurn to set
	 */
	public void setPreInterventionTurn(String preInterventionTurn) {
		this.preInterventionTurn = preInterventionTurn;
	}

	/**
	 * @return the enviroment
	 */
	public boolean isEnviroment() {
		return enviroment;
	}

	/**
	 * @param enviroment the enviroment to set
	 */
	public void setEnviroment(boolean enviroment) {
		this.enviroment = enviroment;
	}

	/**
	 * @return the isCombat
	 */
	public boolean isCombat() {
		return isCombat;
	}

	/**
	 * @param isCombat the isCombat to set
	 */
	public void setCombat(boolean isCombat) {
		this.isCombat = isCombat;
	}

	/**
	 * @return the combat
	 */
	public TreeCard getCombat() {
		return combat;
	}

	/**
	 * @param combat the combat to set
	 */
	public void setCombat(TreeCard combat) {
		this.combat = combat;
	}

	/**
	 * @return the interventions
	 */
	public StackCard getInterventions() {
		return interventions;
	}

	/**
	 * @param interventions the interventions to set
	 */
	public void setInterventions(StackCard interventions) {
		this.interventions = interventions;
	}

	/**
	 * @return the onlyPay
	 */
	public boolean isOnlyPay() {
		return onlyPay;
	}

	/**
	 * @param onlyPay the onlyPay to set
	 */
	public void setOnlyPay(boolean onlyPay) {
		this.onlyPay = onlyPay;
	}

	/**
	 * @return the inInterventionLoop
	 */
	public Phase getInInterventionLoop() {
		return inInterventionLoop;
	}

	/**
	 * @param inInterventionLoop the inInterventionLoop to set
	 */
	public void setInInterventionLoop(Phase inInterventionLoop) {
		this.inInterventionLoop = inInterventionLoop;
	}

	/**
	 * @return the passIntervention
	 */
	public int getPassIntervention() {
		return passIntervention;
	}

	/**
	 * @param passIntervention the passIntervention to set
	 */
	public void setPassIntervention(int passIntervention) {
		this.passIntervention = passIntervention;
	}

	/**
	 * @return the unDoInterventions
	 */
	public Stack<Card> getUnDoInterventions() {
		return unDoInterventions;
	}

	/**
	 * @param unDoInterventions the unDoInterventions to set
	 */
	public void setUnDoInterventions(Stack<Card> unDoInterventions) {
		this.unDoInterventions = unDoInterventions;
	}

	/**
	 * @return the undo
	 */
	public Stack<ActiveAction> getUndo() {
		return undo;
	}

	/**
	 * @param undo the undo to set
	 */
	public void setUndo(Stack<ActiveAction> undo) {
		this.undo = undo;
	}

	public void save(String dir) throws FileError{
		 try {
			FileOutputStream fileOut=new FileOutputStream(dir+".estado");
			ObjectOutputStream exit=new ObjectOutputStream(fileOut);
			exit.writeObject(wa);
			exit.writeObject(wb);
			exit.writeObject(wAt);
			exit.writeObject(wTurn);
			exit.writeObject(phase);
			
			exit.writeObject(preInterventionTurn);
			exit.writeObject(enviroment);	
			exit.writeObject(isCombat);
			exit.writeObject(combat);
			exit.writeObject(interventions);
			exit.writeObject(onlyPay);
			exit.writeObject(inInterventionLoop);
			exit.writeObject(passIntervention);	
			
			exit.writeObject(unDoInterventions);
			exit.writeObject(undo);

			
			exit.close();
		} catch (FileNotFoundException e) {
			throw new FileError("Fichero no encontrado");
		} catch (IOException e) {
			throw new FileError("Se provoco un facho en la apertura del fichero");
		}
	}
	public void load(String dir) throws FileError{
		 try {
			 FileInputStream fileIn=new FileInputStream(dir);
			 ObjectInputStream in=new ObjectInputStream(fileIn);
			 wa = (Warlock)in.readObject();
	         wb = (Warlock)in.readObject();
	         wAt = (String)in.readObject();
	         wTurn = (String)in.readObject();
	         phase = (Phase)in.readObject();
	         
	        preInterventionTurn = (String)in.readObject();
			enviroment = (Boolean)in.readObject();
			isCombat = (Boolean)in.readObject();
			combat = (TreeCard)in.readObject();
			interventions = (StackCard)in.readObject();
			onlyPay = (Boolean)in.readObject();
			inInterventionLoop = (Phase)in.readObject();
			passIntervention = (Integer)in.readObject();
			
			unDoInterventions = (Stack<Card>)in.readObject();
			undo = (Stack<ActiveAction>)in.readObject();
			
			in.close();
		 } catch (FileNotFoundException e) {
			 throw new FileError("Fichero no encontrado");
		} catch (IOException e) {
			throw new FileError("Se provoco un facho en la apertura del fichero");
		} catch (ClassNotFoundException e) {
			throw new FileError("Se produjo un problema leyendo el fichero con:"+e.getLocalizedMessage());
		}
	}
}
