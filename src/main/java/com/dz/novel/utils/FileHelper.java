package com.dz.novel.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: Deng Zhou
 * @Description:
 * @Date: 0:28 2020/1/20
 */
public class FileHelper {

    public static void downloadZip(String downloadUrl, File file) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        URL url = new URL(downloadUrl);
        URLConnection connection = url.openConnection();
        InputStream inputStream = connection.getInputStream();
        int length = 0;
        byte[] bytes = new byte[1024];
        while ((length = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, length);
        }
        fileOutputStream.close();
        inputStream.close();
    }

}
