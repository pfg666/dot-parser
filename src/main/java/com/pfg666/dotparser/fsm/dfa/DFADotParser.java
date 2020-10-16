package com.pfg666.dotparser.fsm.dfa;

import java.util.List;
import java.util.Map;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Graph;
import com.alexmerz.graphviz.objects.Node;
import com.pfg666.dotparser.exceptions.MalformedNodeException;
import com.pfg666.dotparser.fsm.FSMConstants;
import com.pfg666.dotparser.fsm.FSMDotParser;

import net.automatalib.automata.fsa.impl.FastDFA;
import net.automatalib.automata.fsa.impl.FastDFAState;
import net.automatalib.words.Alphabet;
import net.automatalib.words.impl.SimpleAlphabet;

public class DFADotParser<L> extends FSMDotParser<FastDFAState, L, FastDFAState, FastDFA<L>>{
	
	private DFAProcessor<L> processor;

	public DFADotParser(DFAProcessor<L> processor) {
		this.processor = processor;
	}
	
	protected FastDFA<L> processGraph(Graph g) {
		Alphabet<L> inputAlphabet = new SimpleAlphabet<>();
		FastDFA<L> muttable = new FastDFA<L>(inputAlphabet);
		Map<Node, FastDFAState> nodeToState = super.addStates(g, muttable);
		
		// making accepting/rejecting
		for (Map.Entry<Node, FastDFAState> entry : nodeToState.entrySet()) {
			String shapeVal = entry.getKey().getAttribute(FSMConstants.NODE_ATTR_SHAPE);
			if (FSMConstants.NODE_ATTR_SHAPE_ACCEPTING.equals(shapeVal)) {
				entry.getValue().setAccepting(true);
			} else {
				if (shapeVal == null || FSMConstants.NODE_ATTR_SHAPE_REJECTING.equals(shapeVal)) {
					entry.getValue().setAccepting(false);	
				} else {
					throw new MalformedNodeException(entry.getKey(), "Unrecognized shape " + shapeVal);
				}
			}
		}
		
		List<Edge> edges = g.getEdges();
		
		for (Edge edge : edges) {
			String strLabel = edge.getAttribute(FSMConstants.EDGE_ATTR_LABEL);
			if (strLabel == null) {
				continue;
			}
			L label = processor.processLabel(strLabel);
			if (label != null) {
				if (!muttable.getInputAlphabet().contains(label)) {
					muttable.addAlphabetSymbol(label);
				}
				FastDFAState sourceState = nodeToState.get(edge.getSource().getNode());
				FastDFAState targetState = nodeToState.get(edge.getTarget().getNode()); 
				muttable.setTransition(sourceState, label, targetState);
			}
		}
		
		return muttable;
	}
}