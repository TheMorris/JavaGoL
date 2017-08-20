package gol.gfx;

import gol.worlds.Worlds;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Screen extends JPanel implements ActionListener, MouseMotionListener, MouseListener, Runnable{
	
	private static final long serialVersionUID = 1L;

	private enum structureList	{SINGLE_CELL, BLINKER, GLIDER, PULSAR, BEACON, PATTERN}

	private JFrame frame = new JFrame();
	private JPanel buttons = new JPanel();
	private JButton nextpop = new JButton("Next Population");
	private JButton clear = new JButton("Clear World");
	private JButton auto = new JButton("Auto");
	private JTextField autoParam = new JTextField("0000"){
		
		@Override
		public String getText(){
			return null;
		}
	};
	private JComboBox<structureList> structures = new JComboBox<structureList>(structureList.values());
			
	private Worlds world;
	
	private ArrayList<Point> activeCells = new ArrayList<Point>();
	
	private final int width;
	private final int height;
	private int tilesizeX;
	private int tilesizeY;
	private final int cellNum;
	private boolean automatic = false;
	private boolean running = false;
	
	public Screen(int width, int height, int cellNum){
		this.width = width;
		this.height = height;
		this.cellNum = cellNum;
		init();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawGrid(g);
		drawCells(g);
		drawIndicator(g);
	}
	

	private void drawGrid(Graphics g){
		g.setColor(Color.BLACK);
		for(int i = 0; i <= cellNum; i++){
			g.drawLine(i*tilesizeX, 0, i*tilesizeX, getHeight());
		}
		for(int j = 0; j <= cellNum; j++){
			g.drawLine(0, j*tilesizeY, width, j*tilesizeY);
		}
	}
	
	private void drawCells(Graphics g){
		for(Point p: activeCells){
			g.fillRect(p.x*tilesizeX, p.y*tilesizeY, tilesizeX, tilesizeY);
		}	
	}
	
	public void drawIndicator(Graphics g){
		g.setColor(Color.BLUE);
		if(getMousePosition() != null)	{g.fillRect((getMousePosition().x/tilesizeX)*tilesizeX, (getMousePosition().y/tilesizeY)*tilesizeY, tilesizeX, tilesizeY);}
	}
	
	private void nextPopulation(){
		activeCells = new ArrayList<Point>(world.nextActiveCells());
	}
	
	private void init(){
		running = true;
		structures.addActionListener(this);
		clear.addActionListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		auto.addActionListener(this);
		autoParam.setEditable(true);
		nextpop.addActionListener(this);
		world = new Worlds(cellNum, cellNum);
		frame.setResizable(false);
		frame.getContentPane().setLayout(new BorderLayout());
		buttons.add(structures);
		buttons.add(nextpop);
		buttons.add(clear);
		buttons.add(auto);
		buttons.add(autoParam);
		buttons.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttons.setPreferredSize(new Dimension(width, 100));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setPreferredSize(new Dimension(width, height-100));
		frame.add(buttons, BorderLayout.SOUTH);
		frame.add(this, BorderLayout.NORTH);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tilesizeX = width/cellNum;
		tilesizeY = height/cellNum;
	}

	private synchronized void setAutomatic(boolean set){
		automatic = set;
	}

	private synchronized boolean isAutomatic(){
		return automatic;
	}
	
	private synchronized boolean isRunning(){
		return running;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == nextpop){
			nextPopulation();
			repaint();
		}
		if(e.getSource() == clear){
			world.clearWorld();
			nextPopulation();
			repaint();
		}
		if(e.getSource() == auto){
			setAutomatic(!isAutomatic());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(structures.getSelectedItem() == structureList.SINGLE_CELL){
			world.placeSingleCell(e.getX()/tilesizeX, e.getY()/tilesizeY);
			activeCells = new ArrayList<Point>(world.activeCells());
			repaint();
		}
		if(structures.getSelectedItem() == structureList.GLIDER){
			world.placeGlider(e.getX()/tilesizeX, e.getY()/tilesizeY);
			activeCells = new ArrayList<Point>(world.activeCells());
			repaint();
		}
		if(structures.getSelectedItem() == structureList.BEACON){
			world.placeBeacon(e.getX()/tilesizeX, e.getY()/tilesizeY);
			activeCells = new ArrayList<Point>(world.activeCells());
			repaint();
		}
		if(structures.getSelectedItem() == structureList.BLINKER){
			world.palceBlinker(e.getX()/tilesizeX, e.getY()/tilesizeY);
			activeCells = new ArrayList<Point>(world.activeCells());
			repaint();
		}
		if(structures.getSelectedItem() == structureList.PULSAR){
			world.placePulsar(e.getX()/tilesizeX, e.getY()/tilesizeY);
			activeCells = new ArrayList<Point>(world.activeCells());
			repaint();
		}
		if(structures.getSelectedItem() == structureList.PATTERN){
			world.placePattern(e.getX()/tilesizeX, e.getY()/tilesizeY);
			activeCells = new ArrayList<Point>(world.activeCells());
			repaint();
		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void run() {
		while(isRunning()){
			while(isAutomatic()){
				nextPopulation();
				repaint();
				int targetStepsPerSeconds;
				if(autoParam.getText() == ""){
					targetStepsPerSeconds = 1;
				}
				else{
					targetStepsPerSeconds = Integer.parseInt(autoParam.getText());
				}
				try {
					Thread.sleep(targetStepsPerSeconds/1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}


}
