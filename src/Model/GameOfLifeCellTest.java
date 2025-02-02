package Model;

import Controller.CellShape;
import Controller.Data;
import Controller.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifeCellTest {
    private Cell deadCell;
    private Cell liveCell;
    private List<Cell> neighbors;
    //private Cell[][] cellGrid;
    Grid cellGrid;
    private CellShape shape = CellShape.SQUARE;

    @BeforeEach
    void setUp() {
        deadCell = new GameOfLifeCell(1, 1, 0, 2);
        liveCell = new GameOfLifeCell(1, 1, 1, 2);
        neighbors = new ArrayList<>();
        //cellGrid = new Cell[5][5];
        Data dat = new Data("GameOfLife_Config_1.csv");
        cellGrid = new Grid(dat);

        Cell temp;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(i==0 || j==2){ //live cells
                    temp = new GameOfLifeCell(i, j, 1, 2);
                }
                else{
                    temp = new GameOfLifeCell(i, j, 0, 2);
                }
                neighbors.add(temp);
            }
        }
    }

    @Test
    void updateCellDeadToAlive() {
        List<Cell> neighs = neighbors.subList(0,4); //3 live, 1 dead
        deadCell.updateCell(neighs, cellGrid, shape);

        var expected = 1;
        var actual = deadCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void updateCellDiesOverpopulation(){
        liveCell.updateCell(neighbors, cellGrid, shape);//Too many live neighbors (5)

        var expected = 0;
        var actual = liveCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void updateCellDiesUnderpopulation(){
        List<Cell> neighs = neighbors.subList(3,8); // 4 dead; 1 live
        liveCell.updateCell(neighs, cellGrid, shape);

        var expected = 0;
        var actual = liveCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void updateCellSurvives(){
        List<Cell> neighs = neighbors.subList(1,5); // 2 live; 2 dead
        liveCell.updateCell(neighs, cellGrid, shape);

        var expected = 1;
        var actual = liveCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void cellEquals(){
        Cell c1 = new GameOfLifeCell(1, 1, 1, 2);
        var actual = liveCell.equals(c1);
        assertTrue(actual);
    }

    @Test
    void cellDoesNotEqual(){
        Cell c1 = new GameOfLifeCell(1, 2, 0, 2);
        var actual = liveCell.equals(c1);
        assertFalse(actual);
    }

    @Test
    void hexCellDeadToAlive(){
        List<Cell> neighs = neighbors.subList(1,4);//2 live
        deadCell.updateCell(neighs, cellGrid, CellShape.HEXAGON);

        var expected = 1;
        var actual = deadCell.getMyNextState();
        assertEquals(expected, actual);
    }

    @Test
    void triCellStaysAlive(){
        List<Cell> neighs = neighbors.subList(1,4);//2 live
        liveCell.updateCell(neighs, cellGrid, CellShape.TRIANGLE);

        var expected = 1;
        var actual = liveCell.getMyNextState();
        assertEquals(expected, actual);
    }
}