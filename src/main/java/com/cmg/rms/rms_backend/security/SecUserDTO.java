package com.cmg.rms.rms_backend.security;

public record SecUserDTO(
    Long userId,
    String userFullName,
    String userFirstName,
    String userLastName,
    String userEmail,
    String userContactNo,
    String userDesignation,
    Long requesterSeqno,
    String requesterCode,
    String requesterDesc,
    String groupName,
    Long unitGroupLevel,
    Long deptSeqno,
    String deptDesc,
    String financialYear,
    String facilityCategory,
    Long prescriberSeqno) {}
