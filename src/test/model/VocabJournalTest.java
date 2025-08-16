package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/*
 * Unit tests for the VocabJournal class.
 */

public class VocabJournalTest {

    private VocabJournal journal;
    private VocabEntry entry1;
    private VocabEntry entry2;

    @BeforeEach
    public void runBefore() {
        journal = new VocabJournal();
        entry1 = new VocabEntry("chi1", "eat");
        entry2 = new VocabEntry("he1", "drink");
    }

    // EFFETCS: Verifies that the constructor initializes the journal with 0
    // entries.
    @Test
    public void testConstructor() {
        assertEquals(0, journal.getEntryCount());
    }

    // EFFETCS: Verifies that adding one entry increases the entry count to 1.
    @Test
    public void testAddEntry() {
        journal.addEntry(entry1);
        assertEquals(1, journal.getEntryCount());
    }

    // EFFETCS: Verifies that adding 2 entries results in a count of 2.
    @Test
    public void testgetEntryCount() {
        journal.addEntry(entry1);
        journal.addEntry(entry2);
        assertEquals(2, journal.getEntryCount());
    }

    // EFFECTS: Verifies that getAllEntry returns all entries added to the journal.
    @Test
    public void testGetAllEntry() {
        journal.addEntry(entry1);
        journal.addEntry(entry2);
        List<VocabEntry> entries = journal.getAllEntry();
        assertEquals(2, entries.size());
    }

    // EFFECTS: Verifies that getRandomEntry returns null when no etries exist.
    @Test
    public void testGetRandomEntryEmpty() {
        VocabEntry randomEntry = journal.getRandomEntry();
        assertNull(randomEntry);
    }

    // Verifies that getRandomEntry returns either of the two added entries.
    @Test
    public void testGetRandomEntryNotEmpty() {
        journal.addEntry(entry1);
        journal.addEntry(entry2);

        VocabEntry result = journal.getRandomEntry();
        assertTrue(result == entry1 || result == entry2);
    }

    // EFFETCS: Verifies that total correct count across all entries.
    @Test
    public void testTotalCorrectCount() {
        assertEquals(0, journal.getTotalCorrectCount());
        journal.addEntry(entry1);
        journal.addEntry(entry2);
        entry1.testAnswer("eat");
        assertEquals(1, journal.getTotalCorrectCount());
        entry2.testAnswer("drink");
        assertEquals(2, journal.getTotalCorrectCount());
    }

    // EFFETCS: Verifies that total incorrect count across all entries.
    @Test
    public void testTotalIncorrectCount() {
        assertEquals(0, journal.getTotalIncorrectCount());
        journal.addEntry(entry1);
        entry1.testAnswer("dog");
        assertEquals(1, journal.getTotalIncorrectCount());
    }

    // EFFECTS: Verifies that an existing entry can be removed
    @Test
    public void testRemoveExistingEntry() {
        journal.addEntry(entry1);
        assertTrue(journal.removeEntry("chi1"));
        assertEquals(0, journal.getEntryCount());
    }

    // EFFECTS: Check to see if trying to remove a non-exsting entry returns false
    @Test
    public void testRemoveNonExistingEntry() {
        journal.addEntry(entry1);
        assertFalse(journal.removeEntry("na"));
        assertEquals(1, journal.getEntryCount());

    }

    // EFFECTS: Check to see if you can remove multiple entries
    @Test
    public void testRemoveMultipleEntries() {
        journal.addEntry(entry1);
        journal.addEntry(entry2);
        assertTrue(journal.removeEntry("he1"));
        assertEquals(1, journal.getEntryCount());
        assertTrue(journal.getAllEntry().contains(entry1));
        assertFalse(journal.getAllEntry().contains(entry2));
    }
}
