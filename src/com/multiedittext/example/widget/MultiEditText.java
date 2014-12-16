package com.multiedittext.example.widget;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.EditText;
import com.multiedittext.example.R;
/**
 * 多个字符分开输入框
 * 
 * @author arlong
 *
 */
public class MultiEditText extends EditText {

	private int textLength;

	private int borderColor;
	private float borderWidth;
	private float borderRadius;

	private int multiLength;
	private int multiColor;
	private float multiWidth;
	private float multiRadius;

	private Paint multiPaint = new Paint(ANTI_ALIAS_FLAG);
	private Paint borderPaint = new Paint(ANTI_ALIAS_FLAG);

	private final int defaultContMargin = 5;
	private final int defaultSplitLineWidth = 3;
	private boolean isTextVisible;

	public MultiEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		final Resources res = getResources();

		final int defaultBorderColor = res.getColor(R.color.default_ev_border_color);
		final float defaultBorderWidth = res.getDimension(R.dimen.default_ev_border_width);
		final float defaultBorderRadius = res.getDimension(R.dimen.default_ev_border_radius);

		final int defaultMultiLength = res.getInteger(R.integer.default_ev_multi_length);
		final int defaultMultiColor = res.getColor(R.color.default_ev_multi_color);
		final float defaultMultiWidth = res.getDimension(R.dimen.default_ev_multi_width);
		final float defaultMultiRadius = res.getDimension(R.dimen.default_ev_multi_radius);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MultiEditText, 0, 0);
		try {
			borderColor = a.getColor(R.styleable.MultiEditText_borderColor, defaultBorderColor);
			borderWidth = a.getDimension(R.styleable.MultiEditText_borderWidth, defaultBorderWidth);
			borderRadius = a.getDimension(R.styleable.MultiEditText_borderRadius, defaultBorderRadius);
			multiLength = a.getInt(R.styleable.MultiEditText_multiLength, defaultMultiLength);
			multiColor = a.getColor(R.styleable.MultiEditText_multiColor, defaultMultiColor);
			multiWidth = a.getDimension(R.styleable.MultiEditText_multiWidth, defaultMultiWidth);
			multiRadius = a.getDimension(R.styleable.MultiEditText_multiRadius, defaultMultiRadius);
			isTextVisible = a.getBoolean(R.styleable.MultiEditText_multiTextVisible, false);
		} finally {
			a.recycle();
		}

		borderPaint.setStrokeWidth(borderWidth);
		borderPaint.setColor(borderColor);
		multiPaint.setStrokeWidth(multiWidth);
		multiPaint.setStyle(Paint.Style.FILL);
		multiPaint.setColor(multiColor);

		setFilters(new InputFilter[] { new InputFilter.LengthFilter(multiLength) });
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();

		// 外边框
		RectF rect = new RectF(0, 0, width, height);
		borderPaint.setColor(borderColor);
		canvas.drawRoundRect(rect, borderRadius, borderRadius, borderPaint);

		// 内容区
		RectF rectIn = new RectF(rect.left + defaultContMargin, rect.top + defaultContMargin, rect.right - defaultContMargin, rect.bottom - defaultContMargin);
		borderPaint.setColor(Color.WHITE);
		canvas.drawRoundRect(rectIn, borderRadius, borderRadius, borderPaint);

		// 分割线
		borderPaint.setColor(borderColor);
		borderPaint.setStrokeWidth(defaultSplitLineWidth);
		for (int i = 1; i < multiLength; i++) {
			float x = width * i / multiLength;
			canvas.drawLine(x, 0, x, height, borderPaint);
		}

		// 密码

		if (isTextVisible) {
			int cellWidth = width / multiLength;
			TextPaint paint = getPaint();
			FontMetricsInt fontMetrics = paint.getFontMetricsInt();  
			float baseline = rectIn.top + (rectIn.bottom - rectIn.top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;  
		    paint.setTextAlign(Paint.Align.CENTER);  
			for (int i = 0; i < textLength; i++) {
				int left=cellWidth *i;
				int right=cellWidth*(i+1);
				RectF rectCell = new RectF(left, rect.top + defaultContMargin, right, rect.bottom - defaultContMargin);				
				canvas.drawText(String.valueOf(getText().toString().charAt(i)), rectCell.centerX(), baseline, paint);
			}
		} else {
			float cx, cy = height / 2;
			float half = width / multiLength / 2;
			for (int i = 0; i < textLength; i++) {
				cx = width * i / multiLength + half;
				canvas.drawCircle(cx, cy, multiWidth, multiPaint);
			}
		}

	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		this.textLength = text.toString().length();
		invalidate();
	}

	public int getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(int borderColor) {
		this.borderColor = borderColor;
		borderPaint.setColor(borderColor);
		invalidate();
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public void setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
		borderPaint.setStrokeWidth(borderWidth);
		invalidate();
	}

	public float getBorderRadius() {
		return borderRadius;
	}

	public void setBorderRadius(float borderRadius) {
		this.borderRadius = borderRadius;
		invalidate();
	}

	public int getMultiLength() {
		return multiLength;
	}

	public void setMultiLength(int multiLength) {
		this.multiLength = multiLength;
		invalidate();
	}

	public int getMultiColor() {
		return multiColor;
	}

	public void setMultiColor(int multiColor) {
		this.multiColor = multiColor;
	}

	public float getMultiWidth() {
		return multiWidth;
	}

	public void setMultiWidth(float multiWidth) {
		this.multiWidth = multiWidth;
		multiPaint.setStrokeWidth(multiWidth);
		invalidate();
	}

	public float getMultiRadius() {
		return multiRadius;
	}

	public void setMultiRadius(float multiRadius) {
		this.multiRadius = multiRadius;
		multiPaint.setStrokeWidth(multiRadius);
		invalidate();
	}

	public boolean isTextVisible() {
		return isTextVisible;
	}

	public void setTextVisible(boolean isTextVisible) {
		this.isTextVisible = isTextVisible;
		invalidate();
	}

	
}
