package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class OrganizationAdapter extends RecyclerView.Adapter
        <OrganizationAdapter.OrganizationViewHolder> {
    List<String> list = new ArrayList<>();
    private Context mContext;
    private OrganizationPresenter mPresenter;

    public OrganizationAdapter(Context context, OrganizationPresenter presenter) {
        mContext = context;
        mPresenter = presenter;
        list.add("سازمان شماره یک");
        list.add("سازمان شماره دو");
        list.add("سازمان شماره سه");
        list.add("سازمان شماره چهارم");
        list.add("سازمان شماره پنجم ");
        list.add("سازمان شماره ششم");
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organization, parent, false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrganizationViewHolder holder, final int position) {
        holder.organizationTextView.setText(list.get(position));
        holder.organizationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        mContext,
                        "Item Clicked " + holder.getAdapterPosition(),
                        Toast.LENGTH_LONG
                ).show();
                mPresenter.openOrganizationDetails(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrganizationViewHolder extends RecyclerView.ViewHolder {
        FontableTextView organizationTextView;

        public OrganizationViewHolder(View itemView) {
            super(itemView);
            organizationTextView = (FontableTextView) itemView.findViewById(
                    R.id.organization_item_textview);
        }
    }
}