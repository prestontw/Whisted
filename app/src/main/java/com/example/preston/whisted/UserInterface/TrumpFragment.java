package com.example.preston.whisted.UserInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.preston.whisted.Cards.Card;
import com.example.preston.whisted.R;

/**
 * Created by preston on 12/10/14.
 */
public class TrumpFragment extends DialogFragment implements DialogInterface.OnClickListener{
    public static final String TAG = "TrumpFragment";

    private String[] suits = {Card.CLUB, Card.DIAMOND, Card.HEART, Card.SPADE, Card.NO_TRUMP};


    private TrumpSelectionInterface bidParent = null;

    public interface TrumpSelectionInterface {
        public void bidSelected(String bid);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            bidParent = (TrumpSelectionInterface) activity;
        } catch (ClassCastException c){
            throw new ClassCastException(activity.toString() + " must implement necessary functions");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        
        builder.setTitle(getString(R.string.select_a_suit_fragment_title));
        builder.setItems(suits, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        bidParent.bidSelected(suits[i]);
    }
}
