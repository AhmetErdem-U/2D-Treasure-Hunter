package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{ // Annehmen der Tastatursteuerung
	
	public boolean upPressed, downPressed, leftPressed, rightPressed; // Abfrage ob die Taste gedrückt wurde

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
		int code = e.getKeyCode(); // Gibt den Int Tastaur Code zurück
		
		if(code == KeyEvent.VK_W) { // Wurde W gedrückt ? -> upPressed true
			upPressed = true;
		}
		
        if(code == KeyEvent.VK_S) { //
        	downPressed = true;
		}
        
        if(code == KeyEvent.VK_A) { //
        	leftPressed = true;
		}
        
        if(code == KeyEvent.VK_D) { //
        	rightPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_W) { // Wurde W losgelassen ? -> upPressed false
			upPressed = false;
		}
		
        if(code == KeyEvent.VK_S) { //
        	downPressed = false;
		}
        
        if(code == KeyEvent.VK_A) { //
        	leftPressed = false;
		}
        
        if(code == KeyEvent.VK_D) { //
        	rightPressed = false;
		}
		
	} 

}
