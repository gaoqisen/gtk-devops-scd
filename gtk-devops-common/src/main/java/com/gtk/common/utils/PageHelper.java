package com.gtk.common.utils;

import com.alibaba.excel.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class PageHelper {

    public static <T> List<T> pagination(List<T> records, int pageNum, int pageSize) {
        if (CollectionUtils.isEmpty(records)) {
            return Collections.emptyList();
        }
        if (pageNum < 0 || pageSize < 0) {
            return Collections.emptyList();
        }
        int totalCount = records.size();
        int pageCount;
        int remainder = totalCount % pageSize;
        if (remainder > 0) {
            pageCount = totalCount / pageSize + 1;
        } else {
            pageCount = totalCount / pageSize;
        }
        log.info("总记录数为: [{}], 当前页码为: [{}], 每页显示的条数为: [{}]", totalCount, pageNum, pageSize);
        if (remainder == 0) {
            records = records.stream().skip((pageNum - 1) * pageSize).limit(pageSize * pageNum).collect(Collectors.toList());
            return records;
        } else {
            if (pageNum == pageCount) {
                records = records.stream().skip((pageNum - 1) * pageSize).limit(totalCount).collect(Collectors.toList());
                return records;
            } else {
                records = records.stream().skip((pageNum - 1) * pageSize).limit(pageSize * pageNum).collect(Collectors.toList());
                return records;
            }
        }
    }
}
