package es.ucm.fdi.lps.p6.test.data.action.passive;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.actions.passive.CantTargetIntervention;

public class CantTargetInterventionTest {
	private class Mock extends CantTargetIntervention{
		
	}
	private Mock mock ;
	@Before
	public void setUp() throws Exception {
		mock = new Mock();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		try{	
			if(mock.done(null, null, null).canTargetIntervention()){
				fail();
			}
		}catch (Exception e) {
			fail();
		}
	}


}
