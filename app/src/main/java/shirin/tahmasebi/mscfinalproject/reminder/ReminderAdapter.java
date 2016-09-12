package shirin.tahmasebi.mscfinalproject.reminder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class ReminderAdapter extends RecyclerView.Adapter
        <ReminderAdapter.ReminderViewHolder> {
    List<Reminder> list = new ArrayList<>();

    public ReminderAdapter(List<Reminder> reminders) {
        list = reminders;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReminderViewHolder holder, final int position) {
        holder.reminderOrganizationNameTextView.setText(
                list.get(holder.getAdapterPosition()).getOrganizationName());
        holder.reminderTimeTextView.setText(Helper.convertToPersianDigits(
                list.get(holder.getAdapterPosition()).getTime()));
        holder.reminderDateTextView.setText(Helper.convertToPersianDigits(
                list.get(holder.getAdapterPosition()).getDate()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        FontableTextView reminderOrganizationNameTextView;
        FontableTextView reminderDateTextView;
        FontableTextView reminderTimeTextView;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            reminderOrganizationNameTextView = (FontableTextView) itemView.findViewById(
                    R.id.reminder_organizationName_textView);
            reminderDateTextView = (FontableTextView) itemView.findViewById(
                    R.id.reminder_date_textView);
            reminderTimeTextView = (FontableTextView) itemView.findViewById(
                    R.id.reminder_time_textView);
        }
    }
}