package shirin.tahmasebi.mscfinalproject.dashboard;

public class DashboardInteractor {
    private DashboardListener mListener;

    public DashboardInteractor(DashboardListener listener) {
        mListener = listener;
    }

    public interface DashboardListener {
    }
}
