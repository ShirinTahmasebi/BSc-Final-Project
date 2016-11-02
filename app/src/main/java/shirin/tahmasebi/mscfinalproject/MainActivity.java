package shirin.tahmasebi.mscfinalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.localizationactivity.LocalizationActivity;

import butterknife.ButterKnife;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.SharedData;

public abstract class MainActivity extends LocalizationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != -1) {
            setContentView(getLayoutId());
        }
        Helper.logEvent(this, "Activity Visited", MainActivity.class.getName());
        setLanguage(SharedData.getInstance().getString("locale", "fa"));
        setupToolbar();
        ButterKnife.bind(this);
        reportScreen(getScreenName());
    }

    private void reportScreen(String screenName) {
        ((BaseApplication) getApplicationContext()).trackScreenView(screenName);
    }

    protected abstract int getLayoutId();

    protected abstract String getScreenName();

    private void setupToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        TextView toolbarTitle;
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            toolbarTitle.setText(getActivityTitle());
            findViewById(R.id.toolbar_help_linearLayout).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Helper.makeTextDialog(MainActivity.this,
                                    getString(getActivityHelpHint()));
                        }
                    }
            );
        } else {
            // TODO:  این حالت معمولا زمانی پیش میاد که صفحه‌ای که باز شده اصلا تولبار دیفالت اکتیویتی رو نداره
        }
    }

    protected abstract int getActivityHelpHint();

    private String getActivityTitle() {
        if (getActivityTitleResourceId() == 0) {
            throw new IllegalStateException("Override getActivityTitleResourceId");
        } else {
            return getString(getActivityTitleResourceId());
        }
    }

    protected abstract int getActivityTitleResourceId();

}
