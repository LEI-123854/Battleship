package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarrackTest {

    @Test
    @DisplayName("Cria Carrack na direção NORTH corretamente")
    void testCarrackNorth() {
        Carrack c = new Carrack(Compass.NORTH, new Position(2, 3));

        assertEquals(3, c.getSize());
        assertEquals("Nau", c.getName());
        assertEquals(Compass.NORTH, c.getBearing());

        assertEquals(3, c.getPositions().size());
    }

    @Test
    @DisplayName("Cria Carrack na direção SOUTH corretamente")
    void testCarrackSouth() {
        Carrack c = new Carrack(Compass.SOUTH, new Position(1, 1));
        assertEquals(3, c.getPositions().size());
    }

    @Test
    @DisplayName("Cria Carrack na direção EAST corretamente")
    void testCarrackEast() {
        Carrack c = new Carrack(Compass.EAST, new Position(4, 4));
        assertEquals(3, c.getPositions().size());
    }

    @Test
    @DisplayName("Cria Carrack na direção WEST corretamente")
    void testCarrackWest() {
        Carrack c = new Carrack(Compass.WEST, new Position(7, 7));
        assertEquals(3, c.getPositions().size());
    }

    @Test
    @DisplayName("Lança NullPointerException se bearing for null")
    void testNullBearing() {
        assertThrows(NullPointerException.class,
                () -> new Carrack(null, new Position(0, 0)));
    }

    @Test
    @DisplayName("Lança NullPointerException se pos for null")
    void testNullPosition() {
        assertThrows(NullPointerException.class,
                () -> new Carrack(Compass.NORTH, null));
    }

    @Test
    @DisplayName("getSize devolve sempre 3")
    void testGetSize() {
        Carrack c = new Carrack(Compass.NORTH, new Position(0, 0));
        assertEquals(3, c.getSize());
    }
}
