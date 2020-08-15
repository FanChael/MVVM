package com.hl.lib_pop.view.share;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.lib_miniui.view.texts.Text_Icon;
import com.hl.lib_pop.R;
import com.hl.lib_pop.view.adapter.SharePopAdapter;
import com.hl.lib_pop.view.bean.PicInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 分享弹窗
 *
 * @Author: hl
 * @Date: created at 2020/6/8 10:46
 * @Description: com.hl.lib_pop.view.share
 */
public class SharePop extends DialogFragment {
    private RecyclerView dfsh_shareGift;
    private SHARE_TYPE pay_type = SHARE_TYPE.ALIPAY;
    private SharePopAdapter sharePopAdapter;
    private List<PicInfoBean> picInfoBeans = new ArrayList<>();

    public enum SHARE_TYPE {
        WEBCHAT, ALIPAY
    }

    private static SharePop instance;

    public static SharePop getInstance() {
        if (null == instance) {
            synchronized (SharePop.class) {
                instance = new SharePop();
            }
        }
        return instance;
    }

    private SharePop() {
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
        View view = inflater.inflate(R.layout.dialog_fragment_share, null);

        dfsh_shareGift = view.findViewById(R.id.dfsh_shareGift);
        // 设置图片呀
        if (null == sharePopAdapter && picInfoBeans.size() > 0) {
            sharePopAdapter = new SharePopAdapter(requireContext(), picInfoBeans);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            dfsh_shareGift.setLayoutManager(linearLayoutManager);
            dfsh_shareGift.setAdapter(sharePopAdapter);
        }

        // 点击事件
        MaterialButton dfsh_cancel = view.findViewById(R.id.dfsh_cancel);
        dfsh_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != instance) {
                    instance.dismiss();
                }
            }
        });

        Text_Icon dfsh_shareWebchart = view.findViewById(R.id.dfsh_shareWebchart);
        dfsh_shareWebchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Text_Icon dfsh_shareAlipay = view.findViewById(R.id.dfsh_shareAlipay);
        dfsh_shareAlipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
     * 设置分享奖品图片
     *
     * @param picList
     */
    public SharePop setImageList(List<String> picList) {
        picInfoBeans.clear();
        for (String url: picList){
            picInfoBeans.add(new PicInfoBean(url));
        }
        return this;
    }
}
