package shirin.tahmasebi.mscfinalproject.util.mail.gmail;

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

public class MailInteractor {

    private MailListener mListener;

    private AccountManager mAccountManager;
    private AuthPreferences mAuthPreferences;
    @SuppressWarnings("FieldCanBeLocal")
    private final String SCOPE =
            Constant.GMAIL_COMPOSE + " " +
                    Constant.GMAIL_MODIFY + " " +
                    Constant.MAIL_GOOGLE_COM;
    private static final int AUTHORIZATION_CODE = 1993;

    public MailInteractor(MailListener listener, Activity context) {
        mListener = listener;
        mAccountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        mAuthPreferences = new AuthPreferences(context);
    }

    public void sendEmail(String subject, String body, String user, String token, String to) {
        new SendEmailAsync(new SendingEmailCallBack()).execute(subject, body, user, token, to);
    }

    public class SendingEmailCallBack {
        public void onEmailSendingFinished(Boolean aBoolean) {
            mListener.onEmailSendingFinished(aBoolean);
        }
    }

    private void requestToken(Context context, String subject, String body, String to) {
        // اگر توکن قبلی برای یوزر را منقضی کرده باشیم باید توکن جدید درخواست دهیم
        Account userAccount = null;
        String user = mAuthPreferences.getUser();
        for (Account account : mAccountManager.getAccountsByType("com.google")) {
            if (account.name.equals(user)) {
                userAccount = account;
                break;
            }
        }
        mAccountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, (Activity) context,
                new OnTokenAcquired(context, subject, body, to), null);
    }

    private void invalidateToken(Context context) {
        // توکن قبلی را منقضی کن و یه توکن جدید را بگیر
        AccountManager accountManager = AccountManager.get(context);
        accountManager.invalidateAuthToken("com.google", mAuthPreferences.getToken());
        mAuthPreferences.setToken(null);
    }


    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

        private Context mContext;
        private String mSubject;
        private String mBody;
        private String mTo;

        public OnTokenAcquired(Context context, String subject, String body, String to) {
            this.mContext = context;
            mBody = body;
            mSubject = subject;
            mTo = to;
        }

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle bundle = result.getResult();
                Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
                if (launch != null) {
                    ((Activity) this.mContext).startActivityForResult(launch, AUTHORIZATION_CODE);
                } else {
                    Log.d("mscFinalProject", "در حال بازیابی توکن جدید ...  ");
                    String token = bundle
                            .getString(AccountManager.KEY_AUTHTOKEN);
                    mAuthPreferences.setToken(token);
                    sendEmail(
                            mSubject,
                            mBody,
                            mAuthPreferences.getUser(),
                            mAuthPreferences.getToken(),
                            mTo
                    );
                }
            } catch (Exception e) {
                Log.w("mscFinalProject",
                        "بازیابی توکن با شکست مواجه شد /n اتصال به اینترنت بررسی شود");
                mListener.onEmailSendingFinished(false);
            }
        }
    }

    public void refreshTokenAndSendEmail(Context context,
                                         String subject,
                                         String body,
                                         String to) {
        invalidateToken(context);
        requestToken(context, subject, body, to);
    }


    public interface MailListener {

        void onEmailSendingFinished(Boolean aBoolean);
    }
}
