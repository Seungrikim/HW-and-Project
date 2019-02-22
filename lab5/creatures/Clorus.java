package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    private int r;
    private int g;
    private int b;
    private double repEnergyRetained = 0.5;
    private double repEnergyGiven = 0.5;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    public Clorus() {this(1);}

    public void move() {
        energy = energy - 0.03;
    }

    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    public void stay() {
        energy = energy - 0.01;

    }

    public Color color() {
        r = 34;
        g = 0;
        b = 231;
        return color(r,g,b);
    }

    public Clorus replicate() {
        double babyenergy = energy;
        energy = energy * repEnergyRetained;
        babyenergy = babyenergy * repEnergyGiven;
        return new Clorus(babyenergy);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptySpace = new ArrayDeque();
        Deque<Direction> plipDetect = new ArrayDeque<>();
        boolean anyPlip = false;
        for (Direction key : neighbors.keySet()) {
            if (neighbors.get(key).name().equals("empty")) {
                emptySpace.addLast(key);
            } else if (neighbors.get(key).name().equals("plip")) {
                anyPlip = true;
                plipDetect.addLast(key);
            }
        }
        if (emptySpace.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plipDetect));
        } else if (energy >= 1.0) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptySpace));
        } else {
            return new Action(Action.ActionType.MOVE, randomEntry(emptySpace));
        }
    }

}
