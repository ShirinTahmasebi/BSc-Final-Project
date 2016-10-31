package shirin.tahmasebi.mscfinalproject.reminder;

import android.location.Address;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class AddReminderViewPagerAdapter extends FragmentPagerAdapter {
    private final static int WIZARD_PAGES_COUNT = 3;
    private final ReminderAddViewPagerPresenter mPresenter;

    AddReminderViewPagerAdapter(FragmentManager fm, ReminderAddViewPagerPresenter presenter) {
        super(fm);
        mPresenter = presenter;
    }

    @Override
    public Fragment getItem(int position) {
        return new AddReminderWizardFragment(position, mPresenter);
    }

    @Override
    public int getCount() {
        return WIZARD_PAGES_COUNT;
    }
}
