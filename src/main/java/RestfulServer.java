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
        Spark.get("/data", this::getData);
        Spark.get("/", this::echoRequest);
        Spark.post("/", this::echoRequest);
    }

    private String echoRequest(Request request, Response response) {
        response.type("application/json");
        response.header("Access-Control-Allow-Origin", "*");
        response.status(200);
        response.redirect("index.html");

        return HttpRequestToJson(request);
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
        
        String url = "jdbc:mysql://localhost/covid";
        Connection conn = null;
        Statement stmt = null;
        String data = "{}";
        try {
            conn = DriverManager.getConnection(url, "root", "");
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

    private String HttpRequestToJson(Request request) {
        logger.info(request.body());
        return "{\n" + "\"attributes\":\"" + request.attributes() + "\",\n" + "\"body\":\"" + request.body() + "\",\n"
                + "\"contentLength\":\"" + request.contentLength() + "\",\n" + "\"contentType\":\""
                + request.contentType() + "\",\n" + "\"contextPath\":\"" + request.contextPath() + "\",\n"
                + "\"cookies\":\"" + request.cookies() + "\",\n" + "\"headers\":\"" + request.headers() + "\",\n"
                + "\"host\":\"" + request.host() + "\",\n" + "\"ip\":\"" + request.ip() + "\",\n" + "\"params\":\""
                + request.params() + "\",\n" + "\"pathInfo\":\"" + request.pathInfo() + "\",\n" + "\"serverPort\":\""
                + request.port() + "\",\n" + "\"protocol\":\"" + request.protocol() + "\",\n" + "\"queryParams\":\""
                + request.queryParams() + "\",\n" + "\"requestMethod\":\"" + request.requestMethod() + "\",\n"
                + "\"scheme\":\"" + request.scheme() + "\",\n" + "\"servletPath\":\"" + request.servletPath() + "\",\n"
                + "\"session\":\"" + request.session() + "\",\n" + "\"uri()\":\"" + request.uri() + "\",\n"
                + "\"url()\":\"" + request.url() + "\",\n" + "\"userAgent\":\"" + request.userAgent() + "\"\n" + "}";
    }

    public static void main(String[] programArgs) {
        RestfulServer restfulServer = new RestfulServer();
    }
}
