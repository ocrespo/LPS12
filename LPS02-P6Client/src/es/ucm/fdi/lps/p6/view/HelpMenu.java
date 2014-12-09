/**
 * 
 */
package es.ucm.fdi.lps.p6.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * @author ocrespo
 *
 */
public class HelpMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton gui = new JButton("Interfaz gráfica");
	private JButton cards = new JButton("Las cartas");
	private JButton warlocks = new JButton("Los brujos");
	private JButton phase = new JButton("Las fases");
	private JPanel iniPanel;
	private JButton home = new JButton("Inicio");
	private JPanel zoneInfo;
	private JButton exit = new JButton("Salir");
	
	public HelpMenu(){
		build();
		configListemer();
	}
	public void build(){
		setLayout(new BorderLayout());
		
		zoneInfo = new JPanel();
		iniPanel = new JPanel();
		iniPanel.setLayout(new GridLayout(2,1));
		iniPanel.add(gui);

		iniPanel.add(phase);	
		zoneInfo.add(iniPanel);
		add(zoneInfo,BorderLayout.NORTH);
		
		JPanel down = new JPanel();
		down.add(home);
		down.add(exit);
		
		add(down,BorderLayout.SOUTH);

	}
	public void configListemer(){
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
				
			}
		});
		gui.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoneInfo.removeAll();	
				zoneInfo.add(gui());
				
				zoneInfo.repaint();
				zoneInfo.revalidate();				
			}
		});
		cards.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoneInfo.removeAll();	
				zoneInfo.add(cards());
				
				zoneInfo.repaint();
				zoneInfo.revalidate();
			}
		});
		warlocks.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoneInfo.removeAll();	
				zoneInfo.add(warlock());
				
				zoneInfo.repaint();
				zoneInfo.revalidate();
				
			}
		});
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoneInfo.removeAll();	
				zoneInfo.add(iniPanel);
				zoneInfo.repaint();
			}
		});
		phase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				zoneInfo.removeAll();	
				zoneInfo.add(phase());
				
				zoneInfo.repaint();
				zoneInfo.revalidate();
				
			}
		});
	}
	public JPanel gui(){
		JPanel guiPanel = new JPanel(new GridLayout(2,1));
		guiPanel.add(cards);
		guiPanel.add(warlocks);
		return guiPanel;
		
	}
	public JPanel cards(){
		JPanel zone = new JPanel();
		zone.setLayout(new BoxLayout(zone, BoxLayout.Y_AXIS));
		JTextArea info = new JTextArea();
		info.setEditable(false);
		info.setBorder(null);
		info.setText("La franaja marron con el icono del reloj de arena representa cuando una carta está descargada. A su derecha se encuentra la vida representada con el icono de un corazon.\n" +
				"La Franja roja con el simbolo del escudo con espada representa que la criatura está en combate.\n" +
				"La franza negra representa el tipo de carta. El dragon es un ser, el arbol un entorno y el libro una intervencion.\n" +
				"A su derecha se encuentra el coste de la carta (si tuviera). El número que se encuentra a la izquierda representa el numero de cloros.\n" +
				"En la franja naranja se repreenta los efectos activos que tiene la carta con un simbolito de impacto." +
				"\nEn la parte inferior de la carta se encuentra representado el ataque y defensa de la carta.\n\n\n\n\n" +
				"Para usar una carta haga boton izquierdo sobre ella y aparecera en la carta el panel de acciones.");
		info.setOpaque(false);
		
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon("imagenes//ayuda//carta_ayuda.png"));
		JLabel image2 = new JLabel();
		image2.setIcon(new ImageIcon("imagenes//ayuda//Leyenda_Elementos.png"));
		zone.add(image);
		zone.add(info);
		
		zone.add(image2);
		return zone;
	}
	public JPanel warlock(){
		JPanel zone = new JPanel();
		zone.setLayout(new BoxLayout(zone, BoxLayout.Y_AXIS));
		JTextArea info = new JTextArea();
		info.setEditable(false);
		info.setBorder(null);
		info.setText("La zona gris se encuentra el nombre del brujo. A su derecha aparecerá (si tuviera) un icono de impacto representando los efectos que hay sobre él.\n" +
				"Sobre el avatar aparecerá, cuando no sea su turno, el simbolo rojo de prohibido. De tal forma que el que no lo tenga posee el turno.\n" +
				"El simbolo rojo con cartas en abanico representa el número de cartas en mano. La pila de cartas azul el del mazoy la calavera el vertedero.");
		info.setOpaque(false);
		
		JLabel image = new JLabel();
		image.setIcon(new ImageIcon("imagenes//ayuda//brujo_ayuda.png"));
		zone.add(image);
		zone.add(info);
		return zone;
	}
	public JPanel phase(){
		JPanel zone = new JPanel();
		zone.setLayout(new BoxLayout(zone, BoxLayout.Y_AXIS));
		JTextArea info = new JTextArea();
		info.setEditable(false);
		info.setBorder(null);
		info.setText("En la fase preTrifulca, para jugar cartas debes pulsar 'ver mano'. Y hacer click sobre la carta y usar.\n" +
				"Las intervenciones se resuelven automaticamente y los combates también. Habiendo un boton para pausar y cuando se desee seguir\n" +
				"continuar.\n\nCuando se juege una intervencion sobre un objetivo. Aparecera el icono, para ver el objetivo de la intervención,\n" +
				"basta con poner el raton sobre la parte izquierda de la imagen. ");
		info.setOpaque(false);	
		zone.add(info);
		
		return zone;
	}
	
}
