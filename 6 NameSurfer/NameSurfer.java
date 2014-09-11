/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		resize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		
		// Configure input box
		nameField = new TextField(TEXT_INPUT_LENGTH);
		add(new Label("Name"), SOUTH);
		add(nameField, SOUTH);
		nameField.addActionListener(this);
		
		// Add buttons
		add(new Button("Graph"), SOUTH);
		add(new Button("Clear"), SOUTH);
		addActionListeners();
		
		// Add grid lines and labels
		add(graph);
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (e.getSource() == nameField || cmd == "Graph") {
			graph.addEntry(database.findEntry(nameField.getText()));
			graph.update();
		} else if (cmd == "Clear") {
			graph.clear();
			graph.update();
		}
	}
	
	private TextField nameField;
	private NameSurferDataBase database = new NameSurferDataBase("names-data.txt");
	private NameSurferGraph graph = new NameSurferGraph();
	
	private static final int TEXT_INPUT_LENGTH = 40;
}
