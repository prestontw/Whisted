package com.example.preston.whisted.Behavior;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.Cards.Deck;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by preston on 12/20/14.
 */
public class GreedyPlayBehavior implements PlayCardBehavior {
    private Deck hand;
    private String trump;
    private boolean decider;
    @Override
    public Card playCard() {
        Random random = new Random();
        int randomIndex = random.nextInt(hand.size());
        Card tempCard = hand.get(randomIndex);
        List<Card> cards = hand.getCardsofSuit(tempCard.getSuit());
        Collections.sort(cards);
        return cards.get(cards.size()-1);
    }

    public void setHand(Deck hand){ this.hand = hand;}
}
