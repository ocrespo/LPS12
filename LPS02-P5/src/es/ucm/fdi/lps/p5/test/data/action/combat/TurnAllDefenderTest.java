package es.ucm.fdi.lps.p5.test.data.action.combat;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.combat.TurnAllDefender;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;

public class TurnAllDefenderTest {
	private TurnAllDefender act =  new TurnAllDefender();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		if (!act.done(null,null, null, null).allOK()) {
			fail();
		}
		if (!act.done(null,new LinkedCard(), new Warlock(20), null).nextTurn()) {
			fail();
		}
	}

}
