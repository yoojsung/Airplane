package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {

    @Test
    void move() {
        Bullet bullet = new Bullet(1,1,1,1,3);
        bullet.move();
        assertEquals(4, bullet.getY(),"move Y by 3");

    }
}