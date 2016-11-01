package shirin.tahmasebi.mscfinalproject.reminder;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
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

class SelectOrganizationInReminderAdapter extends RecyclerView.Adapter
        <SelectOrganizationInReminderAdapter.OrganizationViewHolder> {
    private List<Organization> list = new ArrayList<>();
    private ReminderAddViewPagerPresenter mPresenter;
    private Context context;
    private int globalPosition = -1;

    SelectOrganizationInReminderAdapter(ReminderAddViewPagerPresenter presenter, List<Organization> organizations,
                                        Context context) {
        mPresenter = presenter;
        list = organizations;
        this.context = context;
    }

    @Override
    public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reminder_organization, parent, false);
        return new OrganizationViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final OrganizationViewHolder holder, final int position) {
        if (globalPosition == position) {
            holder.organizationCardView.setBackgroundColor(
                    context.getResources().getColor(R.color.primaryDark));
        } else {
            holder.organizationCardView.setBackgroundColor(
                    context.getResources().getColor(R.color.white));
        }
        holder.organizationTextView.setText(list.get(holder.getAdapterPosition()).getName());

        Uri uri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                        context.getPackageName() + "/mipmap/org" + (position + 1));

        Picasso.with(context)
                .load(uri)
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

    class OrganizationViewHolder extends RecyclerView.ViewHolder {
        FontableTextView organizationTextView;
        ImageView organizationImageView;
        CardView organizationCardView;

        OrganizationViewHolder(View itemView) {
            super(itemView);
            organizationTextView = (FontableTextView) itemView.findViewById(
                    R.id.organization_item_textview);
            organizationImageView = (ImageView) itemView.findViewById(
                    R.id.organization_item_imageView);
            organizationCardView = (CardView) itemView.findViewById(R.id.orgaization_item_cardView);

            organizationCardView.setOnClickListener(cardSelected);
            organizationImageView.setOnClickListener(cardSelected);
            organizationTextView.setOnClickListener(cardSelected);
        }

        View.OnClickListener cardSelected = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int oldItem = globalPosition;
                globalPosition = getAdapterPosition();
                mPresenter.setOrganizationName(organizationTextView.getText());
                notifyItemChanged(oldItem);
                notifyItemChanged(globalPosition);
            }
        };

    }
}