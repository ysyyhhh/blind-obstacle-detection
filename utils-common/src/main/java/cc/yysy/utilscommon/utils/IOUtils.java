package cc.yysy.utilscommon.utils;

import com.alibaba.nacos.common.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * IO 工具类
 * @author Wds
 */
public class IOUtils {

    public static String getRootPath(){
        String projectRootDirectoryPath = System.getProperty("user.dir");
//        String parentPath = new File(projectRootDirectoryPath).getParent();
        return projectRootDirectoryPath;
    }

    public static List<String> findDir(String path){
        List<String> results = new ArrayList<>();
//        String pathDir = "files";
//        String path = "your path"; // 文件路径
        File file = new File(path);
        File[] files = file.listFiles(); // 获取路径下的所有文件
        for (File f : files) {
            if (f.isFile()) { // 判断是否为文件
                results.add(f.getName());
//                System.out.println(f.getName()); // 打印文件名
            }
        }
        return results;
    }

    public static void writeFile(String data, File file) throws IOException {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.flush();
        } finally {
            close(out);
        }
    }

    public static String readFile(File file) throws IOException {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.flush();
            byte[] data = out.toByteArray();
            return new String(data);
        } finally {
            close(in);
            close(out);
        }
    }

    public static void close(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                // nothing
            }
        }
    }

}

