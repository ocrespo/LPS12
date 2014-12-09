package es.ucm.fdi.lps.p5.test.data.action.active;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.actions.active.ChangeEntity;
import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;

public class ChangeEntityTest {
	private class Mock extends ChangeEntity{


		public void setWarlock(Warlock w1,Warlock w2){
			super.w1 = w1;
			super.w2 =  w2;
		}
		public void setTar(Card c){
			super.tar = c;
		}
	}
	private Mock mock;
	private Warlock w1;
	private Warlock w2;
	@Before
	public void setUp() throws Exception {
		w1 =  new Warlock(20);
		w2 =  new Warlock(20);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		try{
			mock = new Mock();
			assertFalse(mock.done(null));
			Card c =  new Entity("pajaro", "das", Element.earth, 1 , "", "");
			w2.addInGame(c);
			mock.setTar(c);
			mock.setWarlock(w1, w1);
			assertFalse(mock.done(null));
			mock.setWarlock(w1, w2);
			assertTrue(mock.done(null));
			
		}catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAllOk() {
		try{
			mock = new Mock();
			ErrorMen er = mock.allOk(null, null, null, null, null, null, null);
			if(!er.needTarget()){
				fail();
			}
			er = mock.allOk(null, new Entity("pajaro", "das", Element.earth, 1 , "", "") , new Entity("pajaro", "das", Element.earth, 1 , "", ""), w1, w2, w2, null);
			if(!er.needEntityTarget()){
				fail();
			}
		}catch (Exception e) {
			fail();
		}
	}

}
