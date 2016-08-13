package shirin.tahmasebi.mscfinalproject.organization;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class OrganizationDetailActivity extends MainActivity {

    private final static String EXTRA_ORGANIZATION_ID = "organizationid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.title_activity_organizationDetail));
        collapsingToolbarLayout.setExpandedTitleColor(
                getResources().getColor(android.R.color.transparent));
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRAN-Sans-Bold.ttf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);

        long id = getIntent().getLongExtra(EXTRA_ORGANIZATION_ID, -1);
        if (id != -1) {
            Organization organization = daoSession.getOrganizationDao().load(id);
            ((FontableTextView) findViewById(R.id.description)).setText(
                    organization.getDescription());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_organizationdetail_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_organizationDetail;
    }
}
