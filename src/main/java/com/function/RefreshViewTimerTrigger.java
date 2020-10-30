package com.function;

import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;


import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
/**
 * Azure Functions with Timer trigger.
 */
public class RefreshViewTimerTrigger {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("RefreshViewTimerTrigger")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "30 * * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());
        String url = "jdbc:postgresql://*your database name here*.postgres.database.azure.com:5432/AdventureWorks?sslmode=require";
        String user = "";
        String password = "";
        Connection c = null;        
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager.getConnection(url, user, password);
           Statement stmt = c.createStatement();
            String sql = "REFRES MATERIALIZED VIEW sales.overall_sales ";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (SQLException e) {

           context.getLogger().info("Error "+ e);
        } catch (Exception e){
            context.getLogger().info("Error "+ e);
        }
    }
    
}
