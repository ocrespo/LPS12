package es.ucm.fdi.lps.p5.test.data.action.active;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.actions.active.Kill;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class KillTest {
	private class Mock extends Kill{

		public Mock() {
		}
		public void setCombat(TreeCard combat){
			super.combat = combat;
		}
		
	}
	private Mock mock;
	private TreeCard combat;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		fail("Not yet implemented");
	}

	@Test
	public void testAllOk() {
		fail("Not yet implemented");
	}

	@Test
	public void testUnDone() {
		fail("Not yet implemented");
	}

}
