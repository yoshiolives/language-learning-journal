package ui;

import model.VocabEntry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*
 * This panel will sit on the left side of GUI and show all the vocab entries currently in the journal.
 */

public class VocabListPanel extends JPanel {
    private DefaultListModel<String> listModel;
    private JList<String> vocabJlist;

    // EFFECTS: Constructs the vocabulary list panel with a scrollable list view.
    public VocabListPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 0));
        listModel = new DefaultListModel<>();
        vocabJlist = new JList<>(listModel);
        add(new JScrollPane(vocabJlist), BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: Clears the current list and repopulated with given entries.
    public void updateList(List<VocabEntry> entries) {
        listModel.clear();
        for (VocabEntry entry : entries) {
            listModel.addElement(entry.getForeignWord() + " - " + entry.getEnglishTranslation());
        }
    }

}
