package com.ylz.demo.tool;

public class FileFunctionUtil {

    /**
     * 获得文件名
     * @param filePath
     * @return
     */
    public static String Get_FilePath_FileName(String filePath){
        String tempData="";
        try {
            //处理间隔符号
            String strTmp = filePath.replaceAll("\\\\", "/");
            //判断是否有效
            if (strTmp.indexOf('/') > -1) {
                String[] nameArray = filePath.replaceAll("\\\\", "/").split("/");

                System.out.println(nameArray.length);
                System.out.println(nameArray[nameArray.length - 1]);
                tempData = nameArray[nameArray.length - 1];
                return tempData;
            } else {
                return filePath;
            }
        }catch (Exception e){
            e.getMessage();
            //处理失败
            return null;
        }


    }
}
