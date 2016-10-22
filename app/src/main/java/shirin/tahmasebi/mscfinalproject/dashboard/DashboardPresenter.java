package shirin.tahmasebi.mscfinalproject.dashboard;

public class DashboardPresenter implements DashboardInteractor.DashboardListener {
    private DashboardView mView;
    DashboardInteractor mInteractor;

    public DashboardPresenter(DashboardView view) {
        mView = view;
        mInteractor = new DashboardInteractor(this);
    }

    public void onStart(){
        mView.init();
    }

    public interface DashboardView {
        void init();
    }
}
