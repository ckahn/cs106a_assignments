/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

@SuppressWarnings("serial")
public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		update();
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		
		int width = getWidth();
		int height = getHeight();
		
		addVerticalLines(width, height);
		addHorizontalLines(width, height);
		addDecadeLabels(width, height);
		plotValues(width, height);
	}
	
	private void addVerticalLines(int width, int height) {
		int x = 0;
		for(int n = 0; n < NDECADES; n++) {
			add(new GLine(x, 0, x, height));
			x += width/NDECADES+1;
		}
	}
	
	private void addHorizontalLines(int width, int height) {
		add(new GLine(0, GRAPH_MARGIN_SIZE, width, GRAPH_MARGIN_SIZE));
		add(new GLine(0, height-GRAPH_MARGIN_SIZE, width, 
				height-GRAPH_MARGIN_SIZE));
	}
	
	private void addDecadeLabels(int width, int height) {
		int yr = 1900;
		int x = 3;
		for (int n = 0; n < NDECADES; n++) {
			int decade = yr+n*10;
			add(new GLabel(""+decade, x, height-4));
			x += width/NDECADES+1;
		}
	}
	
	/**
	 * Plots all the lines from the list of entries.
	 */
	private void plotValues(int width, int height) {
		if (values.isEmpty()) { return; }
			
		// Loop through entries in list; rotate color per entry
		Color colors[] = {Color.black, Color.red, Color.blue, Color.magenta};
		for(int entry = 0; entry < values.size(); entry++) {
			Color color = colors[entry%4];
			
			// Loop through decades in entry
			double yUnit = (height-GRAPH_MARGIN_SIZE*2)/1000.0;
			int x1 = 0;
			int y1 = GRAPH_MARGIN_SIZE + (int)((getRank(entry, 0)) * yUnit);
			for(int decade = 1; decade < NDECADES; decade++) {
				
				int x2 = x1+width/NDECADES+1;
				int y2 = GRAPH_MARGIN_SIZE + (int)((getRank(entry, decade)) * yUnit);
				
				GLabel lab1 = new GLabel(getLabel(entry,decade-1), x1+2, y1-2);
				lab1.setColor(color);
				add(lab1);
				
				GLine line = new GLine(x1, y1, x2, y2);
				line.setColor(color);
				add(line);
				
				GLabel lab2 = new GLabel(getLabel(entry,decade), x2+2, y2-2);
				lab2.setColor(color);
				add(lab2);
				
				x1 = x2;
				y1 = y2;
			}
		}
	}
	
	private String getLabel(int entry, int decade) {
		String label = values.get(entry).getName();
		int rank = values.get(entry).getRank(decade);
		if (rank != 0) {
			label += " " + rank;
		} else {
			label += " " + "*";
		}
		return label;
	}

	private int getRank(int entry, int decade) {
		int rank = values.get(entry).getRank(decade);
		if (rank == 0) { rank = 1000; }
		return rank;
	}

	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		values.clear();
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		if (entry != null) { values.add(entry); }
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
	
	GLabel lab;
	private ArrayList<NameSurferEntry> values = new ArrayList<NameSurferEntry>();
}
