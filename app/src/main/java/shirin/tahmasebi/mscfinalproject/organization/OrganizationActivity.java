package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class OrganizationActivity extends MainActivity
        implements OrganizationPresenter.OrganizationView {

    private final static String EXTRA_ORGANIZATION_ID = "organizationid";
    OrganizationPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new OrganizationPresenter(this);
        mPresenter.getOrganizationsList(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onStart();
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

    @Override
    public void showOrganizationsList(List<Organization> list) {
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.organization_organizationList_recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new OrganizationAdapter(mPresenter, list));
    }

    @Override
    public void init() {
        Spinner spinner = (Spinner) findViewById(R.id.organization_filter_spinner);
        SpinnerAdapter adapter = new SpinnerAdapter(
                this,
                R.layout.item_organization_filter,
                Arrays.asList(getResources().getStringArray(
                        R.array.lable_organizationFilter
                ))
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int position, long id) {
                //همه سازمان ها را نمایش بده
                if (position == 0) {
                    mPresenter.getOrganizationsList(OrganizationActivity.this);
                }
                //سازمانهای نشانه گذاری شده را نمایش بده
                else if (position == 1) {
                    mPresenter.getOrganizationsListByFavoriteProperty(
                            OrganizationActivity.this,
                            true
                    );
                }
                //سازمانهای نشانه گذاری نشده را نمایش بده
                else if (position == 2){
                    mPresenter.getOrganizationsListByFavoriteProperty(
                            OrganizationActivity.this,
                            false
                    );
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}

