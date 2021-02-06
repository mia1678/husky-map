package deque.palindrome;

import deque.Deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class PalindromeTest {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }


    @Test
    public void isPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("Noon"));
        assertTrue(palindrome.isPalindrome("!!"));
        assertTrue(palindrome.isPalindrome("RRARR"));
        assertTrue(palindrome.isPalindrome("121"));



        assertFalse(palindrome.isPalindrome("horse"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertFalse(palindrome.isPalindrome("{rancor"));
        assertFalse(palindrome.isPalindrome("Rancor"));
        assertFalse(palindrome.isPalindrome("ra!!__or"));
        assertFalse(palindrome.isPalindrome("12345"));
        assertFalse(palindrome.isPalindrome("!?"));


    }

    @Test
    public void isPalindrome2() {
        assertTrue(palindrome.isPalindrome("", new OffByOne()));
        assertTrue(palindrome.isPalindrome("flake", new OffByOne()));
        assertTrue(palindrome.isPalindrome("{|", new OffByOne()));

    }


}
