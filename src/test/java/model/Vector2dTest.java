package model;

import darwin.model.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    void equals(){

        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(-1,2);
        Vector2d v4 = new Vector2d(1,-2);
        Vector2d v5 = new Vector2d(-1,-2);
        Vector2d v6 = new Vector2d(0,0);
        Vector2d v7 = new Vector2d(2,1);

        //when then
        assertTrue(v1.equals(v2));
        assertFalse(v1.equals(v3));
        assertFalse(v1.equals(v4));
        assertFalse(v1.equals(v5));
        assertFalse(v1.equals(v6));
        assertFalse(v1.equals(v7));
    }

    @Test
    void testToString(){
        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(-1,2);
        Vector2d v3 = new Vector2d(1,-2);
        Vector2d v4 = new Vector2d(-1,-2);
        Vector2d v5 = new Vector2d(0,0);

        //when then
        assertEquals("(1,2)", v1.toString());
        assertEquals("(-1,2)", v2.toString());
        assertEquals("(1,-2)", v3.toString());
        assertEquals("(-1,-2)", v4.toString());
        assertEquals("(0,0)", v5.toString());
    }

    @Test
    void testPrecedes(){

        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,3);
        Vector2d v3 = new Vector2d(1,3);
        Vector2d v4 = new Vector2d(2,2);
        Vector2d v5 = new Vector2d(0,0);

        //when then
        assertTrue(v1.precedes(v1));
        assertTrue(v1.precedes(v2));
        assertTrue(v1.precedes(v3));
        assertTrue(v1.precedes(v4));
        assertFalse(v1.precedes(v5));
    }

    @Test
    void testFollows(){

        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(1,1);
        Vector2d v3 = new Vector2d(0,2);
        Vector2d v4 = new Vector2d(0,0);

        Vector2d v5 = new Vector2d(2,2);
        Vector2d v6 = new Vector2d(1,3);
        Vector2d v7 = new Vector2d(3,4);

        //when then
        assertTrue(v1.follows(v1));
        assertTrue(v1.follows(v2));
        assertTrue(v1.follows(v3));
        assertTrue(v1.follows(v4));

        assertFalse(v1.follows(v5));
        assertFalse(v1.follows(v6));
        assertFalse(v1.follows(v7));
    }

    @Test
    void upperRight(){
        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(2,2);

        Vector2d v4 = new Vector2d(1,1);
        Vector2d v5 = new Vector2d(2,2);
        Vector2d v6 = new Vector2d(2,2);

        Vector2d v7 = new Vector2d(0,0);

        Vector2d v8 = new Vector2d(-1,-2);
        Vector2d v9 = new Vector2d(-2,-1);
        Vector2d v10 = new Vector2d(-1,-1);

        Vector2d v11 = new Vector2d(-1,-1);
        Vector2d v12 = new Vector2d(-2,-2);
        Vector2d v13 = new Vector2d(-1,-1);

        //when then
        assertEquals(v3,v2.upperRight(v2));

        assertEquals(v3, v1.upperRight(v2));
        assertEquals(v3, v2.upperRight(v1));

        assertEquals(v6, v4.upperRight(v5));
        assertEquals(v6, v5.upperRight(v4));

        assertEquals(v7, v7.upperRight(v7));

        assertEquals(v10, v8.upperRight(v9));
        assertEquals(v10, v9.upperRight(v8));

        assertEquals(v13, v11.upperRight(v12));
        assertEquals(v13, v12.upperRight(v11));
    }

    @Test
    void lowerLeft(){

        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(1,2);

        Vector2d v4 = new Vector2d(1,1);
        Vector2d v5 = new Vector2d(2,2);
        Vector2d v6 = new Vector2d(1,1);

        Vector2d v7 = new Vector2d(0,0);

        Vector2d v8 = new Vector2d(-1,-2);
        Vector2d v9 = new Vector2d(-2,-1);
        Vector2d v10 = new Vector2d(-2,-2);

        Vector2d v11 = new Vector2d(-1,-1);
        Vector2d v12 = new Vector2d(-2,-2);
        Vector2d v13 = new Vector2d(-2,-2);

        //when then
        assertEquals(v3,v1.lowerLeft(v1));

        assertEquals(v3,v1.lowerLeft(v2));
        assertEquals(v3,v2.lowerLeft(v1));

        assertEquals(v6,v4.lowerLeft(v5));
        assertEquals(v6,v5.lowerLeft(v4));

        assertEquals(v7,v7.lowerLeft(v7));

        assertEquals(v10,v8.lowerLeft(v9));
        assertEquals(v10,v9.lowerLeft(v8));

        assertEquals(v13,v11.lowerLeft(v12));
        assertEquals(v13,v12.lowerLeft(v11));
    }

    @Test
    void add(){

        //given
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(3,4);

        Vector2d v4 = new Vector2d(0,0);
        Vector2d v5 = new Vector2d(2,2);
        Vector2d v6 = new Vector2d(2,2);

        Vector2d v7 = new Vector2d(-1,3);
        Vector2d v8 = new Vector2d(-2,-1);
        Vector2d v9 = new Vector2d(-3,2);

        Vector2d v10 = new Vector2d(0,0);
        Vector2d v11 = new Vector2d(0,0);
        Vector2d v12 = new Vector2d(0,0);

        //when then
        assertEquals(v3,v1.add(v2));
        assertEquals(v3,v2.add(v1));

        assertEquals(v6,v4.add(v5));
        assertEquals(v6,v5.add(v4));

        assertEquals(v9,v7.add(v8));
        assertEquals(v9,v8.add(v7));
    }

    @Test
    void subtract(){

        //given
        Vector2d v1 = new Vector2d(2,3);
        Vector2d v2 = new Vector2d(1,2);
        Vector2d v3 = new Vector2d(1,1);

        Vector2d v4 = new Vector2d(2,3);
        Vector2d v5 = new Vector2d(0,0);
        Vector2d v6 = new Vector2d(2,3);

        Vector2d v7 = new Vector2d(-1,-3);
        Vector2d v8 = new Vector2d(-2,-5);
        Vector2d v9 = new Vector2d(1,2);

        Vector2d v10 = new Vector2d(-2,-3);
        Vector2d v11 = new Vector2d(-1,-2);
        Vector2d v12 = new Vector2d(-1,-1);

        Vector2d v13 = new Vector2d(0,0);
        Vector2d v14 = new Vector2d(0,0);
        Vector2d v15 = new Vector2d(0,0);

        //when then
        assertEquals(v3,v1.subtract(v2));
        assertEquals(v6,v4.subtract(v5));
        assertEquals(v9,v7.subtract(v8));
        assertEquals(v12,v10.subtract(v11));
        assertEquals(v15,v13.subtract(v14));

    }

    @Test
    void opposite(){
        Vector2d v1 = new Vector2d(1,2);
        Vector2d v2 = new Vector2d(-1,-2);

        Vector2d v3 = new Vector2d(-2,-1);
        Vector2d v4 = new Vector2d(2,1);

        Vector2d v5 = new Vector2d(0,0);
        Vector2d v6 = new Vector2d(0,0);

        assertEquals(v2,v1.opposite());
        assertEquals(v4,v3.opposite());
        assertEquals(v6,v5.opposite());
    }
}

