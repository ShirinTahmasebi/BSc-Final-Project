package shirin.tahmasebi.mscfinalproject.reminder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shirin.tahmasebi.mscfinalproject.R;


@SuppressLint("ValidFragment")
public class AddReminderWizardFragment extends Fragment {
    private int wizard_page_position;
    private ReminderAddViewPagerPresenter mPresenter;

    public AddReminderWizardFragment(int position, ReminderAddViewPagerPresenter presenter) {
        this.wizard_page_position = position;
        this.mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate;
        switch (wizard_page_position) {
            case 0:
                inflate = inflater.inflate(R.layout.reminderadd_wizard1, container, false);
                initWizard1(inflate);
                break;

            case 1:
                inflate = inflater.inflate(R.layout.reminderadd_wizard2, container, false);
                initWizard2(inflate);
                break;

            case 2:
                inflate = inflater.inflate(R.layout.reminderadd_wizard3, container, false);
                initWizard3(inflate);
                break;
            default:
                inflate = inflater.inflate(R.layout.reminderadd_wizard1, container, false);
                initWizard1(inflate);
                break;
        }

        return inflate;
    }

    private void initWizard3(View view) {
        mPresenter.initWizard3(view);
    }

    private void initWizard2(View view) {
        mPresenter.initWizard2(view);
    }

    private void initWizard1(View view) {
        mPresenter.initWizard1(view);
    }
}
