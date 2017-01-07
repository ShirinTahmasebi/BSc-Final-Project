package shirin.tahmasebi.mscfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import shirin.tahmasebi.mscfinalproject.dashboard.DashboardActivity;
import shirin.tahmasebi.mscfinalproject.util.SharedData;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent;
        if (SharedData.getInstance().getBoolean("seenIntroduction", false)) {
            intent = new Intent(this, DashboardActivity.class);
        } else {
            SharedData.getInstance().put("seenIntroduction", true);
            intent = new Intent(this, DemoActivity.class);
        }
        startActivity(intent);
        finish();
    }
}