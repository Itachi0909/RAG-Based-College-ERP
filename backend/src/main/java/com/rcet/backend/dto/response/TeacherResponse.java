package com.rcet.backend.dto.response;

import com.rcet.backend.entity.Designation;
import com.rcet.backend.entity.TeacherStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherResponse {

    private Long id;

    private String employeeId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String email;

    private String phone;

    private String qualification;

    private Designation designation;

    private TeacherStatus status;

    // Department
    private Long departmentId;

    private String departmentName;

    // Branch
    private Long branchId;

    private String branchName;

    private boolean active;
}