package shirin.tahmasebi.mscfinalproject.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.view.View;

public class UIUtils {

    /**
     * {@link Drawable} با آی دی {@param drawableId} را بر اساس نسخه‌ی API، باز می‌گرداند.
     *
     * @param context    نمونه {@link Context} جهت resolve کردن {@param drawableId}
     * @param drawableId ID تصویر مورد نظر
     * @return نمونه {@link Drawable} با آی‌دی {@param drawableId}
     */
    public static Drawable getDrawable(Context context, @DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(drawableId, context.getTheme());
        } else {
            //noinspection deprecation
            return context.getResources().getDrawable(drawableId);
        }
    }

    /**
     * @see UIUtils#getDrawable(Context, int)
     */
    public static Drawable getDrawable(View view, @DrawableRes int drawableId) {
        if (Build.VERSION.SDK_INT >= 21) {
            return view.getResources().getDrawable(drawableId, null);
        } else {
            //noinspection deprecation
            return view.getResources().getDrawable(drawableId);
        }
    }

    /**
     * روی یک {@link View} ضمن در نظر گرفتن نسخه API، پس زمینه قرار می‌دهد.
     *
     * @param view     نمونه {@link View} برای قرار دادن پس‌زمینه
     * @param drawable پس زمینه
     */
    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            //noinspection deprecation
            view.setBackgroundDrawable(drawable);
        }
    }

    private UIUtils() {
        throw new AssertionError();
    }
}
