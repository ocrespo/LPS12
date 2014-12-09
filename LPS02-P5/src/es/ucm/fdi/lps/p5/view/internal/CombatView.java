/**
 * 
 */
package es.ucm.fdi.lps.p5.view.internal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import es.ucm.fdi.lps.p5.controller.Controller;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.view.GameView;
import es.ucm.fdi.lps.p5.view.ImagePanel;
import es.ucm.fdi.lps.p5.view.GameView.CardPanel;

/**
 * @author Roni
 *
 */
public class CombatView extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private TreeMap<Card, LinkedList<CardPanel>> combat;
	private JTabbedPane tab;
	private JLabel damage;
	private Timer t;
	private boolean automatic = false;
	private ArrayList<JLabel>  infoSolve = new ArrayList<JLabel>();
	private JButton pause;
	private JLabel erDeal;
	private Controller controller;
	private GameView game;
	
	
	private static final String dirVSIcon =  "imagenes\\Imagenes fondo\\Fondo Versus.png";
	
	/**
	 * build
	 * @param combat the info about combat
	 * @param automatic if go to resolve combat
	 * @param controller the controller
	 * @param game the gameView
	 */
	public CombatView(TreeMap<Card, LinkedList<CardPanel>> combat,boolean automatic,Controller controller,GameView game){
		this.combat = combat;
		this.automatic = automatic;
		this.controller = controller;
		this.game = game;
		build();
		configListener();
	}
	/**
	 * config the frame and add default panels
	 */
	private void build(){
		setPreferredSize(new Dimension(800, 550));
		setMinimumSize(new Dimension(800, 550));
		
		tab = new JTabbedPane();
		
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
		fill();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		setLocation(d.width/4, d.height/4);
		setClosable(!automatic);
		pause = new JButton("Pausar");
		main.add(tab);
		if(automatic){
			setTitle("Resolviendo Trifulca");
			tab.setFocusable(false);
			tab.setEnabled(false);
			
			
			main.add(pause);
		}
		else{
			setTitle("Trifulcas activas");
		}
		//setClosable(false);
		//setUndecorated(automatic);
		//setDefaultCloseOperation(operation))
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		getContentPane().add(main);
		pack();
		setVisible(true);
	}
	/**
	 * config the initials listener
	 */
	private void configListener(){
		pause.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(t != null){
					if(t.isRunning()){
						t.stop();
						pause.setText("Continuar");
					}
					else{
						t.start();
						pause.setText("Parar");
					}
				}
				
			}
		});
		
		
		/**
		 * configuration the operation close
		 */
		addInternalFrameListener(new InternalFrameListener() {
			
			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {
				
			}
			
			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {			
			}
			
			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {			
			}
			
			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {				
			}
			
			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {
				//tab.removeAll();
				setVisible(false);
				game.finishCombatFrame();
				game.activeActActionPanel();
			}
			
			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {
				
				
			}
			
			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {
				
			}
		});
	}
	/**
	 * fill the zone of card which the information
	 */
	private void fill(){
		for(LinkedList<CardPanel> lin : combat.values()){
			Iterator<CardPanel> it = lin.iterator();
			JPanel com = new JPanel(new BorderLayout());
			JPanel up = new JPanel(new BorderLayout());
			
			CardPanel or = ((CardPanel) it.next());
			CardPanel at = (CardPanel) or.clone();
			//or.setCanUse(false);
			at.defaultCard();
			up.add(at,BorderLayout.WEST);
			
			JLabel info = new JLabel("<html>");
			infoSolve.add(info);
			up.add(info,BorderLayout.EAST);
			JPanel down = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			
			CardPanel clon = null;
			while(it.hasNext()){
				or = ((CardPanel) it.next());
				clon = (CardPanel) or.clone();
				clon.defaultCard();
				//or.setCanUse(false);
				//clon.setCanUse(false);
				if(automatic){
					clon.removeMouseEventAction();
					clon.addMouseListener(new DealDamage(clon));
				}
				//clon.addMouse();
				down.add(clon);
			}
			JPanel image = new ImagePanel(800,100,dirVSIcon, false, false,false);
			com.add(image,BorderLayout.CENTER);
			com.add(up,BorderLayout.NORTH);
			
			JScrollPane sDown = new JScrollPane(down);
		
			sDown.setPreferredSize(new Dimension(880, 190));
			sDown.setAlignmentX(FlowLayout.RIGHT);
			
			com.add(sDown,BorderLayout.SOUTH);
			tab.add(at.getNameCard(),com);
			
		}
	}
	/**
	 * inicialize the deal damage moment
	 * @param count the damage to deal
	 */
	public void iniDealDamage(int count){
		t.stop();
		JButton pass = new JButton("Pasar");
		pass.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.passDeal();
				t.start();
				pause.setEnabled(true);
			}
		});
		
		JPanel middle = new JPanel(new FlowLayout(FlowLayout.LEFT));
		middle.add(pass);
		middle.add(new JLabel("Reparta el daño entre los seres. Daño:"));
		damage = new JLabel(String.valueOf(count));
		middle.add(damage);
		
		erDeal = new JLabel("<html>");
		erDeal.setForeground(Color.red);
		middle.add(erDeal);
		
		pause.setEnabled(false);
		//main.getC;
		//int pos = main.getSelectedIndex();
		//main.getcomp
		((JPanel)tab.getSelectedComponent()).add(middle,BorderLayout.CENTER);
		tab.revalidate();
		
		
	}
	/**
	 * go to next combat
	 */
	public void nextCombat(){
		int pos = tab.getSelectedIndex();
		pause.setEnabled(true);
		pos++;
		if(pos < tab.getComponents().length)
			tab.setSelectedIndex(pos);
	}
	/**
	 * set the timer
	 * @param t the timer
	 */
	public void setTimer(Timer t){
		this.t = t;
	}
	/**
	 * add info to the actually combat
	 * @param text the information
	 */
	public void addCombatText(String text){
		JLabel info = infoSolve.get(tab.getSelectedIndex());
		info.setText(info.getText()+"<br>"+text);
	}
	/**
	 * show the error information in actually combat
	 * @param text the info about the error
	 */
	public void setErrorDeal(String text){
		erDeal.setText("        "+text);
	}
	/**
	 * decrease the damage to deal in 1
	 */
	public void decreaseDealDamage(){
		int count = Integer.parseInt(damage.getText());
		count--;
		damage.setText(String.valueOf(count));
		erDeal.setText("");
		//JPanel aux = (JPanel) tab.getSelectedComponent();
		//aux.getComponent(2).getc;
		//tab.getSelectedComponent();
		tab.repaint();
	}
	/**
	 * mouse evnt to deal damage
	 * @author ocrespo
	 *
	 */
	private class DealDamage implements MouseListener{
		private CardPanel panel;
		public DealDamage(CardPanel panel){
			this.panel = panel;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(damage != null &&!damage.getText().equals("0")){
				controller.dealDamage(panel.getId());
			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {

			
		}
		
	}
}
