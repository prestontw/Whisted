package com.example.preston.whisted.UserInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.preston.whisted.Behavior.BidBehavior;
import com.example.preston.whisted.Game.Game;
import com.example.preston.whisted.R;

/**
 * Created by preston on 12/14/14.
 */
public class BidFragment extends DialogFragment implements DialogInterface.OnClickListener{
    public static final String TAG = "BidFragment";

    private String[] bids = {Game.getInstance().getActivity().getString(R.string.bid_fragment_pass), "1", "2", "3", "4", "5", "6", "7"};
    private int PASS_INDEX = 0;
    private BidSelectionInterface bidParent = null;

    public interface BidSelectionInterface {
        public void bidSelected(int bid);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            bidParent = (BidSelectionInterface) activity;
        } catch (ClassCastException c){
            throw new ClassCastException(activity.toString() + " must implement necessary functions");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.place_a_bid_fragment_title));
        builder.setItems(bids, this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i == PASS_INDEX)
            bidParent.bidSelected(BidBehavior.PASS);
        else
            bidParent.bidSelected(Integer.parseInt(bids[i]));
    }
}
