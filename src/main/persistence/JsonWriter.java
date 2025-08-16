package persistence;

/*
 * CITATION: This class is modeled after the JsonReader class provided in the CPSC210 sample project.
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import java.io.*;

import org.json.JSONObject;

import model.Event;
import model.VocabJournal;
import model.EventLog;

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIEs: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIED: this
    // EFFECTS: Writes JSON representation of VocabJournal to file
    public void write(VocabJournal journal) {
        JSONObject jsonObject = journal.toJson();
        saveToFile(jsonObject.toString(TAB));
        EventLog.getInstance().logEvent(new Event("Saved Journal to file."));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
