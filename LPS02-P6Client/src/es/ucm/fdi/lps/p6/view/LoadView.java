/**
 * 
 */
package es.ucm.fdi.lps.p6.view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author Roni
 *
 */
public class LoadView extends JFrame {
	private static final long serialVersionUID = 1L;

	/**
	 * builder
	 */
	public LoadView(){
		build();
		
	}
	/**
	 * builder the frame
	 */
	public void build(){
		JPanel main = new JPanel();
		main.add(new ImagePanel(600, 600, "imagenes//Imagenes fondo//Fondo_cargando.png", false,false, false));
	
		getContentPane().add(main);
		
		setSize(600,600);
		setMinimumSize(new Dimension(600, 600));

		setUndecorated(true);
		setLocationRelativeTo(null);
		setTitle("Cargando");
		pack();
		setVisible(true);
	}
}
