package fr.selquicode.go4lunch.ui.workmates;


public interface OnWorkmateClickedListener {

    void onWorkmateClick(boolean hasChosen, String placeId);

    void onChatClicked(String workmateId);
}
