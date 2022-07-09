package com.example.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.example.utils.DiffUtil.Diff;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;

public class JsonCompareTest {

  @Test
  void testSetJsonCompareTest() throws Exception {
    String expected = FileUtil.readString(ResourceUtil.getResource("json1.json").getFile(),
        StandardCharsets.UTF_8);
    String actual = FileUtil.readString(ResourceUtil.getResource("json2.json").getFile(),
        StandardCharsets.UTF_8);
    List<Diff> diffs = DiffUtil.diffJson(actual, expected);
    DiffUtil.diffJson( expected,actual).forEach(diffs::add);

    System.out.println(JSON.toJSONString(diffs,true));
  }
}