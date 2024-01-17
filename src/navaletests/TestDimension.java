package navaletests;
/**
 * /**
 * La classe TestDimension contient les tests unitaires pour la classe Dimension.
 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

public class TestDimension {
	
	@Test
	public void testConstructeur() {
		Dimension dimension = new Dimension(5,3);
		assertEquals("le nombre de lignes doit etre 5",5, dimension.getRows());
		assertEquals("le nombre de colonnes doit etre 3",3, dimension.getCols());
	}

}
