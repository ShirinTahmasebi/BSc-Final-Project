package shirin.tahmasebi.mscfinalproject.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class Helper {
    public static void startActivity(Activity activity, Class aClass) {
        activity.startActivity(new Intent(activity, aClass));
    }

    public static void startActivityWithExtraString(Activity activity, Class aClass,
                                                    String data1, String dataName1,
                                                    String data2, String dataName2) {
        Intent intent = new Intent(activity, aClass);
        intent.putExtra(dataName1, data1);
        intent.putExtra(dataName2, data2);
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
        if (context == null) {
            return;
        }
        String toastText = context.getString(stringId);

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(
                R.layout.toast,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_root_linearLayout)
        );

        FontableTextView txt = (FontableTextView) layout.findViewById(R.id.toast_text_textView);
        txt.setText(toastText);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.setView(layout);
        toast.show();
    }

    public static void showToast(Context context, String toastText) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(
                R.layout.toast,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_root_linearLayout)
        );

        FontableTextView txt = (FontableTextView) layout.findViewById(R.id.toast_text_textView);
        txt.setText(toastText);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 80);
        toast.setView(layout);
        toast.show();
    }

    public static Date convertSolarToGregorianTimeDateFormat(
            int hour,
            int minuteOfHour,
            int year,
            int monthOfYear,
            int dayOfMonth,
            boolean isPm, boolean zeroBasedMonth) {
        if (!zeroBasedMonth) {
            monthOfYear--;
        }
        JalaliCalendar.YearMonthDate yearMonthDate = JalaliCalendar.jalaliToGregorian(
                new JalaliCalendar.YearMonthDate(year, monthOfYear, dayOfMonth));
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd hh:mm aa", Locale.US);
        Date date = null;
        try {
            String dateString =
                    yearMonthDate.getYear() + ":"
                            + ((yearMonthDate.getMonth() < 9) ? "0" : "")
                            + (yearMonthDate.getMonth() + 1) + ":"
                            + yearMonthDate.getDate() + " "
                            + (hour - (isPm ? 12 : 0)) + ":"
                            + minuteOfHour + " "
                            + (isPm ? "PM" : "AM");
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date currentGregorianTimeDateFormat() {
        Calendar currentDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd hh:mm aa", Locale.US);
        String strDate = sdf.format(currentDate.getTime());
        try {
            return sdf.parse(strDate);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void logEvent(Context context, String contentType, String name) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1234");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, contentType);
        ((BaseApplication) context.getApplicationContext()).firebaseAnalytics.
                logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public static void sendSms(String smsNumber, String text) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsNumber, null, text, null, null);
    }

//    public static Tracker getTracker() {
//        Tracker tracker;
//        if (false == isGooglePlayServicesAvailable()) {
//            return null;
//        }
//
//        if (tracker == null) {
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(BaseApplication.mInstance);
//            tracker = analytics.newTracker(R.xml.app_tracker);
//        }
//        return tracker;
//    }

}
