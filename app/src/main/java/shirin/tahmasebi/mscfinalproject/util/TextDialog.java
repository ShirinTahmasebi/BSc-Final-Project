package shirin.tahmasebi.mscfinalproject.util;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class TextDialog extends DialogFragment {
    String mText;

    public TextDialog() {
    }

    @SuppressLint("ValidFragment")
    public TextDialog(String text) {
        mText = text;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_text, container);

        // تایتل دیالوگ را بردار
        getDialog().getWindow().requestFeature(
                Window.FEATURE_NO_TITLE
        );

        ((FontableTextView) view.findViewById(R.id.toolbar_hint_textView)).setText(mText);
        view.findViewById(R.id.toolbar_cancelDialog_imageView).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
        return view;
    }
}
