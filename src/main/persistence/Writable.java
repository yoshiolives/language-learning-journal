package persistence;

/*
 * CITATION: This class is modeled after the JsonReader class provided in the CPSC210 sample project.
 * Reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
