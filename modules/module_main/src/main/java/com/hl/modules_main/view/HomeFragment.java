package com.hl.modules_main.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.CommonApi;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.message.MessageEvent;
import com.hl.base_module.page.BaseWithServiceFragment;
import com.hl.base_module.util.screen.DensityUtil;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.viewmodel.SelfViewModelFactory;
import com.hl.lib_pop.view.taskstate.TaskFull;
import com.hl.modules_main.R;
import com.hl.modules_main.databinding.FragmentHomeBinding;
import com.hl.modules_main.model.bean.Home_Step_State_Bean;
import com.hl.modules_main.model.bean.Home_Task_Item_Bean;
import com.hl.modules_main.view.adapter.HomeAdatper;
import com.hl.modules_main.view.event.HomeEventHandler;
import com.hl.modules_main.viewmodel.HomeViewModel;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.today.step.liyu.ISportStepInterface;
import com.today.step.liyu.TodayStepManager;
import com.today.step.liyu.TodayStepService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Route(path = ArouterPath.HOME_FRAGMENT)
public class HomeFragment extends BaseWithServiceFragment<FragmentHomeBinding> {
    // 碎片databinding一把
    private FragmentHomeBinding fragmentHomeBinding;
    private HomeAdatper homeAdatper;
    private List<Home_Task_Item_Bean> rvBeanList = new ArrayList<>();

    public HomeViewModel homeViewModel;
    private HomeEventHandler homeEventHandler;

    // 计步相关
    //循环取当前时刻的步数中间的间隔时间
    private static final int REFRESH_STEP_WHAT = 0;
    private long TIME_INTERVAL_REFRESH = 500;
    private int mStepSum;
    private int incrementStepSum = 0;
    private ISportStepInterface iSportStepInterface;
    private Handler mDelayHandler = new Handler(new TodayStepCounterCall());

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected SmartRefreshLayout getSmartRefreshLayout() {
        return fragmentHomeBinding.fhSmartRefresh;
    }

