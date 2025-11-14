package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Ship implements IShip {

    private static final String GALEAO = "galeao";
    private static final String FRAGATA = "fragata";
    private static final String NAU = "nau";
    private static final String CARAVELA = "caravela";
    private static final String BARCA = "barca";

    /**
     * Fábrica de navios
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        if (bearing == null || pos == null) {
            throw new NullPointerException("Bearing and position cannot be null");
        }
        Ship s;
        switch (shipKind) {
            case BARCA:
                s = new Barge(bearing, pos);
                break;
            case CARAVELA:
                s = new Caravel(bearing, pos);
                break;
            case NAU:
                s = new Carrack(bearing, pos);
                break;
            case FRAGATA:
                s = new Frigate(bearing, pos);
                break;
            case GALEAO:
                s = new Galleon(bearing, pos);
                break;
            default:
                s = null;
        }
        return s;
    }

    private String category;
    private Compass bearing;
    private IPosition pos;
    protected List<IPosition> positions;

    /**
     * Construtor do navio
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        if (bearing == null) {
            throw new NullPointerException("Bearing cannot be null");
        }
        if (pos == null) {
            throw new NullPointerException("Position cannot be null");
        }

        this.category = category;
        this.bearing = bearing;
        this.pos = pos;
        positions = new ArrayList<>();
    }

    @Override
    public String getCategory() {
        return category;
    }

    public List<IPosition> getPositions() {
        return positions;
    }

    @Override
    public IPosition getPosition() {
        return pos;
    }

    @Override
    public Compass getBearing() {
        return bearing;
    }

    @Override
    public boolean stillFloating() {
        for (int i = 0; i < getSize(); i++)
            if (!getPositions().get(i).isHit())
                return true;
        return false;
    }

    @Override
    public int getTopMostPos() {
        int top = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() < top)
                top = getPositions().get(i).getRow();
        return top;
    }

    @Override
    public int getBottomMostPos() {
        int bottom = getPositions().get(0).getRow();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() > bottom)
                bottom = getPositions().get(i).getRow();
        return bottom;
    }

    @Override
    public int getLeftMostPos() {
        int left = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() < left)
                left = getPositions().get(i).getColumn();
        return left;
    }

    @Override
    public int getRightMostPos() {
        int right = getPositions().get(0).getColumn();
        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() > right)
                right = getPositions().get(i).getColumn();
        return right;
    }

    @Override
    public boolean occupies(IPosition pos) {
        if (pos == null) {
            throw new NullPointerException("Position cannot be null");
        }
        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).equals(pos))
                return true;
        return false;
    }

    @Override
    public boolean tooCloseTo(IShip other) {
        if (other == null) {
            throw new NullPointerException("Other ship cannot be null");
        }
        Iterator<IPosition> otherPos = other.getPositions().iterator();
        while (otherPos.hasNext())
            if (tooCloseTo(otherPos.next()))
                return true;

        return false;
    }

    @Override
    public boolean tooCloseTo(IPosition pos) {
        if (pos == null) {
            throw new NullPointerException("Position cannot be null");
        }
        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).isAdjacentTo(pos))
                return true;
        return false;
    }

    @Override
    public void shoot(IPosition pos) {
        if (pos == null) {
            throw new NullPointerException("Position cannot be null");
        }
        for (IPosition position : getPositions()) {
            if (position.equals(pos))
                position.shoot();
        }
    }

    @Override
    public String toString() {
        return "[" + category + " " + bearing + " " + pos + "]";
    }
}
