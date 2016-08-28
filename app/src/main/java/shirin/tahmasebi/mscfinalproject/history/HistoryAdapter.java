package shirin.tahmasebi.mscfinalproject.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<History> mList = new ArrayList<>();
    private HistoryPresenter mPresenter;

    public HistoryAdapter(HistoryPresenter presenter, List<History> list) {
        mPresenter = presenter;
        mList = list;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getType();
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == WriteOptionEnum.CALL.getIntValue()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_call, parent, false);
        } else if (viewType == WriteOptionEnum.EMAIL.getIntValue()) {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_mail, parent, false);
        }
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        holder.historyOrganizationName.setText(
                Helper.convertToPersianDigits(
                        mList.get(holder.getAdapterPosition()).getOrganizationName()
                ));
        holder.historyWriteDate.setText(
                Helper.convertToPersianDigits(
                        mList.get(holder.getAdapterPosition()).getDate()
                ));
        // TODO: Inja onclick ro ezafe kon
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        FontableTextView historyOrganizationName;
        FontableTextView historyWriteDate;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            historyOrganizationName = (FontableTextView) itemView.findViewById(
                    R.id.history_organizationName_textView);
            historyWriteDate = (FontableTextView) itemView.findViewById(
                    R.id.history_writeDate_textView);
        }
    }
}