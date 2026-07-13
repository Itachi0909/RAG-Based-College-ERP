package com.rcet.backend.dto.response;

import com.rcet.backend.entity.NoticePriority;
import com.rcet.backend.entity.TargetAudience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponse {

    private Long id;

    private String title;

    private String description;

    private NoticePriority priority;

    private TargetAudience targetAudience;

    private Long branchId;
    private String branchName;

    private Long semesterId;
    private Integer semesterNumber;

    private Boolean active;
}