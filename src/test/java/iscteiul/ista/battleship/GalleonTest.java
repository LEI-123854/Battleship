package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GalleonTest {

    @Test
    @DisplayName("Galleon getSize deve devolver 5")
    void testGetSize() {
        Galleon g = new Galleon(Compass.NORTH, new Position(0, 0));
        assertEquals(5, g.getSize());
    }

    @Test
    @DisplayName("Galleon orientado a NORTH deve ter posições corretas")
    void testPositionsNorth() {
        Galleon g = new Galleon(Compass.NORTH, new Position(10, 10));
        List<IPosition> pos = g.getPositions();

        assertEquals(5, pos.size());
        assertTrue(pos.contains(new Position(10, 10)));
        assertTrue(pos.contains(new Position(10, 11)));
        assertTrue(pos.contains(new Position(10, 12)));
        assertTrue(pos.contains(new Position(11, 11)));
        assertTrue(pos.contains(new Position(12, 11)));
    }

    @Test
    @DisplayName("Galleon orientado a SOUTH deve ter posições corretas")
    void testPositionsSouth() {
        Galleon g = new Galleon(Compass.SOUTH, new Position(5, 5));
        List<IPosition> pos = g.getPositions();

        assertEquals(5, pos.size());
        assertTrue(pos.contains(new Position(5, 5)));
        assertTrue(pos.contains(new Position(6, 5)));
        assertTrue(pos.contains(new Position(7, 4)));
        assertTrue(pos.contains(new Position(7, 5)));
        assertTrue(pos.contains(new Position(7, 6)));
    }

    @Test
    @DisplayName("Galleon orientado a EAST deve ter posições corretas")
    void testPositionsEast() {
        Galleon g = new Galleon(Compass.EAST, new Position(3, 3));
        List<IPosition> pos = g.getPositions();

        assertEquals(5, pos.size());
        assertTrue(pos.contains(new Position(3, 3)));
        assertTrue(pos.contains(new Position(4, 1)));
        assertTrue(pos.contains(new Position(4, 2)));
        assertTrue(pos.contains(new Position(4, 3)));
        assertTrue(pos.contains(new Position(5, 3)));
    }

    @Test
    @DisplayName("Galleon orientado a WEST deve ter posições corretas")
    void testPositionsWest() {
        Galleon g = new Galleon(Compass.WEST, new Position(2, 2));
        List<IPosition> pos = g.getPositions();

        assertEquals(5, pos.size());
        assertTrue(pos.contains(new Position(2, 2)));
        assertTrue(pos.contains(new Position(3, 2)));
        assertTrue(pos.contains(new Position(3, 3)));
        assertTrue(pos.contains(new Position(3, 4)));
        assertTrue(pos.contains(new Position(4, 2)));
    }

    @Test
    @DisplayName("Deve lançar NullPointerException se o bearing for nulo")
    void testNullBearing() {
        assertThrows(NullPointerException.class, () ->
                new Galleon(null, new Position(0, 0))
        );
    }
}
