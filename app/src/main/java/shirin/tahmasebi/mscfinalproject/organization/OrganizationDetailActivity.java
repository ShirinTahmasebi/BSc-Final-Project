package shirin.tahmasebi.mscfinalproject.organization;

import android.app.FragmentManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
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
import shirin.tahmasebi.mscfinalproject.inlineBrowser.InlineBrowserActivity;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class OrganizationDetailActivity extends MainActivity
        implements OrganizationDetailPresenter.OrganizationDetailView {

    private static final int PERMISION_REQUEST_PHONECALL = 1234;
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
                Toast.makeText(this, "Activity Not Found", Toast.LENGTH_SHORT).show();
            }
        } else if (type == WriteOptionEnum.WEBSITE.getIntValue()) {
            String url = org.getSiteUrl();
            final String EXTRA_URL = "customurl";
            Intent intent = new Intent(this, InlineBrowserActivity.class);
            intent.putExtra(EXTRA_URL, url);
            startActivity(intent);
        }
    }

    @Override
    public void cancelDialog(SelectWriteModeDialog dialog) {
        dialog.dismiss();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISION_REQUEST_PHONECALL) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
}
