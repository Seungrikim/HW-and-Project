package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c = new Clorus(2);
        c.replicate();
        assertEquals(1, c.energy(), 0.01);
        c.replicate();
        assertEquals(0.5, c.energy(), 0.01);
        c.replicate();
        assertEquals(0.25, c.energy(), 0.01);
    }
    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        //Plip p = new Plip(2);
        HashMap<Direction, Occupant> topEnemy = new HashMap<Direction, Occupant>();
        topEnemy.put(Direction.TOP, new Plip(2));
        topEnemy.put(Direction.BOTTOM, new Empty());
        topEnemy.put(Direction.LEFT, new Empty());
        topEnemy.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(topEnemy);
        expected = new Action(Action.ActionType.ATTACK, Direction.TOP);

        assertEquals(expected, actual);


        // Energy < 1; stay.
        c= new Clorus(.99);

        HashMap<Direction, Occupant> bottomEmpty = new HashMap<Direction, Occupant>();
        bottomEmpty.put(Direction.TOP, new Impassible());
        bottomEmpty.put(Direction.BOTTOM, new Empty());
        bottomEmpty.put(Direction.LEFT, new Impassible());
        bottomEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(bottomEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.BOTTOM);

        assertEquals(expected, actual);


        // Energy < 1; stay.
        c = new Clorus(.99);

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);


        // We don't have Cloruses yet, so we can't test behavior for when they are nearby right now.
    }
}
