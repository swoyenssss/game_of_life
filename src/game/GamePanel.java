package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

import javax.swing.JLabel;
import javax.swing.JPanel;



public class GamePanel extends JPanel implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean[][] gameState;
	private int rows = 150;
	private int columns = 150;
	private int positionX = 0;
	private int positionY = 0;
	private int HEIGHT = 1000;
	private int WIDTH = 1000;
	private int boxHeight = 10;
	private int boxWidth = 10;
	public boolean started;

	private int sleep = 300;
	private int generation = 0;
	private JLabel generationLabel;
	private JLabel aliveLabel;

	private int mouseX;
	private int mouseY;

	private int tempX;
	private int tempY;
	private int tempBoxX;
	private int tempBoxY;
	
	private Color deadCellColor;
	private Color aliveCellColor;
	
	public GamePanel() {	

		this.setSize(HEIGHT,WIDTH);
		this.setVisible(true);
		this.setBackground(new Color(240,240,240));

		deadCellColor = Color.GREEN;
		aliveCellColor = Color.WHITE;
		createGameState();
		//populateRandomCells();
		this.setDoubleBuffered(true);
		createBlinker();
		//createButton();

		started = false;
		//this.add(tickButton);

		this.addMouseWheelListener(e->{
			int steps = e.getWheelRotation();
							
			tempBoxX = getBoxX(e.getX()); 
			tempBoxY = getBoxY(e.getY());
			
			tempX = tempBoxX * boxHeight;
			tempY = tempBoxY * boxWidth;
			
			
			if(steps == 1) {
				if(boxHeight > 5 || boxWidth > 5) {
					boxHeight -= 1;
					boxWidth -= 1;
					
					
					
				}
			}
			else {
				boxHeight += 1;
				boxWidth +=1;
				

			
				
			}
			repaint();
		});

		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				try {
					changeBoxClicked(e.getX(),e.getY());
				}catch(Exception ex) {

				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {			
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		});
		
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				
				positionX -= (mouseX - e.getX());
				positionY -= (mouseY - e.getY());
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {			
			}
			
		});
	}

	public void setDeadCellColor(Color color) {
		this.deadCellColor = color;
	}
	
	public void setAliveColor(Color color) {
		this.aliveCellColor = color;
	}
	
	public void setGenerationLabel(JLabel label) {
		this.generationLabel = label;
	}

	public void setAliveLabel(JLabel label) {
		this.aliveLabel = label;
	}

	private void changeBoxClicked(int x, int y) {
		int tempX = x - positionX;
		int tempY = y - positionY;

		int boxX = tempX/boxWidth;
		int boxY = tempY/boxHeight;

		gameState[boxY][boxX] = (gameState[boxY][boxX] == true) ? false: true;
		repaint();
	}
	
	private int getBoxX(int x) {
		int tempX = x - positionX;
		return tempX/boxWidth;
	}
	
	private int getBoxY(int y) {
		int tempY = y - positionY;
		return tempY/boxWidth;
	}
	
	public void pauseGame() {
		started = false;
	}

	public void startGame() {
		started = true;
	}

	public void clearBoard() {
		generation = 0;
		started = false;
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				gameState[i][j] = false;
			}
		}
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
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
	
	public void createInfiniteGrowth() {
		rows = 500;
		columns = 500;
		positionX = 0;
		positionY = 0;
		HEIGHT = 5000;
		WIDTH = 5000;
		gameState= new boolean[rows][columns];
		clearBoard();
		
		positionX = (int) (-HEIGHT/2.45);
		positionY = (int) (-WIDTH/2.45);
		
		int midRow = rows/2;
		int midCol = columns/2;
		gameState[midRow][midCol] = true;
		gameState[midRow][midCol+2] = true;
		gameState[midRow-1][midCol+2] = true;
		gameState[midRow-2][midCol+4] = true;
		gameState[midRow-3][midCol+4] = true;
		gameState[midRow-4][midCol+4] = true;
		gameState[midRow-3][midCol+6] = true;
		gameState[midRow-4][midCol+6] = true;
		gameState[midRow-4][midCol+7] = true;
		gameState[midRow-5][midCol+6] = true;
		
		
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
				Thread.currentThread();
				Thread.sleep(this.sleep);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tick();
		}
		else {
		}
	}

	public void populateRandomCells() {
		clearBoard();
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
					g2d.fillRect(positionX + 1 + boxWidth * j, positionY + 1 + boxHeight * i, boxWidth - 1 , boxHeight - 1);
				}
				else if(gameState[i][j] == false) {
					g2d.setColor(Color.white);
					g2d.fillRect(positionX + 1 + boxWidth * j, positionY + 1 + boxHeight * i, boxWidth - 1 , boxHeight - 1);
				}
			}


		}
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

	public void createGliders() {
		clearBoard();
		gameState[2][3] = true;
		gameState[3][4] = true;
		gameState[4][2] = true;
		gameState[4][3] = true;
		gameState[4][4] = true;
	}

	public void stopGame() {
		started = false;
		generation = 0;
	}

	@Override
	public void run() {
		repaint();
	}

}
