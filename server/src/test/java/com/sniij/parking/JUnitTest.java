package com.sniij.parking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitTest {

    @DisplayName("JUnit Test")
    @Test
    public void assertionTest(){
        String expected = "Hello, JUnit";
        String actual = "Hello, World";

        assertEquals(expected, actual); // (2)
    }



}
