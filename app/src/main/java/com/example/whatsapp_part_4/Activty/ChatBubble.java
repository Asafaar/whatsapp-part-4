//package com.example.whatsapp_part_4.Activty;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.widget.FrameLayout;
//
//import com.amulyakhare.textdrawable.TextDrawable;
//import com.google.android.material.color.MaterialColors;
//import com.google.android.material.shape.MaterialShapeDrawable;
//import com.google.android.material.shape.ShapeAppearanceModel;
//
//public class ChatBubble extends FrameLayout {
//    private MaterialShapeDrawable shapeDrawable;
//    public TextDrawable textDrawable;
//
//    public ChatBubble(Context context) {
//        super(context);
//        init();
//    }
//
//    public ChatBubble(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public ChatBubble(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init();
//    }
//
//    private void init() {
//        shapeDrawable = new MaterialShapeDrawable();
//        textDrawable = new TextDrawable();
//
//        shapeDrawable.setShapeAppearanceModel(
//                ShapeAppearanceModel.builder()
//                        .setAllCornersRadius(8f)
//                        .build());
//
//        textDrawable.setTextSize(16f);
//        textDrawable.setTextColor(MaterialColors.getColor(getContext(), R.color.primary));
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        shapeDrawable.draw(canvas);
//        textDrawable.draw(canvas);
//    }
//
//    public void setMessage(String message) {
//        if (TextUtils.isEmpty(message)) {
//            return;
//        }
//
//        textDrawable.setText(message);
//        invalidate();
//    }
//
//    public void setSender(boolean isSender) {
//        int backgroundColor = isSender ? MaterialColors.getColor(getContext(), R.color.primary) : MaterialColors.getColor(getContext(), R.color.secondary);
//        int textColor = isSender ? MaterialColors.getColor(getContext(), R.color.white) : MaterialColors.getColor(getContext(), R.color.black);
//
//        shapeDrawable.setFillColor(backgroundColor);
//        textDrawable.setTextColor(textColor);
//    }}
