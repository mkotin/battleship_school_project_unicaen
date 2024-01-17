package model;

import java.util.ArrayList;

import util.AbstractListenableModel;
import util.notifications.ShipNotification;
/**
 * classe représentant un navire
*/
public class Ship extends AbstractListenableModel {
	private ArrayList<Cellule> shipCell;
	private boolean visible;
	private int size;
	protected boolean isDestroyed = false;

	/**
	 * Constructeur de la classe initialise un ship avec une liste de cellule vide, un etat visible false et une taille du navire
	 * @param size : represente la taille du navire
	 */
	public Ship(int size) {
		this.shipCell = new ArrayList<>();
		this.visible = false;
		this.size = size;
	}

	public Ship(int size, boolean visible) {
		this(size);
		this.visible = visible;
	}

	//getters et setters

	public ArrayList<Cellule> getShipCell() {
		return shipCell;
	}

	/**
	 * Destroy a ship
	 */
	public void destroy() {
		// Free ship cell
		for(Cellule cell: shipCell) {
			cell.setAssignedShip(null);
		}
		this.isDestroyed = true;
		this.fireChangement(ShipNotification.SHIP_DESTROYED);
	}

	public void setShipCell(ArrayList<Cellule> shipCell) {
		this.shipCell = shipCell;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		this.fireChangement(ShipNotification.SHIP_VISIBILITY_CHANGED);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * vérifie si l'ensemble des cellules d'un navire sont touchés
	 * @return true si toutes les cellules sont touchées false sinon
	 */
	public boolean isSank(){
		for(Cellule cellule : shipCell){
			if(!cellule.getState().equals(CellState.HIT)){
				return false;
			}
		}
		return true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}

	public void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}	
	
}