package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import model.VocabEntry;
import model.VocabJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

/*
 * Console-based UI interface for the Language Learning Journal App.
 * Displays menu options and handles user input
 */

public class LanguageJournalApp {

    private VocabJournal journal;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_FILE = "./data/journal.json";

    // EFFECTS: Run the journal App
    public LanguageJournalApp() {
        journal = new VocabJournal();
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);
        input = new Scanner(System.in);
        runJournal();
    }

    // EFFECTS: Menu loop for App
    private void runJournal() {
        boolean keepRunning = true;

        while (keepRunning) {
            displayMenu();
            String key = input.nextLine();
            keepRunning = handleCommands(key);
        }
    }

    // EFFECTS handles user commands and returns false if user quits
    @SuppressWarnings("methodlength")
    private boolean handleCommands(String key) {
        switch (key) {
            case "1":
                doAddEntry();
                break;
            case "2":
                doViewAllEntries();
                break;
            case "3":
                doPractice();
                break;
            case "4":
                doStats();
                break;
            case "5":
                saveJournal();
                break;
            case "6":
                loadJournal();
                break;
            case "0":
                System.out.println("Goodbye!");
                return false;
            default:
                System.out.println("That was an invalid option");
                break;
        }
        return true;
    }

    // EFFECTS: Prints menu options
    private void displayMenu() {
        System.out.println("Welcome to the Language Learning Journal");
        System.out.println("Select an option:");
        System.out.println("1. Add a new word");
        System.out.println("2. View all entries");
        System.out.println("3. Practice");
        System.out.println("4. View stats");
        System.out.println("5. Save Journal to file");
        System.out.println("6. Load Journal from file");
        System.out.println("0. Exit");
    }

    // EFFECTS: Prompts user to add a new word entry
    private void doAddEntry() {
        System.out.println("Enter the foreign word: ");
        String foreign = input.nextLine();
        System.out.println("Enter the English translation: ");
        String english = input.nextLine();
        VocabEntry newVocab = new VocabEntry(foreign, english);
        journal.addEntry(newVocab);
        System.out.println("Word added.");
    }

    // EFFECTS: Display all entries in the Journal
    private void doViewAllEntries() {
        List<VocabEntry> entries = journal.getAllEntry();
        if (entries.isEmpty()) {
            System.out.println("No entries.");
        } else {
            System.out.println("Foreign/English word");
            for (VocabEntry e : entries) {
                System.out.println("[" + e.getForeignWord() + " / " + e.getEnglishTranslation() + "]");
            }
        }
    }

    // EFFETCS: Quizes user on a random word
    private void doPractice() {
        VocabEntry entry = journal.getRandomEntry();
        if (entry == null) {
            System.out.println("No words to practice yet!");
            return;
        }

        System.out.println("Translate this word: " + entry.getForeignWord());
        String answer = input.nextLine();

        if (entry.testAnswer(answer)) {
            System.out.println("Correct!");
        } else {
            System.out.println("Incorrect. Correct answer is: " + entry.getEnglishTranslation());
        }
    }

    // EFFETCS: Displays overall stats
    private void doStats() {
        System.out.println("Total words: " + journal.getEntryCount());
        System.out.println("Correct answers: " + journal.getTotalCorrectCount());
        System.out.println("Incorrect answers: " + journal.getTotalIncorrectCount());
    }

    // MODIFIES: File system
    // EFFECTS: saves the current state of the VocabJournal to file
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved journal to " + JSON_FILE);

        } catch (FileNotFoundException e) {
            System.out.println("Saved journal to " + JSON_FILE);
        }

    }

    // MODIFIES: File system
    // EFFECTS: saves the current state of the VocabJournal to file
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded journal from " + JSON_FILE);

        } catch (IOException e) {
            System.out.println("Saved journal to " + JSON_FILE);
        }

    }

}
