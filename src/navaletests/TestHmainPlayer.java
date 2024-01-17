package navaletests;
/**
 * /**
 * La classe TestHumainPlayer contient les tests unitaires pour la classe HumanPlayer.
 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

public class TestHmainPlayer  {
	//creation de variables communes à toutes les méthodes
	private Grid grid = new Grid(new Dimension(6,6));
	private HumanPlayer humain = new HumanPlayer(grid, "saoudatou");
	
	
	@Test
	public void testConstructeur() {
		assertEquals("saoudatou",humain.getName());
		assertEquals(grid,humain.getGrid());
		assertNotNull(humain.getGrid());
		assertEquals("la flotte du joueur doit etre vide",true, humain.getFleet().isEmpty());
		assertEquals(6, humain.getGrid().getDimension().getRows());
        assertEquals(6, humain.getGrid().getDimension().getCols());
	}
	
	@Test
	public void testShoot()  throws Exception {
		//test pour une entrée valide
		String entree = "A1";
		
		InputStream in = new ByteArrayInputStream(entree.getBytes());
        System.setIn(in);
        
        Position resultat = humain.shoot();
        Position position = new Position(0,1);
        
        assertEquals(position.getX(),resultat.getX());
        assertEquals(position.getY(),resultat.getY());
        //test pour une entrée invalide
        //on simule plusieurs entrées utilisateur invalides ("A", "1A")  avant de simuler l'entrée valide "B1  
        ByteArrayInputStream in2 = new ByteArrayInputStream("A\n1A\nB1\n".getBytes());
        System.setIn(in2);
        Position position1 = humain.shoot();
        assertEquals(1, position1.getX());
        assertEquals(1, position1.getY());
        
        
		
	}
	
	@Test
	public void testPutShipVisible() {
		
		 Ship ship1 = new Ship(3);
		 Ship ship2 = new Ship(2);
		 humain.addShip(2, 2, ship1, false);
		 humain.addShip(3, 3, ship2,true);
		 
		 humain.getFleet().add(ship1);
		 humain.putShipVisible();
		 assertTrue(ship1.isVisible());
		 assertTrue(ship2.isVisible());
		
	}

}
