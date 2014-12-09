/**
 * 
 */
package es.ucm.fdi.lps.p6.controller;


import jargs.gnu.CmdLineParser;

import java.io.IOException;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Configuration;

/**
 * @author Roni
 *
 */
public  class ParserArgs {
	private static CmdLineParser parser;
	
	/**
	 * parses info to Jargs
	 * @param min
	 * @param max
	 * @param WarlockA
	 * @param WarlockB
	 * @param hand
	 * @param life
	 * @param help
	 */
	private static boolean parserJargs(CmdLineParser.Option min,CmdLineParser.Option max,CmdLineParser.Option WarlockA,CmdLineParser.Option WarlockB,CmdLineParser.Option hand
			,CmdLineParser.Option life,CmdLineParser.Option help,CmdLineParser.Option inPut,CmdLineParser.Option outPut,CmdLineParser.Option debug
			,CmdLineParser.Option console){
		int aux = 0;
		boolean exit = false;
		
		try{
			if((Boolean) parser.getOptionValue(help) ){
				(Console.getInstance()).writeIniHelp();
				return true;
			}
		}
		catch(Exception e){
		}
		try{
			if((Boolean)parser.getOptionValue(debug)){
				(Configuration.getInstance()).setDebug(true);
			}
		}
		catch(Exception e){
			
		}
		
		
		String dir = "";
		boolean isExit = false;
		try{
			dir = (String)parser.getOptionValue(inPut);
			if(dir != null){
				isExit = true;
				if(dir.endsWith("entrada")){
					(Console.getInstance()).setFileIn(dir);
				}
				else{
					(Console.getInstance()).write("Parametro 'entrada' no valido, la extension del fichero debe ser *.entrada");
					exit = true;
				}
			}
		}catch (IOException io) {
			(Console.getInstance()).write("Error en la lectura del fichero de entrada.");
			exit = true;
		}catch (Exception e) {
		}
		try{
			dir = (String)parser.getOptionValue(outPut);
			if(dir != null){
				if(dir.endsWith("salida")){
					(Console.getInstance()).setFileOut(dir);
				}
				else{
					(Console.getInstance()).write("Parametro 'salida' no valido, la extension del fichero debe ser *.salida");
					exit = true;
				}
			}
			else if(isExit){
				(Console.getInstance()).write("No has introducido fichero de salida, pero si entrada automatica.\nTengo serias dudas de tus poderes JEDAY para poder intuir que hacer\nMejor salimos y lo haces bien.");
				exit = true;
			}
		}catch (IOException io) {
			(Console.getInstance()).write("Error en la lectura del fichero de salida.");
			exit = true;
		}catch (Exception e) {
		
		}
		
		
		try{
			aux = (Integer)parser.getOptionValue(min,null);
			if( (aux >= 3) && (aux <= 100))
				(Configuration.getInstance()).setMinDeck(aux);
			else{
				(Console.getInstance()).write("Parametro 'min' no valido , el valor minimo del mazo debe ser un valor entre 3 y 100");
				exit = true;
			}
		}catch (Exception e) {
			(Console.getInstance()).write("Parametro 'min' no introducido se leera desde el fichero de propiedades");
		}
		try{
			aux = (Integer)parser.getOptionValue(max,null);
			if((aux >= 3) && (aux <= 100))
				(Configuration.getInstance()).setMaxDeck(aux);
			else{
				(Console.getInstance()).write("Parametro 'max' no valido, l valor maximo del mazo debe ser un valor entre 3 y 100");
				exit = true;
			
			}
		}catch (Exception e) {
			(Console.getInstance()).write("Parametro 'max' no introducido se leera desde el fichero de propiedades");
		}
		try{
			aux = (Integer)parser.getOptionValue(hand,null);
			if(aux>=3 && aux<=7)
				(Configuration.getInstance()).setMaxHand(aux);
			else{
				(Console.getInstance()).write("Parametro 'mano' no valido, el valor de cartas en mano entre 3 y 7");
				exit = true;
			}
		}catch (Exception e) {
			(Console.getInstance()).write("Parametro 'mano' no introducido se leera desde el fichero de propiedades");
		}
		try{
			aux = (Integer)parser.getOptionValue(life);
			if(aux>=1 && aux<=20)
				(Configuration.getInstance()).setLife(aux);
			else{
				(Console.getInstance()).write("Parametro 'vidas' no valido, el numero de vidas debe ser un valor entre 1 y 20");
				exit = true;
			}
		}catch (Exception e) {
			(Console.getInstance()).write("Parametro 'vidas' no introducido se leera desde el fichero de propiedades");
		}
		/*try{
			dir = (String)parser.getOptionValue(WarlockA);
			if(dir != null){
				if(dir.endsWith("brujo"))
					(Configuration.getInstance()).setDirA(dir);
				else{
					(Console.getInstance()).write("Parametro 'BrujoA' no valido, la extension del fichero debe ser *.brujo");
					exit = true;
				}
			}
		}catch (Exception e) {
			(Console.getInstance()).write("Parametro 'BrujoA' no introducido se leera desde el fichero de propiedades");
		}
		try{
			dir = (String)parser.getOptionValue(WarlockB);
			if(dir != null){
				if(dir.endsWith("brujo"))
					(Configuration.getInstance()).setDirB(dir);
				else{
					(Console.getInstance()).write("Parametro 'BrujoB' no valido, la extension del fichero debe ser *.brujo");
					exit = true;
				}
			}
		}catch (Exception e) {
			(Console.getInstance()).write("Parametro 'BrujoB' no introducido se leera desde el fichero de propiedades");
		}*/
		int minDeck = (Configuration.getInstance()).getMinDeck();
		int maxDeck = (Configuration.getInstance()).getMaxDeck();
		if(minDeck != -1 && maxDeck != -1 && minDeck>maxDeck){
			(Console.getInstance()).write("El valor minimo del mazo debe ser menor o igual que el valor maximo del mazo");
			exit = true;
		}
		
		
		try{
			if((Boolean) parser.getOptionValue(console) ){
				(Configuration.getInstance()).setConsole(true);
			}
		}
		catch(Exception e){
		}
		
		return exit;
	}
	/**
	 * read the configuration, create the warlocks and launch the game
	 * @param args
	 */
	public static void readJArgs(String[] args){
		parser = new CmdLineParser();
		CmdLineParser.Option min = parser.addIntegerOption(')', "min");
		CmdLineParser.Option max = parser.addIntegerOption('(', "max");
		CmdLineParser.Option WarlockA = parser.addStringOption('a', "BrujoA");
		CmdLineParser.Option WarlockB = parser.addStringOption('b', "BrujoB");
		CmdLineParser.Option hand = parser.addIntegerOption('m', "mano");
		CmdLineParser.Option life = parser.addIntegerOption('v', "vidas");
		CmdLineParser.Option help = parser.addBooleanOption('?', "ayuda");
		CmdLineParser.Option debug = parser. addBooleanOption('d', "depurar");
		CmdLineParser.Option console = parser. addBooleanOption('c', "consola),");
		
		
		CmdLineParser.Option inPut = parser.addStringOption('e', "entrada");
		CmdLineParser.Option outPut = parser.addStringOption('s', "salida");
		
		try {
			parser.parse(args);
		}
		catch ( CmdLineParser.OptionException e ){
	   	}
		try{
			boolean exit = parserJargs(min,max,WarlockA,WarlockB,hand,life,help,inPut,outPut,debug,console);
			
			if(exit){
				(Console.getInstance()).closeIn();
				(Console.getInstance()).closeOut();
				(Console.getInstance()).closeLog();
				System.exit(1);
			}
			
			if(!(Configuration.getInstance()).validValues()){
				try{
					exit = (Configuration.getInstance()).readPropierties();
				}catch (IOException e) {
					System.out.print("El fichero propierties no existe, se cargara configuracion por defecto");
				}
			}
			if(!(Configuration.getInstance()).validValues()){
				(Configuration.getInstance()).generic();
			}
			
			if(exit){
				System.exit(1);
			}
		}catch (Exception e) {
			(Console.getInstance()).write("\n¡¡Error inesperado!!, se cerrara la aplicacion");
		}
			
			
	}

}
