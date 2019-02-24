package com.yuntian.basecommon.util;

import com.ibatis.common.beans.Probe;
import com.ibatis.common.beans.ProbeFactory;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import static org.apache.commons.collections.CollectionUtils.EMPTY_COLLECTION;

@SuppressWarnings({"deprecation"})
public class CommonUtil {


    private static final Probe PROBE = ProbeFactory.getProbe();
    @SuppressWarnings("rawtypes")
    public static final Collection NULL_COLLECTION = EMPTY_COLLECTION;

    public static String getIp() {
        StringBuffer sb = new StringBuffer();
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();

                sb.append("DisplayName:" + ni.getDisplayName() + ",");
                sb.append("Name:" + ni.getName() + ",");
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    sb.append("IP:" + ips.nextElement().getHostAddress());
                }
                sb.append("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String moveLastSymbol(String source, String symbol) {
        if (null == source) {
            return null;
        }
        int flag = source.lastIndexOf(symbol);
        if (flag != -1) {
            source = source.substring(0, flag);
        }
        return source;
    }

    public static BufferedImage getHttpImage(String url) {

        URL urlcon = null;
        BufferedImage bi = null;
        try {
            urlcon = new URL(url);
            bi = ImageIO.read(urlcon);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bi;
    }

    /**
     * 将字符串按照编码写入文件
     *
     * @param filePath ：文件路径
     * @param content  :写入的内容
     * @param charset  ：文件编码
     * @return
     */
    public static boolean writeFile(String filePath, String fileName,
                                    String content, String charset) {
        FileOutputStream fops = null;
        OutputStreamWriter os = null;
        boolean sign = false;
        try {
            File file = new File(filePath);
            file.mkdirs();
            File newFile = new File(filePath + fileName);
            fops = new FileOutputStream(newFile);
            os = new OutputStreamWriter(fops, charset);
            os.write(content);
            sign = true;
        } catch (Exception e) {
            e.printStackTrace();
            sign = false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (fops != null) {
                    fops.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sign;
    }

    /**
     * 将字符串按照编码写入文件
     *
     * @param filePath ：文件路径
     * @param content  :写入的内容
     * @param charset  ：文件编码
     * @return
     */
    public static boolean writeFile(String filePath, String fileName,
                                    long lastModified, String content, String charset) {
        FileOutputStream fops = null;
        OutputStreamWriter os = null;
        boolean sign = false;
        try {
            File path = new File(filePath);
            path.mkdirs();

            File _file = new File(filePath + fileName);
            fops = new FileOutputStream(_file);
            os = new OutputStreamWriter(fops, charset);
            os.write(content);
            _file.setLastModified(lastModified);
            sign = true;
        } catch (Exception e) {
            e.printStackTrace();
            sign = false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                if (fops != null) {
                    fops.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sign;
    }

    /**
     * 读入字符文件内容
     *
     * @param filePath ：文件路径
     * @return
     */
    public static String readFile(String filePath, String charset) {
        StringBuilder sb = new StringBuilder("");
        FileInputStream fips = null;
        InputStreamReader in = null;
        try {
            fips = new FileInputStream(filePath);
            in = new InputStreamReader(fips, charset);
            char[] buffer = new char[4 * 1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                sb.append(buffer, 0, len);
            }
            in.close();
            fips.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (fips != null) {
                    fips.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 有效邮件正则表达式$[_a-zA-Z0-9.\-]+@([_a-zA-Z0-9]+\.)+[a-zA-Z0-9]{2,3}
     */
    private static Pattern emailPattern = Pattern
            .compile("[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}");
    /**
     * 有效联系人名称
     */
    private static Pattern contactPattern = Pattern
            .compile("[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+");

    /**
     * 是否有中文字正则表达式
     */
    private static Pattern zhongWenPattern = Pattern.compile("[\u4e00-\u9fa5]");
    /**
     * 手机号正则表达式
     */
    private static Pattern mobilePattern = Pattern
            .compile("(13[0-9]|15[0|1|3|5|6|8|9])\\d{8}");

    /**
     * 数字政策表达式
     */
    private static Pattern numberPattern = Pattern
            .compile("[0-9]{1,}[.][0-9]{1,}|[0-9]{1,}");

    /**
     * 正整数
     */
    private static Pattern positiveNumPattern = Pattern
            .compile("[1-9][0-9]{0,}");


    /**
     * 浮点数，包含负数小数
     */
    private static Pattern decimalsMinusNumPattern = Pattern
            .compile("^(-?\\d+)(\\.\\d+)?$");

    public static String[] domains = {".com.cn", ".net.cn", ".cn", ".com",
            ".net" /** ,".org",".edu",".mil",".gov" */
    };

    /**
     * 密码正则校验
     */
    private static Pattern isPassword = Pattern
            .compile("^(?![a-zA-z]+$)(?!\\d+$)(?![!@#$%^&*(),.]+$)[a-zA-Z\\d/\\\\!@#$%^&*(),.=+-_'|{}:;\"~`]{8,16}$");

    /**
     * 校验字母和数字正则表达式
     */
    private static Pattern lettersAndNumberPattern = Pattern
            .compile("^[A-Za-z0-9]+$");

    /**
     * 校验字母正则表达式
     */
    private static Pattern lettersPattern = Pattern
            .compile("^([A-Za-z])+$");

    /**
     * 校验浮点数，包含负数小数
     *
     * @param val
     * @return
     */
    public static boolean checkNumber(String val) {
        if (val == null) {
            return false;
        }
        Matcher m = decimalsMinusNumPattern.matcher(val);
        return m.matches();
    }

    /**
     * 判断是否包含字母
     *
     * @param userName
     */
    public static boolean isLetters(String userName) {
        Matcher m = lettersPattern.matcher(userName);
        return m.matches();
    }

    /**
     * 判断是否有效邮件
     *
     * @param email
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }

    /**
     * 判断是否有效联系人名称
     *
     * @param contactName
     */
    public static boolean isValidContactName(String contactName) {
        if (contactName == null) {
            return false;
        }
        Matcher m = contactPattern.matcher(contactName);
        return m.matches();
    }

    /**
     * 判断是否是包含数字和字母
     *
     * @param Mobile
     */
    public static boolean isLettersAndNumber(String Mobile) {
        if (Mobile == null) {
            return false;
        }
        Matcher m = lettersAndNumberPattern.matcher(Mobile);
        return m.matches();
    }

    /**
     * 判断是否有效手机号
     *
     * @param mobile
     */
    public static boolean isValidMobile(String mobile) {

        Matcher m = mobilePattern.matcher(mobile);
        return m.matches();
    }

    /**
     * 判断是否有效手机号 ,1开头，校验位数为11为
     *
     * @param mobile
     */
    public static boolean isValidMobileNew(String mobile) {
        String regExp = "^(1)\\d{10}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 给定字符传是否是数字
     *
     * @return
     */
    public static boolean isNumber(String targetStr) {
        Matcher m = numberPattern.matcher(targetStr);
        return m.matches();
    }

    /**
     * 密码正则校验
     *
     * @return
     */
    public static boolean isPassword(String targetStr) {
        Matcher m = isPassword.matcher(targetStr);
        return m.matches();
    }


    /**
     * 给定字符串是否正整数
     *
     * @param targetStr
     * @return
     */
    public static boolean isPositiveNumber(String targetStr) {
        Matcher m = positiveNumPattern.matcher(targetStr);
        return m.matches();
    }

    /**
     * 给定字符串是否为金额类型
     *
     * @param targetStr
     * @return
     */
    public static boolean isMoneyNumber(String targetStr, int floatLen) {
        Pattern moneyPattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0," + floatLen + "})?$");
        Matcher m = moneyPattern.matcher(targetStr);
        return m.matches();
    }

    /**
     * 去掉所有空格
     *
     * @param str
     */
    public static String ignoreSpaces(String str) {

        StringBuffer sb = new StringBuffer("");
        String[] temp = str.split(" ");
        for (int i = 0; i < temp.length; i++) {
            sb.append(temp[i]);
        }
        return sb.toString();
    }

    /**
     * 是否空字符串
     *
     * @param targetStr
     */
    public static boolean isEmpty(String targetStr) {
        return targetStr == null || "".equals(targetStr.trim());
    }

    /**
     * 是否map集合空
     */
    public static boolean isMapEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    /**
     * 是否map集合空
     */
    public static boolean isMapNotEmpty(Map map) {
        return map!=null&&map.size()>0;
    }

    /**
     * 去掉字符串前后的空字符，包括全角的
     *
     * @return
     */
    public static String trim(String str, String Flag) {

        if (str == null || str.equals("")) {
            return str;
        } else {
            str = "" + str;
            if (Flag.equals("l") || Flag.equals("L")) {// 去掉首空格
                String RegularExp = "^[\u00a0|\u0020]+";
                return str.replaceAll(RegularExp, "");
            } else if (Flag.equals("r") || Flag.equals("R")) { // 去掉尾空格
                String RegularExp = "[\u00a0|\u0020]+$";
                return str.replaceAll(RegularExp, "");
            } else { // 去掉首和尾空格
                String RegularExp = "^[\u00a0|\u0020]+|[\u00a0|\u0020]+$";
                return str.replaceAll(RegularExp, "");
            }
        }
    }

    /**
     * 字符串左侧补零
     *
     * @param srcStr
     * @param length 需要
     * @return
     */
    public static String leftFillZero(String srcStr, int length) {
        if (StringUtils.isEmpty(srcStr)) {
            return srcStr;
        }
        String result = srcStr;
        for (int i = 0; i < length - srcStr.length(); i++) {
            result = "0" + result;
        }
        return result;
    }

    /**
     * 精确除法 d1 / d2
     *
     * @param d1
     * @param d2
     * @param scale 保留小数个数
     * @return double
     * @author yuanyuan1
     */
    public static double divide(double d1, double d2, int scale) {
        double result = 0.0;
        if (d2 != 0) {
            BigDecimal b1 = new BigDecimal(d1);
            BigDecimal b2 = new BigDecimal(d2);

            BigDecimal b3 = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP); // 计算结果

            result = b3.doubleValue();
        }

        return result;
    }

    /**
     * 日期转字符串
     *
     * @param date 待转日期
     * @param ds   日期格式
     * @return
     * @author yuanyuan1
     */
    public static String dateToString(Date date, String ds) {
        String dateStr = null;
        if (date != null && ds != null) {
            SimpleDateFormat sf = new SimpleDateFormat(ds);
            dateStr = sf.format(date);
        }
        return dateStr;
    }

    /**
     * 字符转日期
     *
     * @param dateStr
     * @param ds
     * @return
     * @author yuanyuan1
     */
    public static Date stringToDate(String dateStr, String ds) {
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat(ds);
        try {
            date = sf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * 计算两个Date类型的日期相差的天数
     *
     * @param beginDate
     * @param endDate
     * @return
     * @author jinxingshan
     */
    public static int dateGap(Date beginDate, Date endDate) {
        long time = endDate.getTime() - beginDate.getTime();
        int days = (int) (time / (24 * 60 * 60 * 1000)) + 1;

        return days;
    }

    /**
     * @param @param  fDate
     * @param @param  oDate
     * @param @return 设定文件
     * @return int    返回类型
     * @author mengchuang
     * @Description: 计算两个时间相差的天数，不考虑时、分、秒
     * @date 创建时间：2017年7月3日 下午5:18:52
     */
    public static int dateNoTimeGap(Date beginDate, Date endDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(beginDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(endDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 计算某个日期所在的月的天数
     *
     * @param countDate
     * @return
     * @author jinxingshan
     */
    public static int countDaysOfMonth(Date countDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(countDate);
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date beginDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH,
                cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date endDate = cal.getTime();
        int days = dateGap(beginDate, endDate);
        return days;
    }

    /**
     * 将从excel里得到的日期数值转换成Date 这个方法用于从excel得到的日期类型是个从1900-1-1号算起到今天的数字
     * 要转换成java里的Date的情况
     *
     * @param days
     * @return
     */
    public static Date excelDateToDate(long days) {
        Calendar cal = Calendar.getInstance();
        cal.set(1900, 0, 1, 0, 0, 0);
        // 因为Excel是从1算起，而且会错误的认为1900为闰年，所以要减2天
        cal.add(Calendar.DAY_OF_YEAR, 40544 - 2);

        return cal.getTime();
    }

    public static String getNotNullStr(Object obj) {

        return (obj != null) ? obj.toString() : "";
    }

    @SuppressWarnings("unchecked")
    public static final <T> Collection<T> nullCollection() {
        return (List<T>) NULL_COLLECTION;
    }

    /**
     * @param <K>
     * @param <V>
     * @param keys
     * @param source
     * @return
     * @author wumc
     * <p>
     * <pre>
     * 批量获取map中的值
     * </pre>
     */
    public static <K, V> List<V> getAllFormMap(List<K> keys, Map<K, V> source) {
        List<V> values = Collections.emptyList();
        if (keys != null && !keys.isEmpty() && source != null
                && !source.isEmpty()) {
            values = new ArrayList<V>();
            for (K k : keys) {
                values.add(source.get(k));
            }
        }
        return values;
    }

    /**
     * @param <E>
     * @param list
     * @param valueProp
     * @return
     * @author wumc
     * <p>
     * <pre>
     * 从List中获取valueProp组成一个新的list
     * </pre>
     */
    public static <E, K> List<K> getValueList(List<E> list, String valueProp) {
        List<K> valueList = new ArrayList<K>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.removeAll(nullCollection());
            for (E e : list) {
                @SuppressWarnings("unchecked")
                K value = (K) PROBE.getObject(e, valueProp);
                valueList.add(value);
            }
        }
        return valueList;
    }

    /**
     * @param <E>
     * @param list
     * @param valueProp
     * @return
     * @author wumc
     * <p>
     * <pre>
     * 从List中获取不为空值的valueProp组成一个新的list
     * </pre>
     */
    public static <E, K> List<K> getValueListNotNull(List<E> list, String valueProp) {
        List<K> valueList = new ArrayList<K>();
        if (CollectionUtils.isNotEmpty(list)) {
            list.removeAll(nullCollection());
            for (E e : list) {
                @SuppressWarnings("unchecked")
                K value = (K) PROBE.getObject(e, valueProp);
                if (value != null && !"".equals(value.toString().trim())) {
                    valueList.add(value);
                }
            }
        }
        return valueList;
    }

    /**
     * @param <K>
     * @param <V>
     * @param <E>
     * @param list
     * @param keyProp
     * @param valueProp
     * @return
     * @author wumc
     * <p>
     * <pre>
     * 用list生成一个map,keyProp 为指定的key,valueProp 为指定是value
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <K, V, E> Map<K, V> listforMap(List<E> list, String keyProp,
                                                 String valueProp) {

        Map<K, V> rs = new HashMap<>();

        if (CollectionUtils.isNotEmpty(list)) {

            ConcurrentHashMap<K, V> map = new ConcurrentHashMap<K, V>();

            list.removeAll(nullCollection());
            for (E object : list) {
                K key = (K) PROBE.getObject(object, keyProp);
                Object value = null;
                if (StringUtils.isEmpty(valueProp)) {
                    value = object;
                } else {
                    value = PROBE.getObject(object, valueProp);
                }
                if (value != null) {
                    map.put(key, (V) value);
                }
            }

            rs = map;
        }
        return rs;
    }


    /**
     * @param <K>
     * @param <V>
     * @param <E>
     * @param list
     * @param keyProp
     * @param valueProp
     * @return
     * @author wumc
     * <p>
     * <pre>
     * list 生成一个Map<K,List<V>>
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <K, V, E> Map<K, List<V>> listforListMap(List<E> list,
                                                           String keyProp, String valueProp) {
        Map<K, List<V>> map = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(list)) {
            list.removeAll(nullCollection());
            map = new HashMap<K, List<V>>(list.size());
            V value = null;
            for (E object : list) {
                K key = (K) PROBE.getObject(object, keyProp);
                if (StringUtils.isEmpty(valueProp)) {
                    value = (V) object;
                } else {
                    value = (V) PROBE.getObject(object, valueProp);
                }
                List<V> values = map.get(key);
                if (values == null) {
                    values = new ArrayList<V>();
                }
                values.add(value);
                map.put(key, values);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        /*String picUrl = "http://xxx.img.com/xxx/xxxxxx.jpg";
		System.out.println(changePicSize(picUrl, 40));
		System.out.println(changePicSize(picUrl, 360));
		System.out.println(changePicSize(picUrl, 450));
		Map map = CommonUtil.listforMap(null,"id",null);
		System.out.println(map.get(1));*/
        System.out.println(CommonUtil.isPositiveNumber("3720205100"));
    }

    /**
     * Description ： 根据尺寸改变图片链接<br>
     * <p>
     * wumc
     *
     * @param picUrl  图片原始网络链接
     * @param picSize 图片需要修改的尺寸大小
     * @return
     */
    public static String changePicSize(String picUrl, int picSize) {
        if (picUrl == null) {
            return picUrl;
        }
        StringBuilder builder = new StringBuilder(picUrl);
        int lastIndexOf = builder.lastIndexOf(".");
        if (lastIndexOf < 0) {
            return picUrl;
        }
        return builder.insert(lastIndexOf, "_" + picSize + "x" + picSize)
                .toString();
    }

    /**
     * 计算两个Date类型的日期相差的分钟数
     *
     * @param beginDate
     * @param endDate
     * @return
     * @author wumc
     */
    public static int DateDiffierMin(Date beginDate, Date endDate) {
        long time = endDate.getTime() - beginDate.getTime();
        int hours = (int) (time / (60 * 1000)) + 1;

        return hours;
    }

    /**
     * @param <K>
     * @param <V>
     * @param <E>
     * @param list
     * @param keyProp
     * @param valueProp
     * @return
     * @author wumc
     * <p>
     * <pre>
     * 用list生成一个map,keyProp 为指定的key,valueProp 为指定是value
     * </pre>
     */
    @SuppressWarnings("unchecked")
    public static <K, V, E> Map<K, V> listforMapNotNull(List<E> list, String keyProp, String valueProp) {
        Map<K, V> map = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(list)) {
            list.removeAll(nullCollection());
            map = new HashMap<K, V>(list.size());
            for (E object : list) {
                K key = (K) PROBE.getObject(object, keyProp);
                Object value = null;
                if (StringUtils.isEmpty(valueProp)) {
                    value = object;
                } else {
                    value = PROBE.getObject(object, valueProp);
                }
                if (value != null) {
                    map.put(key, (V) value);
                }
            }
        }
        return map;
    }

    /**
     * list装换成有序的map
     *
     * @param list
     * @param keyProp   key
     * @param valueProp value
     * @return Map<K   ,       V>
     * @author zhanghaiming  <br/>
     */
    @SuppressWarnings("unchecked")
    public static <K, V, E> Map<K, V> listforLinkedHashMap(List<E> list, String keyProp, String valueProp) {
        Map<K, V> map = Collections.emptyMap();
        if (CollectionUtils.isNotEmpty(list)) {
            list.removeAll(nullCollection());
            map = new LinkedHashMap<K, V>(list.size());
            for (E object : list) {
                K key = (K) PROBE.getObject(object, keyProp);
                Object value = null;
                if (StringUtils.isEmpty(valueProp)) {
                    value = object;
                } else {
                    value = PROBE.getObject(object, valueProp);
                }
                if (value != null) {
                    map.put(key, (V) value);
                }
            }
        }
        return map;
    }

    /**
     * 电话号码中间四位隐藏
     *
     * @param
     * @param
     * @return
     */
    public static String encryptionPhone(String phone) {
        if (StringUtils.isBlank(phone)) {
            return "";
        } else {
            return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        }
    }

    /**
     * 生成6位字母数字的随机数
     *
     * @param
     * @return
     * @author chengfan
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }
        return buffer.toString();
    }

    /**
     * 深度拷贝，这里需要深拷贝的类（T）需要继承 Serializable 接口
     *
     * @param src
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static <T> List<T> deepCopy(List<T> src) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        @SuppressWarnings("unchecked")
        List<T> dest = null;
        try {
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dest;
    }

    public static <T> List<T> frequency(List<T> ids) {
        //去重复
        List<T> newIds = new ArrayList<>();
        for (T id : ids) {
            if (Collections.frequency(newIds, id) < 1) {
                newIds.add(id);
            }
        }
        return newIds;
    }

    /**
     * list去重
     *
     * @param
     * @return
     */
    public static List removeDuplicate(List src) {
        List listTemp = new ArrayList();
        for (int i = 0; i < src.size(); i++) {
            if (!listTemp.contains(src.get(i))) {
                listTemp.add(src.get(i));
            }
        }
        return listTemp;
    }

    //首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public static String getLocalIP() {
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();//获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;

    }


    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    public static boolean isContainChinese(String str) {
        Matcher m = zhongWenPattern.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 获取当月最小时间
     *
     * @return
     */
    public static long getMonthMinDay() {
        //获取当月第一天
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.DAY_OF_MONTH, begin.getActualMinimum(Calendar.DAY_OF_MONTH));
        begin.set(Calendar.SECOND, 00);
        begin.set(Calendar.HOUR_OF_DAY, 00);
        begin.set(Calendar.MINUTE, 00);
        return begin.getTimeInMillis();
    }

    /**
     * 获取当月最大时间
     *
     * @return
     */
    public static long getMonthMaxDay() {
        // 获取当前月最后一天
        Calendar end = Calendar.getInstance();
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        end.set(Calendar.SECOND, 59);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        return end.getTimeInMillis();
    }

    /**
     * 获取当月最小时间
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFisrtDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        cal.set(Calendar.SECOND, 00);
        cal.set(Calendar.HOUR_OF_DAY, 00);
        cal.set(Calendar.MINUTE, 00);
        return getStringTimeByLong(cal.getTimeInMillis());
    }


    /**
     * 获取当月最大时间
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month - 1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        return getStringTimeByLong(cal.getTimeInMillis());
    }

    /**
     * 将long类型数据转换为string类型
     *
     * @param startTime
     * @return
     */
    public static String getStringTimeByLong(long startTime) {
        Calendar starCal = Calendar.getInstance();
        starCal.setTimeInMillis(startTime);
        Date startDate = starCal.getTime();
        SimpleDateFormat startTimeTemp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return startTimeTemp.format(startDate);
    }

    public static int getMonthNum(String end, String start) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar bef = Calendar.getInstance();
            Calendar aft = Calendar.getInstance();
            bef.setTime(sdf.parse(end));
            aft.setTime(sdf.parse(start));
            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;

            return Math.abs(month + result);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }


}
