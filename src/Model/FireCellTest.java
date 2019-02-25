package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FireCellTest {
    private Cell emptyCell;
    private Cell treeCell;
    private Cell burningCell;
    private List<Cell> neighbors;

    @BeforeEach
    void setUp() {
        emptyCell = new FireCell(1, 1, 0, 1, 1);
        treeCell = new FireCell(1, 1, 1, 1, 1);
        burningCell = new FireCell(1, 1, 2, 1, 1);
        neighbors = new ArrayList<>();
    }

    @Test
    void treeToBurning() {
        for(int i=0; i<2; i++){
            neighbors.add(treeCell);
        }
        neighbors.add(burningCell);
        treeCell.updateCell(neighbors);

        var expected = 2;
        var actual = treeCell.myNextState;
        assertEquals(expected,actual);

    }

    @Test
    void burningToEmpty() {
        for(int i=0; i<4; i++){
            neighbors.add(treeCell);
        }
        neighbors.add(burningCell);
        neighbors.add(emptyCell);
        burningCell.updateCell(neighbors);

        var expected = 0;
        var actual = burningCell.myNextState;
        assertEquals(expected, actual);
    }

    @Test
    void emptyToEmpty(){
        for(int i=0; i<3; i++){
            neighbors.add(treeCell);
            neighbors.add(burningCell);
            neighbors.add(emptyCell);
        }
        emptyCell.updateCell(neighbors);

        var expected = 0;
        var actual = emptyCell.myNextState;
        assertEquals(expected, actual);
    }
}