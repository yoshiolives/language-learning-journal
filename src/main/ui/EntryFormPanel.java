package ui;

import model.VocabEntry;
import model.VocabJournal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Panel that contains a form for adding a new vocabulary entry.
 */

public class EntryFormPanel extends JPanel {

    private VocabJournal journal;
    private VocabListPanel listPanel;

    private JTextField foreignField;
    private JTextField englishField;
    private JButton addButton;
    private JButton removeButton;

    // MODIFIES: This
    // EFFECTS: Modifies this journal with a new journal
    public void setJournal(VocabJournal newJournal) {
        this.journal = newJournal;
    }

    // EFFECTS: Creates a panel with form inputs and an add button
    public EntryFormPanel(VocabJournal journal, VocabListPanel listPanel) {
        this.journal = journal;
        this.listPanel = listPanel;

        setLayout(new GridLayout(6, 2));

        foreignField = new JTextField();
        englishField = new JTextField();
        addButton = new JButton("Add Entry");
        removeButton = new JButton("Remove Entry");

        add(new JLabel("Foreign Word:"));
        add(foreignField);
        add(new JLabel("English Translation:"));
        add(englishField);
        add(addButton);
        add(removeButton);

        setupAddListener();
        setupRemoveListener();
    }

    // MODIFIES: journal, listPanel
    // EFFECTS: handles add button click to create a new vocab entry,
    // add it to the journal, and update displayed list.
    private void setupAddListener() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foreign = foreignField.getText().trim();
                String english = englishField.getText().trim();

                if (!foreign.isEmpty() && !english.isEmpty()) {
                    VocabEntry entry = new VocabEntry(foreign, english);
                    journal.addEntry(entry);
                    listPanel.updateList(journal.getAllEntry());

                    foreignField.setText("");
                    englishField.setText("");
                } else {
                    JOptionPane.showMessageDialog(EntryFormPanel.this,
                            "Both field must be filled!",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: journal, listPanel
    // EFFECTS: button to remove entry
    public void setupRemoveListener() {
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String foreign = foreignField.getText().trim();

                if (!foreign.isEmpty()) {
                    handleRemoval(foreign);
                } else {
                    JOptionPane.showMessageDialog(EntryFormPanel.this,
                            "Please enter a foreign word to remove.",
                            "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                }
                foreignField.setText("");
                englishField.setText("");
            }
        });
    }

    // MODIFIES: This
    // EFFECTS: Updates list and shows sucess message
    public void handleRemoval(String foreign) {
        boolean removed = journal.removeEntry(foreign);

        if (removed) {
            listPanel.updateList(journal.getAllEntry());
            JOptionPane.showMessageDialog(EntryFormPanel.this,
                    "Entry removed: " + foreign, "Removed",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(EntryFormPanel.this,
                    "No entry found with the foreign word: " + foreign,
                    "Not Found",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

}
