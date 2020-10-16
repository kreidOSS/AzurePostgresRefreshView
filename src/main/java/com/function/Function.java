package com.function;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                name = "req",
                methods = {HttpMethod.GET, HttpMethod.POST},
                authLevel = AuthorizationLevel.ANONYMOUS)
                HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        String url = "jdbc:postgresql://localhost:5432/testdb";
        String user = "user12";
        String password = "34klq*";
        Connection c = null;        
        try {
           Class.forName("org.postgresql.Driver");
           c = DriverManager.getConnection(url, user, password);
           stmt = c.createStatement();
            String sql = "CREATE TABLE COMPANY " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (SQLException e) {

           return request.createResponseBuilder(HttpStatus.OK).body(e.getMessage()).build();
        }
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
    
    }
}


/*try (Connection con = DriverManager.getConnection(url, user, password);
        PreparedStatement pst = con.prepareStatement("SELECT * FROM authors");
        ResultSet rs = pst.executeQuery()) {

    while (rs.next()) {
    
        System.out.print(rs.getInt(1));
        System.out.print(": ");
        System.out.println(rs.getString(2));
    }

} catch (SQLException ex) {

    Logger lgr = Logger.getLogger(JavaPostgreSqlRetrieve.class.getName());
    lgr.log(Level.SEVERE, ex.getMessage(), ex);
}*/