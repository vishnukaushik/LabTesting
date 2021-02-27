package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HMODULE;
import com.sun.jna.platform.win32.WinDef.LRESULT;
import com.sun.jna.platform.win32.WinDef.WPARAM;
import com.sun.jna.platform.win32.WinDef.LPARAM;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.KBDLLHOOKSTRUCT;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import com.sun.jna.platform.win32.WinUser.MSG;
import com.sun.jna.Pointer;

public class Main extends Application {

    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
    private static User32 lib;

    @Override
    public void start(Stage primaryStage) throws Exception{
        blockKeys();
        Parent root = FXMLLoader.load(getClass().getResource("firstScene.fxml"));
        //Parent second = FXMLLoader.load(getClass().getResource("Secondary.fxml"));
        primaryStage.setTitle("Login Page");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(null);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.show();
    }

    public static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.indexOf( "win" ) >= 0);
    }

    public static void blockKeys() {
        if (isWindows()) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    lib = User32.INSTANCE;
                    HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
                    keyboardHook = new LowLevelKeyboardProc() {
                        public LRESULT callback(int nCode, WPARAM wParam, KBDLLHOOKSTRUCT info) {
                            if (nCode >= 0) {
                                switch (info.vkCode){
                                    case 0x14: // capslock
                                    case 0x5B: // Windows key
                                    case 0x73: //F4 key
                                    case 0x09: //tab key
                                    case 0x5C: // right windows key
                                        return new LRESULT(1);
                                    default: //do nothing
                                }
                            }
                            Pointer ptr = info.getPointer();
                            long peer = Pointer.nativeValue(ptr);
                            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, new LPARAM(peer));

                        }
                    };
                    hhk = lib.SetWindowsHookEx(13, keyboardHook, hMod, 0);
                    int result;
                    MSG msg = new MSG();
                    while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
                        if (result == -1) {
                            break;
                        } else {
                            lib.TranslateMessage(msg);
                            lib.DispatchMessage(msg);
                        }
                    }
                    lib.UnhookWindowsHookEx(hhk);
                }
            }).start();
        }
    }


    public static void main(String[] args) {
        String username = "root",password="root", url = "jdbc:mysql://localhost:3306/Lab";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Successful");
        }
        catch (SQLException e)
        {
            System.out.println("Connection failed!!!");

        }
        launch(args);
    }
}

