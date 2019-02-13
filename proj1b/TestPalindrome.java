import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIspalindrome() {
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome("noon"));
        assertFalse(palindrome.isPalindrome("seugnri"));
        assertFalse(palindrome.isPalindrome("rancor"));
        assertTrue(palindrome.isPalindrome("a"));
        assertTrue(palindrome.isPalindrome(" "));
        assertFalse(palindrome.isPalindrome("Racecar"));
        assertFalse(palindrome.isPalindrome("racercaR"));
        assertTrue(palindrome.isPalindrome("RacecaR"));
        System.out.println("ispalindrome test passed");

    }

    @Test
    public void testOffByOne() {
        CharacterComparator obo = new OffByOne();
        assertTrue(palindrome.isPalindrome("acdb", obo));
        assertTrue(palindrome.isPalindrome("%l&", obo));
        assertFalse(palindrome.isPalindrome("abcd", obo));
        assertTrue(palindrome.isPalindrome("AcdB", obo));
        assertTrue(palindrome.isPalindrome("a", obo));
        assertTrue(palindrome.isPalindrome(" ", obo));
        assertTrue(palindrome.isPalindrome("K", obo));
        assertTrue(palindrome.isPalindrome("&", obo));
        assertFalse(palindrome.isPalindrome("oHjp", obo));
        assertTrue(palindrome.isPalindrome("jahibk", obo));
        assertTrue(palindrome.isPalindrome("EjqLrkF", obo));
        System.out.println("ispalindrome for OffByOne test passed");
    }

    @Test
    public void testOffByN() {
        CharacterComparator obn = new OffByN(5);
        assertTrue(palindrome.isPalindrome("afaf", obn));
        assertTrue(palindrome.isPalindrome("AfaF", obn));
        assertTrue(palindrome.isPalindrome("&$)+", obn));
        assertTrue(palindrome.isPalindrome("&$p)+", obn));
        assertTrue(palindrome.isPalindrome("AflaF", obn));
        assertTrue(palindrome.isPalindrome(" ", obn));
        assertTrue(palindrome.isPalindrome("O", obn));
        assertTrue(palindrome.isPalindrome("PjqvoU", obn));
        assertTrue(palindrome.isPalindrome("PjqavoU", obn));
        System.out.println("ispalindrome for OffByN test passed");

    }
}