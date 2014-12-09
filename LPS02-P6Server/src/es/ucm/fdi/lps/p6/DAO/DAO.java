/**
 * 
 */
package es.ucm.fdi.lps.p6.DAO;

import es.ucm.fdi.lps.p6.model.data.Configuration;


/**
 * @author Roni
 *
 */
public class DAO implements DAOinterface {
	private static DAO SINGLETON;
	
	
	/**
	 * builder of the judge
	 * @throws DAOError 
	 */
	
	@SuppressWarnings("unused")
	private synchronized static void createInstance() throws DAOError {
	    if (SINGLETON == null) { 
	    	SINGLETON = DAOFactory.getDAO((Configuration.getInstance()).getDAO()); 
	    }
	  }
	/**
	 * 
	 * @return the instance of judge
	 * @throws DAOError 
	 */
	public final static DAO getInstance() throws DAOError { 
	    if (SINGLETON == null)
	    	SINGLETON = DAOFactory.getDAO((Configuration.getInstance()).getDAO());
	    
	    return SINGLETON;
	  } 
	@Override
	/**
	 * 
	 */
	public Object clone() throws CloneNotSupportedException { 
		throw new CloneNotSupportedException();  
	}
	@Override
	public DTOCard getCard(String key) throws DAOError {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void open(String conexion) throws DAOError {
		// TODO Auto-generated method stub
		
	}
}
