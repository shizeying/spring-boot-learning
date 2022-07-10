package com.example.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.example.utils.DiffUtil.DiffJson;
import com.github.difflib.DiffUtils;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;
import com.github.difflib.text.DiffRow;
import com.github.fge.jsonpatch.JsonPatchException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiffUtilsTest {

  private List<String> file1;
  private List<String> file2;
  private String source;
  private String target;

  @BeforeEach
  public void BeforeEach() throws IOException {
    file1 = FileUtil.readUtf8Lines(ResourceUtil.getResource("diff1").getFile());
    file2 = FileUtil.readUtf8Lines(ResourceUtil.getResource("diff2").getFile());
    source = FileUtil.readString(ResourceUtil.getResource("json1.json").openConnection()
        .getURL(), StandardCharsets.UTF_8.toString());
    target = FileUtil.readString(ResourceUtil.getResource("json2.json").openConnection().getURL(),
        StandardCharsets.UTF_8.toString());
  }

  @Test
  void test() {
    //行比较器，原文件删除的内容用"~"包裹，对比文件新增的内容用"**"包裹
    String original = String.join("\n", file1);
    String revised = String.join("\n", file2);
    List<DiffRow> diffRows = DiffUtil.diffRowsByString(original, revised, "\n");
    System.out.println(JSON.toJSONString(diffRows, true));

  }

  @Test
  void test2() {
    String original = String.join("\n", file1);
    String revised = String.join("\n", file2);
//    DiffAlgorithmListener
    Patch<String> stringPatch = DiffUtils.diff(file1, file2);
    for (AbstractDelta<String> delta : stringPatch.getDeltas()) {
      System.out.println(delta);
    }

  }

  @Test
  void test3() throws JsonPatchException {
    List<DiffJson> diffJsons = DiffUtil.diffJsonByString(source, target);
    System.out.println(JSON.toJSONString(diffJsons,true));
  }

}