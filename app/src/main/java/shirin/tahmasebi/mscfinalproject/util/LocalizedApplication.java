package shirin.tahmasebi.mscfinalproject.util;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

/**
 * کلاس والد {@link Application} با قابلیت محلی سازی
 * <p/>
 * این کلاس می‌تواند به عنوان یک کلاس والد برای کلاس {@link Application} اصلی برنامه استفاده شود. این کلاس دو
 * تابع را در سطح کل برنامه منتشر می‌کند. با استفاده از توابع مذکور، عملیات تغییر {@link Locale} برنامه در سطح
 * خود برنامه (و نه Android) انجام می‌شود.
 * تضمین می‌شود که در صورت ذخیره‌ی صحیح {@link Locale} کاربر (با استفاده از تابع
 * {@link LocalizedApplication#setLocale(Locale)}، با هر بار باز شدن برنامه، مجددا همان {@link Locale} بارگزاری
 * شود.
 * <p/>
 * برای استفاده از این کلاس، پیش از ایجاد شدن Application، تابع {@link LocalizedApplication#getLocale()} باید
 * قابل فراخوانی باشد.
 */
public abstract class LocalizedApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        configLocale();
    }

    private void configLocale() {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = getLocale();
        if (!config.locale.equals(locale)) {
            Locale.setDefault(locale);
            config.locale = locale;
            Resources resources = getBaseContext().getResources();
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Configuration configClone = new Configuration(newConfig);
        Locale locale = getLocale();
        if (locale != null) {
            configClone.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(configClone,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }

    /**
     * {@link Locale} فعلی برنامه را، به مقدار {@param locale} تغییر می‌دهد.
     *
     * @param locale مقدار مورد نظر برای تغییر
     */
    public void changeLocale(Locale locale) {
        setLocale(locale);
        configLocale();
    }

    /**
     * نمونه {@link Locale} فعلی دستگاه را باز می‌گرداند.
     * این تابع می‌بایست یک نمونه {@link Locale} پیش‌فرض یا نمونه‌ای که توسط
     * {@link LocalizedApplication#setLocale(Locale)} ذخیره شده است را (با دقت دلخواه) باز گرداند.
     * در مورد دقت نمونه {@link Locale} بازگردانده شده، این مورد به خواست توسعه دهنده وابسته است. به عنوان مثال
     * می‌توان تنها بخش زبان {@link Locale} مورد نظر را ذخیره کرد و توسط این تابع بازگردانی نمود.
     * {@see LocalizedApplication#setLocale(Locale)}
     *
     * @return نمونه {@link Locale} فعلی برنامه.
     */
    public abstract Locale getLocale();

    /**
     * یک نمونه {@link Locale} را جهت بازیابی بعدی ذخیره می‌کند.
     * این تابع باید نمونه {@param locale} ارائه شده را، با دقت مورد نیاز ذخیره کند. در هنگاه تغییرlocale، این تابع
     * فراخوانی خواهد شد.
     *
     * @param locale نمونه {@link Locale} جهت ذخیره
     */
    public abstract void setLocale(Locale locale);
}
