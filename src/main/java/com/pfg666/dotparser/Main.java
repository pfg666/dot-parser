package com.pfg666.dotparser;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import com.alexmerz.graphviz.ParseException;
import com.pfg666.dotparser.fsm.mealy.BasicStringMealyProcessor;
import com.pfg666.dotparser.fsm.mealy.MealyDotParser;

import net.automatalib.automata.transout.impl.FastMealy;

public class Main {
	
	public static void main(String args[]) throws FileNotFoundException, ParseException {
		PrintStream out = System.out;
		if (args.length != 1) {
			printUsage(out);
		} else {
			MealyDotParser<String, String> dotParser = new MealyDotParser<String, String>(new BasicStringMealyProcessor());
			List<FastMealy<String, String>> automata = dotParser.parseAutomaton(args[0]);
			printAutomata(out, automata);
		}
	}
	
	public static void printUsage(PrintStream ps) {
		ps.println("Usage:");
		ps.println("java -jar dot-parser.jar dotFile");
	}
	
	public static void printAutomata(PrintStream ps, List<FastMealy<String, String>> automata) {
		int index = 1;
		for (FastMealy<String, String> aut : automata) {
			ps.format("Automaton %d with %d states\n", index, aut.getStates().size());
		}
	}
}
