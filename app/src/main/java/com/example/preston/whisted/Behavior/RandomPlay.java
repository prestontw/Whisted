package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;

import java.util.Random;

/**
 * Created by preston on 11/26/14.
 */
public class RandomPlay implements PlayCardBehavior {
    private Deck hand;

    //note that this does not remove the card from our hand
    @Override
    public Card playCard() {

        //get integer within range of hand, play card and remove it
        Random random = new Random();
        int randomIndex = random.nextInt(hand.size());
        return hand.get(randomIndex);

    }


    public void setHand(Deck hand){
        this.hand = hand;
    }

}
