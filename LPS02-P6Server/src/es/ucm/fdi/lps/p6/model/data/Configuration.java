/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.StringTokenizer;

import es.ucm.fdi.lps.p6.DAO.DAO;
import es.ucm.fdi.lps.p6.DAO.DAOError;
import es.ucm.fdi.lps.p6.DAO.DTOCard;
import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;




/**
 * @author Roni
 * 
 */
public class Configuration {
	private static Configuration SINGLETON = null;
	private int maxHand = -1;
	private int maxDeck = -1;
	private int minDeck = -1;
	private int life = -1;
	private String dirA = null;
	private String dirB = null;
	private String nameA = null;
	private String nameB = null;
	private boolean admin = false;
	private boolean debug = false;
	private boolean console = false;
	private String imageA;
	private String imageB;
	private String data;
	private String connection;
	/**
	 * Constructor por defecto
	 */
	private Configuration() {
	}

	@SuppressWarnings("unused")
	private synchronized static void createInstance() {
		if (SINGLETON == null) {
			SINGLETON = new Configuration();
		}
	}
	/**
	 * return the instance of Configuration
	 * @return Configuration
	 */
	public final static Configuration getInstance() {
		if (SINGLETON == null)
			SINGLETON = new Configuration();

		return SINGLETON;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * @return the maxHand
	 */
	public int getMaxHand() {
		return maxHand;
	}

	/**
	 * @param maxHand the maxHand to set
	 */
	public void setMaxHand(int maxHand) {
		this.maxHand = maxHand;
	}

	/**
	 * @param maxDeck the maxDeck to set
	 */
	public void setMaxDeck(int maxDeck) {
		this.maxDeck = maxDeck;
	}

	/**
	 * @param minDeck the minDeck to set
	 */
	public void setMinDeck(int minDeck) {
		this.minDeck = minDeck;
	}

	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * @param dirA the dirA to set
	 */
	public void setDirA(String dirA) {
		this.dirA = dirA;
	}

	/**
	 * @return the maxDeck
	 */
	public int getMaxDeck() {
		return maxDeck;
	}


	/**
	 * @return the minDeck
	 */
	public int getMinDeck() {
		return minDeck;
	}


	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @return the dirA
	 */
	public String getDirA() {
		return dirA;
	}

	/**
	 * @return the dirB
	 */
	public String getDirB() {
		return dirB;
	}

	/**
	 * @param dirB the dirB to set
	 */
	public void setDirB(String dirB) {
		this.dirB = dirB;
	}
	/**
	 * @return the console
	 */
	public boolean isConsole() {
		return console;
	}

	/**
	 * @param console the console to set
	 */
	public void setConsole(boolean console) {
		this.console = console;
	}

	public String getImageA() {
		return imageA;
	}

	public void setImageA(String imageA) {
		this.imageA = imageA;
	}

	public String getImageB() {
		return imageB;
	}

	public void setImageB(String imageB) {
		this.imageB = imageB;
	}

	/**
	 * 
	 * @return if all values are correct or not
	 */
	public boolean validValues(){
		if((maxHand != -1) && maxDeck < 101 && minDeck > 2 && (maxDeck != -1) && (minDeck != -1) && (life != -1) && (!dirA.equals(""))&&(!dirB.equals("")))
			if(maxDeck > minDeck)
				return true;
		return false;
	}
	/**
	 * check if all ok
	 */
	public void generic(){
		if(maxHand == -1) 
			maxHand = 5;	
		if(maxDeck == -1)
			maxDeck = 50;
		if(minDeck == -1)
			minDeck = 10;
		if(minDeck > maxDeck){
			minDeck = 10;
			maxDeck = 50;
		}
		if(life == -1) 
			life = 10;
		
	}
	/**
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		(Console.getInstance()).iniLog();
		this.debug = debug;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the dAO
	 */
	public String getDAO() {
		return data;
	}

	/**
	 * @param dAO the dAO to set
	 */
	public void setDAO(String dAO) {
		data = dAO;
	}

	/**
	 * @return the conexion
	 */
	public String getConexion() {
		return connection;
	}


	/**
	 * @return the nameA
	 */
	public String getNameA() {
		return nameA;
	}

	/**
	 * @param nameA the nameA to set
	 */
	public void setNameA(String nameA) {
		this.nameA = nameA;
	}

	/**
	 * @return the nameB
	 */
	public String getNameB() {
		return nameB;
	}

	/**
	 * @param nameB the nameB to set
	 */
	public void setNameB(String nameB) {
		this.nameB = nameB;
	}

	/**
	 * read the file properties
	 * @throws IOException if  file doesnt exist
	 * @throws Exception if a value isnt a number
	 */
	public boolean readPropierties() throws IOException {
		Properties prop = new Properties();
		InputStream file = null;
		file = new FileInputStream("brujos.properties");
		prop.load(file);
		String aux = "";
		int value = 0;
		boolean exit = false;
		if(maxHand == -1){
			aux = prop.getProperty("mano");
			if(aux != null){
				try{
					value = Integer.parseInt(aux);
					if (value >= 3 && value <= 7)
						maxHand = value;
					else{
						(Console.getInstance()).write("El valor minimo del mazo debe ser un valor entre 3 y 100");
						exit = true;
					}
				}catch (Exception e) {
					(Console.getInstance()).write("El valor minimo del mazo debe ser un valor entre 3 y 100");
					exit = true;
				}
			}
			else
				(Console.getInstance()).write("No has introducido el valor mano en el fichero de propiedades, se cargara por defecto");	
		}
		if(minDeck == -1 || minDeck>maxDeck){
			aux = prop.getProperty("min");
			if(aux != null){
				try{
					value = Integer.parseInt(aux);
					if (value >= 3 && value <= 100)
						minDeck = value;
					else{
						(Console.getInstance()).write("El valor maximo del mazo debe ser un valor entre 3 y 100");
						exit = true;
					}
				}catch (Exception e) {
					(Console.getInstance()).write("El valor maximo del mazo debe ser un valor entre 3 y 100");
					exit = true;
				}
			}
			else
				(Console.getInstance()).write("No has introducido el valor minimo en el fichero de propiedades, se cargara por defecto");	
		}
		if(maxDeck == -1  || minDeck>maxDeck){
			aux = prop.getProperty("max");
			if(aux != null){
				try{
					value = Integer.parseInt(aux);
					if (value >= 3 && value <= 100)
						maxDeck = value;
					else{
						(Console.getInstance()).write("El valor de cartas en mano entre 3 y 7");
						exit = true;
					}
				}catch (Exception e) {
					(Console.getInstance()).write("El valor de cartas en mano entre 3 y 7");
					exit = true;
				}
			}
			else
				(Console.getInstance()).write("No has introducido el valor maximo en el fichero de propiedades, se cargara por defecto");	
		}
		if(life == -1){
			aux = prop.getProperty("vidas");
			if(aux != null){
				try{
					value = Integer.parseInt(aux);
					if (value >= 1 && value <= 20)
						life = value;
					else{
						(Console.getInstance()).write("El numero de vidas debe ser un valor entre 1 y 20");
						exit = true;
					}
				}catch (Exception e) {
					(Console.getInstance()).write("El numero de vidas debe ser un valor entre 1 y 20");
					exit = true;
				}
			}
			else
				(Console.getInstance()).write("No has introducido el valor vida en el fichero de propiedades, se cargara por defecto");	
		}
		/*if(dirA == null){
			aux = prop.getProperty("brujoA");
			if(aux != null){
				if (aux!=null && aux.endsWith("brujo"))
					dirA = aux;
				else{
					(Console.getInstance()).write("Parametro 'BrujoA' no valido, la extension del fichero debe ser *.brujo");
					exit = true;
				}
			}
		}
		if(dirB.equals("")){
			aux = prop.getProperty("brujoB");
			if(aux != null){
				if (aux!=null && aux.endsWith("brujo"))
					dirB = aux;
				else{
					(Console.getInstance()).write("Parametro 'BrujoA' no valido, la extension del fichero debe ser *.brujo");
					exit = true;
				}
			}
		}*/
		if(minDeck > maxDeck){
			(Console.getInstance()).write("El valor minimo del mazo debe ser menor o igual que el valor maximo del mazo");
			exit = true;
		}
		data = prop.getProperty("datos");
		connection = prop.getProperty("conexion");
		return exit;
		
	}
	/**
	 * read the deck of the warlock
	 * @param dir , direction of file
	 * @param w , Warlock
	 * @return if all card are valid or not
	 * @throws IOException , if file doesnt exist
	 * @throws Exception , if a value isnt a numbe
	 */
	public boolean readDeck(String file ,Warlock w) throws IOException,FileError,Exception{
		(DAO.getInstance()).open(connection);
		
		boolean pass = true;
		//File archivo = new File (dir);
		//FileReader fr = new FileReader (archivo);
		//BufferedReader br = new BufferedReader(fr);
		StringTokenizer tok = new StringTokenizer(file, "\n");
		String line = null;
		boolean actAdmin = false;
		boolean title=false,name = false,phrase=false;
		while(tok.hasMoreTokens() && pass){
			line = tok.nextToken();
			if((!line.equals("")) && (!line.startsWith("#")) && (!line.equals("@admin"))){
				if (!title){
					if(line.startsWith("BRUJOS&TRIFULCAS")){
						if (!line.endsWith("6") &&!line.endsWith("5") && !line.endsWith("4") && !line.endsWith("3") && !line.endsWith("2") && !line.endsWith("1")){
							(Console.getInstance()).write("Error de version del juego");
							throw new FileError("Error de version del juego");
						}
						title=true;
					}
					else{
						(Console.getInstance()).write("Formato de fichero brujo no valido, especifique la version");
						throw new FileError("Formato de fichero brujo no valido, especifique la version");

					}
				}
				else if (!name && line.startsWith("@nombre")){
					//w.setName(line.substring(8));
					name=true;
				}
				else if (!phrase && line.startsWith("@frase")){
					w.setPhrase(line.substring(7));
					phrase=true;
				}
				else{
					pass = parserCard(pass,line,w,actAdmin);
				}
		
			}
			else if(line.equals("@admin"))
				actAdmin = true;
				
			//if
			
			
		}
		if(actAdmin){
			w.addMana(new Cost(10, 10, 10, 10, 0));
			w.setLife(9999999);
			
		}
		if(!name){
			(Console.getInstance()).write("Falta por introducir el nombre del burjo '@nombre  nombre_burjo'");
			throw new FileError("Falta por introducir el nombre del burjo '@nombre  nombre_burjo'");

		}
		else if(!phrase){
			(Console.getInstance()).write("Falta por introducir el nombre del burjo '@frase  frase_burjo'");

			throw new FileError("Falta por introducir el nombre del burjo '@frase  frase_burjo'");

		}
		int size = w.getNumCardsDeck();
		if(pass){
			if(size < minDeck){
				(Console.getInstance()).write("No hay suficientes cartas en el mazo de "+w.getName()+", el valor minimo debe ser "+minDeck);

				throw new FileError("No hay suficientes cartas en el mazo de "+w.getName()+", el valor minimo debe ser "+minDeck);

			}
			if(size > maxDeck){
				(Console.getInstance()).write("Hay demasiadas cartas en el mazo de "+w.getName()+", el valor maximo debe ser "+minDeck);
				throw new FileError("Hay demasiadas cartas en el mazo de "+w.getName()+", el valor maximo debe ser "+minDeck);
			}
		}
		if(actAdmin)
			admin = true;
		return pass;
	}
	/**
	 * get the card by line of the file
	 * @param pass if pss the process
	 * @param line  the line
	 * @param w warlock
	 * @param actAdmin if use admin account
	 * @return if all ok
	 * @throws DAOError 
	 * @throws FileError 
	 */
	private boolean parserCard(boolean pass,String line,Warlock w,boolean actAdmin) throws DAOError, FileError{
		StringTokenizer tok = new StringTokenizer(line);
		String code = tok.nextToken();
		DTOCard dto = null;
		if(code.equals("@mano") && actAdmin){
			dto = (DAO.getInstance()).getCard(tok.nextToken());
			w.addInHand(CardFactory.getCard(dto,false));
			return true;
		}
		else if(code.equals("@mano")){
			(Console.getInstance()).write("@mano no se identifico como admin para poder usarlo");
			throw new FileError("@mano no se identifico como admin para poder usarlo");

		}
		if(code.equals("@entorno") && actAdmin){
			dto = (DAO.getInstance()).getCard(tok.nextToken());
			w.addEnviroment(CardFactory.getCard(dto,false));
			return true;
		}
		else if(code.equals("@entorno")){
			(Console.getInstance()).write("@entorno no se identifico como admin para poder usarlo");	
			throw new FileError("@entorno no se identifico como admin para poder usarlo");

		}
		int num;
		if (!tok.hasMoreTokens()) 
			num=1;
		else 
			num = Integer.parseInt(tok.nextToken());
		dto = (DAO.getInstance()).getCard(code);
		
		if(dto != null){
			Card c = CardFactory.getCard(dto,false); // CardFactory.getCard(code);
			if(w.foundInDeck(code) == null || actAdmin || debug){
				int max = c.getMax();
				if(max == 0 || num<=max || debug){
					if(actAdmin)
						w.addInGame(c);
					else
						w.addCardToDeck(c);
					for(int i=1;i<num;i++){
						if(actAdmin)
							w.addInGame(CardFactory.getCard(dto,false));
						else
							w.addCardToDeck(CardFactory.getCard(dto,false));
					}
				}
				else{
					if(!debug){
						(Console.getInstance()).write("La carta, '"+code+"', del brujo '"+w.getName()+"' esta mas veces de las permitidas.");

						throw new FileError("La carta, '"+code+"', del brujo '"+w.getName()+"' esta mas veces de las permitidas.");

					}
				}
			}
			else{
				if(!debug){
					(Console.getInstance()).write("La carta, '"+code+"', del brujo '"+w.getName()+"' aparece mas de una vez, no se permite la repeticion de cartas en el fichero.");	
					throw new FileError("La carta, '"+code+"', del brujo '"+w.getName()+"' aparece mas de una vez, no se permite la repeticion de cartas en el fichero.");
				}
			}
		}
		else{
			throw new FileError("La carta, "+code+" del brujo '"+w.getName()+"' no se reconocio como una carta valida");
		}
		return pass;
	}
	
}
