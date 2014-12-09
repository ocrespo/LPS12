/**
 * 
 */
package es.ucm.fdi.lps.p5.DAO;

/**
 * @author Roni
 *
 */
public class DAOError extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String info;

	public DAOError(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
