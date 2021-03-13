package sample;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.HHOOK;
import com.sun.jna.platform.win32.WinUser.LowLevelKeyboardProc;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicBoolean;

public class Kiosk {

    private static HHOOK hhk;
    private static LowLevelKeyboardProc keyboardHook;
    private static User32 lib;
    public static Thread kiosk_thread;
    public static boolean kiosk_breaker= false;


    public static boolean isWindows(){
        String os = System.getProperty("os.name").toLowerCase();
        return (os.contains("win"));
    }

    public static void kiosk(Stage primaryStage)
    {
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint(null);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }



    public static void blockKeys() {
        if(kiosk_breaker==true){
            System.exit(1);
        }
        if (isWindows()) {
            kiosk_thread=new Thread(new Runnable() {

                @Override
                public void run() {
                    lib = User32.INSTANCE;
                    WinDef.HMODULE hMod = Kernel32.INSTANCE.GetModuleHandle(null);
                    keyboardHook = new LowLevelKeyboardProc() {
                        public WinDef.LRESULT callback(int nCode, WinDef.WPARAM wParam, WinUser.KBDLLHOOKSTRUCT info) {
                            if (nCode >= 0) {
                                switch (info.vkCode){
                                    case 0x14: // capslock
                                    case 0x5B: // Windows key
                                    case 0x73: //F4 key
                                    case 0x09: //tab key
                                    case 0x5C: // right windows key
                                        return new WinDef.LRESULT(1);
                                    default: //do nothing
                                }
                            }
                            Pointer ptr = info.getPointer();
                            long peer = Pointer.nativeValue(ptr);
                            return User32.INSTANCE.CallNextHookEx(hhk, nCode, wParam, new WinDef.LPARAM(peer));

                        }
                    };
                    hhk = lib.SetWindowsHookEx(13, keyboardHook, hMod, 0);

                    int result;
                    WinUser.MSG msg = new WinUser.MSG();

                    while ((result = lib.GetMessage(msg, null, 0, 0)) != 0) {
                        System.out.println("running while + ");
                        System.out.print(kiosk_thread);
                        if (result == -1) {
                            break;
                        }
                        else if(kiosk_breaker){
                            System.out.println("i am in else if loop");
                            break;
                        }
                        else {
                            lib.TranslateMessage(msg);
                            lib.DispatchMessage(msg);
                        }
                    }

                    lib.UnhookWindowsHookEx(hhk);

                }
            });
            kiosk_thread.start();
        }
    }

    public static void unblockKey() {
        if (isWindows() && lib != null) {
            lib.UnhookWindowsHookEx(hhk);
        }
    }
}
