package app.handler;

import pakages.floppy.http.framework.HttpMethods;
import pakages.floppy.http.framework.annotation.*;
import pakages.floppy.http.framework.exception.RequestHandleException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class CoursesHandler {

  @RequestMapping(method = HttpMethods.POST, path = "/courses")
  public void postCourses (OutputStream response,
                           @RequestHeader("Username") String username,
                           @RequestBody(value = "body") byte[] test){
    ByteArrayInputStream in = new ByteArrayInputStream(test);
    ObjectInputStream body_in = null;
    try {
      body_in = new ObjectInputStream(in);
    } catch (IOException e) {
      e.printStackTrace();
    }

    String body = "";
    try {
      body = "{\"body\": " + body_in.readObject() +"}" +
              "\n{\"username\": [" + username + "]}";
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    try {
      response.write(
              (
                      // language=HTTP
                      "HTTP/1.1 201 Created\r\n" +
                              "Content-Length: " + body.length() + "\r\n" +
                              "Content-Type: application/json\r\n" +
                              "Connection: close\r\n" +
                              "\r\n" +
                              body
              ).getBytes(StandardCharsets.UTF_8)
      );
    } catch (IOException e) {
      throw new RequestHandleException(e);
    }
  }

  @RequestMapping(method = HttpMethods.GET, path = "/courses")
  public void getCourses (OutputStream response,
                          @RequestHeader("Username") String username,
                          @RequestParam("test") Map<String, List<String>> test) {

    final var body = "{\"courses\": []}" +
                     "\n{\"username\": [" + username + "]}" +
                     "\n{\"query\": [" + test + "]}";

    try {
      response.write(
          (
              // language=HTTP
              "HTTP/1.1 200 OK\r\n" +
                  "Content-Length: " + body.length() + "\r\n" +
                  "Content-Type: application/json\r\n" +
                  "Connection: close\r\n" +
                  "\r\n" +
                  body
          ).getBytes(StandardCharsets.UTF_8)
      );
    } catch (IOException e) {
      throw new RequestHandleException(e);
    }
  }
}
