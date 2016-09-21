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
import shirin.tahmasebi.mscfinalproject.io.models.ReminderDao;
import shirin.tahmasebi.mscfinalproject.organization.OrganizationActivity;

public class ReminderInteractor {

    ReminderListener mListener;

    public ReminderInteractor(ReminderListener listener) {
        mListener = listener;
    }

    public void createNotification(Context context, long reminder1) {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.reminder_notification);

        Reminder reminder =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getReminderDao().queryBuilder()
                        .where(ReminderDao.Properties.Id.eq(reminder1)).unique();
        // Set Notification Title
        String title = null;
        if (reminder != null) {
            title = reminder.getOrganizationName();
        }
        // Set Notification Text
        String text = context.getResources().getString(R.string.lable_reminder_notifCustomText);
        if (reminder != null) {
            if (!("".equals(reminder.getText()))) {
                text = reminder.getText();
            }
        }

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(context, OrganizationActivity.class);
        // Send data to NotificationView Class
        intent.putExtra("title", title);
        intent.putExtra("text", text);
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

        // Locate and set the Text into reminder_notification.xml TextViews
        remoteViews.setTextViewText(R.id.customNotification_title_textView, title);
        remoteViews.setTextViewText(R.id.customNotification_text_textView, text);

        // Create Notification Manager
        NotificationManager notificationmanager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        if (reminder != null) {
            notificationmanager.notify(reminder.getId().intValue(), builder.build());
        } else {
            notificationmanager.notify(0, builder.build());

        }
    }

    public void retrieveReminderList(Context context) {
        List<Reminder> list =
                ((BaseApplication) context.getApplicationContext())
                        .daoSession.getReminderDao().loadAll();
        mListener.onReminderListRetrieved(list);

    }

    public void removeReminderItemById(Context context, Long id) {
        Reminder reminder = ((BaseApplication) context.getApplicationContext())
                .daoSession.getReminderDao().load(id);
        ((BaseApplication) context.getApplicationContext())
                .daoSession.getReminderDao().deleteByKey(id);
        mListener.onReminderRemoved(context, reminder);

    }

    public boolean isReminderListEmpty(Context context) {
        long count = ((BaseApplication) context.getApplicationContext())
                .daoSession.getReminderDao().count();

        return count == 0;
    }

    public interface ReminderListener {

        void onReminderListRetrieved(List<Reminder> list);

        void onReminderRemoved(Context context, Reminder reminder);
    }
}
