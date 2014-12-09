package es.ucm.fdi.lps.p6.test.data.info;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.CardFactory;
import es.ucm.fdi.lps.p6.model.data.cards.Entity;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;

public class TreeCardTest {
	private TreeCard tree =  new TreeCard();
	@Before
	public void setUp() throws Exception {
		tree.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		tree.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		tree.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
		tree.insert(new Entity("pajaro", "das", Element.earth, 1 , "", ""));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsertStringCard() {
		try{
			/*assertTrue(tree.insert("lobo", null));
			Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
			assertTrue(tree.insert("lobo", c));
			assertFalse(tree.insert("albatros",c));
			assertFalse(tree.insert("lobo1", c));
			assertTrue(tree.insert("lobo2",c));*/
		}catch (Exception e) {
			fail("Failed");
		}
	}


	@Test
	public void testIsHere() {
		try{
			//assertFalse(tree.isHere( CardFactory.getCard("lobo")));
			//Card c = tree.getCombatDefense("lobo").getCard();
			//assertTrue(tree.isHere(c));
		}
		catch(Exception e){
			fail("Failed");
		}
	}
	@Test
	public void testRemoveCard() {
		try{
			//assertFalse(tree.removeCard( CardFactory.getCard("lobo")));
			//Card c = tree.getCombatDefense("lobo").getCard();
			//assertTrue(tree.removeCard(c));
		}
		catch(Exception e){
			fail("Failed");
		}
	}
	

}
