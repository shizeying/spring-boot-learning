package com.example.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.github.difflib.text.DiffRow;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DiffUtilsTest {

  private List<String> file1;
  private List<String> file2;

  @BeforeEach
  public void BeforeEach() throws IOException {
    file1 = FileUtil.readUtf8Lines(ResourceUtil.getResource("diff1").getFile());
    file2 = FileUtil.readUtf8Lines(ResourceUtil.getResource("diff2").getFile()
    );
  }

  @Test
  void test() {
    //行比较器，原文件删除的内容用"~"包裹，对比文件新增的内容用"**"包裹
    String  original=String.join("\n", file1);
    String  revised=String.join("\n", file2);
    List<DiffRow> diffRows =DiffUtil. diffRowsByString(original, revised,"\n");
    System.out.println(JSON.toJSONString(diffRows, true));

  }

}