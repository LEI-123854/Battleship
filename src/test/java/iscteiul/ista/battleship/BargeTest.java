package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BargeTest {

    @Test
    @DisplayName("Deve criar uma Barge corretamente e retornar o tamanho correto")
    void testBargeCreationAndSize() {
        // Arrange
        Compass bearing = Compass.NORTH;                // direção
        IPosition pos = new Position(2, 3);             // posição inicial

        // Act
        Barge barge = new Barge(bearing, pos);

        // Assert
        assertAll("Propriedades da Barge",
                () -> assertEquals(1, barge.getSize(), "O tamanho da barge deve ser 1"),
                () -> assertEquals("Barca", barge.getName(), "O nome da barge deve ser 'Barca'"),
                () -> assertEquals(bearing, barge.getBearing(), "O bearing deve ser o mesmo passado no construtor"),
                () -> assertEquals(1, barge.getPositions().size(), "A barge deve ter exatamente uma posição"),
                () -> {
                    IPosition bargePos = barge.getPositions().get(0);
                    assertEquals(pos.getRow(), bargePos.getRow(), "A linha da posição da barge deve ser igual à posição passada");
                    assertEquals(pos.getColumn(), bargePos.getColumn(), "A coluna da posição da barge deve ser igual à posição passada");
                }
        );
    }
}
