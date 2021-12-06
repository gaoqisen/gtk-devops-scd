package com.gtk.common.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseExcelImport {

    private String excelName;

    private String url;

    private Integer maxSize = 1000;

    private Class zclass;

}
