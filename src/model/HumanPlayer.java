package model;
import java.util.InputMismatchException;
import java.util.Scanner;
import config.Config;

/**
 * Classe représentant un joueur humain du jeu de bataille navale.
 */
public class HumanPlayer  extends AbstractPlayer{
	
	/**
	 * Constructeur de la classe.
	 * Initialise le joueur avec un nom, une grille de jeu vide et une flotte vide.
	 * @param name Le nom du joueur.
	 * @param grid la grille du joueur
	 */
	public HumanPlayer(Grid grid,String name) {
		super(grid,name);
	}


	

/**
 * constructeur sans argument
 */
public HumanPlayer() {
	super();
}





/**
 * Demande à l'utilisateur de choisir une position sur le plateau
 * et retourne la position choisie sous forme d'objet Position.
 * La méthode s'assure que la position choisie est valide (de forme A1, A2, ..., J10),
 * et lève une exception si la position choisie n'est pas valide.
 * @return l'objet Position correspondant à la position choisie par l'utilisateur
 * @throws IllegalArgumentException si la position choisie n'est pas valide
 * @throws StringIndexOutOfBoundsException si les coordonnées ne sont pas complètes
 */
	public Position shoot() {
		Scanner scanner = new Scanner(System.in);
		String entree = "";
		int x = 0, y = 0;

	
		boolean entreeValide = false;
		while (!entreeValide) {
			try {
				System.out.println("Choisissez une case (ex : A1) : ");
				entree = scanner.nextLine();
				if (entree.length() == 2 && Character.isLetter(entree.charAt(0)) 
				&& Character.isDigit(entree.charAt(1)) && Character.isUpperCase(entree.charAt(0)) && (entree.charAt(0) - 'A'<= 'J' - 'A')) {
					
					x = entree.charAt(0) - 'A';
					y = Character.getNumericValue(entree.charAt(1));
					entreeValide = true;
				}else if (entree.length()!=2) {
					throw new StringIndexOutOfBoundsException("vous devez entrer une case (ex : A1)");
				}else if (Character.isDigit(entree.charAt(0))) {
					throw new IllegalArgumentException("La première valeur doit être une lettre (A...J)");
				} else if (Character.isLetter(entree.charAt(1))) {
					throw new IllegalArgumentException("La deuxième valeur doit être un entier (1..9)");
				} else {
					System.out.println("entrée invalide");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			} catch( StringIndexOutOfBoundsException e){
				System.out.println(e.getMessage());

			}

		}
		return new Position(x, y);
	}

	


	/**
	 * met tous les navires du joueur en mode visible
	 */
	public void putShipVisible(){
		for(Ship ship : this.fleet){
			ship.setVisible(true);	
		}
	}
	
	/**
	 * cette méthode permet au joueur humain d'ajouter ses navires
	 * @throws InputMismatchException si l'entrée n'est pas numérique
	 */
	
	public void humainAddShip() {
		Scanner entree = new Scanner(System.in);
		String carac = "";
	
		for (int i = 0; i < Config.sizeShip.length; i++) {
			boolean added = false;
			while (!added) {
				try {
					System.out.print("choisissez une coordonnée X  pour votre navire " + (i+1) + " ");
					int x = entree.nextInt();
					System.out.print("choisissez une coordonnée Y  pour votre navire  "+ (i+1) + " ");
					int y = entree.nextInt();
					entree.nextLine(); // consommer la ligne vide restante
	
					do {
						System.out.print("votre navire est verticale ou horizontale(ve/ho)");
						carac = entree.nextLine();
					} while (!carac.equals("ve") && !carac.equals("ho"));
	
					if (carac.equals("ve")) {
						added = this.addShip(x, y, new Ship(Config.sizeShip[i], true), true);
					} else {
						added = this.addShip(x, y, new Ship(Config.sizeShip[i], true), false);
					}
	
					if (added) {
						System.out.println("ajouté\n");
						this.grid.afficher();
					} else {
						System.out.println("non ajouté\n");
					}
				} catch (InputMismatchException e) {
					System.out.println("Erreur: Entrée non valide. Veuillez entrer une valeur numérique.");
					entree.nextLine(); // consommer la ligne erronée restante
				} catch (IllegalArgumentException e) {
					System.out.println("Erreur: Entrée non valide. ");
				}
			}
		}		
	}

}

