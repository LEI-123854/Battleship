package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    // -------------------------
    // FakeShip mínimo para testes
    // -------------------------
    static class FakeShip implements IShip {
        private final IPosition pos;
        private boolean hit = false;
        private boolean sunk = false;

        public FakeShip(IPosition pos) { this.pos = pos; }

        @Override
        public void shoot(IPosition p) { if (p.equals(pos)) hit = true; }

        @Override
        public boolean stillFloating() { return !sunk && !hit; }

        @Override
        public boolean occupies(IPosition p) { return p.equals(pos); }

        @Override
        public boolean tooCloseTo(IShip other) { return false; }

        @Override
        public boolean tooCloseTo(IPosition p) { return false; }

        @Override
        public int getTopMostPos() { return pos.getRow(); }

        @Override
        public int getBottomMostPos() { return pos.getRow(); }

        @Override
        public int getLeftMostPos() { return pos.getColumn(); }

        @Override
        public int getRightMostPos() { return pos.getColumn(); }

        @Override
        public Integer getSize() { return 1; } // ✅ Integer, não int

        @Override
        public String getCategory() { return "Fake"; }

        @Override
        public java.util.List<IPosition> getPositions() { return java.util.List.of(pos); }

        @Override
        public IPosition getPosition() { return pos; }

        @Override
        public Compass getBearing() { return Compass.NORTH; }
    }

    @Test
    @DisplayName("Fire em posição inválida incrementa countInvalidShots")
    void testInvalidShot() {
        Game game = new Game(new Fleet());
        Position pos = new Position(-1, 0);

        game.fire(pos);

        assertEquals(1, game.getInvalidShots());
    }

    @Test
    @DisplayName("Fire em posição repetida incrementa countRepeatedShots")
    void testRepeatedShot() {
        Game game = new Game(new Fleet());
        Position pos = new Position(0, 0);

        game.fire(pos);
        game.fire(pos);

        assertEquals(1, game.getRepeatedShots());
    }

    @Test
    @DisplayName("Fire em posição sem navio retorna null")
    void testMissShot() {
        Game game = new Game(new Fleet());
        Position pos = new Position(1, 1);

        assertNull(game.fire(pos));
    }

    @Test
    @DisplayName("Fire acerta navio mas ainda flutuando")
    void testHitButNotSunk() {
        Fleet fleet = new Fleet();
        Position pos = new Position(0, 0);

        // FakeShip especial: nunca afunda
        IShip ship = new FakeShip(pos) {
            @Override
            public void shoot(IPosition p) {
                // marca o tiro mas ainda continua a flutuar
            }

            @Override
            public boolean stillFloating() {
                return true; // sempre flutuando
            }
        };

        fleet.addShip(ship);

        Game game = new Game(fleet);
        IShip result = game.fire(pos);

        assertNull(result);       // navio ainda flutuando
        assertEquals(1, game.getHits());
        assertEquals(0, game.getSunkShips());
    }

    @Test
    @DisplayName("Fire acerta e afunda navio")
    void testHitAndSink() {
        Fleet fleet = new Fleet();
        Position pos = new Position(0, 0);
        FakeShip ship = new FakeShip(pos) {
            @Override
            public boolean stillFloating() { return false; } // força afundar
        };
        fleet.addShip(ship);

        Game game = new Game(fleet);
        IShip result = game.fire(pos);

        assertNotNull(result);    // navio afundou → retorna o navio
        assertEquals(1, game.getHits());
        assertEquals(1, game.getSunkShips());
    }

    @Test
    @DisplayName("getShots retorna todas posições válidas disparadas")
    void testGetShots() {
        Game game = new Game(new Fleet());
        Position pos = new Position(0, 0);

        game.fire(pos);

        assertEquals(1, game.getShots().size());
        assertEquals(pos, game.getShots().get(0));
    }

    @Test
    @DisplayName("getRemainingShips devolve número correto de navios não afundados")
    void testGetRemainingShips() {
        Fleet fleet = new Fleet();
        Game game = new Game(fleet);

        assertEquals(0, game.getRemainingShips());
    }

    @Test
    @DisplayName("printBoard não lança exceção")
    void testPrintBoard() {
        Game game = new Game(new Fleet());
        Position pos = new Position(0, 0);
        game.getShots().add(pos);

        assertDoesNotThrow(() -> game.printBoard(game.getShots(), 'X'));
    }

    @Test
    @DisplayName("printFleet não lança exceção")
    void testPrintFleet() {
        Fleet fleet = new Fleet();
        Game game = new Game(fleet);

        assertDoesNotThrow(game::printFleet);
    }

    @Test
    @DisplayName("printValidShots não lança exceção")
    void testPrintValidShots() {
        Game game = new Game(new Fleet());
        Position pos = new Position(0, 0);
        game.fire(pos);

        assertDoesNotThrow(game::printValidShots);
    }
}