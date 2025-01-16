package main;

import entity.Entity;

public class CollisionChecker {
	
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp =gp;
	}
	
	// Methode zur Überprüfung von Kollision zwischen Entity und Tile
	public void checkTile(Entity entity) {
		
		// Ecken des Kollisionsbereichs
		int entityLeftWorldX = entity.worldX + entity.solidArea.x; //  8 
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width; // 16
		int entityTopWorldY = entity.worldY + entity.solidArea.y; // 32
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height; //32
		
		// Berechnung der Spalten und ReihenTile Position
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		// Variablen zur Speicherung der TileNummern, die überprüft werden müssen
		int tileNum1, tileNum2;
		
		 // Überprüfung basierend auf der Bewegungsrichtung des Entities
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
				entity.collisionOn = true;
			}
		
			break;
			
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
				entity.collisionOn = true;
			}
		
			break;
			
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
				entity.collisionOn = true;
			}
		
			break;
			
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
				entity.collisionOn = true;
			}
		
			break;
		}
	
	}
    // Methode zur Überprüfung von Kollisionen zwischen einem Entity und Objekten
	public int checkObject(Entity entity, boolean player) { 
		
		// Rückgabewert, falls keine Kollision festgestellt wird
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			
			if(gp.obj[i] != null) {
				
				// Entitys Bereich Position finden ( x , y ) 
				entity.solidArea.x = entity.worldX + entity.solidArea.x; // Erhöht Wolrd x und Wolrd y
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				
				// Objekt Bereich Position finden (x , y)
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
			    // Überprüfen der Kollision basierend auf der Bewegungsrichtung
				switch(entity.direction) { 
				
				case "up":					
					entity.solidArea.y = entity.solidArea.y - entity.speed;
					// intersect - > Checkt ob doe beiden Rechtecke von entity & Object sich berühren( Kollision)

					if(entity.solidArea.intersects(gp.obj[i].solidArea)) { 
						
						if(gp.obj[i].collision ==true) { //
							entity.collisionOn = true;
							
						}
						
						if ( player == true) { //Berührt ein Spieler das Objekt- Return , Bei NPCS passiert nichts 
							index = i;
						}
					}
					break;
					
				case "down":
					entity.solidArea.y = entity.solidArea.y + entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision ==true) { //
							entity.collisionOn = true;
							
						}
						
						if ( player == true) { //Berührt ein Spieler das Objekt- Return , Bei NPCS passiert nichts 
							index = i;
						}
					}
					break;
					
				case "left":
					entity.solidArea.x = entity.solidArea.x - entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision ==true) { //
							entity.collisionOn = true;
							
						}
						
						if ( player == true) { //Berührt ein Spieler das Objekt- Return , Bei NPCS passiert nichts 
							index = i;
						}
					}
					break;
					
				case "right":
					entity.solidArea.x = entity.solidArea.x + entity.speed;
					if(entity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision ==true) { //
							entity.collisionOn = true;
							
						}
						
						if ( player == true) { //Berührt ein Spieler das Objekt- Return , Bei NPCS passiert nichts 
							index = i;
						}
					}
					break;
					
				}
				 
				// Zurücksetzen der Positionen auf die Standardwerte
				entity.solidArea.x = entity.solidAreaDefaultX; 
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
				
			}
			
		}
		
		return index;
		
	}

}
