package com.tankwar.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * File operator util class.
 * @since 2015/11/14
 */
public class FileOperator {
    /** The file that to operating. */
    private File mFile = null;

    /** The input stream of operating. */
    private InputStream mInputStream = null;

    /** The buffer of input stream. */
    private byte[] buffer = new byte[1024*1024];


    /**
     * Constructing a file operator.
     * @param file The file.
     */
    public FileOperator(File file) {
        mFile = file;
    }


    /**
     * Constructor a file operator.
     * @param inputStream The input stream.
     */
    public FileOperator(InputStream inputStream) {
        mInputStream = inputStream;
    }


    /**
     * Copy file to target position.
     * @param target Target position.
     * @return true success, false failed.
     */
    public synchronized boolean copyTo(String target) {
        InputStream is = mInputStream;
        OutputStream os = null;
        if ((is == null && mFile == null) || (mFile != null && !mFile.exists()))
            return false;

        try {
            if (mFile != null)
                is = new FileInputStream(mFile);

            os = new FileOutputStream(target);
            int len = 0;

            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            return true;
        }catch(IOException ex) {
            Log.e(ex);
        }finally{
            try {
                if (is != null)
                    is.close();

                if (os != null) {
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
