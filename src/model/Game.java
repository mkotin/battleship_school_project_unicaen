package model;

import util.AbstractListenableModel;
import util.notifications.GameNotification;

/**
 * cette classe représente le jeu,elle contient toutes les méthodes utilisées pour faire tourner le jeu
 */

public class Game extends AbstractListenableModel {
	private HumanPlayer humainPlayer;
	private RandomPlayer randomPlayer;
	private AbstractPlayer currentPlayer;
	private boolean started = false;

	/**
	 * Constructeur de la classe Game
	 * @param humainPlayer le joueur humain
	 * @param randomPlayer le joueur aléatoire
	 */
	public Game(HumanPlayer humainPlayer, RandomPlayer randomPlayer) {
		this.humainPlayer = humainPlayer;
		this.randomPlayer = randomPlayer;
		this.currentPlayer = humainPlayer;
	}
	//getters and setters

	public HumanPlayer getHumainPlayer() {
		return humainPlayer;
	}

	public void setHumainPlayer(HumanPlayer humainPlayer) {
		this.humainPlayer = humainPlayer;
	}

	public RandomPlayer getRandomPlayer() {
		return randomPlayer;
	}

	public void setRandomPlayer(RandomPlayer randomPlayer) {
		this.randomPlayer = randomPlayer;
	}

	public AbstractPlayer getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(AbstractPlayer currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	/**
	 * cette méthode retourne le gagnant de la partie
	 * @return retourne le joueur qui reussi à couler tous les bateaux de l'adversaire
	 */
	public AbstractPlayer getWinner() {
		if (this.randomPlayer.isLost()) {
            return this.humainPlayer;
        }
		if (this.humainPlayer.isLost()){
			return this.randomPlayer;
		}
		return null;
    }

	/**
	 * vérifie si le jeu est terminé
	 * @return true si getWinner est différent de null et faux sinon
	 */
    public boolean isOver() {
        return (getWinner()!= null);
    }
	
	/**
    * Cette méthode permet à un joueur de tirer sur la grille de son adversaire.
    * Elle prend en paramètre la position à laquelle le joueur souhaite tirer.
    * L'adversaire est choisi en fonction du joueur courant.
    * Si la cellule visée contient un bateau, sa cellule est mise à jour en tant que HIT, sinon elle est mise à jour en tant que MISSED.
    * Enfin, le joueur courant est changé pour passer à l'adversaire.
    @param position la position visée par le joueur
    */
	public void shootGridAdversaire(Position position) {
		AbstractPlayer adversaire = (this.currentPlayer == this.humainPlayer) ? this.randomPlayer : this.humainPlayer;
	
		Cellule celluleAdversaire = adversaire.getGrid().getCellulePosition(position);
	
		if (celluleAdversaire.isAssignedShip()) {
			celluleAdversaire.setState(CellState.HIT);
				
		} else {
			celluleAdversaire.setState(CellState.MISSED);
		}
		
		// Check if ship is sanked and show it
		if(celluleAdversaire.isAssignedShip()) {
			Ship ship = celluleAdversaire.getAssignedShip();
			if(ship.isSank()) {
				ship.setVisible(true);
			}
		}
	
		this.currentPlayer = adversaire;
	}

	/**
	 * Create human fleet randomly
	 */
	public void humanAddShipRandomLy() {
		this.humainPlayer.addShipRandomLy();
		this.humainPlayer.putShipVisible();
		this.fireChangement(GameNotification.HUMAN_FLEET_CREATED);
	}


	/**
	 * Create random fleet
	 */
	public void randomAddShip() {
		this.randomPlayer.addShipRandomLy();
		this.fireChangement(GameNotification.RANDOM_FLEET_CREATED);
	}

	
	/**
	 * Start the game
	 */
	public void startGame() {
		this.randomAddShip();
		this.setStarted(true);
		this.fireChangement(GameNotification.GAME_STARTED);
	}


	/**
    * Joue une partie complète de bataille navale entre les deux joueurs.
    * Tant que la partie n'est pas terminée, le joueur en cours demande une position
    * pour tirer sur la grille de l'adversaire, puis l'autre joueur joue à son tour.
    */
	public void play() {
        while (!isOver()) {
			AbstractPlayer adversaire = (this.currentPlayer == this.humainPlayer) ? this.randomPlayer : this.humainPlayer;
            System.out.println(currentPlayer.getName() + " joue :");
			System.out.println("grille de :"+adversaire.getName());
			adversaire.grid.afficher();
            Position pos = currentPlayer.shoot();
            this.shootGridAdversaire(pos);
			this.getWinner();
        }
        System.out.println("Le joueur " + getWinner().getName() + " a gagné !");	
    }

	
		
}
