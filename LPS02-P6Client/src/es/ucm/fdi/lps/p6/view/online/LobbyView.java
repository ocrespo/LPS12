/**
 * 
 */
package es.ucm.fdi.lps.p6.view.online;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import es.ucm.fdi.lps.p6.controller.Controller;
import es.ucm.fdi.lps.p6.controller.ObserverNet;
import es.ucm.fdi.lps.p6.view.GameView;
import es.ucm.fdi.lps.p6.view.ImagePanel;
import es.ucm.fdi.lps.p6.view.LoadView;

/**
 * @author Roni
 *
 */
public class LobbyView extends JFrame implements ObserverNet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel main;
	private JTextField name = new JTextField("Player");
	private JTextField ip = new JTextField("localhost");
	private JButton ok = new JButton("Aceptar");
	private JButton start = new JButton("Empezar");
	private JPanel imgA = new JPanel();
	private JPanel imgB = new JPanel();
	private JLabel nameA = new JLabel();
	private JLabel nameB = new JLabel();
	private GameView game;
	private Timer t;
	
	private Controller controller;
	
	public LobbyView(Controller controller){
		this.controller = controller;
		buildIniLobby();
		configListener();
	}
	public void buildIniLobby(){
		main = new JPanel(new GridLayout(3, 2));
		main.add(new JLabel("Nombre"));
		main.add(name);
		main.add(new JLabel("IP del server"));
		main.add(ip);
		main.add(ok);
		
		getContentPane().add(main);
		setSize(600,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}
	public void buildLobby(){
		main.removeAll();
		main.setLayout(new BorderLayout());
		JPanel zoneA = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel zoneB = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JPanel center = new JPanel();
		
		zoneA.add(imgA);
		zoneA.add(nameA);
		
		zoneB.add(nameB);
		zoneB.add(imgB);
		
		
		main.add(zoneA,BorderLayout.NORTH);
		main.add(zoneB,BorderLayout.SOUTH);
		main.add(center,BorderLayout.CENTER);
		
		JPanel image = new ImagePanel(800,100,"imagenes\\Imagenes fondo\\Fondo Versus.png", false, false,false);
		center.add(image);
		center.add(start);
		
		setMinimumSize(new Dimension(600, 500));
		setTitle("Sala de espera");
		main.repaint();
		
	
	}
	public void configListener(){
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {		
				addObserver();
				controller.modeNet(ip.getText(),name.getText());
				
				buildLobby();
			}
		});
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start.setEnabled(false);
				//removeObserver();
				controller.ready();			
			}
		});
	}
	public void removeObserver(){
		controller.removeObserverNet(this);
	}
	public void addObserver(){
		controller.addObserverNet(this);
	}
	@Override
	public void updatePlayerLobby(String nameA,String dirA, String nameB,String dirB) {
		this.nameA.setText(nameA);
		if(nameB == null){
			this.nameB.setText("Esperando jugador...");
		}
		else{
			this.nameB.setText(nameB);
		}
		
		ImagePanel avatar = null;
		if(imgA.getComponentCount() == 0){
			if(dirA == null ||dirA.equals("")){
				dirA = "imagenes//Iconos//perfil.jpg";
			}
			avatar = new ImagePanel(95,105,dirA,false,false,true);
			imgA.add(avatar);
			imgA.revalidate();
		}
		
		
		if(imgB.getComponentCount() == 0 && nameB != null){
			if(dirB == null ||dirB.equals("")){
				dirB = "imagenes//Iconos//perfil.jpg";
			}
			avatar = new ImagePanel(95,105,dirB,false,false,true);
			imgB.add(avatar);
			imgB.revalidate();
		}
		
		main.repaint();
		
	}
	@Override
	public void updateStart() {	
		
		dispose();
		setVisible(false);
		final LoadView load = new LoadView();
		
		t = new Timer(2000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				t.stop();
				t = null;
				game = new GameView(controller,true);
				controller.addObserverGUI(game);
				game.setVisible(false);
				game.setLoad(load);
			
				removeObserver();
				controller.start();
				
				
				//load.dispose();
				/*load.setVisible(false);
				game.setVisible(true);
				game.fullScreen();
				*/
			}
		});
		t.start();
		
	}
	@Override
	public void updateError(String text) {
		JOptionPane.showMessageDialog(getContentPane(), text,"Error", JOptionPane.ERROR_MESSAGE);
		
	}
}
