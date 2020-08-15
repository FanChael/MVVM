package com.hl.module_productdetail.view;

import android.content.Context;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.nukc.stateview.StateView;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceActivity;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.viewmodel.SelfViewModelFactory;
import com.hl.module_productdetail.R;
import com.hl.module_productdetail.databinding.ActivityProductDetialBinding;
import com.hl.module_productdetail.model.bean.ChallengeSuccessBean;
import com.hl.module_productdetail.model.bean.TaskDetailBean;
import com.hl.module_productdetail.view.event.ProductDetailEventHandler;
import com.hl.module_productdetail.viewmodel.ProductDetailViewModel;

@Route(path = ArouterPath.PRODUCT_DETAIL)
public class ProductDetialActivity extends BaseWithServiceActivity<ActivityProductDetialBinding> {
    @Autowired()
    public int taskId; // 任务id

    public ProductDetailViewModel productDetailViewModel;

    @Override
    public int setLayout() {
        return R.layout.activity_product_detial;
    }

    @Override
    public void initLayout(Context context) {
        // 顶部间距设置一下
        ScreenUtil.setStatusBarHeightTop(getViewDataBinding().apdTitleBar);
    }

    @Override
    public void requestData(Context context) {
        // 自定义ModelFactory创建ViewModel
        productDetailViewModel = new ViewModelProvider(this, new SelfViewModelFactory(baseControlPresenter)).get(ProductDetailViewModel.class);
        // 监听列表数据变化
        productDetailViewModel.getSuccessBeanMutableLiveData().observe(this, new Observer<ChallengeSuccessBean>() {
            @Override
            public void onChanged(ChallengeSuccessBean challengeSuccessBean) {
                getViewDataBinding().getProductEvent().toAttenSuccessPage(challengeSuccessBean);
            }
        });
        productDetailViewModel.getDetailBeanMutableLiveData().observe(this, new Observer<TaskDetailBean>() {
            @Override
            public void onChanged(TaskDetailBean taskDetailBean) {
                // 播放换一个视频看看 "http://ts7.sztv.com.cn/szmg/vod/2020/04/17/da36d035ab394d1295deb39eca12d678/h264_1200k_mp4.mp4"
                getViewDataBinding().apdVideoLayout
                        .initMediaSource(ProductDetialActivity.this,
                                taskDetailBean.getProductList().get(0).getVideo_url());
            }
        });
        getViewDataBinding().getProductEvent().getTastDetail(taskId);
    }

    @Override
    public void eventHandler(Context context) {
        // 注册事件对象
        if (null == getViewDataBinding().getProductEvent()) {
            getViewDataBinding().setProductEvent(new ProductDetailEventHandler(this));
        }
        // 详情重试
        super.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                getViewDataBinding().getProductEvent().getTastDetail(taskId);
            }
        });
    }

    @Override
    public void onSucess(String _functionName, Object t) {
        if (t instanceof ChallengeSuccessBean){
            productDetailViewModel
                    .getSuccessBeanMutableLiveData()
                    .setValue((ChallengeSuccessBean) t);
        } else if (t instanceof TaskDetailBean) {
            productDetailViewModel
                    .getDetailBeanMutableLiveData()
                    .setValue((TaskDetailBean) t);
        }
    }

    @Override
    public void onFailed(String _functionName, String _message) {
        // TODO 测试数据
        if (_functionName.contains("v1/task/info")) {
            disDialog();
            // 播放换一个视频看看 "http://ts7.sztv.com.cn/szmg/vod/2020/04/17/da36d035ab394d1295deb39eca12d678/h264_1200k_mp4.mp4"
            getViewDataBinding().apdVideoLayout
                    .initMediaSource(ProductDetialActivity.this,
                            "http://ts7.sztv.com.cn/szmg/vod/2020/04/17/da36d035ab394d1295deb39eca12d678/h264_1200k_mp4.mp4");
        }
    }
}
