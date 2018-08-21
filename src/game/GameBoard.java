package game;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class GameBoard extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GamePanel gamePanel;
	private Runnable gameThread;
	private JButton startButton;
	private JButton pauseButton;
	private JButton stopButton;
	private JPanel buttonPanel;
	private JLabel sleepLabel;
	private JTextField sleepText; 
	private JComboBox optionBox;
	private JLabel generationNumberLabel;
	private JLabel aliveSpeciesNumberLabel;
	
	public GameBoard() {
		super("Game of life");
		setSize(1000,1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		gamePanel = new GamePanel();
		gameThread = new Thread((gamePanel));
		this.setLayout(new BorderLayout());
		this.add(gamePanel);
		
		createButtonPanel();
		this.setVisible(true);
		
	}
	
	private void createButtonPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new BorderLayout());
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		
		
		String[] optionStrings = {"Clear","Random","Gliders","InfiniteGrowth"};
		
		optionBox = new JComboBox<String>(optionStrings);
		optionBox.addActionListener(e->{
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>)(e.getSource());
			String option = (String)cb.getSelectedItem();
			
			if(option.equals("Clear")) {
				gamePanel.clearBoard();
			}
			else if(option.equals("Random")) {
				gamePanel.populateRandomCells();
			}
			else if(option.equals("Gliders")) {
				gamePanel.createGliders();
			}
			else if(option.equals("InfiniteGrowth")) {
				gamePanel.createInfiniteGrowth();
			}
		});	
	
		
		startButton = new JButton();
		startButton.setText("Start");
		startButton.addActionListener(e->{
			
			if(sleepText!=null) {
				try {
					gamePanel.setSleep(Integer.parseInt(sleepText.getText()));
					gamePanel.startGame();
				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(this, "Please enter numbers");
				}
			}
		});
		
		pauseButton = new JButton();
		pauseButton.setText("Pause");
		pauseButton.addActionListener(e->{
			gamePanel.pauseGame();
		});
		
		
		stopButton = new JButton();
		stopButton.setText("Stop");
		stopButton.addActionListener(e->{
			gamePanel.stopGame();
		});
		
		
		sleepLabel = new JLabel("Sleep: ");
		sleepText = new JTextField(20);
		
		buttonPanel.add(optionBox);
		buttonPanel.add(sleepLabel);
		buttonPanel.add(sleepText);
		buttonPanel.add(startButton);
		buttonPanel.add(pauseButton);
		buttonPanel.add(stopButton);
		
		JPanel generationPanel = new JPanel();
		generationPanel.setLayout(new FlowLayout());
		JLabel generationLabel = new JLabel("Generation");
		generationNumberLabel = new JLabel();
		
		generationPanel.add(generationLabel);
		generationPanel.add(generationNumberLabel);
		
		JPanel alivePanel = new JPanel();
		alivePanel.setLayout(new FlowLayout());
		JLabel aliveSpeciesLabel = new JLabel("alive");
		aliveSpeciesNumberLabel = new JLabel();
		
		alivePanel.add(aliveSpeciesLabel);
		alivePanel.add(aliveSpeciesNumberLabel);
		
		tempPanel.add(generationPanel,BorderLayout.WEST);
		tempPanel.add(alivePanel, BorderLayout.EAST);
		
		gamePanel.setGenerationLabel(generationNumberLabel);
		gamePanel.setAliveLabel(aliveSpeciesNumberLabel);
		
		bottomPanel.add(buttonPanel,BorderLayout.SOUTH);
		this.add(tempPanel, BorderLayout.NORTH);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}
	
	public void start() {
		while(true) {
			gameThread.run();
			
		}
	}

}
