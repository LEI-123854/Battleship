package iscteiul.ista.battleship;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {

    @Test
    @DisplayName("Fleet começa vazia")
    void testGetShipsInitiallyEmpty() {
        Fleet f = new Fleet();
        assertTrue(f.getShips().isEmpty());
    }

    @Test
    @DisplayName("addShip adiciona um navio válido")
    void testAddValidShip() {
        Fleet f = new Fleet();
        Ship s = new Frigate(Compass.NORTH, new Position(0, 0));

        assertTrue(f.addShip(s));
        assertEquals(1, f.getShips().size());
    }

    @Test
    @DisplayName("addShip falha para navio fora do tabuleiro")
    void testAddShipOutsideBoard() {
        Fleet f = new Fleet();
        Ship s = new Frigate(Compass.NORTH, new Position(20, 20)); // completamente fora
        assertFalse(f.addShip(s));
        assertTrue(f.getShips().isEmpty());
    }

    @Test
    @DisplayName("addShip impede colisões entre navios do mesmo tipo")
    void testCollisionRiskSameType() {
        Fleet f = new Fleet();

        Ship s1 = new Frigate(Compass.NORTH, new Position(2, 2));
        Ship s2 = new Frigate(Compass.NORTH, new Position(3, 2)); // muito próximo → colisão

        assertTrue(f.addShip(s1));
        assertFalse(f.addShip(s2));
    }

    @Test
    @DisplayName("addShip impede colisões entre navios de tipos diferentes")
    void testCollisionRiskDifferentTypes() {
        Fleet f = new Fleet();

        Ship galleon = new Galleon(Compass.NORTH, new Position(0, 0));
        Ship frigate = new Frigate(Compass.NORTH, new Position(0, 0)); // overlap com galleon

        assertTrue(f.addShip(galleon));
        assertFalse(f.addShip(frigate));
    }

    @Test
    @DisplayName("addShip null deve lançar NullPointerException")
    void testAddShipNull() {
        Fleet f = new Fleet();
        assertThrows(NullPointerException.class, () -> f.addShip(null));
    }

    @Test
    @DisplayName("getShipsLike devolve navios da categoria pedida")
    void testGetShipsLike() {
        Fleet f = new Fleet();

        f.addShip(new Frigate(Compass.NORTH, new Position(0, 0)));
        f.addShip(new Galleon(Compass.NORTH, new Position(5, 5)));

        List<IShip> frigs = f.getShipsLike("Fragata");

        assertEquals(1, frigs.size());
        assertEquals("Fragata", frigs.get(0).getCategory());
    }

    @Test
    @DisplayName("getShipsLike devolve lista vazia quando não há navios da categoria")
    void testGetShipsLikeEmpty() {
        Fleet f = new Fleet();

        f.addShip(new Frigate(Compass.NORTH, new Position(0, 0)));

        List<IShip> res = f.getShipsLike("Nau");
        assertTrue(res.isEmpty());
    }

    @Test
    @DisplayName("getFloatingShips devolve apenas navios não afundados")
    void testGetFloatingShips() {
        Fleet f = new Fleet();

        Ship s1 = new Frigate(Compass.NORTH, new Position(0, 0));
        Ship s2 = new Frigate(Compass.NORTH, new Position(5, 5));

        f.addShip(s1);
        f.addShip(s2);

        // simular afundamento de s1
        for (IPosition p : s1.getPositions())
            s1.shoot(p);

        List<IShip> flot = f.getFloatingShips();

        assertEquals(1, flot.size());
        assertEquals(s2, flot.get(0));
    }

    @Test
    @DisplayName("shipAt devolve navio correto")
    void testShipAt() {
        Fleet f = new Fleet();

        Ship s = new Frigate(Compass.NORTH, new Position(3, 3));
        f.addShip(s);

        IPosition p = new Position(3, 3);
        assertEquals(s, f.shipAt(p));
    }

    @Test
    @DisplayName("shipAt devolve null quando não há navio na posição")
    void testShipAtReturnsNull() {
        Fleet f = new Fleet();

        f.addShip(new Frigate(Compass.NORTH, new Position(0, 0)));

        assertNull(f.shipAt(new Position(9, 9)));
    }

    @Test
    @DisplayName("Testes básicos para printStatus e prints relacionados")
    void testPrintMethods() {
        Fleet f = new Fleet();
        f.addShip(new Frigate(Compass.NORTH, new Position(0, 0)));
        f.addShip(new Galleon(Compass.NORTH, new Position(5, 5)));

        // redirecionar System.out
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        f.printStatus(); // deve executar sem lançar exceção
        f.printFloatingShips();
        f.printShipsByCategory("Fragata");
        f.printShipsByCategory("Galeao");
        f.printAllShips();

        System.setOut(originalOut);

        assertTrue(outContent.toString().contains("Fragata"));
        assertTrue(outContent.toString().contains("Galeao"));
    }

    @Test
    @DisplayName("Navios no limite do tabuleiro devem ser adicionáveis se couberem")
    void testAddShipOnBoardEdge() {
        Fleet f = new Fleet();

        Ship s1 = new Frigate(Compass.NORTH, new Position(6, 0)); // cabe na vertical
        Ship s2 = new Frigate(Compass.EAST, new Position(0, 6));  // cabe na horizontal

        assertTrue(f.addShip(s1));
        assertTrue(f.addShip(s2));
    }
}