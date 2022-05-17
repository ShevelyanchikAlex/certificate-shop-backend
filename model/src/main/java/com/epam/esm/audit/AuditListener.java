package com.epam.esm.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

public class AuditListener {
    private static final String PRE_INSERT_OPERATION = "Pre insert: ";
    private static final String POST_INSERT_OPERATION = "Post insert: ";

    private static final String PRE_UPDATE_OPERATION = "Pre update: ";
    private static final String POST_UPDATE_OPERATION = "Post update: ";

    private static final String PRE_REMOVE_OPERATION = "Pre remove: ";
    private static final String POST_REMOVE_OPERATION = "Post remove: ";

    private final Logger logger = LoggerFactory.getLogger(AuditListener.class);

    @PrePersist
    private void prePersistAudit(Object entity) {
        logger.info(PRE_INSERT_OPERATION + entity);
    }

    @PostPersist
    private void postPersistAudit(Object entity) {
        logger.info(POST_INSERT_OPERATION + entity);
    }

    @PreUpdate
    private void preUpdateAudit(Object entity) {
        logger.info(PRE_UPDATE_OPERATION + entity);
    }

    @PostUpdate
    private void postUpdateAudit(Object entity) {
        logger.info(POST_UPDATE_OPERATION + entity);
    }

    @PreRemove
    private void preRemoveAudit(Object entity) {
        logger.info(PRE_REMOVE_OPERATION + entity);
    }

    @PostRemove
    private void postRemoveAudit(Object entity) {
        logger.info(POST_REMOVE_OPERATION + entity);
    }
}
