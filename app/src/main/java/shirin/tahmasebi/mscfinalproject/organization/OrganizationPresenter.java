package shirin.tahmasebi.mscfinalproject.organization;

public class OrganizationPresenter implements OrganizationInteractor.OrganizationListener {
    private OrganizationView mView;
    OrganizationInteractor mInteractor;

    public OrganizationPresenter(OrganizationView view) {
        mView = view;
        mInteractor = new OrganizationInteractor(this);
    }

    public void openOrganizationDetails(int organizationId) {
        mView.showOrganizationDetails(organizationId);

    }

    public interface OrganizationView {
        void showOrganizationDetails(int organizationId);
    }
}
