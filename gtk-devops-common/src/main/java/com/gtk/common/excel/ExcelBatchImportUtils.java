package com.gtk.common.excel;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@AllArgsConstructor
public class ExcelBatchImportUtils<T> {


    /**
     * 通过fastDfs地址读取excel数据
     * @param excelImport
     * @return
     */
    public List<T> readDataByFastUrl(BaseExcelImport excelImport) throws Exception {
        String url = excelImport.getUrl();
        log.info("excel{}批量导入从fastDfs下载文件入参start， url: {}",excelImport.getExcelName(), url);
        byte[] byteStream = null; // fastDFSUtils.downloadFileToByte(url);
        try (InputStream inputStream = new ByteArrayInputStream(byteStream)){
            ExcelBatchImportListener<T> excelListener = new ExcelBatchImportListener<T>(excelImport.getMaxSize(), excelImport.getExcelName());
            EasyExcel.read(inputStream, excelImport.getZclass(), excelListener).headRowNumber(3).sheet().doRead();
            return excelListener.getDatas();
        } catch (Exception exception) {
            log.info("excel{}批量导入从fastDfs下载文件异常 Exception", excelImport.getExcelName(), exception);
            throw new Exception(exception.getMessage());
        } finally {
            // fastDFSUtils.deleteFile(url);
        }
    }

    static class ExcelBatchImportListener<T> extends AnalysisEventListener<T> {

        /**
         * 最大长度
         */
        private int maxSize;

        /**
         * excel名称
         */
        private String excelName;

        /**
         * 用于存储读取到校验合格的数据
         */
        private List<T> datas = new ArrayList<>();

        public ExcelBatchImportListener(int maxSize, String excelName) {
            this.maxSize = maxSize;
            this.excelName = excelName;
        }

        /**
         * <p>解析每条数据处理的动作 </p>
         * @param validate 解析参数
         * @param analysisContext 分析上下文参数
         */
        @SneakyThrows
        @Override
        public void invoke(T validate, AnalysisContext analysisContext) {
            int rowIndex = analysisContext.readRowHolder().getRowIndex() +1;
            log.info("{}批量导入开始解析第{}行数据, 当前处理数据行数: {}, apollo数量: {}, validate: {}",
                    excelName, rowIndex, datas.size(), maxSize, validate);
            if(BeanUtil.isEmpty(validate)) {
                return;
            }
            try{
                // 数据校验 ValidatorUtils.validateEntity(validate);
            } catch (Exception e) {
                String msg = "第" + rowIndex + "行数据校验失败," + e.getMessage();
                throw new Exception(msg);
            }

            //判断导入的数据是否超过配置的最大行
            if(datas.size() >= maxSize){
                String msg = excelName + "导入条数超过最大数" + maxSize + "条";
                throw new Exception(msg);
            }
            datas.add(validate);
        }

        public List<T> getDatas() {
            return datas;
        }


        /**
         * 解析所有数据之后处理的动作
         * @param analysisContext 分析上下文
         */
        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }
    }

}