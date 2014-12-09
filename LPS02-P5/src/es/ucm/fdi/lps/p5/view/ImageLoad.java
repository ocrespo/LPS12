/**
 * 
 */
package es.ucm.fdi.lps.p5.view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


/**
 * @author Roni
 *
 */
public class ImageLoad {
	
	private static ImageLoad SINGLETON = null;
	private HashMap<String, Image> images = new HashMap<String, Image>();
	
	/**
	 * builder of the judge
	 */
	private ImageLoad() {} 
	@SuppressWarnings("unused")
	private synchronized static void createInstance() {
	    if (SINGLETON == null) { 
	    	SINGLETON = new ImageLoad(); 
	    }
	  }
	/**
	 * 
	 * @return the instance of judge
	 */
	public final static ImageLoad getInstance() { 
	    if (SINGLETON == null)
	    	SINGLETON = new ImageLoad();
	    
	    return SINGLETON;
	  } 
	@Override
	/**
	 * 
	 */
	public Object clone() throws CloneNotSupportedException { 
		throw new CloneNotSupportedException();  
	}
	
	public Image getImage(String key,int width,int height) throws IOException{
		Image aux = images.get(key);
		if(aux == null){
			BufferedImage buff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    		//tmp = ImageIO.read(new File("imagenes\\"+file));
			buff = ImageIO.read(new File(key));
    		aux = buff.getScaledInstance(width, height,Image.SCALE_AREA_AVERAGING);
    		images.put(key, aux);
		}
		
		return aux;
	}
	
	
}
