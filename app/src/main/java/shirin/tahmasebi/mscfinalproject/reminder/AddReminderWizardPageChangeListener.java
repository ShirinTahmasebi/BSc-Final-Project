package shirin.tahmasebi.mscfinalproject.reminder;


import android.support.v4.view.ViewPager;

public class AddReminderWizardPageChangeListener implements ViewPager.OnPageChangeListener {
    ReminderAddViewPagerPresenter mPresenter;

    public AddReminderWizardPageChangeListener(ReminderAddViewPagerPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPresenter.viewPagerPageChanged(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
