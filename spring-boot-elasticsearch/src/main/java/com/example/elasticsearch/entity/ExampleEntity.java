package com.example.elasticsearch.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
@Data
@Document(indexName = "todo")
public class ExampleEntity {

    @Id
    private String id;

    private String title;

    private String desc;
}
