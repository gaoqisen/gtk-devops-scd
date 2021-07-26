package com.gaoqisen.pattern.template;

public abstract class PageTemplate {

    void start() {
        // 总条数
        int totalRow = 50;
        // 每页记录数
        int pageSize = 20;
        // 总页数
        int totalPage = (totalRow - 1) / pageSize + 1;
        for(int i = 0; i< totalPage; i++) {
            int total = execute(i + 1, pageSize);
            if(total != totalPage) {
                totalRow = total;
                totalPage = (totalRow - 1) / pageSize + 1;
            }
        }
    }

     abstract int execute(int start, int end);

}
