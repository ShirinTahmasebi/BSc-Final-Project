package shirin.tahmasebi.mscfinalproject.organization;

import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class WriteEmailActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_email;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_writeEmail;
    }
}
