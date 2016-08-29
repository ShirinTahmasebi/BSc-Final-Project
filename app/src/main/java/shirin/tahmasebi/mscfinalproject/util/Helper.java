package shirin.tahmasebi.mscfinalproject.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
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

    public static String convertToPersianDigits(String value) {
        return value.replace('1', '١').replace('2', '٢').replace('3', '۳')
                .replace('4', '٤').replace('5', '٥').replace('6', '٦')
                .replace('7', '٧').replace('8', '٨').replace('9', '٩')
                .replace('0', '٠');
    }

    public static String convertToEnglishDigits(String value) {
        return value.replace('١', '1').replace('٢', '2').replace('۳', '3')
                .replace('٤', '4').replace('٥', '5').replace('٦', '6')
                .replace('٧', '7').replace('٨', '8').replace('٩', '9')
                .replace('٠', '0').replace('۱', '1').replace('۲', '2').replace('۳', '3')
                .replace('۴', '4').replace('۵', '5').replace('۶', '6')
                .replace('۷', '7').replace('۸', '8').replace('۹', '9')
                .replace('۰', '0');
    }

    public static void makeDialog(Context context, String text) {
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        TextDialog dialog = new TextDialog(text);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "Dialog_Text");
    }
}
