package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;

/**
 * Created by preston on 11/26/14.
 */
public class KnowHalfTheDeckBehavior implements PickupCardBehavior{

    @Override
    public boolean pickUpCard(Card potential) {
        return false;
    }
}
