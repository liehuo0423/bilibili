package com.bilibili.utils;

import com.bilibili.domain.exception.ConditionException;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public class FastDFSUtil {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private AppendFileStorageClient appendFileStorageClient;

    public static final String GROUP_NAME = "group1";
    public String getFileType(MultipartFile file) {
        if (file == null) throw new ConditionException("非法文件");
        String filename = file.getOriginalFilename();
        int index = filename.lastIndexOf(".");
        return filename.substring(index + 1);
    }

    public String uploadCommonFile(MultipartFile file) throws IOException {
        Set<MetaData> metaData = new HashSet<>();
        String fileType = getFileType(file);
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), fileType, metaData);
        return storePath.getPath();
    }

    public void deleteFile(String filePath) {
        fastFileStorageClient.deleteFile(filePath);
    }

    public String uploadAppendFile(MultipartFile file) throws IOException {
        String fileType = getFileType(file);
        StorePath storePath = appendFileStorageClient.uploadAppenderFile(GROUP_NAME, file.getInputStream(), file.getSize(), fileType);
        return storePath.getPath();
    }

    public void modifyAppendFile(MultipartFile file,String filePath,Long offset) throws IOException {
        appendFileStorageClient.modifyFile(GROUP_NAME,filePath,file.getInputStream(),file.getSize(),offset);
    }
}
