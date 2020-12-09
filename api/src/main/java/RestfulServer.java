import spark.Spark;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestfulServer {
    private final Logger logger = LoggerFactory.getLogger(RestfulServer.class);

    public RestfulServer() {
        this.configureRestfulApiServer();
        this.processRestfulApiRequests();
    }

    private void configureRestfulApiServer() {
        Spark.port(8080);
        logger.info("Server configured to listen on port 8080");
    }

    private void processRestfulApiRequests() {
        Spark.staticFileLocation("/public");
        Spark.get("/", this::displayMap);
        Spark.get("/data", this::getData);
        Spark.post("/data", this::getData);
    }

    private String displayMap(Request request, Response response) {
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.status(200);
        response.redirect("index.html");

        return "{}";
    }


    private String convertResult(ResultSet rs) throws SQLException {
        String data = "";
        while (rs.next()){
            if (data.isEmpty()) {
                data = "[";
            }
            else {
                data = data + ",";
            }
            String state = rs.getString("Province_State");
            String lat = rs.getString("Lat");
            String long_ = rs.getString("Long_");
            int total_deaths = rs.getInt("Total_Deaths");
            String row = "{\"Province_State\":\""+state+"\",\"Lat\":\""+lat+"\",\"Long_\":\""+long_+"\",\"Total_Deaths\":\""+total_deaths+"\"}";
            data = data + row;
        }
        if (data.isEmpty()) {
            data = "[]";
        }
        else {
            data = data + "]";
        }
        return data;
    }

    private String getData(Request request, Response response) {
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.status(200);

        logger.info(request.body());
        
        String url = "jdbc:mysql://localhost/covid";
        Connection conn = null;
        Statement stmt = null;
        String data = "{}";
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            logger.info("Connection to database has been established.");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM covid;");
            data = convertResult(rs);
            
        }
        catch(SQLException e){
            logger.info(e.getMessage());
        }
        try {
            if(conn != null)
                conn.close();
        }
        catch(SQLException e){
            // connection close failed.
            logger.info(e.getMessage());
        }
        logger.info("Connection to database has been closed.");
        return data;
    }

    public static void main(String[] programArgs) {
        RestfulServer restfulServer = new RestfulServer();
    }
}
