package model;
import java.util.ArrayList;
import java.util.Random;


/**
 * Classe représentant un joueur aléatoire du jeu de bataille navale.
 * Un joueur possède une grille de jeu, une flotte de bateaux et un nom.
 */

public class RandomPlayer extends AbstractPlayer {

	private Random random;
	private ArrayList<Position> positionsAlreadyShot; // ajouter pour s'assurer qu'une position n'est pas choisi deux fois par le Random dans la methode shoot
	private ArrayList<Position> possiblePoisitions;
	private Grid gridOpponent;

	/**
	 * Constructeur de la classe.
	 * Initialise le joueur avec un nom, une grille de jeu vide et une flotte vide.
	 * @param grid La grille du joueur.
	 */
	public RandomPlayer(Grid grid, Grid gridOpponent) {
		super(grid, "Random");
		this.random = new Random();
		this.gridOpponent = gridOpponent;
		this.positionsAlreadyShot = new ArrayList<Position>();
		this.possiblePoisitions = grid.getAllPositions();
	}

	/**
	 * constructeur sans argument
	 */
	public RandomPlayer(){
		this(null, null);
	}

	//getters and setters
	public Random getRandom() {
		return random;
	}

	public void setRandom(Random random) {
		this.random = random;
	}

	
	/**
	 * Tirer de manière intelligente
	 */
	public Position shoot(){
		// Rechercher les positions les plus probables pour contenir des bateaux
        ArrayList<Position> positionsProbables = new ArrayList<>();
		Position resPos;

        // Parcourir les positions déjà tirées
        for (Position position : positionsAlreadyShot) {
			int x = position.getX();
            int y = position.getY();
			if(gridOpponent.getCellulePosition(position).getState() != CellState.HIT) {
				continue;
			}

			// Positionad adjacentes
			ArrayList<Position> nestedPositions = new ArrayList<Position>();
			nestedPositions.add(new Position(x - 1, y));
			nestedPositions.add(new Position(x + 1, y));
			nestedPositions.add(new Position(x, y - 1));
			nestedPositions.add(new Position(x, y + 1));

            for(Position nestedPos: nestedPositions ) {
				Cellule cell = gridOpponent.getCellulePosition(nestedPos);
				if (cell != null && cell.getState() == CellState.BLANK)
					positionsProbables.add(nestedPos);
			}
        }

        // Si aucune position probable n'est trouvée, tirer aléatoirement
        if (positionsProbables.isEmpty()) {
            resPos = randomShoot();
        } else {
			// Choisir une position probable aléatoirement parmi les positions probables
			int index = random.nextInt(positionsProbables.size());
			resPos = positionsProbables.get(index);
		}

		
		positionsAlreadyShot.add(resPos);
		possiblePoisitions.remove(resPos);
		return resPos;
		

	}


	/**
    * Méthode  qui génère une position aléatoire pour un tir sur la grille de l'adversaire.
    * @return La position aléatoire générée.
    */
	public Position randomShoot() {
		int index = random.nextInt(possiblePoisitions.size());
		Position pos = possiblePoisitions.get(index);
		return pos;
	}
		
	
	/**
    Met à jour la visibilité des navires coulés de la flotte pour les rendre visibles.
    Parcourt chaque navire de la flotte, et si le navire est coulé, définit sa visibilité à true.
    */
	public void putShipVisible(){
		for(Ship ship : this.fleet){
			if(ship.isSank()){
				ship.setVisible(true);
			}
		}

	}
		
	

	
	
	
}
