package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Deck;

/**
 * Created by preston on 12/10/14.
 */
public interface BidBehavior {
    public static int PASS = -1;
    public static int WAIT = -2;
    public int getBid(Deck hand, int currentBid);
}
