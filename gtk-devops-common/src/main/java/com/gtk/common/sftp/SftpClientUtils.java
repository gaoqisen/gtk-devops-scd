package com.gtk.common.sftp;

import com.gtk.common.config.MsgSupport;
import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.io.BufferedReader;

@Slf4j
public class SftpClientUtils {

    private static final String PROTOCOL = "sftp";

    /**
     * 通过远程文件目录获取数组文本
     *
     * @param remoteFilePath 远程文件目录
     * @param sftpConnect sftp链接信息
     * @return 每行的列表数据
     */
    public static List<String> getListByRemoteFilePath(String remoteFilePath, SftpConnect sftpConnect) throws Exception {
        log.info("通用SFTP文件下载转List sftpConnect：{}, sftpConnect: {}", remoteFilePath , sftpConnect);
        ChannelSftp sftp = null;
        List<String> result = new ArrayList<>();
        BufferedReader b = null;
        try {
            sftp = createSftp(sftpConnect);
            String remoteFileName;
            // 远端目录确定以 / 作为目录格式
            String rFileSeparator = "/";
            int rDirNameSepIndex = remoteFilePath.lastIndexOf(rFileSeparator) + 1;
            String rDir = remoteFilePath.substring(0, rDirNameSepIndex);
            remoteFileName = remoteFilePath.substring(rDirNameSepIndex);
            sftp.cd(rDir);

            // 读取文本内容
            b = new BufferedReader(new InputStreamReader(sftp.get(remoteFileName)));
            String lineContext;
            while ((lineContext = b.readLine()) != null) {
                result.add(lineContext);
            }
            log.info("通用SFTP文件下载转List: " + remoteFileName + " success from sftp.");
        } catch (JSchException e) {
            log.error("通用SFTP文件下载转List-创建sftp失败", e);
            throw new Exception("创建sftp失败");
        } catch (SftpException e) {
            log.error("通用SFTP文件下载转List-sftp下载文件失败", e);
            throw new Exception("sftp下载文件失败");
        } catch (FileNotFoundException e) {
            log.error("通用SFTP文件下载转List-本地目录异常，请检查" + remoteFilePath, e);
            throw new Exception("本地目录异常");
        } catch (IOException e) {
            log.error("通用SFTP文件下载转List-创建本地文件失败" + remoteFilePath, e);
            throw new Exception("创建本地文件失败");
        } finally {
            disconnect(sftp);
            if(b != null) {
                try{
                    b.close();
                } catch (IOException ignored) {
                }
            }
        }
        log.info("通用SFTP文件下载转List--sftp下载文件" + remoteFilePath + "结束>>>>>>>>>>>>>");
        return result;
    }

    /**
     * 上传文件
     *
     * @param path   上传的路径，如果目录不存在，则自动创建，/user/boss/a.txt
     * @param inputStream 要上传的文件流
     * @param sftpConnect    sftp的配置信息，ip，port，username，password
     */
    public static boolean uploadStream(String path, InputStream inputStream, SftpConnect sftpConnect) throws Exception {
        ChannelSftp sftp = null;
        String directory = null;
        String fileName = null;
        try {
            sftp = createSftp(sftpConnect);
            String rFileSeparator = "/";
            int rDirNameSepIndex = path.lastIndexOf(rFileSeparator) + 1;
            directory = path.substring(0, rDirNameSepIndex);
            fileName = path.substring(rDirNameSepIndex);
            //判断文件目录是否存在，目录不存在则创建目录
            boolean mkdirs = mkdirs(directory, sftp);
            if(!mkdirs){
                throw new Exception("sftp创建目录失败");
            }
            sftp.put(inputStream, fileName);
            inputStream.close();
        } catch (JSchException e) {
            log.error("通用SFTP文件上传-创建sftp失败", e);
            throw new Exception("创建sftp失败");
        } catch (SftpException e) {
            log.error("通用SFTP文件上传-sftp下载文件失败", e);
            throw new Exception("sftp下载文件失败");
        } catch (FileNotFoundException e) {
            log.error("通用SFTP文件上传-本地目录异常，请检查" + directory, e);
            throw new Exception("本地目录异常");
        } catch (IOException e) {
            log.error("通用SFTP文件上传-创建本地文件失败" + directory, e);
            throw new Exception("创建本地文件失败");
        } finally {
            disconnect(sftp);
        }
        return true;
    }

    /**
     * 创建多级目录
     *
     * @param directory 目录名
     */
    public static boolean mkdirs(String directory,ChannelSftp sftp) {
        boolean flag = false;
        try {
            sftp.cd(directory);
            flag = true;
        } catch (SftpException e) {
            //捕获异常则说明此目录不存在，需要创建
            String[] dirs = directory.split("/");
            //默认从第一层开始验证目录是否存在
            String tempPath = "";
            for (String dir : dirs) {
                if (dir != null && !dir.isEmpty()) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                    flag = true;
                } catch (SftpException ex) {
                    log.error("sftp创建目录" + tempPath, ex.getMessage());
                    try {
                        sftp.mkdir(tempPath);
                        sftp.cd(tempPath);
                        flag = true;
                    } catch (SftpException exc) {
                        log.error("sftp创建目录" + tempPath + "失败", exc.getMessage());
                        flag = false;
                        break;
                    }
                }
            }
            log.info("sftp创建目录完成");
        }
        return flag;
    }



    /**
     * 判断文件或目录是否存在
     *
     * @param path 路径
     * @return boolean
     */
    public static boolean isExist(String path, SftpConnect sftpConnect) throws Exception {
        ChannelSftp sftp;
        try {
            sftp = createSftp(sftpConnect);
        } catch (Exception ex) {
            log.error("通用SFTP判断文件是否存在-创建sftp失败:{}", ex.getMessage());
            throw new Exception(MsgSupport.SFTP_ERROR);
        }
        boolean isExist = false;
        try {
            sftp.lstat(path);
            isExist = true;
        } catch (Exception e) {
            log.warn("通用SFTP判断文件是否存在-远程连接SFTP发生警告:{}", e.getMessage());
            if (!NO_SUCH_FILE.equals(e.getMessage().toLowerCase())) {
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 没有文件标识
     */
    private static final String NO_SUCH_FILE = "no such file";

    /**
     * 创建SFTP连接
     */
    private static synchronized ChannelSftp createSftp(SftpConnect sftpConnect) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(sftpConnect.getUsername(), sftpConnect.getIp(), sftpConnect.getPort());
        session.setPassword(sftpConnect.getPassword());
        Properties properties = new Properties();
        properties.setProperty("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        Channel sftp = session.openChannel(PROTOCOL);
        sftp.connect();
        return (ChannelSftp) sftp;
    }

    /**
     * 关闭连接
     */
    private static void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            log.error("通用SFTP判断文件是否存在-关闭sftp连接失败", e);
        }
    }

}
