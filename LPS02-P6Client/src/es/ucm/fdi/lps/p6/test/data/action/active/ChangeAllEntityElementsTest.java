package es.ucm.fdi.lps.p6.test.data.action.active;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.Warlock;
import es.ucm.fdi.lps.p6.model.data.actions.active.ChangeAllEntityElements;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;

public class ChangeAllEntityElementsTest {
	private class Mock extends ChangeAllEntityElements{

		public Mock(ArrayList<Element> elems) {
			super(elems);
		}
		public void setWarlock(Warlock w1,Warlock w2){
			super.w1 = w1;
			super.w2 =  w2;
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
		ArrayList<Element> elem =  new ArrayList<Element>();
		elem.add(Element.air);
		mock =  new Mock(elem);
		mock.setWarlock(w1, w2);
		try{
			assertFalse(mock.done(null));
			w2.addInGame(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
			assertFalse(mock.done(null));
			w2.addInGame(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
			assertFalse(mock.done(null));
		}catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testAllOk() {
		try{
			mock = new Mock(null);
			ErrorMen er = mock.allOk(null, null, null, null, null, null, null);
			if(!er.allOK())
				fail();
		}catch (Exception e) {
			fail();
		}
	}

}
