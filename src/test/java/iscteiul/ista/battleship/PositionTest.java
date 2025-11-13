package iscteiul.ista.battleship;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//a
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
    @DisplayName("Deve comparar igualdade corretamente (cobertura total)")
    void testEquals() {
        Position position = new Position(2, 3); // ← adiciona esta linha
        Position same = new Position(2, 3);
        Position differentRow = new Position(1, 3);
        Position differentColumn = new Position(2, 4);
        Object unrelated = new Object();

        assertAll("Testes de igualdade",
                () -> assertTrue(position.equals(position), "Um objeto deve ser igual a si próprio"),
                () -> assertTrue(position.equals(same), "Mesmas coordenadas devem ser iguais"),
                () -> assertFalse(position.equals(differentRow), "Linhas diferentes não devem ser iguais"),
                () -> assertFalse(position.equals(differentColumn), "Colunas diferentes não devem ser iguais"),
                () -> assertFalse(position.equals(null), "Não deve ser igual a null"),
                () -> assertFalse(position.equals(unrelated), "Não deve ser igual a um tipo não relacionado")
        );
    }

    @Test
    void isAdjacentTo() {
        Position center = new Position(3, 3);

        // ➤ Mesma posição (deve ser adjacente a si mesma)
        assertTrue(center.isAdjacentTo(new Position(3, 3)),
                "Uma posição deve ser adjacente a si mesma");

        // ➤ Adjacentes horizontais
        assertTrue(center.isAdjacentTo(new Position(3, 2)),
                "Posição imediatamente à esquerda deve ser adjacente");
        assertTrue(center.isAdjacentTo(new Position(3, 4)),
                "Posição imediatamente à direita deve ser adjacente");

        // ➤ Adjacentes verticais
        assertTrue(center.isAdjacentTo(new Position(2, 3)),
                "Posição acima deve ser adjacente");
        assertTrue(center.isAdjacentTo(new Position(4, 3)),
                "Posição abaixo deve ser adjacente");

        // ➤ Adjacentes diagonais
        assertTrue(center.isAdjacentTo(new Position(2, 2)),
                "Posição na diagonal superior esquerda deve ser adjacente");
        assertTrue(center.isAdjacentTo(new Position(2, 4)),
                "Posição na diagonal superior direita deve ser adjacente");
        assertTrue(center.isAdjacentTo(new Position(4, 2)),
                "Posição na diagonal inferior esquerda deve ser adjacente");
        assertTrue(center.isAdjacentTo(new Position(4, 4)),
                "Posição na diagonal inferior direita deve ser adjacente");

        // ➤ Não adjacentes (distância maior que 1)
        assertFalse(center.isAdjacentTo(new Position(5, 3)),
                "Posição duas linhas abaixo não deve ser adjacente");
        assertFalse(center.isAdjacentTo(new Position(3, 5)),
                "Posição duas colunas à direita não deve ser adjacente");
        assertFalse(center.isAdjacentTo(new Position(5, 5)),
                "Posição diagonal distante não deve ser adjacente");
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
