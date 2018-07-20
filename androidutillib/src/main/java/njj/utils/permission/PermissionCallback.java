package njj.utils.permission;

import java.util.List;

/**
 * Created by niejianjian on 2018/7/17.
 */

public interface PermissionCallback {

    /**
     * @param isFinish true表示，经过用户选择而完成，false表示，所申请的权限已经是允许的
     */
    void onFinish(boolean isFinish);

    /**
     * @param denyPermissions 禁止的权限
     */
    void onCancel(List<String> denyPermissions);

    /**
     * 禁止所有权限或者部分权限后，回调onDeny函数
     *
     * @param isCheck         是否部分或者全部权限，存在勾选了“禁止后不再询问选框” true为存在，false为不存在
     * @param denyPermissions 禁止的权限
     */
    void onDeny(boolean isCheck, List<String> denyPermissions);
}
