/**
 * 
 */
package es.ucm.fdi.lps.p6.model.console;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import es.ucm.fdi.lps.p6.model.data.Configuration;

/**
 * 
 * @author usuario_local
 *
 */
public class Console {
	private BufferedReader in = new BufferedReader (new InputStreamReader(System.in));
	private FileReader inFile = null;
	private FileWriter outFile = null;
	private FileWriter log = null;
	
	private static Console SINGLETON = null;
	
	
	/**
	 * Cant create more instances
	 */
	@SuppressWarnings("unused")
	private synchronized static void createInstance() {
	    if (SINGLETON == null) { 
	    	SINGLETON = new Console(); 
	    }
	  }
	/**
	 * 
	 * @return the intance of console
	 */
	public final static Console getInstance() { 
	    if (SINGLETON == null)
	    	SINGLETON = new Console();
	    
	    return SINGLETON;
	  } 
	@Override
	/**
	 * 
	 */
	public Object clone() throws CloneNotSupportedException { 
		throw new CloneNotSupportedException();  
	}
	/**
	 * inicialice a log to write in file
	 */
	public void iniLog(){
		try {
			log =  new FileWriter("log.brujo");
		} catch (IOException e) {
		}
	}
	/**
	 * 
	 * @param file the file
	 * @throws FileNotFoundException if the file dont exist
	 */
	public void setFileIn(String file) throws FileNotFoundException{
		inFile = new FileReader(file);
		in = new BufferedReader (inFile);
	}
	/**
	 * 
	 * @param file the fail
	 * @throws IOException if fail with file
	 */
	public void setFileOut(String file) throws IOException{
		outFile = new FileWriter(file);
	}
	public void closeIn(){
		if(inFile != null){
			try {
				inFile.close();
			} catch (IOException e) {
				write("Se produjo un error cerrando el fichero de entrada");
				System.exit(-1);
			}
		}
	}
	public void closeOut(){
		if(outFile != null){
			try {
				outFile.close();
			} catch (IOException e) {
				write("Se produjo un error cerrando el fichero de salida");
				System.exit(-1);
			}
		}
	}
	
