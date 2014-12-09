package es.ucm.fdi.lps.p6.test.data.action.active;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.actions.active.Counter;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;

public class CounterTest {
	private class Mock extends Counter{
		public void setTar(Card c){
			super.tar = c;
		}
		public void setInterventions(StackCard stack){
			super.interventions = stack;
		}
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
		try{
			mock =  new Mock();
			assertFalse(mock.done(null));
			
			StackCard stack = new StackCard();
			mock.setInterventions(stack);
			assertFalse(mock.done(null));
			Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
			
			stack.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
			assertFalse(mock.done(null));
			stack.insert(c);
			mock.setTar(c);
			assertTrue(mock.done(null));
		}catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAllOk() {
		try{
			mock =  new Mock();
			Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
			ErrorMen er =mock.allOk(null, null,c, null, null, null, null);
			if(!er.needTarget()){
				fail();
			}
			er =  mock.allOk(null,c, c, null, null, null, new StackCard());
			if(!er.needInterventionTarget()){
				fail();
			}
		}catch (Exception e) {
			fail();
		}
	}

}
