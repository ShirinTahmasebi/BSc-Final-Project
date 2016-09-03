package shirin.tahmasebi.mscfinalproject.profile;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;

import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;

public class ProfileInteractor {
    private ProfileListener mListener;

    private static final int ACCOUNT_CODE = 1601;

    public ProfileInteractor(ProfileListener listener) {
        mListener = listener;
    }

    public void saveAccountTypeSelected(Activity activity, int position,
                                        AuthPreferences mAuthPreferences) {
        mAuthPreferences.clearAuthPrefs();

        if (position == AccountTypeEnum.Google.getIntValue()) {
            mAuthPreferences.setKeyAccountType(
                    AccountTypeEnum.Google.toString()
            );
            chooseAccount(activity);
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

    @SuppressWarnings("deprecation")
    private void chooseAccount(Activity context) {
        // اکانت گوگل را انتخاب کن
        Intent intent;
        intent = AccountManager.newChooseAccountIntent(null, null,
                new String[]{"com.google"}, false, null, null, null, null);
        context.startActivityForResult(intent, ACCOUNT_CODE);
    }

    public void saveUser(Intent data, AuthPreferences mAuthPreferences) {
        String accountName = data
                .getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        mAuthPreferences.setUser(accountName);
    }

    public interface ProfileListener {

    }
}
