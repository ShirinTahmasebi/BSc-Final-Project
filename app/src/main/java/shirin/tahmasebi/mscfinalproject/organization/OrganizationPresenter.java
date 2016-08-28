package shirin.tahmasebi.mscfinalproject.organization;

import android.content.Context;

import java.util.List;

import shirin.tahmasebi.mscfinalproject.io.models.Organization;

public class OrganizationPresenter implements OrganizationInteractor.OrganizationListener {
    private OrganizationView mView;
    OrganizationInteractor mInteractor;

    public OrganizationPresenter(OrganizationView view) {
        mView = view;
        mInteractor = new OrganizationInteractor(this);
    }

    public void openOrganizationDetails(long organizationId) {
        mView.showOrganizationDetails(organizationId);
    }

    public void getOrganizationsList(Context context) {
        mInteractor.retrieveOrganizationsList(context);
    }

    @Override
    public void onOrganizationListRetrieved(List<Organization> list) {
        mView.showOrganizationsList(list);
    }

    public void onStart() {
        mView.init();
    }

    public void getOrganizationsListByFavoriteProperty(Context context, Boolean isFav) {
        mInteractor.retrieveOrganizationsListByFavoriteProperty(context, isFav);
    }

    public interface OrganizationView {
        void showOrganizationDetails(long organizationId);

        void showOrganizationsList(List<Organization> list);

        void init();
    }
}
