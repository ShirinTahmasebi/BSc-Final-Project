package shirin.tahmasebi.mscfinalproject.organization;

import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.inlineBrowser.InlineBrowserActivity;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.SharedData;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;

public class OrganizationActivity extends MainActivity
        implements OrganizationPresenter.OrganizationView {

    private static final int PERMISION_REQUEST_PHONECALL = 1234;
    OrganizationPresenter mPresenter;
    private OrganizationAdapter organizationAdapter;
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.context = this.getBaseContext();
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
    protected String getScreenName() {
        return OrganizationActivity.class.getSimpleName();
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
    public void showOrganizationsList(List<Organization> list) {
        RecyclerView recyclerView = (RecyclerView)
                findViewById(R.id.organization_organizationList_recyclerView);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)
                findViewById(R.id.organization_organizationList_swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3,
                R.color.refresh_progress_4,
                R.color.refresh_progress_5,
                R.color.refresh_progress_6
        );
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        organizationAdapter = new OrganizationAdapter(mPresenter, list, this);
        recyclerView.setAdapter(organizationAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshOrganizations(OrganizationActivity.this, swipeRefreshLayout);
            }
        });
    }

    @Override
    public void init() {
        CollapsingToolbarLayout mCollapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(getString(R.string.title_activity_organizationDetail));
        mCollapsingToolbarLayout.setExpandedTitleColor(
                getResources().getColor(android.R.color.transparent));
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRAN-Sans-Bold.ttf");
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        mCollapsingToolbarLayout.setExpandedTitleTypeface(tf);
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
                                    searchText,
                                    true
                            );

                            return true;
                        }
                        return false;
                    }
                }
        );

        ((EditText) findViewById(R.id.organization_search_editText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.searchOrganization(
                        OrganizationActivity.this,
                        s.toString(),
                        false
                );

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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


    @Override
    public void showWriteOptionDialog(OrganizationPresenter presenter, Organization org) {
        FragmentManager fragmentManager = getFragmentManager();
        SelectWriteModeDialog writeModeDialog = new SelectWriteModeDialog(presenter, org);
        writeModeDialog.setCancelable(false);
        writeModeDialog.show(fragmentManager, "Dialog_WriteOption");
    }

    @Override
    public void openWriteActivity(SelectWriteModeDialog dialog, int type, Organization org) {
        if (type == WriteOptionEnum.EMAIL.getIntValue()) {
            mPresenter.emailSelected(dialog, org, this);
        } else if (type == WriteOptionEnum.CALL.getIntValue()) {
            String phone = org.getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission("android.permission.CALL_PHONE") ==
                            PackageManager.PERMISSION_GRANTED) {
                        startActivity(intent);
                        mPresenter.onNumberDialed(this, org);
                    } else {
                        requestPermissions(new String[]{"android.permission.CALL_PHONE"},
                                PERMISION_REQUEST_PHONECALL);
                    }
                } else {
                    startActivity(intent);
                    mPresenter.onNumberDialed(this, org);
                }
            } catch (ActivityNotFoundException ex) {
                Helper.showToast(this, R.string.error_writeEmail_noCallApplication);
            }
        } else if (type == WriteOptionEnum.SMS.getIntValue()) {
            mPresenter.messageSelected(dialog, org);
        } else if (type == WriteOptionEnum.WEBSITE.getIntValue()) {
            if (SharedData.getInstance().getBoolean("defaultBrowser", true)) {
                String url = org.getWebsite();
                final String EXTRA_URL = "customurl";
                Intent intent = new Intent(this, InlineBrowserActivity.class);
                intent.putExtra(EXTRA_URL, url);
                startActivity(intent);
            } else {
                String url = org.getWebsite();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(this, "Activity Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void cancelDialog(SelectWriteModeDialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void openEmailActivity(SelectWriteModeDialog dialog, Organization org) {
        Helper.startActivityWithExtraString(
                this,
                WriteSmsEmailActivity.class,
                org.getId().toString(),
                "ORGANIZATION_ID",
                WriteOptionEnum.EMAIL.getStringValue(),
                "TYPE");
        dialog.dismiss();
    }

    @Override
    public void openSmsActivity(SelectWriteModeDialog dialog, Organization org) {
        Helper.startActivityWithExtraString(
                this,
                WriteSmsEmailActivity.class,
                org.getId().toString(),
                "ORGANIZATION_ID",
                WriteOptionEnum.SMS.getStringValue(),
                "TYPE");
        dialog.dismiss();
    }

    @Override
    public void showServerProblemMessage() {
        Helper.showToast(this, R.string.error_server);
    }

    @Override
    public void updateAdapter(boolean b,
                              List<Organization> organizations,
                              SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setRefreshing(false);
        if (!b) return;
        organizationAdapter.clear();
        organizationAdapter.addAll(organizations);
    }

    @Override
    public void showMapActivity(Organization organization) {
        String url = "http://maps.google.com/maps?daddr=" +
                organization.getLat() + "," + organization.getLan();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void showNetworkProblemMessage() {
        Helper.showToast(this, R.string.error_connection);
    }

    @Override
    public void showOrganizationFavorite(int adapterPosition) {
        organizationAdapter.notifyItemChanged(adapterPosition);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISION_REQUEST_PHONECALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // ...
            }
        }
    }
}

