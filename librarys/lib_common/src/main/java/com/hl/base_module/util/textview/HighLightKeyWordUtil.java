package com.hl.base_module.util.textview;

import android.graphics.LinearGradient;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;

import com.hl.base_module.util.screen.DensityUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighLightKeyWordUtil {
    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param keyColor 关键字颜色和字体大小
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int keyColor, String text, String keyword, int keySize) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            s.setSpan(new ForegroundColorSpan(keyColor),
                    start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            s.setSpan(new AbsoluteSizeSpan(keySize), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 多个关键字
     * @return
     */
    public static SpannableString getHighLightKeyWord(int color, String text,String[] keyword) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keyword.length; i++) {
            Pattern p = Pattern.compile(keyword[i]);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * @param color 关键字背景颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int tvcolor, int color, String text, int keySize, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        //while (m.find()) {
        if (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new RoundBackgroundColorSpan(color, tvcolor, DensityUtil.dip2px(keySize), 6), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param color 关键字边框颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getStrokeKeyWord(int tvcolor, int color, String text, int keySize, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        //while (m.find()) {
        if (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new RoundBackgroundColorSpa(color, tvcolor, DensityUtil.dip2px(keySize), 6), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param color 关键字边框颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    public static SpannableString getGradiantKeyWord(LinearGradient linearGradient, int color, String text, int keySize, String keyword) {
        SpannableString s = new SpannableString(text);
        Pattern p = Pattern.compile(keyword);
        Matcher m = p.matcher(s);
        //while (m.find()) {
        if (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new RoundBackgroundGradiantColorSp(linearGradient, color, DensityUtil.dip2px(keySize), 6), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return s;
    }

    /**
     * @param color 关键字背景颜色
     * @param text 文本
     * @param keywords 多个关键字
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int tvcolor, int color, String text, int keySize, String[] keywords) {
        SpannableString s = new SpannableString(text);
        for (int i = 0; i < keywords.length; i++) {
            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            //while (m.find()) {
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new RoundBackgroundColorSpan(color, tvcolor, DensityUtil.dip2px(keySize), 6), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * @param color 关键字背景颜色
     * @param s SpannableString
     * @param keywords 多个关键字
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int[] tvcolor, int[] color, SpannableString s, int keySize, String[] keywords) {
        int strLength = 0;
        for (int i = 0; i < keywords.length; i++) {
            ///< 必须是开头的才标记背景，所以索引必须小于开头内容长度
            strLength += keywords[i].length();

            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            ///< 只找开头的，标题中的不找
            //while (m.find()) {
            //if (m.find()) {
            if (m.find() && m.start() < strLength) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new RoundBackgroundColorSpan(color[i], tvcolor[i], DensityUtil.dip2px(keySize), 6), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }

    /**
     * @param color 关键字背景颜色
     * @param text 文本
     * @param keywords 多个关键字
     * @return
     */
    public static SpannableString getBackgroudKeyWord(int[] tvcolor, int[] color, String text, int keySize, String[] keywords) {
        SpannableString s = new SpannableString(text);
        int strLength = 0;
        for (int i = 0; i < keywords.length; i++) {
            ///< 必须是开头的才标记背景，所以索引必须小于开头内容长度
            strLength += keywords[i].length();

            Pattern p = Pattern.compile(keywords[i]);
            Matcher m = p.matcher(s);
            ///< 只找开头的，标题中的不找
            //while (m.find()) {
            //if (m.find()) {
            if (m.find() && m.start() < strLength) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new RoundBackgroundColorSpan(color[i], tvcolor[i], DensityUtil.dip2px(keySize), 6), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return s;
    }
}