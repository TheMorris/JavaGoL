package gol.worlds;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Worlds {
	protected int xSize;
	protected int ySize;
		
	protected boolean[][] worldArray;
	
	public Worlds(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = ySize;
		worldArray = new boolean[xSize][ySize];
	}
	
	public void clearWorld(){
		for(int i = 0; i<worldArray.length; i++){
			for(int j = 0; j<worldArray[0].length; j++){
				worldArray[i][j] = false;
			}
		}
	}
	
	/**
	 * This method returns the amount of neighbouring active cells for the cell xy.
	 * <p>
	 * The corresponding cell will have its bounds checked in order to prevent an {@link java.lang.IndexOutOfBoundsException IndexOutOfBoundsException}
	 * @param x - x-coordinate of cell
	 * @param y - y-coordinate of cell
	 * @return - Amount of neighbouring active cells
	 */
	protected int performNeighbourCheck(int x, int y){
		int counter = 0;
		for(int i = x-1; i <= x+1; i++){
			for(int j = y-1; j <= y+1; j++){
				if(i < worldArray.length && j < worldArray[0].length && i >= 0 && j >= 0){
					counter += worldArray[i][j] ? 1 : 0;
				}
			}
		}
		counter += worldArray[x][y] ? -1 : 0;
		return counter;
	}
	
	/**
	 * This method checks for the currently active cells in the world
	 * @return - an ArrayList that holds all currently active cells
	 */
	public ArrayList<Point> activeCells(){
		ArrayList<Point> activeCells = new ArrayList<Point>();
		for(int i = 0; i<worldArray.length; i++){
			for(int j = 0; j<worldArray[0].length; j++){
				if(worldArray[i][j])	{activeCells.add(new Point(i, j));}
			}
		}
		return activeCells;
	}
	
	/**
	 * This is an implementation of Conways basic rules for his "Game Of Life".
	 * These rules can be apllied to any board.
	 * @return - an ArrayList filled with the next generation of cells 
	 */
	public ArrayList<Point> nextActiveCells(){
		ArrayList<Point> activeCells = new ArrayList<Point>();
		for(int i = 0; i<worldArray.length; i++){
			for(int j = 0; j<worldArray[0].length; j++){
				if(performNeighbourCheck(i, j) == 3)	{activeCells.add(new Point(i, j));}
				else if(performNeighbourCheck(i, j) == 2 && worldArray[i][j] == true)	{activeCells.add(new Point(i, j));}
			}
		}
		clearWorld();
		for(Point p: activeCells){
			worldArray[p.x][p.y] = true;
		}
		return activeCells;		
	}		
	
	public void placeSingleCell(int x, int y){
		if(x >= 0 && x < worldArray.length && y >= 0 && x < worldArray[0].length)	{worldArray[x][y] = true;}
	}
	
	public void placePattern(int x, int y){
		int xmin,ymin, xmax, ymax;
		xmin = x;
		ymin = y;
		xmax = x+2;
		ymax = y+6;
		if(xmin >= 0 && ymin >= 0 && xmax < worldArray.length && ymax < worldArray[0].length){
			worldArray[x][y] = true;
			worldArray[x][y+1] = true;
			worldArray[x][y+2] = true;
			worldArray[x][y+4] = true;
			worldArray[x][y+5] = true;
			worldArray[x][y+6] = true;
			worldArray[x+2][y] = true;
			worldArray[x+2][y+1] = true;
			worldArray[x+2][y+2] = true;
			worldArray[x+2][y+4] = true;
			worldArray[x+2][y+5] = true;
			worldArray[x+2][y+6] = true;
			worldArray[x+1][y] = true;
			worldArray[x+1][y+6] = true;
		}
	}
	
	public void placeGlider(int x, int y){
		int xmin,ymin, xmax, ymax;
		xmin = x+1;
		ymin = y;
		xmax = x+1;
		ymax = y+2;
		if(xmin >= 0 && ymin >= 0 && xmax < worldArray.length && ymax < worldArray[0].length){
			worldArray[x][y] = true;
			worldArray[x+1][y+1] = true;
			worldArray[x+1][y+2] = true;
			worldArray[x][y+2] = true;
			worldArray[x-1][y+2] = true;
		}
	}

	public void placeBeacon(int x, int y) {
		int xmin,ymin, xmax, ymax;
		xmin = x;
		ymin = y;
		xmax = x+3;
		ymax = y+3;
		if(xmin >= 0 && ymin >= 0 && xmax < worldArray.length && ymax < worldArray[0].length){
			worldArray[x][y] = true;
			worldArray[x+1][y] = true;
			worldArray[x][y+1] = true;
			worldArray[x+1][y+1] = true;
			worldArray[x+3][y+3] = true;
			worldArray[x+2][y+3] = true;
			worldArray[x+3][y+2] = true;
			worldArray[x+2][y+2] = true;
		}
	}

	public void palceBlinker(int x, int y) {
		int xmin,ymin, xmax, ymax;
		xmin = x;
		ymin = y;
		xmax = x;
		ymax = y+2;
		if(xmin >= 0 && ymin >= 0 && xmax < worldArray.length && ymax < worldArray[0].length){
			worldArray[x][y] = true;
			worldArray[x][y+1] = true;
			worldArray[x][y+2] = true;		
		}
	}

	public void placePulsar(int x, int y) {
		int xmin,ymin, xmax, ymax;
		xmin = x;
		ymin = y;
		xmax = x+12;
		ymax = y+12;
		if(xmin >= 0 && ymin >= 0 && xmax < worldArray.length && ymax < worldArray[0].length){
			worldArray[x][y+2] = true;
			worldArray[x][y+3] = true;
			worldArray[x][y+4] = true;
			worldArray[x+2][y] = true;
			worldArray[x+3][y] = true;
			worldArray[x+4][y] = true;
			worldArray[x+5][y+2] = true;
			worldArray[x+5][y+3] = true;
			worldArray[x+5][y+4] = true;
			worldArray[x+2][y+5] = true;
			worldArray[x+3][y+5] = true;
			worldArray[x+4][y+5] = true;
	
			worldArray[x+7][y+2] = true;
			worldArray[x+7][y+3] = true;
			worldArray[x+7][y+4] = true;
			worldArray[x+8][y+5] = true;
			worldArray[x+9][y+5] = true;
			worldArray[x+10][y+5] = true;
			worldArray[x+12][y+2] = true;
			worldArray[x+12][y+3] = true;
			worldArray[x+12][y+4] = true;
			worldArray[x+8][y] = true;
			worldArray[x+9][y] = true;
			worldArray[x+10][y] = true;
	
			worldArray[x][y+8] = true;
			worldArray[x][y+9] = true;
			worldArray[x][y+10] = true;
			worldArray[x+2][y+7] = true;
			worldArray[x+3][y+7] = true;
			worldArray[x+4][y+7] = true;
			worldArray[x+5][y+8] = true;
			worldArray[x+5][y+9] = true;
			worldArray[x+5][y+10] = true;
			worldArray[x+2][y+12] = true;
			worldArray[x+3][y+12] = true;
			worldArray[x+4][y+12] = true;
			
			worldArray[x+7][y+8] = true;
			worldArray[x+7][y+9] = true;
			worldArray[x+7][y+10] = true;
			worldArray[x+8][y+12] = true;
			worldArray[x+9][y+12] = true;
			worldArray[x+10][y+12] = true;
			worldArray[x+12][y+8] = true;
			worldArray[x+12][y+9] = true;
			worldArray[x+12][y+10] = true;
			worldArray[x+8][y+7] = true;
			worldArray[x+9][y+7] = true;
			worldArray[x+10][y+7] = true;
		}
	}
}

