package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;

/**
 * Created by preston on 12/18/14.
 */
public class GreedyPickupBehavior implements PickupCardBehavior {
    private int base = 9;
    @Override
    public boolean pickUpCard(Card potential) {
        return potential.getValue() > base;
    }
}
