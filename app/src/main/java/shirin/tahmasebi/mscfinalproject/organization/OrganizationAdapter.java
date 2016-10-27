package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

public class OrganizationAdapter extends RecyclerView.Adapter
        <OrganizationAdapter.OrganizationViewHolder> {
    List<Organization> list = new ArrayList<>();
    private OrganizationPresenter mPresenter;
    private Context context;

    public OrganizationAdapter(OrganizationPresenter presenter, List<Organization> organizations,
                               Context context) {
        mPresenter = presenter;
        list = organizations;
        this.context = context;
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_organization, parent, false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final OrganizationViewHolder holder, final int position) {
        holder.organizationTextView.setText(list.get(holder.getAdapterPosition()).getName());
        holder.organizationTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.openOrganizationDetails(list.get(holder.getAdapterPosition()).getId());
            }
        });
        Picasso.with(context)
                .load(list.get(holder.getAdapterPosition()).getImage())
                .placeholder(R.anim.loading_animation)
                .noFade()
                .into(holder.organizationImageView, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                (holder.organizationImageView)
                                        .setImageResource(R.drawable.error);
                            }
                        }
                );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class OrganizationViewHolder extends RecyclerView.ViewHolder {
        FontableTextView organizationTextView;
        ImageView organizationImageView;

        public OrganizationViewHolder(View itemView) {
            super(itemView);
            organizationTextView = (FontableTextView) itemView.findViewById(
                    R.id.organization_item_textview);
            organizationImageView = (ImageView) itemView.findViewById(
                    R.id.organization_item_imageView);
        }
    }
}