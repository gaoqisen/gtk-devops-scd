package com.gtk.common.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cursor.Cursor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gaoqisen
 * @Description: excel导出模版
 * @date 2021/9/9 15:55
 * @UpdateDesc <p>
 * 2021/9/915:55
 * @UpdateAuthor </p>
 * <p>
 * 2021/9/915:55
 * @UpdateAuthor </p>
 */
@Slf4j
public abstract class AbstractExcelExport<T> {

    /**
     * 获取excel的fastDfs地址
     *
     * @param sheetName sheet名
     * @param pageReq 分页参数
     * @param zclass excel对象
     * @return fastDfs地址
     */
    public String getExcelUrlByData(String sheetName, BasePageReq pageReq, Class zclass) throws Exception {
        try {
            return excelExportPaging(sheetName, pageReq, zclass);
        } catch (IOException e) {
            throw new Exception("excel导出异常");
        }
    }

    /**
     * 批处理excel导入数量
     */
    private static final int BATCH_SIZE = 5000;


    /**
     * EXCEL文件后缀名
     */
    public static final String FILE_EXT_NAME = "xlsx";

    /**
     * 分页导出excel【单个sheet】
     *
     * 通过【excel处理过程】抽象方法返回的数据生成excel并返回fastDfs地址
     *
     * @param sheetName sheet名
     * @param pageReq 分页参数
     * @param zclass excel对象
     * @return fastDfs地址
     * @throws IOException io异常
     */
    public String excelExportPaging(String sheetName, BasePageReq pageReq, Class zclass) throws IOException {
        // 校验数据 ...

        List<T> list = new ArrayList<>();
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
        Map<String, Object> prepareData = excelPrepareData();
        ExcelWriter excelWriter = null;
        try (Cursor<T> cursor = excelGetCursorData(pageReq);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            excelWriter = EasyExcel.write(bos, zclass).build();

            // mybatis流式查询后批量写入excel
            Iterator<T> iterator = cursor.iterator();
            while (iterator.hasNext()) {
                list.add(excelDataHandle(iterator.next(), prepareData));
                int size = list.size();
                if (size == BATCH_SIZE) {
                    log.debug("Excel批量导出模版当前处理条数 size: {}", size);
                    excelWriter.write(list, writeSheet);
                    list.clear();
                }
            }
            excelWriter.write(list, writeSheet);
            excelWriter.finish();
            list.clear();

            // 将bos上传到文件服务器后返回地址
            return "url";
        }
    }

    /**
     * 准备数据（可以传递全局使用的字典用于码值替换）
     *
     * @return 准备的map数据
     */
    public Map<String, Object> excelPrepareData(){return null;};

    /**
     * 单条数据处理（如果导出10w条数据则执行该方法10w次，注意大量日志输出）
     *
     * @param data 需要处理的数据
     * @param params 准备的数据
     * @return 处理后的数据
     */
    public T excelDataHandle(T data, Map<String, Object> params){return data;};

    /**
     * 获取游标数据（注入SqlSession后调用样例：sqlSession.selectCursor("namespace", pageReq);）
     *
     * @param pageReq 查询参数
     * @return 查询返回的excel数据
     */
    public abstract Cursor<T> excelGetCursorData(BasePageReq pageReq);

}