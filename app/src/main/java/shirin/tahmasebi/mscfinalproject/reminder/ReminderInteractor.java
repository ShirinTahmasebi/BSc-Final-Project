package shirin.tahmasebi.mscfinalproject.reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.BaseApplication;
import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;

public class ReminderInteractor {

    ReminderListener mListener;

    public ReminderInteractor(ReminderListener listener) {
        mListener = listener;
    }

    public void createNotification(Context context) {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.reminder_notification);

        // Set Notification Title
        String strtitle = "نوتیفیکیشن جدید";
        // Set Notification Text
        String strtext = "متن نوتیفیکیشن جدید";

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, ReminderActivity.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                // Set Icon
                .setSmallIcon(R.drawable.logo)
                // Set Ticker Message
                .setTicker("نوتیفیکیشن اومد")
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title, strtitle);
        remoteViews.setTextViewText(R.id.text, strtext);

        // Create Notification Manager
        NotificationManager notificationmanager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());
    }

    public void retrieveReminderList(Context context) {
        List<Reminder> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getReminderDao().loadAll();
        mListener.onReminderListRetrieved(list);

    }

    public void removeReminderItemById(Context context, Long id) {
        ((BaseApplication) context.getApplicationContext())
                .daoSession.getReminderDao().deleteByKey(id);
        mListener.onReminderRemoved(context);

    }

    public interface ReminderListener {

        void onReminderListRetrieved(List<Reminder> list);

        void onReminderRemoved(Context context);
    }
}
