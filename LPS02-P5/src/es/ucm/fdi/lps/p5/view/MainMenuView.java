/**
 * 
 */
package es.ucm.fdi.lps.p5.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import es.ucm.fdi.lps.p5.DAO.DAOError;
import es.ucm.fdi.lps.p5.controller.Controller;
import es.ucm.fdi.lps.p5.model.data.FileError;

/**
 * @author Roni
 *
 */
public class MainMenuView extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JButton playVS;
	private JButton help;
	private JButton exit;
	private Timer t;
	private LoadView load;
	
	/**
	 * builder
	 * @param controller the controller
	 */
	public MainMenuView(Controller controller){
		this.controller = controller;
		build();
		configListener();
		
	}
	/**
	 * configuration listener
	 */
	private void configListener(){
		ActionPlay play =  new ActionPlay();
		playVS.addActionListener(play);
		
		ActionExit exitListe =  new ActionExit();
		exit.addActionListener(exitListe);
		
		help.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame aux = new JFrame();
				aux.add(new HelpMenu());
				
				aux.setSize(1000,700);
				aux.setMinimumSize(new Dimension(1000, 700));
				aux.setLocationRelativeTo(null);
				aux.pack();
				aux.setVisible(true);
				
			}
		});
	}
	/**
	 * build the frame
	 */
	private void build(){
		setSize(600,100);
		setVisible(true);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		JPanel panelUp =  new JPanel();
		panelUp.setLayout(new GridLayout(2, 1));
		
		playVS = new JButton("Jugar 1vs1");

		help = new JButton("Ayuda");
		
		exit = new JButton("Salir");
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelUp.setMaximumSize(new Dimension(250,50));

		panel.add(Box.createRigidArea(new Dimension(50, 50)));
		panelUp.add(playVS);
		panelUp.add(help);
		
		panel.add(panelUp);
		panel.add(Box.createRigidArea(new Dimension(50, 50)));

		panel.add(exit);
		
		panel.add(Box.createRigidArea(new Dimension(50, 50)));
		
		
		getContentPane().add(panel);
		setTitle("Brujos & Trifulcas");
		setPreferredSize(new Dimension(350, 250));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setLocationRelativeTo(null);
		setResizable(false);
		pack();
		setVisible(true);
	}
	public class ActionPlay implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			load = new LoadView();
			
			dispose();
			setVisible(false);
			
			
			t = new Timer(2000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GameView game = new GameView(controller);
					controller.addObserver(game);
					game.setVisible(false);
					try {
						controller.iniGame();
					}catch (DAOError d) {
						JOptionPane.showMessageDialog(getContentPane(), d.getInfo(),"Error", JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					}catch (FileError f) {
						JOptionPane.showMessageDialog(getContentPane(), f.getInfo(),"Error", JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					}catch (IOException e2) {
						JOptionPane.showMessageDialog(getContentPane(),"Error con un problema en la apertura del fichero","Error", JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					}catch (Exception e1) {
						game.dispose();
						game.setVisible(false);
						JOptionPane.showMessageDialog(getContentPane(), "Error leyendo los fichero,\n Porfavor verifíquelo","Error", JOptionPane.ERROR_MESSAGE);
						System.exit(-1);
					}
					load.dispose();
					load.setVisible(false);
					load = null;
					game.setVisible(true);
					game.fullScreen();
					t.stop();
					
					
					
				}
			});
			t.start();
		}
		
	}
	public class ActionExit implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
			
		}
		
	}
}
