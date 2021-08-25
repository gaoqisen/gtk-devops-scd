package com.gtk.common.db;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName JdbcTemplateUtils
 * @Author gaoqisen
 * @Date 2019-12-01
 * @Version 1.0
 */
public class JdbcTemplateUtils {

    /**
     * @Author gaoqisen
     * @Description //TODO 批量数据处理 例子： final List<Object[]> listObject = new ArrayList<Object[]>();
     * @Date 2019-12-01 16:32
     * @Param [sqlStr, jdbcTemplate, listObject]
     * @return void
     **/
    private void dataBatchDeal(String sqlStr, JdbcTemplate jdbcTemplate, final List<Object[]> listObject) {
        BatchPreparedStatementSetter bpss = new BatchPreparedStatementSetter(){  //
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Object[] args = listObject.get(i);
                for(int j = 0; j<args.length; j++) {
                    Object object = args[j];
                    ps.setString(j+1, String.valueOf(object));
                }
            }
            @Override
            public int getBatchSize() {
                return listObject.size();
            }
        };
        jdbcTemplate.batchUpdate(sqlStr, bpss);
    }

    /**
     * @Author gaoqisen
     * @Description //TODO 获取JdbcTemplate通过resources目录下的properties配置
     * @Date 2019-12-01 16:36
     * @Param [serverBean, proName]
     * @return org.springframework.jdbc.core.JdbcTemplate
     **/
    private static JdbcTemplate getJdbcTemplateServer(String serverBean, String proName){
        JdbcTemplate jtsqlserver=null;
        try {
            DriverManagerDataSource ds = new DriverManagerDataSource();
            Properties p = new Properties();
            p.load(JdbcTemplateUtils.class.getClassLoader().getResourceAsStream(proName));
            ds.setDriverClassName(p.getProperty("jdbc."+serverBean+".driverClassName"));
            ds.setUrl(p.getProperty("jdbc."+serverBean+".url"));
            ds.setUsername(p.getProperty("jdbc."+serverBean+".username"));
            ds.setPassword(p.getProperty("jdbc."+serverBean+".password"));
            jtsqlserver= new JdbcTemplate(ds);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return jtsqlserver;
    }
}
