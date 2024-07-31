package org.task_manager.model.dao;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.task_manager.model.entity.Subtask;
import org.task_manager.model.entity.Task;

public class DataSource {

    private final static String DATABASE = "task_manager";
    private final static String MONGODB_URI = "mongodb://localhost:27017";

    public static Datastore getDataStore() {
        final Datastore datastore = Morphia.createDatastore(MongoClients.create(MONGODB_URI), DATABASE);
        datastore.getMapper().map(Task.class, Subtask.class);
        datastore.ensureIndexes();
        return datastore;
    }

}
