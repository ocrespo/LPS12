package es.ucm.fdi.lps.p5.test.data.action.active;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.active.DamageAttackStat;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;

public class DamageAttackStatTest {
	private class Mock extends DamageAttackStat {
		
	}
	private Mock mock;
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
	}

	@Test
	public void testAllOk() {
		try{
			mock =  new Mock();
			Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
			ErrorMen er =  mock.allOk(null, null, c, null, null, null, null);
			if(!er.needTarget()){
				fail();
			}
			er =  mock.allOk(null,c, c, null, null, new Warlock(20), null);
			if(!er.needEntityTarget()){
				fail();
			}
		}catch (Exception e) {
			fail();
		}
	}

}
