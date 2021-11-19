package com.techgeeknext.controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

@RestController
public class SpringBootGzipCompController {

    private static final String FILES_STORAGE_PATH = "D:/files/";

    @RequestMapping("/file/{fileName:.+}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("fileName") String fileName) throws IOException {

        File file = new File(FILES_STORAGE_PATH + fileName);
        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //in case mimetype not available, set to default mimetype
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            // set downloaded file as inline file
            response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + file.getName() + "\""));

            //set downloaded file as attachment
            // response.setHeader("Content-Disposition",
			// String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            //FileCopyUtils.copy utility methods for file and stream copying.
			//All copy methods use a block size of 4096 bytes, and close all affected streams when done.
            FileCopyUtils.copy(new BufferedInputStream(new FileInputStream(file)),
					response.getOutputStream());
        }
    }
}
