package com.pfg666.dotparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.alexmerz.graphviz.ParseException;
import com.alexmerz.graphviz.Parser;
import com.alexmerz.graphviz.objects.Graph;

import net.automatalib.automata.Automaton;

public abstract class DotParser<A extends Automaton<?,?,?>> {
	
	public DotParser() {
	}
	
	/**
	 * Parses the graphs in a .dot file and generates corresponding automatalib models.
	 * @param file - location of the file
	 * @return - a list of models. Note that it is assumed that graphs in the .dot file 
	 * describe the same type of model. 
	 * @throws FileNotFoundException 
	 * @throws ParseException
	 */
	public List<A> parseAutomaton(String file) throws FileNotFoundException, ParseException {
		List<Graph> graphs = readGraphs(file);
		List<A> automata = new ArrayList<>(graphs.size());
		for (Graph graph : graphs) {
			A aut = processGraph(graph);
			automata.add(aut);
		}
		
		return automata;
	}
	
	protected abstract A processGraph(Graph g);
	

	private List<Graph> readGraphs(String file) throws FileNotFoundException, ParseException {
		File f = new File(file);
		FileReader in = new FileReader(f);
		Parser p = new Parser();
		p.parse(in);
		return p.getGraphs();
	} 
}
