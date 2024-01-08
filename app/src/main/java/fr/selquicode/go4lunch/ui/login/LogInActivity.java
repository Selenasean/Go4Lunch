package fr.selquicode.go4lunch.ui.login;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.ui.MainActivity;
import fr.selquicode.go4lunch.ui.utils.ViewModelFactory;

public class LogInActivity extends AppCompatActivity {

    private LogInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // bind with LogInViewModel
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(LogInViewModel.class);

        isUserSigned();
    }

    private void isUserSigned() {
//        if(isUserLogged()){
//            Log.i("currentUser", currentUser.toString());
//                startMainActivity();
//        }else{
//            Log.i("login", "open firebase activity");
//            startFirebaseLogIn();
//        }
        startFirebaseLogIn();
    }

    private void startFirebaseLogIn() {
        //bind the created custom layout XML resource & firebaseUI
        AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.firebase_login)
                .setEmailButtonId(R.id.sign_email)
                .setGoogleButtonId(R.id.sign_google)
                .build();

        //choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        //create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.Theme_LogIn)
                .setIsSmartLockEnabled(true)
                .setAuthMethodPickerLayout(customLayout)
                .build();

        signInLauncher.launch(signInIntent);

    }

    //create an ActivityResultLauncher<Intent> to call firebaseUI activity
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    //catch result of the authentication
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed i
            viewModel.createUser();
            startMainActivity();
        } else {
            Log.i("MA", "sign in failed");
            //TODO
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }

    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
