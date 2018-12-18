package com.fl.sp.user.controller;

import com.fl.sp.common.CommonHelp;
import com.fl.sp.common.controller.BaseController;
import com.fl.sp.model.FileMeta;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

@Controller
@RequestMapping("/user")
public class FileController extends BaseController {
  @Value("${file.uploadFolder}")
  private String uploadFolder;

  LinkedList<FileMeta> files = new LinkedList<FileMeta>();
  FileMeta fileMeta = null;

  @RequestMapping(value = "/fileupload")
  @ResponseBody
  public LinkedList<FileMeta> upload( MultipartFile[] picfile) {

    for (int i = 0; i <picfile.length ; i++) {

      String newname = picfile[i].getOriginalFilename();
      String fguid = CommonHelp.getUuid();
      String newfilename = fguid + CommonHelp.getHZMDot(newname);
      String xdlj = "/upload/userlogo/" + newfilename;
      System.out.println(picfile[i].getOriginalFilename() + " uploaded! " + files.size());
      // 2.2 if files > 10 remove the first from the list
      if (files.size() >= 10) files.pop();
      // 2.3 create new fileMeta
      fileMeta = new FileMeta();
      fileMeta.setFileName(picfile[i].getOriginalFilename());
      fileMeta.setFileSize(picfile[i].getSize() / 1024 + " Kb");
      fileMeta.setFileType(picfile[i].getContentType());
      fileMeta.setXdlj(xdlj);
      try {
        fileMeta.setBytes(picfile[i].getBytes());
        FileCopyUtils.copy(picfile[i].getBytes(), new FileOutputStream(uploadFolder + xdlj));

      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      // 2.4 add to files
      files.add(fileMeta);
    }
    // result will be like this
    // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
    return files;
  }
}
