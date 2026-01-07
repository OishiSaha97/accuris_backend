package com.datasoft.bkash.ea.model.enums;

public enum PlanStatus {
    BOOKED(1), PLANNED(2), ASSESSED(3), VALIDATED(4), ANALYSED(5), APPROVED(6), EXPIRED(7), RESIDUE(8),CLOSED(9),AVAILABLE(10),CANCEL(11),RESCHEDULE(12),ONGOING(13);

    private Integer status;

    PlanStatus(Integer status) {
        this.status = status;
    }

    public PlanStatus getByStatusNumber(Integer status) {
        this.setStatus(status);
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
