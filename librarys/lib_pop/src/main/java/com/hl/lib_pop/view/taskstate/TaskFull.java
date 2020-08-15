package com.hl.lib_pop.view.taskstate;

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
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.hl.base_module.util.glide.GlideUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.lib_pop.R;

/**
 * 任务完成弹窗
*@Author: hl
*@Date: created at 2020/6/8 10:46
*@Description: com.hl.lib_pop.view.taskstate
*/
public class TaskFull extends DialogFragment {
    private AppCompatTextView dftf_taskName;
    private AppCompatImageView dftf_taskPic;

    private static TaskFull instance;
    public static TaskFull getInstance() {
        if (null == instance) {
            synchronized (TaskFull.class){
                instance = new TaskFull();
            }
        }
        return instance;
    }
    private TaskFull(){}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //设置背景透明，才能显示出layout中诸如圆角的布局，否则会有白色底（框）
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
    }

    /**
     * 普通的弹窗，也可以getLayoutInflater.inflate加载AlertDialog.Builder、Dialog
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
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
        View view = inflater.inflate(R.layout.dialog_fragment_taskfull, null);

        dftf_taskName =  view.findViewById(R.id.dftf_taskName);
        dftf_taskPic =  view.findViewById(R.id.dftf_taskPic);

        // 点击事件
        AppCompatImageView dft_closeTaskPop = view.findViewById(R.id.dftf_closeTaskPop);
        dft_closeTaskPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != instance) {
                    instance.dismiss();
                }
            }
        });
        MaterialButton dft_taskGet = view.findViewById(R.id.dftf_taskGet);
        dft_taskGet.setOnClickListener(new View.OnClickListener() {
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
            dialog.getWindow().setLayout(ScreenUtil.SCREEN_WIDTH * 29 / 37,-2 );
        }
    }

    /**
     * 防止多次被add
     */
    public void showSafely(@NonNull FragmentManager fm, @Nullable String tag){
        if (this.isAdded() || null != fm.findFragmentByTag( tag )) {
            return;
        }
        show(fm, tag);
    }

    /**
     * 设置产品信息
     * @param name
     * @param picUrl
     */
    public void setTaskInfo(String name, String picUrl){
        dftf_taskName.setText(name);
        GlideUtil.initImageWithFileCache(requireContext(),
                picUrl,
                dftf_taskPic);
    }
}
