package com.taurus.trolley.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.taurus.trolley.R;

/**
 * Created by semih on 19.12.2015.
 */
public class SlideButton extends FrameLayout {
    private static final long ANIM_MS = 100;
    private static final int COLLAPSED = 0;
    private static final int EXPANDED = 1;
    private static final String KEY_STATE = "key_state";
    private ImageView imageDown;
    private TextView textView;
    private int state;

    public SlideButton(Context context) {
        super(context);
        init(context);
    }

    public SlideButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setBackgroundResource(R.drawable.slide_button);
        FrameLayout root = (FrameLayout) inflate(context, R.layout.slide_button, this);
        textView = (TextView) root.findViewById(R.id.text_view_slide_button);
        imageDown = (ImageView) root.findViewById(R.id.image_view_slide_button);
    }

    public void setCollapsed() {
        if (state == COLLAPSED) {
            return;
        }
        state = COLLAPSED;
        YoYo.with(Techniques.FadeOutUp).duration(ANIM_MS).playOn(imageDown);
        YoYo.with(Techniques.FadeInUp).duration(ANIM_MS).playOn(textView);
    }

    public void setExpanded() {
        if (state == EXPANDED) {
            return;
        }
        state = EXPANDED;
        imageDown.setVisibility(VISIBLE);
        YoYo.with(Techniques.FadeOutDown).duration(ANIM_MS).playOn(textView);
        YoYo.with(Techniques.FadeInDown).duration(ANIM_MS).playOn(imageDown);
    }
}
