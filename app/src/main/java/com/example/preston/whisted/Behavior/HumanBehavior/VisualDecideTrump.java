package com.example.preston.whisted.Behavior.HumanBehavior;

import com.example.preston.whisted.Behavior.DecideTrumpBehavior;
import com.example.preston.whisted.Cards.Deck;
import com.example.preston.whisted.Game.Game;
import com.example.preston.whisted.UserInterface.TrumpFragment;

/**
 * Created by preston on 12/14/14.
 */
public class VisualDecideTrump implements DecideTrumpBehavior{
    public static String NO_TRUMP = "zoinks";
    @Override
    public String decideTrump(Deck hand) {
        new TrumpFragment().show(Game.getInstance().getActivity().getFragmentManager(), TrumpFragment.TAG);
        return NO_TRUMP;
    }
}
