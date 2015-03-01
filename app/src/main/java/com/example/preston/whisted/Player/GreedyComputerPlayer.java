package com.example.preston.whisted.Player;

import com.example.preston.whisted.Behavior.GreedyPickupBehavior;
import com.example.preston.whisted.Behavior.GreedyPlayBehavior;
import com.example.preston.whisted.Behavior.MinRespondingPlay;
import com.example.preston.whisted.Behavior.RandomPlay;
import com.example.preston.whisted.Behavior.SimpleBidBehavior;
import com.example.preston.whisted.Behavior.SimpleDecideTrumpBehavior;
import com.example.preston.whisted.Behavior.TotalDecideTrumpBehavior;
import com.example.preston.whisted.Cards.Card;

/**
 * Created by preston on 12/18/14.
 */
public class GreedyComputerPlayer extends Player{
    public GreedyComputerPlayer(){
        super(new GreedyPickupBehavior(), new GreedyPlayBehavior(), new MinRespondingPlay(), new SimpleBidBehavior(), new TotalDecideTrumpBehavior());
    }

    @Override
    public Card playCard(){
        ((GreedyPlayBehavior)play).setHand(hand);
        Card ret = play.playCard();
        hand.remove(ret);
        return ret;
    }

    @Override
    public Card playCard(Card otherCard){
        ((MinRespondingPlay)respond).setHand(hand);
        Card ret = respond.playRespondingCard(otherCard);
        hand.remove(ret);
        return ret;
    }

    @Override
    public void pickUpCard(Card firstChoice, Card secondChoice) {
        boolean debug = false;
        if (debug)
            System.out.println("ooh, choice of two cards!");
        hand.addCard(firstChoice.getValue() > secondChoice.getValue()?
        firstChoice: secondChoice);
    }
}
