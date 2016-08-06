package shirin.tahmasebi.mscfinalproject.dashboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

import shirin.tahmasebi.mscfinalproject.R;

public class DashboardActivity extends Activity implements DashboardPresenter.DashboardView {
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_layout);
    }
}
