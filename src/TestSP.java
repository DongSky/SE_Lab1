import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSP {

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
	public void testcalcShortestPath() {
		String result1 = mainCla.calcShortestPath(mainCla.g, "misakamikoto", "van");
		assertEquals("-1 source word not found", result1);
		String result2 = mainCla.calcShortestPath(mainCla.g, "*******", "artist");
		assertEquals("-1 source word not found", result2);
		String result3 = mainCla.calcShortestPath(mainCla.g, "van", "misakamikoto");
		assertEquals("-2 dest word not found", result3);
		String result4 = mainCla.calcShortestPath(mainCla.g, "m", "m");
		assertEquals("0 ", result4);
		String result5 = mainCla.calcShortestPath(mainCla.g, "dungeon", "van");
		assertEquals("2147483647 no direcrted path", result5);
		String result6 = mainCla.calcShortestPath(mainCla.g, "i", "artist");
		assertEquals("7 ", result6);
//		String result1 = mainCla.calcShortestPath(mainCla.g, "van", "artist");
//		assertEquals("8 ", result1);
//		
	}

}
