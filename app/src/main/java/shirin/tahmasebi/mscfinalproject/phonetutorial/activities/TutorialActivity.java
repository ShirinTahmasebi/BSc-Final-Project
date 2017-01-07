package shirin.tahmasebi.mscfinalproject.phonetutorial.activities;


import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import shirin.tahmasebi.mscfinalproject.R;
import shirin.tahmasebi.mscfinalproject.phonetutorial.utils.Utils;
import shirin.tahmasebi.mscfinalproject.phonetutorial.views.ButtonFloat;
import shirin.tahmasebi.mscfinalproject.view.FontableTextView;


public class TutorialActivity extends Activity implements OnTouchListener, OnClickListener {

    int width = 0;
    int widthTitle = 0;
    int position = 0;
    int height = 0;

    int[] images;

    String[] titles;
    LinearLayout imageContainer;
    LinearLayout titleContainer;
    LinearLayout positionIndicator;

    public final static String TITLES = "TITLES";
    public final static String IMAGES = "IMAGES";
    public final static String COLORINDICATOR = "COLORINDICATOR";
    public final static String COLORBACKGROUND = "COLORBACKGROUND";
    public final static String COLORBUTTON = "COLORBUTTON";
    public final static String ICON = "ICON";
    public final static String COLORICON = "COLORICON";

    ButtonFloat nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        this.images = getIntent().getIntArrayExtra(IMAGES);
        this.titles = getIntent().getStringArrayExtra(TITLES);

        positionIndicator = (LinearLayout) findViewById(R.id.positionIndicator);
        imageContainer = (LinearLayout) findViewById(R.id.image_container);
        titleContainer = (LinearLayout) findViewById(R.id.title_container);

        View content = findViewById(R.id.content);
        content.setOnTouchListener(this);
        content.setBackgroundColor(getResources().getColor(R.color.primary));

        final View originContent = findViewById(R.id.origin_content);
        originContent.post(new Runnable() {

            @Override
            public void run() {

                width = originContent.getWidth();
                height = originContent.getHeight();

                // Collocate frame in to the phone
                View frame = findViewById(R.id.frame);
                RelativeLayout.LayoutParams params =
                        (RelativeLayout.LayoutParams) frame.getLayoutParams();
                params.height = height;
                params.width = width;
                ViewHelper.setX(frame, Utils.getRelativeLeft(originContent));
                ViewHelper.setY(frame, Utils.getRelativeTop(originContent));

                // Add images to frame
                addImages();
                // Add titles
                addTitles();
                // Initiate indicators
                addPositionIndicators();
                setBigBackground(positionIndicator.getChildAt(0));
            }
        });

