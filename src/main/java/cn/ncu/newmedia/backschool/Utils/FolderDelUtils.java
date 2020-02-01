package cn.ncu.newmedia.backschool.Utils;

import java.io.File;

/**
 * @author maoalong
 * @date 2020/2/1 19:35
 * @description
 */
public class FolderDelUtils {
    /*删除存在的文件夹*/
    public static void deleteFolder(File director) {

        File[] files = director.listFiles();

        if(files!=null&&files.length!=0){
            for(File file:files){
                if(file.isDirectory())  deleteFolder(file);
                else    file.delete();
            }
        }

        director.delete();
    }

    /*仅删除文件夹中的文件*/
    public static void deleteFileInFolder(File director){
        File[] files = director.listFiles();

        if(files!=null&&files.length!=0){
            for(File file:files){
                if(file.isDirectory())  deleteFileInFolder(file);
                else    file.delete();
            }
        }
    }


}
