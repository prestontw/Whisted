package com.example.preston.whisted.Behavior.HumanBehavior;

import com.example.preston.whisted.Behavior.BidBehavior;
import com.example.preston.whisted.Cards.Deck;
import com.example.preston.whisted.Game.Game;
import com.example.preston.whisted.UserInterface.BidFragment;

/**
 * Created by preston on 12/14/14.
 */
public class VisualBid implements BidBehavior {
    @Override
    public int getBid(Deck hand, int currentBid) {
        new BidFragment().show(Game.getInstance().getActivity().getFragmentManager(),
                BidFragment.TAG);
        return BidBehavior.WAIT;
    }

}
