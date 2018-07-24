package njj.androidutil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import njj.utils.CreateDimensUtil;
import njj.utils.permission.PermissionCallback;
import njj.utils.permission.ReqPermisson;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText mSizeEt, mDensityEt;
    private Button mCreateBtn, mPermissionBtn, mSettginsBtn, mSecondBtn;
    private TextView mWidthHeightTv, mDensityTv, mUseTv;
    private final int STANDARD = 1080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(1024, 1024);

        setContentView(R.layout.activity_main);

        mSizeEt = (EditText) findViewById(R.id.size_et);
        mDensityEt = (EditText) findViewById(R.id.density_et);
        mCreateBtn = (Button) findViewById(R.id.create_btn);
        mCreateBtn.setOnClickListener(this);
        mWidthHeightTv = (TextView) findViewById(R.id.width_height_tv);
        mDensityTv = (TextView) findViewById(R.id.density_tv);
        mUseTv = (TextView) findViewById(R.id.use_tv);
        mPermissionBtn = (Button) findViewById(R.id.permission_btn);
        mPermissionBtn.setOnClickListener(this);
        mSettginsBtn = (Button) findViewById(R.id.settings_btn);
        mSettginsBtn.setOnClickListener(this);
        mSecondBtn = (Button) findViewById(R.id.second_btn);
        mSecondBtn.setOnClickListener(this);

        init();
    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mWidthHeightTv.setText("Width * Height ：" + metrics.widthPixels + " * " + metrics.heightPixels);
        mDensityTv.setText("Density ：" + metrics.density);
        mUseTv.setText("建议使用值 ：" + calculateValue(metrics));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        }
    }

    private float calculateValue(DisplayMetrics metrics) {
        int minValue = Math.min(metrics.widthPixels, metrics.heightPixels);
        float ratioValue = (float) STANDARD / (float) minValue;
        return ratioValue * metrics.density;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_btn:
                String length = mSizeEt.getText().toString();
                String density = mDensityEt.getText().toString();
                boolean result = CreateDimensUtil.createDeimensXml(
                        length.isEmpty() ? 1920 : Integer.valueOf(length),
                        density.isEmpty() ? 2.0f : Float.valueOf(density),
                        null);
                Toast.makeText(getApplicationContext(), result ? "成功" : "失败", Toast.LENGTH_SHORT).show();
                break;
            case R.id.permission_btn:
                ReqPermisson.init(this)
                        .isShowDialog(true) // 不设置默认为true
                        .addPermission(
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                        .setAgainMsg("禁止相关权限将导致应用无法正常使用") // 自定义禁止授予权限dialog的弹窗提示信息。（未勾选下次不在询问）
                        .setSettingMsg("禁止相关权限将导致应用无法正常使用") // 自定义禁止授予权限dialog的弹窗提示信息。（勾选下次不在询问）
                        .request(new PermissionCallback() {

                            @Override
                            public void onFinish(boolean isFinish) {
                                // 权限申请全部完成，相关处理
                            }

                            @Override
                            public void onCancel(List<String> denyPermissions) {
                                // 权限申请未全部授予，返回参数为拒绝的相应权限。一般在提示弹窗，点击取消后调用。
                            }

                            @Override
                            public void onDeny(boolean isCheck, List<String> denyPermissions) {
                                // 权限申请未全部授予，一定会调用，可联合isShowDialog(false)，去掉默认弹窗后，自定义逻辑处理
                            }
                        });
                break;
            case R.id.settings_btn:
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                startActivity(intent);
                break;
            case R.id.second_btn:
                Intent intent1 = new Intent(this, SecondActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
