package es.ucm.fdi.lps.p6.model.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import es.ucm.fdi.lps.p6.model.console.Console;
import es.ucm.fdi.lps.p6.model.data.Cost.PayCost;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen;
import es.ucm.fdi.lps.p6.model.data.actions.errors.ErrorMen.ErrorEnum;
import es.ucm.fdi.lps.p6.model.data.actions.permanent.PermanentAction;
import es.ucm.fdi.lps.p6.model.data.cards.Card;
import es.ucm.fdi.lps.p6.model.data.cards.Card.Element;
import es.ucm.fdi.lps.p6.model.data.cards.Card.PlayCard;
import es.ucm.fdi.lps.p6.model.data.info.IndexCards;
import es.ucm.fdi.lps.p6.model.data.info.LinkedCard;
import es.ucm.fdi.lps.p6.model.data.info.StackCard;
import es.ucm.fdi.lps.p6.model.data.info.TreeCard;
import es.ucm.fdi.lps.p6.model.game.Observed;


/**
 * @author Roni
 * 
 */

public class Warlock extends Observed implements Serializable , Cloneable {
	/**
	 * 
	 */
	private static int count = 1;
	private  int id ;
	private static final long serialVersionUID = 1L;
	private int life = 10;
	private LinkedCard deck = new LinkedCard();
	private StackCard grave = new StackCard();
	private IndexCards hand = new IndexCards();
	private IndexCards enviroment = new IndexCards();
	private IndexCards inGame = new IndexCards();
	private Cost mana = new Cost();
	private String phrase;
	private String name;
	private PermanentAction actionPermanent = null;
	private String infoBuff = "";
	private String dirImage;
	/**
	 * builder default
	 */
	public Warlock(int life) {
		this.life = life;
		deck = new LinkedCard();
		id = count;
		count++;
	}
	/**
	 * 
	 * @return the dir image
	 */
	public String getImage(){
		return dirImage;
	}
	/**
	 * 
	 * @return the dir image
	 */
	public void setImage(String dirImage){
		this.dirImage = dirImage;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phrase
	 */
	public String getPhrase() {
		return phrase;
	}
	/**
	 * @param phrase the phrase to set
	 */
	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}
	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}
	/**
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}
	/**
	 * initialize the warlock's chlorine
	 */
	public void iniMana() {
		mana.iniCost();
	}
	/**
	 * 
	 * @return the number of all mana
	 */
	public int getNumMana(){
		return mana.value();
	}
	/**
	 * return  numbers of cards into hand
	 * @return integer
	 */
	public int getNumHand(){
		return hand.getNumCards();
	}
	/**
	 * 
	 * @return the number of card in hand which same code
	 */
	public int getNumHandEquals(){
		return hand.getNumCardsEquals();
	}
	/**
	 * return the hand into string
	 * @return String
	 */
	public String handToString() {
		return hand.toString();
	}
	/**
	 * 
	 * @return gome into string
	 */
	public String inGameToString(){
		return inGame.toStringGame();
	}
	/**
	 * 
	 * @return enviroment into string
	 */
	public String enviromentToString(){
		return enviroment.toStringGame();
	}
	/**
	 * 
	 * @return chlorine into string
	 */
	public String manaToString(){
		return mana.toString();
	}
	/**
	 * 
	 * @param key , code card
	 * @return the card by key into game
	 */
	public Card getGameCard(String key){
		(Console.getInstance()).writeLog("<getGameCard Warlock");
		return inGame.getCard(key);
	}
	/**
	 * 
	 * @param pos , position
	 * @param key , code of card
	 * @return the card by key in position pos, into game
	 */
	public Card getGameCard(int pos,String key){
		(Console.getInstance()).writeLog("<getGameCard Warlock");
		return inGame.getCard(pos,key);
	}
	/**
	 * 
	 * @param pos
	 * @param key
	 * @return the card by key in position pos, into enviroment
	 */
	public Card getEnviromentCard(int pos,String key){
		(Console.getInstance()).writeLog("<getEnviromentCard Warlock");
		return enviroment.getCard(pos,key);
	}
	/**
	 * 
	 * @return all enviroments
	 */
	public Iterator<LinkedCard> getAllEnviroment(){
		(Console.getInstance()).writeLog("<getAllEnviroment Warlock");
		return enviroment.getAll();
	}
	/**
	 * 
	 * @param key
	 * @return the card by key , into enviroment
	 */
	public Card getEnviroment(String key){
		(Console.getInstance()).writeLog("<getEnviroment Warlock");
		return enviroment.getCard(key);
	}
	/**
	 * 
	 * @param key , code card
	 * @return the card by key into hand
	 */
	public Card getHandCard(String key){
		(Console.getInstance()).writeLog("<getHandCard Warlock");
		return hand.getCard(key);
	}
	/**
	 * 
	 * @param key
	 * @return the number of card with same key into game
	 */
	public int getNumCardEquals(String key){
		(Console.getInstance()).writeLog("<getNumCardEquals Warlock");
		return inGame.getNumCardsEquals(key);
	}
	/**
	 * 
	 * @param key
	 * @return the number of card with same key into enviroment
	 */
	public int getNumEnviromentEquals(String key){
		(Console.getInstance()).writeLog("<getNumEnviromentEquals Warlock");
		return enviroment.getNumCardsEquals(key);
	}
	public int getNumEnoviroments(){
		return enviroment.getNumCards();
	}
	/**
	 * @param actionPermanent the actionPermanent to set
	 */
	public void setActionPermanent(PermanentAction actionPermanent) {
		this.actionPermanent = actionPermanent;
	}
	/**
	 * draw a card from the deck and put it in hand
	 */
	public boolean draw() {
		(Console.getInstance()).writeLog("<draw Warlock");
		if(deck.isEmpty())
			return false;
		Card c = deck.removeTop();
		hand.insert(c);
		super.setChanged();
		super.notifyNewHandCard(c, this);
		return true;
	}
	/**
	 * draw all hand
	 * @return if can
	 */
	public boolean drawHand(){
		(Console.getInstance()).writeLog("<drawHand Warlock");
		int max = (Configuration.getInstance()).getMaxHand();
		for(int i=0;i<max;i++)
			if(!draw())
				return false;
		return true;
	}
	/**
	 * draw card until full his hand
	 * @return if can
	 */
	public boolean drawToMaxHand(){
		(Console.getInstance()).writeLog("drawToMaxHand Warlock");
		int max = (Configuration.getInstance()).getMaxHand() - hand.getNumCards();
		for(int i=0;i<max;i++)
			if(!draw())
				return false;
		return true;
	}
	/**
	 * insert card c into hand
	 * @param c card
	 */
	public void addInHand(Card c){
		(Console.getInstance()).writeLog("<addInHand Warlock");
		hand.insert(c);
		super.setChanged();
		super.notifyNewHandCard(c, this);
	}
	/**
	 * fill deck
	 */
	public void fillDeck(){
		deck.fill();
	}
	/**
	 * remove card into game with id c
	 * @param c card
	 */
	public Card removeInGameCard(Card c){
		(Console.getInstance()).writeLog("<removeInGameCard Warlock");
		super.setChanged();
		super.notifyRemovEntity(c, this);
		return inGame.removeCard(c);
	}
	/**
	 * put a card into graveyard, and remove from game
	 * @param c , Card
	 */
	public void dieInGame(Card c){	
		(Console.getInstance()).writeLog("<removeInGame Warlock");
		grave.insert(inGame.removeCard(c));
		super.setChanged();
		super.notifyDieEntity(c, this);
	}
	/**
	 * remove card into enviroment with id c
	 * @param c
	 */
	public void removeEnviromentCard(Card c){	
		(Console.getInstance()).writeLog("<removeEnviromentCard Warlock");
		enviroment.removeCard(c);
		super.setChanged();
		super.notifyRemoveManaCard(c, this);
	}
	/**
	 * remove card into enviroment with id c, and insert in graveyard
	 * @param c
	 */
	public void removeEnviroment(Card c){	
		(Console.getInstance()).writeLog("<removeEnviroment Warlock");
		grave.insert(enviroment.removeCard(c));
		super.setChanged();
		super.notifyRemoveManaCard(c, this);
	}
	/**
	 * remove card c from graveyard
	 * @param c the card
	 */
	public void removeGraveyard(Card c){	
		(Console.getInstance()).writeLog("<removeGraveyard Warlock");
		grave.removeCard(c);
		super.setChanged();
		super.notifyRemoveGraveyard(c, this);
	}
	/**
	 * insert card c into game
	 * @param c
	 */
	public void addInGame(Card c){
		(Console.getInstance()).writeLog("<addInGame Warlock");
		inGame.insert(c);
		super.setChanged();
		super.notifyAddGameEntity(c, this);
	}
	/**
	 * insert card c into enviroment
	 * @param c
	 */
	public void addEnviroment(Card c){
		(Console.getInstance()).writeLog("<addEnviroment Warlock");
		enviroment.insert(c);
		super.setChanged();
		super.notifyAddManaCard(c, this);
	}
	/**
	 * 
	 * @param key
	 * @return if card by key is in deck
	 */
	public Card foundInDeck(String key){
		(Console.getInstance()).writeLog("<foundInDeck Warlock");
		return deck.found(key);
	}
	/**
	 * put card from hand in game
	 * @param c the card
	 * @param canEnvi if can play environment
	 * @return info about the play
	 */
	public PlayCard playCard(Card c,boolean canEnvi,boolean canInCombat){
		(Console.getInstance()).writeLog("playCard Warlock");
		//Card c = hand.getCard(key);
		
		PlayCard value = c.insert(canEnvi, canInCombat);
		if(value == PlayCard.goEnviroment){
			enviroment.insertFirst(c);
			hand.removeCard(c);
			super.setChanged();
			super.notifyPlayCardMana(c,this);
			
			return value;
		}
		else if(value == PlayCard.goGame){
			PayCost enought = mana.pay(c.getCost(),c);
			if(enought == PayCost.allOk){
				inGame.insert(c);
				hand.removeCard(c);
				super.setChanged();
				super.notifyPlayCardEntity(c,this);
				return value;
			}
			else if(enought == PayCost.needDeal){
				return PlayCard.needDealMana;
			}
			return PlayCard.needMoreMana;
		}
		/*else if(value == PlayCard.goIntervention)
			return value;*/
		return value;
	}
	/**
	 * 
	 * @param e the element
	 * @return if finish to pay deal mana
	 */
	public boolean payDeal(Element e,Card c,boolean isEntity){
		boolean finishDeal = mana.payDealMana(e,c);
		if(finishDeal && isEntity){
			inGame.insert(c);
			hand.removeCard(c);
			super.setChanged();
			super.notifyPlayCardEntity(c,this);
			
			return true;
		}
		return finishDeal;
	}
	/**
	 *  mana to pay c
	 * @param c the card
	 * @return the success
	 */
	public PlayCard payCard(Card c){
		PayCost enought = mana.pay(c.getCost(),c);
		if(enought == PayCost.allOk){
			//inGame.insert(c);
			//super.setChanged();
			//super.notifyPlayCardIntervention();
			return PlayCard.goIntervention;
		}
		else if(enought == PayCost.needDeal){
			return PlayCard.needDealMana;
		}
		return PlayCard.needMoreMana;
	}
	
	/**
	 * return the card on game to hand
	 * @param c
	 */
	public boolean upInGame(Card c){
		(Console.getInstance()).writeLog("upInGame Warlock");
		if(inGame.removeCard(c) == null)
			return false;
		setChanged();
		notifyRemovEntity(c, this);	
		hand.insert(c);
		setChanged();
		notifyNewHandCard(c, this);
		return true;
	}
	/**
	 * insert card into deck
	 * @param c
	 */
	public void addCardToDeck(Card c){
		(Console.getInstance()).writeLog("<addCardToDeck Warlock");
		deck.insert(c);
	}
	/**
	 * turnoff all crads
	 */
	public void iniCards(){
		(Console.getInstance()).writeLog("<iniCards");
		inGame.turnOff();
		enviroment.turnOff();
	}
	/**
	 * turnoff all crads
	 */
	public void iniCardsBuff(){
		(Console.getInstance()).writeLog("<iniCards");
		inGame.iniBuff();
	}
	/**
	 * undo all action of cards in game
	 */
	public void unDone(){
		(Console.getInstance()).writeLog("<unDone Warlock");
		inGame.unDone();
	}
	/**
	 * add chlorine
	 * @param c cost to add
	 */
	public void addMana(Cost c){
		(Console.getInstance()).writeLog("<addMana Warlock");
		mana.add(c);
	}
	/**
	 * use a active action of the card
	 * @param key , code of card
	 * @param c , card (if  is entity)
	 * @param combat , combat info
	 * @param tar , target to apply action
	 * @param pos position of the card (if is entityt)
	 * @param w2 , warlock enemi
	 * @param onlyPay  y only can use card to take chlorine
	 * @return info abaout success
	 */
	public ErrorMen useActiveAction(String key,Card c,TreeCard combat, String tar,int pos, Warlock w2,boolean onlyPay){
		(Console.getInstance()).writeLog("useActiveAction Warlock");
		Card env = enviroment.getCard(key);
		if(env != null){
			enviroment.rotate(key);
			return env.doActive(this);				
		}
		if(onlyPay)
			return new ErrorMen(ErrorEnum.noSuccess);
		if(c != null){
			Card aux = null;
			if(tar != null){
				aux = w2.getGameCard(pos, tar);
				if(aux == null){
					aux = w2.getEnviroment(tar);
					if(aux == null)
						return new ErrorMen(ErrorEnum.targetNoExist);
				}
			}
			ErrorMen er = c.allOK(combat, aux, c, this, w2,null,null);
			if(er.allOK())
				return c.doActive(this);
			return er;
		}
		return null;
	}
	/**
	 * use a active action of the card
	 * @param c , card (if  is entity)
	 * @param combat , combat info
	 * @param tar , target to apply action
	 * @param w2 , warlock enemi
	 * @param onlyPay  y only can use card to take chlorine
	 * @return info abaout success
	 */
	public ErrorMen useActiveActionId(Card c,TreeCard combat, Card tar, Warlock w2,boolean onlyPay){
		(Console.getInstance()).writeLog("useActiveAction Warlock");
		if(c.insert(true, false) == PlayCard.goEnviroment){
			return c.doActive(this);				
		}
		if(onlyPay)
			return new ErrorMen(ErrorEnum.noSuccess);
		
		ErrorMen er = c.allOK(combat, tar, c, this, w2,null,null);
		if(er.allOK())
			return c.doActive(this);
		return er;
	}
	/**
	 * 
	 * @return if life is under o equals to 0
	 */
	public boolean isDie(){
		return life <= 0;
	}
	/**
	 * decrement warlock's life
	 * @param value
	 */
	public void loseLife(int value){
		life -= value;
		super.setChanged();
		super.notifyRefreshStatsWarlock(this);
	}
	/**
	 * do damage if surplus chlorine
	 * @return value of damage
	 */
	public int manaFire(){
		(Console.getInstance()).writeLog("manaFire Warlock");
		int value = mana.value();
		if(value == 0)
			return value;	
		life -= value;
		super.setChanged();
		super.notifyRefreshStatsWarlock(this);
		return value;
	}
	/**
	 * discard card and put into graveyard
	 * @param key , key of card
	 * @return if can (card exist)
	 */
	public boolean discard(String key){
		(Console.getInstance()).writeLog("discard string Warlock");
		Card c = hand.removeCard(key);
		if(c != null){
			grave.insert(c);
			super.setChanged();
			super.notifyDiscar(c,this);
			return true;
		}
		return false;
	}
	/**
	 * discard card and put into graveyard
	 * @param c , the card
	 * @return if can (card exist)
	 */
	public boolean discard(Card c){
		(Console.getInstance()).writeLog("discard string Warlock");
		hand.removeCard(c);
		if(c != null){
			grave.insert(c);
			super.setChanged();
			super.notifyDiscar(c,this);
			return true;
		}
		return false;
	}
	/**
	 * remove card from hand
	 * @param pos , position of the card
	 * @return the card
	 */
	public Card discard(int pos){
		(Console.getInstance()).writeLog("<discard pos Warlock");
		Card c = hand.removeCard(pos);
		super.setChanged();
		super.notifyDiscar(c,this);
		return c;
	}
	/**
	 * modifiers stats of all card in game
	 * @param at attack value
	 * @param def defense value
	 * @return false if is empty or true if no
	 */
	public boolean modStatsAllGameCard(int at, int def,boolean die){
		(Console.getInstance()).writeLog("<modStatsAllGameCard Warlock");
		return inGame.modStatsAllCards(at, def);
	}
	/**
	 * 
	 * @return the numbers of enviroments which is turn
	 */
	public int getNumEnviromentTurn(){
		return enviroment.getNumTurnCard();
	}
	/**
	 * 
	 * @param elem the element
	 * @return all in game card which element is elem
	 */
	public ArrayList<Card> getInGameCardElement(Element elem){
		(Console.getInstance()).writeLog("<getInGameCardElement Warlock");
		return inGame.getCardsElement(elem);
	}
	/**
	 * 
	 * @return the ingame cards into Collection
	 */
	public Collection<LinkedCard> getInGameCards(){
		(Console.getInstance()).writeLog("<getInGameCards Warlock");
		return inGame.getValues();
	}
	/**
	 * 
	 * @return the ingame cards by copy into arraylist
	 */
	public ArrayList<Card> getInGameCardsByCopy(){
		(Console.getInstance()).writeLog("<getInGameCardsByCopy Warlock");
		return inGame.getValuesCopy();
	}
	/**
	 * 
	 * @return ArraysList of card which all entities in deck
	 */
	public ArrayList<Card> getEntitiesIntoDeck(){
		(Console.getInstance()).writeLog("<getEntitiesIntoDeck Warlock");
		return deck.getAllEntities();
	}
	/**
	 * 
	 * @return Arraylist of linkedcasr which all entities into hand
	 */
	public ArrayList<LinkedCard> getEntitiesIntoHand(){
		(Console.getInstance()).writeLog("<getEntitiesIntoHand Warlock");
		return hand.getAllEntities();
	}
	/**
	 * 
	 * @return Arraylist of cards which all entities into graveyard
	 */
	public ArrayList<Card> getEntitiesIntoGraveyard(){
		(Console.getInstance()).writeLog("<getEntitiesIntoGraveyard Warlock");
		return grave.getAllEntities();
	}
	/**
	 * insert all card into aux to graveyard
	 * @param aux the info to insert
	 */
	public void addAllGraveyard(Collection<Card> aux){
		(Console.getInstance()).writeLog("<addAllGraveyard Warlock");
		grave.insertAll(aux);
	}
	/**
	 * remove all card into game which the same key
	 * @param key the from the cards
	 */
	public void removeAllCardsInGame(String key){
		(Console.getInstance()).writeLog("<removeAllCardsInGame Warlock");
		grave.insertAll(inGame.removeAllCard(key));
	}
	/**
	 * 
	 * @param key the key
	 * @return the collection of card which all card who have same key and remove from the hand
	 */
	public Collection<Card> removeAllCardsInHand(String key){
		(Console.getInstance()).writeLog("<removeAllCardsInHand Warlock");
		Collection<Card> aux = hand.removeAllCard(key);
		for(Card c : aux){
			super.setChanged();
			super.notifyRemoveHandCard(c, this);
		}
		return aux;
	}
	/**
	 * remove the card c from deack
	 * @param c the card
	 */
	public void removeDeck(Card c){
		(Console.getInstance()).writeLog("<removeDeck Warlock");
		deck.removeCard(c);
		super.setChanged();
		super.notifyRefreshStatsWarlock(this);
	}
	/**
	 * inser card c into deck
	 * @param c the card
	 */
	public void addInDeck(Card c){
		(Console.getInstance()).writeLog("<addInDeck Warlock");
		deck.insert(c);
	}
	/**
	 * fill the graveyard
	 */
	public void fillGraveyard(){
		grave.fill();
	}
	/**
	 * do the permanent action
	 * @return the result of the execute
	 */
	public ErrorMen doPermanent(){
		(Console.getInstance()).writeLog("doPermanent Warlock");
		if(actionPermanent == null)
			return new ErrorMen(ErrorEnum.allOk);
		return actionPermanent.doPermanent(null);
	}
	/**
	 * do passice action of warlocks cards in game
	 */
	public void doAllPassiveAction(){
		(Console.getInstance()).writeLog("doAllPassiveAction Warlock");
		Collection<LinkedCard> cards = getInGameCards();
		LinkedList<Card> auxLink = null;
		for(LinkedCard link : cards){
			auxLink = link.getValues();
			for(Card c : auxLink){
				c.doPassive(null, this);
			}
		}
	}
	/**
	 * 
	 * @return numers of cards into deck
	 */
	public int getNumCardsDeck(){
		return deck.size();
	}
	/**
	 * 
	 * @return the graveyard into string
	 */
	public String graveyardToString(){
		return grave.toString();
	}
	/**
	 * insert c into graveyard
	 * @param c the card
	 */
	public void addGraveyard(Card c){
		(Console.getInstance()).writeLog("<addGraveyard Warlock");
		grave.insert(c);
		super.setChanged();
		super.notifyAddGraveyard(c,this);
	}
	/**
	 * insert all card in link into ingame
	 * @param link the link with cards
	 */
	public void addAllCardsInGame(LinkedCard link){
		(Console.getInstance()).writeLog("<addAllCardsInGame Warlock");
		inGame.insertAllCardsCode(link);
		LinkedList<Card> aux = link.getValues();
		for(Card c : aux){
			setChanged();
			super.notifyAddGameEntity(c, this);
		}
	}
	/**
	 * 
	 * @param c the card by Id
	 * @return if there is ingmae or not
	 */
	public boolean foundInGame(Card c){
		return inGame.found(c);
	}
	/**
	 * 
	 * @param c the card by id
	 * @return if there is in enviroments or no
	 */
	public boolean foundInEnviroment(Card c){
		return enviroment.found(c);
	}
	/**
	 * 
	 * @param c the card by Id
	 * @return if there is ingmae or not
	 */
	public Card foundInGameCard(Card c){
		return inGame.foundCard(c) ;
	}
	/**
	 * 
	 * @param c the card by id
	 * @return if there is in enviroments or no
	 */
	public Card foundInEnviromentCard(Card c){
		return enviroment.foundCard(c);
	}
	/**
	 * 
	 * @return all cards in hand by copy
	 */
	public ArrayList<Card> getAllHand(){
		return hand.getValuesCopy();
	}
	/**
	 * 
	 * @param c the card
	 * @return if card is property of warlock
	 */
	public boolean found(Card c){
		return inGame.foundCard(c) != null || enviroment.foundCard(c) != null;
	}
	/**
	 * if card is into hand
	 * @param c the card
	 * @return the card
	 */
	public Card foundInHandCard(Card c){
		return hand.foundCard(c);
	}
	/**
	 * if card is into hand
	 * @param c the card
	 * @return the card
	 */
	public boolean foundInHand(Card c){
		return hand.found(c);
	}
	/**
	 * 
	 * @return size of deck
	 */
	public int getNumDeck(){
		return deck.size();
	}
	/**
	 * 
	 * @return size of graveyard
	 */
	public int getNumGraveyard(){
		return grave.size();
	}
	/**
	 * @return the infoBuff
	 */
	public String getInfoBuff() {
		return infoBuff;
	}
	/**
	 * add buff
	 * @param buff
	 */
	public void addBuff(String buff){
		if(this.infoBuff.equals("")){
			this.infoBuff = "<html>" + buff;
		}
		else
			this.infoBuff = this.infoBuff + "<br>" + buff;
		
		setChanged();
		notifyRefreshStatsWarlock(this);
		//this.buff = this.buff + "<br>sasadas</html>";
	}
	/**
	 * 
	 * @return all cards in hand
	 */
	public ArrayList<Card> getAllCardsInHand(){
		return hand.getAllCards();
	}
	/**
	 * 
	 * @return all cards in game
	 */
	public ArrayList<Card> getAllCardsInGame(){
		return inGame.getAllCards();
	}
	/**
	 * 
	 * @return all cards in enviroment
	 */
	public ArrayList<Card> getAllCardsInEnviroment(){
		return enviroment.getAllCards();
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * clone
	 */
	public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
            
            ((Warlock)obj).hand = (IndexCards) this.hand.clone();
            ((Warlock)obj).grave = (StackCard) this.grave.clone();
            ((Warlock)obj).deck = (LinkedCard) this.deck.clone();
        }catch(CloneNotSupportedException ex){
            //System.out.println(" no se puede duplicar");
        }
        return obj;
    }
}
