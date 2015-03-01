package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;

/**
 * Created by preston on 11/26/14.
 */
public interface PickupCardBehavior {
    //should rename this want card
    public boolean pickUpCard(Card potential);
    //and have another function for deciding between two cards
    //public boolean wantFirstCard(Card first, Card second);
}
