package fr.selquicode.go4lunch.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.material.snackbar.Snackbar;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import fr.selquicode.go4lunch.R;
import fr.selquicode.go4lunch.databinding.ActivityLoginBinding;
import fr.selquicode.go4lunch.ui.MainActivity;
import fr.selquicode.go4lunch.ui.ViewModelFactory;

public class LogInActivity extends AppCompatActivity {

    private LogInViewModel viewModel;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // bind with LogInViewModel
        viewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(LogInViewModel.class);

        isUserSigned();
    }

    private void isUserSigned() {
        Log.i("logInAct", viewModel.isUserLogged() + "");
        if(viewModel.isUserLogged()){
                startMainActivity();
        }else{
            Log.i("login", "open firebase activity");
            startFirebaseLogIn();
        }
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
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build());

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

    /**
     * create an ActivityResultLauncher<Intent> to call firebaseUI activity
     */
     private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            this::onSignInResult
    );

    /**
     * catch result of the authentication
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            viewModel.createUser();
            startMainActivity();
            finish();
        } else {
            //sign in failed
            Log.i("MA", "sign in failed");
            if(response == null){
                //means that the user pressed back button
                showSnackBar(R.string.cancelled_signin);
                finish();
                return;
            }
            if(Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK){
                //means that there is no internet connexion
                showSnackBar(R.string.no_network);
                finish();
                return;
            }
            showSnackBar(R.string.unknown_error);
            finish();
            Log.e("LogInActivity", "Sign-in error : ", response.getError());
        }
    }

    private void showSnackBar(int value) {
        Snackbar.make(binding.getRoot(), String.valueOf(value), Snackbar.LENGTH_LONG).show();
    }

    /**
     * To start MainActivity
     */
    private void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        finish();
    }
}
