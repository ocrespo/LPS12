/**
 * 
 */
package es.ucm.fdi.lps.p6.model.data;

/**
 * @author Roni
 *
 */
public class FileError extends Exception {

	private static final long serialVersionUID = 1L;
	private String info;

	public FileError(String info){
		this.info = info;
	}
	
	public String getInfo(){
		return info;
	}
}
