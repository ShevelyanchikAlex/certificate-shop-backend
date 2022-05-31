package com.epam.esm.audit;

import com.epam.esm.domain.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.Date;

public class AuditListener {
    private static final String PRE_INSERT_OPERATION = "Pre insert";
    private static final String POST_INSERT_OPERATION = "Post insert";
    private static final String PRE_UPDATE_OPERATION = "Pre update";
    private static final String POST_UPDATE_OPERATION = "Post update";
    private static final String PRE_REMOVE_OPERATION = "Pre remove";
    private static final String POST_REMOVE_OPERATION = "Post remove";
    private static final String ID = "id";

    private final Logger logger = LoggerFactory.getLogger(AuditListener.class);

    @PrePersist
    private void prePersistAudit(AbstractEntity abstractEntity) {
        logger.info("{}", getMessage(PRE_INSERT_OPERATION, abstractEntity));
    }

    @PostPersist
    private void postPersistAudit(AbstractEntity abstractEntity) {
        logger.info("{}", getMessage(POST_INSERT_OPERATION, abstractEntity));
    }

    @PreUpdate
    private void preUpdateAudit(AbstractEntity abstractEntity) {
        logger.info("{}", getMessage(PRE_UPDATE_OPERATION, abstractEntity));
    }

    @PostUpdate
    private void postUpdateAudit(AbstractEntity abstractEntity) {
        logger.info("{}", getMessage(POST_UPDATE_OPERATION, abstractEntity));
    }

    @PreRemove
    private void preRemoveAudit(AbstractEntity abstractEntity) {
        logger.info("{}", getMessage(PRE_REMOVE_OPERATION, abstractEntity));
    }

    @PostRemove
    private void postRemoveAudit(AbstractEntity abstractEntity) {
        logger.info("{}", getMessage(POST_REMOVE_OPERATION, abstractEntity));
    }

    private String getMessage(String operation, AbstractEntity abstractEntity) {
        return String.format("%s, %s, %s: %d, %tc", operation, abstractEntity.getClass(), ID, abstractEntity.getId(), new Date());
    }
}
