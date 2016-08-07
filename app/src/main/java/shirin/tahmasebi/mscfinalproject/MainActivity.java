package shirin.tahmasebi.mscfinalproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public abstract class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setupToolbar();
    }

    protected abstract int getLayoutId();

    private void setupToolbar() {
        TextView toolbarTitle;
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getActivityTitle());
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
