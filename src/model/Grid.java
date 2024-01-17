package model;
import java.util.ArrayList;
import java.util.List;

import util.AbstractListenableModel;

import java.util.Collections;
/**
 * cette classe représente la grille d'un joueur
 */

public class Grid extends AbstractListenableModel {
	private Dimension dimension;
	private Cellule [][] board;
	private ArrayList<Position> allPositions = new ArrayList<Position>();

	/**
	 * Constructeur de la classe permet d'initialiser la grille
	 * @param dimension représente les dimensions de la grille du joueur
	 */
	public Grid(Dimension dimension) {
		this.dimension = dimension;
		this.board = new Cellule[dimension.getRows()][dimension.getCols()];
		for(int i=0;i<this.dimension.getRows();i++){
			for(int j=0; j< this.dimension.getCols();j++){
				Position pos = new Position(i, j);
				allPositions.add(pos);
				this.board[i][j] = new Cellule(pos);
			} 
		}

	}
	

	

	public ArrayList<Position> getAllPositions() {
		return allPositions;
	}




	public void setAllPositions(ArrayList<Position> allPositions) {
		this.allPositions = allPositions;
	}




	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public Cellule[][] getBoard() {
		return board;
	}

	public void setBoard(Cellule[][] board) {
		this.board = board;
	}

	/**
	 * cette méthode renvoie la cellule correspondant à une position de la grille
	 * @param position
	 * @return
	 */
	public Cellule getCellulePosition(Position position){
		int x = position.getX();
		int y = position.getY();
		if( x >= 0 && x < board.length && y >= 0 && y < board[0].length)
			return this.board[position.getX()][position.getY()];
		else
			return null;
	}

	/**
	 * cette méthode permet d'afficher la grille
	 */
	public void afficher() {

		System.out.print("  ");
        for (int i = 0; i < dimension.getRows(); i++) {
            System.out.print(i  + " ");
        }
        System.out.println();

		for (int i = 0; i < this.dimension.getRows(); i++) {
			System.out.print((char) ('A' + i) + " ");
			for (int j = 0; j < this.dimension.getCols(); j++) {

				 if (this.board[i][j].getState() == CellState.HIT) {
					System.out.print("X "); // si une cellule est touché
				// si une cellule est ratéé
				} else if( this.board[i][j].getState() == CellState.MISSED) {
					System.out.print("! ");
				}
				else{
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}

	/**
    * Cette méthode retourne une liste aléatoire de toutes les positions de la grille de jeu.
    * Elle parcourt chaque cellule de la grille de jeu et crée une Position correspondante
    * qu'elle ajoute à la liste de positions. Ensuite, elle mélange aléatoirement la liste de positions
    * pour que l'ordre dans lequel les positions sont parcourues ne soit pas toujours le même.
    * @return une liste de positions aléatoire pour la grille de jeu
    */
	public List <Position> gridPosition(){
		List <Position> positions = new ArrayList<>();
		for(int i=0;i<this.getBoard().length;i++){
			for(int j =0;j<this.getBoard()[0].length;j++){
				positions.add(new Position(i, j));
			}
		}	
		Collections.shuffle(positions);
		return positions;
	}



	
	
}
