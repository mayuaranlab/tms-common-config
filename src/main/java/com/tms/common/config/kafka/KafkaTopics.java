package com.tms.common.config.kafka;

/**
 * Centralized Kafka topic names for the Trade Management System.
 * All services should use these constants for consistency.
 */
public final class KafkaTopics {

    private KafkaTopics() {
        // Utility class - prevent instantiation
    }

    // ==========================================
    // TRADE TOPICS
    // ==========================================

    /** Raw trades from OMS */
    public static final String OMS_TRADES_RAW = "oms.trades.raw";

    /** Validated trades ready for enrichment */
    public static final String TRADES_VALIDATED = "trades.validated";

    /** Enriched trades with reference data */
    public static final String TRADES_ENRICHED = "trades.enriched";

    /** Rejected trades (failed validation) */
    public static final String TRADES_REJECTED = "trades.rejected";

    // ==========================================
    // POSITION TOPICS
    // ==========================================

    /** Position updates */
    public static final String POSITIONS_UPDATED = "positions.updated";

    // ==========================================
    // RISK TOPICS
    // ==========================================

    /** Risk alerts and breaches */
    public static final String RISK_ALERTS = "risk.alerts";

    // ==========================================
    // SETTLEMENT TOPICS
    // ==========================================

    /** Settlement instructions created */
    public static final String SETTLEMENTS_PENDING = "settlements.pending";

    /** Settlement events (created, confirmed, failed) */
    public static final String SETTLEMENTS = "settlements";

    /** Settlement status changes */
    public static final String SETTLEMENTS_STATUS = "settlements.status";

    // ==========================================
    // NOTIFICATION TOPICS
    // ==========================================

    /** Notification requests */
    public static final String NOTIFICATIONS = "notifications";

    // ==========================================
    // AUDIT TOPICS
    // ==========================================

    /** Audit log events */
    public static final String AUDIT_LOG = "audit.log";

    // ==========================================
    // DEAD LETTER QUEUES
    // ==========================================

    public static final String DLQ_SUFFIX = ".dlq";

    public static String getDlqTopic(String topic) {
        return topic + DLQ_SUFFIX;
    }

    public static final String TRADES_VALIDATED_DLQ = TRADES_VALIDATED + DLQ_SUFFIX;
    public static final String TRADES_ENRICHED_DLQ = TRADES_ENRICHED + DLQ_SUFFIX;
    public static final String POSITIONS_UPDATED_DLQ = POSITIONS_UPDATED + DLQ_SUFFIX;
    public static final String SETTLEMENTS_PENDING_DLQ = SETTLEMENTS_PENDING + DLQ_SUFFIX;
}
