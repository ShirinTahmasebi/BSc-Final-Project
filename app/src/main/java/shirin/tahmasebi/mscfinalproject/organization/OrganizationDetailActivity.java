package shirin.tahmasebi.mscfinalproject.organization;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class OrganizationDetailActivity extends MainActivity
        implements OrganizationDetailPresenter.OrganizationDetailView {

    private OrganizationDetailPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new OrganizationDetailPresenter(this);
        mPresenter.showOrganizationDetail(this);
    }

    private void initializeCollapsingLayoutToolbar(String name) {
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(name);
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                ));
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/IRAN-Sans-Bold.ttf");
        collapsingToolbarLayout.setCollapsedTitleTypeface(tf);
        collapsingToolbarLayout.setExpandedTitleTypeface(tf);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_organizationdetail_layout;
    }

    @Override
    protected int getActivityTitleResourceId() {
        return R.string.title_activity_organizationDetail;
    }

    @Override
    public void showOrganizationDetail(Organization org) {

        if (org == null) {
            return;
        }

        ((FontableTextView) findViewById(R.id.description)).setText(org.getDescription());

        Picasso.with(this)
                .load(org.getImage())
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .noFade()
                .into(((ImageView) findViewById(R.id.image)));
        initializeCollapsingLayoutToolbar(org.getName());
    }
}
