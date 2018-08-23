package hssc.androidview.utils;

import android.util.Log;

import java.math.BigDecimal;

/**
 * Created by li on 2017/7/31.
 */
public class Arith {
    /**
     * 3      * 提供精确加法计算的add方法
     * 4      * @param value1 被加数
     * 5      * @param value2 加数
     * 6      * @return 两个参数的和
     * 7
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1)+"");
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2)+"");
        return b1.add(b2).doubleValue();
    }

    /**
     * 15      * 提供精确减法运算的sub方法
     * 16      * @param value1 被减数
     * 17      * @param value2 减数
     * 18      * @return 两个参数的差
     * 19
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1)+"");
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2)+"");
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 27      * 提供精确乘法运算的mul方法
     * 28      * @param value1 被乘数
     * 29      * @param value2 乘数
     * 30      * @return 两个参数的积
     * 31
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1)+"");
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2)+"");
        return b1.multiply(b2).doubleValue();
    }
    /**
     * 39      * 提供精确的除法运算方法div
     * 40      * @param value1 被除数
     * 41      * @param value2 除数
     * 42      * @param scale 精确范围
     * 43      * @return 两个参数的商
     * 44      * @throws IllegalAccessException
     * 45
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        //如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1)+"");
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2)+"");
        return b1.divide(b2, scale).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v,int scale){
        if(scale<0){
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one,scale,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的类型转换(Float)
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static float convertsToFloat(double v){
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static int convertsToInt(double v){
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * 提供精确的类型转换(Long)
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public static long convertsToLong(double v){
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * 返回两个数中大的一个值
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public static double returnMax(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * 返回两个数中小的一个值
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public static double returnMin(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * 精确对比两个数字
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1,double v2){
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }
}
