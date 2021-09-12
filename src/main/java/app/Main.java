package app;

import lombok.extern.java.Log;
import pakages.floppy.http.framework.Server;
import pakages.floppy.http.framework.resolver.argument.*;




@Log
public class Main {
  public static void main(String[] args) {
    final var server = new Server();
    server.autoRegisterHandlers("app");
    server.addArgumentResolver(
            new RequestHandlerMethodArgumentResolver(),
            new ResponseHandlerMethodArgumentResolver(),
            new RequestHeaderHandlerMethodArgumentResolver(),
            new RequestParamHandlerMethodArgumentResolver(),
            new RequestBodyHandlerMethodArgumentResolver()
    );
//    new Thread(() -> {
//      try {
//        Thread.sleep(20_000);
//        server.stop();
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    }).start();
    server.listen(8080);
  }
}
