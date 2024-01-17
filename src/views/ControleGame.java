package views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.AbstractPlayer;
import model.Game;
import model.HumanPlayer;
import model.Ship;
import util.ListeningModel;
import util.notifications.GameNotification;

/**
 * Main view of the. It represents the game baord.
 * It contains grid and control buttons
 */
public class ControleGame extends JPanel implements ListeningModel {
	/** The class responsible for navigating thru pages */
	protected PageManager pageManager;

	/** GridBagConstraints of the layout */
	protected GridBagConstraints gbc;
	
	/** The game model associated to this vi */
	protected Game game;

	/** Player grid view */
	protected GridView humanGridView;

	/** Random grid view */
	protected GridView randomGridView;

	/** Controls buttons */
	protected JPanel buttonPanel;
	protected JButton randomButton; // randomly add ships for human player
	protected JButton playButton; // start the game

	public ControleGame(PageManager pageManager, Game game) {
		super();
		this.setBackground(Color.WHITE);
		this.pageManager = pageManager;
		
		// Assign associated model and listen to it
		this.game = game;
		this.game.addListening(this);

		// Creating the grids
		humanGridView = new GridView(game.getHumainPlayer().getGrid(), "Moi", true);
		randomGridView = new GridView(game.getRandomPlayer().getGrid(), "Jouer aléatoire", false);

		// Setting the layout
		this.setLayout(new GridBagLayout());

		// creates a constraints object
		this.gbc = new GridBagConstraints();

		this.gbc.fill = GridBagConstraints.HORIZONTAL;

		this.gbc.insets = new Insets(10, 0, 0, 0);

		// column 0
		this.gbc.gridx = 0;

		// row 0
		this.gbc.gridy = 0;

		// increases components width by 150 pixels
		// this.gbc.ipadx = 50;

		// increases components height by 150 pixels
		// this.gbc.ipady = 50;

		// Adding the grids
		this.add(humanGridView, this.gbc);

		// column 1
		this.gbc.gridx = 1;

		// row 0
		this.gbc.gridy = 0;
		this.gbc.weightx = 1;

		this.add(randomGridView, this.gbc);

		// column 0
		this.gbc.gridx = 0;

		// row 1
		this.gbc.gridy = 1;
		this.gbc.insets = new Insets(60, 0, 0, 0);

		// Adding controls buttons
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		randomButton = new JButton("Placer mes navires");
		playButton = new JButton("Jouer");
		buttonPanel.add(randomButton);
		buttonPanel.add(playButton);

		this.add(buttonPanel, this.gbc);

		// Create human fleet
		createHumanFleet();

		// Event binding
		manageEvent();

	}

	/**
	 * Creat human fleet
	 */
	public void createHumanFleet() {
		if(game.isStarted()) {
			new MessageDialog("Vous ne pouvez pas placez vos navires en pleine partie",
			JOptionPane.WARNING_MESSAGE).showMessageDialog();
		} else {
			this.game.humanAddShipRandomLy();
		}
		
	}

	/**
	 * End the game
	 */
	public void endGame() {
		// @todo: Maybe we will do some stuffs in the game model
		if(game.isOver()) {
			// Game is over, who's the winner ?
			AbstractPlayer winner = game.getWinner();
			String endMsg = "";

			// Getting right end msg
			if (winner instanceof HumanPlayer) {
				endMsg = "T'es un champion \uD83E\uDD29. T'as gagné. T'es le roi des mers \uD83D\uDC51 .";
			} else {
				endMsg = "T'as perdu \uD83D\uDE16.";
			}

			// Show end msg and quit
			int result = new MessageDialog(endMsg, JOptionPane.INFORMATION_MESSAGE)
			.showConfirmationMessageDialog("Ok", "Quitter");

			if (result == JOptionPane.OK_OPTION) {
				System.exit(0);
			} else {
				// quit application
				System.exit(0);
			}
		}
		
	}

	/**
	 * Handler for button play(jouer)
	 */
	public void startGameClicked() {
		if(game.isStarted()){
			new MessageDialog("La partie est déjà en cours \uD83D\uDE16", JOptionPane.INFORMATION_MESSAGE).showMessageDialog();
		} else {
			this.game.startGame();
		}
		
	}
	
	/**
	 * Handler for human fleet created notification
	 */
	public void handleHumanFleetCreated() {
		// Assign a view for each ship
		ArrayList<Ship> fleet = this.game.getHumainPlayer().getFleet();

		for (Ship ship : fleet) {

			new ViewShip(ship, this.humanGridView);
			// viewShipsHuman.add(new ViewShip(ship, this.humanGridView, true));
		}
	}

	/**
	 * Handler for random fleet crated notification
	 */
	public void handleRandomFleetCreated() {
		// Assign a view for each ship
		ArrayList<Ship> fleet = this.game.getRandomPlayer().getFleet();

		for (Ship ship : fleet) {
			new ViewShip(ship, this.randomGridView);
		}
	}

	/** 
	 * Handler for game started notfication. Show a start message
	 */
	public void handleGameStarted() {
		new MessageDialog("La partie peu commencer", JOptionPane.INFORMATION_MESSAGE).showMessageDialog();
	}

	/**
	 * Update method for model notification.
	 * Call appropriated handler switch notification
	 */
	@Override
	public void modeleMIsAJour(Object source, Object notification) {
        if (notification instanceof GameNotification) {

			if(notification == GameNotification.HUMAN_FLEET_CREATED) {
				this.handleHumanFleetCreated();
			} else if(notification == GameNotification.RANDOM_FLEET_CREATED) {
				this.handleRandomFleetCreated();
			} else if(notification == GameNotification.GAME_STARTED) {
				this.handleGameStarted();
			}

        } else {
            System.out.println("Unhandled notification for ViewOneCell:  " + notification);
        }
    }

	/**
	 * Event manager
	 */
	public void manageEvent() {
		randomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createHumanFleet ();
			}
		});

		playButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startGameClicked();
			}
		});
	}

}
