package shirin.tahmasebi.mscfinalproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import shirin.tahmasebi.mscfinalproject.dashboard.DashboardActivity;
import shirin.tahmasebi.mscfinalproject.phonetutorial.activities.TutorialActivity;

public class DemoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] titles = getResources().getStringArray(R.array.titles);
        int[] images = {
                R.drawable.tuto1,
                R.drawable.tuto2,
                R.drawable.tuto3,
                R.drawable.tuto4,
                R.drawable.tuto5,
                R.drawable.tuto6,
        };

        int colorIndicator = Color.parseColor("#FFFFFF");
        int colorBackground = Color.parseColor("#1E88E5");
        int colorButton = Color.parseColor("#FFFFFF");
        int colorIcon = Color.parseColor("#1E88E5");
        Intent tutorialIntent = new Intent(this, TutorialActivity.class);
        tutorialIntent.putExtra(
                TutorialActivity.COLORBACKGROUND,
                colorBackground);
        tutorialIntent.putExtra(
                TutorialActivity.COLORBUTTON,
                colorButton);
        tutorialIntent.putExtra(
                TutorialActivity.COLORINDICATOR,
                colorIndicator);
        tutorialIntent.putExtra(
                TutorialActivity.COLORICON,
                colorIcon);
        tutorialIntent.putExtra(
                TutorialActivity.IMAGES,
                images);
        tutorialIntent.putExtra(
                TutorialActivity.TITLES,
                titles);

        startActivityForResult(tutorialIntent, TUTORIALACTIVITY);

    }

    final int TUTORIALACTIVITY = 0;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == TUTORIALACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}
