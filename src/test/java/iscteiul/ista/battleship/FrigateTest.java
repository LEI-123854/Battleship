package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrigateTest {

    @Test
    @DisplayName("A fragata deve ter sempre tamanho 4")
    void testSizeIsAlwaysFour() {
        Frigate f = new Frigate(Compass.NORTH, new Position(0, 0));
        assertEquals(4, f.getSize());
    }

    @Test
    @DisplayName("Construção da fragata orientada a Norte deve gerar 4 posições consecutivas na linha crescente")
    void testFrigateNorth() {
        Position start = new Position(2, 3);
        Frigate f = new Frigate(Compass.NORTH, start);

        assertEquals(4, f.getPositions().size());
        assertEquals(new Position(2, 3), f.getPositions().get(0));
        assertEquals(new Position(3, 3), f.getPositions().get(1));
        assertEquals(new Position(4, 3), f.getPositions().get(2));
        assertEquals(new Position(5, 3), f.getPositions().get(3));
    }

    @Test
    @DisplayName("Construção da fragata orientada a Sul deve gerar 4 posições consecutivas na linha crescente (igual a Norte)")
    void testFrigateSouth() {
        Position start = new Position(5, 1);
        Frigate f = new Frigate(Compass.SOUTH, start);

        assertEquals(4, f.getPositions().size());
        assertEquals(new Position(5, 1), f.getPositions().get(0));
        assertEquals(new Position(6, 1), f.getPositions().get(1));
        assertEquals(new Position(7, 1), f.getPositions().get(2));
        assertEquals(new Position(8, 1), f.getPositions().get(3));
    }

    @Test
    @DisplayName("Construção da fragata orientada a Este deve gerar 4 posições consecutivas na coluna crescente")
    void testFrigateEast() {
        Position start = new Position(4, 4);
        Frigate f = new Frigate(Compass.EAST, start);

        assertEquals(4, f.getPositions().size());
        assertEquals(new Position(4, 4), f.getPositions().get(0));
        assertEquals(new Position(4, 5), f.getPositions().get(1));
        assertEquals(new Position(4, 6), f.getPositions().get(2));
        assertEquals(new Position(4, 7), f.getPositions().get(3));
    }

    @Test
    @DisplayName("Construção da fragata orientada a Oeste deve gerar 4 posições consecutivas na coluna crescente (igual a Este)")
    void testFrigateWest() {
        Position start = new Position(3, 7);
        Frigate f = new Frigate(Compass.WEST, start);

        assertEquals(4, f.getPositions().size());
        assertEquals(new Position(3, 7), f.getPositions().get(0));
        assertEquals(new Position(3, 8), f.getPositions().get(1));
        assertEquals(new Position(3, 9), f.getPositions().get(2));
        assertEquals(new Position(3, 10), f.getPositions().get(3));
    }

    @Test
    @DisplayName("Deve lançar exceção se o bearing for nulo")
    void testNullBearingThrowsException() {
        assertThrows(
                NullPointerException.class,
                () -> new Frigate(null, new Position(0, 0))
        );
    }
}
//
