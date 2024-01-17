package navaletests;
/**
 * /**
 * La classe TestRandomPlayer contient les tests unitaires pour la classe RandomPlayer.
 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.InputMismatchException;

public class TestRandomPlayer {
	
	//creation de variables commune à toutes les méthodes
	private Grid grid = new Grid(new Dimension(20,20));
	private Grid gridHmain = new Grid(new Dimension(20,20));
	private RandomPlayer random = new RandomPlayer(grid,gridHmain);
	
	@Test
	public void testConstructeur() {
		assertEquals("Random",random.getName());
		assertEquals(grid,random.getGrid());
		assertNotNull(random.getGrid());
		assertEquals("flot du joueur doit etre vide au debut",true, random.getFleet().isEmpty());
		assertEquals(20, random.getGrid().getDimension().getRows());
        assertEquals(20, random.getGrid().getDimension().getCols());
	}
	
	
	
	@Test
	public void testAddShipRandomly() {
		//on verifie que la méthode a bien ajouté 5 navires
		    random.addShipRandomLy();
		    int nbreNavire = random.getFleet().size();
		    assertTrue("le nbre de navire doit etre egale à 5",nbreNavire == 5);	
	}

}
