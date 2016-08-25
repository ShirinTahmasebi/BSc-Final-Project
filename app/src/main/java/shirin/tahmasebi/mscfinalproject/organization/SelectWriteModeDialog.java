package shirin.tahmasebi.mscfinalproject.organization;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import shirin.tahmasebi.mscfinalproject.R;

public class SelectWriteModeDialog extends DialogFragment {
    SelectWriteModeDialogListener mListener;

    public interface SelectWriteModeDialogListener {

        void onWriteEmailClicked(SelectWriteModeDialog dialog);

        void onCancelDialogClicked(SelectWriteModeDialog selectWriteModeDialog);
    }

    public SelectWriteModeDialog() {

    }

    @SuppressLint("ValidFragment")
    public SelectWriteModeDialog(SelectWriteModeDialogListener listener) {
        mListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.dialog_organizationdetail_write, container);

        // تایتل دیالوگ را بردار
        getDialog().getWindow().requestFeature(
                Window.FEATURE_NO_TITLE
        );

        view.findViewById(R.id.organizationDetail_writeEmail_linearLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onWriteEmailClicked(SelectWriteModeDialog.this);
                    }
                }
        );

        view.findViewById(R.id.organizationDetail_writeCall_linearLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onWriteEmailClicked(SelectWriteModeDialog.this);
                    }
                }
        );

        view.findViewById(R.id.organizationDetail_writeSms_linearLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onWriteEmailClicked(SelectWriteModeDialog.this);
                    }
                }
        );

        view.findViewById(R.id.organizationDetail_cancelDialog_imageView).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onCancelDialogClicked(SelectWriteModeDialog.this);
                    }
                }
        );
        return view;
    }
}
