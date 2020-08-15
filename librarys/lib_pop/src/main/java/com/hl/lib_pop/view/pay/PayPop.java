package com.hl.lib_pop.view.pay;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.lib_pop.R;

/**
 * 支付弹窗
 *
 * @Author: hl
 * @Date: created at 2020/6/8 10:46
 * @Description: com.hl.lib_pop.view.taskstate
 */
public class PayPop extends DialogFragment {
    private AppCompatTextView dfpp_payValue;
    private RadioGroup dfpp_payChoose;
    private PAY_TYPE pay_type = PAY_TYPE.ALIPAY;
    private PayBack payBack;
    private String priceValue = "";

    public enum PAY_TYPE {
        WEBCHAT, ALIPAY
    }

    private static PayPop instance;

    public static PayPop getInstance() {
        if (null == instance) {
            synchronized (PayPop.class) {
                instance = new PayPop();
            }
        }
        return instance;
    }

    private PayPop() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    /**
     * 普通的弹窗，也可以getLayoutInflater.inflate加载AlertDialog.Builder、Dialog
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        // window.setLayout(ScreenUtil.SCREEN_WIDTH * 29 / 37,-2 );
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_paypop, null);

        dfpp_payValue = view.findViewById(R.id.dfpp_payValue);
        dfpp_payValue.setText(priceValue);
        dfpp_payChoose = view.findViewById(R.id.dfpp_payChoose);

        // 点击事件
        AppCompatImageView dfpp_payClose = view.findViewById(R.id.dfpp_payClose);
        dfpp_payClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != instance) {
                    instance.dismiss();
                }
            }
        });
        MaterialButton dfpp_payNow = view.findViewById(R.id.dfpp_payNow);
        dfpp_payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != payBack) {
                    payBack.howToPay(pay_type);
                }
            }
        });

        dfpp_payChoose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.dfpp_weChat)
                    pay_type = PAY_TYPE.WEBCHAT;
                else if (checkedId == R.id.dfpp_alipay)
                    pay_type = PAY_TYPE.ALIPAY;
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && null != dialog.getWindow()) {
            dialog.getWindow().setLayout(ScreenUtil.SCREEN_WIDTH, -2);
        }
    }

    /**
     * 防止多次被add
     */
    public void showSafely(@NonNull FragmentManager fm, @Nullable String tag) {
        if (this.isAdded() || null != fm.findFragmentByTag(tag)) {
            return;
        }
        show(fm, tag);
    }

    /**
     * 设置运费价格
     *
     * @param price
     */
    public PayPop setPrice(String price) {
        this.priceValue = price;
        return this;
    }

    /**
     * 设置支付回调
     *
     * @param payBack
     * @return
     */
    public PayPop setPayBack(PayBack payBack) {
        this.payBack = payBack;
        return this;
    }

    public interface PayBack {
        void howToPay(PAY_TYPE pay_type);
    }
}
