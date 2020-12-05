package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletListTest {


    @Test
    void add() {
        BulletList listTest = new BulletList();
        Bullet bullet = new Bullet(1,1,1,1,3);
        listTest.add(bullet);
        assertEquals(1,listTest.getSize(),"gain size");
        listTest.add(bullet);
        assertEquals(2,listTest.getSize(),"gain size");
    }

    @Test
    void remove() {
        BulletList listTest = new BulletList();
        Bullet bullet = new Bullet(1,1,1,1,3);
        listTest.add(bullet);
        listTest.add(bullet);
        listTest.remove(0);
        assertEquals(1,listTest.getSize(),"decrese size");
    }

    @Test
    void getSize() {
        BulletList listTest = new BulletList();
        Bullet bullet = new Bullet(1,1,1,1,3);
        listTest.add(bullet);
        listTest.add(bullet);
        assertEquals(2,listTest.getSize(),"size = 1");
    }
}