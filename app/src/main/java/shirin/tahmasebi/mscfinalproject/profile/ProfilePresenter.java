package shirin.tahmasebi.mscfinalproject.profile;

import android.app.Activity;
import android.content.Intent;

import shirin.tahmasebi.mscfinalproject.util.AccountTypeEnum;
import shirin.tahmasebi.mscfinalproject.util.AuthPreferences;

@SuppressWarnings("FieldCanBeLocal")
public class ProfilePresenter implements ProfileInteractor.ProfileListener {
    private ProfileView mView;
    private ProfileInteractor mInteractor;
    private AuthPreferences mAuthPreferences;

    public ProfilePresenter(Activity context, ProfileView view) {
        mView = view;
        mInteractor = new ProfileInteractor(this);
        mAuthPreferences = new AuthPreferences(context);
    }

    public void onStart() {
        int pos;
        String str = mAuthPreferences.getKeyAccountType();
        if (str == null) {
            pos = 0;
        } else {
            pos = AccountTypeEnum.valueOf(mAuthPreferences.getKeyAccountType()).getIntValue();
        }
        mView.init(pos);
    }

    public void accountTypeSelected(Activity context, int position) {
        mInteractor.saveAccountTypeSelected(context, position, mAuthPreferences);
    }

    public void googleAccountSelected(Intent data) {
        mInteractor.saveUser(data, mAuthPreferences);
    }

    @Override
    public void onGoogleAccountSelected() {
        mView.showGoogleAccountChooser();
    }


    public interface ProfileView {
        void init(int pos);

        void showGoogleAccountChooser();
    }
}
