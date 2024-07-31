package org.task_manager.model.entity;

import dev.morphia.annotations.*;
import dev.morphia.utils.IndexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity("tasks")
@Indexes(@Index(fields = @Field(value = "description", type = IndexType.TEXT)))
public class Task {

    @Id
    private ObjectId id;
    private Date dateOfCreation;
    private Date deadline;
    private String name;
    private String description;
    private List<Subtask> subTasks;
    private String category;

}