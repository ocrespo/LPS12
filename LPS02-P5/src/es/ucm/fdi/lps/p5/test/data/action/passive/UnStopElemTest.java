package es.ucm.fdi.lps.p5.test.data.action.passive;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p5.model.data.actions.passive.UnStopElem;
import es.ucm.fdi.lps.p5.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.info.LinkedCard;

public class UnStopElemTest {
	private class Mock extends UnStopElem{

		public Mock(Element elem) {
			super(elem);
		}
		
	}
	private Mock mock;
	@Before
	public void setUp() throws Exception {
		mock =  new Mock(Element.earth);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDone() {
		ErrorMen er = mock.done(null, null, null);
		if(!er.allOK()){
			fail();
		}
		LinkedCard lin =  new LinkedCard();
		lin.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		er = mock.done(lin, null, null);
		if(er.canDefense()){
			fail();
		}
		lin.insertFirst(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		er = mock.done(lin, null, null);
		if(er.canDefense()){
			fail();
		}
	}

}
