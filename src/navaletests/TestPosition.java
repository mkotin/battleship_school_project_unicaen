package navaletests;
/**
 * /**
 * La classe TestPosition contient les tests unitaires pour la classe Position.
 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

public class TestPosition {
	
	
	@Test
	public void testConstructeur() {
		Position position = new Position(0,0);
		assertEquals("la position doit avoir 0 comme x",0,position.getX());
		assertEquals("la position doit avoir 0 comme x",0,position.getY());
	}

}
