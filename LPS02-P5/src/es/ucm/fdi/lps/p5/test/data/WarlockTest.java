package es.ucm.fdi.lps.p5.test.data;

import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.ucm.fdi.lps.p5.model.data.Cost;
import es.ucm.fdi.lps.p5.model.data.Warlock;
import es.ucm.fdi.lps.p5.model.data.cards.Card;
import es.ucm.fdi.lps.p5.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p5.model.data.cards.Card.PlayCard;
import es.ucm.fdi.lps.p5.model.data.cards.Entity;
import es.ucm.fdi.lps.p5.model.data.cards.Enviroment;
import es.ucm.fdi.lps.p5.model.data.cards.Intervention;

public class WarlockTest {
	private Warlock w = new Warlock(20);
	private ArrayList<Card> aux = new ArrayList<Card>();
	@Before
	public void setUp() throws Exception {
		Card c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		w.addInGame(c);
		aux.add(c);
		c = new Enviroment("pajaro", "das", Element.earth, 1 , "", "");
		w.addEnviroment(c);
		aux.add(c);
		c =  new Enviroment("pajaro", "das", Element.earth, 1 , "", "");
		w.addEnviroment(c);
		aux.add(c);
		c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		w.addInGame(c);
		aux.add(c);
		c =  new Enviroment("pajaro", "das", Element.earth, 1 , "", "");
		w.addInHand(c);
		aux.add(c);
		c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		w.addInHand(c);
		aux.add(c);
		c = new Entity("pajaro", "das", Element.earth, 1 , "", "");
		w.addInHand(c);
		aux.add(c);
		c = new Intervention("pajaro", "das", Element.earth, 1 , "", "");
		w.addInHand(c);
		aux.add(c);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlayCard() {
		if(w.playCard(aux.get(2), false, false) != PlayCard.noMoreEnviroment)
			fail();
		if(w.playCard(aux.get(2), false, true) != PlayCard.noEnviromentNow)
			fail();
		if(w.playCard(aux.get(2), true, false) != PlayCard.goEnviroment)
			fail();
		//if(w.playCard(aux.get(5), true, false) != PlayCard.needMoreMana)
		//	fail();
		w.addMana(new Cost(4, 4, 4, 4, 4));
		//if(w.playCard(aux.get(5), true, false) != PlayCard.goGame)
		//	fail();
		if(w.playCard(aux.get(5), true, true) != PlayCard.noEntityNow)
			fail();
		if(w.playCard(aux.get(7), true, false) != PlayCard.noInterventionNow)
			fail();
		if(w.playCard(aux.get(7), true, true) != PlayCard.goIntervention)
			fail();
	}

	@Test
	public void testUseActiveAction() {
		/*if(!w.useActiveAction("prado", null, null, null, 0, null, true).allOK())
			fail();
		if(!w.useActiveAction("prado", null, null, null, 0, null, false).allOK())
			fail();
		if(!w.useActiveAction("lobo", w.getGameCard("lobo"), null, null, 0, null, false).noAction())
			fail();
		if(w.useActiveAction("lobo", w.getGameCard("lobo"), null, null, 0, null, true).success())
			fail();
		if(w.useActiveAction("aberracion", w.getGameCard("aberracion"), new TreeCard(), null, 0, null, false).allOK())
			fail();
		if(!w.useActiveAction("aberracion", w.getGameCard("aberracion"), new TreeCard(), "lobo", 0, new Warlock(20), false).targetNoExist())
			fail();
		TreeCard combat = new TreeCard();
		combat.insert(w.getGameCard("lobo"));
		if(w.useActiveAction("aberracion", w.getGameCard("aberracion"), combat, "lobo", 0,w, false).allOK())
			fail();*/
		
	}

}
