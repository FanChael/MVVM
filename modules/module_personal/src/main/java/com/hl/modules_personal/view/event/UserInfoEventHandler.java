package com.hl.modules_personal.view.event;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.hl.base_module.handler.BaseHandlers;
import com.hl.lib_pop.view.photo.PhotoOrTakepicPop;
import com.hl.modules_personal.databinding.ActivityUserInfoBinding;
import com.hl.modules_personal.view.activity.UserInfoActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 点击事件统一到一个类
 *
 * @Author: hl
 * @Date: created at 2020/3/27 10:43
 * @Description: com.hl.modules_personal.view.event
 */
public class UserInfoEventHandler extends BaseHandlers<UserInfoActivity, ActivityUserInfoBinding> {
    public UserInfoEventHandler(UserInfoActivity userInfoActivity) {
        super(userInfoActivity, userInfoActivity.getViewDataBinding());
    }

    /**
     * 更换头像
     */
    public void changeHeader() {
        PhotoOrTakepicPop.getInstance()
                .enableCrop(new int[]{200, 200})
                .setResultCallBack(new PhotoOrTakepicPop.ResultCallBack() {
                    @Override
                    public void from(PhotoOrTakepicPop.PHOTO_TYPE photo_type, Uri photo) {
                        try {
                            String picPath = new File(
                                    new URI(photo.toString())).getAbsolutePath();
                            Bitmap bitmap = BitmapFactory.decodeStream(
                                    getContext().getContentResolver().openInputStream(photo));
                            // 上传头像，成功之后刷新，这里简单本地测试下效果
                            getBindingView().auiHeaderImg.setImageBitmap(bitmap);
                            Log.e("test picPath=", picPath);
                            Log.e("test bitmap=", "" + bitmap);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .showSafely(getBindedView().getSupportFragmentManager(), "更新头像");
    }
}