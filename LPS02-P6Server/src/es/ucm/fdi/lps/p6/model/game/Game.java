/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import es.ucm.fdi.lps.p6.controller.Client;
import es.ucm.fdi.lps.p6.model.data.Configuration;

/**
 * @author Roni
 *
 */
public class Game extends Observed {

	private int countClient = 0;
	
	private Client client1;
	private Client client2;
	
	private Model model = new Model();
	
	public Game(){
		
	}
	public boolean addClient(ObjectInputStream inObject,ObjectOutputStream outObject){
		if(countClient == 2){
			return false;
		}
		try {
			if(countClient == 0){
				(Configuration.getInstance()).setNameA((String) inObject.readObject());
				(Configuration.getInstance()).setImageA(((String) inObject.readObject()));
				(Configuration.getInstance()).setDirA((String) inObject.readObject());
				
				client1 = new Client(inObject,model,countClient);
				model.setClient1(client1);
				new Thread(client1).start();
			}
			else{
				(Configuration.getInstance()).setNameB((String) inObject.readObject());
				(Configuration.getInstance()).setImageB((String) inObject.readObject());
				(Configuration.getInstance()).setDirB((String) inObject.readObject());
				
				client2 = new Client(inObject,model,countClient);
				model.setClient2(client2);
				new Thread(client2).start();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		(Judge.getInstance()).add(outObject);
		model.add(outObject);
		add(outObject);
		
		notifyPlayersLobby((Configuration.getInstance()).getNameA(),(Configuration.getInstance()).getImageA(),(Configuration.getInstance()).getNameB(),
				(Configuration.getInstance()).getImageB());
		 
		 countClient ++;
		 return true;
	}
}
