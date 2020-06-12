package com.book.store.controller.manage;


import com.book.store.common.ServerResponse;
import com.book.store.utils.DateTimeUtil;
import com.book.store.utils.FileUploadUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RequestMapping("/manage/file")
@RestController
public class FileUploadController {

    @PostMapping(value = "/upload")
    public ServerResponse fileUpload(MultipartFile upImg) throws IOException {
        System.out.println("上传");
        String oldName = upImg.getOriginalFilename();
        String newName = FileUploadUtils.createNewFileName(oldName);
        String fileName = DateTimeUtil.dateToStr(new Date(),"yyyyMMdd");
        File file = new File(FileUploadUtils.file_path,fileName);
        if (!file.exists()){
            file.mkdirs();
        }
        File newFile = new File(file,newName);
        upImg.transferTo(newFile);
        Map<String,Object> map = new HashMap<>();
        map.put("path",fileName+"/"+newName);
        return ServerResponse.createBySuccess(map);
    }
    @GetMapping("/file_download")
    public ResponseEntity<byte[]> fileDownLoad(String path)  {
        return FileUploadUtils.createEntity(path);
    }
}
