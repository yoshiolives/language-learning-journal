package persistence;

/*
 * CITATION: This class is modeled after the JsonReader class provided in the CPSC210 sample project.
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.json.*;

import model.Event;
import model.EventLog;
import model.VocabEntry;
import model.VocabJournal;

// Represents a reader that reads VocabJournal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads VocabJournal from file and returns it;
    // throws IOException if an error occurs reading data from file
    public VocabJournal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded journal from file"));
        return parseVocabJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses VocabJournal from JSON object and returns it
    private VocabJournal parseVocabJournal(JSONObject jsonObject) {
        VocabJournal journal = new VocabJournal();
        addEntries(journal, jsonObject);
        return journal;
    }

    // MODIFIES: journal
    // EFFECTS: parses entries from JSON object and adds them to workroom
    private void addEntries(VocabJournal journal, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("entries");
        for (Object json : jsonArray) {
            JSONObject entryJson = (JSONObject) json;
            VocabEntry entry = parseEntry(entryJson);
            journal.addEntry(entry);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses journal from JSON object and adds it to VocabJournal
    private VocabEntry parseEntry(JSONObject jsonObject) {
        String foreignWord = jsonObject.getString("foreignWord");
        String englishTranslation = jsonObject.getString("englishTranslation");
        int correctCount = jsonObject.getInt("correctCount");
        int incorrectCount = jsonObject.getInt("incorrectCount");

        // Parse LastReviewed
        LocalDateTime lastReviewed = null;
        if (!jsonObject.isNull("lastReviewed")) {
            lastReviewed = LocalDateTime.parse(jsonObject.getString("lastReviewed"));
        }

        VocabEntry entry = new VocabEntry(foreignWord, englishTranslation);

        // Set review stats: Correct answer
        for (int i = 0; i < correctCount; i++) {
            entry.testAnswer(englishTranslation);
        }
        // Set review stats: Inorrect answer
        for (int i = 0; i < incorrectCount; i++) {
            entry.testAnswer("wrong");
        }

        // Set LastReviewed
        if (lastReviewed != null) {
            entry.setLastReviewed(lastReviewed);
        }
        return entry;
    }
}
