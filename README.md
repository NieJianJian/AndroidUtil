# AndroidUtil
Android的工具类

# 使用方式

Gradle：

    compile 'njj.utils:androidutillib:1.0.7'

Maven：

    <dependency>
      <groupId>njj.utils</groupId>
      <artifactId>androidutillib</artifactId>
      <version>1.0.5</version>
      <type>pom</type>
    </dependency>


**Android运行时权限申请使用方式：**

1.最简单的接入方式（Activity的onCreate中调用）
        
        ReqPermisson.init(this)
                .addPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                .request();

2.个性化配置（Activity的onCreate中调用）
        
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
                


**屏幕适配引用方案：**

1.在Application中进行初始化
    
     public class MyApplication extends Application {
        @Override
        public void onCreate() {
            super.onCreate();
            Density.setDensity(this);
            // Density.setSize(375f); // 设置基础宽高，如设计图是720*1280，density=2.0，对应的基础宽就是360f，不设置默认是360f
            // Density.setSize(360f, 640f); // 也可以同时设置基础宽和高。当以高为基准进行适配时，要用到，默认是640f
        }
     }
    
2.使用方式 （Activity的onCreate中调用）

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Density.setDefault(this); // 要在setContentView()之前调用
            // 默认是以宽为基础，进行适配，如果有需要根据高为基础进行适配,改为调用下面的代码。
            // Density.setOrientation(this, Density.ORI_HEIGHT);
            setContentView(R.layout.activity_second);
        }

3.优化调用，可以创建BaseActivity，完成调用

    public class BaseActivity extends Activity {
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setOrientation();
            if (getLayout() != 0) {
                setContentView(getLayout());
            }
        }

        protected abstract int getLayout();

        /**
        * 如果改为以高度为基础进行适配，子类自行重写该方法，调用：
        * Density.setOrientation(this, Density.ORI_HEIGHT);
        */
        public void setOrientation() {
            Density.setDefault(this);
        }
    }

4.dimens.xml文件

https://github.com/NieJianJian/AndroidUtil/tree/master/app/src/main/res/values

5.xml布局文件中的使用方式

根据设计图设置，如果一个View设计图给出的宽高分别是100px * 50px，字体大小为28px，布局文件设置如下：
    
    <TextView
        android:layout_width="@dimen/dimen_100px"
        android:layout_height="@dimen/dimen_50px"
        android:textSize="@dimen/tvSize_28px"/>
