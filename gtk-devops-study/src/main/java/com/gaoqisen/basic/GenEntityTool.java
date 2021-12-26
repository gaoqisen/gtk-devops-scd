package com.gaoqisen.basic;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class GenEntityTool {
    /**
     * 程序自动设置
     */
    private static String tableName = "test";//表名
    private static String tableComment = "test";//表注释
    private static String filePath;//最终文件生成位置
    /**
     * 基础路径，需要手动设置
     */
    private static String basePackage = "com\\test\\";//根包位置
    private static String filePackage = basePackage + "sys\\";//文件所在包位置

    public static void main(String[] args) {
        //文件所在包位置
        filePackage = filePackage + StringUtil.camelCaseName(tableName).toLowerCase() + "\\";

        //拼接完整最终位置 System.getProperty("user.dir") 获取的是项目所在路径，如果我们是子项目，则需要添加一层路径
        filePath = System.getProperty("user.dir") + "\\src\\main\\java\\" + filePackage;
        List<TableInfo> list = Arrays.asList(
                TableInfo.builder().columnName("billNo").dataType("string").columnComment("票据号码").build(),
                TableInfo.builder().columnName("discountDt").dataType("string").columnComment("贴现日期").build(),
                TableInfo.builder().columnName("billTp").dataType("string").columnComment("票据类型").build(),
                TableInfo.builder().columnName("drawBillDt").dataType("string").columnComment("出票日期").build(),
                TableInfo.builder().columnName("billEndDt").dataType("string").columnComment("票据到期日期").build(),
                TableInfo.builder().columnName("brrNm").dataType("string").columnComment("持票人名称").build(),
                TableInfo.builder().columnName("acctNo").dataType("string").columnComment("账号").build(),
                TableInfo.builder().columnName("brrOpnAcctBnkNo").dataType("string").columnComment("持票人开户行行号").build(),
                TableInfo.builder().columnName("brrOpnAcctBnkNm").dataType("string").columnComment("持票人开户行行名").build(),
                TableInfo.builder().columnName("stickInPrsnOpnBnkNo").dataType("string").columnComment("贴入人开户行行号").build(),
                TableInfo.builder().columnName("stickInPrsnNm").dataType("string").columnComment("贴入人名称").build(),
                TableInfo.builder().columnName("drftAmt").dataType("double").columnComment("汇票金额").build(),
                TableInfo.builder().columnName("dcnIntRate").dataType("double").columnComment("贴现利率").build(),
                TableInfo.builder().columnName("discountInt").dataType("double").columnComment("贴现利息").build(),
                TableInfo.builder().columnName("setlAmt").dataType("double").columnComment("结算金额").build()


                );

        // 遍历字段
        for (TableInfo tableInfo : list) {
            StringBuilder out = new StringBuilder();
            out.append("    /**\n" +
                    "     * " + tableInfo.getColumnComment() + "\n" +
                    "     */ " + "\n");
            if (!tableInfo.isNull) {
               // out.append("    @NotBlank\n");
            }
            if (tableInfo.columnLength != null) {
                out.append("    @Size(max = " + tableInfo.columnLength + ", message = \"" + tableInfo.getColumnComment() + "长度不能超过" + tableInfo.columnLength + "\")\n");
            }
            out.append("    @ApiModelProperty(\"" + tableInfo.getColumnComment() + "\")\n");
            out.append("    private ").append(StringUtil.typeMapping(tableInfo.getDataType())).append(" ").append(StringUtil.camelCaseName(tableInfo.getColumnName())).append(";\n");
            System.out.println(out.toString());

        }
        // createVo(list);
    }

    /**
     * 表结构信息实体类
     */
    @Data
    @Builder
    private static class TableInfo {
        private String columnName;//字段名

        /**
         * 字段类型
         * int|integer
         * int|integer
         * float|double|decimal|real
         * date|time|datetime|timestamp
         * string
         */
        private String dataType;

        // 字段注释
        private String columnComment;

        // 字段长度
        private Integer columnLength;

        // 是否为空
        private boolean isNull;

        //主键
        private String columnKey;

        //主键类型
        private String extra;
    }

    /**
     * 创建vo类
     */
    private static void createVo(List<TableInfo> tableInfos) {
        File file = FileUtil.createFile(filePath + "vo\\" + StringUtil.captureName(StringUtil.camelCaseName(tableName)) + "Vo.java");
        StringBuilder stringBuilder = new StringBuilder(1024);
        stringBuilder.append(
                "package " + filePackage.replaceAll("\\\\", ".") + "vo;\n" +
                        "\n" +
                        "import "+ basePackage.replaceAll("\\\\", ".") +" common.pojo.PageCondition;"+
                        "import lombok.Data;\n" +
                        "import java.io.Serializable;\n" +
                        "import java.util.Date;\n" +
                        "\n" +
                        "@Data\n" +
                        "public class " + StringUtil.captureName(StringUtil.camelCaseName(tableName)) + "Vo extends PageCondition implements Serializable {\n"
        );
        //遍历设置属性
        for (TableInfo tableInfo : tableInfos) {
            stringBuilder.append("    private ").append(StringUtil.typeMapping(tableInfo.getDataType())).append(" ").append(StringUtil.camelCaseName(tableInfo.getColumnName())).append(";//").append(tableInfo.getColumnComment()).append("\n\n");
        }
        stringBuilder.append("}");
        //FileUtil.fileWriter(file, stringBuilder);
    }

    /**
     * file工具类
     */
    private static class FileUtil {
        /**
         * 创建文件
         *
         * @param pathNameAndFileName 路径跟文件名
         * @return File对象
         */
        private static File createFile(String pathNameAndFileName) {
            File file = new File(pathNameAndFileName);
            try {
                //获取父目录
                File fileParent = file.getParentFile();
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                }
                //创建文件
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (Exception e) {
                file = null;
                System.err.println("新建文件操作出错");
                e.printStackTrace();
            }
            return file;
        }

        /**
         * 字符流写入文件
         *
         * @param file         file对象
         * @param stringBuilder 要写入的数据
         */
        private static void fileWriter(File file, StringBuilder stringBuilder) {
            //字符流
            try {
                FileWriter resultFile = new FileWriter(file, false);//true,则追加写入 false,则覆盖写入
                PrintWriter myFile = new PrintWriter(resultFile);
                //写入
                myFile.println(stringBuilder.toString());

                myFile.close();
                resultFile.close();
            } catch (Exception e) {
                System.err.println("写入操作出错");
                e.printStackTrace();
            }
        }
    }

    /**
     * 字符串处理工具类
     */
    private static class StringUtil {
        /**
         * 数据库类型->JAVA类型
         * int|integer
         * float|double|decimal|real
         * date|time|datetime|timestamp
         * string
         * @param dbType 数据库类型
         * @return JAVA类型
         */
        private static String typeMapping(String dbType) {
            String javaType;
            if ("int|integer".contains(dbType)) {
                javaType = "Integer";
            } else if ("float|double|decimal|real".contains(dbType)) {
                javaType = "BigDecimal";
            } else if ("date|time|datetime|timestamp".contains(dbType)) {
                javaType = "Date";
            } else {
                javaType = "String";
            }
            return javaType;
        }

        /**
         * 驼峰转换为下划线
         */
        private static String underscoreName(String camelCaseName) {
            StringBuilder result = new StringBuilder(1024);
            if (camelCaseName != null && camelCaseName.length() > 0) {
                result.append(camelCaseName.substring(0, 1).toLowerCase());
                for (int i = 1; i < camelCaseName.length(); i++) {
                    char ch = camelCaseName.charAt(i);
                    if (Character.isUpperCase(ch)) {
                        result.append("_");
                        result.append(Character.toLowerCase(ch));
                    } else {
                        result.append(ch);
                    }
                }
            }
            return result.toString();
        }

        /**
         * 首字母大写
         */
        private static String captureName(String name) {
            char[] cs = name.toCharArray();
            cs[0] -= 32;
            return String.valueOf(cs);

        }

        /**
         * 下划线转换为驼峰
         */
        private static String camelCaseName(String underscoreName) {
            StringBuilder result = new StringBuilder(1024);
            if (underscoreName != null && underscoreName.length() > 0) {
                boolean flag = false;
                for (int i = 0; i < underscoreName.length(); i++) {
                    char ch = underscoreName.charAt(i);
                    if ("_".charAt(0) == ch) {
                        flag = true;
                    } else {
                        if (flag) {
                            result.append(Character.toUpperCase(ch));
                            flag = false;
                        } else {
                            result.append(ch);
                        }
                    }
                }
            }
            return result.toString();
        }
    }



}
