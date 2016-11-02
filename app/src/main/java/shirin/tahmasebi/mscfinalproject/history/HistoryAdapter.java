package shirin.tahmasebi.mscfinalproject.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.History;
import shirin.tahmasebi.mscfinalproject.util.Helper;
import shirin.tahmasebi.mscfinalproject.util.WriteOptionEnum;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<History> mList = new ArrayList<>();
    private HistoryPresenter mPresenter;

    HistoryAdapter(HistoryPresenter presenter, List<History> list) {
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
        } else if (viewType == WriteOptionEnum.SMS.getIntValue()) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_history_sms, parent, false);
        }
        return new HistoryViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final HistoryViewHolder holder, final int position) {
        holder.historyOrganizationName.setText(
                Helper.convertToPersianDigits(
                        mList.get(holder.getAdapterPosition()).getOrganizationName()
                ));
        if (holder.viewType == WriteOptionEnum.CALL.getIntValue()) {
            holder.historyWriteDate.setText(
                    Helper.convertToPersianDigits(
                            mList.get(holder.getAdapterPosition()).getDate()
                    ));
        } else if (holder.viewType == WriteOptionEnum.EMAIL.getIntValue()) {
            holder.historyEmailMoreDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.openEmailDetail(mList.get(holder.getAdapterPosition()).getDate(),
                            mList.get(holder.getAdapterPosition()).getEmailText());
                }
            });
        } else if (holder.viewType == WriteOptionEnum.SMS.getIntValue()) {
            holder.historyEmailMoreDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.openEmailDetail(mList.get(holder.getAdapterPosition()).getDate(),
                            mList.get(holder.getAdapterPosition()).getEmailText());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        FontableTextView historyOrganizationName;
        FontableTextView historyWriteDate;
        LinearLayout historyEmailMoreDetail;
        int viewType;

        HistoryViewHolder(View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;

            historyOrganizationName = (FontableTextView) itemView.findViewById(
                    R.id.history_organizationName_textView);

            if (viewType == WriteOptionEnum.CALL.getIntValue()) {
                historyWriteDate = (FontableTextView) itemView.findViewById(
                        R.id.history_writeDate_textView);
            } else if (viewType == WriteOptionEnum.EMAIL.getIntValue()
                    || viewType == WriteOptionEnum.SMS.getIntValue()) {
                historyEmailMoreDetail = (LinearLayout) itemView.findViewById(
                        R.id.history_moreDetails_linearLayout);
            }
        }
    }
}