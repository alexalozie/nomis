package org.nomisng.config;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.net.URI;
import java.util.Arrays;

@Component
public class ApplicationInitializer {
    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ApplicationInitializer.class);
    //</editor-fold>

    @EventListener
    @Async
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        int port = event.getApplicationContext().getWebServer().getPort();
        String url = "http://localhost:" + port;
        new BrowserLaunch().openURL(url);
    }


//<editor-fold defaultstate="collapsed" desc="delombok">
//</editor-fold>
    static class BrowserLaunch {
        //<editor-fold defaultstate="collapsed" desc="delombok">
        final String[] browsers = {"x-www-browser", "google-chrome", "firefox", "opera", "epiphany", "konqueror", "conkeror", "midori", "kazehakase", "mozilla"};
        //</editor-fold>

        // Open the specified web page in the user's default browser
        public void openURL(String url) {
            try {
                //attempt to use Desktop library from JDK 1.6+
                Class<?> d = Class.forName("java.awt.Desktop");
                d.getDeclaredMethod("browse", new Class<?>[] {java.net.URI.class}).invoke(d.getDeclaredMethod("getDesktop").invoke(null), URI.create(url));
            } catch (Exception ignore) {
                //library not available or failed
                try {
                    if (SystemUtils.IS_OS_MAC) {
                        Class.forName("com.apple.eio.FileManager").getDeclaredMethod("openURL", new Class<?>[] {String.class}).invoke(null, url);
                    } else if (SystemUtils.IS_OS_WINDOWS) {
                        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
                    } else {
                        //assume Unix or Linux
                        String browser = null;
                        for (String b : browsers) {
                            if (browser == null && Runtime.getRuntime().exec(new String[] {"which", b}).getInputStream().read() != -1) {
                                Runtime.getRuntime().exec(new String[] {browser = b, url});
                            }
                        }
                        if (browser == null) {
                            throw new Exception(Arrays.toString(browsers));
                        }
                    }
                } catch (Exception ex) {
                    log.error("Could not open browser: {}", ex.getMessage());
                }
            }
        }
    }

    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    public ApplicationInitializer() {
    }
    //</editor-fold>
}
