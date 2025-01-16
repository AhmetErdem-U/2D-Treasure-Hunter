package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

//GamePanel ist ein JPanel und implementiert Runnable für den Spiel-Thread
public class GamePanel extends JPanel implements Runnable{ 
	
	// Screen Einstellungen
	final int originalTileSize = 16; // 16x 16 - Üblich für 2D Spiele
	final int scale = 3; // 16 x 16 ist sehr klein deshaöb vergößert mit dem Faktor 3 => 16 x 3 = 48 ( 48 pixel x 48 pixel )
	
	public final int tileSize = originalTileSize * scale; // 48 x 48 Größe 
	public final int maxScreenCol = 16; // Maximale Anzahl von Tiles in der Breite
	public final int maxScreenRow = 12; // Maximale Anzahl von Tiles in der Länge
	public final int screenWidth = tileSize * maxScreenCol; // 768 Pixel, Breite des Bildschirms
	public final int screenHeight = tileSize * maxScreenRow; // 576 Pixel, Länge des Bildschirms

	//World Settings
    // Maximale Anzahl von Tiles in der Welt
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	
	
	//FPS
	int FPS = 60; 
	
	//System
	TileManager tileM = new TileManager(this); // TileManager für das zeichnen von Tiles
	KeyHandler keyH = new KeyHandler(); // KeyHandler für Tastatur-Eingaben
	Sound music = new Sound(); // Musik Objekt (Hintergrund)
	Sound se = new Sound(); // Sound Objekt für Sound Effekte
	Thread gameThread; // Spiel-Thread für den Gameloop
	public CollisionChecker cChecker = new CollisionChecker(this); // Kollisionserkennung
	public AssetSetter aSetter = new AssetSetter(this); // Setzt Objekte in die Welt
	public UI ui = new UI(this); // BenutzerOberfläche
	
	// ENtity & Object
	public Player player = new Player(this,keyH); // Spieler-Objekt
	public SuperObject obj[] = new SuperObject[10]; // Array für bis zu 10 sichtbare Objekte

    public GamePanel () {
    	
    	this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Größe des Panels
    	this.setBackground(Color.black); // Hintergrund-Farbe
    	this.setDoubleBuffered(true); // alle Zeichnungen werden in einem Offscreen Painting Buffer gemacht , Hilft die Rendering Performance zu verbessern
        this.addKeyListener(keyH);// KeyListener Hinzufügen
        this.setFocusable(true);// Panel kann Tastatur Eingaben empfangen
    
    }
    // methode um das Spiel vorzubereiten
    public void setupGame() {
    	
    	aSetter.setObject(); // Objekte werden Platziert
    	
    	playMusic(0); // HintergrundMusik starten mit dem Index[0]
    }

 // Startet Spiel-Thread
    public void startGameThread() { 
    	
    	gameThread = new Thread(this);
    	gameThread.start(); // Startet den Thread und führt die Methode run() aus
    }

	@Override
	public void run() { // Gameloop des Spiels
		
		double drawInterval = 1000000000 / FPS; // Zeit zwischen zwei Frames in Nanosekunden, Screen wird alle 1/60 = 0,01666 sekunden nachgezeichnet
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0; //Timer für Zählung
		int drawCount = 0; // Anzahl gezeichneter Frames
		
		
		while(gameThread != null) { // Solange gameThread aktiv ist
			
			
			currentTime = System.nanoTime(); // CurrentTime gecheckt
			delta = delta + (currentTime - lastTime)/drawInterval; 
			timer = (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta >=1) { // 1 = Drawintervall
				update(); //  Update: Update von Informationen ( Character-Bewegung ) 
				repaint(); // Zeichnung: Screen Updates zur Characterbewegung
				delta--; //delta reset
				drawCount++; // Erhöhung des Zählers um 1
			}
			
			if(timer >= 1000000000) { // Jede Sekunde
				System.out.println("FPS:" + drawCount); // FPS ausgeben
				drawCount = 0; // Reset
				timer = 0; // Reset
			}
			
		}
			
		
	}
	//Aktualisierung des Spielzustandes
	public void update() { 
		
		player.update(); // Ruft die Methode Update in der Player Klasse auf
	}
	
	// Zeichnet das Spiel auf das Panel
	public void paintComponent(Graphics g) { 
			 super.paintComponent(g);// Super = aktuelle Klasse = JPanel
	      	 Graphics2D g2 = (Graphics2D)g; // Graphics2D = Erweiterung der Graphics Klasse -> Bessere Kontrolle der Geometrie,Koordinaten,Text,Layout
	      
	      // Tile
	       tileM.draw(g2); //Zuerst werden die Tiles geladen dnach der Charakter
	      
	       //Object
	       for(int i = 0; i < obj.length;i++) { //Zählt von - 9 und checkt on ein Objekt im Array ist 
	    	   if(obj[i] != null) {
	    		   obj[i].draw(g2, this);
	    	   }
	       }
	       
	       //Player
	       player.draw(g2); // Ruft die Methode Draw der Klasse Player auf
	       
	       
	       // UI-Benutzeroberfläche
	       ui.draw(g2);
	  
	       g2.dispose(); // Kontext freigeben
	       	       
	       
	}
	
	public void playMusic(int i) {
		
		music.setFile(i);  // File gewählt und Loop startet
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
		
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
		
	}



 
} 

