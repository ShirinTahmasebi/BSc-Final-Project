package shirin.tahmasebi.mscfinalproject.organization;

import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class OrganizationActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_organization_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_organizations;
    }
}
