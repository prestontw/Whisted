package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;

/**
 * Created by preston on 12/14/14.
 */
public class SimpleBidBehavior implements BidBehavior {

    public static final int VALUE_IN_DECK = 364;
    public static final int BOOK = 7;

    @Override
    public int getBid(Deck hand, int currentBid) {

        int total = 0;
        for (Card card: hand.getDeck()){
            total += card.getValue();
        }
        int possibleBid = total * BOOK / VALUE_IN_DECK;

        if (possibleBid > currentBid)
            return possibleBid;
        return PASS;
    }
}
