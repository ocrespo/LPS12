/**
 * 
 */
package es.ucm.fdi.lps.p5.DAO;

/**
 * @author Roni
 *
 */
public class DAOFactory {
	public static DAO getDAO(String name) throws DAOError{
		Class daoClass;
		try {
			daoClass = Class.forName("es.ucm.fdi.lps.p5.DAO.DAO"+name);
			return (DAO) daoClass.newInstance();  
		} catch (ClassNotFoundException e) {
			throw new DAOError(e.getLocalizedMessage());
		} catch (InstantiationException e) {
			throw new DAOError(e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			throw new DAOError(e.getLocalizedMessage());
		}
	}
}
