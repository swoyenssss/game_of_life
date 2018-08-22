package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
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
	private JComboBox<String> optionBox;
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
		this.setResizable(false);
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


		String[] optionStrings = { "Clear100x100","Clear","Random","Gliders","InfiniteGrowth","PufferTrain","Mario"};

		optionBox = new JComboBox<String>(optionStrings);
		optionBox.addActionListener(e->{
			@SuppressWarnings("unchecked")
			JComboBox<String> cb = (JComboBox<String>)(e.getSource());
			String option = (String)cb.getSelectedItem();

			if(option.equals("Clear")) {
				gamePanel.clearBoard();

				setSleepText(150);
			}
			else if(option.equals("Clear100x100")) {
				gamePanel.createClear100x100();

				setSleepText(150);
			}
			else if(option.equals("Random")) {
				gamePanel.populateRandomCells();

				setSleepText(150);
			}
			else if(option.equals("Gliders")) {
				gamePanel.createGliders();

				setSleepText(50);
			}
			else if(option.equals("InfiniteGrowth")) {
				gamePanel.createInfiniteGrowth();

				setSleepText(50);
			}
			else if(option.equals("PufferTrain")) {
				gamePanel.createPufferTrain();

				setSleepText(50);
			}
			else if(option.equals("Mario")) {
				gamePanel.createMario();
			}
			startButton.setText("Start");
		});	


		startButton = new JButton();
		startButton.setText("Start");
		startButton.addActionListener(e->{

			if(sleepText!=null) {
				try {
					gamePanel.setSleep(Integer.parseInt(sleepText.getText()));
					gamePanel.startGame();
					startButton.setText("Update");
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
			if(gamePanel.started) {
				gamePanel.pauseGame();
				startButton.setText("Resume");
			}

		});

		stopButton = new JButton();
		stopButton.setText("Stop");
		stopButton.addActionListener(e->{
			gamePanel.stopGame();
			startButton.setText("Start");

		});


		sleepLabel = new JLabel("Sleep: ");
		sleepText = new JTextField(20);
		sleepText.setText("150");

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
		
		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new FlowLayout());
		
		JLabel aliveLabel = new JLabel("Alive:");
		JButton aliveColorButton = new JButton();
		aliveColorButton.setBackground(gamePanel.getAliveCellColor());
		
		aliveColorButton.addActionListener(e->{
			Color color = JColorChooser.showDialog(null, "Please select a color", Color.black);
			aliveColorButton.setBackground(color);
			gamePanel.setAliveCellColor(color);
		});
		
		JLabel deadLabel = new JLabel("   Dead:");
		JButton deadColorButton = new JButton();
		deadColorButton.setBackground(gamePanel.getDeadCellColor());
		deadColorButton.addActionListener(e->{
			Color color = JColorChooser.showDialog(null, "Please select a color", Color.black);
			deadColorButton.setBackground(color);
			gamePanel.setDeadCellColor(color);
		});
		
		JLabel borderLabel = new JLabel("   Borders:");
		JCheckBox borderCheckBox = new JCheckBox();
		borderCheckBox.setSelected(true);
		borderCheckBox.addActionListener(e->{
			gamePanel.toggleBorders();
		});
		
		colorPanel.add(aliveLabel);
		colorPanel.add(aliveColorButton);
		
		
		//colorPanel.add(color);
		colorPanel.add(deadLabel);
		colorPanel.add(deadColorButton);

		colorPanel.add(borderLabel);
		colorPanel.add(borderCheckBox);
		
		
		generationPanel.add(generationLabel);
		generationPanel.add(generationNumberLabel);

		JPanel alivePanel = new JPanel();
		alivePanel.setLayout(new FlowLayout());
		JLabel aliveSpeciesLabel = new JLabel("Alive: ");
		aliveSpeciesNumberLabel = new JLabel();

		alivePanel.add(aliveSpeciesLabel);
		alivePanel.add(aliveSpeciesNumberLabel);

		tempPanel.add(generationPanel,BorderLayout.WEST);
		tempPanel.add(alivePanel, BorderLayout.EAST);
		tempPanel.add(colorPanel, BorderLayout.CENTER);
		
		gamePanel.setGenerationLabel(generationNumberLabel);
		gamePanel.setAliveLabel(aliveSpeciesNumberLabel);

		bottomPanel.add(buttonPanel,BorderLayout.SOUTH);
		this.add(tempPanel, BorderLayout.NORTH);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}

	private void setSleepText(int num) {
		if(sleepText.getText()!="") {
			sleepText.setText(num+"");	
		}
	}

	public void start() {
		while(true) {
			gameThread.run();

		}
	}

}
