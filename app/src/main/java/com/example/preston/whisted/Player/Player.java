package com.example.preston.whisted.Player;

import com.example.preston.whisted.Behavior.BidBehavior;
import com.example.preston.whisted.Behavior.DecideTrumpBehavior;
import com.example.preston.whisted.Behavior.PickupCardBehavior;
import com.example.preston.whisted.Behavior.PlayCardBehavior;
import com.example.preston.whisted.Behavior.PlayRespondingCardBehavior;
import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;
import com.example.preston.whisted.Game.Game;

/**
 * Created by preston on 11/26/14.
 */
public abstract class Player {
    protected Deck hand;
    protected PickupCardBehavior pickup;
    protected PlayCardBehavior play;
    protected PlayRespondingCardBehavior respond;
    protected BidBehavior bidBehavior;
    protected DecideTrumpBehavior trumpBehavior;
    protected int currentBid;

    public void setTrumpBehavior(DecideTrumpBehavior trumpBehavior) {
        this.trumpBehavior = trumpBehavior;
    }

    protected Player() {
        hand = new Deck();
    }

    protected Player(PickupCardBehavior pickup, PlayCardBehavior play, PlayRespondingCardBehavior respond, BidBehavior bidBehavior, DecideTrumpBehavior trumpBehavior) {
        this.pickup = pickup;
        this.play = play;
        this.respond = respond;
        this.bidBehavior = bidBehavior;
        this.trumpBehavior = trumpBehavior;
        hand = new Deck();

    }

    //public abstract void notifyNeedBid(int currentBid);
    private void setBid(){
        Game.getInstance().setBid(getBid(currentBid));
    }
    public int getBid(int otherBid){ return bidBehavior.getBid(hand, otherBid);}

    public String decideTrump(){ return trumpBehavior.decideTrump(hand);}

    public void pickUpCard(Card newCard){
        hand.addCard(newCard);
    }

    //TODO: should this maybe be added to the want card category?
    public void pickUpCard(Card firstChoice, Card secondChoice){
        hand.addCard(firstChoice);
    }

    public boolean wantCard(Card potentialCard){
        return pickup.pickUpCard(potentialCard);
    }

    public Card playCard(){
        return play.playCard();
    }
    public Card playCard(Card card){
        return respond.playRespondingCard(card);
    }

    public PlayRespondingCardBehavior getRespondBehavior() {
        return respond;
    }
    public void setRespondBehavior(PlayRespondingCardBehavior respond) {
        this.respond = respond;
    }

    public Deck getHand() {
        return hand;
    }
    public void setHand(Deck hand) {
        this.hand = hand;
    }

    public void setPickupBehavior(PickupCardBehavior pickup) {
        this.pickup = pickup;
    }

    public void setPlayBehavior(PlayCardBehavior play) {
        this.play = play;
    }

    public void setBidBehavior(BidBehavior bidBehavior) {
        this.bidBehavior = bidBehavior;
    }
}
