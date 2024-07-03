package com.zhaiker.dimencreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Android屏幕适配Dimens文件生成工具类
 */
public class AndroidDimensUtil {

    /**
     * 基准宽度和高度(通常设置成UI图的分辨率的高度和宽度)
     */
    public static final int baseHeight = 1920;
    public static final int baseWidth = 1080;

    /**
     * 生成Dimens文件的路径（项目的res文件夹）
     */
    private static String FILE_PATH;

    private static final String WidthTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    //private static final String HeightTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    public static void main(String[] args) {
        // 获取项目res文件的路径
        getResPath(new File("dimencreator").getAbsolutePath());

        //需要注意，在3.0之后，某些型号手机需要将高度减去48像素，即底部虚拟状态栏的高度
        //N60C实际分辨率为1440*720，需要生成1392才能匹配
        AndroidDimensUtil.createDimens(1392, 720);
        //AndroidDimensUtil.createDimens(1280, 800);

        AndroidDimensUtil.createDimens(1920, 1080);


        // 添加以上分辨率都木有通用的,参考:http://blog.csdn.net/guozhaohui628/article/details/71870530
        AndroidDimensUtil.createCommonDimens(1f);
    }

    /**
     * 生成相应分辨率的文件
     *
     * @param dimenHeight 要生成相应分辨率的高
     * @param dimenWidth  要生成相应分辨率的宽
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createDimens(int dimenHeight, int dimenWidth) {

        int max = Math.max(baseHeight, baseWidth);
        // 生成Width
        StringBuilder sbForWidth = new StringBuilder();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");

        float scaleWidth = dimenWidth * 1.0f / baseWidth;
        float scaleHeight = dimenHeight * 1.0f / baseHeight;
        float scale = Math.min(scaleWidth,scaleHeight);

//        System.out.println("生成Width : " + dimenWidth + " , 基准Width : " + baseWidth + " , width比例 : " + scaleWidth);

        for (int i = -100; i <= max; i++) {
            String k = i + "";
            if(i < 0){
                k = "_"+(-i);
            }
            sbForWidth.append(WidthTemplate.replace("{0}", k).replace("{1}", leftTwoDecimal(scale * i) + ""));

        }

        sbForWidth.append("</resources>");

        // 生成文件
        File dimenFile = new File(FILE_PATH + File.separator + "values-" + dimenHeight + "x" + dimenWidth);
        dimenFile.mkdirs();
        System.out.println("指定分辨率:" + dimenHeight + "x" + dimenWidth);

        File lay_xFile = new File(dimenFile.getAbsolutePath(), "dimens.xml");

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(lay_xFile));
            printWriter.print(sbForWidth.toString());
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成适配未找到对应分辨率设备的通用dimen文件（直接放置在values中，单位为px）
     *
     * @param commonDensity 通用density的值
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createCommonDimens(float commonDensity) {
        float commonScale = 1.0f / commonDensity;

        // 生成Width
        StringBuilder sbForWidth = new StringBuilder();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n");

        int max = Math.max(baseHeight, baseWidth);

        for (int i = -100; i <= max; i++) {
            String k = i + "";
            if(i < 0){
                k = "_"+(-i);
            }
            sbForWidth.append(WidthTemplate.replace("{0}", k).replace("{1}", leftTwoDecimal(commonScale * i) + ""));

        }
        sbForWidth.append("</resources>");

        // 生成文件
        File dimenFile = new File(FILE_PATH + File.separator + "values");
        dimenFile.mkdirs();
        System.out.println("未指定的通用分辨率（values中）");

        File lay_xFile = new File(dimenFile.getAbsolutePath(), "dimens.xml");

        try {
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(lay_xFile));
            printWriter.print(sbForWidth.toString());
            printWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保留两位小数
     *
     * @param a 要保留的Float数值
     * @return 保留后的数值
     */
    private static float leftTwoDecimal(float a) {
        return (int) (a * 100) / 100f;
    }

    /**
     * 利用递归查询res文件夹的绝对路径并赋值
     *
     * @param filePath 文件路径
     */
    private static void getResPath(String filePath) {
        for (File file : new File(filePath).listFiles()) {
            if (file.isDirectory()) {
                // 递归
                //这里将列出所有的文件夹
                if (file.getAbsolutePath().contains("src") && file.getAbsolutePath().contains("res")) {
                    System.out.println("res路径：" + file.getAbsolutePath());
                    FILE_PATH = file.getAbsolutePath();
                    return;
                }
                getResPath(file.getAbsolutePath());
            }
        }
    }
}