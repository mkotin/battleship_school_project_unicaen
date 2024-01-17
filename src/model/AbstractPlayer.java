package model;
import config.Config;
import util.AbstractListenableModel;

/**
 * Classe représentant un joueur du jeu de bataille navale.
 * Un joueur possède une grille de jeu, une flotte de bateaux et un nom.
 */
import java.util.ArrayList;

public abstract class AbstractPlayer extends AbstractListenableModel {
	protected Grid grid;
	protected String name;
	protected ArrayList<Ship> fleet;
	
	/**
	 * Constructeur de la classe.
	 * Initialise le joueur avec un nom, une grille de jeu vide et une flotte vide.
	 * @param name Le nom du joueur.
	 * @param grid la grille du joueur
	 */
	public AbstractPlayer(Grid grid,String name) {
		this.grid = grid;
		this.name = name;
		this.fleet = new ArrayList<>();
	}

	/**
	 * constructeur sans argument
	 */
	public AbstractPlayer(){
		this.fleet = new ArrayList<>();

	}

	//getters and setters

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Ship> getFleet() {
		return fleet;
	}

	public void setFleet(ArrayList<Ship> fleet) {
		this.fleet = fleet;
	}

	/**
	 * Destroy the fleet of the player
	 */
	public void resetFleet() {
		for(Ship ship: fleet) {
			ship.destroy();
		}
		this.fleet = null ;
		this.fleet = new ArrayList<>() ;
	}

	/**
	 * Vérifie si le navire peut être placé à la position donnée sur la grille
	 * @param x la coordonnée x de la position de départ du navire
	 * @param y la coordonnée y de la position de départ du navire
	 * @param ship e navire à placer
	 * @param estVertical estVertical true si le navire doit être placé verticalement, false s'il doit être placé horizontalement
	 * @return  true si le navire peut être placé à la position donnée, false sinon
	 */
	public boolean isPlaceable(int x, int y, Ship ship, boolean estVertical){

		// si on veut ajouter le navire de façon horizontale
		if (!estVertical) {

			if (y + ship.getSize() > this.grid.getDimension().getCols()) {
				return false;
			}
			for (int i = y; i < y + ship.getSize(); i++) {

				// si la cellule n'est pas déjà affectée à un bateau
				if (this.grid.getBoard()[x][i].isAssignedShip()) {
					return false;
				}
			}
		} else {
			if (x + ship.getSize() > this.grid.getDimension().getRows()) {
				return false;
			}
			for (int j = x; j < x + ship.getSize(); j++) {
				if (this.grid.getBoard()[j][y].isAssignedShip()) {
					return false;
				}	
			}
		}
		return true;
	}


	/**
	 * Ajoute un navire à la grille à la position (x, y) dans la direction verticale
	 * ou horizontale en fonction de la valeur de estVertical. Si le navire ne peut pas
	 * être placé à cette position, renvoie false. Sinon, assigne la case de la grille
	 * correspondante au navire, ajoute les cellules du navire à sa liste de cellules
	 * et ajoute le navire à la flotte. Enfin, renvoie true.
	 *il faut noter qu'on place le navire soit vers le bas à partir d'une position ou vers la droite à partir d'une position
	* @param x la coordonnée x de la position de départ du navire
	* @param y la coordonnée y de la position de départ du navire
	* @param ship le navire à ajouter à la grille
	* @param estVertical la direction dans laquelle ajouter le navire (true pour verticale, false pour horizontale)
	* @return true si le navire a été ajouté avec succès, false sinon
	*/
	 public boolean addShip(int x, int y, Ship ship, boolean estVertical) {

		if(!isPlaceable(x, y, ship, estVertical)){
			return false;
		}else{
			if (!estVertical) {
				for (int i = y; i < y + ship.getSize(); i++) {
					this.grid.getBoard()[x][i].setAssignedShip(ship);
					ship.getShipCell().add(this.grid.getBoard()[x][i]); // ajouter la cellule à la liste des cellules du bateau
				}
			} else {
				for (int j = x; j < x + ship.getSize(); j++) {
					this.grid.getBoard()[j][y].setAssignedShip(ship);
					ship.getShipCell().add(this.grid.getBoard()[j][y]);
				}
			}
	 	}
		this.fleet.add(ship);
		return true;				
	 }
			 
		/**
		
		* Vérifie si tous les navires de la flotte du joueur ont été coulés.
		* @return true si tous les navires ont été coulés, false sinon.
		*/
		public boolean isLost(){
			for(Ship ship : this.fleet){
				if(!ship.isSank()){
					return false;
				}
			}
			return true;
		}

	/**
	 * Place un navire aléatoirement sur la grille.
	 * @param nbreNavire : le nombre de navires à ajouter
	 */
	public void addShipRandomLy() {	
		this.resetFleet();

		for(int i =0; i<Config.sizeShip.length;i++){

			for(Position position : this.getGrid().gridPosition()){

				boolean added = this.addShip(position.getX(),position.getY(), new Ship(Config.sizeShip[i]), true); // modifier la taille du navire
				if (!added) {
					added = this.addShip(position.getX(),position.getY(), new Ship(Config.sizeShip[i]), false);
				}
				if (added){
					break;
				}

			}

		}
	}
	/**
	 *  méthode abstraite permettant  au joueur de choisir la position sur laquelle il va effectuer son tir
	 * @return la position choisit par le joueur
	 */
	public abstract Position shoot();
	
	/**
	 * méthode abstraite permettant de rendre un navire visible
	 */
	public abstract void putShipVisible();

	@Override
	public String toString() {
		return "AbstractPlayer [name :" + name + "]";
	}	

}
