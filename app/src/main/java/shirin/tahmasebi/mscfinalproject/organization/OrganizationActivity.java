package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new OrganizationPresenter(this);
        mPresenter.getOrganizationsList(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onStart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_organization_layout;
    }

    @Override
    protected int getActivityHelpHint() {
        return R.string.helpHint_activity_organization;
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
        recyclerView.setAdapter(new OrganizationAdapter(mPresenter, list, this));
    }

    @Override
    public void init() {
        initSpinner();
        initSearchLayout();
    }

    @Override
    public void closeKeyboard() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void initSearchLayout() {
        findViewById(R.id.organization_searchButton_linearLayout).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String searchText =
                                ((EditText) findViewById(R.id.organization_search_editText))
                                        .getText().toString();

                        mPresenter.searchOrganization(
                                OrganizationActivity.this,
                                searchText
                        );
                    }
                }
        );


        ((EditText) findViewById(R.id.organization_search_editText)).setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView,
                                                  int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            String searchText =
                                    ((EditText) findViewById(R.id.organization_search_editText))
                                            .getText().toString();

                            mPresenter.searchOrganization(
                                    OrganizationActivity.this,
                                    searchText
                            );

                            return true;
                        }
                        return false;
                    }
                }
        );
    }

    private void initSpinner() {
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
                else if (position == 2) {
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

