/**
 * 
 */
package es.ucm.fdi.lps.p5.view.internal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import es.ucm.fdi.lps.p5.view.GameView;
import es.ucm.fdi.lps.p5.view.GameView.CardPanel;

/**
 * @author Roni
 *
 */
public class InterventionsSolveView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JLabel infoSolve;
	private JPanel zoneStack;
	private JPanel act;
	private JButton pause;
	private Timer t;
	
	/**
	 * builder
	 * @param zone the zone
	 */
	public InterventionsSolveView(JPanel zone) {
		build(zone);
		fill(zone);
		configListener();
	}
	/**
	 * build the frame
	 * @param zone
	 */
	public void build(JPanel zone){
		JPanel main = new JPanel(new BorderLayout());
		zoneStack = new JPanel();
		zoneStack.setLayout(new BoxLayout(zoneStack, BoxLayout.Y_AXIS));
		act = new JPanel(new FlowLayout());
		
		main.add(act,BorderLayout.NORTH);
		JScrollPane sZone = new JScrollPane(zoneStack);
		zoneStack.setMinimumSize(new Dimension(171,181));
		main.add(sZone,BorderLayout.EAST);
		
		infoSolve = new JLabel();
		pause = new JButton("Pausar");
		JPanel middle = new JPanel(new FlowLayout());
		middle.add(pause);
		middle.add(infoSolve);
		main.add(middle,BorderLayout.CENTER);
		
		
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(d.width/4, d.height/4);
		setClosable(false);
		setTitle("Resolviendo Intervenciones");
		
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setPreferredSize(new Dimension(800, 500));	
		
		getContentPane().add(main);
		pack();
		setVisible(true);
		
	}
	/**
	 * fill zone which CardPanels
	 * @param zone
	 */
	public void fill(JPanel zone){
		Component[] aux = zone.getComponents();
		for(Component c : aux){
			zoneStack.add((CardPanel) ((CardPanel) c).clone() );
		}
	}
	/**
	 * configuration listener
	 */
	public void configListener(){
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(t.isRunning()){
					t.stop();
					pause.setText("Continuar");
				}
				else{
					t.start();
					pause.setText("Pausar");
				}
				
			}
		});
	}
	/**
	 * next CardPanel
	 */
	public void next(){		
		if(zoneStack.getComponents().length > 0){
			act.removeAll();
			act.add(zoneStack.getComponent(0));	
			infoSolve.setText("");
		}
		zoneStack.revalidate();
		act.revalidate();
		
	}
	/**
	 * set the timer
	 * @param t the timer to set
	 */
	public void setTimer(Timer t){
		this.t = t;
	}
	/**
	 * set info text
	 * @param text the text to set
	 */
	public void setInfoText(String text){
		infoSolve.setText(text);
	}
	/**
	 * remove all CardPanel in stack
	 */
	public void removeStack(){
		zoneStack.removeAll();
		zoneStack.revalidate();
	}
}
