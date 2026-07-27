package com.rcet.backend.controller.teacher;

import com.rcet.backend.dto.request.MarksBulkRequest;
import com.rcet.backend.dto.response.MarksBulkResponse;
import com.rcet.backend.dto.response.StudentMarksResponse;
import com.rcet.backend.dto.response.TeacherSubjectResultResponse;
import com.rcet.backend.service.TeacherResultService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher/results")
public class TeacherResultController {

    private final TeacherResultService teacherResultService;

    public TeacherResultController(
            TeacherResultService teacherResultService) {

        this.teacherResultService = teacherResultService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<TeacherSubjectResultResponse>>
    getMySubjects() {

        return ResponseEntity.ok(
                teacherResultService.getMySubjects());
    }

    @GetMapping("/students/{teacherSubjectId}")
    public ResponseEntity<List<StudentMarksResponse>>
    getStudentsForMarks(
            @PathVariable Long teacherSubjectId) {

        return ResponseEntity.ok(
                teacherResultService.getStudentsForMarks(
                        teacherSubjectId));
    }

    @PostMapping("/save")
    public ResponseEntity<MarksBulkResponse>
    saveMarks(
            @Valid @RequestBody MarksBulkRequest request) {

        return ResponseEntity.ok(
                teacherResultService.saveMarks(request));
    }

    @PostMapping("/submit/{teacherSubjectId}")
    public ResponseEntity<String>
    submitMarks(
            @PathVariable Long teacherSubjectId) {

        teacherResultService.submitMarks(
                teacherSubjectId);

        return ResponseEntity.ok(
                "Marks submitted successfully.");
    }
}