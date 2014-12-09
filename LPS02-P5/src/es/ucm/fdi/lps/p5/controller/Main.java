package es.ucm.fdi.lps.p5.controller;

import java.io.IOException;

import javax.swing.JOptionPane;

import es.ucm.fdi.lps.p5.DAO.DAOError;
import es.ucm.fdi.lps.p5.model.console.Console;
import es.ucm.fdi.lps.p5.model.data.Configuration;
import es.ucm.fdi.lps.p5.model.data.FileError;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.game.Model;
import es.ucm.fdi.lps.p5.model.game.ModelConsole;
import es.ucm.fdi.lps.p5.view.ConfigView;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
			}catch (DAOError d) {
				(Console.getInstance()).write(d.getInfo());
				System.exit(-1);
			}catch (FileError f) {
				(Console.getInstance()).write(f.getInfo());
				System.exit(-1);
			}catch (IOException e2) {
				(Console.getInstance()).write("Error con un problema en la apertura del fichero o el fichero del brujo no existe");
				System.exit(-1);
			}catch (Exception e1) {
				(Console.getInstance()).write("Error leyendo los fichero o los valores del mazo no son válido,\n Porfavor verifíquelo");
				System.exit(-1);
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
