package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * Unit tests for the VocabEntry class.
 */

public class VocabEntryTest {

    private VocabEntry entry;

    @BeforeEach
    void runBefore() {
        entry = new VocabEntry("chi1", "eat");
    }

    // EFFECTS: Verifies that constructor correctly initializes fields
    @Test
    public void testConstructor() {
        assertEquals("chi1", entry.getForeignWord());
        assertEquals("eat", entry.getEnglishTranslation());
        assertEquals(0, entry.getCorrectCount());
        assertEquals(0, entry.getIncorrectCount());
        assertNull(entry.getLastReviewed());
    }

    // EFFETCS: Verifies that LastReviewes is null
    @Test
    public void testGetLastReviewedNull() {
        assertNull(entry.getLastReviewed());
    }

    // EFFETCS: Check LastReviewed
    @Test
    public void testGetLastReviewed() {
        LocalDateTime expected = LocalDateTime.of(2025, 1, 1, 0, 0);
        entry.setLastReviewed(expected);
        assertEquals(expected, entry.getLastReviewed());
    }

    // EFFECTS: Verifies testAnswer returns false for the wrong answer
    // and true for correct answer.
    @Test
    public void testTestAnswer() {
        assertFalse(entry.testAnswer("drink"));
        assertTrue(entry.testAnswer("eat"));
    }

    // EFFETCS: Verifies getAccuracyRate returns 0.0 initially,
    // then updates corectlly based on answers.
    @Test
    public void testGetAccuracy() {
        assertEquals(0.0, entry.getAccuracyRate(), 0.0001);
        entry.testAnswer("eat");
        assertEquals(1.0, entry.getAccuracyRate(), 0.0001);
        entry.testAnswer("potato");
        assertEquals(0.5, entry.getAccuracyRate(), 0.0001);
    }

    // EFFECTS: toJson() with set LastReviewed populated
    @Test
    public void testToJsonLastReviewedNull() {
        JSONObject json = entry.toJson();
        assertTrue(json.isNull("lastReviewed"));
    }

    // EFFECTS: toJson() with set LastReviewed populated
    @Test
    public void testToJsonLastReviewed() {
        LocalDateTime expected = LocalDateTime.of(2025, 1, 1, 0, 0);
        entry.setLastReviewed(expected);
        JSONObject json = entry.toJson();
        assertEquals(expected.toString(), json.getString("lastReviewed"));
    }
}
