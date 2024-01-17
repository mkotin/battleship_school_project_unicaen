package navaletests;
/**
 * /**
 * La classe TestShip contient les tests unitaires pour la classe Ship.

 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;



import java.util.ArrayList;



public class TestShip {
	
	@Test
	public void testConstructeur() {
		Ship ship = new Ship(10);
		assertNotNull(ship);
		assertEquals("la taille du navire doit etre 10",10,ship.getSize());//verifie que la taille du navire est bien initialisé à 10
		assertEquals("la visibilité du navire doit être à false",false, ship.isVisible()); // verifie que isvisible est bien initialisé à false
		assertEquals("l'ArrayList shipCelle doit etre vide au debut",true, ship.getShipCell().isEmpty());// verifie que la liste est bien une liste vide
	}
	
	
	@Test
	public void testIsSank() {
		Ship ship = new Ship(10);
		ArrayList<Cellule> cellules = new ArrayList<>();
		ship.setShipCell(cellules);
		//on met létat de toutes les cellules à Hit pour tester le retour de la méthode
		for(Cellule cellule : ship.getShipCell()) {
			cellule.setState(CellState.HIT);
		}
		boolean verif = ship.isSank();
		assertEquals(true,verif );
		
		// on met l'etat d'aumoins une  cellule à missed ou Blank
		Cellule cellule1 = new Cellule();
		cellule1.setAssignedShip(ship);
		cellule1.setState(CellState.MISSED);
		ship.getShipCell().add(cellule1);
		//une autre cellule qui a BLANK comme etat
		Cellule cellule2 = new Cellule();
		cellule2.setAssignedShip(ship);
		cellule2.setState(CellState.BLANK);
		ship.getShipCell().add(cellule2);
		boolean verif2 = ship.isSank();
		assertEquals(false,verif2 );
		
	}

}
