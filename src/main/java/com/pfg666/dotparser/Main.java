package com.pfg666.dotparser;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import com.alexmerz.graphviz.ParseException;
import com.pfg666.dotparser.fsm.dfa.BasicStringDFAProcessor;
import com.pfg666.dotparser.fsm.dfa.DFADotParser;
import com.pfg666.dotparser.fsm.mealy.BasicStringMealyProcessor;
import com.pfg666.dotparser.fsm.mealy.MealyDotParser;

import net.automatalib.automata.Automaton;
import net.automatalib.automata.fsa.impl.FastDFA;
import net.automatalib.automata.transducers.impl.FastMealy;

public class Main {
	
	public static void main(String args[]) throws FileNotFoundException, ParseException {
		PrintStream out = System.out;
		if (args.length != 2 || !(args[1].equals("Mealy") || args[1].equals("DFA"))) {
			printUsage(out);
		} else {
			if (args[1].equals("Mealy") ) {
				MealyDotParser<String, String> dotParser = new MealyDotParser<String, String>(new BasicStringMealyProcessor());
				List<FastMealy<String, String>> automata = dotParser.parseAutomaton(args[0]);
				printAutomata(out, automata);
			} else {
				DFADotParser<String> dotParser = new DFADotParser<String>(new BasicStringDFAProcessor());
				List<FastDFA<String>> automata = dotParser.parseAutomaton(args[0]);
				printAutomata(out, automata);
			}
		}
	}
	
	public static void printUsage(PrintStream ps) {
		ps.println("Usage:");
		ps.println("java -jar dot-parser.jar Mealy|DFA dotFile");
	}
	
	public static void printAutomata(PrintStream ps, List<? extends Automaton<?,?,?>> automata) {
		int index = 1;
		for (Automaton<?, ?, ?> aut : automata) {
			ps.format("Automaton %d with %d states\n", index, aut.getStates().size());
		}
	}
}
