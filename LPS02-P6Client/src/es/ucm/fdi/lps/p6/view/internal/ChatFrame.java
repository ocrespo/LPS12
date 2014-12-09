/**
 * 
 */
package es.ucm.fdi.lps.p6.view.internal;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import es.ucm.fdi.lps.p6.controller.Controller;

/**
 * @author Roni
 *
 */
public class ChatFrame extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton send = new JButton("Enviar");
	private JPanel chatZone;
	private JTextField text;
	private Controller controller;
	private JScrollPane sChat;
	
	public ChatFrame(Controller controller){
		this.controller = controller;
		buidl();
		configListener();
	}
	/**
	 * build frame
	 */
	private void buidl(){
		JPanel main = new JPanel(new FlowLayout(0,0, 0));
		//main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		
		//main.add();
		
		chatZone = new JPanel();
		chatZone.setLayout(new BoxLayout(chatZone, BoxLayout.Y_AXIS));
		sChat = new JScrollPane(chatZone);
		//chatZone.add(BoxLayou)
		sChat.setMaximumSize(new Dimension(790,435));
		sChat.setMinimumSize(new Dimension(790,435));
		sChat.setPreferredSize(new Dimension(790,435));
		chatZone.setBackground(Color.white);
		
		
		
		main.add(sChat);
		
		text = new JTextField();
		text.setMaximumSize(new Dimension(710,30));
		text.setMinimumSize(new Dimension(710,30));
		text.setPreferredSize(new Dimension(710,30));
		
		
		send.setMaximumSize(new Dimension(80,30));
		send.setMinimumSize(new Dimension(80,30));
		send.setPreferredSize(new Dimension(80,30));
		
		JPanel down = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		
		down.add(text);
		down.add(send);
		down.setMaximumSize(new Dimension(800,30));
		down.setMinimumSize(new Dimension(800,30));
		down.setPreferredSize(new Dimension(800,30));
		
		main.add(down);
		
		//main.setMaximumSize(new Dimension(800,500));
		//main.setMinimumSize(new Dimension(800,500));
		//main.setPreferredSize(new Dimension(800,500));
		
		getContentPane().add(main);
		setSize(800, 500);
		setMinimumSize(new Dimension(800, 500));
		setPreferredSize(new Dimension(800, 500));
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		
		setClosable(true);
		setTitle("Chat");
		setLocation(d.width/4, d.height/4);
		pack();
		setVisible(true);
	}
	/**
	 * config listener
	 */
	private void configListener(){
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!text.getText().equals("")){
					controller.sendChat(text.getText());
					text.setText("");
				}
				
			}
		});
		text.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				int key = arg0.getKeyCode();  
                if (key == KeyEvent.VK_ENTER) {  
                	if(!text.getText().equals("")){
	                	controller.sendChat(text.getText());
	    				text.setText("");
                	}
                }  
				
			}
		});
	}
	/**
	 * add text to chat
	 * @param text
	 */
	public void addText(String text){
		chatZone.add(new JLabel("<html>"+text));
		chatZone.revalidate();
	
	}

}
