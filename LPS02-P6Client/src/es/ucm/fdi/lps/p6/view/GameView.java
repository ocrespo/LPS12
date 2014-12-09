package es.ucm.fdi.lps.p6.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import es.ucm.fdi.lps.p6.controller.Controller;
import es.ucm.fdi.lps.p6.controller.ObserverGUI;
import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.game.Model.Phase;
import es.ucm.fdi.lps.p6.view.internal.ChatFrame;
import es.ucm.fdi.lps.p6.view.internal.CombatView;
import es.ucm.fdi.lps.p6.view.internal.ExchangeView;
import es.ucm.fdi.lps.p6.view.internal.InfoInternalFrame;
import es.ucm.fdi.lps.p6.view.internal.InterventionsSolveView;
import es.ucm.fdi.lps.p6.view.internal.ListFrame;

public class GameView extends JFrame implements ObserverGUI {
	
	/**
	 * 
	 */
	//constants
	//Cards dimensions
	private static final Dimension dCardInfoMiddleSize = new Dimension(147,17);
	private static final Dimension dCardInfoSize = new Dimension(151, 160);
	private static final Dimension dCardInfoDownSize = new Dimension(147,32);
	private static final Dimension dCardActionSize = new Dimension(151, 160);
	private static final Dimension dCardManaSize = new Dimension(151, 160);
	
	private static final String dirEarthIcon = "imagenes//Iconos//Mountain.png";
	private static final String dirAirIcon =  "imagenes//Iconos//Viento.png";
	private static final String dirSeaIcon = "imagenes//Iconos//wave3000.png";
	private static final String dirSpiritIcon = "imagenes//Iconos//Spirit.png";
	private static final String dirGenericIcon = "imagenes//Iconos//generico.png";
	
	
	private static final String dirEarthIconLittle = "imagenes//Iconos//MountainIcon.png";
	private static final String dirAirIconLittle =  "imagenes//Iconos//VientoIcon.png";
	private static final String dirSeaIconLittle = "imagenes//Iconos//wave3000Icon.png";
	private static final String dirSpiritIconLittle = "imagenes//Iconos//SpiritIcon.png";
	
	//Cards icon
	private static final String dirAtIcon = "imagenes//Iconos//Ataque.png";
	private static final String dirDefIcon = "imagenes//Iconos//Escudo.png";
	
	private static final String dirBuffIcon = "imagenes//Iconos//Warning.png";
	
	private static final String dirLifeIcon = "imagenes//Iconos//Vida.png" ;
	private static final String dirLifeWarlockIcon = "imagenes//Iconos//VidaBrujo.png" ;
	
	private static final String dirEntityIcon = "imagenes//Iconos//imagen carta seres.png";
	private static final String dirEnviromentcon = "imagenes//Iconos//imagen carta entornos.png";
	private static final String dirInterventionIcon = "imagenes//Iconos//imagen carta intervenciones.png";
	
	private static final String dirCancelIcon = "imagenes//Iconos//icon_cancel.gif";
	private static final String dirBackgroundActionIcon = "imagenes//Imagenes Fondo//Fondo carta menu.png";
	private static final String dirTurnIcon = "imagenes//Iconos//descargado.png";
	
	//Warlocks icons
	private static final String dirDeckIcon = "imagenes//Iconos//Mazo.png";
	private static final String dirHandIcon = "imagenes//Iconos//Mano.png";
	private static final String dirGraveIcon = "imagenes//Iconos//Cementerio.png";
	
	private static final String dirDefaultImage ="imagenes//Iconos//perfil.jpg" ;
	
	
	//game sizes
	private static final Dimension dimensionScreen =  Toolkit.getDefaultToolkit().getScreenSize();

	private static final Dimension dGameZone = new Dimension(dimensionScreen.width, (dimensionScreen.height-180)/3);
	
	private static final Dimension dLateralZone =  new Dimension(171, dimensionScreen.height-152);
	private static final Dimension dPreLateralZone =  new Dimension(151, dimensionScreen.height-152);
	
	
	private static final Dimension dIntoLateralZone =  new Dimension(171, (dimensionScreen.height-152)-23);
	private static final Dimension dPreIntoLateralZone =  new Dimension(151, (dimensionScreen.height-152)-23);
	
	private static final Dimension dInfoZone = new Dimension(dimensionScreen.width, 152);
	
	private static final float prop =(float) dimensionScreen.width / (float)1024;
	
	private static Dimension dKeyPadSize = new Dimension((int)(417 * prop), 152);
	private static Dimension dPhaseZoneSize = new Dimension((int)(155  * prop), 152);	
	private static Dimension dWarlocksZoneSize = new Dimension((int)(452  * prop), 152);
	//game icon
	private static final String dirExitIcon = "imagenes//Iconos//Exit.png";
	private static final String dirHelpIcon = "imagenes//Iconos//Ayuda.png";
	private static final String dirSaveIcon = "imagenes//Iconos//save.png";
	private static final String dirLoadIcon = "imagenes//Iconos//open.png";
	private static final String dirChatIcon = "imagenes//Iconos//chat-icon.png";
	private static final String dirBackgroundGameAt = "imagenes//Imagenes fondo//Fondo Juego-ataque.png";
	private static final String dirBackgroundGameDef = "imagenes//Imagenes fondo//Fondo Juego-defensa.png";
	private static final String dirBackgroundGameMana = "imagenes//Imagenes fondo//Fondo Juego-mana.png";
	private static final String dirBackgroundLateral = "imagenes//Imagenes fondo//Mano.png";
	
	private LoadView load ;
	
	private static final long serialVersionUID = 1L;
	
	
	//variables
	private enum Select{defense,use,play};
	private Controller controller ;
	private JPanel main ;
	//private HashMap<Warlock, JP>
	private Select select;
	private JPanel gameZone;
	private GridBagConstraints constraints;
	private JPanel gameB;
	private JScrollPane scrollGameB;
	private JPanel gameA;
	private JScrollPane scrollGameA;
	private JPanel manaA;
	private JScrollPane scrollManaA;
	private JPanel manaB;
	private JScrollPane scrollManaB;
	private JPanel interventionPanel;
	private JPanel interventionZone = null;
	private JScrollPane scrollInterventionZone;
	private JPanel handA;
	private JScrollPane scrollHandA;
	private JPanel handB;
	private JScrollPane scrollHandB;
	private JPanel panelHand;
	private JPanel gameTurn;
	
	
	private JPanel gameAct;
	private JScrollPane scrollGameAct;
	private JPanel manaAct;
	private JScrollPane scrollManaAct;
	private JPanel handAct;
	private JScrollPane scrollHandAct;
	private JPanel gameDef;
	private JScrollPane scrollGameDef;
	private JPanel manaDef;
	private JScrollPane scrollManaDef;
	//private JPanel handDef;
	//private JScrollPane scrollHandDef;
	private JTabbedPane zoneDef;
	
	private JPanel waInfo;
	private JPanel wbInfo;
	
	
	private CardPanel selectCard = null;
	
	private JPanel infoZone = null;
	private CardPanel targetCard = null;
	
	//private Component[]  originCard = null;
	
	//private JPanel infoCard ; 
	//private JPanel card ;
	
	private TreeMap<Card, LinkedList<CardPanel>> combatInfo;
	
	private JLabel phase = new JLabel();
	private JButton handButton = new JButton("Ver mano");
	private JButton nextButton = new JButton("Siguiente");
	private JButton combatButton = new JButton("Ver Combate");
	private JButton exitButton = new JButton();
	private JButton helpButton = new JButton();
	private JButton cancelHanZoneButton;
	private JButton saveButton = new JButton();
	private JButton loadButton = new JButton();
	
	private JDesktopPane mainPanel;
	
	private GraphicsDevice gd;

