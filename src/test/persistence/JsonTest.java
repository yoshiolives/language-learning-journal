package persistence;

/*
 * CITATION: This class is modeled after the JsonReader class provided in the CPSC210 sample project.
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.VocabEntry;

public class JsonTest {

    // EFFECTS: Checks that filds for given VocabEntry match expected values
    protected void checkVocabEntry(String foreign, String english, int correct, int incorrect, VocabEntry entry) {
        assertEquals(foreign, entry.getForeignWord());
        assertEquals(english, entry.getEnglishTranslation());
        assertEquals(correct, entry.getCorrectCount());
        assertEquals(incorrect, entry.getIncorrectCount());
    }
}
