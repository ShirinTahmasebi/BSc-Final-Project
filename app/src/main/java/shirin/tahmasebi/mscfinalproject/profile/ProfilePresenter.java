package shirin.tahmasebi.mscfinalproject.profile;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Constant;

@SuppressWarnings("FieldCanBeLocal")
public class ProfilePresenter implements ProfileInteractor.ProfileListener {
    private ProfileView mView;
    private ProfileInteractor mInteractor;
    private AuthPreferences mAuthPreferences;
    private AccountManager mAccountManager;

    public ProfilePresenter(ProfileView view) {
        mView = view;
        mInteractor = new ProfileInteractor(this);
    }

    public void onStart() {
        mView.init();
    }

    public void accountTypeSelected(Activity context, int position) {
        mAccountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        mAuthPreferences = new AuthPreferences(context);
        mInteractor.saveAccountTypeSelected(context, position, mAuthPreferences);
    }

    public void googleAccountSelected(Intent data) {
        mInteractor.saveUser(data, mAuthPreferences);
//        refreshTokenAndSendEmail(context);
    }



    public interface ProfileView {
        void init();
    }
}