	private CombatView combatFrame = null;
	private InfoInternalFrame discardFrame = null;
	private InfoInternalFrame infoFrame = null;
	private InfoInternalFrame rotateFrame = null;
	private ExchangeView exchangeFrame = null;
	private InterventionsSolveView interventionFrame = null;
	private JInternalFrame fileFrame = null;
	private ListFrame listFrame = null;
	private ChatFrame chat = null;
	
	
	private boolean solveCombat = false;
	//private boolean dealDamage = false;
	private Timer t;
	private Timer changeTimer;
	private Timer loadTimer = null;
	
	
	private boolean vs = false;
	private JButton chatButton = new JButton();
	/**
	 * builder
	 * @param controller the controller
	 */
	public GameView(Controller controller){
		this.controller = controller;
		//cardPanel();
		
		build();
		configListener();
		//controller.iniGame();
		
	}
	/**
	 * builder
	 * @param controller the controller
	 */
	public GameView(Controller controller,boolean vs){
		this.controller = controller;
		//cardPanel();
		this.vs = vs;
		build();
		configListener();
		
		//controller.iniGame();
		
	}
	/**
	 * config component´s listener
	 */
	private void configListener(){
		handButton.addActionListener(new HandEvent());
		nextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.nextPhase();
				if(combatFrame != null && !solveCombat){
					combatFrame.setVisible(false);
					combatFrame = null;
				}
				if(fileFrame != null){
					fileFrame.setVisible(false);
					fileFrame.dispose();
				}
				
			}
		});
		exitButton.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!vs)
					System.exit(0);
				else{
					controller.goFinish();
				}
			}
		});
		combatButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				showCombatFrame(false);				
			}
		});
		helpButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JInternalFrame aux = new JInternalFrame();
				aux.add(new HelpMenu());
				
				aux.setSize(1000,700);
				aux.setMinimumSize(new Dimension(1000, 700));
				aux.setPreferredSize(new Dimension(1000, 700));
				
				aux.setTitle("Ayuda");
				aux.setClosable(true);
				mainPanel.add(aux);
				aux.pack();
				aux.setVisible(true);
				
			}
		});
		
		saveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inifile(true,"Guardar");
			}
		});
		
		loadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inifile(true,"Cargar");
			}
		});
		chatButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showChat();
				chatButton.setBackground(new Color(238,238,238));
				
			}
		});
		
	}
	/**
	 * build the frame
	 */
	private void  build(){
		
		//create main panel
		main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		iniGameZone();
		main.add(gameZone);
		infoZone = infoZone();
		main.add(infoZone);
		
		//create handA panel
		//handA = new JPanel();
		handA =  new PanelBackgroundImage(dirBackgroundLateral);
		handA.setLayout(new BoxLayout(handA,BoxLayout.Y_AXIS));
		scrollHandA = new JScrollPane(handA);
		iniHandZone( scrollHandA);
		
		handAct = handA;
		scrollHandAct = scrollHandA;
		
		iniPanelHand();
		
		//create handB panel
		//handB = new JPanel();
		handB = new PanelBackgroundImage(dirBackgroundLateral);
		handB.setLayout(new BoxLayout(handB,BoxLayout.Y_AXIS));
		scrollHandB = new JScrollPane(handB);
		iniHandZone( scrollHandB);
		
		//initial gameTurn to gameAct
		gameTurn = gameAct;
		
		
		

		combatButton.setEnabled(false);
		setTitle("Brujos & Trifulcas");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setUndecorated(true);
		
		setPreferredSize(dimensionScreen);
		setMinimumSize(dimensionScreen);
		
		
		//full screen configuration
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		
		pack();
		getContentPane().add(main);
		mainPanel = new JDesktopPane();
		mainPanel.add(getContentPane());
		
		
		setContentPane(mainPanel);	
		
		
		setVisible(true);
		
		
	}
	/**
	 * active full screen
	 */
	public void fullScreen(){
		try
		{
			gd.setFullScreenWindow(this);	
		}
		catch(Throwable e){
			System.exit(-1);
		}
	}
	/**
	 * initialized game zone
	 */
	private void iniGameZone(){
		constraints =  new GridBagConstraints();
		gameZone  = new JPanel(new GridBagLayout());
		gameZone.setLayout(new GridBagLayout());
			
		//zone defense
		zoneDef = new JTabbedPane();
		//gameB = new JPanel(new FlowLayout());
		gameB = new PanelBackgroundImage(dirBackgroundGameDef);
		gameB.setLayout(new FlowLayout());
		
		scrollGameB = new JScrollPane(gameB);
		setAllSize(scrollGameB, dGameZone,dGameZone);
		gameB.setBackground(Color.GRAY);
		
		gameDef = gameB;
		scrollGameDef = scrollGameB;
			
		
		//attack zone
		gameA = new PanelBackgroundImage(dirBackgroundGameAt);	
		scrollGameA = new JScrollPane(gameA);
		
		setAllSize(scrollGameA, dGameZone,dGameZone);
		
		gameAct = gameA;
		scrollGameAct = scrollGameA;

		manaA = new PanelBackgroundImage(dirBackgroundGameMana);
		scrollManaA = new JScrollPane(manaA);
		
		setAllSize(scrollManaA, dGameZone,dGameZone);
		
		manaAct = manaA;
		scrollManaAct = scrollManaA;
		
		
		manaB = new PanelBackgroundImage(dirBackgroundGameMana);
		scrollManaB = new JScrollPane(manaB);
		
		setAllSize(scrollManaB,dGameZone, dGameZone);
		manaB.setBackground(Color.gray);
		manaDef = manaB;
		scrollManaDef = scrollManaB;
		setAllSize(scrollManaB, dGameZone, dGameZone);
		
		zoneDef.add("Seres",scrollGameDef);
		zoneDef.add("Entornos",scrollManaDef);
		
		setGameZone();
		
	}
	/**
	 * configuration constraints to gameZone and add
	 */
	private void setGameZone(){
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 1; 
		constraints.weightx = 1.0; 
		constraints.weighty = 1.0; 
		constraints.anchor = GridBagConstraints.WEST;
		
		gameZone.add(zoneDef,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.anchor = GridBagConstraints.WEST;
		gameZone.add(scrollGameAct,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 2;
		gameZone.add(scrollManaAct,constraints);
		
		gameZone.setOpaque(true);
	}
	/**
	 * delete all game zone
	 */
	private void deleteGameZone(){
		gameZone.remove(zoneDef);
		gameZone.remove(scrollGameAct);
		gameZone.remove(scrollManaAct);
	}
	/**
	 * initialize the panel hand
	 */
	private void iniPanelHand(){				
		panelHand = new JPanel();
		panelHand.setLayout(new BoxLayout(panelHand, BoxLayout.Y_AXIS));
		
		setAllSize(panelHand, dLateralZone, dPreLateralZone);
			
		cancelHanZoneButton = new JButton();
		Icon iconCancel = new ImageIcon(dirCancelIcon);
		cancelHanZoneButton.setIcon(iconCancel);
			
		cancelHanZoneButton.addActionListener(new CancelHandEvent());
				
		cancelHanZoneButton.setMaximumSize(new Dimension(50, 20));
		cancelHanZoneButton.setBorderPainted(false);
		cancelHanZoneButton.setBackground(new Color(238,238,238));

		
		Box box = Box.createHorizontalBox();
		box.add(new JLabel("Mano"));
		box.add(Box.createRigidArea(new Dimension(45, 0)));

		box.add(cancelHanZoneButton,BorderLayout.EAST);
		panelHand.add(box);
		panelHand.add(scrollHandAct);
	}
	/**
	 * initialized hand zone
	 * @param scrollHand the scroll
	 */
	private void iniHandZone(JScrollPane scrollHand){		
		setAllSize(scrollHand, dIntoLateralZone, dPreIntoLateralZone);					
	}
	/**
	 * add hand zone to game zone
	 */
	private void addHandZone(){											
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 3; 
		constraints.weightx = 0.0; 
		constraints.weighty = 1.0; 

		gameZone.add(panelHand,constraints);
				
		gameZone.revalidate(); 
	
	}
	/**
	 * initialize intervention zone
	 * @param intervention the intervention zone
	 * @param scrollIntervention the scroll 
	 */
	private void iniInterventionZone(JPanel intervention,JScrollPane scrollIntervention){
		
		interventionPanel = new JPanel();
		interventionPanel.setLayout(new BoxLayout(interventionPanel, BoxLayout.Y_AXIS));
		setAllSize(interventionPanel,dLateralZone,dPreLateralZone);
		setAllSize(scrollIntervention, dIntoLateralZone,dPreIntoLateralZone);
		
		JPanel aux = new JPanel();
		aux.setMaximumSize(new Dimension(175, 23));
		aux.setPreferredSize(new Dimension(175, 23));
		aux.add(new JLabel("Intervenciones"));
		interventionPanel.add(aux);
		scrollIntervention.setBackground(Color.ORANGE);		
	}
	/**
	 * add intervention zone to game zone
	 */
	private void addInterventionZone(){
		//interventionZone = new JPanel();
		interventionZone =  new PanelBackgroundImage(dirBackgroundLateral);
		interventionZone.setLayout(new BoxLayout(interventionZone,BoxLayout.Y_AXIS));
		
		scrollInterventionZone = new JScrollPane(interventionZone);
		iniInterventionZone(interventionZone, scrollInterventionZone);
		
		interventionPanel.add(scrollInterventionZone);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		constraints.gridwidth = 1;
		constraints.gridheight = 3; 
		constraints.weightx = 0.0; 
		constraints.weighty = 1.0; 
		gameZone.add(interventionPanel,constraints);
				
		
		gameZone.revalidate();
		
	}
	/**
	 * set all size 
	 * @param zone the zone to set size
	 * @param d the dimension
	 * @param preD the preffer dimension
	 */
	private void setAllSize(Component zone,Dimension d,Dimension preD){
		zone.setMinimumSize(d);
		zone.setMaximumSize(d);
		zone.setPreferredSize(preD);
	}
	/**
	 * @param load the load to set
	 */
	public void setLoad(LoadView load) {
		this.load = load;
	}
	/**
	 * create info zone
	 * @return the info zone
	 */
	private JPanel infoZone(){
		JPanel zone = new JPanel();
		
		zone.setLayout(new GridBagLayout());
	
		zone.add(keyPad());
		zone.add(phaseZone());
		
		zone.setBackground(Color.yellow);
		
		zone.setPreferredSize(dInfoZone);
		zone.setMinimumSize(dInfoZone);
		return zone;
	}
	/**
	 * create the keypad zone
	 * @return the zone
	 */
	private JPanel keyPad(){
		JPanel zone = new JPanel(new GridLayout(3,2,2,5));

		zone.setLayout(new BorderLayout());
		JPanel up = new JPanel(new GridLayout(1,2));
		
		Icon icon = null;
		up.add(handButton);
		handButton.setBackground(Color.LIGHT_GRAY);
		//zone.add(nextButton);
		up.add(combatButton);
		combatButton.setBackground(Color.LIGHT_GRAY);
		
		zone.add(up,BorderLayout.CENTER);
		
		JPanel down = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		icon = new ImageIcon(dirExitIcon);	
		exitButton.setIcon(icon);
		exitButton.setBorderPainted(false);
		
		down.add(exitButton);
		icon = new ImageIcon(dirHelpIcon);
		helpButton.setIcon(icon);
		helpButton.setBorderPainted(false);

		down.add(helpButton);
		if(!vs){
			icon = new ImageIcon(dirSaveIcon);	
			saveButton.setIcon(icon);
			saveButton.setBorderPainted(false);
			
			
			down.add(saveButton);
			
			icon = new ImageIcon(dirLoadIcon);	
			loadButton.setIcon(icon);
			loadButton.setBorderPainted(false);
			
			down.add(loadButton);
		}
		else{
			icon = new ImageIcon(dirChatIcon);
			
			chatButton.setIcon(icon);
			chatButton.setBorderPainted(false);
			down.add(chatButton);
		}
		exitButton.setBackground(new Color(238,238,238));
		helpButton.setBackground(new Color(238,238,238));
		saveButton.setBackground(new Color(238,238,238));
		loadButton.setBackground(new Color(238,238,238));
		chatButton.setBackground(new Color(238,238,238));
		
		zone.add(down,BorderLayout.SOUTH);
		zone.setBackground(Color.DARK_GRAY);
		
		setAllSize(zone, dKeyPadSize, dKeyPadSize);
		return zone;
		
	}
	/**
	 * create the phase zone
	 * @return the zone
	 */
	private JPanel phaseZone(){
		JPanel zone = new JPanel(new GridLayout(2, 1));
		zone.add(phase);
		zone.add(nextButton);
		nextButton.setBackground(Color.LIGHT_GRAY);
		
		setAllSize(zone, dPhaseZoneSize, dPhaseZoneSize);
		return zone;
	}
	/**
	 * create the warlock zone
	 * @return the zone
	 */
	private JPanel warlocksZone(){
		JPanel zone = new JPanel(new GridLayout(1,2,0,0));
		zone.add(waInfo);
		zone.add(wbInfo);
		
		setAllSize(zone,dWarlocksZoneSize,dWarlocksZoneSize);
		return zone;
	}
	/**
	 * rotate all components in game zone
	 * @param allTurn if change all turn
	 */
	private void rotate(boolean allTurn,boolean changeWarlock){
		deleteGameZone();
		zoneDef.removeAll();
		setActiveActionPanel(gameAct,false);
		setActiveActionPanel(manaAct,false);
		
		setActiveActionPanel(gameDef,true);
		setActiveActionPanel(manaDef,true);
		setActiveActionPanel(handAct,true);
		
		panelHand.remove(scrollHandAct);
		
		if(gameAct == gameA){				
			gameAct = gameB;
			scrollGameAct = scrollGameB;
			handAct = handB;
			scrollHandAct = scrollHandB;
			manaAct = manaB;
			scrollManaAct = scrollManaB;
			
			gameDef = gameA;
			scrollGameDef = scrollGameA;

			manaDef = manaA;
			scrollManaDef = scrollManaA;
			if(changeWarlock){
				((WarlockPanel)waInfo).avatar.setWrong(true);
				((WarlockPanel)wbInfo).avatar.setWrong(false);
			}
			
		}
		else{						
			
			gameAct = gameA;
			scrollGameAct = scrollGameA;
			handAct = handA;
			scrollHandAct = scrollHandA;
			manaAct = manaA;
			scrollManaAct = scrollManaA;
			
			gameDef = gameB;
			scrollGameDef = scrollGameB;

			manaDef = manaB;
			scrollManaDef = scrollManaB;
			if(changeWarlock){
				((WarlockPanel)wbInfo).avatar.setWrong(true);
				((WarlockPanel)waInfo).avatar.setWrong(false);
			}
			
		}
		if(allTurn){
			gameTurn = gameAct;
			((PanelBackgroundImage) gameAct).dir = dirBackgroundGameAt;
			((PanelBackgroundImage) gameDef).dir = dirBackgroundGameDef;
		}

		setActiveActionPanel(handAct,true);
		setAllDefaultCard(gameAct);
		setAllDefaultCard(gameDef);
		setAllDefaultCard(manaAct);
		setAllDefaultCard(manaDef);
		zoneDef.add("Seres",scrollGameDef);
		zoneDef.add("Entornos",scrollManaDef);
		panelHand.add(scrollHandAct);
		setGameZone();
		gameZone.revalidate();
		
	}
	/**
	 * 
	 * set all CardPanel into zone to default card
	 * @param zone the zone to set
	 */
	private void setAllDefaultCard(JPanel zone){
		Component[] aux = zone.getComponents();
		for(Component c : aux){
			((CardPanel) c).defaultCard();
		}
	}
	/**
	 * set all CardPanel into zone to canUse and disable check
	 * @param zone the zone
	 * @param active if can use
	 */
	private void setActiveActionPanel(JPanel zone,boolean active){
		Component[] aux = zone.getComponents();
		for(Component p : aux){
			((CardPanel) p).setCanUse(active);
			((CardPanel) p).image.setCheck(false);
		}
		
	}
	/**
	 * active CardPanel in gameAct
	 */
	public void activeActActionPanel(){
		
		setActiveActionPanel(gameAct,true);
		setActiveActionPanel(manaAct,true);
		
	}
	public void finishCombatFrame(){
		mainPanel.removeAll();
		mainPanel.add(main);
		combatFrame = null;
	}
	/**
	 * set the combat image of all CarDPanel in combatInfo  
	 * @param active if active
	 */
	private void setCombatIcon(boolean active){
		if(combatInfo != null){
			for(LinkedList<CardPanel> lin : combatInfo.values()){
				for(JPanel p : lin){
					((CardPanel) p).image.setCombat(active);
				}
			}
		}
	}
	/**
	 * search the CardPanel which have Card id equals id
	 * @param zone the zone to search
	 * @param id the id of card
	 * @return the CardPanel
	 */
 	private JPanel found(JPanel zone,int id){
		Component[] aux = zone.getComponents();
		int pos = 0;
		int max = aux.length;
		boolean found = false;
		JPanel value = null;
		while(!found && pos < max){
			if(id == ((CardPanel) aux[pos]).getId()){
				found = true;
				value = (JPanel) aux[pos];
			}
			pos++;
		}
		return value;
	}
	/**
	 * add card in zone
	 * @param zone the zone
	 * @param scrollzone the scrollzone
	 * @param card the cardPanel
	 */
	private void addCard(JPanel zone,JScrollPane scrollzone,JPanel card){
		zone.add(card);
	}
	/**
	 * add card into zone in fist position
	 * @param zone zone the zone
	 * @param scrollzone  the scrollzone
	 * @param card the cardPanel
	 */
	private void addCardIn(JPanel zone,JScrollPane scrollzone,JPanel card){
		zone.add(card);
		
		zone.setComponentZOrder(card, 0);

	}
	/**
	 * remove card into zonr
	 * @param zone zone the zone
	 * @param scrollzone the scrollzone
	 * @param card the cardPanel
	 */
	private void removeCard(JPanel zone,JScrollPane scrollzone,JPanel card){
		zone.remove(card);
		zone.repaint();
		scrollzone.revalidate();
	}
	/**
	 * set default mouseEvent of CardPanel into zone
	 * @param zone the zone
	 */
	private void setDeafultMouseEvent(JPanel zone){
		Component[] aux = zone.getComponents();
		for(Component c : aux){
			((CardPanel) c).setDefaultMouseEventAction();
		}
	}
	/**
	 * show combatFrame
	 * @param automatic
	 */
	private void showCombatFrame(boolean automatic){
		setActiveActionPanel(gameAct,false);
		setActiveActionPanel(manaAct,false);
		combatFrame = new CombatView(combatInfo,automatic,controller,this,vs);
				
		mainPanel.add(combatFrame);
		
		

		try {
			combatFrame.setSelected(true);
		} catch (Exception e) {
		}
	}
	/**
	 * show discard frame
	 * @param count the count of discard
	 * @param isValue if have value
	 */
	private void showDiscardFrame(int count,boolean isValue){
		discardFrame = new InfoInternalFrame("<html>Debes descartarte de:",isValue, String.valueOf(count));
		
		discardFrame.setSize(200, 100);
		discardFrame.setLocation(0, 0);
		mainPanel.add(discardFrame);
		discardFrame.setTitle("Tienes que descartarte");
		
	}
	/**
	 * show info frame
	 * @param text the info
	 * @param isValue if have value
	 * @param count the value
	 */
	private void showInfoTime(String text,boolean isValue,int count){
		infoFrame = new InfoInternalFrame(text, isValue,String.valueOf(count));
		setContentPane(mainPanel);
		
		infoFrame.setSize(600, 100);
		infoFrame.setTitle("Aviso");
		mainPanel.add(infoFrame);
		
	}
	/**
	 * show rotate info
	 * @param text the text
	 * @param isValue if have value
	 * @param count the value
	 */
	private void showRotateInfo(String text,boolean isValue,int count){
		rotateFrame = new InfoInternalFrame(text, isValue,String.valueOf(count));
		//setContentPane(mainPanel);
		
		rotateFrame.setSize(600, 100);
		rotateFrame.setTitle("Aviso");
		mainPanel.add(rotateFrame);	
	}
	/**
	 * show laod frame
	 * @param aux the frame
	 */
	private void showLoadFrame(JInternalFrame aux){
		aux.add(new ImagePanel(600, 600, "imagenes//Imagenes fondo//Fondo_cargando.png", false,false, false));
	
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		aux.setLocation(d.width/3, d.height/5);
		
		aux.setBorder(null);
		//setContentPane(mainPanel);
		aux.setVisible(true);
		aux.setSize(600,600);
		aux.setTitle("Cargando...");
		aux.pack();
		mainPanel.add(aux);	
		
		
	}
	/**
	 * show exchange frame
	 * @param zoneA zone A
	 * @param zoneB zone B
	 */
	private void showExchangeFrame(JPanel zoneA,JPanel zoneB){
		exchangeFrame = new ExchangeView(zoneA,zoneB,controller,selectCard.getIdCard());
		
		mainPanel.add(exchangeFrame);
		try {
			exchangeFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
		
		exchangeFrame.setTitle("Seleccione entornos a intercambiar");
		exchangeFrame.setClosable(false);
		
	}
	/**
	 * show exchange frame
	 * @param zoneA zone A
	 * @param zoneB zone B
	 */
	private void showListFrame(JPanel zone){
		if(listFrame == null){
			//listFrame = new ListFrame(zone, t);
			 listFrame = new ListFrame(zone, t,this);
		}
	
		//JInternalFrame a = new JInternalFrame();
		//a.add(listFrame);
		//mainPanel.add(listFrame);
		//listFrame.add(new ListFrame(zone, t));
		//a.setVisible(true);
		//listFrame.setSize(800, 500);
		mainPanel.add(listFrame);
		try {
			listFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
		

		
		
		listFrame.setTitle("Mano");
		//listFrame.setClosable(false);
		
	}
	private void showChat(){
		if(chat == null){
			//listFrame = new ListFrame(zone, t);
			chat = new ChatFrame(controller);
		}
		else{
			chat.setVisible(true);
		}
		
		mainPanel.add(chat);
		
		try {
			chat.setSelected(true);
		} catch (PropertyVetoException e) {
		}

	}
	/**
	 * finish exchange frame
	 */
	public void finishExchange(){
		//exchangeFrame.dispose();
		exchangeFrame.setVisible(false);
		mainPanel.removeAll();
		mainPanel.add(main);
		
		//mainPanel.remove(exchangeFrame);
		exchangeFrame = null;
		selectCard = null;
		
		
		setActiveActionPanel(handAct, true);
		setActiveActionPanel(gameAct, true);
		setActiveActionPanel(manaAct, true);
		nextButton.setEnabled(true);
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
	}
	/**
	 * finish exchange frame
	 */
	public void finishListFrame(){
		//exchangeFrame.dispose();
		listFrame.setVisible(false);
		//mainPanel.removeAll();
		//mainPanel.add(main);
		
		//mainPanel.remove(exchangeFrame);
		listFrame = null;
		if(interventionFrame != null && !interventionFrame.isShowing()){
			interventionFrame.setVisible(true);
		}
		//selectCard = null;
	}
	/**
	 * show intervention frame
	 */
	public void showInterventionsFrame(){
		interventionFrame = new InterventionsSolveView(interventionZone);	
		
		mainPanel.add(interventionFrame);
		try {
			interventionFrame.setSelected(true);
		} catch (PropertyVetoException e) {
		}
		
		
	}
	/**
	 * remove CardPanel in link
	 * @param lin the linkedList
	 * @param id the id of card
	 */
	private void removeLink (LinkedList<CardPanel> lin,final int id){
		int pos = 0;
		Iterator<CardPanel> it = lin.iterator();
		boolean done = false;
		while(!done && it.hasNext()){
			if(it.next().getId() == id){
				done = true;
				lin.remove(pos);
			}
			pos++;
		}
	}
	/**
	 * initialized browser file
	 * @param text the textfield where put the ath of file
	 * @param filter if its need file filter
	 */
	private void inifile(boolean filter,String name){
		final JFileChooser chooser = new JFileChooser();
		fileFrame = new JInternalFrame();
		
		if(filter){
			FileFilter fil = new FileNameExtensionFilter("Archivo de partida  (.estado)", "estado");
			chooser.setFileFilter(fil);
		}
			
		File f = null;
		try {
			f = new File(new File(".").getCanonicalPath());
		} catch (IOException e) {
		}
		chooser.setCurrentDirectory(f);
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		chooser.setApproveButtonText(name);

		chooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				 if (arg0.getActionCommand().equals("CancelSelection")){
					 fileFrame.setVisible(false);
					 fileFrame.dispose();
				 }
				 if (arg0.getActionCommand().equals("ApproveSelection")){
					 final String dir = chooser.getSelectedFile().getAbsolutePath();
					 
					 fileFrame.setVisible(false);
					 fileFrame.dispose();
					 if(chooser.getApproveButtonText().equals("Guardar")){
						 controller.saveGame(dir);
					 }
					 else{
						 
						 final JInternalFrame aux = new JInternalFrame();
						 loadTimer  = new Timer(1000, new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent arg0) {
								controller.loadGame(dir);
								 
								 main.setVisible(true);
								 mainPanel.remove(aux);
								 
								 loadTimer.stop();
								 loadTimer = null;
							}
						});	
						main.setVisible(false);
						showLoadFrame(aux);
						loadTimer.start();
						 
					 }
					 
				 }			
			}
		});
		
		fileFrame.add(chooser);
		fileFrame.pack();
		fileFrame.setVisible(true);
	
		mainPanel.add(fileFrame);
				
		/*int option = chooser.showOpenDialog(mainPanel);
		chooser.
		if (option == JFileChooser.APPROVE_OPTION) {	
			return chooser.getSelectedFile().getAbsolutePath();
		}*/

	}
	/**
	 * remove all info into panels cards
	 */
	private void clearAll(){
		gameA.removeAll();
		gameB.removeAll();
		handA.removeAll();
		handB.removeAll();
		combatInfo = null;
		manaA.removeAll();
		manaB.removeAll();

		infoZone.remove(2);
		
		deleteGameZone();
		panelHand.remove(scrollHandAct);
		
		gameAct = gameA;
		scrollGameAct = scrollGameA;
		handAct = handA;
		scrollHandAct = scrollHandA;
		manaAct = manaA;
		scrollManaAct = scrollManaA;
		
		gameDef = gameB;
		scrollGameDef = scrollGameB;

		manaDef = manaB;
		scrollManaDef = scrollManaB;
		
		zoneDef.removeAll();
		
		zoneDef.add("Seres",scrollGameDef);
		zoneDef.add("Entornos",scrollManaDef);
		panelHand.add(scrollHandAct);
		setGameZone();
		gameZone.revalidate();
		//infoZone.remove(wbInfo);
		
		if(interventionPanel != null){
			gameZone.remove(interventionPanel);
			interventionZone = null;	
			
		}
		if(combatFrame != null){
			combatFrame.setVisible(false);
			combatFrame = null;
		}
	}
	/**
	 * 
	 * @author ocrespo
	 *
	 */
	public class CardPanel extends JPanel implements Cloneable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private  final int id;
		private final Card idCard;
		private JButton use = new JButton("Usar");
		private JLabel info;
		private Component separateAction;
		private JButton attack = new JButton("Atacar");
		private JButton defense = new JButton("Defender");
		private JPanel actionPanel;
		private JPanel manaPanel;
		private JPanel infoPanel;
		private JLabel atStat;
		private JLabel defStat;
		private JLabel lifeStat;
		private JLabel actionText;
		private ImagePanel buff;
		
		private JLabel manaCount;

		private JLabel name;
		private JButton earthButton;
		private JButton spiritButton;
		private JButton seaButton;
		private JButton airButton;
		private ImagePanel image;
		private ImagePanel turnIcon;
		
		private MouseListener mouseEvent = null;
		private boolean canUse = true;
		/**
		 * builder
		 * @param code the code
		 * @param at the attack
		 * @param def the defense
		 * @param life the life
		 * @param dirImg the directory of image
		 * @param e the element
		 * @param id the id
		 */
		private CardPanel(String code,String at,String def,String life,String dirImg,Element e,String buff,boolean turn,String description,boolean haveActiveAction,final Card id){
			this.id = id.getId();
			idCard = id;
			setLayout(new CardLayout());
			Dimension cardSize = new Dimension(151, 160);
			setAllSize(this, cardSize, cardSize);

			String des = id.getDescription();
			if(!des.equals("")){
				actionText = new JLabel("Comportamiento");
				actionText.setToolTipText(id.getDescription());
			}
			
			if(id.getLife() != -1){
				atStat = new JLabel(at);
				defStat = new JLabel(def);
				lifeStat = new JLabel(life);							
				infoCardPanale(code,dirImg,true,e);
			}
			else{
				infoCardPanale(code,dirImg,false,e);
			}
			
			add("info",infoPanel);
			iniCardActionPanel();
			add("action",actionPanel);
			iniCardDealMananPanel();
			add("pay",manaPanel);
			
			iniConfigListener();
			
			updateStats(at, def, life, buff,description, turn,haveActiveAction,true);
		}
		/**
		 * @param info the info to set
		 */
		private void setInfo(String info,Color color) {
			
			this.info.setText(info);
			this.info.setForeground(color);
		}
		/**
		 * 
		 * @return the id
		 */
		public final int getId(){
			return id;
		}
		/**
		 * 
		 * @return the id of the card
		 */
		public final Card getIdCard(){
			return idCard;
		}
		/**
		 * 
		 * @return the name
		 */
		public String getNameCard(){
			return name.getText();
		}
		/**
		 * set canUse
		 * @param canUse the canUse
		 */
		public void setCanUse(boolean canUse) {
			this.canUse = canUse;
		}
		/**
		 * refresh CardPanel stats´s
		 * @param at the attack
		 * @param def the defense
		 * @param life the life
		 * @param buff the buff
		 * @param turn is turn
		 */
		private void updateStats(String at,String def,String life,String buff,String description,boolean turn,boolean haveActiveAction,boolean isNew){
			if(atStat != null){
				atStat.setText(at);
				defStat.setText(def);
				lifeStat.setText(life);
				if(actionText != null){
					actionText.setToolTipText(description);
				}
				if(!buff.equals("")){
					this.buff.setVisible(true);
					this.buff.setEnabled(true);
					this.buff.setToolTipText(buff);
				}
				else{
					this.buff.setVisible(false);
					this.buff.setEnabled(false);
					this.buff.setToolTipText("");
				}
				if(turn){
					turnIcon.setVisible(true);
				}
				else{
					turnIcon.setVisible(false);
				}
				if(!isNew && found(handA, id) == null && found(handB, id) == null){
					if(!haveActiveAction){
						use.setVisible(false);
						use.setFocusable(false);
					}
					else{
						use.setVisible(true);
						use.setFocusable(true);
						setUseActionInGame();
					}
				}
				else{
					use.setVisible(true);
					use.setFocusable(true);
				}
			}
			else{
				actionText.setToolTipText(description);
				if(!buff.equals("")){
					this.buff.setVisible(true);
					this.buff.setEnabled(true);
					this.buff.setToolTipText(buff);
					this.image.setToolTipText(buff);
					this.image.addMouseListener(mouseEvent);
				}
				else{
					this.buff.setVisible(false);
					this.buff.setEnabled(false);
					this.buff.setToolTipText("");
				}
				if(turn){
					turnIcon.setVisible(true);
				}
				else{
					turnIcon.setVisible(false);
				}
			}
			
		}
		/**
		 * decrease the deal pay count
		 */
		private void decreasetPayCount(){
			int count = Integer.parseInt(manaCount.getText());
			count--;
			manaCount.setText(String.valueOf(count));
		}
		/**
		 * initialized listeners
		 */
		private void iniConfigListener(){
			mouseEvent = new MouseAction(this);
			infoPanel.addMouseListener(mouseEvent);
			use.addActionListener(new PlayCardEvent(this));
			attack.addActionListener(new AttackCardEvent(this));
			defense.addActionListener(new DefenseCardEvent(this));
			
			earthButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.payDealMana(Element.earth, idCard);
					
				}
			});
			spiritButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.payDealMana(Element.spirit, idCard);
					
				}
			});
			seaButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.payDealMana(Element.sea, idCard);
					
				}
			});
			airButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					controller.payDealMana(Element.air, idCard);
					
				}
			});
		}
		/**
		 * guild the info card panel (default)
		 * @param code the code
		 * @param dir the image directory
		 * @param haveStats if have stats
		 * @param e the element
		 */
		private void infoCardPanale(String code,String dir,boolean haveStats,Element e){
			
			infoPanel =  new JPanel();
			infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
			
			//up panel
			JPanel up = new JPanel(new BorderLayout());
			JPanel nameZone = new JPanel(new FlowLayout(FlowLayout.RIGHT,1,1));
			
			name = new JLabel(code);
			nameZone.add(name);
			turnIcon = new ImagePanel(16, 16, dirTurnIcon, false, false, false);
			turnIcon.setVisible(false);
			nameZone.add(turnIcon);

			up.add(nameZone,BorderLayout.WEST);

			if(haveStats){
				JPanel life = new JPanel();
				life.setLayout(new BoxLayout(life, BoxLayout.X_AXIS));
				life.add(lifeStat);
				life.add(new ImagePanel(16,16,dirLifeIcon,false,false,false));

				up.add(life,BorderLayout.EAST);
			}
			up.setMaximumSize(new Dimension(147, 18));
			
			Box upBox = Box.createHorizontalBox();
			upBox.add(Box.createRigidArea(new Dimension(2, 18)));
			upBox.add(up);
			upBox.add(Box.createRigidArea(new Dimension(2, 18)));
			infoPanel.add(Box.createRigidArea(new Dimension(147, 2)));
			infoPanel.add(upBox);
			
			//imagen
			infoPanel.add(Box.createRigidArea(new Dimension(147,2)));
			image = new ImagePanel(147,81,dir,true,true,false);
			infoPanel.add(image);
			
			//middle
			JPanel middle = new JPanel(new BorderLayout());
			buff = new  ImagePanel(16,16,dirBuffIcon,false,false,false);
			middle.add(buff,BorderLayout.EAST);
			buff.setVisible(false);
			buff.setEnabled(true);
			
			if(!haveStats && idCard.getCost() == null){
				middle.add(new ImagePanel(16, 16, dirEnviromentcon, false, false,false),BorderLayout.WEST);
			}
			else if(!haveStats){
				middle.add(new ImagePanel(16, 16, dirInterventionIcon, false, false,false),BorderLayout.WEST);

			}
			else{
				middle.add(new ImagePanel(16, 16, dirEntityIcon, false, false,false),BorderLayout.WEST);

			}
			
			JPanel cost = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
			buildCost(cost);
			cost.setAlignmentX(RIGHT_ALIGNMENT);
			middle.add(cost,BorderLayout.CENTER);

			setAllSize(middle,dCardInfoMiddleSize,dCardInfoMiddleSize);

			
			Box middleBox = Box.createHorizontalBox();
			middleBox.add(Box.createRigidArea(new Dimension(2, 17)));
			middleBox.add(middle);
			middleBox.add(Box.createRigidArea(new Dimension(2, 17)));
			
			infoPanel.add(Box.createRigidArea(new Dimension(147,2)));
			infoPanel.add(middleBox);

			//DOWN ZONE
			Box downBox = Box.createHorizontalBox();
			JPanel down = new JPanel(new BorderLayout());
			
			JPanel action = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
			if(actionText != null){
				
				action.add(actionText);
				
			}
			else{
				action.add(Box.createRigidArea(new Dimension(15, 15)));
			}
			down.add(action,BorderLayout.NORTH);
			if(haveStats){
				JPanel at = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
				at.add(atStat);
				at.add(new ImagePanel(16,16,dirAtIcon,false,false,false));
				
				JPanel def = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 0));
				def.add(defStat);
				def.add(new ImagePanel(16,16,dirDefIcon,false,false,false));
				
				down.add(at,BorderLayout.WEST);
				down.add(def,BorderLayout.EAST);
			}
			setAllSize(down, dCardInfoDownSize, dCardInfoDownSize);
			downBox.add(Box.createRigidArea(new Dimension(2, 20)));
			downBox.add(down);
			downBox.add(Box.createRigidArea(new Dimension(2, 20)));
			
			infoPanel.add(Box.createRigidArea(new Dimension(147, 2)));
			infoPanel.add(downBox);
			infoPanel.add(Box.createRigidArea(new Dimension(147, 2)));
			
			buildElement(e);
			
			setAllSize(infoPanel, dCardInfoSize,dCardInfoSize);		
		
		}
		/**
		 * build the action panel
		 */
		private void iniCardActionPanel(){
			
			
			actionPanel =  new ImagePanel(151,160,dirBackgroundActionIcon,false,false,false);
			actionPanel.setLayout(new BoxLayout(actionPanel,BoxLayout.PAGE_AXIS));

			//up
			Icon iconCancel = new ImageIcon(dirCancelIcon);
			JButton cancel = new JButton();
			cancel.setIcon(iconCancel);
			cancel.setContentAreaFilled(false);
			cancel.setOpaque(false);

			
			cancel.addActionListener(new CancelActionCardEvent(this));
			
			cancel.setMaximumSize(new Dimension(50, 20));
			cancel.setBorderPainted(false);
			cancel.setBackground(Color.white);

			Box box = Box.createHorizontalBox();
			box.add(Box.createRigidArea(new Dimension(100, 0)));

			box.add(cancel,BorderLayout.EAST);
			actionPanel.add(box);
			
			//the info
			info = new JLabel();
			info.setText("");
			
			info.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			actionPanel.add(info,BorderLayout.CENTER);
			separateAction = Box.createRigidArea(new Dimension(0, 10));
			actionPanel.add(separateAction);		
			
			
			
			attack.setMaximumSize(new Dimension(90, 35));
			attack.setAlignmentX(Component.CENTER_ALIGNMENT);
	
			actionPanel.add(attack,BorderLayout.CENTER);							
			actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			attack.setVisible(false);
			attack.setFocusable(false);
			attack.setBackground(Color.LIGHT_GRAY);
			
			defense.setMaximumSize(new Dimension(90, 35));
			defense.setAlignmentX(Component.CENTER_ALIGNMENT);
			defense.setBackground(Color.LIGHT_GRAY);

			actionPanel.add(defense,BorderLayout.CENTER);							
			actionPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			defense.setVisible(false);
			defense.setFocusable(false);
		
			use.setAlignmentX(Component.CENTER_ALIGNMENT);
			use.setMaximumSize(new Dimension(80, 35));
			use.setBackground(Color.LIGHT_GRAY);
			use.setVisible(true);
			actionPanel.add(use,BorderLayout.CENTER);
			
			setAllSize(actionPanel, dCardActionSize,dCardActionSize);
		}
		/**
		 * build deal mana panel
		 */
		private void iniCardDealMananPanel(){
			manaPanel =  new JPanel();
			
			setAllSize(actionPanel, dCardManaSize,dCardManaSize);
			
			JPanel text = new JPanel();
			text.setLayout(new BoxLayout(text,BoxLayout.Y_AXIS));
			text.add(new JLabel("<html>Pague los cloros<br>genericos:</html>"));
			
			JPanel elements = new JPanel(new BorderLayout());
			JPanel buttons = new JPanel(new GridLayout(2,2));
			
			earthButton = new JButton();
			seaButton = new JButton();
			airButton = new JButton();
			spiritButton = new JButton();
			
			Icon icon = new ImageIcon(dirEarthIconLittle);
			earthButton.setIcon(icon);
			earthButton.setBorderPainted(false);
			earthButton.setBackground(Color.WHITE);
			
			
			icon = new ImageIcon(dirSeaIconLittle);
			seaButton.setIcon(icon);
			seaButton.setBorderPainted(false);
			seaButton.setBackground(Color.WHITE);
			
			
			icon = new ImageIcon(dirAirIconLittle);
			airButton.setIcon(icon);
			airButton.setBorderPainted(false);
			airButton.setBackground(Color.WHITE);
			
			
			icon = new ImageIcon(dirSpiritIconLittle);
			spiritButton.setIcon(icon);
			spiritButton.setBorderPainted(false);
			spiritButton.setBackground(Color.WHITE);
			
			
			
			buttons.add(earthButton);
			buttons.add(seaButton);
			buttons.add(airButton);
			buttons.add(spiritButton);
			
			manaCount = new JLabel();	
			manaPanel.add(text);
			JPanel info = new JPanel(new BorderLayout());
			info.add(new JLabel("Faltan: "),BorderLayout.WEST);
			info.add(manaCount,BorderLayout.CENTER);
			
			
			elements.add(info,BorderLayout.NORTH);
			elements.add(buttons,BorderLayout.CENTER);
			manaPanel.add(elements);
		}
		/**
		 * build cost of the CardPanel
		 * @param cost
		 */
		private void buildCost(JPanel cost){
			if(idCard.getCost() != null){
				int earth = idCard.getCost().getEarth();
				int air = idCard.getCost().getAir();
				int sea = idCard.getCost().getSea();
				int spirit = idCard.getCost().getSpirit();
				int generic = idCard.getCost().getGeneric();
				if( earth > 0){
					cost.add(new JLabel(String.valueOf(earth)));
					cost.add(new ImagePanel(16,16,dirEarthIcon ,false,false,false));
				}
				if( air > 0){
					cost.add(new JLabel(String.valueOf(air)));
					cost.add(new ImagePanel(16, 16,dirAirIcon,false,false,false));
				}
				if( sea > 0){
					cost.add(new JLabel(String.valueOf(sea)));
					cost.add(new ImagePanel(16, 16, dirSeaIcon,false,false,false));			
				}
				if( spirit > 0){
					cost.add(new JLabel(String.valueOf(spirit)));
					cost.add(new ImagePanel(16, 16,dirSpiritIcon ,false,false,false));
				}
				if( generic > 0){
					cost.add(new JLabel(String.valueOf(generic)));
					cost.add(new ImagePanel(16, 16, dirGenericIcon,false,false,false));
				}
			}
			
		}
		/**
		 * build the element of CardPanel
		 * @param e
		 */
		private void buildElement(Element e){
			if(e == Element.earth){
				infoPanel.setBackground(new Color(144,62 ,9));
			}
			else if(e == Element.air){
				infoPanel.setBackground(new Color(144,216 ,255));	
			}
			else if(e == Element.sea){
				infoPanel.setBackground(new Color(0,0 ,255));	
			}
			else if(e == Element.spirit){
				infoPanel.setBackground(new Color(144, 102, 255));	
			}
		}
		/**
		 * active the entity actionPanel
		 */
		private void entityCardActionPanel(Card c){
			attack.setVisible(true);
			attack.setFocusable(true);
			
			if(!c.haveActiveAction()){
				use.setVisible(false);
				use.setFocusable(false);
				setSeparate();
			}
			else
				setUseActionInGame();
		}
		/**
		 * active the enviroment actionPanel
		 */
		private void manaCardActionPanel(Card c){
			if(c.haveActiveAction()){
				setUseActionInGame();
			}
			else{
				use.setVisible(false);
				use.setFocusable(false);
			}
		}
		/**
		 * remove mouse event action
		 */
		public void removeMouseEventAction(){
			infoPanel.removeMouseListener(mouseEvent);
		}
		/**
		 * set default mouse event action
		 */
		public void setDefaultMouseEventAction(){
			MouseListener []aux = infoPanel.getMouseListeners();
			for(MouseListener m : aux){
				infoPanel.removeMouseListener(m);
			}
			infoPanel.addMouseListener(mouseEvent);
		}
		/**
		 * show deal mana panel
		 * @param count the count of deal
		 */
		private void showManaPayPanel(int count){
			manaCount.setText(String.valueOf(count));
			CardLayout cl = (CardLayout)(this.getLayout());
		    cl.show(this,"pay");
		  
		}
		/**
		 * active default panel
		 */
		public void defaultCard(){
			CardLayout cl = (CardLayout)(this.getLayout());
		    cl.show(this,"info");
		    info.setText("");
		    separateAction.setMaximumSize(new Dimension(0, 20));
		}
		/**
		 * 
		 * @author ocrespo
		 *
		 */
		public class CancelActionCardEvent implements ActionListener{
			private CardPanel card;
			public CancelActionCardEvent(CardPanel card){
				this.card = card;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				defaultCard();
				if(selectCard == card){
					selectCard = null;
				}
			}
		
		}
		/**
		 * set separte into action panel
		 */
		private void setSeparate(){
			separateAction.setMaximumSize(new Dimension(0, 5));
		}
		/**
		 * set use action listener
		 */
		private void setUseActionInGame(){
			use.removeActionListener(use.getActionListeners()[0]);
			use.addActionListener(new UseGameCardEvent(this));
		}
		@Override
		public Object clone() {
			Object clon = null;
			try {
				clon =  super.clone();
				//clon = new CardPanel("adsa", "2", "3", "2", "a", Element.earth, "", false, "", false, idCard);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			return clon;
		}
		/**
		 * 
		 * @author ocrespo
		 *
		 */
		public class UseGameCardEvent implements ActionListener{
			private CardPanel card;
			public UseGameCardEvent(CardPanel card){
				this.card = card;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				selectCard = card;
				select = Select.use;
				controller.useAction(idCard, null);
				
			}
		}
		/**
		 * 
		 * @author ocrespo
		 *
		 */
		public class PlayCardEvent implements ActionListener{
			private CardPanel card;
			public PlayCardEvent(CardPanel card){
				this.card = card;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				selectCard = card;
				select = Select.play;
				controller.playCard(idCard,null);
				
				
			}
		
		}
		/**
		 * 
		 * @author ocrespo
		 *
		 */
		public class AttackCardEvent implements ActionListener{
			private CardPanel card;
			public AttackCardEvent(CardPanel card){
				this.card = card;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				selectCard = card;
				controller.attack(idCard);
				
				
			}
		
		}
		/**
		 * 
		 * @author ocrespo
		 *
		 */
		public class DefenseCardEvent implements ActionListener{
			private CardPanel card;
			public DefenseCardEvent(CardPanel card){
				this.card = card;
			}
			@Override
			public void actionPerformed(ActionEvent e) {
				selectCard = card;
				select = Select.defense;
				controller.defense(null,idCard);
				
				
			}
		
		}
		/**
		 * 
		 * @author ocrespo
		 *
		 */
		public class MouseAction implements MouseListener{
			private CardPanel card;
			
			
			public MouseAction(CardPanel card){
				this.card = card;
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {		
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {	
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {			
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(selectCard == null && canUse && discardFrame == null){
					Component[] aux = gameTurn.getComponents();
					boolean found = false;
					int pos = 0;
					int max = aux.length;
					while(!found && pos < max ){
						if(aux[pos] == card)
							found = true;
						pos++;
					}
					if(found){
						attack.setVisible(true);
						attack.setFocusable(true);
						defense.setVisible(false);
						defense.setFocusable(false);
					}
					else if(attack.isVisible() || defense.isVisible()){
						attack.setVisible(false);
						attack.setFocusable(false);
						defense.setVisible(true);
						defense.setFocusable(true);
					}
					
					CardLayout cl = (CardLayout)(card.getLayout());
				    cl.show(card,"action");
				}
				else if(canUse && discardFrame != null){
					int num = Integer.parseInt(discardFrame.getValue());
					num--;
					discardFrame.setValue(String.valueOf(num));
					controller.discard(idCard);
				}
				else{
					if(selectCard != null){
						if(select == Select.play){
							targetCard = card;
							controller.playCard(selectCard.idCard, idCard);
						}
						else if(select == Select.use){
							targetCard = card;
							controller.useAction(selectCard.idCard, idCard);
						}
						else if(select == Select.defense){
							targetCard = card;
							controller.defense(idCard, selectCard.idCard);
						}
					}
				}
								
			}
		}
	}
	
	
	/**
	 * 
	 * @author ocrespo
	 *
	 */
	public class WarlockPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final int id ;
		private final Warlock idWar;
		//private final int id ;
		private JLabel name = new JLabel();
		private JLabel numHand = new JLabel();
		private JLabel numGrave = new JLabel();
		private JLabel numDeck = new JLabel();
		private JLabel life = new JLabel();
		private ImagePanel avatar;
		private ImagePanel buffImage = new ImagePanel(16, 16, dirBuffIcon, false, false,false);
		
		/**
		 * builder
		 * @param id the id
		 * @param name the name
		 * @param hand the number hand cards
		 * @param grave the number graveyard card
		 * @param deck the number deck card
		 * @param life the life
		 * @param image the image directory
		 */
		private WarlockPanel(final Warlock id,String name,String hand,String grave,String deck,String life,String image){
			this.id = id.getId();
			this.idWar = id;
			//this.id = w
			refreshStats(name, hand, grave, deck, life,"");
			setLayout(new BorderLayout());
			build(image);
			configListener();
		}
		/**
		 * 
		 * @return the id
		 */
		private final int getId(){
			return id;
		}
		/**
		 * update stats
		 * @param name the name
		 * @param hand the number hand cards
		 * @param grave the number graveyard card
		 * @param deck the number deck card
		 * @param life the life
		 * @param buff the buff
		 */
		private void refreshStats(String name,String hand,String grave,String deck,String life,String buff){
			this.name.setText(name);
			
			numHand.setText(hand);
			numGrave.setText(grave);
			numDeck.setText(deck);
			this.life.setText(life);
			if(buff == null || buff.equals("")){
				buffImage.setVisible(false);
				buffImage.setFocusable(false);
			}
			else{
				buffImage.setVisible(true);
				buffImage.setFocusable(true);
				buffImage.setToolTipText(buff);
			}
			
			//this.revalidate();
			//this.repaint();
			
		}
		/**
		 * configuration listener
		 */
		private void configListener(){
			this.addMouseListener(new MouseActionWarlock());	
		}
		/**
		 * build the zone
		 * @param image
		 */
		private void build(String image){
			JPanel left = new JPanel();
			if(image == null ||image.equals("")){
				image = dirDefaultImage;
			}
			avatar = new ImagePanel(95,105,image,false,false,true);
			left.add(avatar);
			
			name.setFont(new Font( "Arial", Font.BOLD, 15 ) );
			name.setForeground(Color.white);
			
			
			JPanel nameZone = new JPanel(new FlowLayout(FlowLayout.LEFT));
			nameZone.setBackground(new Color(100, 100, 100));
			nameZone.add(name);
			
			buffImage.setVisible(false);
			buffImage.setFocusable(false);
			nameZone.add(buffImage);

			nameZone.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,1, 1,1, Color.white), BorderFactory.createLoweredBevelBorder()));
			
			
			add(nameZone,BorderLayout.NORTH);
			add(left,BorderLayout.WEST);
			left.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			
			JPanel right = new JPanel(new BorderLayout());
			JPanel up = new JPanel(new FlowLayout(FlowLayout.LEFT));
			up.add(life);
			up.add(new ImagePanel(30, 30, dirLifeWarlockIcon,false,false,false));
			up.setBackground(new Color(238, 238, 238));
			//up.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			
			up.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2,2, 2,2, Color.white), BorderFactory.createLoweredBevelBorder()));
			
			right.add(up,BorderLayout.NORTH);
			
			JPanel middle = new JPanel(new GridLayout(1,2));
			JPanel mLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
			mLeft.add(numHand);
			mLeft.setBackground(new Color(238, 238, 238));
			//mLeft.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			mLeft.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2,2, 2, 2, Color.white), BorderFactory.createLoweredBevelBorder()));
			mLeft.add(new ImagePanel(18, 18, dirHandIcon,false,false,false));
			
			JPanel mRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			mRight.add(new ImagePanel(18, 18, dirDeckIcon,false,false,false));
			mRight.add(numDeck);
			//mRight.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			mRight.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2,2, Color.white), BorderFactory.createLoweredBevelBorder()));
			mRight.setBackground(new Color(238, 238, 238));

			middle.add(mLeft);
			middle.add(mRight);
			
			right.add(middle,BorderLayout.CENTER);
			
			JPanel down = new JPanel(new FlowLayout(FlowLayout.CENTER));
			down.add(numGrave);
			down.add(new ImagePanel(18, 18, dirGraveIcon,false,false,false));
			down.setBackground(new Color(238, 238, 238));
			//down.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
			down.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.white), BorderFactory.createLoweredBevelBorder()));
			
			right.add(down,BorderLayout.SOUTH);
			add(right,BorderLayout.CENTER);

		}
		/**
		 * decrease number of cards into hand
		 */
		private void  decreaseHandNum(){
			int aux = Integer.parseInt(numHand.getText());
			aux--;
			numHand.setText(String.valueOf(aux));
		}
		
		public class MouseActionWarlock implements MouseListener{
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(selectCard != null){
					if(select == Select.play){
						controller.playCardWarlock(idWar);
						controller.playCard(selectCard.idCard, null);
					}
				}
				
			}
		}
		
	}
	
	public class PanelBackgroundImage extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String dir;
		
		public PanelBackgroundImage(String dir){
			this.dir = dir;
		}
		
		
		@Override
		public void paint(Graphics g) {	
	        try {
				g.drawImage((ImageLoad.getInstance()).getImage(dir, getWidth(), getWidth()), 0, 0, getWidth(), getHeight(),this);
			} catch (IOException e) {
				e.printStackTrace();
			}
	        super.setOpaque(false);
	        super.paint(g);
		}
	}
	public class HandEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			addHandZone();
			
		}
	
	}
	public class CancelHandEvent implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			gameZone.remove(panelHand);
			gameZone.revalidate();
		}
	
	}
	@Override
	public void updateNewHandCard(final Card id,final Warlock w) {
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			addCard(handA, scrollHandA, new CardPanel(id.getCode(),String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getDirImg(),id.getElement(),id.getBuff(),!id.canUse(),id.getDescription(),id.haveActiveAction(),id));
			((WarlockPanel)waInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());
			
		}
		else{
			addCard(handB, scrollHandB, new CardPanel(id.getCode(),String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getDirImg(),id.getElement(),id.getBuff(),!id.canUse(),id.getDescription(),id.haveActiveAction(),id));
			((WarlockPanel)wbInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());

		}
	}
	@Override
	public void updatePlayCardMana(final Warlock w) {
		addCard(manaAct, scrollManaAct, selectCard);
		removeCard(handAct, scrollHandAct, selectCard);
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			((WarlockPanel) waInfo).decreaseHandNum();
		}
		else{
			((WarlockPanel) wbInfo).decreaseHandNum();
		}
		
		selectCard.defaultCard();
		selectCard.manaCardActionPanel(selectCard.idCard);
		selectCard = null;
		
	}
	@Override
	public void updatePlayCardEntity(final Warlock w) {
		removeCard(handAct, scrollHandAct, selectCard);
		addCard(gameAct, scrollGameAct, selectCard);
		
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			((WarlockPanel) waInfo).decreaseHandNum();
		}
		else{
			((WarlockPanel) wbInfo).decreaseHandNum();
		}
		
		selectCard.defaultCard();		
		selectCard.entityCardActionPanel(selectCard.idCard);
		selectCard.canUse = true;
		selectCard = null;
	}
	@Override
	public void updatePlayCardMana(final Card idCard ,final Warlock w) {
		JPanel aux;
		if( (aux = found(handA, idCard.getId())) != null){
			addCard(manaA, scrollManaA, aux);
			removeCard(handA, scrollHandA, aux);
			if(handA == handAct){
				((CardPanel) aux).manaCardActionPanel(idCard);
				((CardPanel) aux).setCanUse(true);
				//setActiveActionPanel(manaA,true);
			}
			else{
				((CardPanel) aux).setCanUse(false);
				//setActiveActionPanel(manaA,false);
			}
		}
		else{			
			aux = found(handB, idCard.getId());
			addCard(manaB, scrollManaB, aux);
			removeCard(handB, scrollHandB, aux);
			if(handB == handAct){			
				((CardPanel) aux).manaCardActionPanel(idCard);
				((CardPanel) aux).setCanUse(true);
				//setActiveActionPanel(manaB,true);
			}
			else{
				((CardPanel) aux).setCanUse(false);
				//setActiveActionPanel(manaB,false);
			}
		}
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			((WarlockPanel) waInfo).decreaseHandNum();
		}
		else{
			((WarlockPanel) wbInfo).decreaseHandNum();
		}
		
		((CardPanel) aux).defaultCard();
		selectCard = null;
		
	}
	@Override
	public void updatePlayCardEntity(final Card idCard ,final Warlock w) {
		JPanel aux;
		if( (aux = found(handA, idCard.getId())) != null){
			removeCard(handA, scrollHandAct, aux);
			addCard(gameA, scrollGameAct, aux);
			if(handA == handAct){	
				((CardPanel) aux).entityCardActionPanel(idCard);
				//setActiveActionPanel(gameA,true);
				((CardPanel) aux).setCanUse(true);
			}
			else{
				//setActiveActionPanel(gameA,false);
				((CardPanel) aux).setCanUse(false);
			}
		}
		else{
			aux = found(handB, idCard.getId());
			removeCard(handB, scrollHandAct, aux);
			addCard(gameB, scrollGameAct, aux);
			if(handB == handAct){				
				((CardPanel) aux).entityCardActionPanel(idCard);
				//setActiveActionPanel(gameB,true);
				((CardPanel) aux).setCanUse(true);
			}
			else{
				//setActiveActionPanel(gameB,false);
				((CardPanel) aux).setCanUse(false);
			}
		}
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			((WarlockPanel) waInfo).decreaseHandNum();
		}
		else{
			((WarlockPanel) wbInfo).decreaseHandNum();
		}
		((CardPanel) aux).defaultCard();
		selectCard = null;
	}
	@Override
	public void updateErrorCard(String er) {
		if(selectCard != null){
			if(exchangeFrame != null){
				exchangeFrame.setErText(er);
	
			}
			else{
				selectCard.setSeparate();
				selectCard.setInfo(er,Color.red);
				selectCard.revalidate();
				selectCard = null;
			}
		}
		
	}
	@Override
	public void updatePhase(Phase phase) {
		if(phase == Phase.ini){
			this.phase.setText("Fase pre-Trifulca");
			combatButton.setEnabled(false);
		}
		else if(phase == Phase.combatInterventionAt){
			this.phase.setText("<html>Fase Intervenciones I <br> Brujo Atacante");
		}
		else if(phase == Phase.combatInterventionDef){
			this.phase.setText("<html>Fase Intervenciones I <br> Brujo Defensor");
		}
		else if(phase == Phase.combatAttack){
			this.phase.setText("<html>Fase asignacion<br>de atacantes");
		}
		else if(phase == Phase.combatDefense){
			this.phase.setText("<html>Fase asignacion<br>de defensores");
		}
		else if(phase == Phase.combatIntervention2At){
			this.phase.setText("<html>Fase Intervenciones II <br> Brujo Atacante");
		}
		else if(phase == Phase.combatIntervention2Def){
			this.phase.setText("<html>Fase Intervenciones II <br> Brujo Defensor");
		}
		else if(phase == Phase.combatSolve){
			this.phase.setText("Resolucion de Trifulca");
		}
		else if(phase == Phase.finaly){
			this.phase.setText("Fase Final");
		}		
		//Toolkit.getDefaultToolkit().beep(); 
		
	}
	@Override
	public void updatePlayCardIntervention() {
		if(interventionZone == null){
			addInterventionZone();
		}
		selectCard.updateStats(String.valueOf(selectCard.idCard.getAttack()),String.valueOf(selectCard.idCard.getDefense()),String.valueOf(selectCard.idCard.getLife()),selectCard.idCard.getBuff(),selectCard.idCard.getDescription(),!selectCard.idCard.canUse(),selectCard.idCard.haveActiveAction(),false);

		addCardIn(interventionZone, scrollInterventionZone, selectCard);
		selectCard.defaultCard();		
		selectCard.setCanUse(false);
		selectCard = null;
		
	}
	@Override
	public void updatePlayCardIntervention(Card id) {
		if(interventionZone == null){
			addInterventionZone();
		}
		if(exchangeFrame != null){
			finishExchange();
		}
		CardPanel aux = (CardPanel) found(handA, id.getId());
		if(aux == null){
			aux = (CardPanel) found(handB, id.getId());
		}
		aux.updateStats(String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getBuff(),id.getDescription(),!id.canUse(),id.haveActiveAction(),false);

		addCardIn(interventionZone, scrollInterventionZone, aux);
		aux.defaultCard();		
		aux.setCanUse(false);
		handAct.repaint();
		selectCard = null;
		
	}
	@Override
	public void updateInfoInCard(String er) {
		selectCard.setSeparate();
		selectCard.setInfo(er,Color.white);
		selectCard.revalidate();
	}
	@Override
	public void updateRotate(boolean allTurn,boolean show,boolean changeWarlock) {
		if(exchangeFrame != null){
			finishExchange();
		}
		
		if(rotateFrame == null && show){
			main.setVisible(false);
			String text = "Cambio de brujo";
			showRotateInfo(text, false, 0);
			changeTimer = new Timer(1000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainPanel.remove(rotateFrame);
					rotateFrame.dispose();
					rotateFrame.setVisible(false);
					
					rotateFrame = null;	
					main.setVisible(true);
					changeTimer.stop();
					changeTimer = null;
				}
			});
			changeTimer.start();
		}
	
		
		gameZone.remove(panelHand);
		rotate(allTurn,changeWarlock);
	}
	@Override
	public void updateFinishIntervention() {
		gameZone.remove(interventionPanel);
		setActiveActionPanel(manaAct, true);
		interventionZone = null;	
	}
	@Override
	public void updateRemoveEntity(final Card id,final Warlock w) {
		JPanel aux = null;
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			aux = found(gameA, id.getId());	
			if(aux != null){
				gameA.remove(aux);
				gameA.repaint();
			}
		}
		else{
			aux = found(gameB, id.getId());	
			if(aux != null){
				gameB.remove(aux);
				gameB.repaint();
			}
		}
		
	}
	@Override
	public void updateDieEntity(final Card id,final Warlock w) {
		JPanel aux = null;
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			aux = found(gameA, id.getId());	
			if(aux != null){
				gameA.remove(aux);
				
				((WarlockPanel)waInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());
				
				gameA.repaint();
			}
		}
		else{
			aux = found(gameB, id.getId());	
			if(aux != null){
				gameB.remove(aux);
				
				((WarlockPanel)wbInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());

				
				gameB.repaint();
			}
		}
	}
	@Override
	public void updateStatsCard(final Card id) {
		JPanel aux = found(gameA, id.getId());	
		if(aux != null){
			((CardPanel) aux).updateStats(String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getBuff(),id.getDescription(),!id.canUse(),id.haveActiveAction(),false);
			aux.validate();
		}
		else if((aux = found(gameB,id.getId())) != null){
			((CardPanel) aux).updateStats(String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getBuff(),id.getDescription(),!id.canUse(),id.haveActiveAction(),false);
			aux.validate();
		}
		if(aux == null){
			aux = found(manaA, id.getId());	
			if(aux != null){
				((CardPanel) aux).updateStats(String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getBuff(),id.getDescription(),!id.canUse(),id.haveActiveAction(),false);
				aux.validate();
			}
			else if((aux = found(manaB,id.getId())) != null){
				((CardPanel) aux).updateStats(String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getBuff(),id.getDescription(),!id.canUse(),id.haveActiveAction(),false);
				aux.validate();
			}
		}
		
		if(combatFrame != null){
			combatFrame.repaint();
			
		}
	}
	@Override
	public void updateNewWarlocks(final Warlock wa,final Warlock wb){
		waInfo = new WarlockPanel(wa,String.valueOf(wa.getName()), String.valueOf(wa.getNumHand()),String.valueOf(wa.getNumGraveyard()) 
				,String.valueOf( wa.getNumDeck()), String.valueOf(wa.getLife()),wa.getImage());
		wbInfo = new WarlockPanel(wb,String.valueOf(wb.getName()), String.valueOf(wb.getNumHand()),String.valueOf(wb.getNumGraveyard())
				,String.valueOf( wb.getNumDeck()), String.valueOf(wb.getLife()),wb.getImage());
	
		((WarlockPanel)wbInfo).avatar.setWrong(true);
		((WarlockPanel)waInfo).avatar.setWrong(false);
		infoZone.add(warlocksZone());
	}
	@Override
	public void updateAddGameEntity(final Card c,final Warlock w) {
		CardPanel aux = new CardPanel(String.valueOf(c.getCode()), String.valueOf(c.getAttack()),String.valueOf( c.getDefense()),String.valueOf( c.getLife()),c.getDirImg(),c.getElement(),c.getBuff(),!c.canUse(), c.getDescription(),c.haveActiveAction(),c);
		aux.entityCardActionPanel(c);
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			if(gameA != gameAct){
				((CardPanel) aux).setCanUse(false);
			}
			gameA.add(aux);
			gameA.revalidate();
		}
		else{
			if(gameB != gameAct){
				((CardPanel) aux).setCanUse(false);
			}
			gameB.add(aux);
			gameB.revalidate();
		}		
	}
	@Override
	public void updateAddManaCard(final Card c,final Warlock w) {
		CardPanel aux = new CardPanel(String.valueOf(c.getCode()), String.valueOf(c.getAttack()),String.valueOf( c.getDefense()),String.valueOf( c.getLife()),c.getDirImg(),c.getElement(),c.getBuff(),!c.canUse(),c.getDescription(),c.haveActiveAction(), c);
		aux.manaCardActionPanel(c);
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			manaA.add(aux);
			manaA.revalidate();
		}
		else{
			manaB.add(aux);
			manaB.revalidate();
		}	
		
	}
	@Override
	public void updateRemoveManaCard(final Card c,final Warlock w) {
		if(w.getId() == ((WarlockPanel) waInfo).getId()){		
			manaA.remove(found(manaA, c.getId()));
			manaA.revalidate();
		
		}
		else{
			manaB.remove(found(manaB, c.getId()));
			manaB.revalidate();
		}	
		
	}
	@Override
	public void updateAddGraveyard(final Card c,final Warlock w) {
		
	}
	@Override
	public void updateRemoveGraveyard(final Card c,final Warlock w) {
		
	}
	@Override
	public void updateRemoveHandCard(final Card c,final Warlock w) {
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			handA.remove(found(handA, c.getId()));
			((WarlockPanel)waInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());

		}
		else{
			handB.remove(found(handB, c.getId()));
			((WarlockPanel)wbInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());

		}	
		
	}
	@Override
	public void updateRefreshStatsWarlock(final Warlock w) {
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			((WarlockPanel)waInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());
		}
		else{
			((WarlockPanel)wbInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());
		}
		//waInfo.revalidate();
		//wbInfo.revalidate();
		
	}
	@Override
	public void updateDiscar(final Card c,final Warlock w) {
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			CardPanel aux = (CardPanel) found(handA, c.getId());
			if(aux != null){
				handA.remove(aux);
				((WarlockPanel)waInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());
				
				handA.revalidate();
				handA.repaint();
				//waInfo.revalidate();
			}
			else{
				((WarlockPanel)waInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());

			}
		}
		else{
			CardPanel aux = (CardPanel) found(handB, c.getId());
			if(aux != null){
				handB.remove(aux);
				((WarlockPanel)wbInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());
				handB.revalidate();
				handB.repaint();
				//wbInfo.revalidate();
			}
			else{
				((WarlockPanel)wbInfo).refreshStats(String.valueOf(w.getName()), String.valueOf(w.getNumHand()),String.valueOf(w.getNumGraveyard()) ,String.valueOf( w.getNumDeck()), String.valueOf(w.getLife()),w.getInfoBuff());

			}
		}
		
	}
	@Override
	public void updateDealMana(int count) {
		if(selectCard != null && found(handAct, selectCard.id) != null){
			nextButton.setFocusable(false);
			nextButton.setEnabled(false);
			loadButton.setEnabled(false);
			saveButton.setEnabled(false);
			setActiveActionPanel(handAct,false);		
			selectCard.showManaPayPanel(count);
		}
		
	}
	@Override
	public void updateDealMana(int count,Card c) {
		if(selectCard != null && found(handAct, c.getId()) != null){
			nextButton.setFocusable(false);
			nextButton.setEnabled(false);
			loadButton.setEnabled(false);
			saveButton.setEnabled(false);
			setActiveActionPanel(handAct,false);		
			selectCard.showManaPayPanel(count);
		}
		
	}
	@Override
	public void updateFinishDealMana() {
		nextButton.setFocusable(true);
		nextButton.setEnabled(true);
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
		setActiveActionPanel(handAct,true);
		selectCard = null;
	}
	@Override
	public void updateDecreDealMana(Card c) {
		if(selectCard != null  && found(handAct, c.getId()) != null)
			selectCard.decreasetPayCount();
		
	}
	@Override
	public void updateDecreDealMana() {
		if(selectCard != null  && found(handAct, selectCard.id) != null)
			selectCard.decreasetPayCount();
		
	}
	@Override
	public void updateInsertNewCombat() {
		if(combatInfo == null){
			combatInfo = new TreeMap<Card,LinkedList<CardPanel>>();
			combatButton.setEnabled(true);
		}
		LinkedList<CardPanel> lin = new LinkedList<CardPanel>();
		lin.add(selectCard);
		selectCard.image.setCombat(true);
		combatInfo.put(selectCard.getIdCard(), lin);
		selectCard.defaultCard();
		selectCard = null;
		targetCard = null;
		
	}
	@Override
	public void updateInsertInCombat() {
		combatInfo.get(targetCard.getIdCard()).add(selectCard);
		selectCard.image.setCombat(true);
		selectCard.defaultCard();	
		selectCard = null;
		targetCard = null;
	}
	
	@Override
	public void updateInsertNewCombat(Card id) {
		if(combatInfo == null){
			combatInfo = new TreeMap<Card,LinkedList<CardPanel>>();
			combatButton.setEnabled(true);
		}
		CardPanel aux = (CardPanel) found(gameA, id.getId());
		if(aux == null){
			aux = (CardPanel) found(gameB, id.getId());
		}
		
		LinkedList<CardPanel> lin = new LinkedList<CardPanel>();
		
		lin.add(aux);
		aux.image.setCombat(true);
		combatInfo.put(id, lin);
		aux.defaultCard();
		aux.repaint();
		selectCard = null;
		targetCard = null;
		
	}
	@Override
	public void updateInsertInCombat(Card at,Card def) {
		
		CardPanel aux = (CardPanel) found(gameA, def.getId());
		if(aux == null){
			aux = (CardPanel) found(gameB, def.getId());
		}
		
		combatInfo.get(at).add(aux);
		aux.image.setCombat(true);
		aux.defaultCard();	
		aux.repaint();
		selectCard = null;
		targetCard = null;
	}
	
	@Override
	public void updateRemoveCombat(Card at) {
		combatInfo.remove(at);
		
	}
	@Override
	public void updateRemoveFromCombat(Card at, Card c) {
		JPanel aux = found(gameA, c.getId());
		if(aux != null){	
			removeLink(combatInfo.get(at),c.getId());
			
		}
		else{
			aux = found(gameB, c.getId());
			if(aux != null){
				removeLink(combatInfo.get(at),c.getId());
			}
		}
		
	}
	@Override
	public void updateRemoveDefenders(Card at) {
		LinkedList<CardPanel> lin = combatInfo.get(at);
		CardPanel aux = lin.getFirst();
		lin.clear();
		lin.add(aux);
	
		
	}
	@Override
	public void updateDealDamage(int count) {
		//showCombatFrame();
		combatFrame.iniDealDamage(count);		
		//dealDamage = true;
	}
	@Override
	public void updateSolveCombat() {
		solveCombat = true;
		setCombatIcon(false);
		setActiveActionPanel(gameAct, false);
		setActiveActionPanel(manaAct, false);
		nextButton.setEnabled(false);
		handButton.setEnabled(false);
		combatButton.setEnabled(false);
		loadButton.setEnabled(false);
		saveButton.setEnabled(false);
		
		if(combatFrame != null){
			combatFrame.setVisible(false);
			combatFrame = null;
		}
		
		showCombatFrame(true);
		//combatFrame.nextCombat();
		t = new Timer(5000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				combatFrame.nextCombat();
				controller.nextCombat();
			}
		});
		combatFrame.setTimer(t);
		t.start();
		controller.nextCombat();
		
		//controller.nextCombat();
		
	}
	@Override
	public void updateSolveCombatNet() {
		solveCombat = true;
		setCombatIcon(false);
		setActiveActionPanel(gameAct, false);
		setActiveActionPanel(manaAct, false);
		nextButton.setEnabled(false);
		handButton.setEnabled(false);
		combatButton.setEnabled(false);
		loadButton.setEnabled(false);
		saveButton.setEnabled(false);
		
		if(combatFrame != null){
			combatFrame.setVisible(false);
			combatFrame = null;
		}
		
		showCombatFrame(true);
		//combatFrame.nextCombat();
		t = new Timer(5000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {	
				controller.nextCombat();
				t.stop();
			}
		});
		combatFrame.setTimer(t);
		t.start();
		controller.nextCombat();
		
		//controller.nextCombat();
		
	}
	@Override
	public void updateNextCombat(){
		if(t != null && !t.isRunning()){
			combatFrame.nextCombat();
			t.restart();
		}
	}
	@Override
	public void updateFinishSolveCombatNet() {
		t.stop();
		t = null;
	
		//combatFrame.dispose();
		//mainPanel.remove(combatFrame);
		//combatFrame.setVisible(false);
		//combatFrame.setVisible(false);
		//combatFrame = null;
		
		finishCombatFrame();
		
		setDeafultMouseEvent(gameDef);
		setDeafultMouseEvent(gameAct);
		setActiveActionPanel(gameAct, true);
		setActiveActionPanel(manaAct, true);
		
		
		combatInfo = null;
		nextButton.setEnabled(true);
		handButton.setEnabled(true);
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
	//	controller.nextPhase();
		
	}
	@Override
	public void updateFinishSolveCombat() {
		t.stop();
		t = null;
		
		setActiveActionPanel(gameAct, true);
		setActiveActionPanel(manaAct, true);
	
		//setActiveActionPanel(gameDef, true);
		setDeafultMouseEvent(gameDef);
		//combatFrame.dispose();
		//mainPanel.remove(combatFrame);
		//combatFrame.setVisible(false);
		combatFrame.setVisible(false);
		combatFrame = null;
		
		combatInfo = null;
		nextButton.setEnabled(true);
		handButton.setEnabled(true);
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
		controller.nextPhase();
		
	}
	@Override
	public void updateInfoSolveCombat(String text) {
		if(combatFrame != null)
			combatFrame.addCombatText(text);
		
	}
	@Override
	public void updateFinishDealDamage() {
		t.restart();		
	}
	@Override
	public void updateErrorSolveCombat(String text) {
		if(combatFrame != null)
			combatFrame.setErrorDeal(text);
		
	}
	@Override
	public void updateDecreaseSolvecombat() {
		if(combatFrame != null)
			combatFrame.decreaseDealDamage();
		
	}
	@Override
	public void updateGoDiscard(int count) {
		nextButton.setEnabled(false);
		loadButton.setEnabled(false);
		saveButton.setEnabled(false);
		addHandZone();
		cancelHanZoneButton.setEnabled(false);
		setActiveActionPanel(gameAct, false);
		setActiveActionPanel(manaAct, false);
		showDiscardFrame(count,true);
	}
	@Override
	public void updateFinishDiscard() {
		cancelHanZoneButton.setEnabled(true);
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
		nextButton.setEnabled(true);
		setActiveActionPanel(gameAct, true);
		setActiveActionPanel(manaAct, true);
		discardFrame.dispose();
		mainPanel.remove(discardFrame);
		discardFrame = null;
		
	}
	@Override
	public void updateGoExchangeEnviroment() {
		if(exchangeFrame == null){
			showExchangeFrame(manaAct, manaDef);
			setActiveActionPanel(handAct, false);
			setActiveActionPanel(gameAct, false);
			setActiveActionPanel(gameDef, false);
			setActiveActionPanel(manaAct, false);
			setActiveActionPanel(manaDef, false);
			nextButton.setEnabled(false);
			loadButton.setEnabled(false);
			saveButton.setEnabled(false);
		}
		if(targetCard != null){
			targetCard.image.setCheck(true);
			//targetCard.repaint();
			exchangeFrame.repaint();
			targetCard = null;
		}

		exchangeFrame.setErText("");
		exchangeFrame.cantExchange();
	}
	@Override
	public void updateCanExchangeEnviroment() {
		if(targetCard != null){
			targetCard.image.setCheck(true);
			//targetCard.repaint();
			exchangeFrame.repaint();
			targetCard = null;
		}
		exchangeFrame.canExchange();
		
	}
	@Override
	public void updateSolveIntervention() {
		setActiveActionPanel(gameAct, false);
		setActiveActionPanel(manaAct, false);
		nextButton.setEnabled(false);
		loadButton.setEnabled(false);
		saveButton.setEnabled(false);
		
		if(combatFrame != null){
			combatFrame.setVisible(false);
			combatFrame = null;
		}
		showInterventionsFrame();
		
		t = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(listFrame != null){
					mainPanel.remove(listFrame);
					listFrame = null;
				}
				if(interventionZone.getComponentCount() > 0){
					interventionZone.remove(0);
					interventionZone.repaint();
				}				
				interventionFrame.next();
				t.stop();
				controller.nextSolveIntervention();		
				if(t != null){
					t.restart();
				}
			}
		});
		interventionFrame.setTimer(t);
		t.start();
		interventionZone.remove(0);
		interventionFrame.next();
		controller.nextSolveIntervention();
		
		
		
		
	}
	@Override
	public void updateSolveInterventionNet() {
		setActiveActionPanel(gameAct, false);
		setActiveActionPanel(manaAct, false);
		nextButton.setEnabled(false);
		loadButton.setEnabled(false);
		saveButton.setEnabled(false);
		
		if(combatFrame != null){
			combatFrame.setVisible(false);
			combatFrame = null;
		}
		
		showInterventionsFrame();
		
		t = new Timer(3000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(listFrame != null){
					mainPanel.remove(listFrame);
					listFrame = null;
				}
				controller.nextSolveIntervention();	
				t.stop();				
				/*if(t != null){
					t.restart();
				}*/
			}
		});
		interventionFrame.setTimer(t);
		t.start();
		interventionZone.remove(0);
		interventionFrame.next();
		controller.nextSolveIntervention();
		
		
		
		
	}
	@Override
	public void updateNextIntervention() {
		if(interventionZone.getComponentCount() > 0){
			interventionZone.remove(0);
			interventionZone.repaint();
		}				
		interventionFrame.next();
		if(t != null){
			t.restart();
		}
	}
	@Override
	public void updateFinishSolveIntervention() {
		t.stop();
		t = null;
		setActiveActionPanel(gameAct, true);
		setActiveActionPanel(manaAct, true);
		
		
		interventionFrame.dispose();
		mainPanel.remove(interventionFrame);
		interventionFrame = null;
		
		nextButton.setEnabled(true);
		loadButton.setEnabled(true);
		saveButton.setEnabled(true);
		if(!vs){
			controller.nextPhase();
		}
		
	}
	@Override
	public void updateInfoSolveIntervention(String str) {
		interventionFrame.setInfoText(str);
		
	}
	@Override
	public void updateUseActive() {
		selectCard.defaultCard();
		selectCard.updateStats(String.valueOf(selectCard.idCard.getAttack()),String.valueOf(selectCard.idCard.getDefense()),String.valueOf(selectCard.idCard.getLife()),selectCard.idCard.getBuff(),selectCard.idCard.getDescription(),!selectCard.idCard.canUse(),selectCard.idCard.haveActiveAction(),false);
		selectCard.validate();
		selectCard = null;
		targetCard = null;
	}
	@Override
	public void updateUseActive(Card c,Warlock w) {
		CardPanel aux = null;
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			aux = (CardPanel) found(gameA, c.getId());
			if(aux == null){
				aux = (CardPanel) found(manaA, c.getId());
			}
			aux.defaultCard();
			aux.updateStats(String.valueOf(c.getAttack()),String.valueOf(c.getDefense()),String.valueOf(c.getLife()),c.getBuff(),c.getDescription(),!c.canUse(),c.haveActiveAction(),false);
			aux.validate();
		}
		else{
			aux = (CardPanel) found(gameB, c.getId());
			if(aux == null){
				aux = (CardPanel) found(manaB, c.getId());
			}
			aux.defaultCard();
			aux.updateStats(String.valueOf(c.getAttack()),String.valueOf(c.getDefense()),String.valueOf(c.getLife()),c.getBuff(),c.getDescription(),!c.canUse(),c.haveActiveAction(),false);
			aux.validate();
		}	
		selectCard = null;
		targetCard = null;
	}
	@Override
	public void updateFinishPlay(String winner,String text) {
		String aux = "";
		if(winner.equals("")){
			aux = "Empate";
		}
		else
			aux = "<html>El brujo "+winner+" ha ganado la partida ¡Gloria al nuevo Rey!<br>"+text;
		JOptionPane.showInternalMessageDialog(getContentPane(), aux, "Ganador", JFrame.EXIT_ON_CLOSE);

		System.exit(0);
		
	}
	@Override
	public void updateManafire(String warlock, int damage) {
		String text = "El brujo "+warlock+" ha sufrido un quemadura de cloro de: ";
		
		
		if(t == null && infoFrame == null){
			showInfoTime(text, true,damage);
			t = new Timer(3000, new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainPanel.remove(infoFrame);
					infoFrame.dispose();
					infoFrame.setVisible(false);
					mainPanel.repaint();
					
					infoFrame = null;	
					t.stop();
					t = null;
				}
			});
			t.start();
		}
		
	}
	@Override
	public void updateNotCardsDeck(String warlock) {
		String text = "El brujo"+warlock+"  no puedo robar mas cartas ¡Morirás!";
		if(infoFrame == null){
			showInfoTime(text, false,0);
		}
		else{
			infoFrame.addText(text);
			if(t != null){
				t.stop();
			}
		}
			
		t = new Timer(5000, new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainPanel.remove(infoFrame);
				infoFrame.dispose();
				infoFrame.setVisible(false);
				mainPanel.repaint();
					
				infoFrame = null;	
				t.stop();
				t = null;
			}
		});
		t.start();
		
	}
	@Override
	public void updateRemoveIntervention(Card c) {
		if(interventionFrame != null){
			JPanel aux = found(interventionZone, c.getId());
			interventionZone.remove(aux);
			interventionZone.repaint();
			interventionFrame.removeStack();
			interventionFrame.fill(interventionZone);
		}
		
	}
	@Override
	public void updateAll(Warlock wa, Warlock wb,Warlock wTurn) {
		clearAll();
		updateNewWarlocks(wa, wb);
		
		if(wTurn == wa){
			gameTurn = gameA;
		}
		else{
			gameTurn = gameB;
		}
		
		//main.updateUI();
		
	}
	@Override
	public void updateInsertNewCombat(Card c, Warlock w) {
		if(combatInfo == null){
			combatInfo = new TreeMap<Card,LinkedList<CardPanel>>();
			combatButton.setEnabled(true);
		}
		CardPanel aux = null;
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			aux = (CardPanel) found(gameA, c.getId());
		}
		else{
			aux = (CardPanel) found(gameB, c.getId());
		}
		LinkedList<CardPanel> lin = new LinkedList<CardPanel>();
		lin.add(aux);
		aux.image.setCombat(true);
		aux.repaint();
		combatInfo.put(c, lin);
		
	}
	@Override
	public void updateInsertInCombat(Card at,Card def, Warlock w) {
		CardPanel aux = null;
		if(w.getId() == ((WarlockPanel) waInfo).getId()){
			aux = (CardPanel) found(gameA, def.getId());
		}
		else{
			aux = (CardPanel) found(gameB, def.getId());
		}
		combatInfo.get(at).add(aux);
		aux.image.setCombat(true);
		aux.repaint();
	}
	@Override
	public void updateAddCardIntervention(Card id) {
		if(interventionZone == null){
			addInterventionZone();
		}
		CardPanel aux = new CardPanel(id.getCode(),String.valueOf(id.getAttack()),String.valueOf(id.getDefense()),String.valueOf(id.getLife()),id.getDirImg(),id.getElement(),id.getBuff(),!id.canUse(),id.getDescription(),id.haveActiveAction(),id);
		addCardIn(interventionZone, scrollInterventionZone, aux);
		aux.setCanUse(false);
	}
	@Override
	public void updateErrorFinish(String text) {
		JOptionPane.showInternalMessageDialog(getContentPane(), text,"Error", JOptionPane.ERROR_MESSAGE);
		System.exit(-1);
		
	}
	@Override
	public void updateChangePhase(){
		if(gameA == gameTurn){
			gameTurn = gameB;
			((PanelBackgroundImage) gameB).dir = dirBackgroundGameAt;
			((PanelBackgroundImage) gameA).dir = dirBackgroundGameDef;
		}
		else{
			gameTurn = gameA;
			((PanelBackgroundImage) gameA).dir = dirBackgroundGameAt;
			((PanelBackgroundImage) gameB).dir = dirBackgroundGameDef;
		}
		gameA.repaint();
		gameB.repaint();
	}
	@Override
	public void updateChangeWarlockTurn(int wIdTurn) {
		if(wIdTurn == ((WarlockPanel)waInfo).getId()){
			((WarlockPanel)waInfo).avatar.setWrong(false);
			((WarlockPanel)wbInfo).avatar.setWrong(true);
		}
		else{
			((WarlockPanel)waInfo).avatar.setWrong(true);
			((WarlockPanel)wbInfo).avatar.setWrong(false);
		}
		waInfo.repaint();
		wbInfo.repaint();
		
	}
	@Override
	public void updateFinishLoad() {
		load.dispose();
		load.setVisible(false);
		load = null;
		setVisible(true);
		//fullScreen();
		
	}
	@Override
	public void updateAct(Warlock w) {
		if(w.getId() == ((WarlockPanel)waInfo).getId()){
			gameAct = gameA;
			manaAct = manaA; 
			handAct = handA;
			
			gameDef = gameB;
			manaDef = manaB;
			
		}
		else{
			gameAct = gameB;
			manaAct = manaB; 
			handAct = handB;
			
			gameDef = gameA;
			manaDef = manaA;
		}
		setActiveActionPanel(gameAct, true);
		setActiveActionPanel(manaAct, true);
		setActiveActionPanel(gameDef, false);
		setActiveActionPanel(manaDef, false);
		
	}
	@Override
	public void updateShowEnemyHand(Warlock w){
		if(t != null){
			t.stop();
		}
		if(interventionFrame != null && interventionFrame.isShowing()){
			interventionFrame.setVisible(false);
		}
		if(w.getId() == ((WarlockPanel)waInfo).getId()){
	
			showListFrame(handA);
			//listFrame.repaint();
		}
		else{

			showListFrame(handB);
			//handAct = handA;
			//listFrame.repaint();
		}
	}
	@Override
	public void updateError(String text){
		JOptionPane.showInternalMessageDialog(getContentPane(), text,"Error", JOptionPane.ERROR_MESSAGE);
	}
	@Override
	public void updateChatText(String text) {
		if(chat == null){
			chat = new ChatFrame(controller);
		}
		
		chat.addText(text);
		
		if(!chat.isShowing()){
			chatButton.setBackground(Color.yellow);
		}
		
	}
	
	
}
