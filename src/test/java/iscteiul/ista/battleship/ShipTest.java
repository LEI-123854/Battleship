package iscteiul.ista.battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Ship.
 */
class ShipTest {

    private Ship ship;
    private Compass north = Compass.NORTH;
    private IPosition basePos;

    // Mock mínimo de IPosition
    static class MockPosition implements IPosition {
        private int row, col;
        private boolean hit = false;
        private boolean occupied = false;

        MockPosition(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int getRow() {
            return row;
        }

        @Override
        public int getColumn() {
            return col;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof IPosition)) return false;
            IPosition p = (IPosition) o;
            return p.getRow() == row && p.getColumn() == col;
        }

        @Override
        public boolean isHit() {
            return hit;
        }

        @Override
        public void shoot() {
            hit = true;
        }

        @Override
        public boolean isAdjacentTo(IPosition pos) {
            int dr = Math.abs(pos.getRow() - row);
            int dc = Math.abs(pos.getColumn() - col);
            return dr <= 1 && dc <= 1;
        }

        @Override
        public void occupy() {
            occupied = true;
        }

        public boolean isOccupied() {
            return occupied;
        }

        @Override
        public String toString() {
            return "(" + row + "," + col + ")";
        }
    }


    // Mock concreto de Ship
    static class TestShip extends Ship {
        public TestShip(String category, Compass bearing, IPosition pos) {
            super(category, bearing, pos);
            // Exemplo simples: 3 posições horizontais
            positions.add(new MockPosition(pos.getRow(), pos.getColumn()));
            positions.add(new MockPosition(pos.getRow(), pos.getColumn() + 1));
            positions.add(new MockPosition(pos.getRow(), pos.getColumn() + 2));
        }

        @Override
        public Integer getSize() {
            return positions.size();
        }
    }

    @BeforeEach
    void setUp() {
        basePos = new MockPosition(2, 3);
        ship = new TestShip("fragata", north, basePos);
    }

    @Test
    void getCategory() {
        assertEquals("fragata", ship.getCategory());
    }

    @Test
    void getPositions() {
        List<IPosition> pos = ship.getPositions();
        assertEquals(3, pos.size());
        assertEquals(2, pos.get(0).getRow());
    }

    @Test
    void getPosition() {
        assertEquals(basePos, ship.getPosition());
    }

    @Test
    void getBearing() {
        assertEquals(north, ship.getBearing());
    }

    @Test
    void stillFloating() {
        assertTrue(ship.stillFloating());
        // Marca todas como atingidas
        ship.getPositions().forEach(IPosition::shoot);
        assertFalse(ship.stillFloating());
    }

    @Test
    void getTopMostPos() {
        assertEquals(2, ship.getTopMostPos());
    }

    @Test
    void getBottomMostPos() {
        assertEquals(2, ship.getBottomMostPos());
    }

    @Test
    void getLeftMostPos() {
        assertEquals(3, ship.getLeftMostPos());
    }

    @Test
    void getRightMostPos() {
        assertEquals(5, ship.getRightMostPos());
    }

    @Test
    void occupies() {
        assertTrue(ship.occupies(new MockPosition(2, 3)));
        assertFalse(ship.occupies(new MockPosition(3, 3)));
    }

    @Test
    void tooCloseToPosition() {
        IPosition near = new MockPosition(2, 6); // adjacente a (2,5)
        IPosition far = new MockPosition(5, 10);
        assertTrue(ship.tooCloseTo(near));
        assertFalse(ship.tooCloseTo(far));
    }

    @Test
    void tooCloseToShip() {
        IShip other = new TestShip("barca", north, new MockPosition(2, 6)); // adjacente
        assertTrue(ship.tooCloseTo(other));

        IShip farShip = new TestShip("barca", north, new MockPosition(10, 10));
        assertFalse(ship.tooCloseTo(farShip));
    }

    @Test
    void shoot() {
        IPosition target = ship.getPositions().get(1);
        assertFalse(target.isHit());
        ship.shoot(target);
        assertTrue(target.isHit());
    }

    @Test
    void buildShip() {
        Ship galeao = Ship.buildShip("galeao", Compass.NORTH, new Position(1, 1));
        Ship fragata = Ship.buildShip("fragata", Compass.EAST, new Position(1, 2));
        Ship invalido = Ship.buildShip("submarino", Compass.SOUTH, new Position(1, 3));
        Ship barca = Ship.buildShip("barca", Compass.NORTH, new Position(1, 4));
        Ship caravela = Ship.buildShip("caravela", Compass.EAST, new Position(1, 5));
        Ship nau = Ship.buildShip("nau", Compass.NORTH, new Position(1, 9));



        assertNotNull(galeao);
        assertNotNull(fragata);
        assertNull(invalido);
        assertNotNull(barca);
        assertNotNull(caravela);
        assertNotNull(nau);

    }
    @Test
    void buildShipComParametrosNulos() {
        // bearing nulo
        assertThrows(AssertionError.class, () -> {
            Ship.buildShip("nau", null, new Position(1, 10));
        });

        // posição nula
        assertThrows(AssertionError.class, () -> {
            Ship.buildShip("nau", Compass.NORTH, null);
        });
    }
    @Test
    void getTopMostPos_DeveRetornarMenorLinhaEntreAsPosicoes() {
        // Mock ship com posições em diferentes linhas
        Ship shipCustom = new Ship("teste", Compass.NORTH, new MockPosition(5, 3)) {
            {
                positions.add(new MockPosition(5, 3)); // linha 5
                positions.add(new MockPosition(3, 3)); // linha 3 -> mais acima
                positions.add(new MockPosition(7, 3)); // linha 7
            }

            @Override
            public Integer getSize() {
                return positions.size();
            }
        };

        // Deve devolver a menor linha (3)
        assertEquals(3, shipCustom.getTopMostPos(),
                "O método deve retornar a linha mais pequena (posição mais acima).");
    }
    @Test
    void getBottomMostPos_DeveRetornarMaiorLinhaEntreAsPosicoes() {
        // Mock ship com posições em diferentes linhas
        Ship shipCustom = new Ship("teste", Compass.SOUTH, new MockPosition(5, 3)) {
            {
                positions.add(new MockPosition(5, 3)); // linha 5
                positions.add(new MockPosition(3, 3)); // linha 3
                positions.add(new MockPosition(7, 3)); // linha 7 ->mais abaixo
            }

            @Override
            public Integer getSize() {
                return positions.size();
            }
        };

        // Deve devolver a menor linha (3)
        assertEquals(7, shipCustom.getBottomMostPos(),
                "O método deve retornar a linha mais pequena (posição mais acima).");
    }

    @Test
    void getLeftMostPos_DeveRetornarLinhaMaisEsquerda() {
        // Mock ship com posições em diferentes linhas
        Ship shipCustom = new Ship("teste", Compass.NORTH, new MockPosition(5, 3)) {
            {
                positions.add(new MockPosition(2, 2)); // linha 5
                positions.add(new MockPosition(3, 5)); // linha 3 -> mais acima
                positions.add(new MockPosition(7, 1)); // linha 7
            }

            @Override
            public Integer getSize() {
                return positions.size();
            }
        };

        // Deve devolver a menor linha (3)
        assertEquals(1, shipCustom.getLeftMostPos(),
                "O método deve retornar a coluna mais pequena.");
    }

    @Test
    void getRightMostPos_DeveRetornarLinhaMaisDireita() {
        // Mock ship com posições em diferentes linhas
        Ship shipCustom = new Ship("teste", Compass.NORTH, new MockPosition(5, 3)) {
            {
                positions.add(new MockPosition(5, 5)); // linha 5
                positions.add(new MockPosition(3, 3)); // linha 3 -> mais acima
                positions.add(new MockPosition(7, 2)); // linha 7
            }

            @Override
            public Integer getSize() {
                return positions.size();
            }
        };

        // Deve devolver a menor linha (3)
        assertEquals(5, shipCustom.getRightMostPos(),
                "O método deve retornar a linha mais pequena (posição mais acima).");
    }

    @Test
    void tooCloseTo_DeveLancarAssertionErrorSeShipNulo() {
        Ship shipCustom = new TestShip("teste", Compass.NORTH, new MockPosition(2, 3));

        // Passando null deve disparar AssertionError
        assertThrows(AssertionError.class, () -> shipCustom.tooCloseTo((IShip) null),
                "Deve lançar AssertionError se o navio passado for null.");
    }
    @Test
    void shoot_DeveLancarAssertionErrorSePosicaoNula() {
        Ship shipCustom = new TestShip("teste", Compass.NORTH, new MockPosition(2, 3));

        // Passando null deve disparar AssertionError
        assertThrows(AssertionError.class, () -> shipCustom.shoot(null),
                "Deve lançar AssertionError se a posição passada for null.");
    }
    @Test

    void testToStringContainsInfo() {
        String str = ship.toString();
        assertTrue(str.contains(ship.getCategory()));
        assertTrue(str.contains(Compass.NORTH.toString()));
        assertTrue(str.contains(ship.toString()));
    }
    @Test
    void occupies_DeveLancarAssertionErrorSePosicaoNula() {
        Ship shipCustom = new TestShip("teste", Compass.NORTH, new MockPosition(2, 3));

        // Passando null deve disparar AssertionError
        assertThrows(AssertionError.class, () -> shipCustom.occupies(null),
                "Deve lançar AssertionError se a posição passada for null.");
    }

}
