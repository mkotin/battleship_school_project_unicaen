package navaletests;
/**
 * /**
 * La classe TestGrid contient les tests unitaires pour la classe Grid.
 * @author sow224
 *
 */

import static org.junit.Assert.*;

import org.junit.Test;

import model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestGrid {

	@Test
	public void testConstructeur() {
		  Dimension dimension = new Dimension(5, 5);
	        Grid grid = new Grid(dimension);
	        assertNotNull("la grille ne doit pas être null après initialisation",grid);
	        assertEquals("la dimension de la grille doit etre celle passee en argument",grid.getDimension(), dimension);
	        assertEquals("le nombre de  lignes de la grille doit correspondre à celui spécifié dans dimension",grid.getBoard().length, dimension.getRows());
	        assertEquals("le nombre de colonnnes de la grille doit correspondre à celui spécifié dans dimension",grid.getBoard()[0].length, dimension.getCols());
	        for (int i = 0; i < dimension.getRows(); i++) {
	            for (int j = 0; j < dimension.getCols(); j++) {
	                assertNotNull(grid.getBoard()[i][j]);
	            }
	        }
	    }
	
	@Test
	public void testAfficherGrid() {
		// on cree une grille et on teste l'affichage en fonction de l'état des cellules
		 	Dimension dimension = new Dimension(3, 3);
	        Grid grid = new Grid(dimension);
	       
	        grid.getBoard()[0][0].setState(CellState.HIT);
	        grid.getBoard()[0][2].setState(CellState.HIT);
	        
	        //on met l'etat des cellules à MISSED
	        grid.getBoard()[0][1].setState(CellState.MISSED);
	        grid.getBoard()[1][2].setState(CellState.MISSED);
	        
	        // Rediriger la sortie standard vers un ByteArrayOutputStream pour pouvoir recuperer la sortie de la fonction affichage
	        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	        System.setOut(new PrintStream(outContent));
	        
	        String sortie = " "+
	        				" 1 2 3 \n"+
	        				"A X ! X \n" +
                    		"B * * ! \n" +
                    		"C * * * \n" ;
	         grid.afficher();
	         
	         String afficher = outContent.toString();
                    		
                    
	        
	        
	        
	        // on vérifie que l'affichage est le même
	        
	        assertEquals("l'affichage doit etre le meme que sortie",sortie, afficher);
	        
	}
	
	@Test
	public void testGetCellulePosition() {
		// on initialise la grille
		Dimension dimension = new Dimension(3, 3);
        Grid grid = new Grid(dimension);
        // on cree une position
        Position position = new Position(0,1);
        //on recupere la cellule à la position 
        Cellule cellule = grid.getCellulePosition(position);
        // on met l'etat de la cellule à HIT
        cellule.setState(CellState.HIT);
        // on verifie que cellule est bien égale à une cellule qui se trouve sur la position (0,1) dans la grille
        assertEquals("la cellule doit avoir le meme etat que la cellule à la position 01",grid.getBoard()[0][1].getState(), cellule.getState());
        assertEquals("la cellule doit avoir le meme etat que la cellule à la position 01",grid.getBoard()[0][1].isAssignedShip(), cellule.isAssignedShip());
        assertEquals("la cellule doit etre identique  à la cellule à la position 01",grid.getBoard()[0][1], cellule);
		
		
	}
		
	}

