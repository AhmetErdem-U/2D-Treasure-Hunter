package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

// Spieler Klasse, Eigenschaften von Entity erweitert
public class Player  extends Entity{
	
	// referenz auf das GamePanel für Spielfunktionen
	GamePanel gp;
	// referenz auf den KeyHandler für Eingaben
	KeyHandler keyH;
	
	public final int screenX; // X Position des Spielers auf dem X-Screen
	public final int screenY; // Y Position des Spielers auf dem Y-Screen
	
	public int hasKey = 0; // Zähler für eingesammelte Schlüssel

	// Konstruktor für den Spieler
	public Player(GamePanel gp, KeyHandler keyH) {
	
	this.gp = gp;
	this.keyH = keyH;
	
	// Spielerposition in der Mitte des Bildschirms
	screenX = gp.screenWidth/2 - (gp.tileSize/2); 
	screenY = gp.screenHeight/2 - (gp.tileSize/2); 
	
	// Festlegung des Kollisionsbereichs ( Im Bauchbereich des Spielers)
	solidArea = new Rectangle(8, 16, 32, 32); 
	solidAreaDefaultX = solidArea.x;
	solidAreaDefaultY = solidArea.y;
	
	// Standardwerte für den Spieler setzen
	setDefaultValues();
	// Bilder für den Spieler Laden
	getPlayerImage();
      
	}
	
	public void setDefaultValues() {
		
		worldX= gp.tileSize * 23; // Startposition X auf der Karte
		worldY= gp.tileSize * 21; // Y
		speed= 5; // Bewegungsgeschwindigkeit
		direction = "down"; // Anfangsrichtung
	}
	public void getPlayerImage() {
		
		try {
			
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	// Methode wird 60 mal die Sekunde aufgerufen
	public void update() { 
		
		// Spieler bewegt sich nur wenn eine Richtungstaste gedrückt wird
		if(keyH.upPressed == true || keyH.downPressed == true ||   
				keyH.leftPressed == true || keyH.rightPressed == true ) {
		
	              if(keyH.upPressed == true) { 
			         direction = "up";
			
		          }
		
                   if(keyH.downPressed == true) {
    	            direction = "down";
    	   
		          }
         
                   if(keyH.leftPressed == true) {
    	            direction = "left";
    	  
		          }
       
                  if(keyH.rightPressed == true) {
    	           direction = "right";
    	     
		  }
       
       // Kollisionserkennung zurücksetzen
       collisionOn = false;
       
       // Überprüfen ob der Spieler auf ein Hindernis trifft
       gp.cChecker.checkTile(this);
       
       // Überprüfen ob ein Objekt berührt wird
       int objIndex = gp.cChecker.checkObject(this, true);
       pickUpObject(objIndex);
       
       // Bewegung wenn keine Kollision vorliegt
       if(collisionOn == false) {
    	   
    	   switch(direction) {
    	   
    	   case "up":
    		   worldY = worldY - speed; 
    		   break;
    	   case "down":
    		   worldY = worldY + speed;
    		   break;
    	   case "left":
    		   worldX = worldX - speed;
    		   break;
    	   case "right":
    		   worldX = worldX + speed;
    		   break;
    		
    	   }
       }
       
       // Animation aktualisieren
       spriteCounter ++;
       if(spriteCounter >14) { // nach 15 Frames ändert sich die Animation
    	   if(spriteNum == 1) {
    		   spriteNum = 2;
    	   }
    	   else if(spriteNum == 2) {
    		   spriteNum =1;
    	   }
    	   spriteCounter = 0;
       }
		}	
	}
	
	// Methode um Objekte aufzusammeln
	public void pickUpObject(int i) {
		if(i != 999) { // Objket wurde berührt
			
			
			String objectName = gp.obj[i].name; // Name des Objektes
			
			switch(objectName) {
			case "key":   // Schlüssel einsammeln
				gp.playSE(1);
				hasKey++; 
				gp.obj[i] = null; // Löscht das Objekt
				gp.ui.showMessage("Schlüssel erhalten !!!");
				
				break;
			case "Door":   // Tür Öffnen
				gp.playSE(3);
				if(hasKey > 0) { // Überprüfen ob ein schlüssel vorhanden ist ( Zum öffen der Tür)
					gp.obj[i] = null; // Tür löschen 
					hasKey--; // SChlüssel ANzahl um 1 verringern
					gp.ui.showMessage("Tür Geöffnet!!");
				} else {
					gp.ui.showMessage("Bruder hol Schlüssel!!");
				}
				
				break;
			case "Boots":  // Geschwindigkeit erhöhen
				gp.playSE(2);
				speed = speed + 2;
				gp.obj[i] = null;
				gp.ui.showMessage("Speed aktiv!!!");
				break;
				
			case "Chest": // Schatz gefunden ( Spiel beenden)
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSE(4);
				break;
				
			}
			
			
		}
		
	}
	
	// Methode zum zeichnen des Spielers auf dem Bildschirm
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		//Bild basierend der Richtung auswählen
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
			
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
			
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
			
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;
			
		}
		
		// Spielerbild zeichnen
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
		
	}
	
}
