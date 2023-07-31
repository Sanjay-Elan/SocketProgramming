import java.net.*;
import java.io.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MultiServer {
    public static void main(String[] args) throws IOException {

        Logger logger = Logger.getLogger("Logs");
        FileHandler fh;
        fh = new FileHandler(System.getProperty("user.dir") + System.getProperty("file.separator") + "Logs.log", true);
        logger.addHandler(fh);
        SimpleFormatter sf = new SimpleFormatter();
        fh.setFormatter(sf);

        logger.info("Logger Init");

        if (args.length != 1) {
            System.err.println("Usage: java KKMultiServer <port number>");
            System.exit(1);
        }

        int portNumber = 0;
        try {
            portNumber = Integer.parseInt(args[0]);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Exception", e);
        }
//        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                new MultiServerThread(serverSocket.accept()).start();
            }
        }catch (Exception e) {
            logger.log(Level.WARNING,"Exception",e);
        }
        logger.info("Server turned off");
    }
}
