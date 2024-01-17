package navaletests;
/**
 * /**
 * La classe TestAbstractPlayer contient les tests unitaires pour la classe AbstractPlayer.
 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

import java.util.ArrayList;

public class TestAbstractPlayer {
	@Test
	public void testConstructeur() {
		Grid grid1 = new Grid(new Dimension(5,6));
		Grid grid2 = new Grid(new Dimension(5,6));
		AbstractPlayer humain = new HumanPlayer(grid1, "humain");
		AbstractPlayer random = new RandomPlayer(grid2,grid1);
		assertEquals("humain", humain.getName());
		assertEquals("Random", random.getName());
		assertEquals("la flotte du joueur humain doit être vide",true, humain.getFleet().isEmpty());
		assertEquals("la flotte du joueur random doit être vide",true, humain.getFleet().isEmpty());
		assertNotNull("la grille du joueur humain ne doit pas etre vide",humain.getGrid());
		assertNotNull("la grille du joueur random ne doit pas etre vide",random.getGrid());
	}
	
	@Test
	public void testAddShip() {
		
		Grid grid1 = new Grid(new Dimension(5,5));
		Grid grid2 = new Grid(new Dimension(5,5));
		AbstractPlayer humain = new HumanPlayer(grid1, "humain");
		AbstractPlayer random = new RandomPlayer(grid2,grid1);
		
		
		//Tests sur le joueur humain
		 // Création d'un navire de taille 3
	    Ship ship = new Ship(3);

	    // Ajout du navire à la grille
	    assertTrue(humain.addShip(2, 2, ship, false));

	    // Vérification que les cellules du navire ont bien été assignées à la grille
	    assertTrue(grid1.getBoard()[2][2].isAssignedShip());
	    assertTrue(grid1.getBoard()[2][3].isAssignedShip());
	    assertTrue(grid1.getBoard()[2][4].isAssignedShip());
	    
	    // Vérification que les cellules adjacentes à celles du navire ne sont pas assignées à un autre navire
	    assertFalse(grid1.getBoard()[1][2].isAssignedShip());
	    assertFalse(grid1.getBoard()[3][2].isAssignedShip());
	    assertFalse(grid1.getBoard()[1][3].isAssignedShip());
	    assertFalse(grid1.getBoard()[3][3].isAssignedShip());
	    assertFalse(grid1.getBoard()[1][4].isAssignedShip());
	    assertFalse(grid1.getBoard()[3][4].isAssignedShip());

	    // Ajout d'un navire qui ne rentre pas complètement sur la grille
	    assertFalse(humain.addShip(3, 3, new Ship(3), true));

	    // Ajout d'un navire qui chevauche un autre navire
	    assertFalse(humain.addShip(1, 2, new Ship(3), true));
	    
	    //Tests sur le joueur Random
	    
	  //Tests sur le joueur humain
		 // Création d'un navire de taille 3
	   

	    // Ajout du navire à la grille
	    assertTrue(random.addShip(2, 2, ship, false));

	    // Vérification que les cellules du navire ont bien été assignées à la grille
	    assertTrue(grid2.getBoard()[2][2].isAssignedShip());
	    assertTrue(grid2.getBoard()[2][3].isAssignedShip());
	    assertTrue(grid2.getBoard()[2][4].isAssignedShip());
	    
	    // Vérification que les cellules adjacentes à celles du navire ne sont pas assignées à un autre navire
	    assertFalse(grid2.getBoard()[1][2].isAssignedShip());
	    assertFalse(grid2.getBoard()[3][2].isAssignedShip());
	    assertFalse(grid2.getBoard()[1][3].isAssignedShip());
	    assertFalse(grid2.getBoard()[3][3].isAssignedShip());
	    assertFalse(grid2.getBoard()[1][4].isAssignedShip());
	    assertFalse(grid2.getBoard()[3][4].isAssignedShip());

	    // Ajout d'un navire qui ne rentre pas complètement sur la grille
	    assertFalse(random.addShip(3, 3, new Ship(3), true));

	    // Ajout d'un navire qui chevauche un autre navire
	    assertFalse(random.addShip(1, 2, new Ship(3), true));
		
	}
	
	@Test
	public void testIsLost() {
		 // Création d'une grille de dimension 10x10
	    Dimension dimension = new Dimension(10, 10);
	    Grid grid1 = new Grid(dimension);
	    Grid grid2 = new Grid(dimension);
	    AbstractPlayer humain = new HumanPlayer(grid1, "humain");
		AbstractPlayer random = new RandomPlayer(grid2,grid1);
	   
	    // Création d'une flotte de navires
	    ArrayList<Ship> fleet = new ArrayList<>();
	    Ship ship1 = new Ship(2);
	    Ship ship2 = new Ship(3);
	    Ship ship3 = new Ship(4);
	    fleet.add(ship1);
	    fleet.add(ship2);
	    fleet.add(ship3);
	    
	    
	    //test pour le joueur humain
	    // Ajout des navires à la grille
	    humain.addShip(0, 0, ship1, true);  // ajouter le navire 1 horizontalement
	    humain.addShip(3, 4, ship2, false); // ajouter le navire 2 verticalement
	    humain.addShip(5, 5, ship3, true);  // ajouter le navire 3 horizontalement

	    // Toutes les cellules de chaque navire ne sont pas touchées, donc la flotte n'est pas coulée
	    assertFalse("le joueur humain n'a pas encore perdu",humain.isLost());

	    // Faire couler tous les navires
	    for (Ship ship : fleet) {
	        for (Cellule cell : ship.getShipCell()) {
	            cell.setState(CellState.HIT);
	        }
	    }

	    // Toutes les cellules de chaque navire sont touchées, donc la flotte est coulée
	    assertTrue("le joueur humain doit perdre",humain.isLost());
	    
	    //test pour le joueur random
	    
	    // Ajout des navires à la grille
	    random.addShip(0, 0, ship1, true);  // ajouter le navire 1 horizontalement
	    random.addShip(3, 4, ship2, false); // ajouter le navire 2 verticalement
	    random.addShip(5, 5, ship3, true);  // ajouter le navire 3 horizontalement

	    // Toutes les cellules de chaque navire ne sont pas touchées, donc la flotte n'est pas coulée
	    assertFalse("le joueur random n'a pas encore perdu",random.isLost());

	    // Faire couler tous les navires
	    for (Ship ship : fleet) {
	        for (Cellule cell : ship.getShipCell()) {
	            cell.setState(CellState.HIT);
	        }
	    }

	    // Toutes les cellules de chaque navire sont touchées, donc la flotte est coulée
	    assertTrue("le random doit perdre",random.isLost());
		
		
		
		
		
		
		
	}

}
