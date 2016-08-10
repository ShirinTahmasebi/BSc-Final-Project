package shirin.tahmasebi.mscfinalproject.organization;

public class OrganizationInteractor {
    private OrganizationListener mListener;

    public OrganizationInteractor(OrganizationListener listener) {
        mListener = listener;
    }

    public interface OrganizationListener {
    }
}

