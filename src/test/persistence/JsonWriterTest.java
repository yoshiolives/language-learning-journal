package persistence;

/*
 * CITATION: This class is modeled after the JsonReader class provided in the CPSC210 sample project.
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.VocabEntry;
import model.VocabJournal;

class JsonWriterTest extends JsonTest {

    private VocabJournal journal;

    @BeforeEach
    void runBefore() {
        journal = new VocabJournal();
    }

    // EFFECTS: tests that the writer throws IOException
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // EFFECTS: tests writting and reading an empty journal
    @Test
    void testWriterEmptyWorkroom() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            VocabJournal loaded = reader.read();
            assertEquals(0, loaded.getEntryCount());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // EFFECTS: tests writting and reading a journal with multiple entries
    @Test
    void testWriterGeneralWorkroom() {
        try {
            journal = createSampleJournal();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(journal);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            VocabJournal loaded = reader.read();
            List<VocabEntry> entries = loaded.getAllEntry();

            assertEquals(2, entries.size());
            checkVocabEntry("bonjour", "hello", 2, 1, entries.get(0));
            checkVocabEntry("chien", "dog", 0, 2, entries.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    // MODIFIES: This
    // EFFECTS: creates and returns a VocabJournal with 2 entries test status
    private VocabJournal createSampleJournal() {
        VocabEntry entry1 = new VocabEntry("bonjour", "hello");
        VocabEntry entry2 = new VocabEntry("chien", "dog");

        entry1.testAnswer("hello"); // +1 correct
        entry1.testAnswer("hi"); // +1 incorrect
        entry1.testAnswer("hello"); // +1 correct

        entry2.testAnswer("cat"); // +1 incorrect
        entry2.testAnswer("cat"); // +1 incorrect

        entry1.setLastReviewed(LocalDateTime.now());
        entry2.setLastReviewed(LocalDateTime.now());

        journal.addEntry(entry1);
        journal.addEntry(entry2);
        return journal;
    }

}