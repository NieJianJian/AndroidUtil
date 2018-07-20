package njj.androidutil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private Button mCreateBtn, mPermissionBtn, mSettginsBtn;
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
                        .isShowDialog(true)
                        .addPermission(
                                Manifest.permission.CALL_PHONE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.CAMERA)
                        .isShowDialog(true)
                        .setAgainMsg("dfadsfdsfdsfdsf")
                        .request(new PermissionCallback() {

                            @Override
                            public void onFinish(boolean isFinish) {
                                Log.i("niejianjian", " -> onFinish -> " + isFinish);
                            }

                            @Override
                            public void onCancel(List<String> denyPermissions) {
                                Log.i("niejianjian", " -> onCancel -> ");
                            }

                            @Override
                            public void onDeny(boolean isCheck, List<String> denyPermissions) {
                                Log.i("niejianjian", " -> onDeny -> " + isCheck);
                            }
                        });
                break;
            case R.id.settings_btn:
                Uri packageURI = Uri.parse("package:" + getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                startActivity(intent);
                break;
        }
    }
}
