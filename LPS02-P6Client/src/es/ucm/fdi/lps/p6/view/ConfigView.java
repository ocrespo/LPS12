package es.ucm.fdi.lps.p6.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ucm.fdi.lps.p6.controller.Controller;

public class ConfigView extends JFrame {

	private static final long serialVersionUID = 1L;
	private Controller controller = null;
	private JPanel main;
	private JTextField minDeck = new JTextField();
	private JTextField maxDeck = new JTextField();
	private JComboBox hand;
	private JTextField dirA = new JTextField();
	private JTextField dirB = new JTextField();
	private JTextField dirImgA = new JTextField();
	private JTextField dirImgB = new JTextField();
	private JButton browserImgA = new JButton("Examinar");
	private JButton browserImgB = new JButton("Examinar");
	private JButton browserA = new JButton("Examinar");
	private JButton browserB = new JButton("Examinar");
	private JComboBox life;
	private JCheckBox debug = new JCheckBox();
	private JCheckBox admin = new JCheckBox();
	private JButton ok = new JButton("Aceptar");
	private JButton cancel = new JButton("Cancelar");
	
	/**
	 * builder
	 * @param controller the controller
	 * @param minDeck the minumum cards in deck
	 * @param maxDeck the maximum cards in deck
	 * @param hand the initial card in hand
	 * @param lifes the lifes of the warlocks
	 * @param dirA address of warlock file
	 * @param dirB address of warlock file
	 * @param debug is debug mode
	 * @param admin id admin mode
	 */
	public ConfigView(Controller controller,int minDeck,int maxDeck,int hand,int lifes,String dirA,String dirB,boolean debug,boolean admin){
		this.controller = controller;
		this.minDeck.setText(String.valueOf(minDeck));
		this.maxDeck.setText(String.valueOf(maxDeck));
		
		Integer[] lifeValue = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
		life = new JComboBox(lifeValue);
		this.life.setSelectedIndex(lifes);
		Integer[] handValue = {3,4,5,6,7};
		this.hand = new JComboBox(handValue);
		this.hand.setSelectedItem(hand);
		
		this.dirA.setText(dirA);
		this.dirB.setText(dirB);
		this.debug.setSelected(debug);
		this.admin.setSelected(admin);
		this.admin.setVisible(admin);
		build();
		configListener();
	}
	/**
	 * build the frame
	 */
	private void build(){
		 main = new JPanel(new GridLayout(7,2,10,10));
		
		minDeck.setToolTipText("Introduce un valor mayor que 3");
		minDeck.setToolTipText("Introduce un valor menor que 100");
		
		addRow("Minimo numero de cartas en grimorio", minDeck);
		addRow("Máximo numero de cartas en grimorio",maxDeck);
		

		addRow("Número de cartas en la mano", hand);
		
		
		addRow("Vidas", life);
		//addRow("Máximo numero de cartas en la mano", maxHand);
		JPanel aux = new JPanel(new GridLayout(1,2));
		aux.add(dirA);
		aux.add(browserA);
		addRow("Fichero BrujoA", aux);
		
		aux = new JPanel(new GridLayout(1,2));
		aux.add(dirB);
		aux.add(browserB);
		addRow("Fichero BrujoB", aux);
		
		aux = new JPanel(new GridLayout(1,2));
		aux.add(dirImgA);
		aux.add(browserImgA);
		addRow("Imagen Brujo A", aux);
		
		aux = new JPanel(new GridLayout(1,2));
		aux.add(dirImgB);
		aux.add(browserImgB);
		addRow("Imagen Brujo B", aux);
		
		
		addRow("Modo debug", debug);
		if(admin.isSelected()){
			addRow("Modo administrador", admin);
		}
		else{
			addRow("", admin);
		}
		//addRow("Minimo numero de cartas en grimorio", new JTextField());
		main.add(ok);
		main.add(cancel);

		
		setSize(new Dimension(800, 400));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(main);
		setTitle("Configuracion inicial");
		
		pack();
		setVisible(true);
	}
	/**
	 * add component to row which label
	 * @param text the text of the label
	 * @param c the component
	 */
	private void addRow(String text,Component c){
		JPanel aux = new JPanel(new GridLayout(1, 2,5,5));
		aux.add(new JLabel(text));
		aux.add(c);
		main.add(aux);
	}
	/**
	 * config component´s listener
	 */
	private void configListener(){
		browserA.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inifile(dirA,true);
				
			}
		});
		browserB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inifile(dirB,true);
				
			}
		});
		browserImgA.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						inifile(dirImgA,false);
						
					}
				});
		browserImgB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				inifile(dirImgA,false);
				
			}
		});
		life.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JComboBox cb = (JComboBox)e.getSource();
		        Integer petName = (Integer)life.getSelectedItem();
		        dirA.setText(petName.toString());
				
			}
		});
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		minDeck.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					controller.validRange(minDeck.getText());
				} catch (Exception e) {
					minDeck.setBackground(Color.red);
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				minDeck.setBackground(Color.white);
				
			}
		});
		maxDeck.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					controller.validRange(maxDeck.getText());
				} catch (Exception e) {
					maxDeck.setBackground(Color.red);
				}
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				maxDeck.setBackground(Color.white);
				
			}
		});
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean ok = false;
				try {
					ok = controller.validateConfig(minDeck.getText(), maxDeck.getText(),(Integer) hand.getSelectedItem(),(Integer) life.getSelectedItem(), dirA.getText(), 
								dirB.getText(), debug.isSelected(), admin.isSelected(),dirImgA.getText(),dirImgB.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(getContentPane(), "Tienes valores no válidos","Error", JOptionPane.ERROR_MESSAGE);
				}
				if(ok){
					new MainMenuView(controller);
					dispose();
					setVisible(false);
				}
				else{
					JOptionPane.showMessageDialog(getContentPane(), "Tienes valores no válidos","Error", JOptionPane.ERROR_MESSAGE);
				}
			
			}
		});
	}
	/**
	 * initialized browser file
	 * @param text the textfield where put the ath of file
	 * @param filter if its need file filter
	 */
	private void inifile(JTextField text,boolean filter){
		JFileChooser chooser = new JFileChooser();
		if(filter){
			FileFilter fil = new FileNameExtensionFilter("Archivo de Brujo (.brujo)", "brujo");
			chooser.setFileFilter(fil);
		}
				
		File f = null;
		try {
			f = new File(new File(".").getCanonicalPath());
		} catch (IOException e) {
		}
		chooser.setCurrentDirectory(f);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int option = chooser.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION) {	
			text.setText( chooser.getSelectedFile().getAbsolutePath());
		}
	}
}
