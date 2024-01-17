package views;

import java.awt.Dimension;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.CellState;
import model.Cellule;
import model.Game;
import util.ListeningModel;
import util.notifications.CellNotification;

/** A class representing a cell view */
public class ViewOneCell extends JPanel implements ListeningModel {
    /** Cell model  */
    public Cellule cellOfGrid;

    /** Cell border properties */
    protected int borderTop = 0;
    protected int borderBottom = 0;
    protected int borderLeft = 0;
    protected int borderRight = 0;
    protected int borderValue = 4;
    protected Color defaultColor = new Color(179, 68, 223);

    public ViewOneCell(Cellule cell) {
        super();
        this.setBackground(Color.WHITE);

        // Set cell model and listen to it
        this.cellOfGrid = cell;
        this.cellOfGrid.addListening(this);

        this.setBorder(BorderFactory.createLineBorder(defaultColor, 1));
        this.setPreferredSize(new Dimension(50, 50));

        manageEvent();

    }

    /** 
     * Set to the cell the default color.
     * It's use when we cell ship's pass from visble to not visible
     */
    public void setDefaultColor() {
        this.setBorder(BorderFactory.createLineBorder(defaultColor, 1));
    }

    /**
     * Assign a cell to ship. 
     * It's use to give a ship appearence to a group of cells by setting the right border to them
     * @param horizontal determine wether the ship is horizontal or vertical
     * @param orientation  1 if ship is oriented top or right, -1 if bottom or left
     * @param last determine wether the cell is the last cell of the ship
     * @param first determine wether the cell is the first cell of the ship
     */
    public void assignToShip(boolean horizontal, int orientation, boolean last, boolean first) {
        // Initialize all properties
        this.borderTop = 0;
        this.borderBottom = 0;
        this.borderLeft = 0;
        this.borderRight = 0;

        if (horizontal) {
            // if horizontal each cell have a bordertop and a border bottom
            borderTop = borderValue;
            borderBottom = borderValue;

            if (orientation == 1) {
                // if oriented right set a borderLeft to the first cell and a  borderRight to the last one
                if (first)
                    borderLeft = borderValue;
                if (last)
                    borderRight = borderValue;
            } else {
                // if oriented left do the invers
                if (first)
                    borderRight = borderValue;
                if (last)
                    borderLeft = borderValue;
            }

        } else {
            // if vertical each cell have a borderLeft and a borderRight
            borderLeft = borderValue;
            borderRight = borderValue;

            if (orientation == 1) {
                // if oriented top set a border bottom the first cell and a border top to the last cell
                if (first)
                    borderBottom = borderValue;
                if (last)
                    borderTop = borderValue;
            } else {
                // if oriented bottom do the inverse
                if (first)
                    borderTop = borderValue;
                if (last)
                    borderBottom = borderValue;
            }
        }
    }

    /**
     * Show the cell border.
     * Used when the cell ship's pass from not visible to visible. 
     */
    public void showBorder() {
        this.setBorder(BorderFactory.createMatteBorder(borderTop, borderLeft, borderBottom, borderRight,
                new Color(148, 39, 191)));
    }

    /**
     * Handler for click event
     */
    public void cellClicked() {
        // If cell have already been touched do nothing
        if(cellOfGrid.getState() != CellState.BLANK) {
            return;
        }

        // Manage click event only on random player grid cells, human cannot hit himself
        Container parent = this.getParent();
        if(parent instanceof GridView) {
            GridView gridViewparent = (GridView) parent;

            
            if(!gridViewparent.isHumanGrid()) {
                 // Get game model from grand-parent component
                ControleGame grandParentCGame = (ControleGame) gridViewparent.getParent();
                Game game = grandParentCGame.game;

                // Make sure the game has started
                if(!game.isStarted()){
                    new MessageDialog("Appuyez sur jouer pour commencer",
                    JOptionPane.INFORMATION_MESSAGE).showMessageDialog();
                    return;
                }

                // @todo: should we make sure human is the current player ?
                game.setCurrentPlayer(game.getHumainPlayer());
                game.shootGridAdversaire(cellOfGrid.getPosition());


                // check if game is over before continue
                if(!game.isOver()) {
                    // game is not over, make the random player shoot 
                    game.shootGridAdversaire(game.getRandomPlayer().shoot());

                    //  also check if the game is over and end the game
                    if(game.isOver()) grandParentCGame.endGame();
                } else {
                    // game is over end the game
                    grandParentCGame.endGame();
                }

                
            }
        } else {
            System.out.println("DEBUG: CELL HAVE NOT PARENT GRID");
        }
        

       
    }

    /** 
     * Handler for cell state changes
     */
    public void handleStateChanged() {
        if (cellOfGrid.getState() == CellState.BLANK) {
            this.setBackground(Color.WHITE);
        } else if (cellOfGrid.getState() == CellState.HIT) {
            this.setBackground(Color.RED);
        } else if (cellOfGrid.getState() == CellState.MISSED) {
            this.setBackground(Color.GREEN);
        }
    }


    /**
     * Update method for method for model notification
     */
    @Override
    public void modeleMIsAJour(Object source, Object notification) {
        if (notification instanceof CellNotification) {
            if (notification == CellNotification.STATE_CHANGED) {
                handleStateChanged();
            }
        } else {
            System.out.println("Unhandled notification for ViewOneCell:  " + notification);
        }
    }

    /**
     * Event manager
     */
    public void manageEvent() {
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cellClicked();
            }
        });
    }

}