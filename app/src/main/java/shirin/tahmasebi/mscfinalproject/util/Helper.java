package shirin.tahmasebi.mscfinalproject.util;

import android.app.Activity;
import android.content.Intent;

public class Helper {
    public static void startActivity(Activity activity, Class aClass) {
        activity.startActivity(new Intent(activity, aClass));
    }

    public static void startActivityWithExtraString(Activity activity, Class aClass,
                                                    String data, String dataName) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtra(dataName, data);
        activity.startActivity(intent);
    }
}
