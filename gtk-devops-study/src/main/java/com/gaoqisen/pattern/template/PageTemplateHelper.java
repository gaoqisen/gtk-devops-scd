package com.gaoqisen.pattern.template;

@FunctionalInterface
public interface PageTemplateHelper {
    // 开始执行分页
    default void start() {
        // 总条数
        int totalRow = 50;
        // 每页记录数
        int pageSize = 20;
        // 总页数
        int totalPage = (totalRow - 1) / pageSize + 1;
        for(int i = 0; i< totalPage; i++) {
            int total = execute(i + 1, pageSize);
            if(total != totalRow) {
                totalRow = total;
                totalPage = (totalRow - 1) / pageSize + 1;
            }
        }
    }

    // 初始化
    static void init(PageTemplateHelper pageTemplate) {
        pageTemplate.start();
    }

    // 执行分页逻辑
    int execute(int start, int end);
}
