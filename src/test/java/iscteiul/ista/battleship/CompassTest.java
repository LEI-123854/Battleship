package iscteiul.ista.battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompassTest {

    @Test
    void getDirection() {
        assertEquals('n', Compass.NORTH.getDirection());
        assertEquals('s', Compass.SOUTH.getDirection());
        assertEquals('e', Compass.EAST.getDirection());
        assertEquals('o', Compass.WEST.getDirection());
        assertEquals('u', Compass.UNKNOWN.getDirection());
    }

    @Test
    void testToString() {
        assertEquals("n", Compass.NORTH.toString());
        assertEquals("s", Compass.SOUTH.toString());
        assertEquals("e", Compass.EAST.toString());
        assertEquals("o", Compass.WEST.toString());
        assertEquals("u", Compass.UNKNOWN.toString());
    }

    @Test
    void charToCompass_ValidValues() {
        assertEquals(Compass.NORTH, Compass.charToCompass('n'));
        assertEquals(Compass.SOUTH, Compass.charToCompass('s'));
        assertEquals(Compass.EAST, Compass.charToCompass('e'));
        assertEquals(Compass.WEST, Compass.charToCompass('o'));
    }

    @Test
    void charToCompass_InvalidValues() {
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('x'));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass(' '));
        assertEquals(Compass.UNKNOWN, Compass.charToCompass('1'));
    }

    @Test
    void values_ShouldContainAllDirections() {
        Compass[] expected = {Compass.NORTH, Compass.SOUTH, Compass.EAST, Compass.WEST, Compass.UNKNOWN};
        assertArrayEquals(expected, Compass.values(), "O enum deve conter todas as direções esperadas");
    }

    @Test
    void valueOf_ShouldReturnCorrectEnumConstant() {
        assertEquals(Compass.NORTH, Compass.valueOf("NORTH"));
        assertEquals(Compass.SOUTH, Compass.valueOf("SOUTH"));
        assertEquals(Compass.EAST, Compass.valueOf("EAST"));
        assertEquals(Compass.WEST, Compass.valueOf("WEST"));
        assertEquals(Compass.UNKNOWN, Compass.valueOf("UNKNOWN"));
    }

    @Test
    void valueOf_InvalidValue_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> Compass.valueOf("INVALID"));
    }
}
