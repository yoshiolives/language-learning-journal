package persistence;

/*
 * CITATION: This class is modeled after the JsonReader class provided in the CPSC210 sample project.
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import model.VocabEntry;
import model.VocabJournal;
//import persistence.JsonReader;
//import persistence.JsonTest;

public class JsonReaderTest extends JsonTest {

    // EFFECTS: Insure exception is thrown when the file does not exist.
    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    // EFFECTS: test the reader when the VocabJournal is empty
    @Test
    public void testReaderEmptyVocabJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            VocabJournal journal = reader.read();
            assertEquals(0, journal.getEntryCount());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    // EFFECTS: test the reader when the VocabJournal contains items
    @Test
    public void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderJournal.json");
        try {
            VocabJournal journal = reader.read();
            List<VocabEntry> entries = journal.getAllEntry();
            assertEquals(2, entries.size());
            checkVocabEntry("bonjour", "hello", 3, 1, entries.get(0));
            checkVocabEntry("chien", "dog", 0, 2, entries.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
