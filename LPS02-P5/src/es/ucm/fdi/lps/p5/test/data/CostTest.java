package es.ucm.fdi.lps.p5.test.data;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.Cost;

public class CostTest {
	
	Cost cost;

	@Before
	public void setUp() throws Exception {
		cost = new Cost();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testPay() {
		try{
			cost.add(new Cost(4, 0, 0, 0, 1));
			//assertTrue(cost.pay(new Cost()));
			///assertTrue(cost.pay(new Cost(4,0,0,0,1)));
			cost = new Cost(4, 0, 0, 0, 0);
			//assertFalse(cost.pay(new Cost(4,0,0,0,1)));
		}
		catch (Exception e){
			fail("Not yet implemented");

		}
	}

}
