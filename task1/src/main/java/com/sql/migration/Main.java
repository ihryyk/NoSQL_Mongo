package com.sql.migration;

import com.sql.migration.service.MigrationService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        MigrationService migrationService = new MigrationService();
        migrationService.migrate();
    }
}