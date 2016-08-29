package shirin.tahmasebi.mscfinalproject.profile;

import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class ProfileActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
