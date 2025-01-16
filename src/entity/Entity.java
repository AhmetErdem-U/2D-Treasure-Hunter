package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {// Klasse für alle nutzbaren Charaktere
	
	// Koordinaten des Charakters in der Welt
	public int worldX, worldY;
	
	// Geschwindigkeit des Charakters
	public int speed;
	
	// Animationen für die Bewegungsrichtung
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	// Aktuelle Bewegungsrichtung des Charakters
	public String direction;

	// Zähler für Animationswechsel
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	// Rechteck für die Kollisionserkennung 
	public Rectangle solidArea; 
	public int solidAreaDefaultX, solidAreaDefaultY;
	
	public boolean collisionOn = false;
}
