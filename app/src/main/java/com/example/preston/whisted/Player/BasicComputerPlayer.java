package com.example.preston.whisted.Player;

import com.example.preston.whisted.Behavior.KnowHalfTheDeckBehavior;
import com.example.preston.whisted.Behavior.MinRespondingPlay;
import com.example.preston.whisted.Behavior.RandomPlay;
import com.example.preston.whisted.Behavior.SimpleBidBehavior;
import com.example.preston.whisted.Behavior.SimpleDecideTrumpBehavior;
import com.example.preston.whisted.Cards.Card;

/**
 * Created by preston on 11/26/14.
 */
public class BasicComputerPlayer extends Player {

    public BasicComputerPlayer(){
        super(new KnowHalfTheDeckBehavior(), new RandomPlay(), new MinRespondingPlay(), new SimpleBidBehavior(), new SimpleDecideTrumpBehavior());
    }


    @Override
    public Card playCard(){
        ((RandomPlay)play).setHand(hand);
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
}
