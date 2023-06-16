package com.mscompra;


import org.testcontainers.containers.PostgreSQLContainer;

public class PostGreSQLTestContainer extends PostgreSQLContainer<PostGreSQLTestContainer> {

    public static final String IMAGE_VERSION = "postgres:11.1";
    public static final String DATABASE_NAME = "test";
    public static PostgreSQLContainer container;

    public PostGreSQLTestContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PostGreSQLTestContainer()
                    .withDatabaseName(DATABASE_NAME);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        super.stop();
    }

}
