package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    private Position p1;
    private Position p2;

    @BeforeEach
    void setUp() {
        p1 = new Position(3, 5);
        p2 = new Position(4, 6);
    }

    @Test
    void getRow() {
        assertEquals(3, p1.getRow(), "Deve devolver a linha correta");
    }

    @Test
    void getColumn() {
        assertEquals(5, p1.getColumn(), "Deve devolver a coluna correta");
    }

    @Test
    void testHashCode() {
        // Duas posições iguais devem ter o mesmo hashCode
        Position p3 = new Position(3, 5);
        assertEquals(p1.hashCode(), p3.hashCode(), "hashCode deve ser igual para posições iguais");
    }

    @Test
    void testEquals_SameObject() {
        assertTrue(p1.equals(p1), "Um objeto deve ser igual a si mesmo");
    }

    @Test
    void testEquals_DifferentObjectSameCoords() {
        Position p3 = new Position(3, 5);
        assertTrue(p1.equals(p3), "Posições com mesma linha e coluna devem ser iguais");
    }

    @Test
    void testEquals_DifferentCoords() {
        assertFalse(p1.equals(p2), "Posições diferentes não devem ser iguais");
    }

    @Test
    void testEquals_NullOrDifferentType() {
        assertFalse(p1.equals(null), "Deve retornar falso para null");
        assertFalse(p1.equals("string"), "Deve retornar falso para objetos de outro tipo");
    }

    @Test
    void isAdjacentTo_TrueCases() {
        // Posições diagonais ou vizinhas diretas são adjacentes
        assertTrue(p1.isAdjacentTo(p2), "Posições diagonais devem ser adjacentes");
        assertTrue(p1.isAdjacentTo(new Position(3, 6)), "Mesma linha, coluna adjacente deve ser adjacente");
        assertTrue(p1.isAdjacentTo(new Position(2, 5)), "Mesma coluna, linha adjacente deve ser adjacente");
    }

    @Test
    void isAdjacentTo_FalseCases() {
        Position far = new Position(10, 10);
        assertFalse(p1.isAdjacentTo(far), "Posições distantes não devem ser adjacentes");
    }

    @Test
    void occupy() {
        assertFalse(p1.isOccupied(), "Inicialmente não deve estar ocupada");
        p1.occupy();
        assertTrue(p1.isOccupied(), "Após ocupar, deve estar ocupada");
    }

    @Test
    void shoot() {
        assertFalse(p1.isHit(), "Inicialmente não deve estar atingida");
        p1.shoot();
        assertTrue(p1.isHit(), "Após disparo, deve estar atingida");
    }

    @Test
    void isOccupied() {
        assertFalse(p1.isOccupied());
        p1.occupy();
        assertTrue(p1.isOccupied());
    }

    @Test
    void isHit() {
        assertFalse(p1.isHit());
        p1.shoot();
        assertTrue(p1.isHit());
    }

    @Test
    void testToString() {
        String expected = "Linha = 3 Coluna = 5";
        assertEquals(expected, p1.toString(), "toString deve retornar a string formatada corretamente");
    }
}
