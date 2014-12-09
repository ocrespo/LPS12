/**
 * 
 */
package es.ucm.fdi.lps.p6.model.game.connection;

import java.io.IOException;
import java.io.ObjectInputStream;

import es.ucm.fdi.lps.p6.model.game.ModelNet;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection;

/**
 * @author Roni
 *
 */
public class Server implements Runnable{
	private  ObjectInputStream in;
	private ModelNet m;
	private boolean stop = false;
	
	public Server(ObjectInputStream in,ModelNet m){
		this.in = in;
		this.m = m;
	}
	public void stop(){
		stop = true;
	}
	@Override
	public void run() {
		while(!stop){
			try {
				TransferObjectConection trans =  (TransferObjectConection) in.readObject();
				m.doneActionServer(trans);
				//trans.done(m);
				//Object a = in.readObject();
			
			} catch (ClassNotFoundException e) {
			} catch (IOException e) {
				m.notifyError("Se ha desconectado del servidor");
				System.exit(-1);
			}
		}
		
	}
}
