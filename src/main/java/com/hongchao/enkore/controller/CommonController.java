package com.hongchao.enkore.controller;

import com.hongchao.enkore.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

// download and upload
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController
{

    @Value("${enkore.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file)
    {
        log.info(file.toString());

        // original file name
        String originalFileName = file.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

        //UUID
        String fileName = UUID.randomUUID().toString() + suffix;

        //check path
        File dir = new File(basePath);
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        try
        {
            // save file
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    // download file
    @GetMapping("/download")

    public void download(String name, HttpServletResponse response)
    {
        try
        {
            //input
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //output
            ServletOutputStream outputStrem = response.getOutputStream();
            response.setContentType("image/jepg");

            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1)
            {
                outputStrem.write(bytes, 0, len);
                outputStrem.flush();
            }

            // close
            outputStrem.close();
            fileInputStream.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
