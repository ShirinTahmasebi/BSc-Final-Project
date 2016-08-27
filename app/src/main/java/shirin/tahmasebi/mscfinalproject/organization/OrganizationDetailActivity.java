package shirin.tahmasebi.mscfinalproject.organization;

import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import shirin.tahmasebi.mscfinalproject.MainActivity;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;
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

        findViewById(R.id.organizationDetail_writeMenu_fab)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.showWriteOptions(org);
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

    @Override
    public void showWriteOptionDialog(OrganizationDetailPresenter presenter, Organization org) {
        FragmentManager fragmentManager = getFragmentManager();
        SelectWriteModeDialog yesnoDialog = new SelectWriteModeDialog(presenter, org);
        yesnoDialog.setCancelable(false);
        yesnoDialog.show(fragmentManager, "Dialog_WriteOption");
    }

    @Override
    public void openWriteActivity(SelectWriteModeDialog dialog, int type, Organization org) {
        if (type == WriteOptionEnum.EMAIL.getIntValue()) {
            Helper.startActivityWithExtraString(
                    this,
                    WriteEmailActivity.class,
                    org.getId().toString(),
                    "ORGANIZATION_ID");
            dialog.dismiss();
        } else if (type == WriteOptionEnum.CALL.getIntValue()) {
            String phone = org.getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(this, "Activity Not Found", Toast.LENGTH_SHORT).show();
            }
        } else if (type == WriteOptionEnum.WEBSITE.getIntValue()) {
            String url = org.getSiteUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                Toast.makeText(this, "Activity Not Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void cancelDialog(SelectWriteModeDialog dialog) {
        dialog.dismiss();
    }
}
