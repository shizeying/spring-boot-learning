package com.example.hive2es.elasticsearch.common;

import com.example.hive2es.elasticsearch.config.RestClientConfig;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConversionUtils {

    private static ConversionUtils conversionUtils;

    private  ConversionUtils() {
    }



    public static ConversionUtils getInstance() {
        if (conversionUtils == null) {
            synchronized (RestClientConfig.class) {
                if (conversionUtils == null) {
                    conversionUtils = new ConversionUtils();
                }
            }
        }
        return conversionUtils ;
    }
    /**
     * 驼峰法转下划线
     *
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public  String camel2Underline(String line) {
        if (line.equals("_")) {
            return line.toLowerCase();
        }
        if (line == null || "".equals(line)) {
            return "";
        }
        line = String.valueOf(line.charAt(0)).toUpperCase()
                .concat(line.substring(1));
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(word.toUpperCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString().toLowerCase();
    }

    /**
     * resources的json名
     *
     * @param jsonSource json源
     * @return {@link String}* @throws IOException ioexception
     */
    public String getJson(String jsonSource) throws IOException {
        String path = this.getClass().getClassLoader().getResource("mapping/"+jsonSource).getPath();
        return FileUtils.readFileToString(new File(path), "UTF-8");
    }

}
