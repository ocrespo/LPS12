package es.ucm.fdi.lps.p6.controller;

import java.io.IOException;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Configuration;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.game.Model;
import es.ucm.fdi.lps.p6.model.game.ModelConsole;
import es.ucm.fdi.lps.p6.view.ConfigView;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* try {
			 Socket socket = new Socket("localhost", 5557);
			 
			 InputStream entrada = socket.getInputStream();
			 OutputStream salida = socket.getOutputStream();
				
			 ObjectOutputStream salidaObjetos = new ObjectOutputStream (salida);
			 ObjectInputStream entradaObjetos = new ObjectInputStream (entrada);
			 
			 File archivo = new File (dir);
			 FileReader fr = new FileReader (archivo);
			 BufferedReader br = new BufferedReader(fr);
			 
			 salidaObjetos.writeObject(obj)
			 
			 
			 try {
				 while(true){
					Object a = entradaObjetos.readObject();
					System.out.println(a);
				 }
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//creamos el flujo de datos por el que se enviara un mensaje
			//< mensaje = new ObjectInputStream(socket.getInputStream());
		
			 	 
	        } catch (UnknownHostException ex) {
	            ex.printStackTrace();
	        } catch (IOException ex) {
	        	ex.printStackTrace();
	        }
		 	
*/
		
		
		ParserArgs.readJArgs(args);
		if((Configuration.getInstance()).isConsole()){
			playConsole();
		}
		else
			playGUI();
	}
	/**
	 * play with GUI
	 */
	public static void playGUI(){
		Model model =  new Model();
		Controller controller = new Controller(model);
		//new MainMenuView(controller);
		new ConfigView(controller, (Configuration.getInstance()).getMinDeck(), (Configuration.getInstance()).getMaxDeck(),
				(Configuration.getInstance()).getMaxHand(), (Configuration.getInstance()).getLife(), (Configuration.getInstance()).getDirA(),
				(Configuration.getInstance()).getDirB(), (Configuration.getInstance()).isDebug(),(Configuration.getInstance()).isAdmin());
	}
	/**
	 * play with console
	 */
	public static void playConsole(){
		try{
			boolean exit = false;
			String dir = (Configuration.getInstance()).getDirA();
			if(!dir.endsWith("brujo")){
				while(!dir.endsWith("brujo") && !dir.equals("--salir")){
					(Console.getInstance()).write("Introduce la direccion del fichero del brujo A, debe ser un fichero del tipo '*.brujo'\n, " +
								"siendo '*' un nombre cualquiera, si desea salir use '--salir'");
					dir = (Console.getInstance()).read();
				}
				(Configuration.getInstance()).setDirA(dir);
			}
			if(dir.equals("--salir"))
				System.exit(1);
			
			dir = (Configuration.getInstance()).getDirB();
			if(!dir.endsWith("brujo")&& !dir.equals("--salir")){
				while(!dir.endsWith("brujo") && dir.equals("--salir")){
					(Console.getInstance()).write("Introduce la direccion del fichero del brujo B, debe ser un fichero del tipo '*.brujo'\n, " +
								"siendo '*' un nombre cualquiera, si desea salir use '--salir'");
					dir = (Console.getInstance()).read();
				}
				(Configuration.getInstance()).setDirA(dir);
			}
			if(dir.equals("--salir"))
				System.exit(1);
			Warlock wa=null,wb=null;
			try{
				wa = new Warlock((Configuration.getInstance()).getLife());
				if((Configuration.getInstance()).readDeck((Configuration.getInstance()).getDirA(), wa)){
					(Console.getInstance()).write("El mazo del brujo A se cargo sin fallos");
					wb = new Warlock((Configuration.getInstance()).getLife());
					if((Configuration.getInstance()).readDeck((Configuration.getInstance()).getDirB(), wb))
						(Console.getInstance()).write("El mazo del brujo B se cargo sin fallos");
					else
						exit = true;
				}
				else
					exit = true;
			}catch(IOException e){
				(Console.getInstance()).write("\nEl fichero del brujo no existe, verifiquelo y inicie otra vez la aplicacion");
				exit = true;
			}
			catch(Exception e){
				(Console.getInstance()).write("Los valores del mazo no son válido, verifiquelo y inicie otra vez la aplicacion");
				exit = true;
			}
			
			
			
			if(!exit){
				ModelConsole board = new ModelConsole(wa,wb);
				board.play();
			}
		}catch(Exception e){
			(Console.getInstance()).write("\n¡¡Error inesperado!!, se cerrara la aplicacion");
			(Console.getInstance()).writeLog(e.getMessage());
		}
		(Console.getInstance()).closeIn();
		(Console.getInstance()).closeOut();
		(Console.getInstance()).closeLog();
		}
}
