package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;

public class OrganizationActivity extends MainActivity implements
        OrganizationPresenter.OrganizationView {

    private final static String EXTRA_ORGANIZATION_ID = "organizationid";

    OrganizationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new OrganizationPresenter(this);

        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.organization_organizationList_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(
                new OrganizationAdapter(
                        this,
                        mPresenter,
                        daoSession.getOrganizationDao().loadAll()
                )
        );
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_organization_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_organizations;
    }

    @Override
    public void showOrganizationDetails(long organizationId) {
        Intent organizationDetail = new Intent(this, OrganizationDetailActivity.class);
        organizationDetail.putExtra(EXTRA_ORGANIZATION_ID, organizationId);
        startActivity(organizationDetail);
    }
}
