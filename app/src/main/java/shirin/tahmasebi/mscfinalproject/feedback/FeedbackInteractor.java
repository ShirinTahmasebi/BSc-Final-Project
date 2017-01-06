package shirin.tahmasebi.mscfinalproject.feedback;

import android.util.Log;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;

import shirin.tahmasebi.mscfinalproject.io.models.Feedback;

class FeedbackInteractor {
    FeedbackListener mListener;

    FeedbackInteractor(FeedbackListener listener) {
        mListener = listener;
    }

    void sendEmail(String subject, String text, String developerEmail) {
        Backendless.Messaging.sendHTMLEmail(subject, text, developerEmail, responder);
        Log.w("mscFinalProject", "Feedback Email Sent!");
    }

    private AsyncCallback<Void> responder = new AsyncCallback<Void>() {
        @Override
        public void handleResponse(Void response) {
            Log.w("mscFinalProject", "[ASYNC] Feedback Email Has Been Sent Successfully");
        }

        @Override
        public void handleFault(BackendlessFault fault) {
            Log.w("mscFinalProject", "Error Sending Feedback Email - " + fault.getMessage());
        }
    };

    void sendFeedBackToServer(String subject, String text) {
        Backendless.Persistence.save(
                new Feedback(subject, text),
                new BackendlessCallback<Feedback>() {
                    @Override
                    public void handleResponse(Feedback feedback) {
                        mListener.onFeedBackSendingFinished(true);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        mListener.onFeedBackSendingFinished(false);
                    }
                });

    }

    interface FeedbackListener {
        void onFeedBackSendingFinished(Boolean result);
    }
}
