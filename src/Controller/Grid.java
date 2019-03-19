package Controller;

import Model.*;

public class Grid {
    private Data myData;
    private Cell[][] myCellGrid;
    private int myRows;
    private int myCols;

    /**
     * Constructor only needs data to fill grid
     * @param dat
     */
    public Grid(Data dat){
        myData = dat;
        myRows = myData.getHeight();
        myCols = myData.getWidth();
    }

    /**
     * Called by main to fill cell grid getting info from data
     * Uses helper method simCellPicker to select right type of concrete cell class to initialize
     * @param simType
     */
    public void fillCellGrid(String simType){
        myCellGrid = new Cell[myRows][myCols];
        for (int i = 0; i < myRows; i++) {
            for (int j = 0; j < myCols; j++) {
                myCellGrid[i][j] = simCellPicker(simType, i, j);
            }
        }
    }

    /**
     * Called by main step method updates cells in grid to new states every step
     * Calls neighbor classes and cell classes in backend to update themselves and front end gets results
     * @param row
     * @param col
     * @param shape
     * @param edgeType
     * @param neighborhoodType
     */
    public void updateGridCell(int row, int col, CellShape shape, EdgeType edgeType, NeighborhoodType neighborhoodType){
        Neighbors n = neighborhoodPicker(row, col, shape, edgeType, neighborhoodType);
        //myCellGrid[row][col] = myCellGrid[row][col].updateCell(n.getMyNeighbors(), myCellGrid, shape);
        myCellGrid = myCellGrid[row][col].updateCell(n.getMyNeighbors(), myCellGrid, shape);
    }

    //Helper method initializes correct cell simulation type
    private Cell simCellPicker(String simType, int x, int y){
        if(simType.toUpperCase().equals("GAMEOFLIFE")){
            return new GameOfLifeCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("PERCOLATION")){
            return new PercolationCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("FIRE")){
            return new FireCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("RPS")){
            return new RPSCell(x, y, myData.getStates()[x][y]);
        }
        else if(simType.toUpperCase().equals("SEGREGATION")){
            return new SegregationCell(x, y, myData.getStates()[x][y]);
        }
        else{
            return new PredatorPreyCell(x, y, myData.getStates()[x][y]);
        }
    }

    //Helper method called by update to get correct neighborhood for cell
    private Neighbors neighborhoodPicker(int x, int y, CellShape shape, EdgeType edgeType, NeighborhoodType neighborhoodType){
        if(neighborhoodType == NeighborhoodType.COMPLETE){
            return new CompleteNeighbors(x, y, myCellGrid, shape, edgeType);
        }
        else if(neighborhoodType == NeighborhoodType.CARDINAL){
            return new CardinalNeighbors(x, y, myCellGrid, shape, edgeType);
        }
        else{ //Corner neighborhoodType
            return new CornerNeighbors(x, y, myCellGrid, shape, edgeType);
        }
    }

    /**
     * calls cell's resetState method after each step
     * @param row
     * @param col
     */
    public void resetCell(int row, int col){
        myCellGrid[row][col].resetState();
    }

    /**
     * Getter call's cell backend to get state at current location
     * @param row
     * @param col
     * @return
     */
    public int getCellState(int row, int col){ return myCellGrid[row][col].getMyCurrentState(); }

    /**
     * getter
     * @return cell grid
     */
    public Cell[][] getCellGrid(){ return myCellGrid; }

    /**
     * getter
     * @return number of rows in grid
     */
    public int getMyRows(){return myRows;}

    /**
     * getter
     * @return number of columns in grid
     */
    public int getMyCols(){return myCols;}

    public Cell getCellAt(int row, int col){
        return myCellGrid[row][col];
    }

    public void setCellAt(int row, int col, Cell cell){
        myCellGrid[row][col] = cell;
    }
}
