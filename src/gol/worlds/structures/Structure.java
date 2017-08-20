package gol.worlds.structures;

import gol.worlds.Worlds;

public abstract class Structure {
	
	protected int structHeight;
	protected int structWidth;
	protected int amountOfCells;
	
	public Structure(Worlds world, int xOrigin, int yOrigin, int width, int height, int cells){
		for(int i = xOrigin; i < structWidth; i++){
			for(int j = yOrigin; j < structHeight; j++){
				
			}
		}
	}
	
	public int amountOfCells(){
		return amountOfCells;
	}
	
	protected void placeAt(int x, int y){
		
	}
}
