package com.volcengine.zeus;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * plugin1-api中的自定义view，与plugin2-impl中重复
 *
 * @author xuekai
 * @date 11/22/21
 */
public class SameNameView extends View {
    public SameNameView(Context context) {
        super(context);
    }

    public SameNameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SameNameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
