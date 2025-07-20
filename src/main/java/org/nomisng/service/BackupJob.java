package org.nomisng.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.nomisng.config.ContextProvider;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
@RequiredArgsConstructor
public class BackupJob implements Job {
    private final Logger LOG = LoggerFactory.getLogger(BackupJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.debug("Running backup job...");
        BackupService backupService = ContextProvider.getBean(BackupService.class);
        backupService.backupPGSQL(false);
        backupService.cleanupBackup();
    }
}
