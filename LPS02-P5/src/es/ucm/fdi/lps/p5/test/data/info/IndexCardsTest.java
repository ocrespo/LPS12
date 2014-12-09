package es.ucm.fdi.lps.p5.test.data.info;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;
import es.ucm.fdi.lps.p5.model.data.info.IndexCards;

public class IndexCardsTest {

	private IndexCards index = new IndexCards();
	@Before
	public void setUp() throws Exception {
		index.insert( new Entity("a", "das", Element.earth, 1 , "", ""));
		index.insert(new Entity("b", "das", Element.earth, 1 , "", ""));
		index.insert(new Entity("c", "das", Element.earth, 1 , "", ""));
		index.insert(new Entity("d", "das", Element.earth, 1 , "", ""));
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testRemoveCardString() {
		try{
			assertNull(index.removeCard(""));
			assertNotNull(index.removeCard("a"));
			assertNotNull(index.removeCard("b"));
			assertNotNull(index.removeCard("c"));
			assertNull(index.removeCard("c"));
		}catch (Exception e) {
			fail("Failed");
		}
	}

	@Test
	public void testRemoveCardCard() {
		try{
			assertNull(index.removeCard(new Entity("a", "das", Element.earth, 1 , "", "")));
			assertNotNull(index.removeCard(index.getCard("a")));
		}catch (Exception e) {
			fail("Failed");
		}
	}

	@Test
	public void testRemoveCardInt() {
		try{
			assertNull(index.removeCard(5));
			assertNull(index.removeCard(6));
			assertNotNull(index.removeCard(1));
		}catch (Exception e) {
			fail("Failed");
		}
	}

	@Test
	public void testGetNumCards() {
		try{
			assertEquals(index.getNumCards(),4);
		}catch (Exception e) {
			fail("Failed");
		}
	}

	@Test
	public void testGetNumCardsEquals() {
		try{
			assertEquals(1,index.getNumCardsEquals("a"));
			assertEquals(1,index.getNumCardsEquals("b"));
		}catch (Exception e) {
			fail("Failed");
		}
	}
	@Test
	public void testRotate() {
		Card c = index.getCard("a");
		index.rotate("a");
		if(index.getCard("b") == c)
			fail("Failed");
	}

}
