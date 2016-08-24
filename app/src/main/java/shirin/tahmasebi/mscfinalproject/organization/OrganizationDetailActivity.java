package shirin.tahmasebi.mscfinalproject.organization;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
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
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    public void showOrganizationDetail(final Organization org) {

        if (org == null) {
            return;
        }

        // توضیحات مربوط به سازمان را اضافه کن
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            ((FontableTextView) findViewById(R.id.description)).setText(
                    Html.fromHtml(org.getDescription(), Html.FROM_HTML_MODE_LEGACY)
            );
        } else {
            ((FontableTextView) findViewById(R.id.description)).setText(
                    Html.fromHtml(org.getDescription())
            );
        }

        // تیتر مربوط به سازمان را به هدر اضافه کن
        initializeCollapsingLayoutToolbar(org.getName());

        // انیمیشن لودینگ و آیکون ارور را به imageview اضافه کن
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
                                ((ImageView) findViewById(R.id.image))
                                        .setImageResource(R.drawable.error);
                            }
                        }
                );

        //ستاره‌دار بودن یا نبودن سازمان را نمایش بده
        showOrganizationFavorite(org);

        //با کلیک بر روی ستاره هر سازمان فیلد مربوطه را toggle  کن
        findViewById(R.id.organizationDetail_favorite_fab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.toggleFavorite(OrganizationDetailActivity.this);
                    }
                });
    }

    @Override
    public void showOrganizationFavorite(Organization org) {

        if (org.getIsFavorite()) {
            ((FloatingActionButton) findViewById(R.id.organizationDetail_favorite_fab))
                    .setImageDrawable(getResources().getDrawable(R.drawable.favorite_enable_icon));
        } else {
            ((FloatingActionButton) findViewById(R.id.organizationDetail_favorite_fab))
                    .setImageDrawable(getResources().getDrawable(R.drawable.favorite_disable_icon));
        }
    }
}
