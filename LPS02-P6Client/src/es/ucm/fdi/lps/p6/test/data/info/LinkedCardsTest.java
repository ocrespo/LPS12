package es.ucm.fdi.lps.p6.test.data.info;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;

public class LinkedCardsTest {
	private LinkedCard lin = new LinkedCard();
	@Before
	public void setUp() throws Exception {
		lin.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		lin.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		lin.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetCardString() {
		try{
			assertNotNull(lin.getCard("lobo"));
			assertNull(lin.getCard("fail"));
		}catch (Exception e) {
			fail("Failed");
		}
	}

	@Test
	public void testFoundCard() {
		try{
			assertNull(lin.found(new Entity("pajaro", "das", Element.earth, 1 , "", "")));
			assertNotNull(lin.found(lin.getCard()));
		}catch (Exception e) {
			fail("Failed");
		}
	}

	@Test
	public void testFoundString() {
		try{
			assertNotNull(lin.found("lobo"));
			assertNull(lin.found("fail"));
		}catch (Exception e) {
			fail("Failed");
		}
	}

}
