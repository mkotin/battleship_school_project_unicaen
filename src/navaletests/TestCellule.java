package navaletests;
/**
 * /**
 * La classe TestCellule contient les tests unitaires pour la classe Cellule.
 * @author sow224
 *
 */



import static org.junit.Assert.*;

import org.junit.Test;

import model.*;


public class TestCellule {
	
	@Test
	public void testConstructeur() {
		Cellule cellule1 = new Cellule();
		Cellule cellule2 = new Cellule(new Position(1,2));
		assertEquals("l'etat de la cellule1 doit etre BLANK",CellState.BLANK, cellule1.getState());
		assertEquals("l'etat de la cellule2 doit etre BLANK",CellState.BLANK, cellule2.getState());
		assertEquals("la cellule 2 ne doit pas etre assigné à un ship",false, cellule2.isAssignedShip());
		assertEquals("la cellule 1 ne doit pas etre assigné à un ship",false, cellule1.isAssignedShip());
		assertEquals("la cellule2 doit avoir un  x égal à 1",1,cellule2.getPosition().getX());
		assertEquals("la cellule2 doit avoir un  y égal à 2",2,cellule2.getPosition().getY());
				
	}

}
