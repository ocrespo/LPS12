package es.ucm.fdi.lps.p6.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import es.ucm.fdi.lps.p6.DAO.DAOError;
import es.ucm.fdi.lps.p6.model.data.FileError;
import es.ucm.fdi.lps.p6.model.game.Game;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection;
import es.ucm.fdi.lps.p6.model.game.TransferObjectConection.Notify;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileError, DAOError {
		try {
			System.out.print("Se ha iniciado el servidor en el puerto : 5557");

			ParserArgs.readJArgs(args);
			
			ServerSocket socketServidor = new ServerSocket(5557);


			ObjectInputStream inObject;
			ObjectOutputStream outObject = null;
			
			InputStream in= null;
			OutputStream out= null;
			Socket client = null;
			Game game = new Game();
			while(true){
				client = socketServidor.accept();
				
				in = client.getInputStream();
				out = client.getOutputStream();
				
				outObject = new ObjectOutputStream (out);
				inObject = new ObjectInputStream (in);
				
				if(!game.addClient(inObject, outObject)){
					//game = new Game();
					//game.addClient(inObject, outObject);
					ArrayList<Object> aux = new ArrayList<Object>();
					aux.add("El servidor esta ocupado");
					outObject.writeObject(new TransferObjectConection(null,Notify.updateError, aux));
				}
				else{
					System.out.print("Un nuevo jugador se ha incorporado a la partida");
				}
				
			}
			
		          
		} catch (IOException e) {
			System.out.print("Ese puerto esta ocupado o un problema con la conexion");
		}
	}
}
