package org.nomisng.web;

import org.nomisng.service.BackupService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@WithMockUser
public class BackupResourceTest {
//
//    @LocalServerPort
//    private  int port;
//
//    private String getRootUrl() {
//        return "http://localhost:" + port + "/api";
//    }
//    @Autowired
//    private MockMvc restBackupMockMvc;
//
//
//    @Test
//    public void contextLoads() {}
//
//    @Test
//    public void restore() throws Exception {
//        restBackupMockMvc.perform(get(getRootUrl() + "/backup/restore/backup_20220511.sql"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void backup() throws Exception {
//        restBackupMockMvc.perform(get(getRootUrl() + "/backup/backup"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void downloadBackup() throws IOException {
//
//    }
//
//    @Test
//    public void backupAvailable() throws Exception {
//        restBackupMockMvc.perform(get(getRootUrl() + "/backup/backup-available"))
//                .andExpect(status().isOk());
//    }



}
