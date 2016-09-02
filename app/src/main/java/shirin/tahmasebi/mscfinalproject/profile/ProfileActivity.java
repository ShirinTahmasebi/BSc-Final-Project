package shirin.tahmasebi.mscfinalproject.profile;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import shirin.tahmasebi.mscfinalproject.MainFragmentActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Constant;
import shirin.tahmasebi.mscfinalproject.util.GMailSender;

public class ProfileActivity extends MainFragmentActivity implements
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private static final int AUTHORIZATION_CODE = 1993;
    private static final int ACCOUNT_CODE = 1601;
    private AuthPreferences authPreferences;
    private AccountManager accountManager;

    private final String SCOPE = Constant.GMAIL_COMPOSE + " " + Constant.GMAIL_MODIFY + " " + Constant.MAIL_GOOGLE_COM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);//AccountManager.get(this);


        authPreferences = new AuthPreferences(this);

        if (authPreferences.getUser() != null
                && authPreferences.getToken() != null) {
            doCoolAuthenticatedStuff();
        } else {
            chooseAccount();
        }

    }

    private void doCoolAuthenticatedStuff() {
        new senmailAsync().execute();   //uncomment to send mail
    }


    private void chooseAccount() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, ACCOUNT_CODE);
    }

    private void requestToken() {
        Account userAccount = null;
        String user = authPreferences.getUser();
        for (Account account : accountManager.getAccountsByType("com.google")) {
            if (account.name.equals(user)) {
                userAccount = account;
                break;
            }
        }

        accountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, this,
                new OnTokenAcquired(), null);
    }

    /**
     * call this method if your token expired, or you want to request a new
     * token for whatever reason. call requestToken() again afterwards in order
     * to get a new token.
     */
    private void invalidateToken() {
        AccountManager accountManager = AccountManager.get(this);
        accountManager.invalidateAuthToken("com.google", authPreferences.getToken());
        Log.v("ranjapp", "invalidating token............");

        authPreferences.setToken(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AUTHORIZATION_CODE) {
                requestToken();
            } else if (requestCode == ACCOUNT_CODE) {
                String accountName = data
                        .getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                authPreferences.setUser(accountName);

                // invalidate old tokens which might be cached. we want a fresh
                // one, which is guaranteed to work
                invalidateToken();

                requestToken();
            }
        }
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle bundle = result.getResult();

                Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
                if (launch != null) {
                    startActivityForResult(launch, AUTHORIZATION_CODE);
                } else {
                    String token = bundle
                            .getString(AccountManager.KEY_AUTHTOKEN);
                    Log.v("ranjapp", "Getting new token............");
                    authPreferences.setToken(token);

                    doCoolAuthenticatedStuff();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    private class senmailAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            GMailSender gMailSender = new GMailSender();
            gMailSender.sendMail("hi", "hi", authPreferences.getUser(), authPreferences.getToken(),
                    "shirin_tahmasebi94@yahoo.com");
            Log.v("ranjapp", "sent mail " + authPreferences.getUser() + "  " + authPreferences.getToken());
            return null;
        }
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        mPresenter = new ProfilePresenter(this);
//
//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are incl/uded in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//
//        // Build a GoogleApiClient with access to the Google Sign-In API and the
//        // options specified by gso.
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//        // Customize sign-in button. The sign-in button can be displayed in
//        // multiple sizes and color schemes. It can also be contextually
//        // rendered based on the requested scopes. For example. a red button may
//        // be displayed when Google+ scopes are requested, but a white button
//        // may be displayed when only basic profile is requested. Try adding the
//        // Scopes.PLUS_LOGIN scope to the GoogleSignInOptions to see the
//        // difference.
//        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
//        signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signInButton.setScopes(gso.getScopeArray());
//
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_profile_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_profile;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_profile;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
