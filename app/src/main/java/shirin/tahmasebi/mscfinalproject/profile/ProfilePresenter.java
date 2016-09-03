package shirin.tahmasebi.mscfinalproject.profile;

public class ProfilePresenter implements ProfileInteractor.ProfileListener {
    private ProfileView mView;
    private ProfileInteractor mInteractor;

    public ProfilePresenter(ProfileView view) {
        mView = view;
        mInteractor = new ProfileInteractor(this);
    }

    public interface ProfileView {

    }
}
