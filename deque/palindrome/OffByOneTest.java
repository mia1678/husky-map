package deque.palindrome;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OffByOneTest {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void equalChars(){
        // True
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('r', 'q'));
        assertTrue(offByOne.equalChars('{', '|'));
        assertFalse(offByOne.equalChars('R', 'q'));


        // False
        assertFalse(offByOne.equalChars('a', 'e'));
        assertFalse(offByOne.equalChars('z', 'a'));
        assertFalse(offByOne.equalChars('a', 'a'));


    }
}
