package model;
/**
 * classe representant les dimensions d'un objet à deux dimensions
 */

public class Dimension {
	private int rows;
	private int cols;

	/**
	 * Constructeur de la classe
	 * @param rows : represente les lignes
	 * @param cols : represente les colones
	 */
	public Dimension(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * constructeur sans argument */	
	public Dimension(){

	}


	//getters et setters
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getCols() {
		return cols;
	}
	public void setCols(int cols) {
		this.cols = cols;
	}
	
	

}
