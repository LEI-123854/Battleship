package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    @Test
    @DisplayName("Cria Caravel na direção NORTH corretamente")
    void testCaravelNorth() {
        Caravel c = new Caravel(Compass.NORTH, new Position(2, 3));

        assertEquals(2, c.getSize());
        assertEquals("Caravela", c.getName());
        assertEquals(Compass.NORTH, c.getBearing());

        assertEquals(2, c.getPositions().size());
        assertEquals(2, c.getPositions().get(0).getRow());
        assertEquals(3, c.getPositions().get(0).getColumn());
        assertEquals(3, c.getPositions().get(1).getRow());
        assertEquals(3, c.getPositions().get(1).getColumn());
    }

    @Test
    @DisplayName("Cria Caravel na direção SOUTH corretamente")
    void testCaravelSouth() {
        Caravel c = new Caravel(Compass.SOUTH, new Position(1, 1));

        assertEquals(2, c.getPositions().size());
        assertEquals(1, c.getPositions().get(0).getRow());
        assertEquals(1, c.getPositions().get(0).getColumn());
        assertEquals(2, c.getPositions().get(1).getRow());
        assertEquals(1, c.getPositions().get(1).getColumn());
    }

    @Test
    @DisplayName("Cria Caravel na direção EAST corretamente")
    void testCaravelEast() {
        Caravel c = new Caravel(Compass.EAST, new Position(4, 4));

        assertEquals(2, c.getPositions().size());
        assertEquals(4, c.getPositions().get(0).getRow());
        assertEquals(4, c.getPositions().get(0).getColumn());
        assertEquals(4, c.getPositions().get(1).getRow());
        assertEquals(5, c.getPositions().get(1).getColumn());
    }

    @Test
    @DisplayName("Cria Caravel na direção WEST corretamente")
    void testCaravelWest() {
        Caravel c = new Caravel(Compass.WEST, new Position(7, 7));

        assertEquals(2, c.getPositions().size());
        assertEquals(7, c.getPositions().get(0).getRow());
        assertEquals(7, c.getPositions().get(0).getColumn());
        assertEquals(7, c.getPositions().get(1).getRow());
        assertEquals(8, c.getPositions().get(1).getColumn());
    }

    @Test
    @DisplayName("Lança AssertionError se bearing for null")
    void testCaravelNullBearing() {
        AssertionError ex =
                assertThrows(AssertionError.class,
                        () -> new Caravel(null, new Position(0, 0)));
    }

    @Test
    @DisplayName("Lança AssertionError se posição for null")
    void testCaravelNullPosition() {
        AssertionError ex =
                assertThrows(AssertionError.class,
                        () -> new Caravel(Compass.NORTH, null));
    }

}
