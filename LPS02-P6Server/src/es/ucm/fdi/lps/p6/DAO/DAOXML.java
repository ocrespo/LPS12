/**
 * 
 */
package es.ucm.fdi.lps.p6.DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import es.ucm.fdi.lps.p6.model.data.Cost;
import es.ucm.fdi.lps.p6.model.data.actions.active.ActiveAction;
import es.ucm.fdi.lps.p6.model.data.actions.combat.CombatAction;
import es.ucm.fdi.lps.p6.model.data.actions.passive.PassiveAction;
import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;

/**
 * @author Roni
 *
 */
public class DAOXML extends DAO implements DAOinterface  {

	private NodeList cards = null;
	
	public DAOXML(){
	}

	@Override
	public DTOCard getCard(String key) throws DAOError {
		if(cards == null){
			return null;
		}
		DTOCard dto = new DTOCard();
		Node card = null;
		NamedNodeMap cardValues = null;	
		int i = 0;
		String code = "";	
		
		while(i < cards.getLength() && !code.equals(key)){
			card = cards.item(i).getChildNodes().item(1); 
			cardValues = card.getAttributes();
			code = takeValue(cardValues, "code");
			i++;
		}
				
		if(i < cards.getLength()+1 && code.equals(key) ){	
			//card atributes
			int a = cards.getLength();
			dto.setType(card.getNodeName());
			dto.setCode(code);
			dto.setName(takeValue(cardValues, "name"));
			dto.setElement(takeValue(cardValues, "element"));
			//dto.setDescription(takeValue(cardValues, "description"));
			dto.setDirImg(takeValue(cardValues, "img"));
			try {
				dto.setMax(takeValue(cardValues, "max"));		
				dto.setAttack(takeValue(cardValues, "attack"));
				dto.setDefense(takeValue(cardValues, "defense"));
			} catch (Exception e) {
				throw new DAOError("El valor de max, attack o defense de la carta "+code+" no es válido");
			}
			NodeList node = ((Element) card).getElementsByTagName("description");
			if(node != null && node.getLength() > 0){
				dto.setDescription(node.item(0).getTextContent());
			}
			else{
				dto.setDescription("");
			}
			node = ((Element) card).getElementsByTagName("cost");
			if(node != null && node.getLength() > 0){
				dto.setCost(parseCost(node.item(0).getAttributes()));
			}
			//action
			node = ((Element) card).getElementsByTagName("action");//.item(0).getChildNodes();
			if(node != null){
				int max = node.getLength();
			    	
			    for(int j=0;j < max;j++){
			    	NodeList action = node.item(j).getChildNodes();
			    	readAction(dto, action);
			    }
			    	
			}
			//Node actionFirst = actions.item(1);
			//NamedNodeMap actionAtribute = actionFirst.getAttributes();
			return dto;
		}  	
			    
		
		return null;
		
	}
	private String takeValue(NamedNodeMap values,String key){
		Node node = values.getNamedItem(key);
		if(node != null){
			return node.getNodeValue();
		}
		return "0";
	}
	private void readAction(DTOCard dto,NodeList action ) throws DAOError{	
		NamedNodeMap actionAtribute = ((Node) action).getAttributes();
		String nameAction = takeValue(actionAtribute,  "name");
		String typeAction = takeValue(actionAtribute,  "type");
		
		Object o = CardFactory.getAction(nameAction, getParamAction(action));
		if(typeAction.equals("active")){
			dto.setActionActive((ActiveAction) o);
		}
		else if(typeAction.equals("attack")){
			dto.setActionAttack((CombatAction) o);
		}
		else if(typeAction.equals("defense")){
			dto.setActionDefense((CombatAction) o);
		}
		else if(typeAction.equals("finally")){
			dto.setActionFinally((CombatAction) o);
		}
		else if(typeAction.equals("passive")){
			dto.setActionPassive((PassiveAction) o);
		}
		else{
			throw new DAOError("Tipo de acción no válida, debe ser active o attack o defense o finally o passive");
		}
		/*else if(nameAction.equals("permanent")){
		}*/
		
		
		//NodeList paramAction = ((Element)action).getChildNodes();
		
	}
	private HashMap<String, Object> getParamAction(NodeList action){
		HashMap<String, Object> info  = new HashMap<String,Object>();
		String key;
		for(int i = 1;i < action.getLength();i = i+2){
			key = action.item(i).getNodeName();
			if(key.equals("mana")){
				info.put("mana",parseCost(action.item(i).getAttributes()));
			}
			else if(key.equals("elements")){
				info.put("elements", parseElements(action.item(i).getAttributes()));
			}
			else{
				info.put(key,takeValue(action.item(i).getAttributes(), "value"));
				//info.put(key,action.item(i).getTextContent());
				//String a = action.item(i).getNodeName();
				//String b = action.item(i).getTextContent();
				//int da=0;
			}
		}
		//String a = e.item(0).getTextContent();
		return info;
	}
	private Cost parseCost(NamedNodeMap infoCost){
		int earth = Integer.parseInt(takeValue(infoCost,"earth"));
		int air = Integer.parseInt(takeValue(infoCost,"air"));
		int sea = Integer.parseInt(takeValue(infoCost,"sea"));
		int spirit = Integer.parseInt(takeValue(infoCost,"spirit"));
		int generic = Integer.parseInt(takeValue(infoCost,"generic"));
		return new Cost(earth, sea, air, spirit, generic);
	}
	private ArrayList<es.ucm.fdi.lps.p6.model.data.cards.Card.Element> parseElements(NamedNodeMap infoCost){
		ArrayList<es.ucm.fdi.lps.p6.model.data.cards.Card.Element> aux = new ArrayList<es.ucm.fdi.lps.p6.model.data.cards.Card.Element>();
		boolean earth = Boolean.parseBoolean(takeValue(infoCost,"earth"));
		boolean air = Boolean.parseBoolean(takeValue(infoCost,"air"));
		boolean sea = Boolean.parseBoolean(takeValue(infoCost,"sea"));
		boolean spirit = Boolean.parseBoolean(takeValue(infoCost,"spirit"));
		boolean generic = Boolean.parseBoolean(takeValue(infoCost,"generic"));
		if(earth){
			aux.add(es.ucm.fdi.lps.p6.model.data.cards.Card.Element.earth);
		}
		if(air){
			aux.add(es.ucm.fdi.lps.p6.model.data.cards.Card.Element.air);
		}
		if(sea){
			aux.add(es.ucm.fdi.lps.p6.model.data.cards.Card.Element.sea);
		}
		if(spirit){
			aux.add(es.ucm.fdi.lps.p6.model.data.cards.Card.Element.spirit);
		}
		if(generic){
			aux.add(es.ucm.fdi.lps.p6.model.data.cards.Card.Element.generic);
		}
		return aux;
	}
	@Override
	public void close() {
		cards = null;
		
	}
	@Override
	public void open(String conexion)  throws DAOError  {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try{
			dbFactory.setValidating(true);
			dBuilder = dbFactory.newDocumentBuilder();
			
			dBuilder.setErrorHandler(new ErrorHandler() {
				
				@Override
				public void warning(SAXParseException e) throws SAXException {
					throw new SAXException("Error en xml: "+e.getLocalizedMessage() +"linea: "+ e.getLineNumber() + "Columna: "+ e.getColumnNumber());
				}
				
				@Override
				public void fatalError(SAXParseException e) throws SAXException {
					throw new SAXException("Error en xml: "+e.getLocalizedMessage()  +"linea: "+ e.getLineNumber() + "Columna:" + e.getColumnNumber());
				}
				
				@Override
				public void error(SAXParseException e) throws SAXException {
					throw new SAXException("Error en xml: "+e.getLocalizedMessage()  +"linea: "+ e.getLineNumber() + "Columna:" + e.getColumnNumber());
				}
			});
			
			doc = dBuilder.parse(new File(conexion));
			
			doc.getDocumentElement().normalize();
	
			cards = doc.getElementsByTagName("card");	
		} catch (SAXException e) {
			throw new DAOError(e.getLocalizedMessage());
		} catch (IOException e) {
			throw new DAOError("Hubo un error con la apertura del fichero"+ e.getLocalizedMessage());
		}catch (ParserConfigurationException e) {
			throw new DAOError(e.getLocalizedMessage());
		}catch (Exception e) {
			throw new DAOError(e.getLocalizedMessage());
		}
	}
}
