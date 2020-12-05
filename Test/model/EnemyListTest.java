package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyListTest {

    @Test
    void add() {
        EnemyList listTest = new EnemyList();
        listTest.add(1,1);
        assertEquals(1,listTest.getSize(),"gain size");
        listTest.add(2,2);
        assertEquals(2,listTest.getSize(),"gain size");
    }

    @Test
    void get() {
        EnemyList listTest = new EnemyList();
        listTest.add(1,1);
        listTest.add(2,2);
        assertTrue(listTest.get(0) instanceof Enemy);
        assertTrue(listTest.get(1) instanceof Enemy);
    }

    @Test
    void getSize() {
        EnemyList listTest = new EnemyList();
        listTest.add(1,1);
        assertEquals(1,listTest.getSize(), "size = 1");
        listTest.add(2,2);
        assertEquals(2,listTest.getSize(), "size = 2");

    }

    @Test
    void remove() {
        EnemyList listTest = new EnemyList();
        listTest.add(1,1);
        listTest.remove(0);
        assertEquals(0,listTest.getSize(), "size = 0");
        listTest.add(2,2);
        listTest.add(3,3);
        assertEquals(2,listTest.getSize(), "size = 2");
    }
}