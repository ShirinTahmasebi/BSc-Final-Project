package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;
import shirin.tahmasebi.mscfinalproject.util.Helper;

class OrganizationPresenter implements OrganizationInteractor.OrganizationListener,
        SelectWriteModeDialog.SelectWriteModeDialogListener {
    private OrganizationView mView;
    private OrganizationInteractor mInteractor;
    private static final int RETRIEVE_TO_OPEN_WRITE = 1;

    OrganizationPresenter(OrganizationView view) {
        mView = view;
        mInteractor = new OrganizationInteractor(this);
    }

    void getOrganizationsList(Context context) {
        mInteractor.retrieveOrganizationsList(context);
    }

    @Override
    public void onOrganizationListRetrieved(List<Organization> list) {
        mView.showOrganizationsList(list);
    }

    @Override
    public void onRetrieveOrganizationFinished(Organization org, int retrieveReason) {
        if (retrieveReason == RETRIEVE_TO_OPEN_WRITE) {
            // اینجا باید دیالوگ مربوط به نوشتن پیام این سازمان خاص را نمایش داد
            mView.showWriteOptionDialog(this, org);
        }
    }

    @Override
    public void onToggleFavoriteOrganizationFinished(Organization org, int adapterPosition) {
        mView.showOrganizationFavorite(org, adapterPosition);
    }

    public void onStart() {
        mView.init();
    }

    void getOrganizationsListByFavoriteProperty(Context context, Boolean isFav) {
        mInteractor.retrieveOrganizationsListByFavoriteProperty(context, isFav);
    }

    void searchOrganization(Context context, String searchText, boolean closeKeyboard) {
        mInteractor.searchOrganizationByName(context, searchText);
        if (closeKeyboard) {
            mView.closeKeyboard();
            Helper.logEvent(context, "search", searchText);
        }
    }

    void showWriteOptions(Context context, Long organizationId) {
        mInteractor.retrieveOrganization(organizationId, context, RETRIEVE_TO_OPEN_WRITE);
    }

    void toggleFavorite(Context context, Long organizationId, int adapterPosition) {
        mInteractor.toggleFavoriteOrganization(organizationId, context, adapterPosition);
    }


    void onNumberDialed(OrganizationActivity context, Organization org) {
        mInteractor.saveDialedNumber(context, org);
    }

    void emailSelected(SelectWriteModeDialog dialog, Organization org, Context context) {
        if (!Helper.isNetworkAvailable(context)) {
            mView.showNetworkProblemMessage();
        } else {
            mView.openEmailActivity(dialog, org);
        }
    }


    @Override
    public void onWriteOptionClicked(SelectWriteModeDialog dialog, int type, Organization org) {
        mView.openWriteActivity(dialog, type, org);
    }

    @Override
    public void onCancelDialogClicked(SelectWriteModeDialog dialog) {
        mView.cancelDialog(dialog);
    }

    void showMapActivity(Organization organization) {
        mView.showMapActivity(organization);
    }

    void messageSelected(SelectWriteModeDialog dialog, Organization org) {
        mView.openSmsActivity(dialog, org);
    }

    interface OrganizationView {
        void showOrganizationsList(List<Organization> list);

        void init();

        void closeKeyboard();

        void showWriteOptionDialog(OrganizationPresenter presenter, Organization org);

        void openWriteActivity(SelectWriteModeDialog dialog, int type, Organization organization);

        void cancelDialog(SelectWriteModeDialog dialog);

        void openEmailActivity(SelectWriteModeDialog dialog, Organization org);

        void showNetworkProblemMessage();

        void showOrganizationFavorite(Organization org, int adapterPosition);

        void showMapActivity(Organization organization);

        void openSmsActivity(SelectWriteModeDialog dialog, Organization org);
    }
}
