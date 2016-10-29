package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;

class OrganizationAdapter extends RecyclerView.Adapter
        <OrganizationAdapter.OrganizationViewHolder> {
    private List<Organization> list = new ArrayList<>();
    private OrganizationPresenter mPresenter;
    private Context context;

    OrganizationAdapter(OrganizationPresenter presenter, List<Organization> organizations,
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
        if (list.get(holder.getAdapterPosition()).getIsFavorite()) {
            holder.organizationFavoriteImageView.setImageDrawable(
                    context.getResources().getDrawable(R.drawable.favorite_enable_icon_primarydark)
            );
        } else {
            holder.organizationFavoriteImageView.setImageDrawable(
                    context.getResources().getDrawable(R.drawable.favorite_disable_icon_primarydark)
            );
        }
        holder.organizationTextView.setText(list.get(holder.getAdapterPosition()).getName());
        Picasso.with(context)
                .load(list.get(holder.getAdapterPosition()).getImage())
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
        holder.organizationWriteRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showWriteOptions(context, list.get(holder.getAdapterPosition()).getId());
            }
        });
        holder.organizationFavoriteRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.toggleFavorite(context,
                        list.get(holder.getAdapterPosition()).getId(),
                        holder.getAdapterPosition());
            }
        });
        holder.organizationGPSRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.showMapActivity(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class OrganizationViewHolder extends RecyclerView.ViewHolder {
        FontableTextView organizationTextView;
        ImageView organizationImageView;
        RelativeLayout organizationWriteRelativeLayout;
        RelativeLayout organizationFavoriteRelativeLayout;
        RelativeLayout organizationGPSRelativeLayout;
        ImageView organizationFavoriteImageView;

        OrganizationViewHolder(View itemView) {
            super(itemView);
            organizationTextView = (FontableTextView) itemView.findViewById(
                    R.id.organization_item_textview);
            organizationImageView = (ImageView) itemView.findViewById(
                    R.id.organization_item_imageView);
            organizationWriteRelativeLayout = (RelativeLayout) itemView.findViewById(
                    R.id.organizationItem_write_relativeLayout
            );
            organizationFavoriteRelativeLayout = (RelativeLayout) itemView.findViewById(
                    R.id.organizationItem_favorite_relativeLayout
            );
            organizationGPSRelativeLayout = (RelativeLayout) itemView.findViewById(
                    R.id.organizationItem_gps_relativeLayout
            );
            organizationFavoriteImageView = (ImageView) itemView.findViewById(
                    R.id.organizationItem_favorite_imageView
            );
        }
    }
}