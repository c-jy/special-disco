package com.example.a44540.myapplication;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;

public class SerialTextView extends android.support.v7.widget.AppCompatTextView {
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private final int mPadding = 30;
    private final float mLineWidth = 3;
    private final int mSerialLargeSize = -5;
    private CharSequence mTitleText;
    private int mSerialColor;
    private int mSerialTextColor;
    private int mSerialTextBgColor;

    public SerialTextView(Context context) {
        super(context);
    }

    public SerialTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        setPadding(0, 10, 0, 10);
        mPaint1 = new Paint();
        mPaint1.setColor(mSerialColor);
        mPaint1.setAntiAlias(true);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        mPaint1.setTypeface(font);

        mPaint2 = new Paint();
        mPaint2.setColor(mSerialTextBgColor);
        mPaint2.setAntiAlias(true);

        mPaint3 = new Paint();
        mPaint3.setColor(mSerialTextColor);
        mPaint3.setTextSize(getTextSize() + mSerialLargeSize);
        mPaint3.setAntiAlias(true);//去除锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = mTitleText.toString();
        Rect rect = new Rect();
        mPaint3.getTextBounds(text,0,text.length(), rect);
        float textWidth = rect.width() + mPadding + mPadding;
        float textHeight = rect.height();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0, 0, textWidth, getMeasuredHeight(), getMeasuredHeight()/2, getMeasuredHeight()/2, mPaint1);
            canvas.drawRoundRect(mLineWidth, mLineWidth, textWidth - mLineWidth, getMeasuredHeight() - mLineWidth, getMeasuredHeight()/2, getMeasuredHeight()/2, mPaint2);
        } else {
            canvas.drawCircle(getMeasuredHeight()/2, getMeasuredHeight()/2, getMeasuredHeight()/2, mPaint1);
            canvas.drawCircle(textWidth - getMeasuredHeight()/2, getMeasuredHeight()/2, getMeasuredHeight()/2, mPaint1);
            canvas.drawRect(getMeasuredHeight()/2, 0, textWidth - getMeasuredHeight()/2, getMeasuredHeight(), mPaint1);
            canvas.drawCircle(getMeasuredHeight()/2, getMeasuredHeight()/2, getMeasuredHeight()/2 - mLineWidth, mPaint2);
            canvas.drawCircle(textWidth - getMeasuredHeight()/2, getMeasuredHeight()/2, getMeasuredHeight()/2 - mLineWidth, mPaint2);
            canvas.drawRect(getMeasuredHeight()/2, mLineWidth, textWidth - getMeasuredHeight()/2, getMeasuredHeight() - mLineWidth, mPaint2);
        }
        canvas.drawRect(mPaint3.measureText(text), getMeasuredHeight() - mLineWidth, getMeasuredWidth() - mPadding, getMeasuredHeight(), mPaint1);
        canvas.drawText(text, mPadding, (getMeasuredHeight() + textHeight) / 2, mPaint3);
        canvas.save();

        canvas.translate(textWidth + mPadding, 0);
        super.onDraw(canvas);
    }
    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray types = context.obtainStyledAttributes(attrs, R.styleable.SerialTextView);

        mTitleText = types.getText(R.styleable.SerialTextView_serialText);
        mSerialColor = types.getColor(R.styleable.SerialTextView_serialColor, getResources().getColor(R.color.normal_color_theme_brown));
        mSerialTextColor = types.getColor(R.styleable.SerialTextView_serialTextColor, getResources().getColor(R.color.normal_color_theme_brown));
        mSerialTextBgColor = types.getColor(R.styleable.SerialTextView_serialTextBgColor, getResources().getColor(android.R.color.white));

        types.recycle();
    }
}
