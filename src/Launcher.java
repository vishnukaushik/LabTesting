import java.lang.reflect.InvocationTargetException;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws UnknownHostException, SQLException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        org.example.sample.Main.main(args);
    }
}
