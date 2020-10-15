package com.pfg666.dotparser.fsm.dfa;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.automatalib.automata.fsa.impl.FastDFA;
import net.automatalib.automata.fsa.impl.FastDFAState;

public class DFADotParserTest {
private static String DTLS_CLIENT="/models/dtls_client.dot";
	
	private DFADotParser<String> stringParser;
	
	@Before
	public void init() {
		stringParser = new DFADotParser<>(new BasicStringDFAProcessor());
	}
	
	@Test
	public void dfaTest() throws Exception{
		InputStream stream = DFADotParserTest.class.getResource(DTLS_CLIENT).openStream();
		List<FastDFA<String>> automata = stringParser.parseAutomaton(stream);
		Assert.assertEquals(1, automata.size());
		FastDFA<String> aut = automata.get(0);
		Assert.assertEquals(18, aut.getStates().size());
		System.out.println(aut.getInputAlphabet());
		Assert.assertEquals(16, aut.getInputAlphabet().size());
		FastDFAState init = aut.getInitialState();
		Assert.assertFalse(init.isAccepting());
		FastDFAState state = aut.getState(Arrays.asList(
				"?DH_SERVER_HELLO",
				"!DH_CLIENT_HELLO",
				"?CERTIFICATE",
				"?DH_SERVER_KEY_EXCHANGE",
				"?SERVER_HELLO_DONE",
				"!DH_CLIENT_KEY_EXCHANGE,CHANGE_CIPHER_SPEC,FINISHED",
				"?CHANGE_CIPHER_SPEC",
				"?FINISHED"
				));
		Assert.assertTrue(state.isAccepting());
	}
}
