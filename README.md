# Language Learning Journal
*Track and review your vocabulary in a new language.*

The **Language Learning Journal** is a desktop application designed to help users grow and retain their vocabulary while learning a new language. Users can store new words and phrases, test themselves with quizzes, and track their vocabulary history over time.

This project is intended for **language learners of all levels** who want a structured way to review and reinforce vocabulary. As someone currently learning Mandarin Chinese, I often find that scattered word lists and flashcards lack long-term memory support. This project combines my passion for languages with my interest in building practical tools. 

## Features
- Add and review vocabulary entries with translations 
- Practice quizzes to reinforce learning 
- Track review stats (accuracy, last reviewed time) 
- Save and reload progress between sessions (JSON persistence) 
- Event logging of user actions (add/remove entries, save/load, quizzes) 
- Modular architecture with packages (model, persistence, ui) and UML documentation

 
## Example Event Log
Tue Aug 05 20:14:57 PDT 2025 - Added entry: maca -> apple
Tue Aug 05 20:15:31 PDT 2025 - Removed entry: maca
Tue Aug 05 20:15:40 PDT 2025 - Loaded journal from file
Tue Aug 05 20:15:43 PDT 2025 - Saved Journal to file

## Instructions for End User
- You can add multiple vocabulary entries to a journal by filling in the form on the right and clicking the "Add Entry" button.
- You can add multiple vocabulary entries to a journal by loading a vocabulary file using the "Load" button at the bottom.
- You can locate my visual component by looking at the program image displayed beneath the Add Entry form on the right side of the app.
- You can save the state of the application by clicking "Save" botton at the bottom of the app window. 
- You can reload the state of the application by clicking the "Load" button at the bottom of the app window. 

## Built With
- Java 17
- Swing (GUI)
- JSON (org.json library for persistence)
- JUnit 5 (testing)