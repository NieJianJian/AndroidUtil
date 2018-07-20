# AndroidUtil
Android的工具类

# 使用方式

Gradle：

    compile 'njj.utils:androidutillib:1.0.2'

Maven：

    <dependency>
      <groupId>njj.utils</groupId>
      <artifactId>androidutillib</artifactId>
      <version>1.0.2</version>
      <type>pom</type>
    </dependency>


Android运行时权限申请使用方式：

1.最简单的接入方式
        
        ReqPermisson.init(this)
                .addPermission(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.CAMERA)
                .request();

2.个性化配置
        
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