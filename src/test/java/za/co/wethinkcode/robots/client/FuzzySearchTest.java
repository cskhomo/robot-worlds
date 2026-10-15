package za.co.wethinkcode.robots.client;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robots.client.utilities.FuzzySearch;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FuzzySearchTest {

    @Test
    void testFindExactMatch() {
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        List<String> result = FuzzySearch.find(words, "banana", 100);
        assertEquals(1, result.size());
        assertTrue(result.contains("banana"));
    }

    @Test
    void testFindSimilarWords() {
        List<String> words = Arrays.asList("apple", "appl", "apply", "banana");
        List<String> result = FuzzySearch.find(words, "apple", 80);
        // 'apple', 'appl', and 'apply' should be similar enough
        assertTrue(result.contains("apple"));
        assertTrue(result.contains("appl"));
        assertTrue(result.contains("apply"));
        assertFalse(result.contains("banana"));
    }

    @Test
    void testFindNoMatches() {
        List<String> words = Arrays.asList("dog", "cat", "mouse");
        List<String> result = FuzzySearch.find(words, "elephant", 50);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindEmptyList() {
        List<String> words = List.of();
        List<String> result = FuzzySearch.find(words, "anything", 50);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindWithZeroThreshold() {
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        List<String> result = FuzzySearch.find(words, "apple", 0);
        // Zero threshold means everything is considered similar
        assertEquals(words.size(), result.size());
        assertTrue(result.containsAll(words));
    }

    @Test
    void testFindWithHighThreshold() {
        List<String> words = Arrays.asList("apple", "appl", "apply", "ape");
        List<String> result = FuzzySearch.find(words, "apple", 95);
        // Only exact "apple" should match at such a high threshold
        assertEquals(1, result.size());
        assertTrue(result.contains("apple"));
    }
}