        // Next Button
        nextButton = (ButtonFloat) findViewById(R.id.buttonNext);
        nextButton.setBackgroundColor(getResources().getColor(R.color.accent));
        nextButton.setOnClickListener(this);
        if (getIntent().hasExtra(ICON)) {
            int iconResource = getIntent().getIntExtra(ICON, 1);
            Drawable drawable = getResources().getDrawable(iconResource);
            nextButton.setIcon(drawable);
        } else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_next);
            Bitmap bitmapMutable = bitmap.copy(Config.ARGB_8888, true);
            bitmapMutable = Utils.changeColorIcon(bitmapMutable, Color.parseColor("#FFFFFF"));
            Drawable drawable = new BitmapDrawable(getResources(), bitmapMutable);
            nextButton.setIcon(drawable);
        }

    }

    @Override
    public void onClick(View v) {
        Intent data = new Intent();
        setResult(RESULT_OK, data);
        finish();
    }

    private void addImages() {
        for (int resource : images) {
            ImageView image = new ImageView(TutorialActivity.this);
            image.setAdjustViewBounds(true);
            image.setScaleType(ScaleType.CENTER_CROP);
            image.setImageResource(resource);
            imageContainer.addView(image);
            LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) image.getLayoutParams();
            params2.width = width;
            params2.height = height;
        }
        FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) imageContainer.getLayoutParams();
        params.width = width * images.length;
    }

    private void addTitles() {
        widthTitle = titleContainer.getWidth();
        for (String title : titles) {
            FontableTextView textView = new FontableTextView(TutorialActivity.this);
            titleContainer.addView(textView);
            LinearLayout.LayoutParams params2 =
                    (LinearLayout.LayoutParams) textView.getLayoutParams();
            params2.width = widthTitle;
            params2.height = titleContainer.getHeight();
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            textView.setText(title);
            textView.setPadding(
                    Utils.dpToPx(16, getResources()),
                    Utils.dpToPx(5, getResources()),
                    Utils.dpToPx(16, getResources()),
                    0);
            Typeface face = Typeface.createFromAsset(getAssets(),
                    "fonts/IRAN Sans Light.ttf");
            textView.setTypeface(face);
        }
        FrameLayout.LayoutParams params =
                (FrameLayout.LayoutParams) titleContainer.getLayoutParams();
        params.width = titleContainer.getWidth() * images.length;
    }

    // Add position indicator to view
    private void addPositionIndicators() {
        for (int i = 0; i < images.length; i++) {
            View v = new View(TutorialActivity.this);
            positionIndicator.addView(v);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) v.getLayoutParams();
            params.width = Utils.dpToPx(10, getResources());
            params.height = Utils.dpToPx(10, getResources());
            params.setMargins(Utils.dpToPx(10, getResources()), 0, 0, 0);
            v.setTag(i);
            setSmallBackground(v);
        }
    }

    // Set small background to position indicator
    private void setSmallBackground(View v) {
        v.setBackgroundResource(R.drawable.background_small_indicator);
        LayerDrawable layer = (LayerDrawable) v.getBackground();
        GradientDrawable shape =
                (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
        shape.setColor(Color.parseColor("#FFFFFF"));
    }

    // Set big background to position indicator
    private void setBigBackground(View v) {
        v.setBackgroundResource(R.drawable.background_big_indicator);
        LayerDrawable layer = (LayerDrawable) v.getBackground();
        GradientDrawable shape =
                (GradientDrawable) layer.findDrawableByLayerId(R.id.shape_bacground);
        shape.setColor(getResources().getColor(R.color.white));
    }


    // Slide interaction
    float fromTouchPosition = 0;
    float fromViewPosition = -1;
    float fromViewTitlePosition = -1;
    float newPosition;

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {
        if (fromViewPosition == -1) {
            fromViewPosition = ViewHelper.getX(imageContainer);
            fromViewTitlePosition = ViewHelper.getX(titleContainer);
        }
        switch (arg1.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fromTouchPosition = arg1.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                newPosition = fromViewPosition - (fromTouchPosition - arg1.getX());
                newPosition = (newPosition > 0) ? 0 : newPosition;
                newPosition = (newPosition < -width * (images.length - 1)) ?
                        -width * (images.length - 1) :
                        newPosition;
                ViewHelper.setX(imageContainer, newPosition);
                ViewHelper.setX(titleContainer,
                        fromViewTitlePosition +
                                (newPosition - fromViewPosition) * (widthTitle / width));
                break;
            case MotionEvent.ACTION_UP:
                if (newPosition <= (fromViewPosition - width / 3)) {
                    setSmallBackground(positionIndicator.getChildAt(position));
                    position++;
                    animate();
                    fromViewPosition = -position * width;
                    fromViewTitlePosition = -position * widthTitle;
                    setBigBackground(positionIndicator.getChildAt(position));
                } else if (newPosition >= (fromViewPosition + width / 3)) {
                    setSmallBackground(positionIndicator.getChildAt(position));
                    position--;
                    animate();
                    fromViewPosition = -position * width;
                    fromViewTitlePosition = -position * widthTitle;
                    setBigBackground(positionIndicator.getChildAt(position));
                } else {
                    animate();
                    fromViewPosition = -position * width;
                    fromViewTitlePosition = -position * widthTitle;
                }

                if (position == images.length - 1 && !nextButton.isShow)
                    nextButton.show();
                else if (nextButton.isShow && position != images.length - 1)
                    nextButton.hide();

                break;

        }
        return true;
    }

    //Animate carrousel
    private void animate() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageContainer, "x", -(position * width));
        animator1.setDuration(300);
        animator1.start();
        ObjectAnimator animator2 =
                ObjectAnimator.ofFloat(titleContainer, "x", -(position * widthTitle));
        animator2.setDuration(300);
        animator2.start();
    }


}
