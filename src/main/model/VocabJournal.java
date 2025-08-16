package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents a journal that contains multiple vocabulary entries.
 * Allows adding new entries, retriving all entries, and selecting
 * random entries for review.
 */

public class VocabJournal implements Writable {
    private List<VocabEntry> entries;

    // MODIFIES: this
    // EFFECTS: Constructs a new VocabJournal with an empty list.
    public VocabJournal() {
        entries = new ArrayList<>();
    }

    // REQUIRES: entry != null
    // MODIFIES: this
    // EFFECTS: Adds a vocabulary entry to the journal.
    public void addEntry(VocabEntry entry) {
        entries.add(entry);
        EventLog.getInstance()
                .logEvent(new Event("Added entry: " + entry.getForeignWord()
                        + " -> " + entry.getEnglishTranslation()));
    }

    // EFFECTS: Return the number of entries in the journal
    public int getEntryCount() {
        return entries.size();
    }

    // EFFECTS: Return list of all vocab entries.
    public List<VocabEntry> getAllEntry() {
        return entries;
    }

    // EFFECTS: Returns a random VocabEntry
    public VocabEntry getRandomEntry() {
        if (!entries.isEmpty()) {
            Random rand = new Random();
            return entries.get(rand.nextInt(entries.size()));
        }
        return null;
    }

    // EFFETCS: Compute total number of correct answers over all entries.
    public int getTotalCorrectCount() {
        int total = 0;
        for (VocabEntry e : entries) {
            total = total + e.getCorrectCount();
        }
        return total;
    }

    // EFFETCS: Compute total number of inccorrect answers over all entries.
    public int getTotalIncorrectCount() {
        int total = 0;
        for (VocabEntry e : entries) {
            total = total + e.getIncorrectCount();
        }
        return total;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("entries", entriesToJson());
        return jsonObject;
    }

    // EFFECTS: returns entries as a JSON array
    private JSONArray entriesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (VocabEntry entry : entries) {
            jsonArray.put(entry.toJson());
        }
        return jsonArray;
    }

    // MODIFIES: This
    // EFFECTS: Removes the first entry with matching foreign word
    public boolean removeEntry(String foreignWord) {
        for (int i = 0; i < entries.size(); i++) {
            VocabEntry entry = entries.get(i);
            if (entry.getForeignWord().equals(foreignWord)) {
                entries.remove(i);
                EventLog.getInstance().logEvent(new Event("Removed entry: " + foreignWord));
                return true;
            }
        }
        return false;
    }
}
