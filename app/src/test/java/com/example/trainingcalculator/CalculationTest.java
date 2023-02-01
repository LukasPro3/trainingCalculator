package com.example.trainingcalculator;

import junit.framework.TestCase;
import org.junit.Test;

public class CalculationTest extends TestCase {
    Calculation calculation;

    @Test
    public void testGetPace() {
        assertEquals(180.0,calculation.getPace(36,200));
    }

    @Test
    public void testSecondsToPace() {
        assertEquals("00:03:00",Calculation.secondsToPace(180.0));
    }
}