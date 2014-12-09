package es.ucm.fdi.lps.p6.test.data.action.active;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.active.MultiplyStatsModifiers;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;

public class MultiplyStatsModifiersTest {
	private MultiplyStatsModifiers act =  new MultiplyStatsModifiers(2, 2,false,false);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAllOk() {
		Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		if (!act.allOk(null, c, c, null, new Warlock(2), new Warlock(2), null).needEntityTarget()) {
			fail();
		}
		if (!act.allOk(null,c,c, null, new Warlock(2), new Warlock(2), null).needEntityTarget()) {
			fail();
		}
	}

}