	/**
	* write welcome menu
	 * @throws IOException 
	*/
	public void writeWelcomeMenu(){
		write("Bienvenido a Brujas & Trifulcas.");
		write("Elige una de las siguientes opciones:");
		write("'1.-Jugar partida 1vs1.");
		write("'2.-Ayuda");
		write("'0.-Salir");

	}
	/**
	 * Write welcome menu and read a user option
	 * @return boolean , if play or not
	 */
	public boolean welcomeMenu(){
		writeWelcomeMenu();
		String aux = "";
		while(!aux.equals("0")){
			aux = read();
			if(aux.equals("1"))
				return true;
			else if(aux.equals("2")){
				helpMenu();
				writeWelcomeMenu();
			}
			else if(!aux.equals("0"))
				write("Opcion no valida");
		}
		return false;
	}
	/**
	 * write help menu
	 */
	public void writeHelpMenu(){
		write("AYUDA");
		write("Elige una de las siguientes opciones:");
		write("1.-Mecanica de juego.");
		write("2.-Controles");
		write("3.-Informacion de GUI");
		write("0.-Salir");
	}
	/**
	 * write utility info
	 */
	public void mecMenu(){
		write("MECANICA");
		write("Para utilizar cartas desde la mano simplemente escriba el codigo de la carta, en el caso de las intervencion, si la intervencion requiere un objetivo\r\n" +
				"introduzca el propietario de la carta 'mio' o 'enemigo' seguido del codigo de la carta. Si hay varias cartas iguales utilice el mismo criterio de la posicion" +
				"\r\n\r\nEn la fase trifulca, para atacar utilice el comando '--atacar codigo' para asignar una criatura al ataque,\nen caso de tener mas de una criatura igual" +
				", '--atacar codigo posicion' siendo posicion la posicion de la criatura de arriba a abajo partiendo de 1 segun se muestra por pantalla" +
				"\r\n\r\nPara asignar defensores aplique el mismo criterio anterior pero en vez de --atacar utilice --defender. Despues de pulsar 'intro'\r\n" +
				"se le pedira a quien quiere defender. En caso de haber mas de un atacante igual, aplique el mismo criterio 'codigo posicion',\r\n" +
				"en caso de omitir la posicion la aplicacion por defecto asignara el 1" +
				"\r\n\r\nPara usar las habilidades de las cartas use '--usar codigo' si hay mas de una (en entornos no hace falta) introducer un numero de su posicion segun se muestra partiendo del 1." +
				"\r\n\r\nEl cloro se pagara de forma automatica siempre que sea posible, sino, se le pedira al usuario que indique como quiere gastarlo." +
				"\r\n\r\n si la aplicacion se queda esperando algo y no escribe nada, pulse intro.");
		read();
	}
	/**
	 * write utility info
	 */
	public void controlsMenu(){
		write("CONTROLES");
		write("En cualquier momento del programa pulse:\r\n" +
				"--sig: pasa a la siguiente etapa de la partida\r\n" +
				"--final: pasas al final del turno\n--combate: pasas a la fase de combate\n--mano: muestra la mano del jugador\r\n"+
				"--ayuda: ir al menu de ayuda\r\n"+
				"--vidas: muestra las vidas de los brujos\r\n"+"--vercombate: muestra el combate si estas en la fase de trifulca\r\n" +
				"--juego: muestra las cartas en juego (no los entornos)\r\n"+"--entornos: muestra los entornos en juego\r\n"
				+"--cloro: muestra las reservas de cloro del brujo\r\n"+"--turno: muestra de quien es el turno en el momento de pedir datos(por ejemplo cuando un brujo juega una intervencion en el turno de su enemigo)\r\n"
				+"--limpiar: limpia la pantalla para ocultar tu informacion al otro brujo\r\n"+"--salir: cerrar el programa\r\n" +
						"\r\n'--usar codigo' usa la habilidad activa de una carta\r\n'--ververtedero' para ver el vertedero.\r\n'--verintervenciones' para ver intervenciones.");
		write("Pulse Enter para volver al menu");
		read();
	}
	/**
	 * write utility info
	 */
	public void guiMenu(){
		write("GUI");
		write("T: elemento tierra \n"+"M: elemento mar \r\n"+"A: elemento aire \nE: elemento espiritu\r\n" +
				"G: elemento generico\nSi hay un opcion y/N, al pulsar enter sin meter datos, por omision se elige la opcion en mayusculas");
		write("Pulse Enter para volver al menu");
		read();
	}
	/**
	 * loop to decide
	 */
	public void helpMenu() {
		writeHelpMenu();
		String aux = "";
		while(!aux.equals("0")){
			aux = read();
			if(aux.equals("1")){
				mecMenu();	
				writeHelpMenu();
			}				
			else if(aux.equals("2")){
				controlsMenu();
				writeHelpMenu();
			}
			else if(aux.equals("3")){
				guiMenu();
				writeHelpMenu();
			}
			else if(!aux.equals("0"))
				write("Opcion no valida");
		}
	}
	/**
	 * clean screen
	 */
	public void clean(){
		write("\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n" +
				"\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n\r\n");
	}
	/**
	 * ask for action to do
	 */
	public void writeChose(){
		write("\nIntroduzca la accion: ");
	}
	/**
	 * write turn and wait until enter
	 * @param aux , warlock's turn
	 */
	public void writeTurn(String aux){
		write("Turno del Brujo " + aux + "\r\n\r\n\r\n");
		write("Pulse Enter para empezar");		
	}
	/**
	 * @return user line
	 */
	public String read() {
		String aux = "";
		if((Configuration.getInstance()).isConsole()){
			try {
				aux = in.readLine();
				aux = aux.toLowerCase();
				writeLog("\r\n\r\nUSUARIO:\r\n\r\n"+aux);
				if(aux == null && inFile != null){
					closeLog();
					closeIn();
					closeOut();
					System.exit(-2);
				}
				return aux;
	
			} catch (IOException e) {
				writeLog("Se produjo un error leyendo la informacion");
				if(inFile != null)
					System.exit(-2);
			}
		}
		return aux;
	}
	/**
	 * write enemy beetwen 
	 * @param text1 , info of warlock which have turn
	 * @param text2 , defender warlock
	 */
	public void writeVs(String text1,String text2){
		if(text1.equals(""))
			write("No tienes cartas ");
		else
			write(text1);
		write("-----------¡¡¡¡¡ENEMIGO!!!!!----------------------------------");
		if(text2.equals(""))
			write("No tienes cartas");
		else
			write(text2);
	}
	/**
	 * write string into console
	 * @param text , string
	 * @throws IOException 
	 */
	public void write(String text){
		if(outFile == null){
			if((Configuration.getInstance()).isConsole())
				System.out.println(text);
		}
		else{
			try {
				text = text+"\r\n";
				outFile.write(text);
			} catch (IOException e) {
				System.exit(-1);
			}
		}
	}
	/**
	 * 
	 * @return boolean if the user want or not
	 */
	public boolean optionTrue(String text){
		write(text+"(por omision NO): y/N");
		String aux = read();
		if(aux.equals("y")){
			return false;
		}
		if(aux.equals("n")){
			return true;
		}
		return true;
	}
	/**
	 * write a help in JArgs
	 */
	public void writeIniHelp(){
		write("Lo posibles valores que puede introducir son:\n'-)' 0 '-min' seguido de un entero, esto representara el minimo de cartas en el mazo inicialmente y debe ser un valor entre 3 y 100" +
				"\r\n'-(' 0 '-max' seguido de un entero, esto representara el maximo de cartas en el mazo inicialmente y debe ser un valor entre 3 y 100 ( debe ser mayor o igual que el minimo)" +
				"\r\n'-m' 0 '-mano' seguido de un entero, esto representaras el numero de cartas iniciales y maximas en la mano y debe ser un valor entre 3 y 7" +
				"\r\n'-v' 0 '-vidas' seguido de un entero, esto representa el numero de vidas de los brujos, debe ser un valor entre 1 y 20" +
				"\r\n'-a' 0 '-BrujoA' seguido del fihcero con extension .brujo, de aqui se cargara el mazo deñ brujo A" +
				"\r\n'-b' 0 '-BrujoB' seguido del fihcero con extension .brujo, de aqui se cargara el mazo deñ brujo B" +
				"\r\n\r\nPara mas informacion lee el fichero README.txt");
	}
	/**
	 * write lifes
	 * @param w1
	 * @param w2
	 */
	public void writeLifes(int w1,int w2){
		write("Vida Brujo A : " + w1 + "\r\nVida Brujo B: "+w2);
	}
	/**
	 * ask how want deal chlorine
	 * @param value , generic mana
	 * @return the user line
	 */
	public String askMana(int value,String cost){
		write("Te queda por pagar: "+value+"G\r\n¿Que coloro quiere utilizar?(se pagara de uno en uno a no ser que se indique '2xC' siendo C el cloro)");
		write("A usted le quedan "+ cost);
		return read();
	}
	/**
	 * close a log file
	 */
	public void closeLog(){
		if(log != null){
			try {
				log.close();
			} catch (IOException e) {
			}
		}
	}
	/**
	 * write in log file
	 * @param str the string to write
	 */
	public void writeLog(String str){
		if(log != null){
			try {
				log.write(str+"\r\n");
			} catch (IOException e) {	
			}
		}
	}
	
}

