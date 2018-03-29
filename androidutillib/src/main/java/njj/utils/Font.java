package njj.utils;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.EmbossMaskFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.MaskFilterSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import java.util.Arrays;

/**
 * Created by jian on 2016/10/14.
 */
public final class Font extends SpannableString {

    public static Font compose(@NonNull CharSequence... fonts) {
        return compose(Arrays.asList(fonts));
    }

    private static Font compose(Iterable<? extends CharSequence> fontIterable) {
        final SpannableStringBuilder builder = new SpannableStringBuilder();
        for (CharSequence font : fontIterable) {
            builder.append(SpannableString.valueOf(font));
        }

        return Font.valueOf(builder);
    }

    public static Font valueOf(CharSequence source) {
        if (source instanceof Font) {
            return (Font) source;
        } else {
            return new Font(source);
        }
    }

    public Font(@NonNull CharSequence content) {
        super(content);
    }

    /**
     * 设置文本颜色
     */
    public Font textColor(Context context, @ColorRes int res) {
        applySpan(new ForegroundColorSpan(context.getResources().getColor(res)));
        return this;
    }

    /**
     * 设置文本背景颜色
     */
    public Font textBkgColor(Context context, @ColorRes int res) {
        applySpan(new BackgroundColorSpan(context.getResources().getColor(res)));
        return this;
    }

    /**
     * 粗体
     */
    public Font bold() {
        applySpan(new StyleSpan(Typeface.BOLD));
        return this;
    }

    /**
     * 斜体
     */
    public Font italic() {
        applySpan(new StyleSpan(Typeface.ITALIC));
        return this;
    }

    /**
     * 粗 斜体
     */
    public Font boldItalic() {
        applySpan(new StyleSpan(Typeface.BOLD_ITALIC));
        return this;
    }

    /**
     * 设置文字字体
     */
    public Font textType(final String type) {
        applySpan(new TypefaceSpan(type));
        return this;
    }

    /**
     * 设置文本外貌（包括字体、大小、样式和颜色）
     */
    public Font textStyle(Context context, final int style) {
        applySpan(new TextAppearanceSpan(context, style));
        return this;
    }

    /**
     * 设置文本删除线
     */
    public Font strikethroughColor(final Context context, @ColorRes final int res, final boolean isShow) {
        applySpan(new StrikethroughSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(res));
                ds.setStrikeThruText(isShow); // true 设置删除线，false 取消删除线
            }
        });
        return this;
    }

    /**
     * 下标，数学公式会用到
     */
    public Font subText() {
        applySpan(new SubscriptSpan());
        return this;
    }

    /**
     * 上标，数学公式会用到
     */
    public Font superText() {
        applySpan(new SuperscriptSpan());
        return this;
    }

    /**
     * 设置字体相对大小
     */
    public Font relativeSize(float size) {
        applySpan(new RelativeSizeSpan(size));
        return this;
    }

    /**
     * 设置字体绝对大小
     */
    public Font absoluteSize(int size) {
        applySpan(new AbsoluteSizeSpan(size));
        return this;
    }

    /**
     * 设置文本超链接
     */
    public Font textUrl(String url) {
        applySpan(new URLSpan(url));
        return this;
    }

    /**
     * 文本基于x轴缩放
     */
    public Font textScaleX(float scaleX) {
        applySpan(new ScaleXSpan(scaleX));
        return this;
    }

    /**
     * 设置图片：drawable
     */
    public Font image(Drawable drawable) {
        applySpan(new ImageSpan(drawable));
        return this;
    }

    /**
     * 设置图片：id
     */
    public Font image(Context context, @DrawableRes int resId) {
        applySpan(new ImageSpan(context, resId));
        return this;
    }

    /**
     * 设置图片，基于文本基线或底部对齐。
     *
     * @param alignment DynamicDrawableSpan.ALIGN_BASELINE || DynamicDrawableSpan.ALIGN_BOTTOM
     */
    public Font drawable(final Context context, @DrawableRes final int resId, int alignment) {
        applySpan(new DynamicDrawableSpan(alignment) {
            @Override
            public Drawable getDrawable() {
                Drawable drawable = context.getResources().getDrawable(resId);
                return drawable;
            }
        });
        return this;
    }

    /**
     * 文字下划线
     */
    public Font underline() {
        applySpan(new UnderlineSpan());
        return this;
    }

    /**
     * 文字光栅效果
     */
//    public Font textRasterizer(){
//        applySpan(new RasterizerSpan());
//        return this;
//    }

    /**
     * 修饰效果
     */
    private void addmaskfilteSpan() {
        //模糊(BlurMaskFilter)
        applySpan(new MaskFilterSpan(new BlurMaskFilter(3, BlurMaskFilter.Blur.OUTER)));
        //浮雕(EmbossMaskFilter)
        applySpan(new MaskFilterSpan(new EmbossMaskFilter(new float[]{1, 1, 3}, 1.5f, 8, 3)));
    }

    /**
     * 带下划线可点击的
     */
    public Font clickableWithUnderline(final View.OnClickListener onClickListener, final int linkColor) {
        applySpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickListener.onClick(widget);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(linkColor);
                ds.setUnderlineText(true);
            }
        });
        return this;
    }

    /**
     * 可点击的
     */
    public Font clickable(final View.OnClickListener onClickListener) {
        applySpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                onClickListener.onClick(widget);
            }
        });
        return this;
    }

    private void applySpan(CharacterStyle style) {
       /*Spannable.SPAN_EXCLUSIVE_INCLUSIVE：在 Span前面输入的字符不应用 Span的效果，在后面输入的字符应用Span效果。
         Spannable.SPAN_INCLUSIVE_EXCLUSIVE：在 Span前面输入的字符应用 Span 的效果，在后面输入的字符不应用Span效果。
         Spannable.SPAN_INCUJSIVE_INCLUSIVE：在 Span前后输入的字符都应用 Span 的效果。
         Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括。*/
        setSpan(style, 0, length(), SPAN_EXCLUSIVE_EXCLUSIVE);
    }
}
