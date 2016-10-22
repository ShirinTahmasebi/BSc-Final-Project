package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.Helper;

public class OrganizationDetailPresenter
        implements OrganizationDetailInteractor.OrganizationDetailListener,
        SelectWriteModeDialog.SelectWriteModeDialogListener {

    private final static String EXTRA_ORGANIZATION_ID = "organizationid";
    private OrganizationDetailView mView;
    OrganizationDetailInteractor mInteractor;

    public OrganizationDetailPresenter(OrganizationDetailView view) {
        mView = view;
        mInteractor = new OrganizationDetailInteractor(this);

    }

    public void showOrganizationDetail(android.content.Context context) {
        Intent intent = ((OrganizationDetailActivity) context).getIntent();
        long id = intent.getLongExtra(EXTRA_ORGANIZATION_ID, -1);
        if (id != -1) {
            mInteractor.retrieveOrganization(id, context);
        } else {
            Log.w("mscFinalProject", "کلید سطر مربوط به این سازمان یافت نشد.");
        }
    }

    @Override
    public void onRetrieveOrganizationFinished(Organization org) {
        mView.showOrganizationDetail(org);
    }

    @Override
    public void onToggleFavoriteOrganizationFinished(Organization org) {
        mView.showOrganizationFavorite(org);
    }

    public void toggleFavorite(android.content.Context context) {
        Intent intent = ((OrganizationDetailActivity) context).getIntent();
        long id = intent.getLongExtra(EXTRA_ORGANIZATION_ID, -1);
        if (id != -1) {
            mInteractor.toggleFavoriteOrganization(id, context);
        } else {
            Log.w("mscFinalProject", "کلید سطر مربوط به این سازمان یافت نشد.");
        }
    }

    public void showWriteOptions(Organization org) {
        mView.showWriteOptionDialog(this, org);
    }

    @Override
    public void onWriteOptionClicked(SelectWriteModeDialog dialog, int type, Organization org) {
        mView.openWriteActivity(dialog, type, org);
    }

    @Override
    public void onCancelDialogClicked(SelectWriteModeDialog dialog) {
        mView.cancelDialog(dialog);
    }

    public void onNumberDialed(OrganizationDetailActivity context, Organization org) {
        mInteractor.saveDialedNumber(context, org);
    }

    public void emailSelected(SelectWriteModeDialog dialog, Organization org, Context context) {
        if (!Helper.isNetworkAvailable(context)) {
            mView.showNetworkProblemMessage();
        } else {
            mView.openEmailActivity(dialog, org);
        }
    }

    public interface OrganizationDetailView {
        void showOrganizationDetail(Organization org);

        void showOrganizationFavorite(Organization org);

        void showWriteOptionDialog(OrganizationDetailPresenter presenter, Organization org);

        void openWriteActivity(SelectWriteModeDialog dialog, int type, Organization organization);

        void cancelDialog(SelectWriteModeDialog dialog);

        void openEmailActivity(SelectWriteModeDialog dialog, Organization org);

        void showNetworkProblemMessage();
    }
}
