package fr.selquicode.go4lunch.ui.chat;

import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.data.model.Message;
import fr.selquicode.go4lunch.databinding.ItemChatBinding;

public class ChatAdapter extends FirestoreRecyclerAdapter<Message, ChatAdapter.ViewHolder> {


    public interface Listener {
        void onDataChanged();
    }

    //VIEW TYPE
    private static final int SENDER_TYPE = 1;
    private static final int RECEIVER_TYPE = 2;

    private final RequestManager glide;
    private final Listener callback;
    private final ChatViewModel chatViewModel;

    /**
     * Constructor
     * @param options from Firestore
     * @param glide to shape images
     * @param callback is a listener
     */
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Message> options,
                       RequestManager glide,
                       Listener callback,
                       ChatViewModel chatViewModel){
        super(options);
        this.glide = glide;
        this.callback = callback;
        this.chatViewModel = chatViewModel;
    }

    //START PART CLASS VIEW HOLDER
    /**
     * ViewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private final boolean isSender;
        private final int colorCurrentUser;
        private final int colorRemoteUser;
        private final TextView messageTV;
        private final TextView dateTV;
        private final ImageView profilePicture;
        private final LinearLayout messageContainerItem;
        private final LinearLayout messageTextContainer;
        private final LinearLayout profileContainer;

        public ViewHolder(@NonNull View itemView, boolean isSender) {
            super(itemView);
            this.isSender = isSender;
            ItemChatBinding binding = ItemChatBinding.bind(itemView);

            //bind with layout elements
            messageTV = binding.messageTv;
            dateTV = binding.dateTv;
            profilePicture = binding.profileImage;
            messageTextContainer = binding.messageTextContainer;
            messageContainerItem = binding.messageContainerItem;
            profileContainer = binding.profileContainer;

            //Setup default color
            colorCurrentUser = ContextCompat.getColor(itemView.getContext(), R.color.orangeClear);
            colorRemoteUser = ContextCompat.getColor(itemView.getContext(), R.color.grey);
        }

        /**
         * Update display on screen according to messages
         * @param message -type Message
         * @param glide -type RequestManager to update profile picture
         */
        public void updateWithMessage(Message message, RequestManager glide){
            //Update message
            messageTV.setText(message.getMessage());
            messageTV.setTextAlignment(isSender ? View.TEXT_ALIGNMENT_TEXT_END : View.TEXT_ALIGNMENT_TEXT_START);

            //Update date
            Timestamp timestampDate = message.getDateCreated();
            Instant instant = timestampDate.toDate().toInstant();
            dateTV.setText(convertDateToHour(instant));

            // Update profile picture
            if (message.getUserSender().getImageUrl() != null){
                glide.load(message.getUserSender().getImageUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilePicture);
            }

            updateLayoutFromSenderType();

        }

        /**
         * Update display accroding to user's status : he's sender or not
         */
        private void updateLayoutFromSenderType(){
            //update Message Bubble Color Background
            ((GradientDrawable) messageTextContainer.getBackground()).setColor(isSender ? colorCurrentUser : colorRemoteUser);
            messageTextContainer.requestLayout();

            if(!isSender){
                updateProfileContainer();
                updateMessageContainer();
            }
        }

        /**
         * Display for profileContainer when it's not the user's logged message
         */
        private void updateProfileContainer() {
            //update constraint for the profile container -> push it to the left when it's the workmate message
            ConstraintLayout.LayoutParams profileContainerLayoutParams = (ConstraintLayout.LayoutParams) profileContainer.getLayoutParams();
            profileContainerLayoutParams.endToEnd = ConstraintLayout.LayoutParams.UNSET;
            profileContainerLayoutParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            profileContainer.requestLayout();
        }

        /**
         * Display for messageContainer when it's not the user's logged message
         */
        private void updateMessageContainer() {
            //update constraint for message container -> push it to the right of the profile container when it's the workmate message
            ConstraintLayout.LayoutParams messageContainerLayoutParams = (ConstraintLayout.LayoutParams) messageContainerItem.getLayoutParams();
            messageContainerLayoutParams.startToStart = ConstraintLayout.LayoutParams.UNSET;
            messageContainerLayoutParams.endToStart = ConstraintLayout.LayoutParams.UNSET;
            messageContainerLayoutParams.startToEnd = profileContainer.getId();
            messageContainerLayoutParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            messageContainerLayoutParams.horizontalBias = 0.0f;
            messageContainerItem.requestLayout();

            //update gravity for the text of message -> align it to the left
            LinearLayout.LayoutParams messageTextLayoutParams = (LinearLayout.LayoutParams) messageTextContainer.getLayoutParams();
            messageTextLayoutParams.gravity = Gravity.START;
            messageTextContainer.requestLayout();

            LinearLayout.LayoutParams dateLayoutParams = (LinearLayout.LayoutParams) dateTV.getLayoutParams();
            dateLayoutParams.gravity = Gravity.BOTTOM | Gravity.START;
            dateTV.requestLayout();
        }

        /**
         * To convert an Instant into a String
         * @param dateCreated of the message sent - type Instant
         * @return a date readable by user - type String
         */
        private String convertDateToHour(Instant dateCreated) {
            LocalDateTime localDateTime = LocalDateTime.ofInstant(dateCreated, ZoneId.systemDefault());
            return localDateTime.format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm"));
        }


    }
    //END OF PART CLASS VIEW HOLDER

    @Override
    public int getItemViewType(int position){
        //determine the type of message : is the user sender or not
        String currentUserId = chatViewModel.getCurrentUserId();
        boolean isSender = getItem(position).getUserSender().getUid().equals(currentUserId);

        return isSender ? SENDER_TYPE : RECEIVER_TYPE;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position,
                                 @NonNull Message message) {
        holder.itemView.invalidate();
        holder.updateWithMessage(message, this.glide);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       ItemChatBinding binding = ItemChatBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new ViewHolder(binding.getRoot(), viewType == 1);
    }

    @Override
    public void onDataChanged(){
        super.onDataChanged();
        this.callback.onDataChanged();
    }
}
