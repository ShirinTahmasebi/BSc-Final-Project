package shirin.tahmasebi.mscfinalproject.util.mail.gmail;

import android.os.AsyncTask;

public class SendEmailAsync extends AsyncTask<String, Void, Boolean> {

    MailInteractor.SendingEmailCallBack mCallBack;

    public SendEmailAsync(MailInteractor.SendingEmailCallBack callBack) {
        mCallBack = callBack;
    }

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
        mCallBack.onEmailSendingFinished(aBoolean);
    }
}