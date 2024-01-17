package views;

import java.awt.*;
import javax.swing.*;

import config.Config;

import java.awt.Color;

import model.Grid;
import util.ListeningModel;
import util.notifications.GridNotification;

/** The Grid view */
public class GridView extends JPanel implements ListeningModel {
    /** Grid dimensions */
    protected int rows = Config.GRID_Rows;
    protected int cols = Config.GRID_Cols;

    /** Grid board view */
    protected ViewOneCell[][] boardView;
    protected Grid gridModel;

    /** GridBagConstraints for the layout */
    protected GridBagConstraints gbc;

    protected String gridLabel = "Joueur";
    protected boolean isHumanGrid;

    public GridView(Grid gridModel, String gridLabel, boolean isHumanGrid) {
        super();
        this.setBackground(Color.WHITE);

        // Assign model to view and listen to it
        this.gridModel = gridModel;
        this.gridModel.addListening(this);

        this.gridLabel = gridLabel;
        this.isHumanGrid = isHumanGrid;

        this.rows = this.gridModel.getDimension().getRows();
        this.cols = this.gridModel.getDimension().getCols();
        this.boardView = new ViewOneCell[this.rows][this.cols];
       

        // Setting the layout
        this.setLayout(new GridBagLayout());

        // creates a constraints object
        this.gbc = new GridBagConstraints();

        // natural height, maximum width
        this.gbc.fill = GridBagConstraints.BOTH;

        this.gbc.gridx = 0;

        // col j
        this.gbc.gridy = 0;
        this.gbc.gridwidth = 10;

        JLabel gridLabelField = new JLabel(gridLabel);
        gridLabelField.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        this.add(gridLabelField, this.gbc);

        this.gbc.gridwidth = 1;
        this.gbc.ipadx = 30;
        this.gbc.ipady = 30;
        this.gbc.weightx = 0;
        this.gbc.weighty = 0;
        
        createGrid();

        //this.setVisible(true);
    }

    /**
     * Return wether the grid is human's or random's
     * @return wether the grid is human's or random's
     */
    public boolean isHumanGrid() {
        return isHumanGrid;
    }

    /**
     * Setter for isHumanGrid
     * @param isHumanGrid
     */
    public void setHumanGrid(boolean isHumanGrid) {
        this.isHumanGrid = isHumanGrid;
    }

    /**
     * Create the grid
     */
    public void createGrid() {
        // Arrange the cells next to each other to form the grid
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                // row i
                this.gbc.gridx = j;

                // col j
                this.gbc.gridy = i + 1;

                this.boardView[i][j] = new ViewOneCell(this.gridModel.getBoard()[i][j]);
                this.add(this.boardView[i][j], this.gbc);
            }
        }
    }

    public ViewOneCell[][] getBoardView() {
        return boardView;
    }

    public void setBoardView(ViewOneCell[][] boardView) {
        this.boardView = boardView;
    }

    /**
     * Update method for grid model notification
     */
    @Override
    public void modeleMIsAJour(Object source, Object notification) {
        if (notification instanceof GridNotification) {

        } else {
            System.out.println("Unhandled notification for GridView:  " + notification);
        }
    }

    
}
