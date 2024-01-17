package views;

import javax.swing.JPanel;

import java.util.ArrayList;

import model.Ship;
import util.ListeningModel;
import util.notifications.ShipNotification;
import model.Cellule;

/** A class representing a ship view */
public class ViewShip extends JPanel implements ListeningModel {
    /** The ship model */
    protected Ship ship;

    /** A one dimension array of ship cell views */
    protected ViewOneCell[] shipCellViews;
    
    /** The parrent grid of the ship */
    protected GridView parentGridView;

    public ViewShip(Ship ship, GridView parentGridView) {
        // Set model and listen to it
        this.ship = ship;
        this.ship.addListening(this);

        this.parentGridView = parentGridView;

        createShip();

        if(this.ship.isVisible()) {
            this.setVisibility(true);
        }
    }

    /**
     * Get the cell view associated to a cell model in the ship
     * @param cell the cell model
     * @return the cell view associated to a cell model in the ship
     */
    private ViewOneCell getViewCell(Cellule cell) {
        int x = cell.getPosition().getX();
        int y = cell.getPosition().getY();

        // @todo: make sure x and y coord are valid
        return this.parentGridView.getBoardView()[x][y];
    }

    /**
     * Createt the ship view appearence
     */
    public void createShip() {
        // initialization
        int index = 0;
        ArrayList<Cellule> shipCells = ship.getShipCell();
        boolean horizontalShip = true;
        int orientation = 1; // 1 if ship is oriented top or right, -1 if bottom or left

        for (Cellule cell : shipCells) {

            // current cell
            ViewOneCell viewCell = this.getViewCell(cell);

            if (index < (shipCells.size() - 1)) { // make sure index is valid
                
                // next cell
                Cellule nextCell = shipCells.get(index + 1);
                
                if (index == 0) {
                    // we determine once if the ship is vertical/horizontal 
                    // and his orientation (top/bottom right/bottom)

                    // Ship is horizontal if cell and next cell have the same x coordinate
                    // meaning there are on the same line
                    horizontalShip = nextCell.getPosition().getX() == cell.getPosition().getX();

                    // Then we compare Y coordinatates to determine orientation
                    if (horizontalShip) {
                        orientation = nextCell.getPosition().getY() > cell.getPosition().getY() ? 1 : -1;
                    } else {
                        orientation = nextCell.getPosition().getX() < cell.getPosition().getX() ? 1 : -1;
                    }
                }
            }

            // check if first or last
            boolean first = (index == 0);
            boolean last = (index == (shipCells.size() - 1));

            // set cell border properties we those informations
            viewCell.assignToShip(horizontalShip, orientation, last, first);

            index++;
        }

    }

    /**
     * Destroy a ship view
     */
    public void destroyShip() {
        this.setVisibility(false);
    }

    /**
     * Set ship visibility
     * @param show
     */
    public void setVisibility(boolean show) {
        ArrayList<Cellule> shipCells = ship.getShipCell();
        if (show) {
            for (Cellule cell : shipCells) {
                ViewOneCell viewCell = this.getViewCell(cell);
                viewCell.showBorder();
            }
        } else {
            for (Cellule cell : shipCells) {
                ViewOneCell viewCell = this.getViewCell(cell);
                viewCell.setDefaultColor();
            }

        }

    }

    /**
     * Update method for model notification
     */
    @Override
    public void modeleMIsAJour(Object source, Object notification) {
        if (notification instanceof ShipNotification) {
            if (notification == ShipNotification.SHIP_DESTROYED) { // Support destroyed ship
                if (this.ship.isDestroyed()) {
                    this.destroyShip();
                }
            } else if(notification == ShipNotification.SHIP_VISIBILITY_CHANGED) {
                this.setVisibility(this.ship.isVisible());
            }

        } else {
            System.out.println("Unhandled notification for ViewShip:  " + notification);
        }

    }
}