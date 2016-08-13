package shirin.tahmasebi.mscfinalproject.organization;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
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
                )
        );

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(
                        this,
                        android.R.color.white
                )
        );

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

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ((FontableTextView) findViewById(R.id.description)).setText(
                    Html.fromHtml(org.getDescription(), Html.FROM_HTML_MODE_LEGACY)
            );
        } else {
            ((FontableTextView) findViewById(R.id.description)).setText(
                    Html.fromHtml(org.getDescription())
            );
        }
        final Animation rotation = AnimationUtils.loadAnimation(this, R.anim.loading_animation);
        rotation.setRepeatCount(Animation.INFINITE);
        findViewById(R.id.image).startAnimation(rotation);

        Picasso.with(this)
                .load(org.getImage())
                .placeholder(R.anim.loading_animation)
                .noFade()
                .into(((ImageView) findViewById(R.id.image)), new Callback() {
                    @Override
                    public void onSuccess() {
                        rotation.cancel();
                    }

                    @Override
                    public void onError() {
                        rotation.cancel();
                        ((ImageView) findViewById(R.id.image)).setImageResource(R.drawable.error);
                    }
                });
        initializeCollapsingLayoutToolbar(org.getName());
    }
}
