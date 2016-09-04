package shirin.tahmasebi.mscfinalproject.profile;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;

import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;

public class ProfileInteractor {

    private static final int ACCOUNT_CODE = 1601;
    private ProfileListener mListener;

    public ProfileInteractor(ProfileListener listener) {
        mListener = listener;
    }

    public void saveAccountTypeSelected(Activity activity, int position,
                                        AuthPreferences mAuthPreferences) {
        if (position == AccountTypeEnum.Google.getIntValue()
                && mAuthPreferences.getKeyAccountType() != null
                && AccountTypeEnum.Google.toString().equals(
                mAuthPreferences.getKeyAccountType()
        )) {
            return;
        }
        mAuthPreferences.clearAuthPrefs();

        if (position == AccountTypeEnum.Google.getIntValue()) {
            mAuthPreferences.setKeyAccountType(
                    AccountTypeEnum.Google.toString()
            );
            mListener.onGoogleAccountSelected();
        } else if (position == AccountTypeEnum.Other.getIntValue()) {
            mAuthPreferences.setKeyAccountType(
                    AccountTypeEnum.Other.toString()
            );
        } else if (position == AccountTypeEnum.NothingSelected.getIntValue()) {
            mAuthPreferences.setKeyAccountType(
                    AccountTypeEnum.NothingSelected.toString()
            );
        }
    }

    public void saveUser(Intent data, AuthPreferences mAuthPreferences) {
        String accountName = data
                .getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        mAuthPreferences.setUser(accountName);
    }

    public interface ProfileListener {

        void onGoogleAccountSelected();
    }
}
