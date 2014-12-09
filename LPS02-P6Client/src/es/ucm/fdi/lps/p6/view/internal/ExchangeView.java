/**
 * 
 */
package es.ucm.fdi.lps.p6.view.internal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.ucm.fdi.lps.p6.controller.Controller;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.view.GameView.CardPanel;

/**
 * @author Roni
 *
 */
public class ExchangeView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JPanel zoneA;
	private JPanel zoneB;
	private JLabel info ;
	private JLabel er;
	private JButton ok;
	private Controller controller;
	private final Card id;
	
	/**
	 * builder
	 * @param zoneA zone A
	 * @param zoneB zone B
	 * @param controller the controller
	 * @param c the card that use
	 */
	public  ExchangeView(JPanel zoneA,JPanel zoneB,Controller controller,final Card c){
		build();
		configListener();
		fill(this.zoneA,zoneA);
		fill(this.zoneB,zoneB);
		this.controller = controller;
		this.id = c;
		//updateUI();
	}
	/**
	 * build the initial frame
	 */
	public void build(){
		zoneA = new JPanel(new FlowLayout());
		zoneB = new JPanel(new FlowLayout());
		
		JPanel main = new JPanel(new BorderLayout());
		
		JPanel middle = new JPanel(new FlowLayout());
		ok = new JButton("Intercambiar");
		ok.setEnabled(false);
		info = new JLabel("Seleccione las cartas a intercambiar:");
		middle.add(info);
		middle.add(ok);
		
		er = new JLabel();
		er.setForeground(Color.red);
		middle.add(er);
		
		JScrollPane sA = new JScrollPane(zoneA);
		JScrollPane sB = new JScrollPane(zoneB);
		
		main.add(sB,BorderLayout.NORTH);
		main.add(sA,BorderLayout.SOUTH);
		main.add(middle,BorderLayout.CENTER);
		
		getContentPane().add(main);
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setPreferredSize(new Dimension(800, 500));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(d.width/4, d.height/4);
		pack();
		setVisible(true);
	}
	/**
	 * fill the zone which cards
	 * @param zone zone which is fill
	 * @param zoneA the originally zone
	 */
	public void fill(JPanel zone,JPanel zoneA){
		Component[] cA = zoneA.getComponents();
		CardPanel clon;
		for(Component c : cA){
			clon = (CardPanel) ((CardPanel) c).clone();
			clon.defaultCard();
			zone.add(clon );
		}
		zone.revalidate();
	}
	/**
	 * add initial listener
	 */
	public void configListener(){
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.playCard(id, null);
			}
		});
	}
	/**
	 * set the error text
	 * @param text the text
	 */
	public void setErText(String text){
		er.setText(text);
	
	}
	/**
	 * set can do exchange
	 */
	public void canExchange(){
		ok.setEnabled(true);
	}
	/**
	 * set cant do exchange
	 */
	public void cantExchange(){
		ok.setEnabled(false);
	}
}
