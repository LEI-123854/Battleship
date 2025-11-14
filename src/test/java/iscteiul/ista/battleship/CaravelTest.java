package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaravelTest {

    @Test
    @DisplayName("Cria Caravel na direção NORTH corretamente")
    void testCaravelCreationNorth() {
        Compass bearing = Compass.NORTH;
        IPosition pos = new Position(2, 3);

        Caravel caravel = new Caravel(bearing, pos);

        assertAll(
                () -> assertEquals(2, caravel.getSize()),
                () -> assertEquals("Caravela", caravel.getName()),
                () -> assertEquals(bearing, caravel.getBearing()),
                () -> assertEquals(2, caravel.getPositions().size()),
                () -> {
                    assertEquals(pos.getRow(), caravel.getPositions().get(0).getRow());
                    assertEquals(pos.getColumn(), caravel.getPositions().get(0).getColumn());
                    assertEquals(pos.getRow() + 1, caravel.getPositions().get(1).getRow());
                    assertEquals(pos.getColumn(), caravel.getPositions().get(1).getColumn());
                }
        );
    }

    @Test
    @DisplayName("Lança NullPointerException se bearing for null")
    void testCaravelNullBearing() {
        IPosition pos = new Position(0, 0);
        NullPointerException ex = assertThrows(NullPointerException.class, () -> new Caravel(null, pos));
        assertTrue(ex.getMessage().contains("invalid bearing"));
    }

    @Test
    @DisplayName("Lança NullPointerException se posição for null")
    void testCaravelNullPosition() {
        assertThrows(NullPointerException.class, () -> new Caravel(Compass.NORTH, null));
    }

    @Test
    @DisplayName("Cria Caravel corretamente em todas as direções")
    void testCaravelOtherDirections() {
        IPosition pos = new Position(5, 5);
        Caravel east = new Caravel(Compass.EAST, pos);
        Caravel south = new Caravel(Compass.SOUTH, pos);
        Caravel west = new Caravel(Compass.WEST, pos);

        assertEquals(2, east.getPositions().size());
        assertEquals(2, south.getPositions().size());
        assertEquals(2, west.getPositions().size());
    }
}
