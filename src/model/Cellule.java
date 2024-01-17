package model;

import util.AbstractListenableModel;
import util.notifications.CellNotification;

/**
 * classe representant une cellule de la grille d'un joueur
 */
public class Cellule extends AbstractListenableModel {
	private Position position;
	private CellState state;
	private Ship assignedShip;

	/**
	 * constructeur de la classe initialise une cellule avec une position ,l'etat de la cellule à BLANK,et dit qu'elle n'appartient pas à un navire
	 * @param position
	 */
	public Cellule(Position position) {
		this.position = position;
		this.state = CellState.BLANK;
		this.assignedShip = null;
		
	}

	public Ship getAssignedShip() {
		return assignedShip;
	}

	/**
	 * constructeur de la classe initialise une cellule  avec l'etat de la cellule à BLANK,et dit qu'elle n'appartient pas à un navire
	 * @param position
	 */
	public Cellule() {
		this(null);	
	}

	//getters et setters

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public CellState getState() {
		return state;
	}

	public void setState(CellState state) {
		this.state = state;
		this.fireChangement(CellNotification.STATE_CHANGED);
	}

	public boolean isAssignedShip() {
		return (this.assignedShip != null);
	}

	public void setAssignedShip(Ship assignedShip) {
		this.assignedShip = assignedShip;
	}


	

	
	


	
	


}
