package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.imageio.ImageIO;

import main.GamePanel;

//Verwaltet alle Kacheln (Tiles) im Spiel
public class TileManager {
	
	GamePanel gp;
	public tile[] tile; // Array, das die verschiedenen Kacheltypen speichert
	public int mapTileNum[][]; // Matrix, die die Nummern der Kacheln in der Spielwelt speichert

	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new tile[10]; //Es werden 10 Tiles erstellt
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];// Matrix, die die Nummern der Kacheln in der Spielwelt speichert
		
		// Lädt fie TileBilder und die Map-Daten
		getTileImage();
		loadMap("/maps/world01.txt");  
	}
	// Lädt die Bilder für die verschiedenen Tiles
	public void getTileImage() {
		
		try {
			
			tile[0] = new tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); // Buffered Image vom Gras erstellt
			
			tile[1] = new tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true; // Fügt Collision zu 
			
			tile[2] = new tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			
			tile[3] = new tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			
			
			tile[4] = new tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			
			tile[5] = new tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	// Lädt die Map-daten aus einer Datei
        public void loadMap(String filePath) {
		
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); // Import vom TextFile, Buffered Reader -> Liest den Inhalt vom Textfile
			
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine(); // Liest eine Zeile der Map-Datei
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" "); // Teilt dieZeile in einzelne Zahlen
					
					
					int num = Integer.parseInt(numbers[col]); // Umwandlung vom String zu Integer 
					
					mapTileNum [col][row] = num; // Speichert die Zahl in der Map-Matrix
					col++;
				}
				// Wenn eine Zeile vollständig verarbeitet ist - > Nächte Reihe
				if(col == gp.maxWorldCol) { 
					col = 0;
					row++;
				}
				
			}
			br.close(); // Schließt den Reader
			
			
		}catch(Exception e) {
			
		}
		
		
		
	}
 // Zeichnet die Tiles auf dem Bildschrim
	public void draw(Graphics2D g2) {
		
		int WorldCol = 0;
		int WorldRow = 0;
		
		
		while(WorldCol < gp.maxWorldCol && WorldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[WorldCol][WorldRow]; // Nummer der aktuellen Tile
			
			 // Berechnet die Position der Kachel in der Welt
			
			int worldX = WorldCol * gp.tileSize; 
			int worldY = WorldRow * gp.tileSize;
			
			// Berechnet die Position der Kachel auf dem Bildschirm relativ zur Spielerposition
			int screenX = worldX - gp.player.worldX + gp.player.screenX; 
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			  // Zeichnet nur Kacheln, die im sichtbaren Bereich des Bildschirms liegen
			if(worldX + gp.tileSize> gp.player.worldX - gp.player.screenX &&  
					worldX  - gp.tileSize< gp.player.worldX +gp.player.screenX &&
					worldY  + gp.tileSize> gp.player.worldY -gp.player.screenY &&
					worldY  -gp.tileSize< gp.player.worldY +gp.player.screenY ) 
			{
			g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			}
			WorldCol ++;
			if(WorldCol == gp.maxWorldCol) {
				WorldCol = 0;
				
				WorldRow ++;
				
			}
		}
	}
	
	
}
