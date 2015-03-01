package com.example.preston.whisted.Player;

import com.example.preston.whisted.Behavior.BidBehavior;
import com.example.preston.whisted.Behavior.HumanBehavior.VisualBid;
import com.example.preston.whisted.Behavior.HumanBehavior.VisualDecideTrump;
import com.example.preston.whisted.Behavior.HumanBehavior.VisualPickUpCard;
import com.example.preston.whisted.Behavior.HumanBehavior.VisualPlayCard;
import com.example.preston.whisted.Behavior.HumanBehavior.VisualPlayRespondingCard;
import com.example.preston.whisted.Behavior.PickupCardBehavior;
import com.example.preston.whisted.Behavior.PlayCardBehavior;
import com.example.preston.whisted.Behavior.PlayRespondingCardBehavior;
import com.example.preston.whisted.Cards.Card;

/**
 * Created by preston on 12/3/14.
 * Represents a human player.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(){
        super();
        PlayCardBehavior redPlayBehavior = new VisualPlayCard();
        PlayRespondingCardBehavior redRespondingBehavior = new VisualPlayRespondingCard();
        PickupCardBehavior redPickBehavior = new VisualPickUpCard();
        setPickupBehavior(redPickBehavior);
        setPlayBehavior(redPlayBehavior);
        setRespondBehavior(redRespondingBehavior);
        BidBehavior redBidBehavior = new VisualBid();
        setBidBehavior(redBidBehavior);
        setTrumpBehavior(new VisualDecideTrump());
    }

    @Override
    public boolean wantCard(Card potentialCard) {
        return false;
    }

    public Card playCard(int index){
        Card ret = hand.remove(index);
        return ret;
    }

    @Override
    public void pickUpCard(Card newCard) {
        super.pickUpCard(newCard);
        hand.sort(Card.DIAMOND);
    }
}
