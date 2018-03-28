package njj.androidutil;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import njj.androidutillib.CreateDimensUtil;

public class MainActivity extends Activity implements View.OnClickListener {

    private EditText mSizeEt, mDensityEt;
    private Button mCreateBtn;
    private TextView mWidthHeightTv, mDensityTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSizeEt = (EditText) findViewById(R.id.size_et);
        mDensityEt = (EditText) findViewById(R.id.density_et);
        mCreateBtn = (Button) findViewById(R.id.create_btn);
        mCreateBtn.setOnClickListener(this);
        mWidthHeightTv = (TextView) findViewById(R.id.width_height_tv);
        mDensityTv = (TextView) findViewById(R.id.density_tv);

        init();
    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mWidthHeightTv.setText("Width * Height ：" + metrics.widthPixels + " * " + metrics.heightPixels);
        mDensityTv.setText("Density ：" + metrics.density);
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
        }
    }
}
