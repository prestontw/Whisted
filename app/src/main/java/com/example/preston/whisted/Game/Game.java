package com.example.preston.whisted.Game;

import android.app.Activity;
import android.widget.Toast;

import com.example.preston.whisted.Behavior.BidBehavior;
import com.example.preston.whisted.Behavior.GreedyPickupBehavior;
import com.example.preston.whisted.Behavior.HumanBehavior.VisualDecideTrump;
import com.example.preston.whisted.Behavior.MinRespondingPlay;
import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.CardComparator;
import com.example.preston.whisted.Cards.CardComparatorInterface;
import com.example.preston.whisted.Cards.Deck;
import com.example.preston.whisted.MainActivity;
import com.example.preston.whisted.Player.BasicComputerPlayer;
import com.example.preston.whisted.Player.GreedyComputerPlayer;
import com.example.preston.whisted.Player.HumanPlayer;
import com.example.preston.whisted.Player.Player;
import com.example.preston.whisted.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preston on 12/3/14.
 * Represents a game of whist.
 * Has two players, one deck, etc.
 */
public class Game {
    //players
    private Player red;
    private Player blue;
    //deck variables
    private String[] suits = Card.SUITS;
    private List<Integer> values;
    private Deck deck;
    boolean redDoneSelecting;
    private Deck playerDeck;
    private boolean lastTwoCards = false;
    //trick variables
    private CardComparatorInterface compare;
    private boolean redWinnerOfBid;
    private boolean redWonLastTrick;
    private String trump;
    private int redTricksWon;
    private int blueTricksWon;
    //displaying cards
    private Card redCard;
    private Card blueCard;
    private Card oldRedCard = null;
    private Card oldBlueCard = null;
    private boolean redNewCard = false;
    private boolean blueCardVisible;
    //bidding
    private int newBid;
    private int currentBid;
    private static int INITIAL_BID = 0;
    private boolean firstBidPass;
    private boolean firstCurrentBid;
    private Player first;
    private Player second;
    private boolean autoHand = false;
    private boolean biddingDone = false;
    private boolean resumingBidding = false;
    private int playerBid = 0;
    private boolean realBid = false;
    private int numPasses = 0;

    private static Game instance = null;
    private Activity activity = null;
    private boolean redPlayerFirst = false;

