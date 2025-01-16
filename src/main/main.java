package main;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		
		JFrame window = new JFrame(); //Erstellt ein neues Fenster
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );//Beendet die Anwendung, wenn das Fenster geschlossen wird
        window.setResizable(false);  // Verhindert, dass die Fenstergröße verändert wird
        window.setTitle("2D Adventure"); // Game-Titel
        
        
        GamePanel gamePanel = new GamePanel(); // GamePanel wird dem Fenster hinzugefügt
        window.add(gamePanel);
        
        window.pack(); // fenster wird der Größe und Layouts vom gamePanel angepasst
        
        
        window.setLocationRelativeTo(null); // Fenster wird in der Mitte ausgegeben
        window.setVisible(true); // Fenster wird angezeigt
        
        gamePanel.setupGame(); // Satrtet SetUp - Objektplatzierung
        gamePanel.startGameThread(); // Startet GameThread
	}

}
