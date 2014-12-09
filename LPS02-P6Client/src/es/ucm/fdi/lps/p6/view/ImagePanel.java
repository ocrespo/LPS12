/**
 * 
 */
package es.ucm.fdi.lps.p6.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.JPanel;

/**
 * @author Roni
 *
 */
public class ImagePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image image;
	private Image combat;
	private Image check;
	private Image wrong;
	private boolean isCombat = false;
	private boolean isCheck = false;
	private boolean isWrong = false;
	
	private static final String dirCombatIcon = "imagenes//Iconos//Combate.png";
	private static final String dirOkIcon = "imagenes//Iconos//Ok.png";
	private static final String dirWrongIcon = "imagenes//Iconos//Bloqueo_brujo.png";
	
    public ImagePanel(int width ,int height,String file,boolean combat,boolean check,boolean disable) {
    	super.setMaximumSize(new Dimension(width, height));
    	super.setMinimumSize(new Dimension(width, height));
    	super.setPreferredSize(new Dimension(width, height));
    	
    	try { 
    		/*BufferedImage aux = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    		//tmp = ImageIO.read(new File("imagenes\\"+file));
    		aux = ImageIO.read(new File(file));
    		image = aux.getScaledInstance(width, height,Image.SCALE_AREA_AVERAGING);*/
    		image = (ImageLoad.getInstance()).getImage(file, width, height);
    		if(combat){
	    		/*aux = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
	    		//tmp = ImageIO.read(new File("imagenes\\"+file));
	    		aux = ImageIO.read(new File(dirCombatIcon));*/
	    		this.combat = (ImageLoad.getInstance()).getImage(dirCombatIcon, 20, 20);
    		}
    		if(check){
	    		/*aux = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
	    		//tmp = ImageIO.read(new File("imagenes\\"+file));
	    		aux = ImageIO.read(new File(dirOkIcon));*/
	    		this.check = (ImageLoad.getInstance()).getImage(dirOkIcon, 20, 20);
    		}
    		if(disable){
    			/*aux = new BufferedImage(40,40, BufferedImage.TYPE_INT_RGB);
	    		aux = ImageIO.read(new File(dirWrongIcon));*/
	    		this.wrong = (ImageLoad.getInstance()).getImage(dirWrongIcon, 40, 40);
    		}
    		
    	} catch (IOException ex) {
    	}    	
    }

    @Override
    public void paintComponent(Graphics g) {	    		    	 	       	    	
        g.drawImage(image,0,0, null);
        if(isCombat){
        	 g.drawImage(combat,0,0, null);
        }
        if(isCheck){
        	g.drawImage(check,0,0, null);
        }
        if(isWrong){
        	g.drawImage(wrong,5,5, null);
        }
    }

	/**
	 * @param isCombat the isCombat to set
	 */
	public void setCombat(boolean isCombat) {
		this.isCombat = isCombat;
	}


	/**
	 * @param isCheck the isCheck to set
	 */
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	/**
	 * @param isWrong the isWrong to set
	 */
	public void setWrong(boolean isWrong) {
		this.isWrong = isWrong;
	}

}
