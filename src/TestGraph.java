import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGraph {
	Main mainCla = new Main();
	@Before
	public void setUp() throws Exception {
		mainCla.g = mainCla.createDirectedGraph("/Users/DongSky/lab1_input.txt");
	}

	@After
	public void tearDown() throws Exception {
	}
	/*
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	*/
	@Test
	public void testqueryBridgeWords() {
		String result1 = mainCla.queryBridgeWords(mainCla.g, "van", "i");
		assertEquals("No bridge words from word1 and word2", result1);
		String result2 = mainCla.queryBridgeWords(mainCla.g, "my", "is");
		assertEquals("The bridge words from word1 to word2 are: name.", result2);
		String result3 = mainCla.queryBridgeWords(mainCla.g, "template", "is");
		assertEquals("No word1 or word2 in the graph!", result3);
		String result4 = mainCla.queryBridgeWords(mainCla.g, "van", "misakamikoto");
		assertEquals("No word1 or word2 in the graph!", result4);
		String result5 = mainCla.queryBridgeWords(mainCla.g, "van", "van");
		assertEquals("No bridge words from word1 and word2", result5);
		String result6 = mainCla.queryBridgeWords(mainCla.g, "van", "*******");
		assertEquals("No word1 or word2 in the graph!", result6);
		String result7 = mainCla.queryBridgeWords(mainCla.g, "", "misakamikoto");
		assertEquals("No word1 or word2 in the graph!", result7);
		String result8 = mainCla.queryBridgeWords(mainCla.g, "i", "van");
		assertEquals("No bridge words from word1 and word2", result8);
		String result9 = mainCla.queryBridgeWords(mainCla.g, "m", "artist");
		assertEquals("The bridge words from word1 to word2 are: an, and performance.", result9);
	}
	
//	@Test
//	public void testcalcShortestPath() {
//		
//	}
}
