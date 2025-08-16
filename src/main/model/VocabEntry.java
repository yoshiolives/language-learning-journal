package model;

import java.time.LocalDateTime;

import org.json.JSONObject;

import persistence.Writable;

/*
 * Represents a vocabulary entry in a language learning journal.
 * Each entry stores the foreign word, its English translation and
 * review statistics as correct/incorrect counts and last review date
 */

public class VocabEntry implements Writable {

    private String foreignWord;
    private String englishTranslation;
    private int correctCount;
    private int incorrectCount;
    private LocalDateTime lastReviewed;

    // REQUIRES: foreignWord and englishTranslation != null
    // MODIFIES: this
    // EFFECTS: Initializes field with a given word and its translation with zeroed
    // review statistics
    public VocabEntry(String foreignWord, String englishTranslation) {
        this.foreignWord = foreignWord;
        this.englishTranslation = englishTranslation;
        this.correctCount = 0;
        this.incorrectCount = 0;
        this.lastReviewed = null;
    }

    // EFFECTS: Gets the foreign word.
    public String getForeignWord() {
        return this.foreignWord;
    }

    // EFFECTS: Gets the English Translation
    public String getEnglishTranslation() {
        return this.englishTranslation;
    }

    // EFFETCS: Gets number of correct answers.
    public int getCorrectCount() {
        return this.correctCount;
    }

    // EFFETCS: Gets number of incorrect answers.
    public int getIncorrectCount() {
        return this.incorrectCount;
    }

    // EFFETCS: Gets last reviewed timestamp.
    public LocalDateTime getLastReviewed() {
        return this.lastReviewed;
    }

    // REQUIRES: user input != null
    // MODIFIES: this
    // EFFECTS: Test whether the user input matches the correct English translation

    public boolean testAnswer(String userInput) {
        lastReviewed = LocalDateTime.now();
        if (userInput.equals(englishTranslation)) {
            correctCount++;
            EventLog.getInstance().logEvent(
                    new Event("Correct answer for: " + foreignWord + " (expected: " + englishTranslation + ")"));
            return true;
        } else {
            incorrectCount++;
            EventLog.getInstance().logEvent(
                    new Event("Incorrect answer for: " + foreignWord + " (expected: " + englishTranslation + ")"));
            return false;
        }
    }

    // EFFECTS: Compute the accuracy rate of this entry.
    public double getAccuracyRate() {
        int total = correctCount + incorrectCount;
        if (total == 0) {
            return 0.0;
        }
        return (double) correctCount / total;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("foreignWord", foreignWord);
        jsonObject.put("englishTranslation", englishTranslation);
        jsonObject.put("correctCount", correctCount);
        jsonObject.put("incorrectCount", incorrectCount);
        jsonObject.put("lastReviewed", (lastReviewed != null) ? lastReviewed.toString() : JSONObject.NULL);
        return jsonObject;
    }

    // MODIFIES: this
    // EFFECTS: sets the last reviewed time when loading from file

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }
}
