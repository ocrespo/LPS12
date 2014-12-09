package es.ucm.fdi.lps.p5.test.data.action.active;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.active.ChangeEntity;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class ChangeTest {
	private ChangeEntity act = new ChangeEntity();
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
		Card c1 = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		if (!act.allOk(null,c1, c1, null, null, new Warlock(20), null).needEntityTarget()) {
			fail();
		}
	}
	
	@Test
	public void testDone() {
		try {
			assertFalse((act.done(null)));
			Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
			act.allOk(new TreeCard(),c, c, new Warlock(20),new Warlock(20), new Warlock(20), null);
		} catch (Exception e) {
			fail();
		}
	}

}
