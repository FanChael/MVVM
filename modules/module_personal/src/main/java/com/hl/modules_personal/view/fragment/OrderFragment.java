package com.hl.modules_personal.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hl.base_module.adapter.BaseMutilayoutAdapter;
import com.hl.base_module.constant.ArouterPath;
import com.hl.base_module.page.BaseWithServiceFragment;
import com.hl.modules_personal.R;
import com.hl.modules_personal.databinding.FragmentOrderBinding;
import com.hl.modules_personal.model.bean.OrderItemBean;
import com.hl.modules_personal.view.adapter.OrderListAdatper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends BaseWithServiceFragment<FragmentOrderBinding> {
    private OrderListAdatper orderListAdatper;
    private List<OrderItemBean> orderItemBeanList = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int setLayout() {
        return R.layout.fragment_order;
    }

    @Override
    public void initLayout(Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        getViewDataBinding().foOrderListRv.setLayoutManager(linearLayoutManager);

        orderItemBeanList.add(new OrderItemBean());
        orderItemBeanList.add(new OrderItemBean());
        orderItemBeanList.add(new OrderItemBean());
        orderItemBeanList.add(new OrderItemBean());
        orderItemBeanList.add(new OrderItemBean());
        orderListAdatper = new OrderListAdatper(requireContext(), orderItemBeanList);
        getViewDataBinding().foOrderListRv.setAdapter(orderListAdatper);
    }

    @Override
    public void requestData(Context context) {
        // 测试空视图等
        //        if (mParam1.contains("all")) {
        //            showDialog();
        //        } else if (mParam1.contains("step")) {
        //            emptyDialog("空空如也 ~ 您还没有奖品哦");
        //        } else if (mParam1.contains("active")) {
        //            retryDialog();
        //        }
        //        super.setOnRetryClickListener(new OnRetryListener() {
        //            @Override
        //            public void onRetryClick() {
        //                showDialog();
        //            }
        //        });
    }

    @Override
    public void eventHandler(Context context) {
        orderListAdatper.setOnItemClickListener(new BaseMutilayoutAdapter.OnItemClickListener<OrderItemBean>() {
            @Override
            public void onClick(View v, int position, OrderItemBean orderItemBean, int itemViewType, Object externParams) {
                String functionType = (String) externParams;
                switch (functionType) {
                    case "修改收货地址":
                        break;
                    case "催促发货":
                        break;
                    case "立即评价":
                        break;
                    default:
                        ARouter.getInstance()
                                .build(ArouterPath.USER_ODERINFO_PAGE)
                                .withInt("order_id", 1)
                                //.setTag(new LoginNavigationCallbackImpl("login"))
                                .navigation();
                        break;
                }
            }
        });
    }

    @Override
    public void onSucess(String _functionName, Object t) {

    }
}
