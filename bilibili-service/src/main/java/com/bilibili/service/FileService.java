package com.bilibili.service;

import com.bilibili.domain.File;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author 于鑫瑞
 * @version 1.0.0
 */
public interface FileService {
    String uploadFileBySlices(MultipartFile slice,
                              String fileMD5,
                              Integer sliceNo,
                              Integer totalSliceNo) throws Exception;
    String getFileMD5(MultipartFile file) throws Exception;
    File getFileByMd5(String fileMd5);
}
