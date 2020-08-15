package com.hl.lib_pop.view.photo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.hl.base_module.constant.GlobalAppInfo;
import com.hl.base_module.util.screen.ScreenUtil;
import com.hl.base_module.util.system.PermissionUtil;
import com.hl.lib_pop.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

import static android.app.Activity.RESULT_OK;

/**
 * 拍照或者相册选择弹窗
 *
 * @Author: hl
 * @Date: created at 2020/6/8 10:46
 * @Description: com.hl.lib_pop.view.taskstate
 */
public class PhotoOrTakepicPop extends DialogFragment implements EasyPermissions.PermissionCallbacks{
    public static final int REQUEST_IMAGE_OPEN = 1;
    public static final int REQUEST_IMAGE_CAPTURE = 2;
    public static final int REQUEST_IMAGE_CROP = 3;
    private Uri locationForPhotos, cropForPhotos;

    private PHOTO_TYPE photo_type = PHOTO_TYPE.SCIM;
    private WhichPhotoBack whichPhotoBack;
    private ResultCallBack resultCallBack;

    private int[] wh;

    public enum PHOTO_TYPE {
        SCIM, CAMERA
    }

    private static PhotoOrTakepicPop instance;

    public static PhotoOrTakepicPop getInstance() {
        if (null == instance) {
            synchronized (PhotoOrTakepicPop.class) {
                instance = new PhotoOrTakepicPop();
            }
        }
        return instance;
    }

    private PhotoOrTakepicPop() {
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
        View view = inflater.inflate(R.layout.dialog_fragment_photopic, null);

        AppCompatTextView afpp_dcim = view.findViewById(R.id.afpp_dcim);
        AppCompatTextView afpp_camera = view.findViewById(R.id.afpp_camera);
        MaterialButton afpp_cancel = view.findViewById(R.id.afpp_cancel);

        // 消失
        afpp_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != instance) {
                    instance.dismiss();
                }
            }
        });
        afpp_dcim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo_type = PHOTO_TYPE.SCIM;
                if (null != whichPhotoBack) {
                    whichPhotoBack.which(PHOTO_TYPE.SCIM);
                } else {
                    selectImage();
                }
            }
        });
        afpp_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photo_type = PHOTO_TYPE.CAMERA;
                if (null != whichPhotoBack) {
                    whichPhotoBack.which(PHOTO_TYPE.CAMERA);
                } else {
                    // 先判断权限
                    EasyPermissions.requestPermissions(
                            new PermissionRequest.Builder(PhotoOrTakepicPop.this,
                                    PermissionUtil.REQUEST_CAMERA,
                                    PermissionUtil.PERMISSIONS_CAMERA)
                                    .setRationale("应用需要相机权限完成一些操作")
                                    .build());
                    // 权限获取后再调用capturePhoto("ddd_header.png");
                }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        capturePhoto("ddd_header.png");
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("媒体")
                    .setRationale("缺少相机权限，将要跳转到设置界面")
                    .setPositiveButton("同意")
                    .setNegativeButton("取消") // "" 是无法隐藏取消按钮的，必须加空格
                    .setRequestCode(requestCode)
                    .build()
                    .show();
        }
    }

    /**
     * 选择图片
     */
    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, REQUEST_IMAGE_OPEN);
    }

    /**
     * 拍照
     *
     * @param targetFilename
     */
    public void capturePhoto(String targetFilename) {
        File outPic = new File(getActivity().getExternalCacheDir(), targetFilename);
        try {
            if (outPic.exists()) {
                outPic.delete();//删除
            }
            outPic.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationForPhotos = FileProvider.getUriForFile(getActivity(),
                    GlobalAppInfo.FileProvider, outPic);
        } else {
            locationForPhotos = Uri.fromFile(outPic);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, locationForPhotos);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    /**
     * 图片裁剪
     *
     * @param uri
     * @return
     */
    @NonNull
    public void CutForPhoto(Context context, Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
            File cutfile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getPath(),
                    System.currentTimeMillis() + ".png");
            if (cutfile.exists()) {
                cutfile.delete();
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            // @hl Uri cropForPhotos = null; //真实的 uri
            cropForPhotos = Uri.fromFile(cutfile);
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", wh[0]); //200dp
            intent.putExtra("outputY", wh[1]);
            intent.putExtra("scale", true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data", false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (cropForPhotos != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, cropForPhotos);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            // uri 权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivityForResult(intent, REQUEST_IMAGE_CROP);
            // @hl return new Object[]{intent, cropForPhotos};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri resultUri = null;
            if (requestCode == REQUEST_IMAGE_OPEN) {
                resultUri = data.getData();
                // Do work with full size photo saved at fullPhotoUri
                // 如果需要裁剪，则裁剪return，之后才统一返回
                if (null != wh) {
                    CutForPhoto(getContext(), resultUri);
                    return;
                }
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // 由于我们指定了Uri，所以成功后可以直接使用locationForPhotos
                if (null == locationForPhotos) {
                    if(data.hasExtra("data")){
                        Bitmap thumbnail = data.getParcelableExtra("data");
                    }
                } else {
                    // Use: U   ri locationForPhotos;
                    resultUri = locationForPhotos;
                }
                // Do other work with full size photo saved in mLocationForPhotos
                // 如果需要裁剪，则裁剪return，之后才统一返回
                if (null != wh) {
                    CutForPhoto(getContext(), resultUri);
                    return;
                }
            } else if (requestCode == REQUEST_IMAGE_CROP) {
                resultUri = cropForPhotos;
            }
            if (null != resultUri && null != resultCallBack) {
                resultCallBack.from(photo_type, resultUri);
            }
            if (null != instance) {
                instance.dismiss();
            }
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
     * 拍照还是相册
     *
     * @param whichPhotoBack
     * @return
     */
    public PhotoOrTakepicPop setChooseBack(WhichPhotoBack whichPhotoBack) {
        this.whichPhotoBack = whichPhotoBack;
        return this;
    }

    /**
     * 获取图片或者拍照Uri路径
     *
     * @param resultCallBack
     * @return
     */
    public PhotoOrTakepicPop setResultCallBack(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
        return this;
    }

    /**
     * 是否启动裁剪 - 可以传入宽高
     *
     * @param wh
     * @return
     */
    public PhotoOrTakepicPop enableCrop(int[] wh) {
        this.wh = wh;
        return this;
    }

    public interface WhichPhotoBack {
        void which(PHOTO_TYPE photo_type);
    }
    public interface ResultCallBack {
        void from(PHOTO_TYPE photo_type, Uri photo);
    }
}
