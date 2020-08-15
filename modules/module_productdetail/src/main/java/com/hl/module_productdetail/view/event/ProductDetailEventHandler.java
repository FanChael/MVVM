package com.hl.module_productdetail.view.event;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.handler.BaseHandlers;
import com.hl.lib_media.view.VideoLayout;
import com.hl.module_productdetail.databinding.ActivityProductDetialBinding;
import com.hl.module_productdetail.model.bean.ChallengeSuccessBean;
import com.hl.module_productdetail.view.ProductDetialActivity;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class ProductDetailEventHandler extends BaseHandlers<ProductDetialActivity, ActivityProductDetialBinding> {
    private boolean bPlay = true;

    public ProductDetailEventHandler(ProductDetialActivity productDetialActivity) {
        super(productDetialActivity, productDetialActivity.getViewDataBinding());
        // 触摸控制播放暂停
        getBindingView().apdVideoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBindingView().apdVideoLayout.playOrPause(bPlay = !bPlay);
            }
        });
        // 播放状态监听
        getBindingView().apdVideoLayout.addListenner(new VideoLayout.OnplayState() {
            @Override
            public void onPlay() {
                getBindingView().apdVideoPlay.setVisibility(View.GONE);
            }

            @Override
            public void onPause() {
                getBindingView().apdVideoPlay.setVisibility(View.VISIBLE);
            }

            @Override
            public void onComplete() {
                bPlay = false;
                getBindingView().apdVideoPlay.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 播放暂停
     *
     * @param view
     */
    public void play(View view) {
        getBindingView().apdVideoLayout.playOrPause(bPlay = !bPlay);
    }

    /**
     * 立即挑战
     */
    public void challengeNow() {
        getBindedView().productDetailViewModel.joinTask(getBindedView().taskId);
    }

    /**
     * 挑战成功跳转到成功页面
     * @param challengeSuccessBean
     */
    public void toAttenSuccessPage(ChallengeSuccessBean challengeSuccessBean) {
        // 跳转到挑战成功或者已经参加过挑战的页面
        ARouter.getInstance()
                .build(ArouterPath.ATTEND_SUCESS)
                .navigation();
    }

    /**
     * 获取任务ID
     * @param taskId
     */
    public void getTastDetail(int taskId){
        getBindedView().productDetailViewModel.getTaskDetail(taskId);
    }
}
