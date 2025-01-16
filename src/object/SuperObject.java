package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

//Basisklasse für alle Objekte im Spiel
public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	// Zeichnet das Objekt auf dem Bildschirm
	public void draw(Graphics2D g2, GamePanel gp) {
		
		// Berechnet die Position des Objekts auf dem Bildschirm basierend auf der Spielerposition
		int screenX = worldX - gp.player.worldX + gp.player.screenX; 
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		
		// Überprüft, ob das Objekt im sichtbaren Bereich des Bildschirms liegt
		if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&  
				worldX  - gp.tileSize< gp.player.worldX +gp.player.screenX &&
				worldY  + gp.tileSize> gp.player.worldY -gp.player.screenY &&
				worldY  -gp.tileSize< gp.player.worldY +gp.player.screenY ) 
		{
			  // Zeichnet das Objekt, wenn es sichtbar ist
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
		}
		
	}
	

}
