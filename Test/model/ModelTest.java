package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelTest {

    @Test
    void isRoundStarted() {
        Model model = new Model();
        assertFalse(model.isRoundStarted());
    }

    @Test
    void isRoundOver() {
        Model model = new Model();
        assertFalse(model.isRoundOver());
    }
}