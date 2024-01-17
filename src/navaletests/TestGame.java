package navaletests;
/**
 * /**
 * La classe TestGame contient les tests unitaires pour la classe Game.
 * @author sow224
 *
 */




import static org.junit.Assert.*;

import org.junit.Test;

import model.*;
import java.util.Scanner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;



public class TestGame {
	
	private  Grid gridHumain = new Grid(new Dimension(5,5));
	private  Grid grid2 = new Grid(new Dimension(5,5));
	private  HumanPlayer humain = new HumanPlayer(gridHumain, "Joueur humain");
    private  RandomPlayer random = new RandomPlayer(grid2,gridHumain);
    private  Game game = new Game(humain, random);
	
	@Test
	public void testConstructeur() {
		Grid grid = new Grid(new Dimension(7,7));
		HumanPlayer humain = new HumanPlayer(grid, "Alseiny");
		RandomPlayer random = new RandomPlayer(grid,gridHumain);
		Game game = new Game(humain,random);
		assertEquals("le joueur humain est le joueur courant au debut",humain.getName(), game.getCurrentPlayer().getName());
		assertEquals("le joueur humain doit etre celui passé en paramètre",random.getName(), game.getRandomPlayer().getName());
		assertEquals("le joueur random doit etre celui passé en paramètre",humain.getName(), game.getHumainPlayer().getName());
		
	}
	
	@Test
	public void testGetWinner() {
		
	       //deux joueurs ne peuvent pas gagner en même temps donc on teste pour chaque joueur à part
	     
	      // Vérifier que le gagnant est le joueur humain si l'ordinateur a perdu
			Ship ship = new Ship(1);
	        random.addShip(0, 0, new Ship(1), false);
	        random.getGrid().getBoard()[0][0].setAssignedShip(ship);
	        random.getGrid().getBoard()[0][0].setState(CellState.HIT);
	       
	        assertEquals("le gagnant doit être le joueur humain",humain, game.getWinner());

	      
	        
	    }
	
	@Test
	public void testIsOver() {
		
		 Grid grid1 = new Grid(new Dimension(5,5));
		 Ship ship = new Ship(1);
		
	     HumanPlayer humain = new HumanPlayer(grid1, "Joueur humain");
	     RandomPlayer random = new RandomPlayer(grid2,gridHumain);
	     Game game = new Game(humain, random);

	       //deux joueurs ne peuvent pas gagner en même temps donc on teste pour chaque joueur à part
	     
	      // Vérifier que le gagnant est le joueur humain si l'ordinateur a perdu
	        random.addShip(0, 0, new Ship(1), false);
	        random.getGrid().getBoard()[0][0].setAssignedShip(ship);//à verifier
	        random.getGrid().getBoard()[0][0].setState(CellState.HIT);
	        assertTrue("le jeu doit etre fini",game.isOver());
		
	}
	
	@Test
	public void testShootGridAdversaire() {
		Ship ship = new Ship(1);//à revoir
		random.addShip(0, 0, new Ship(1), false);
		//random.getGrid().getBoard()[0][0].setAssignedShip(true);
		random.getGrid().getBoard()[0][0].setAssignedShip(ship);
		game.setCurrentPlayer(humain);
		game.shootGridAdversaire(new Position(0,0));
		assertEquals("le joueur courant est le random après l'appel de shootGridAdversaire",random.getName(), game.getCurrentPlayer().getName());
		assertTrue("la cellule de la grille du joueur Random doit être touché à la position 00", random.getGrid().getBoard()[0][0].getState() == CellState.HIT);
		
		
		
		
	}
	
	
	
	

}
