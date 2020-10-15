package com.pfg666.dotparser.exceptions;

import com.alexmerz.graphviz.objects.Edge;
import com.alexmerz.graphviz.objects.Node;

public class MalformedNodeException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MalformedNodeException(Node node, String reason) {
		super(String.format("Node %s is malformed \n %s", node, reason));
	}
}
