package njj.utils;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 回调同步工具类 全部条件达到 会触发action
 * Created by jian on 2016/10/13.
 */
public class Barrier {

    private volatile boolean hasFinished = false;
    Runnable mAction;
    private final Map<String, Boolean> mConditions = new HashMap<>();

    public Barrier(Runnable action, @NonNull String[] conditions) {
        mAction = action;

        // 将所有的条件遍历出来，当作key，保存到map，value为false，表示未完成条件
        for (String condition : conditions) {
            mConditions.put(condition, false);
        }
    }

    /**
     * 某个条件满足的回调
     * 全部条件满足时会调用 action
     */
    public void onConditionSatisfied(String condition) {
        synchronized (this) {
            if (hasFinished) return;

            // 将满足条件的value置成true
            mConditions.put(condition, true);
            // 如果所有条件的value都没有false，说明都完成了，就可以再执行了
            if (!mConditions.values().contains(false)) {
                hasFinished = true;
                mAction.run();
            }
        }
    }


}
