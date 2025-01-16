package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

//Klasse für die Benutzeroberfläche (On-Screen-Elemente)
public class UI { 
	
	GamePanel gp;
	Font arial_40, arial_80B;
	BufferedImage keyImage;// Bild für den Schlüssel
	public boolean messageOn = false;// Status, ob eine Nachricht angezeigt wird
	public String message = "";
	int messageCounter = 0;
	public boolean gameFinished = false; // Status, ob das Spiel beendet ist
	
	double playTime;// Spielzeit
	DecimalFormat dFormat = new DecimalFormat("#0.00"); // Formatierung der Spielzeit (2 Nachkommastellen)
	
	public UI(GamePanel gp) {
		this.gp = gp;
		
		arial_40 = new Font("Arial", Font.BOLD, 40); // Schriftarten
		arial_80B = new Font("Arial", Font.BOLD, 80);
		
		// Schlüssel-Bild laden
		OBJ_Key key = new OBJ_Key();
		keyImage = key.image;
	}

	public void showMessage(String text) {
		
		message = text;
		messageOn= true;
	}
public void draw(Graphics2D g2) { // Texte auf dem Bildschrim 
	
	if(gameFinished == true) {
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		String text;
		int textLength;
		int x;
		int y;
		
		// Text:Schatz gefunden
		text = "Du hast den Schatz gefunden!";
		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); // Länge des Textes berechnen
		
		 x = gp.screenWidth/2- textLength/2; // Text wird in der Mitte Angezeigt 
		 y = gp.screenHeight/2 - (gp.tileSize*3); // Y-Position über dem Spieler
		 g2.drawString(text, x, y);
		 
		 
		 // Text: Spielzeit
		 text = "Deine Zeit ist:" + dFormat.format(playTime)+ "!";
			textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); 
			
			 x = gp.screenWidth/2- textLength/2; 
			 y = gp.screenHeight/2 + (gp.tileSize*4); 
			 g2.drawString(text, x, y);
		 
		 // Glückwunsch Anzeige 
		 
		 g2.setFont(arial_80B);
		g2.setColor(Color.green);
		 
		text = "Glückwunsch!";
		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth(); 
		
		 x = gp.screenWidth/2- textLength/2;
		 y = gp.screenHeight/2 + (gp.tileSize*2); 
		 g2.drawString(text, x, y);
		 
		 // Beendet das Spiel-Thread ( Das Spiel)
		 gp.gameThread = null;
		 
		
	}
	// Spiel Läuft
	else {
	
		// Schlüssel Anzeige
	g2.setFont(arial_40);
	g2.setColor(Color.white);
	g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
	g2.drawString("x "+ gp.player.hasKey, 74, 65);
	
	
	// Zeit Anzeige
	playTime  = playTime + 1.0/60.0;
	
	g2.drawString("Zeit:" + dFormat.format(playTime), gp.tileSize * 11, 65);
	
	
	//Nachrichten anzeigen falls aktiv
	if(messageOn == true) {

		g2.setFont(g2.getFont().deriveFont(30));
		g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
		
		messageCounter++;
		
		if(messageCounter > 120) { // 2 sekunden wird der text angezeigt 
			messageCounter = 0;
			messageOn = false; // Text wird gelöscht 
		}
	}
	}
}
}
