package fr.selquicode.go4lunch.ui.chat;

import android.text.Editable;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.Query;

import fr.selquicode.go4lunch.data.firebase.FirebaseAuthRepository;
import fr.selquicode.go4lunch.data.firebase.FirestoreRepository;
import fr.selquicode.go4lunch.data.model.CreateMessageRequest;
import fr.selquicode.go4lunch.data.model.User;
import fr.selquicode.go4lunch.data.model.UserSender;

public class ChatViewModel extends ViewModel {

    private final FirestoreRepository firestoreRepository;
    private final FirebaseAuthRepository firebaseAuthRepository;
    private final String workmateId;


    public ChatViewModel(FirestoreRepository firestoreRepository, FirebaseAuthRepository firebaseAuthRepository, String workmateId) {
        this.firestoreRepository = firestoreRepository;
        this.firebaseAuthRepository = firebaseAuthRepository;
        this.workmateId = workmateId;

    }

    public String getCurrentUserId(){
        return firebaseAuthRepository.getCurrentUser().getUid();
    }

    public Query getAllMessageForChat(){
        return firestoreRepository.getAllMessageForChat(workmateId, getCurrentUserId());
    }

    public void sendMessage(EditText editText) {
        if(!TextUtils.isEmpty(editText.getText())){
            CreateMessageRequest messageRequest = new CreateMessageRequest(editText.getText().toString(), Timestamp.now());
            firestoreRepository.createMessageForChat(messageRequest, getCurrentUserId(), workmateId);
        }
    }

    public LiveData<User> getWorkmateToChat(){
        return firestoreRepository.getOneUser(workmateId);
    }


}
