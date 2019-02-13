import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByN {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.

    static CharacterComparator offByOne = new OffByN(5);

    // Your tests go here.
    @Test
    public void testOffByOne() {
        assertTrue(offByOne.equalChars('a', 'f'));
        assertTrue(offByOne.equalChars('A', 'F'));
        assertTrue(offByOne.equalChars('o', 't'));
        assertTrue(offByOne.equalChars('O', 'T'));
        assertFalse(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('A', 'f'));
        assertTrue(offByOne.equalChars('#', '('));
        assertTrue(offByOne.equalChars('&', '+'));
        assertFalse(offByOne.equalChars('o', 'q'));
        assertTrue(offByOne.equalChars('u', 'z'));
        assertTrue(offByOne.equalChars('Z', 'U'));
        System.out.println("Test for OffByN passed");
    }
}
