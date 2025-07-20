package org.nomisng.config;

import org.apache.commons.io.FileUtils;
import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

public class NodeSyncConfig {
    //<editor-fold defaultstate="collapsed" desc="delombok">
    @SuppressWarnings("all")
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(NodeSyncConfig.class);
    //</editor-fold>

    @PostConstruct
    public void initNodeEngine() {
        try {
            File file = new File("C:/symmetric-server/engines");
            if (file.exists()) {
                FileUtils.cleanDirectory(file);
            }
        } catch (IOException ignored) {
        }
    }
}
