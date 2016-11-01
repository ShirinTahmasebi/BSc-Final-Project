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
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class SelectWriteModeDialog extends DialogFragment {
    SelectWriteModeDialogListener mListener;
    Organization mOrganization;

    public interface SelectWriteModeDialogListener {

        void onWriteOptionClicked(SelectWriteModeDialog dialog, int type, Organization organization);

        void onCancelDialogClicked(SelectWriteModeDialog selectWriteModeDialog);
    }

    public SelectWriteModeDialog() {

    }

    @SuppressLint("ValidFragment")
    public SelectWriteModeDialog(SelectWriteModeDialogListener listener, Organization org) {
        mListener = listener;
        mOrganization = org;
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

        if (mOrganization.getEmailAddress() != null) {
            view.findViewById(R.id.organizationDetail_writeEmail_linearLayout).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tag = Integer.parseInt(view.getTag().toString());
                            mListener.onWriteOptionClicked(
                                    SelectWriteModeDialog.this,
                                    tag,
                                    mOrganization
                            );
                        }
                    }
            );
        } else {
            view.findViewById(R.id.organizationDetail_writeEmail_linearLayout)
                    .setVisibility(View.GONE);
        }

        if (mOrganization.getPhoneNumber() != null) {
            view.findViewById(R.id.organizationDetail_writeCall_linearLayout).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tag = Integer.parseInt(view.getTag().toString());
                            mListener.onWriteOptionClicked(
                                    SelectWriteModeDialog.this,
                                    tag,
                                    mOrganization
                            );
                        }
                    }
            );
        } else {
            view.findViewById(R.id.organizationDetail_writeCall_linearLayout)
                    .setVisibility(View.GONE);
        }

        if (mOrganization.getSmsNumber() != null) {
            view.findViewById(R.id.organizationDetail_writeSms_linearLayout).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tag = Integer.parseInt(view.getTag().toString());
                            mListener.onWriteOptionClicked(
                                    SelectWriteModeDialog.this,
                                    tag,
                                    mOrganization
                            );
                        }
                    }
            );
        } else {
            view.findViewById(R.id.organizationDetail_writeSms_linearLayout)
                    .setVisibility(View.GONE);
        }

        if (mOrganization.getSiteUrl() != null) {
            view.findViewById(R.id.organizationDetail_visitSite_linearLayout).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int tag = Integer.parseInt(view.getTag().toString());
                            mListener.onWriteOptionClicked(
                                    SelectWriteModeDialog.this,
                                    tag,
                                    mOrganization
                            );
                        }
                    }
            );
        } else {
            view.findViewById(R.id.organizationDetail_visitSite_linearLayout)
                    .setVisibility(View.GONE);
        }

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
