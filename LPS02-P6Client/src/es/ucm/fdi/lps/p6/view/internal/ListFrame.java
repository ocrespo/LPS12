/**
 * 
 */
package es.ucm.fdi.lps.p6.view.internal;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import es.ucm.fdi.lps.p6.view.GameView;
import es.ucm.fdi.lps.p6.view.GameView.CardPanel;

/**
 * @author Roni
 *
 */
public class ListFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Timer t;
	private JButton accept = new JButton("Aceptar");
	private JPanel zoneList = new JPanel(new FlowLayout());
	private JPanel zoneOr ;
	private GameView game;
	
	public ListFrame(JPanel zone,Timer t,GameView game){
		this.t = t;
		this.zoneOr = zone;
		fill(zoneList, zone,false);
		this.game = game;
		build();
		configListener();
	}
	/**
	 * build frame
	 */
	public void build(){
		JPanel main = new JPanel(new BorderLayout());
		
		//zoneList = 
		
		JScrollPane sZone = new JScrollPane(zoneList);
		
		main.add(zoneList,BorderLayout.CENTER);
		main.add(accept,BorderLayout.SOUTH);
		
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(d.width/4, d.height/4);
		setClosable(false);
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setPreferredSize(new Dimension(800, 500));
		
		//getContentPane().add(main);
		add(main);
		pack();
		setVisible(true);
	}
	/**
	 * configuration listener
	 */
	public void configListener(){
		accept.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(t != null){
					t.restart();
				}
				//setVisible(false);	
				fill(zoneOr, zoneList,true);
				game.finishListFrame();
			}
		});
		
	}
	/**
	 * fill the zone which cards
	 * @param zone zone which is fill
	 */
	public void fill(JPanel zone,JPanel zoneA,boolean canUse){
		Component[] cA = zoneA.getComponents();
		for(Component c : cA){
			//CardPanel aux =  ;
			//aux.removeMouseEventAction();
			((CardPanel) c).setCanUse(canUse);
			((CardPanel) c).defaultCard();
			zone.add(  c);
		}
		zone.revalidate();
	}
}
