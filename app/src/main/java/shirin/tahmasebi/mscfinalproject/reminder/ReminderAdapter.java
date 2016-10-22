package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Reminder;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class ReminderAdapter extends RecyclerView.Adapter
        <ReminderAdapter.ReminderViewHolder> {
    private List<Reminder> list = new ArrayList<>();
    private ReminderPresenter mPresenter;
    private Context mContext;

    public ReminderAdapter(ReminderPresenter presenter, List<Reminder> reminders, Context context) {
        list = reminders;
        mPresenter = presenter;
        mContext = context;
    }

    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reminder, parent, false);
        return new ReminderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReminderViewHolder holder, final int position) {
        String time = mPresenter.timeToShow(list.get(holder.getAdapterPosition()).getDate());
        String date = mPresenter.dateToShow(list.get(holder.getAdapterPosition()).getDate());
        holder.reminderOrganizationNameTextView.setText(
                list.get(holder.getAdapterPosition()).getOrganizationName());
        holder.reminderTimeTextView.setText(Helper.convertToPersianDigits(time));
        holder.reminderDateTextView.setText(Helper.convertToPersianDigits(date));
        holder.reminderDeleteItemRelativeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mPresenter.removeReminderItem(
                                mContext,
                                list.get(position).getId());
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeRemoved(position, list.size());
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        FontableTextView reminderOrganizationNameTextView;
        FontableTextView reminderDateTextView;
        FontableTextView reminderTimeTextView;
        RelativeLayout reminderDeleteItemRelativeLayout;

        public ReminderViewHolder(View itemView) {
            super(itemView);
            reminderOrganizationNameTextView = (FontableTextView) itemView.findViewById(
                    R.id.reminder_organizationName_textView);
            reminderDateTextView = (FontableTextView) itemView.findViewById(
                    R.id.reminder_date_textView);
            reminderTimeTextView = (FontableTextView) itemView.findViewById(
                    R.id.reminder_time_textView);
            reminderDeleteItemRelativeLayout = (RelativeLayout) itemView.findViewById(
                    R.id.reminder_deleteItem_relativeLayout);
        }
    }
}