package shirin.tahmasebi.mscfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public abstract class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();

    private void setupToolbar() {
        TextView toolbarTitle;
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        if (toolbarTitle != null) {
            toolbarTitle.setText(getActivityTitle());
            findViewById(R.id.toolbar_help_linearLayout).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Helper.makeDialog(MainActivity.this,
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
