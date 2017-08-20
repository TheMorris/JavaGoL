package gol.gfx;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

public class Setup extends JFrame implements ActionListener{
	private Screen screen;
	private JButton set;
	private JTextField widthInput;
	private JTextField heightInput;
	private JTextField cellInput;
	private Thread gthread;
	private JLabel width;
	private JLabel height;
	private JLabel cells;
	private JPanel widthPanel;
	private JPanel heightPanel;
	private JPanel cellPanel;
	private JPanel buttons;
	private JPanel order;
	
	private int screenWidth;
	private int screenHeight;
	private int amountOfCells;
	
	public Setup(){
		set = new JButton("Start!");
		widthInput = new JTextField("800");
		heightInput = new JTextField("600");
		cellInput = new JTextField("50");
		width = new JLabel("Input width here:");
		height = new JLabel("Input height here:");
		cells = new JLabel("Input amount of cells here:");
		widthPanel = new JPanel();
		heightPanel = new JPanel();
		cellPanel = new JPanel();
		buttons = new JPanel();
		order = new JPanel();
		init();
	}
	
	
	public static void main(String[] args){
		new Setup();
	}
	
	private void init(){
		set.addActionListener(this);
		widthInput.setPreferredSize(new Dimension(100, 25)); 
		heightInput.setPreferredSize(new Dimension(100, 25));
		cellInput.setPreferredSize(new Dimension(100, 25)); 
		widthPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		widthPanel.add(width);
		widthPanel.add(widthInput);
		heightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		heightPanel.add(height);
		heightPanel.add(heightInput);
		cellPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		cellPanel.add(cells);
		cellPanel.add(cellInput);
		order.setLayout(new BoxLayout(order, BoxLayout.Y_AXIS));
		order.add(widthPanel);
		order.add(heightPanel);
		order.add(cellPanel);
		order.add(buttons);
		order.add(set);
		add(order);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == set){
			screenWidth = Integer.parseInt(widthInput.getText());
			screenHeight = Integer.parseInt(heightInput.getText());
			amountOfCells = Integer.parseInt(cellInput.getText());
			screen = new Screen(screenWidth, screenHeight, amountOfCells);
			gthread = new Thread(screen);
			gthread.start();
			this.dispose();
		}
	}
}
