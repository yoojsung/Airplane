package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirplaneTest {
    Airplane airplane = new Airplane(1,1,1,1);
    @Test
    void getPoint() {
        assertEquals(airplane.getPoint(),0,"get point should be 0 by default");
    }

    @Test
    void getHp() {
        assertEquals(airplane.getHp(), 5, "Hp should be 5 by default");
    }

    @Test
    void decHp() {
        airplane.decHp();
        assertEquals(airplane.getHp(),4, "hp 1 decrease");;
        airplane.decHp();
        assertEquals(airplane.getHp(),3, "hp 1 decrease");;
    }

    @Test
    void gainPoint() {
        airplane.gainPoint(1);
        assertEquals(airplane.getPoint(),1,"total 1 point");
        airplane.gainPoint(1);
        assertEquals(airplane.getPoint(),2,"total 2 point");


    }
}