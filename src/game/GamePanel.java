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



@SuppressWarnings("unused")
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

	private boolean borders = true;
	
	private int mouseX;
	private int mouseY;

	@SuppressWarnings("unused")
	private int tempX;
	@SuppressWarnings("unused")
	private int tempY;
	private int tempBoxX;
	private int tempBoxY;

	private Color deadCellColor;
	private Color aliveCellColor;


	public GamePanel() {	

		this.setSize(HEIGHT,WIDTH);
		this.setVisible(true);
		this.setBackground(new Color(240,240,240));

		deadCellColor = Color.WHITE;
		aliveCellColor = Color.GREEN;
		createGameState();
		//populateRandomCells();
		this.setDoubleBuffered(true);
		createClear100x100();
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
				boxWidth += 1;


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
	
	public void toggleBorders() {
		borders = !borders;
	}

	public void setDeadCellColor(Color color) {
		this.deadCellColor = color;
	}

	public void setAliveCellColor(Color color) {
		this.aliveCellColor = color;
	}
	
	public Color getDeadCellColor() {
		return this.deadCellColor;
	}
	
	public Color getAliveCellColor() {
		return this.aliveCellColor;
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

	private void createGameState() {
		gameState = new boolean[rows][columns];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				gameState[i][j] = false;
			}
		}
	}

	private void createBlinker() {
		rows = 150;
		columns = 150;
		positionX = 0;
		positionY = 0;
		HEIGHT = 1000;
		WIDTH = 1000;
		boxHeight = 10;
		boxWidth = 10;

		gameState= new boolean[rows][columns];
		clearBoard();

		gameState[1][1] = true;
		gameState[1][2] = true;
		gameState[1][3] = true;
	}

	public void createGliders() {
		rows = 150;
		columns = 150;
		positionX = 0;
		positionY = 0;
		HEIGHT = 1000;
		WIDTH = 1000;
		boxHeight = 10;
		boxWidth = 10;

		gameState= new boolean[rows][columns];
		clearBoard();

		gameState[2][3] = true;
		gameState[3][4] = true;
		gameState[4][2] = true;
		gameState[4][3] = true;
		gameState[4][4] = true;
	}

	public void createInfiniteGrowth() {
		rows = 500;
		columns = 500;
		positionX = 0;
		positionY = 0;
		HEIGHT = 5000;
		WIDTH = 5000;
		boxHeight = 10;
		boxWidth = 10;

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

	public void createClear100x100() {
		rows = 100;
		columns = 100;
		
		HEIGHT = 1000;
		WIDTH = 1000;
		
		positionX = 0;
		positionY = 0;
		
		boxHeight = 10;
		boxWidth = 10;

		gameState= new boolean[rows][columns];
		clearBoard();
		
		
	}
	
	public void createMario() {
		createClear100x100();
		
		int startRow = rows/2 - 15;
		int startCol = columns/2 - 15;
		
		gameState[startRow][startCol+4] = true;
		gameState[startRow][startCol+5] = true;
		gameState[startRow][startCol+6] = true;
		gameState[startRow][startCol+7] = true;
		gameState[startRow][startCol+8] = true;
		gameState[startRow][startCol+9] = true;
		
		gameState[startRow+1][startCol+3] = true;
		gameState[startRow+1][startCol+4] = true;
		gameState[startRow+1][startCol+5] = true;
		gameState[startRow+1][startCol+6] = true;
		gameState[startRow+1][startCol+7] = true;
		gameState[startRow+1][startCol+8] = true;
		gameState[startRow+1][startCol+9] = true;
		gameState[startRow+1][startCol+10] = true;
		gameState[startRow+1][startCol+11] = true;
		
		gameState[startRow+2][startCol+3] = true;
		gameState[startRow+2][startCol+4] = true;
		gameState[startRow+2][startCol+5] = true;
		gameState[startRow+2][startCol+6] = true;
		gameState[startRow+2][startCol+7] = true;
		gameState[startRow+2][startCol+8] = true;
		gameState[startRow+2][startCol+9] = true;
		
		gameState[startRow+3][startCol+2] = true;
		gameState[startRow+3][startCol+3] = true;
		gameState[startRow+3][startCol+4] = true;
		gameState[startRow+3][startCol+5] = true;
		gameState[startRow+3][startCol+6] = true;
		gameState[startRow+3][startCol+8] = true;
		gameState[startRow+3][startCol+10] = true;
		gameState[startRow+3][startCol+11] = true;
		
		gameState[startRow+4][startCol+2] = true;
		gameState[startRow+4][startCol+4] = true;
		gameState[startRow+4][startCol+8] = true;
		gameState[startRow+4][startCol+11] = true;
	
		gameState[startRow+5][startCol+2] = true;
		gameState[startRow+5][startCol+4] = true;
		gameState[startRow+5][startCol+5] = true;
		gameState[startRow+5][startCol+6] = true;
		gameState[startRow+5][startCol+7] = true;
		gameState[startRow+5][startCol+12] = true;
		
		
		gameState[startRow+6][startCol+2] = true;
		gameState[startRow+6][startCol+3] = true;
		gameState[startRow+6][startCol+9] = true;
		gameState[startRow+6][startCol+10] = true;
		gameState[startRow+6][startCol+11] = true;
		
		gameState[startRow+7][startCol+4] = true;
		gameState[startRow+7][startCol+5] = true;
		gameState[startRow+7][startCol+6] = true;
		gameState[startRow+7][startCol+7] = true;
		gameState[startRow+7][startCol+8] = true;
		gameState[startRow+7][startCol+9] = true;
		gameState[startRow+7][startCol+10] = true;

		gameState[startRow+8][startCol+3] = true;
		gameState[startRow+8][startCol+5] = true;
		gameState[startRow+8][startCol+10] = true;
		gameState[startRow+8][startCol+11] = true;
				
		gameState[startRow+9][startCol+2] = true;
		gameState[startRow+9][startCol+3] = true;
		gameState[startRow+9][startCol+5] = true;
		gameState[startRow+9][startCol+9] = true;
		gameState[startRow+9][startCol+11] = true;
		gameState[startRow+9][startCol+12] = true;
		
		
		gameState[startRow+10][startCol+1] = true;
		gameState[startRow+10][startCol+4] = true;
		gameState[startRow+10][startCol+5] = true;
		gameState[startRow+10][startCol+6] = true;
		gameState[startRow+10][startCol+8] = true;
		gameState[startRow+10][startCol+9] = true;
		gameState[startRow+10][startCol+10] = true;
		gameState[startRow+10][startCol+13] = true;
		
		gameState[startRow+11][startCol+1] = true;
		gameState[startRow+11][startCol+5] = true;
		gameState[startRow+11][startCol+6] = true;
		gameState[startRow+11][startCol+7] = true;
		gameState[startRow+11][startCol+8] = true;
		gameState[startRow+11][startCol+9] = true;
		gameState[startRow+11][startCol+13] = true;
		
		gameState[startRow+12][startCol+1] = true;
		gameState[startRow+12][startCol+4] = true;
		gameState[startRow+12][startCol+5] = true;
		gameState[startRow+12][startCol+6] = true;
		gameState[startRow+12][startCol+8] = true;
		gameState[startRow+12][startCol+9] = true;
		gameState[startRow+12][startCol+10] = true;
		gameState[startRow+12][startCol+13] = true;
		
		gameState[startRow+13][startCol+1] = true;
		gameState[startRow+13][startCol+2] = true;
		gameState[startRow+13][startCol+3] = true;
		gameState[startRow+13][startCol+6] = true;
		gameState[startRow+13][startCol+7] = true;
		gameState[startRow+13][startCol+8] = true;
		gameState[startRow+13][startCol+11] = true;
		gameState[startRow+13][startCol+12] = true;
		gameState[startRow+13][startCol+13] = true;
		
		gameState[startRow+14][startCol+3] = true;
		gameState[startRow+14][startCol+6] = true;
		gameState[startRow+14][startCol+8] = true;
		gameState[startRow+14][startCol+11] = true;
		
		gameState[startRow+15][startCol+2] = true;
		gameState[startRow+15][startCol+5] = true;
		gameState[startRow+15][startCol+9] = true;
		gameState[startRow+15][startCol+12] = true;

		gameState[startRow+16][startCol+1] = true;
		gameState[startRow+16][startCol+2] = true;
		gameState[startRow+16][startCol+3] = true;
		gameState[startRow+16][startCol+4] = true;
		gameState[startRow+16][startCol+5] = true;
		gameState[startRow+16][startCol+9] = true;
		gameState[startRow+16][startCol+10] = true;
		gameState[startRow+16][startCol+11] = true;
		gameState[startRow+16][startCol+12] = true;
		gameState[startRow+16][startCol+13] = true;
		
	}

	public void createPufferTrain() {
		rows = 100;
		columns = 1000;
		
		HEIGHT = 1000;
		WIDTH = 10000;
		
		positionX = 0;
		positionY = HEIGHT / 4;
		
		boxHeight = 4;
		boxWidth = 4;

		gameState= new boolean[rows][columns];
		clearBoard();
		
		int startRow = rows/2 - 27/2;
		int startCol = 10;
		
		gameState[startRow][startCol+5] = true;
		gameState[startRow+1][startCol+6] = true;
		gameState[startRow+2][startCol+6] = true;
		gameState[startRow+2][startCol] = true;
		gameState[startRow+3][startCol+6] = true;
		gameState[startRow+3][startCol+5] = true;
		gameState[startRow+3][startCol+4] = true;
		gameState[startRow+3][startCol+3] = true;
		gameState[startRow+3][startCol+2] = true;
		gameState[startRow+3][startCol+1] = true;
		
		gameState[startRow+6][startCol] = true;
		gameState[startRow+6][startCol+1] = true;
		gameState[startRow+6][startCol+2] = true;
		gameState[startRow+7][startCol] = true;
		gameState[startRow+7][startCol+1] = true;
		
		gameState[startRow+8][startCol+4] = true;
		gameState[startRow+9][startCol+4] = true;
		gameState[startRow+9][startCol+5] = true;		
		gameState[startRow+10][startCol+5] = true;
		gameState[startRow+10][startCol+6] = true;
		gameState[startRow+11][startCol+4] = true;
		gameState[startRow+11][startCol+5] = true;
		
		gameState[startRow+15][startCol+4] = true;
		gameState[startRow+15][startCol+5] = true;
		gameState[startRow+16][startCol+5] = true;
		gameState[startRow+16][startCol+6] = true;
		gameState[startRow+17][startCol+4] = true;
		gameState[startRow+17][startCol+5] = true;
		gameState[startRow+18][startCol+4] = true;
		
		gameState[startRow+19][startCol] = true;
		gameState[startRow+19][startCol+1] = true;
		gameState[startRow+20][startCol] = true;
		gameState[startRow+20][startCol+1] = true;
		gameState[startRow+20][startCol+2] = true;
		
		gameState[startRow+23][startCol+1] = true;
		gameState[startRow+23][startCol+2] = true;
		gameState[startRow+23][startCol+3] = true;
		gameState[startRow+23][startCol+4] = true;
		gameState[startRow+23][startCol+5] = true;
		gameState[startRow+23][startCol+6] = true;
		gameState[startRow+24][startCol+6] = true;
		gameState[startRow+24][startCol] = true;
		gameState[startRow+25][startCol+6] = true;
		gameState[startRow+26][startCol+5] = true;
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
		if(started )	{

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
				if(borders) {
				if(gameState[i][j] == true) {
					g2d.setColor(aliveCellColor);
					g2d.fillRect(positionX + 1 + boxWidth * j, positionY + 1 + boxHeight * i, boxWidth - 1 , boxHeight - 1);
				}
				else if(gameState[i][j] == false) {
					g2d.setColor(deadCellColor);
					g2d.fillRect(positionX + 1 + boxWidth * j, positionY + 1 + boxHeight * i, boxWidth - 1 , boxHeight - 1);
				}
				}
				else{
					if(gameState[i][j] == true) {
						g2d.setColor(aliveCellColor);
						g2d.fillRect(positionX + 1 + boxWidth * j, positionY + 1 + boxHeight * i, boxWidth  , boxHeight );
					}
					else if(gameState[i][j] == false) {
						g2d.setColor(deadCellColor);
						g2d.fillRect(positionX + 1 + boxWidth * j, positionY + 1 + boxHeight * i, boxWidth  , boxHeight);
					}
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

	public void stopGame() {
		started = false;
		generation = 0;
	}

	@Override
	public void run() {
		repaint();
	}

}
