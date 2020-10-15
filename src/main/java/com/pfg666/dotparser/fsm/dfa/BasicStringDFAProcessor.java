package com.pfg666.dotparser.fsm.dfa;

public class BasicStringDFAProcessor implements DFAProcessor<String>{

	@Override
	public String processLabel(String input) {
		return input;
	}

}
