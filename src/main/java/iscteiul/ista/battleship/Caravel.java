package iscteiul.ista.battleship;

public class Caravel extends Ship {
    private static final Integer SIZE = 2;
    private static final String NAME = "Caravela";

    public Caravel(Compass bearing, IPosition pos) {
        super(Caravel.NAME, bearing, pos);

        if (bearing == null)
            throw new NullPointerException("invalid bearing");

        if (pos == null)
            throw new NullPointerException("position cannot be null");

        switch (bearing) {
            case NORTH:
            case SOUTH:
                for (int r = 0; r < SIZE; r++)
                    getPositions().add(new Position(pos.getRow() + r, pos.getColumn()));
                break;
            case EAST:
            case WEST:
                for (int c = 0; c < SIZE; c++)
                    getPositions().add(new Position(pos.getRow(), pos.getColumn() + c));
                break;
            default:
                throw new IllegalArgumentException("invalid bearing");
        }
    }

    @Override
    public Integer getSize() {
        return SIZE;
    }


    public String getName() {
        return NAME;
    }
}
