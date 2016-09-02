package shirin.tahmasebi.mscfinalproject.setting;

public class SettingPresenter implements SettingInteractor.SettingListener {

    private SettingView mView;
    private SettingInteractor mInteractor;

    public SettingPresenter(SettingView view) {
        mView = view;
        mInteractor = new SettingInteractor(this);
    }

    public interface SettingView {

    }
}
