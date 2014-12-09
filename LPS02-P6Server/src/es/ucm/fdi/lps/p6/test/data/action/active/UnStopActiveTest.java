package es.ucm.fdi.lps.p6.test.data.action.active;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.active.UnStopActive;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;

public class UnStopActiveTest {
	private UnStopActive act  =  new UnStopActive();
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	@Test
	public void testAllOk() {
		if (act.allOk(null, null, null, null, null, null, null).allOK()) {
			fail();
		}
		Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		if (!act.allOk(null,c, c, null, null, new Warlock(20), null).needEntityTarget()) {
			fail();
		}
	}

}