    private Game(){ }

    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }

    public void setAutoHand(boolean autoHand){
        this.autoHand = autoHand;
    }

    public void setRedPlayerFirst(boolean redPlayerFirst) {
        this.redPlayerFirst = redPlayerFirst;
    }

    public boolean initializeGame(){
        //set behaviors of players
        initializePlayers();

        initializeDeck();
        redDoneSelecting = false;
        newBid = 0;
        currentBid = INITIAL_BID;
        firstBidPass = false;
        playerBid = 0;
        //distribute cards
        distributeCards(deck, red, blue);

        oldBlueCard = null;
        oldRedCard = null;

        //get trump
        initializeTrump();
        updateTrump();

        //award points
        return true;
    }

    private void initializePlayers() {
        red = new HumanPlayer();
        if (autoHand)
            red.setPickupBehavior(new GreedyPickupBehavior());
        blue = new GreedyComputerPlayer();

        blueTricksWon = 0;
        redTricksWon = 0;
    }

    private void initializeDeck(){
        //initialize deck
        values = new ArrayList<Integer>();
        initializeValues(values);
        deck = new Deck(suits, values);
        deck.shuffle();
    }

    private void initializeBid(){
        //ask players to bid
        redWinnerOfBid = getBid(red, blue);
        redWonLastTrick = redWinnerOfBid;
    }

    private void initializeTrump() {
        //make sure human player has cards
        if (redDoneSelecting)
            trump = (redWinnerOfBid ? red : blue).decideTrump();

        if (trump == null)
            trump = Card.SPADE;
    }
    private void updateTrump(){
        //if we are not done picking up cards, or waiting for fragment to return trump
        if (!redDoneSelecting || trump == VisualDecideTrump.NO_TRUMP)
            return;
        //update comparator as well as AI's intelligence
        ((MinRespondingPlay) blue.getRespondBehavior()).setTrump(trump);
        compare = new CardComparator(trump);

        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.trump_is) + trump, Toast.LENGTH_SHORT).show();
    }

    private boolean getBid(Player first, Player second) {
        firstCurrentBid = redPlayerFirst;
        this.first = first;
        this.second = second;

        //start the bidding
        return resumeBidding();

    }

    //called after waiting for player to bid
    private void updateCurrentBid(int newBid, boolean firstBid){
        redWinnerOfBid = firstBid;
        currentBid = newBid;
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.bid_is_now) + currentBid, Toast.LENGTH_SHORT).show();
    }
    private void incorrectBid(){
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.bid_is_not_bigger) + " " + currentBid, Toast.LENGTH_SHORT).show();
        firstCurrentBid = !firstCurrentBid;
    }
    private void biddingOver(){
        biddingDone = true;
        Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.contract_is) + currentBid, Toast.LENGTH_SHORT).show();

        playerBid = currentBid;

        //redWinnerOfBid = !firstCurrentBid;
        redWonLastTrick = redWinnerOfBid;
        ((MainActivity)activity).getView().invalidate();
        //need to invalidate here to show card blue played

    }
    private boolean resumeBidding(){

        while(numPasses < 1 || (numPasses < 2 && firstBidPass)){

            //if new bid is fake one from player, then we wait
            if (newBid == BidBehavior.WAIT)
                return false;
            if (newBid == BidBehavior.PASS){
                if (currentBid == INITIAL_BID){
                    firstBidPass = true;
                    //TODO: check this twice, as two initial passes should restart the game
                }
                else if (realBid){
                    Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.player_passes), Toast.LENGTH_SHORT).show();
                    //then bidding is over, and we need to break out

                    biddingOver();
                    break;
                }
                numPasses ++;
            }
            else if (newBid == 0)
                ;
            else{
                realBid = true;
            }

            if (resumingBidding) {
                if (newBid <= currentBid && newBid != BidBehavior.PASS){
                    incorrectBid();
                    //make sure that it is still player's turn
                }
            }

            if (newBid > currentBid)
                updateCurrentBid(newBid, firstCurrentBid);
            //if we have a response from a player

            firstCurrentBid = !firstCurrentBid;
            newBid = (firstCurrentBid ? first : second).getBid(currentBid);
            resumingBidding = false;
        }

        //TODO: CHECK TO SEE number of passes. if we have two, then we need to restart
        return !firstCurrentBid;
    }

    //entry point for visual bidding behavior
    public void setBid(int visualBid){
        newBid = visualBid;
        resumingBidding = true;
        resumeBidding();
        redPlayerDoneSelecting();
    }

    //entry point for visual decision of trump
    public void setTrump(String trump){
        this.trump = trump;
        updateTrump();
    }
    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }


    //when player is done selecting cards
    public void redPlayerDoneSelecting(){
        redDoneSelecting = true;
        if (!biddingDone) {
            initializeBid();
            return;
        }
        initializeTrump();
        updateTrump();

        if(!redWinnerOfBid)
            leadBlueCard();
    }

    //play indexed card from red's hand
    public void setRedPlayerCardIndex(int cardIndex){

        redCard = ((HumanPlayer)red).playCard(cardIndex);
        redNewCard = true;
    }

    public void redPlayerPlayed(){

        if (redWonLastTrick && redNewCard){
            setBlueCard();
            //compare the two cards
            redWonLastTrick = !compare.compareFirstToSecond(redCard, blueCard);
        }

        else if (!redWonLastTrick) {
            //try to get a card from blue player
            if(!redCorrectPlay()){
                validateRedCard();
                return;
            }
            redWonLastTrick =  compare.compareFirstToSecond(blueCard, redCard);
        }
        if (redWonLastTrick)
            redTricksWon ++;
        else
            blueTricksWon ++;

        redNewCard = false;

        oldRedCard = redCard;
        oldBlueCard = blueCard;
        redCard = null;
        blueCard = null;

        //if player didn't win, blue plays a card!
        leadBlueCard();

        //check to see if game is over
        if (red.getHand().size() == 0){
            endGame();
        }
    }

    //get card from blue player
    public void setBlueCard(){
        if (redNewCard)
            blueCard = blue.playCard(redCard);
        else
            blueCard = blue.playCard();
        blueCardVisible = true;
    }

    //get blue's leading card
    private void leadBlueCard(){
        if (!redWonLastTrick && blue.getHand().size() > 0){
            setBlueCard();
            //display this card so red knows how to respond
            setMiddleCards(null, blueCard, true);
        }
    }

    private void endGame(){
        //display a toast message
        //and display something to reset?

        int winnerTricks = (redWinnerOfBid? redTricksWon: blueTricksWon);
        int tricksInBook = 6;
        int difference = winnerTricks - tricksInBook - playerBid;
        boolean madeIt = difference >= 0;
        String message = (madeIt?
                (redWinnerOfBid ? (activity.getString(R.string.you_made_contract) + playerBid + activity.getString(R.string.and) + difference + activity.getString(R.string.extra)) :
                                    (activity.getString(R.string.opponent_made_contract) + " " + playerBid)) :
                (redWinnerOfBid? (activity.getString(R.string.you_set) + (difference * -1) + activity.getString(R.string.sorry)) :
                                    (activity.getString(R.string.opponent_set) + (difference * -1) + "!")));
        ((MainActivity)activity).changeFirstPlayer();

        Toast.makeText(activity.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        ((MainActivity)activity).getView().almostDone();

    }
    public void finishEndingGame(){
        instance = new Game();
        ((MainActivity) activity).restart();
    }

    private boolean redCorrectPlay(){
        String properSuit = blueCard.getSuit();
        if (properSuit.equals(redCard.getSuit()))
            return true;
        //check to see if red has any of the proper suit
        return (red.getHand().getCardsofSuit(properSuit).size() == 0);

    }

    //make sure that red follows suit
    private void validateRedCard(){
        if (!redCorrectPlay()){
            //red picks back up card
            red.pickUpCard(redCard);
            //reset visual
            setMiddleCards(null, blueCard, true);
            Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.player_played_wrong_suit) + blueCard.getSuit(), Toast.LENGTH_SHORT).show();
        }
    }

    public int getRedTricksWon() {
        return redTricksWon;
    }

    public int getBlueTricksWon() {
        return blueTricksWon;
    }

    public Card getOldRedCard() {
        return oldRedCard;
    }

    public Card getOldBlueCard() {
        return oldBlueCard;
    }

    public Player getRedPlayer(){
        return red;
    }

    public Player getBluePlayer(){
        return blue;
    }

    //initialize values of deck
    private void initializeValues(List<Integer> values) {
        int lowestValue = 2;    //two
        int highestValue = 14; //ace
        for (int i = lowestValue; i <= highestValue; i ++){
            values.add(i);
        }
    }

    //distribute cards to two players
    //TODO: something weird is going on here...
    private void distributeCards(Deck deck, Player one, Player two){
        boolean debug = true;
        playerDeck = new Deck();

        boolean playerOneTurn = redPlayerFirst;
        while (deck.size() > 2){
            //assuming player 1 is human player, put its cards away
            if (playerOneTurn) {
                playerDeck.addCard(deck.removeTopCard());
                playerDeck.addCard(deck.removeTopCard());
            }
            else playerBuildCard(deck, playerOneTurn? one : two);
            playerOneTurn = !playerOneTurn;
        }
        //distribute human player cards
        distributePlayerCards();
        //check to see whether we show both cards to human player
        lastTwoCards = playerOneTurn;
        if (playerOneTurn) {
            playerDeck.addCard(deck.removeTopCard());
            playerDeck.addCard(deck.removeTopCard());
        }
        else two.pickUpCard(deck.removeTopCard(), deck.removeTopCard());
    }

    //show middle cards
    public void distributePlayerCards(){
        setMiddleCards(playerDeck.removeTopCard(), playerDeck.removeTopCard(),
                lastTwoCards && playerDeck.size() == 0);

    }

    //whether to show middle cards or not for human player to select
    public void updateMiddlePlayerChoiceCards(){
        if (playerDeck.size() > 1)
            distributePlayerCards();
        else setMiddleCards(null, null, false);
    }

    //prepares for cards to be shown
    public void setMiddleCards(Card redCard, Card blueCard, boolean blueCardVisible){
        this.redCard = redCard;
        this.blueCard = blueCard;
        this.blueCardVisible = blueCardVisible;
    }

    public Card getRedMiddleCard() {
        return redCard;
    }

    public Card getBlueMiddleCard() {
        return blueCard;
    }

    public boolean getBlueMiddleCardVisible() {
        return blueCardVisible;
    }

    //how the player builds their hand: look at the top, and then either take it or the next one blindly
    private void playerBuildCard(Deck deck, Player player){
        if (deck.size() > 1){
            Card newCard = deck.removeTopCard();
            if (player.wantCard(newCard)){
                player.pickUpCard(newCard);
                //don't forget to delete the other card!
                deck.removeTopCard();
            } else {
                Card otherCard = deck.removeTopCard();
                player.pickUpCard(otherCard);
            }
        } else {
            System.out.println("THERE WERE NOT 2 CARDS TO PICK UP");
        }
    }

}
