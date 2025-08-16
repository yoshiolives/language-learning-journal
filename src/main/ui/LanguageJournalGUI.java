package ui;

import model.Event;
import model.EventLog;
import model.VocabJournal;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Graphical user interface for the Language Learning Journal - main window.
 */

public class LanguageJournalGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_FILE = "./data/hsk1_vocab.json";

    private VocabJournal journal;
    private VocabListPanel listPanel;
    private EntryFormPanel formPanel;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JButton saveButton;
    private JButton loadButton;

    // EFFECTS: sets up the main layout and window properties
    public LanguageJournalGUI() {
        super("Language Learning Journal");
        journal = new VocabJournal();
        jsonWriter = new JsonWriter(JSON_FILE);
        jsonReader = new JsonReader(JSON_FILE);

        initializeGraphics();
    }

    // MODIFIES: this
    // EFFECTS: Set up the layout and display main window
    private void initializeGraphics() {
        setupFrame();
        setupPanels();
        setupButtons();

        setupEventLogging();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: Adds a window listener that prints all logged events when app is
    // closed
    private void setupEventLogging() {
        addWindowListener(createWindowListener());

    }

    // EFFECTS: Returns a WindowAdapter
    private WindowAdapter createWindowListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                handleAppExit();
            }
        };
    }

    // EFFECTS: Prints event log to console and exits app
    private void handleAppExit() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }

    // MODIFIES: This
    // EFFECTS: Sets up window size and layout
    private void setupFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLayout(new BorderLayout());
    }

    // MODIFIES: This
    // EFFECTS: initializes the vocab list panel and for panel. Adds to split pane
    private void setupPanels() {
        listPanel = new VocabListPanel();
        formPanel = new EntryFormPanel(journal, listPanel);
        VisualPanel visualPanel = new VisualPanel();

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(formPanel);
        rightPanel.add(visualPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, rightPanel);
        splitPane.setDividerLocation(250);
        splitPane.setResizeWeight(0);
        add(splitPane, BorderLayout.CENTER);
    }

    // MODIFIES: This
    // EFFECTS: Creates save/load buttons and add them to the panel
    private void setupButtons() {
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");

        saveButton.addActionListener(e -> saveJournal());
        loadButton.addActionListener(e -> loadJournal());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // EFFECTS: Writes the journal path.
    private void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved Journal to " + JSON_FILE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "Unable to write to file.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this, listPanel
    // EFFECTS: Loads journal from file and updates the display.
    private void loadJournal() {
        try {
            journal = jsonReader.read();
            listPanel.updateList(journal.getAllEntry());
            formPanel.setJournal(journal);
            JOptionPane.showMessageDialog(this, "Loaded Journal from " + JSON_FILE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Unable to read from file.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}
