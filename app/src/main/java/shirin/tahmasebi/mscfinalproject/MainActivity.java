package shirin.tahmasebi.mscfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.ButterKnife;

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
        } else {
            // TODO:  این حالت معمولا زمانی پیش میاد که صفحه‌ای که باز شده اصلا تولبار دیفالت اکتیویتی رو نداره
        }
    }

    private String getActivityTitle() {
        if (getActivityTitleResourceId() == 0) {
            throw new IllegalStateException("Override getActivityTitleResourceId");
        } else {
            return getString(getActivityTitleResourceId());
        }
    }

    protected abstract int getActivityTitleResourceId();
}
