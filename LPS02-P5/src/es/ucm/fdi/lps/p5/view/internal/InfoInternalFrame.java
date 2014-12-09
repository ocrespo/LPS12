/**
 * 
 */
package es.ucm.fdi.lps.p5.view.internal;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Roni
 *
 */
public class InfoInternalFrame extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	
	private JLabel infoText;
	private JLabel valueText;

	/**
	 * builder
	 * @param infoText the test
	 * @param isValue if have value
	 * @param valueText the value
	 */
	public InfoInternalFrame(String infoText,boolean isValue,String valueText	){
		this.infoText = new JLabel(infoText);
		if(isValue){
			this.valueText = new JLabel(valueText);
		}
		build();
	}
	/**
	 * build the frame
	 */
	private void build(){
		JPanel main = new JPanel(new FlowLayout());
		
		main.add(infoText);
		if(valueText != null){
			main.add(valueText);
		}
	
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(d.width/4, d.height/4);	
		
		setClosable(false);
		getContentPane().add(main);
		pack();
		setVisible(true);
	}
	/**
	 * set value
	 * @param value the value to set
	 */
	public void setValue(String value){
		valueText.setText(value);
	}
	/**
	 * 
	 * @return the value
	 */
	public String getValue(){
		return valueText.getText();
	}
	/**
	 * add text
	 * @param text the text
	 */
	public void addText(String text){
		infoText.setText("<html>"+infoText.getText() + "<br>"+text);
	}
}
