package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{ //JPanel ist die Unterklasse : GamePanel Oberklasse alle Eigenschaften von JPanel zugeschrieben

	// Screen Einstellungen
	final int originalTileSize = 16; // 16x 16 - Üblich für 2D Spiele
	final int scale = 3; // 16 x 16 ist sehr klein deshaöb vergößert mit dem Faktor 3 = 16 x 3 = 48 ( 48 pixel x 48 pixel )
	
	public final int tileSize = originalTileSize * scale; // 48 x 48 Größe public -> damit andere Klassen zugreifen könenn
	public final int maxScreenCol = 16; // 16 Breite 
	public final int maxScreenRow = 12; // 12 Länge
	public final int screenWidth = tileSize * maxScreenCol; // 48 x 16 = 768 Pixel, Breite des GameScreens 
	public final int screenHeight = tileSize * maxScreenRow; // 48 x 12 = 576 Pixel, Länge des GameScreens

	//World Settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	
	
	
	//FPS
	int FPS = 60; // FPS auf 60
	
	//System
	TileManager tileM = new TileManager(this); // Integration der Klasse TileManager im GamePanel
	KeyHandler keyH = new KeyHandler(); //Integration der Klasse KeyHandler zum Lesen der Tastatur EIngabe
	Sound music = new Sound();
	Sound se = new Sound();
	Thread gameThread; // Satrt von Thread = Bringt das Programm zum dauerhaften Laufen
	public CollisionChecker cChecker = new CollisionChecker(this); // Integration des Kollisions Checkers
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	
	// ENtity & Object
	public Player player = new Player(this,keyH);
	public SuperObject obj[] = new SuperObject[10]; // 10 - > 10 Slots ( 10 Objekte auf der Map Sichtbar, nach einsammeln eines Objektes kann aber ein neues erzeugt werden ) 

    public GamePanel () {
    	
    	this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Größe der Klasse JPanel
    	this.setBackground(Color.black); // Hintergrund-Farbe
    	this.setDoubleBuffered(true); // alle Zeichnungen werden in einem Offscreen Painting Buffer gemacht , Hilft die Rendering Performance zu verbesern
        this.addKeyListener(keyH);// Fügt die Klasse KeyListener dem Gamepanel hinzu 
        this.setFocusable(true);// GamePanel ist " fokussiert" um tastatreinagebn anzunehmen
    
    }
    
    public void setupGame() {
    	
    	aSetter.setObject();
    	
    	playMusic(0); // 0 -> Array Zahl Von Blueboyadventure
    }

    public void startGameThread() { // methode startet die Speilzeit
    	
    	gameThread = new Thread(this);
    	gameThread.start(); // automatisch wird nun die Run Methode unterhalb abgerufen
    }

	@Override
	public void run() { // Gameloop wird erstellt , Basisi Für das Spiel |||Delta Methode = Loop
		
		double drawInterval = 1000000000 / FPS; // Screen wird alle 1/60 = 0,01666 sekunden nachgezeichnet
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0; //Timer startet bei 0
		int drawCount = 0; // 
		
		
		while(gameThread != null) { // Solange gameThread existiert wird der Prozess wiederholt
			
			
			currentTime = System.nanoTime(); // CurrentTime gecheckt
			delta = delta + (currentTime - lastTime)/drawInterval; // currenTime - last Time -> Wie viel Zeit ist dazwischen vergangen !!, 
			timer = (currentTime - lastTime);
			
			lastTime = currentTime;
			
			if(delta >=1) { // 1 = Drawintervall
				update(); // 1 Update: Update von Informationen ( Character-Bewegung ) 
				repaint(); // 2 Zeichnung: Screen Updates zur Characterbewegung
				delta--; //delta reset
				drawCount++; // Erhöhung des Zählers um 1
			}
			
			if(timer >= 1000000000) { // wenn der Timer 1 Sekunde erreicht -> Zeigt FPS Counter
				System.out.println("FPS:" + drawCount);
				drawCount = 0; // Reset
				timer = 0; // Reset
			}
			
		}
			
		
	}
	
	public void update() { // Update der Character Koordinaten
		
		player.update(); // Ruft die Methode Update in der Player Klasse auf
	}
	
	public void paintComponent(Graphics g) { // StandardMethode um Sachen am JPanel zu zeichnen, Graphics = Klasse mit vielen Funktionen um Objekte zu zeichenn
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
	       
	       
	       // UI
	       ui.draw(g2);
	  
	       g2.dispose(); // Beseitigt den Kontext der Graphic
	       	       
	       
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

