package com.gtk.common.excel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasePageReq {

    private Integer pageNum = 1;

    private Integer pageSize = 500;

    private String startTime;

    private String endTime;

}
