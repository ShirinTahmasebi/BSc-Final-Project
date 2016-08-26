package shirin.tahmasebi.mscfinalproject.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import shirin.tahmasebi.mscfinalproject.R;


public class AttractiveTextInputLayout extends TextInputLayout {
    private static final int ERROR_SHAKE_DURATION = 1000;

    public AttractiveTextInputLayout(Context context) {
        super(context);
    }

    public AttractiveTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AttractiveTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setErrorEnabled(boolean enabled) {
        super.setErrorEnabled(enabled);
        if (!enabled) {
            if (super.getEditText() != null) {
                Drawable background = UIUtils.getDrawable(this, R.drawable.bg_bottom_line_edit_text);
                UIUtils.setBackground(super.getEditText(), background);
            }
        }
    }

    @Override
    public void setError(@Nullable final CharSequence error) {
        super.setError(error);
        if (error == null)
            return;
        YoYo.with(Techniques.Shake)
                .duration(ERROR_SHAKE_DURATION)
                .playOn(super.getEditText());

        if (super.getEditText() != null) {
            Drawable background = UIUtils.getDrawable(this, R.drawable.bg_bottom_line_edit_text_error);
            UIUtils.setBackground(super.getEditText(), background);
        }
    }
}
