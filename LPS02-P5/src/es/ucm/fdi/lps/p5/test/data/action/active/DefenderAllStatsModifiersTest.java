package es.ucm.fdi.lps.p5.test.data.action.active;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.actions.active.DefenderAllStatsModifiers;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.info.TreeCard;

public class DefenderAllStatsModifiersTest {
	private class Mock extends DefenderAllStatsModifiers{

		public Mock(int at, int def) {
			super(at, def);
		}
		public void setCombat(TreeCard combat){
			super.combat = combat;
		}
		
	}
	private Mock mock;
	private TreeCard combat;
	private Card c;
	@Before
	public void setUp() throws Exception {
		combat =  new TreeCard();
		c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		combat.insert(c);
		combat.insertCard(c,new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		mock = new Mock(0, 0);
		try{
			assertFalse(mock.done(null));
			
			mock.setCombat(new TreeCard());
			assertFalse(mock.done(null));
			mock.setCombat(combat);
			
			assertTrue(mock.done(null));
		}catch (Exception e) {
		}
		
	}

	@Test
	public void testAllOk() {
		try{
			mock =  new Mock(0, 0);
			ErrorMen er = mock.allOk(null, null, null, null, null, null, null);
			if(!er.needCombat()){
				fail();
			}
			er = mock.allOk(new TreeCard(), null, null, null, null, null, null);
			if(!er.needDefenderChoose()){
				fail();
			}
			er =  mock.allOk(combat, null, c, null, null, null, null);
			if(!er.allOK()){
				fail();
			}
		}catch (Exception e) {
			fail();
		}
	}

}
