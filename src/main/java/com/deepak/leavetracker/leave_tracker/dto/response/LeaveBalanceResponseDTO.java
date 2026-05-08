package com.deepak.leavetracker.leave_tracker.dto.response;

import com.deepak.leavetracker.leave_tracker.entity.Employee;
import com.deepak.leavetracker.leave_tracker.entity.LeaveType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class LeaveBalanceResponseDTO {

    private LeaveType leaveType;

    private Integer totLeaves;

    private Integer usedLeaves;

    private Integer remLeaves;


    public Integer getRemLeaves() {
        return remLeaves;
    }

    public void setRemLeaves(Integer remLeaves) {
        this.remLeaves = remLeaves;
    }

    public Integer getUsedLeaves() {
        return usedLeaves;
    }

    public void setUsedLeaves(Integer usedLeaves) {
        this.usedLeaves = usedLeaves;
    }

    public Integer getTotLeaves() {
        return totLeaves;
    }

    public void setTotLeaves(Integer totLeaves) {
        this.totLeaves = totLeaves;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

}
