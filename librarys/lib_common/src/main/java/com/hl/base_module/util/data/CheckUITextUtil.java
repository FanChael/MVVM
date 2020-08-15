package com.hl.base_module.util.data;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hl.base_module.util.app.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by hl on 2018/3/29.
 */

public class CheckUITextUtil {
    /**
     * 匹配手机号的规则：[3578]是手机号第二位可能出现的数字
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    /**
     * 匹配邮箱的规则
     */
    public static final String REGEX_EMAIL = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 检测Editter是否有空
     *
     * @param views
     * @return
     */
    public static boolean bIsNullString(EditText... views) {
        for (EditText view : views) {
            if (view.getText().toString().trim().equals("")) {
                if (view.getHint().toString().contains("输入")){
                    ToastUtil.showTost(view.getHint().toString());
                } else {
                    ToastUtil.showTost("请输入" + view.getHint().toString());
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 检测Editter是否有空 - 没有提示
     * @param views
     * @return
     */
    public static boolean bIsNullStringNoTip(EditText... views) {
        for (EditText view : views) {
            if (view.getText().toString().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测Editter是否有空 - 没有提示
     * @param views
     * @return
     */
    public static boolean bIsNullStringNoTip(TextView... views) {
        for (TextView view : views) {
            if (view.getText().toString().trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 对比两个编辑框文本是否相同
     *
     * @param view1
     * @param view2
     * @return
     */
    public static boolean bEqualString(EditText view1, EditText view2) {
        boolean bSame = view1.getText().toString().equals(view2.getText().toString());
        if (!bSame){
            ToastUtil.showTost("两次输入不一致!");
        }
        return bSame;
    }

    /**
     * 检查是否同意用户协议
     * @param checkBox
     * @return
     */
    public static boolean bAgreement(CheckBox checkBox) {
        if (!checkBox.isChecked()){
            ToastUtil.showTost("请先同意用户协议!");
            return false;
        }
        return true;
    }

    /**
     * 检测邮箱的正确性
     *
     * @param view1
     * @return
     */
    public static boolean bIsNormalEmail(EditText view1) {
        return Pattern.matches(REGEX_EMAIL, view1.getText().toString());
    }

    /**
     * 检测手机号或邮箱的正确性
     *
     * @param view1
     * @return
     */
    public static boolean bIsNormalPhoneOrEmail(EditText view1) {
        if (view1.getText().toString().contains("@")) {
            return Pattern.matches(REGEX_EMAIL, view1.getText().toString());
        }
        return Pattern.matches(REGEX_MOBILE, view1.getText().toString());
    }

    //    1、手机号开头集合
    //        166，
    //        176，177，178
    //        180，181，182，183，184，185，186，187，188，189
    //        145，147
    //        130，131，132，133，134，135，136，137，138，139
    //        150，151，152，153，155，156，157，158，159
    //        198，199

    /**
     * 检测手机号正确性
     *
     * @param editText
     * @return
     */
    public static boolean bIsNormalPhoneNumber(EditText editText)
            throws PatternSyntaxException {
        if (bIsNullString(editText)) {
            ToastUtil.showTost("请输入手机号");
            return false;
        }
        if (editText.getText().toString().length() != 11) {
            ToastUtil.showTost("请输入11位手机号");
            return false;
        }
        // String regExp = "^((13[0-9])|(15[^4])|(166)|(17[0-8])|(18[0-9])|(19[8-9])|(147,145))\\d{8}$";
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(editText.getText().toString());
        if (!m.matches()){
            ToastUtil.showTost("请输入正确的手机号");
            return false;
        }
        return true;
    }

    /**
     * 密码位数必须大于等于6位
     *
     * @param view
     * @return
     */
    public static boolean bCheckPassLengthOk(EditText view) {
        if (view.getText().toString().length() < 6) {
            ToastUtil.showTost(view.getHint().toString() + "至少6位!");
            return false;
        }
        return true;
    }

    /**
     * 位数必须大于等于2位
     * @param views
     * @return
     */
    public static boolean bMin2( EditText...views){
        for (EditText view:views) {
            if (view.getText().toString().length() < 2){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是纯数字
     * @return
     */
    public static boolean bIsNumber( EditText view){
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(view.getText().toString());
        if (matcher.matches()) {
            ToastUtil.showTost("不能纯数字作为用户名!");
        }
        return matcher.matches();
    }

    /**
     * 位数必须大于等于4位
     * @param views
     * @return
     */
    public static boolean bMin4( EditText...views){
        for (EditText view:views) {
            if (view.getText().toString().length() < 4){
                ToastUtil.showTost(view.getHint().toString() + "至少输入4个字符!");
                return false;
            }
        }
        return true;
    }

    /**
     * 检查微信账号可用性
     * @param view
     * @return
     */
    public static boolean checkWebchat(EditText view){
        String regExs = "^[a-zA-Z][a-zA-Z0-9_-]{5,21}$";
        Pattern p = Pattern.compile(regExs);
        Matcher m = p.matcher(view.getText().toString());
        return m.matches();
    }
}