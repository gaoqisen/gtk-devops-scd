package com.gtk.common.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.apache.ibatis.session.ResultContext;
import org.apache.ibatis.session.ResultHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataResultHandler implements ResultHandler<DataExportExcel> {

    private Map<String, Object> params;
    private List<DataExportExcel> excelList = new ArrayList<>();
    private String excelUrl;
  //  private FastDFSUtils dfsUtils;
    private ExcelWriter excelWriter = null;
    private WriteSheet writeSheet = EasyExcel.writerSheet("机构信息").build();
    private static final int BATCH_SIZE = 5000;

    public DataResultHandler(String excelUrl) {
        excelWriter = EasyExcel.write(excelUrl, DataExportExcel.class).build();
        this.excelUrl = excelUrl;
    }

    @Override
    public void handleResult(ResultContext<? extends DataExportExcel> resultContext) {
        DataExportExcel resultObject = resultContext.getResultObject();
        excelList.add(excelDataHandle(resultObject, params));
        if (excelList.size() > BATCH_SIZE) {
            writeExcel();
        }
    }

    private DataExportExcel excelDataHandle(DataExportExcel resultObject, Map<String, Object> params) {
        return null;
    }

    public String getFastDfsUrl() {
        writeExcel();
        if (excelWriter != null) {
            excelWriter.finish();
        }
        try {
            File file = new File(excelUrl);
            return null;// return this.dfsUtils.uploadFileWithByte(Files.readAllBytes(file.toPath()), "group1", BasicConstant.XLSX);
        } catch (Exception e) {
            //throw new BsException(Msg.FILE_UPLOAD_FAIL, e);
        }
        return "";
    }

    public void writeExcel() {
        try {
            excelWriter.write(excelList, writeSheet);
            if (excelList.size() > 0) {
                excelList.clear();
            }
        } catch (Exception e) {
            //throw new BsException(MsgSupport.EXCEL_ERROR,"写入数据失败", e);
        }
    }

}