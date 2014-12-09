package es.ucm.fdi.lps.p6.test.data.action.active;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.actions.active.AttackerAllStatsModifiers;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class AttackerAllStatsModifiersTest {
	private class Mock extends AttackerAllStatsModifiers{

		public Mock(int at, int def) {
			super(at, def);
		}
		public void setCombat(TreeCard combat){
			super.combat = combat;
		}
		
	}
	private Mock mock;
	private TreeCard combat;
	@Before
	public void setUp() throws Exception {
		combat =  new TreeCard();
		combat.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		combat.insert("pajaro",new Entity("pajaro", "das", Element.earth, 1 , "", ""));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		try{
			mock =  new Mock(0, 0);
			assertFalse(mock.done(null));
			
			mock.setCombat(combat);
			assertTrue(mock.done(null));
		}catch (Exception e) {
			fail();
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
			
			er =  mock.allOk(new TreeCard(), null, null, null, null, null, null);
			if(!er.needAttackerChoose()){
				fail();
			}
			
			er = mock.allOk(combat, null, null, null, null, null, null);
			if(!er.allOK()){
				fail();
			}
		}catch (Exception e) {
		}
	}

}
