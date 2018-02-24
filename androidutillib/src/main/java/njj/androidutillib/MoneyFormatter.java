package njj.androidutillib;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 价格 金钱 格式化工具
 * Created by WangWei on 2015/11/9.
 */
public class MoneyFormatter {

    private static NumberFormat getFormatWithoutSign() {
        final NumberFormat format = NumberFormat.getNumberInstance();
        format.setRoundingMode(RoundingMode.FLOOR);
        return format;
    }

    /**
     * 格式化显示钱数
     *
     * @return 输入 12312 输出 12,312
     */
    public static String formatMoney(int money) {
        return getFormatWithoutSign().format(money);
    }

    /**
     * 格式化显示钱数
     *
     * @return 输入 324444.234 输出 324,444
     */
    // TODO: 2016/8/4  由于format没有设置小数位个数，实际：输入 324444.234 输出 324,444.234
    public static String formatMoney(double money) {
        return getFormatWithoutSign().format(money);
    }

    /**
     * 格式化显示钱数
     *
     * @return 输入 324444.234 输出 324,444
     */
    // TODO: 2016/8/4  由于format没有设置小数位个数，实际：输入 324444.234 输出 324,444.234
    public static String formatMoney(BigDecimal money) {
        return getFormatWithoutSign().format(money);
    }

    /**
     * 格式化需要显示小数的钱数
     *
     * @param money 123123.234234234234
     * @return 123, 123.23
     */
    public static String formatDecimalMoney(double money) {
        final NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(money);
    }

    /**
     * 格式化需要显示小数的钱数---四舍五入
     *
     * @param money 123123.234234234234
     * @return 123, 123.23
     */
    public static String formatDecimalHalfMoney(double money) {
        final NumberFormat format = NumberFormat.getNumberInstance();
        if (money < 0.01) return "0.01";
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.HALF_UP);
        return format.format(money);
    }

    /**
     * 格式化需要显示小数的钱数
     *
     * @param money 123123.234234234234
     * @return 123, 123.23
     */
    public static String formatDecimalMoney(BigDecimal money) {
        final NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(2);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(money);
    }

    /**
     * 格式化，给double类型的整数增加两位小数
     */
    public static String formatDecimalAddMoney(double money) {
        final DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0.00");
        return decimalFormat.format(money);
    }

    /**
     * 最少保留两位小数
     *
     * @param money
     * @return
     */
    public static String formatDecimalAddMoney1(double money) {
        final NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        format.setRoundingMode(RoundingMode.FLOOR);
        return format.format(money);
    }
}
