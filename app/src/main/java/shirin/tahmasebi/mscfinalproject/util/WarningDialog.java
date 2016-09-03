package shirin.tahmasebi.mscfinalproject.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.profile.ProfileActivity;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class WarningDialog extends DialogFragment {
    private String mText;
    private Class mDestinationActivity;
    private Activity mSourceActivity;

    public WarningDialog() {
    }

    @SuppressLint("ValidFragment")
    public WarningDialog(String text, Activity sourceActivity,
                         Class destinationActivity) {
        mText = text;
        mSourceActivity = sourceActivity;
        mDestinationActivity = destinationActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_confirm, container);

        // تایتل دیالوگ را بردار
        getDialog().getWindow().requestFeature(
                Window.FEATURE_NO_TITLE
        );

        ((FontableTextView) view.findViewById(R.id.toolbar_hint_textView)).setText(mText);
        view.findViewById(R.id.toolbar_cancelDialog_imageView).setOnClickListener(cancel);
        view.findViewById(R.id.confirm_button).setOnClickListener(confirm);
        return view;
    }

    View.OnClickListener confirm = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
            mSourceActivity.finish();
            Intent intent = new Intent(mSourceActivity, mDestinationActivity);
            startActivity(intent);

        }
    };


    View.OnClickListener cancel = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
            mSourceActivity.finish();
        }
    };
}
