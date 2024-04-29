package fr.selquicode.go4lunch.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

import fr.selquicode.go4lunch.data.model.Message;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.databinding.ActivityChatBinding;
import fr.selquicode.go4lunch.ui.ViewModelFactory;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.Listener{

    private ActivityChatBinding binding;
    private ChatViewModel chatViewModel;
    private ChatAdapter chatAdapter;
    public static final String WORKMATE_ID = "WORKMATE_ID";


    /**
     * Start ChatActivity
     *
     * @param context the context
     */
    public static void launch(String workmateId, Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(ChatActivity.WORKMATE_ID, workmateId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setViewModel();
        setWorkmateBar();
        configureRecyclerView();
        setupSendBtn();
    }

    private void setViewModel() {
        chatViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(ChatViewModel.class);
    }

    private void setWorkmateBar() {
        chatViewModel.getWorkmateToChat().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                render(user);
            }
        });
    }

    private void render(User user) {
        binding.workmateName.setText(user.getDisplayName().contains(" ")?
                user.getDisplayName().split(" ")[0] : user.getDisplayName());
        Glide.with(this)
                .load(user.getPhotoUserUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.workmatePhoto);
    }

    private void configureRecyclerView() {
        //Configure Adapter
        chatAdapter = new ChatAdapter(generateOptionsForAdapter(chatViewModel.getAllMessageForChat()),
                Glide.with(this),
                this,
                chatViewModel);

        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                binding.chatRecyclerview.smoothScrollToPosition(chatAdapter.getItemCount());
                //scroll to bottom on new message
            }
        });

        //Configure RecyclerView
        binding.chatRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.chatRecyclerview.setAdapter(chatAdapter);
    }

    private void setupSendBtn() {
        binding.sendBtn.setOnClickListener(view -> {
            chatViewModel.sendMessage(binding.chatEditText);
            binding.chatEditText.setText("");
        });
    }



    //Create options for RecyclerView from a Query
    private FirestoreRecyclerOptions<Message> generateOptionsForAdapter(Query query) {
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class)
                .setLifecycleOwner(this)
                .build();
    }


    @Override
    public void onDataChanged() {
        //show textView in case RecyclerView is empty
        binding.emptyRecyclerview.setVisibility(chatAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);

    }
}
