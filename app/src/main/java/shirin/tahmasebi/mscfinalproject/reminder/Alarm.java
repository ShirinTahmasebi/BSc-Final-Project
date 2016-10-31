package shirin.tahmasebi.mscfinalproject.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;

import shirin.tahmasebi.mscfinalproject.io.models.Reminder;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();

        ReminderActivity activity = new ReminderActivity();
        ReminderPresenter presenter = new ReminderPresenter(activity);
        long reminder = intent.getExtras().getLong("reminder");
        presenter.showNotification(context, reminder);

        wl.release();
    }

    public static void setAlarm(Context context, Reminder reminder) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        SerializableReminder serializableReminder =
                new SerializableReminder(reminder.getId(),
                        reminder.getDate(),
                        reminder.getOrganizationName(),
                        reminder.getText());
        i.putExtra("reminder", reminder.getId());
        long triggerTime = reminder.getDate().getTime() - System.currentTimeMillis();
        PendingIntent pi = PendingIntent.getBroadcast(context, reminder.getId().intValue(), i, 0);
        am.set(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + triggerTime,
                pi);
    }

    public static void cancelAlarm(Context context, int alarmId) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

}
