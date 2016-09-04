package shirin.tahmasebi.mscfinalproject.util;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

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

    @NonNull
    public static String convertToPersianDigits(String value) {
        return value.replace('1', '١').replace('2', '٢').replace('3', '۳')
                .replace('4', '٤').replace('5', '٥').replace('6', '٦')
                .replace('7', '٧').replace('8', '٨').replace('9', '٩')
                .replace('0', '٠');
    }

    @NonNull
    public static String convertToEnglishDigits(String value) {
        return value.replace('١', '1').replace('٢', '2').replace('۳', '3')
                .replace('٤', '4').replace('٥', '5').replace('٦', '6')
                .replace('٧', '7').replace('٨', '8').replace('٩', '9')
                .replace('٠', '0').replace('۱', '1').replace('۲', '2').replace('۳', '3')
                .replace('۴', '4').replace('۵', '5').replace('۶', '6')
                .replace('۷', '7').replace('۸', '8').replace('۹', '9')
                .replace('۰', '0');
    }

    public static void makeTextDialog(Context context, String text) {
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        TextDialog dialog = new TextDialog(text);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "Dialog_Text");
    }


    public static void makeConfirmDialog(Context context, String text,
                                         Class destinationActivity) {
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        WarningDialog dialog = new WarningDialog(text, (Activity) context, destinationActivity);
        dialog.setCancelable(false);
        dialog.show(fragmentManager, "Dialog_Text");
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }

    public static void showToast(Context context, int stringId) {
        String toastText = context.getString(stringId);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(
                R.layout.toast,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_root_lineatLayout)
        );

        FontableTextView txt = (FontableTextView) layout.findViewById(R.id.toast_text_textView);
        txt.setText(toastText);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.setView(layout);
        toast.show();
    }
}
