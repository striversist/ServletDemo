package demo.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

public class FileUtils {

    public static final String TAG = FileUtils.class.getSimpleName();

    public static boolean copyFile(String srcPath, String desPath) {
        InputStream inStream = null;
        FileOutputStream fs = null;

        try {
            int read = 0;
            inStream = new FileInputStream(srcPath); // 璇诲叆鍘熸枃浠�            fs = new FileOutputStream(desPath);
            byte[] buffer = new byte[2048];
            while ((read = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, read);
            }
            fs.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    /**
     * 灏嗘寚瀹氱殑杈撳叆娴佷綔涓轰竴涓枃浠讹紝淇濆瓨鑷虫寚瀹氳矾寰�     * @param iss
     * @param outPath
     * @return
     */
    public static boolean transferInputStreamToFile(InputStream iss, String outPath) {
        if (iss == null || outPath == null)
            return false;
        
        File mf = new File(outPath);
        ReadableByteChannel rfc = Channels.newChannel(iss);
        FileOutputStream oss = null;
        try {
            oss = new FileOutputStream(mf);
            FileChannel ofc = oss.getChannel();
            long pos = 0;
            try {
                long size = iss.available();
                while ((pos += ofc.transferFrom(rfc, pos, size
                        - pos)) < size)
                    ;
            } catch (IOException ex) {
                ex.printStackTrace();
                return false;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (oss != null) {
                try {
                    oss.flush();
                    oss.getFD().sync();
                } catch (Exception e) {
                } finally {
                    try {
                        oss.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return true;
    }
    
    public static boolean deleteFile(String absolutePath) {
        try {
            File file = new File(absolutePath);
            if (file.exists()) {
                return file.delete();
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static void deleteDirectory(String path) {
        deleteFiles(new File(path));
    }
    
    /**
     * 閫掑綊鍒犻櫎
     * @param file
     */
    private static void deleteFiles(File file) {
        if (file == null)
            return;
        
        if (!file.exists()) {
            return;
        }
        
        if (file.isFile()) {
            if (!file.delete()) {   // 鍒犻櫎鏂囦欢澶辫触
                Log.e(TAG, "deleteFiles: delete failed, file=" + file.getAbsolutePath());
            }
        } else if (file.isDirectory()) {
            File files[] = file.listFiles();
            if (files != null) {
                for (int i=0; i<files.length; i++) {
                    deleteFiles(files[i]);
                }
            }
            
            if (!file.delete()) {   // 鍒犻櫎绌烘枃浠跺す澶辫触
                Log.e(TAG, "deleteFiles: delete failed, file=" + file.getAbsolutePath());
            }
        }
    }
    
    public static byte[] getFileBytes(String path) {
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
