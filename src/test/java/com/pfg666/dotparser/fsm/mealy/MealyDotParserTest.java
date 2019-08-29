package com.pfg666.dotparser.fsm.mealy;

import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import net.automatalib.automata.transducers.impl.FastMealy;
import net.automatalib.automata.transducers.impl.FastMealyState;
import net.automatalib.automata.transducers.impl.MealyTransition;

public class MealyDotParserTest {
	
	private static String WIN_CLIENT_PATH = Paths.get("src", "test", "resources", "models", "win8client.dot").toString();
	
	private MealyDotParser<String,String> stringParser;
	
	@Before
	public void init() {
		stringParser = new MealyDotParser<>(new BasicStringMealyProcessor());
	}
	
	@Test
	public void mealyMachineTest() throws Exception{
		List<FastMealy<String, String>> automata = stringParser.parseAutomaton(WIN_CLIENT_PATH);
		Assert.assertEquals(1, automata.size());
		FastMealy<String, String> aut = automata.get(0);
		Assert.assertEquals(13, aut.getStates().size());
		Assert.assertEquals(10, aut.getInputAlphabet().size());
		FastMealyState<String> init = aut.getInitialState();
		String input = "RCV";
		MealyTransition<FastMealyState<String>, String> trans = aut.getTransition(init, input);
		String actualOutput = trans.getOutput();
		Assert.assertEquals("TIMEOUT", actualOutput);
	}
}
