package org.task_manager.model.entity;

import dev.morphia.annotations.*;
import dev.morphia.utils.IndexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embedded
@Indexes(@Index(fields = @Field(value = "name", type = IndexType.TEXT)))
public class Subtask {

    private String name;
    private String description;

}
