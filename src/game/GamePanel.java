package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class GamePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean[][] gameState;
	private int rows = 70;
	private int columns = 80;
	private int boxSize = 12;
	private int margin = 10;
	private final int HEIGHT = 1000;
	private final int WIDTH = 1000;
	public boolean started;
	private JButton tickButton = new JButton();
	private JPanel gamePanel;
	private int sleep = 300;
	private int generation = 0;
	private JLabel generationLabel;
	private JLabel aliveLabel;
	
	public GamePanel() {	
		
		this.setSize(HEIGHT,WIDTH);
		this.setVisible(true);
		
		
		createGameState();
		//populateRandomCells();
		this.setDoubleBuffered(true);
		createBlinker();
		//createButton();
		tickButton.setBounds(900,900,50,50);
		
		started = false;
		//this.add(tickButton);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(e.getX() + ", " + e.getY());
				try {
				changeBoxClicked(e.getX(),e.getY());
				}catch(Exception ex) {
					
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
			
		});
	}
	
	public void setGenerationLabel(JLabel label) {
		this.generationLabel = label;
	}
	
	public void setAliveLabel(JLabel label) {
		this.aliveLabel = label;
	}

	private void changeBoxClicked(int x, int y) {
		int tempX = x - margin;
		int tempY = y - margin;
		
		int boxX = tempX/boxSize;
		int boxY = tempY/boxSize;
		
		gameState[boxY][boxX] = (gameState[boxY][boxX] == true) ? false: true;
		repaint();
		
		
	}
	
	public void startGame() {
		started = true;
	}
	
	public void clearBoard() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				gameState[i][j] = false;
			}
		}
	}
	
	public void setSleep(int sleep) {
		this.sleep = sleep;
	}
	
	
	
	private void createButton() {
		gamePanel = new JPanel();
		gamePanel.setLayout(null);
		this.add(gamePanel);

		tickButton = new JButton("tick");
		tickButton.setBounds(250,250, 120,30);
		tickButton.addActionListener(e->{
			tick();
			started = true;

		});

		gamePanel.add(tickButton);
	}
	private void createBlinker() {
		gameState[1][1] = true;
		gameState[1][2] = true;
		gameState[1][3] = true;

	}
	private void createGameState() {
		gameState = new boolean[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				gameState[i][j] = false;
			}
		}
	}

	public void printGameState() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				System.out.print((gameState[i][j]) + ",");
			}
			System.out.println();
		}
		System.out.println();

	}

	public int getAliveStates() {
		int alive = 0;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(gameState[i][j] == true) alive++;
			}
			
		}
		return alive;
	}
	
	public int getGeneration() {
		return this.generation;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		RenderingHints rh = new RenderingHints(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		update(g2d);
	}

	private void update(Graphics2D g2d) {
		//drawGrids(g2d);
		
		paintCells(g2d);
		generationLabel.setText(generation + "");
		aliveLabel.setText(getAliveStates()+ "");
		if(started)	{
			try {
				Thread.sleep(this.sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tick();
		}
	}

	public void populateRandomCells() {
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				if(Math.random() < 0.2) {
					gameState[i][j] = true;
				}
				else {
					gameState[i][j] = false;
				}
			}
		}
	}

	private void paintCells(Graphics2D g2d) {
		for(int i=0;i<rows;i++) {

			for(int j=0;j<columns;j++) {
				if(gameState[i][j] == true) {
					g2d.setColor(Color.green);
					g2d.fillRect(margin + 1 + boxSize * j, margin + 1 + boxSize * i, boxSize - 2, boxSize - 2);
				}
				else if(gameState[i][j] == false) {
					g2d.setColor(Color.white);
					g2d.fillRect(margin + 1 + boxSize * j, margin + 1 + boxSize * i, boxSize - 2, boxSize - 2);
				}
			}


		}
	}

	private int getTotalNeighbors(int row, int col) {
		// Corners
		if((col % columns == columns - 1 || col % columns == 0) && (row % rows == 0 || row % rows==rows-1)) {
			return 3;
		}

		// Sides
		if((col % columns == columns - 1 || col % columns == 0) || (row % rows == 0 || row % rows==rows-1)) {
			return 5;
		}

		// Everything else
		return 8;
	}

	private int getActiveNeighbors(boolean[][] state,int row,int col) {
		int activeNeighbors = 0;

		// Left top corner
		if((col % columns == 0) &&  (row % rows == 0)){
			if(state[row][col + 1] == true) activeNeighbors++;
			if(state[row + 1][col] == true) activeNeighbors++;
			if(state[row + 1][col + 1] == true) activeNeighbors++;
			return activeNeighbors;
		}

		// Left bottom corner
		if((col % columns == 0) &&  (row % rows == rows - 1)){
			if(state[row][col + 1] == true) activeNeighbors++;
			if(state[row - 1][col] == true) activeNeighbors++;
			if(state[row - 1][col + 1] == true) activeNeighbors++;
			return activeNeighbors;
		}

		// Right top corner
		if((col % columns == columns - 1) &&  (row % rows == 0)){
			if(state[row][col - 1] == true) activeNeighbors++;
			if(state[row + 1][col] == true) activeNeighbors++;
			if(state[row + 1][col - 1] == true) activeNeighbors++;
			return activeNeighbors;
		}

		// Right bottom corner
		if((col % columns == columns - 1) &&  (row % rows == rows - 1)){
			if(state[row][col - 1] == true) activeNeighbors++;
			if(state[row - 1][col] == true) activeNeighbors++;
			if(state[row - 1][col - 1] == true) activeNeighbors++;
			return activeNeighbors;
		}

		// Left side
		if(col % columns == 0) {
			if(state[row - 1][col] == true) activeNeighbors++;
			if(state[row - 1][col + 1] == true) activeNeighbors++;
			if(state[row][col + 1] == true) activeNeighbors++;
			if(state[row + 1][col] == true) activeNeighbors++;
			if(state[row + 1][col + 1] == true) activeNeighbors++;

			return activeNeighbors;
		}

		// Right side
		if(col % columns == columns - 1 ) {
			if(state[row - 1][col] == true) activeNeighbors++;
			if(state[row - 1][col - 1] == true) activeNeighbors++;
			if(state[row][col - 1] == true) activeNeighbors++;
			if(state[row + 1][col] == true) activeNeighbors++;
			if(state[row + 1][col - 1] == true) activeNeighbors++;

			return activeNeighbors;
		}

		// Top side
		if(row % rows == 0) {
			if(state[row][col - 1] == true) activeNeighbors++;
			if(state[row][col + 1] == true) activeNeighbors++;
			if(state[row + 1][col - 1] == true) activeNeighbors++;
			if(state[row + 1][col + 1] == true) activeNeighbors++;
			if(state[row + 1][col] == true) activeNeighbors++;

			return activeNeighbors;
		}

		// Bottom side
		if(row % rows == rows - 1) {

			if(state[row][col - 1] == true) activeNeighbors++;
			if(state[row][col + 1] == true) activeNeighbors++;
			if(state[row - 1][col - 1] == true) activeNeighbors++;
			if(state[row - 1][col + 1] == true) activeNeighbors++;
			if(state[row - 1][col] == true) activeNeighbors++;

			return activeNeighbors;

		}

		// For other cells
		if(state[row + 1][col - 1] == true) activeNeighbors++;
		if(state[row + 1][col + 1] == true) activeNeighbors++;
		if(state[row + 1][col] == true) activeNeighbors++;
		if(state[row][col - 1] == true) activeNeighbors++;
		if(state[row][col + 1] == true) activeNeighbors++;
		if(state[row - 1][col - 1] == true) activeNeighbors++;
		if(state[row - 1][col + 1] == true) activeNeighbors++;
		if(state[row - 1][col] == true) activeNeighbors++;

		return activeNeighbors;

	}

	private void applyRules(boolean[][] state,int row,int col) {
		// When the cell is alive
		if(state[row][col] == true) {
			// Under population
			if(getActiveNeighbors(state,row,col) < 2) {
				gameState[row][col] = false;
			}

			// Lives in the next gen
			else if (getActiveNeighbors(state,row,col) >= 2 && getActiveNeighbors(state,row,col) <=3) {
				gameState[row][col] = true;
			}

			// Over population
			else if(getActiveNeighbors(state,row,col) > 3) {
				gameState[row][col] = false;
			}
		}
		// When the cell is dead
		else {
			// Reproduction
			if(getActiveNeighbors(state,row,col) == 3) {
				gameState[row][col] = true;
			}
		}
	}

	private void tick() {
		boolean[][] tempState = copyGameState();
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {

				applyRules(tempState,i,j);
			}
		}
		generation++;
	}

	private boolean[][] copyGameState() {
		boolean[][] tempState = new boolean[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				tempState[i][j] = gameState[i][j];
			}
		}
		return tempState;
	}

	
	public void stopGame() {
		started = false;
		generation = 0;
	}

}
