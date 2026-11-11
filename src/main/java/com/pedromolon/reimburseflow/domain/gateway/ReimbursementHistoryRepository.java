package com.pedromolon.reimburseflow.domain.gateway;

import com.pedromolon.reimburseflow.domain.ReimbursementHistory;

public interface ReimbursementHistoryRepository {
    ReimbursementHistory save(ReimbursementHistory reimbursementHistory);
}
