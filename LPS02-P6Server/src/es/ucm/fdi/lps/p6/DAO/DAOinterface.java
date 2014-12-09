/**
 * 
 */
package es.ucm.fdi.lps.p6.DAO;

/**
 * @author Roni
 *
 */
public interface DAOinterface {
	/**
	 * 
	 * @param key the code of the card
	 * @return DTO whith information
	 * @throws DAOError error
	 */
	public DTOCard getCard(String key) throws DAOError;
	/**
	 * Close conexion
	 */
	public void close();
	/**
	 * open conexion
	 * @param conexion the dir conexion
	 * @throws DAOError a error
	 */
	public void open(String conexion) throws DAOError;
}
