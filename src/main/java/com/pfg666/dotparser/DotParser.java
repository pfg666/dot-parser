package com.pfg666.dotparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
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
	 * Parses the graphs from a .dot file and generates corresponding automatalib models.
	 * @param file - location of the file
	 * @return - a list of models. Note that it is assumed that graphs in the .dot file 
	 * describe the same type of model. 
	 * @throws FileNotFoundException 
	 * @throws ParseException
	 */
	public List<A> parseAutomaton(String file) throws FileNotFoundException, ParseException {
		File f = new File(file);
		FileReader reader = new FileReader(f);
		List<A> automata = parseAutomaton(reader);
		return automata;
	}
	
	/**
	 * Parses the graphs from the a .dot contents of an input stream and generates corresponding automatalib models.
	 * @param is - input stream
	 * @return - a list of models. Note that it is assumed that graphs in the stream
	 * describe the same type of model. 
	 * @throws FileNotFoundException 
	 * @throws ParseException
	 */
	public List<A> parseAutomaton(InputStream is) throws FileNotFoundException, ParseException {
		InputStreamReader reader = new InputStreamReader(is);
		List<A> automata = parseAutomaton(reader);
		return automata;
	}
	
	/**
	 * Parses the graphs from the a .dot contents of an reader stream and generates corresponding automatalib models.
	 * @param reader - reader stream
	 * @return - a list of models. Note that it is assumed that graphs in the stream
	 * describe the same type of model. 
	 * @throws FileNotFoundException 
	 * @throws ParseException
	 */
	public List<A> parseAutomaton(Reader reader) throws FileNotFoundException, ParseException {
		List<Graph> graphs = readGraphs(reader);
		List<A> automata = new ArrayList<>(graphs.size());
		for (Graph graph : graphs) {
			A aut = processGraph(graph);
			automata.add(aut);
		}
		
		return automata;
	}
	
	protected abstract A processGraph(Graph g);
	
	private List<Graph> readGraphs(Reader reader) throws ParseException {
		Parser p = new Parser();
		p.parse(reader);
		return p.getGraphs();
	}
}
