package shirin.tahmasebi.mscfinalproject.profile;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import shirin.tahmasebi.mscfinalproject.MainFragmentActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;
import shirin.tahmasebi.mscfinalproject.util.Constant;
import shirin.tahmasebi.mscfinalproject.util.GMailSender;

public class ProfileActivity extends MainFragmentActivity implements ProfilePresenter.ProfileView {

    private ProfilePresenter mPresenter;
    private static final int AUTHORIZATION_CODE = 1993;
    private static final int ACCOUNT_CODE = 1601;
    private AuthPreferences authPreferences;
    private AccountManager accountManager;

    private final String SCOPE =
            Constant.GMAIL_COMPOSE + " " +
                    Constant.GMAIL_MODIFY + " " +
                    Constant.MAIL_GOOGLE_COM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new ProfilePresenter(this);

        accountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
        authPreferences = new AuthPreferences(this);
        if (authPreferences.getUser() != null
                && authPreferences.getToken() != null) {
            refreshTokenAndSendEmail();
        } else {
            chooseAccount();
        }

    }

    private void sendEmail() {
        new sendMailAsync().execute("salam",
                "boodyyyy",
                authPreferences.getUser(),
                authPreferences.getToken(),
                "shirin_tahmasebi94@yahoo.com");
    }

    @SuppressWarnings("deprecation")
    private void chooseAccount() {
        // اکانت گوگل را انتخاب کن
        Intent intent;
        intent = AccountManager.newChooseAccountIntent(null, null,
                new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, ACCOUNT_CODE);
    }

    private void requestToken() {
        // اگر توکن قبلی برای یوزر را منقضی کرده باشیم باید توکن جدید درخواست دهیم
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

    private void invalidateToken() {
        // توکن قبلی را منقضی کن و یه توکن جدید را بگیر
        AccountManager accountManager = AccountManager.get(this);
        accountManager.invalidateAuthToken("com.google", authPreferences.getToken());
        authPreferences.setToken(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AUTHORIZATION_CODE) {
                requestToken();
            } else if (requestCode == ACCOUNT_CODE) {
                // کاربر اکانت گوگل را انتخاب کرده
                // نام کاربری را بگیر و ذخیره کن
                // توکن را بروز کن

                String accountName = data
                        .getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                authPreferences.setUser(accountName);
                refreshTokenAndSendEmail();
            }
        }
    }

    private void refreshTokenAndSendEmail() {
        invalidateToken();
        requestToken();
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
                    Log.d("mscFinalProject", "در حال بازیابی توکن جدید ...  ");
                    String token = bundle
                            .getString(AccountManager.KEY_AUTHTOKEN);
                    authPreferences.setToken(token);
                    sendEmail();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class sendMailAsync extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            GMailSender gMailSender = new GMailSender();
            return gMailSender.sendMail(
                    params[0],
                    params[1],
                    params[2],
                    params[3],
                    params[4]
            );
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Log.w("mscFinalProject",
                        "پیام با موفقیت ارسال شد " +
                                "  " +
                                authPreferences.getUser() +
                                "  " +
                                authPreferences.getToken());
            } else {
                Log.w("mscFinalProject",
                        "ارسال پیام با شکست مواجه شد " +
                                "  " +
                                authPreferences.getUser() +
                                "  " +
                                authPreferences.getToken());
            }
        }
    }

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

}