    @Override
    public void initLayout(Context context) {
        EventBus.getDefault().register(this);

        fragmentHomeBinding = getViewDataBinding();
        // 设置顶部控件距离顶部高度
        ScreenUtil.setStatusBarHeightTop(fragmentHomeBinding.fhWalkTitle);
        ScreenUtil.setLinearLayoutWHNoRatio(fragmentHomeBinding.fhBvp2,
                ScreenUtil.SCREEN_WIDTH - DensityUtil.dip2px(requireContext(), 30),
                (ScreenUtil.SCREEN_WIDTH - DensityUtil.dip2px(requireContext(), 30)) * 70 / 345);

        // 初始化列表
        homeAdatper = new HomeAdatper(requireContext(), rvBeanList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //        fragmentHomeBinding.fhHomeListRv.addItemDecoration(new RecycleViewDivider(
        //                context,
        //                LinearLayoutManager.VERTICAL,
        //                3, R.color.little_gray));
        fragmentHomeBinding.fhHomeListRv.setLayoutManager(linearLayoutManager);
        fragmentHomeBinding.fhHomeListRv.setAdapter(homeAdatper);
        // 默认先隐藏banner组件，等请求成功后再显示
        fragmentHomeBinding.fhBvp2.setVisibility(View.GONE);

        /**https://blog.csdn.net/codezjx/article/details/45314925
         那么， 什么情况下既使用start Service， 又使用bind Service呢?
         如果你只是想要启动一个后台服务长期进行某项任务， 那么使用start Service便可以了， 如果你还想要与正在运行的Service取得联系， 那么
         有两种方法：一种是使用broadcast， 另一种是使用bind Service。前者的缺点是如果交流较为频繁， 容易造成性能上的问题， 而后者则没有这
         些问题。因此， 这种情况就需要start Service和bind Service一起使用了。
         另外， 如果你的服务只是公开一个远程接口， 供连接上的客户端(Android的Service是C/S架构) 远程调用执行方法， 这个时候你可以不让
         服务一开始就运行， 而只是bind Service， 这样在第一次bind Service的时候才会创建服务的实例运行它， 这会节约很多系统资源， 特别是如果
         你的服务是远程服务， 那么效果会越明显(当然在Servcie创建的是偶会花去一定时间， 这点需要注意) 。
         ***/
        //初始化计步模块
        TodayStepManager.startTodayStepService(CommonApi.getApplication());
        // 开启计步，同时绑定aidl通信
        Intent intent = new Intent(requireContext(), TodayStepService.class);
        requireContext().startService(intent);
        // @1 首次获取当天记步数总数
        requireContext().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //Activity和Service通过aidl进行通信
                iSportStepInterface = ISportStepInterface.Stub.asInterface(service);
                try {
                    mStepSum = iSportStepInterface.getCurrentTimeSportStep();
                    updateStepCount(mStepSum);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
        // 背景位移
        getViewDataBinding().fhWalkBg.startAnimation(50);
    }

    @Override
    public void requestData(Context context) {
        // 自定义ModelFactory创建ViewModel
        homeViewModel = new ViewModelProvider(this, new SelfViewModelFactory(baseControlPresenter)).get(HomeViewModel.class);
        // 监听列表数据变化
        homeViewModel.getHomeLd().observe(this, new Observer<List<Home_Task_Item_Bean>>() {
            @Override
            public void onChanged(List<Home_Task_Item_Bean> datasBean) {
                if (1 == current_page) {
                    fragmentHomeBinding.fhSmartRefresh.finishRefresh();
                    rvBeanList.clear();
                    rvBeanList.addAll(datasBean);
                    homeAdatper.notifyDataSetChanged();
                } else {
                    fragmentHomeBinding.fhSmartRefresh.finishLoadMore();
                    rvBeanList.addAll(datasBean);
                    homeAdatper.notifyDataSetChanged();
                }
            }
        });
        // 请求首页数据
        homeEventHandler.getHomeListData((current_page = 1), true);
    }

    @Override
    public void eventHandler(Context context) {
        // 注册事件对象
        if (null == fragmentHomeBinding.getClickHandler()) {
            fragmentHomeBinding.setClickHandler(homeEventHandler = new HomeEventHandler(this));
        }
        fragmentHomeBinding.fhSmartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                homeEventHandler.getHomeListData(++current_page, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homeEventHandler.getHomeListData((current_page = 1), false);
            }
        });
        // 点击跳转到浏览器页面
        homeAdatper.setOnItemClickListener(new BaseMutilayoutAdapter.OnItemClickListener<Home_Task_Item_Bean>() {
            @Override
            public void onClick(View v, int position, Home_Task_Item_Bean datasBean, int itemViewType, Object externParams) {
                // Bundle bundle = new Bundle();
                //bundle.putString("html", datasBean.getLink());
                //                Intent intent = new Intent(requireContext(), WebviewActivity.class);
                //                intent.putExtras(bundle);
                //                requireContext().startActivity(intent);
                // 跳转到挑战页面
                ARouter.getInstance()
                        .build(ArouterPath.PRODUCT_DETAIL)
                        .withInt("taskId", datasBean.getTaskId())
                        .navigation();
            }
        });
        // 重试事件
        super.setOnRetryClickListener(new OnRetryListener() {
            @Override
            public void onRetryClick() {
                // 请求首页数据
                homeEventHandler.getHomeListData((current_page = 1), true);
            }
        });
    }

