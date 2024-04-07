package fr.selquicode.go4lunch.ui.utils;

import android.view.View;

public interface OnWorkmateClickedListener {

    void onWorkmateClick(boolean hasChosen, String placeId);

    void onChatClicked(String workmateId);
}