    /**
     * 收到刷新
     *
     * @param messageEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void studentEventBus(MessageEvent messageEvent) {
        if (messageEvent.getObject().equals("update_home")) {
            fragmentHomeBinding.fhSmartRefresh.autoRefresh();
        }
    }

    @Override
    public void onFailed(String _functionName, String _message) {
        super.onFailed(_functionName, _message);
        // TODO 其他数据展示(轮播，进度等)
        disDialog();
        testData(null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSucess(String _functionName, Object t) {
        if (_functionName.contains("v1/task/list")) {
            List<Home_Task_Item_Bean> homeBean = (List<Home_Task_Item_Bean>) t;
            homeViewModel.getHomeLd().setValue(homeBean);
            // TODO 其他数据展示(轮播，进度等)
            testData(homeBean);
        } else if (_functionName.contains("v1/step/upload")) {
            List<Home_Step_State_Bean> home_step_state_beans =
                    (List<Home_Step_State_Bean>) t;
            showToast("上传步数成功:" + home_step_state_beans.size());
        }
    }

    /**
     * TODO 测试数据
     */
    private void testData(List<Home_Task_Item_Bean> homeBean){
        List<Home_Task_Item_Bean> home_task_item_beans = homeBean;
        if (null == home_task_item_beans) {
            home_task_item_beans = new ArrayList<>();
        }
        Home_Task_Item_Bean home_task_item_bean = new Home_Task_Item_Bean();
        home_task_item_bean.setPart_num(1000);
        Home_Task_Item_Bean.ProductBean productBean = new Home_Task_Item_Bean.ProductBean();
        productBean.setPd_img("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592283437665&di=74a53cd311efd06735817d03ed9606fa&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg");
        productBean.setPd_name("测试商品");
        productBean.setTarget_step(100000);
        home_task_item_bean.setProduct(productBean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        home_task_item_beans.add(home_task_item_bean);
        homeViewModel.getHomeLd().setValue(home_task_item_beans);
        // 其他测试代码
        {
            // 请求成功再显示banner
            fragmentHomeBinding.fhBvp2.setVisibility(View.VISIBLE);

            // 添加一个Banner
            List<String> imgListB = new ArrayList<>();
            imgListB.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592997330612&di=2fe9189f250092886162302e17bc8dab&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F05%2F23%2F66%2F8259eda7b18e2a4.jpg");
            imgListB.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592997330617&di=8ba4f0cbf4a2dd6d38a6288391fdd1f8&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F03%2F76%2F77%2F9057befb759e5ac.jpg");
            imgListB.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1592997330615&di=7b363fb010a84c5295f1ff5d02d5d9c4&imgtype=0&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F05%2F26%2F63%2F1959ff0d5ab92c2.jpg");
            fragmentHomeBinding.fhBvp2
                    .setData(imgListB)
                    .start(3f);

            Map<String, String> musicList = new HashMap<>();
            musicList.put("不知道叫啥1", "http://music.163.com/song/media/outer/url?id=447925558.mp3");
            musicList.put("不知道叫啥2", "http://music.163.com/song/media/outer/url?id=1433815945.mp3");
            musicList.put("不知道叫啥3", "http://music.163.com/song/media/outer/url?id=524152260.mp3");
            fragmentHomeBinding.fhMusicRoot.initMusicSource(requireContext(), musicList);

            fragmentHomeBinding.fhGiftProgress.setImage("");
            fragmentHomeBinding.fhGiftProgress.setProgress(0.7f);

            List<String> imgList = new ArrayList<>();
            imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590253928478&di=ba12501ec8003fd5822394b583b45a2f&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F57%2F93%2F24%2F58face867aabf.png");
            imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590253928478&di=ba12501ec8003fd5822394b583b45a2f&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F57%2F93%2F24%2F58face867aabf.png");
            imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590253928478&di=ba12501ec8003fd5822394b583b45a2f&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fdesign%2F00%2F57%2F93%2F24%2F58face867aabf.png");
            fragmentHomeBinding.fhHeaderLisst.setImage(imgList);

            fragmentHomeBinding.fhLotterGift.setNumber("99+");
            fragmentHomeBinding.fhGetGift.setNumber("9+");
        }

        // TODO 任务完成弹窗测试
        //TaskFull taskComplete = TaskFull.getInstance();
        //taskComplete.showSafely(getParentFragmentManager(), "立即领取");
    }

    /**
     * @2 定时不停获取步数
     */
    private class TodayStepCounterCall implements Handler.Callback {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_STEP_WHAT: {
                    //每隔500毫秒获取一次计步数据刷新UI
                    if (null != iSportStepInterface) {
                        int step = 0;
                        try {
                            step = iSportStepInterface.getCurrentTimeSportStep();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        // 步数发生变化，更新界面
                        if (mStepSum != step) {
                            // 步数增量
                            incrementStepSum += (step -  mStepSum);
                            mStepSum = step;
                            // 设备步数发生变化，则通知界面刷新
                            updateStepCount(mStepSum);
                            // 增量步数达到100，则通知后台
                            if (incrementStepSum > 10) {
                                // 统计步数
                                homeEventHandler.updateIncrementStepSum(incrementStepSum);
                            }
                        }
                    }
                    mDelayHandler.sendEmptyMessageDelayed(REFRESH_STEP_WHAT, TIME_INTERVAL_REFRESH);
                    break;
                }
            }
            return false;
        }
    }

    /**
     * 更新步数
     */
    private void updateStepCount(int mStepSum) {
        String mStr = String.valueOf(mStepSum);
        fragmentHomeBinding.fhStepCounter.updateText("今日".concat(mStr).concat("步数"), mStr);
        // 没加载过才加载
        if (!fragmentHomeBinding.fhSignStepInfoLayout.isInflated()) {
            fragmentHomeBinding.fhSignStepInfoLayout.getViewStub().inflate();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mDelayHandler) {
            mDelayHandler.removeMessages(REFRESH_STEP_WHAT);
        }
    }
}
