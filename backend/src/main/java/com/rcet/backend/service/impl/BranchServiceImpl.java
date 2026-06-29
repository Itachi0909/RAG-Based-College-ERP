package com.rcet.backend.service.impl;

import com.rcet.backend.exception.DuplicateResourceException;
import com.rcet.backend.exception.ResourceNotFoundException;
import com.rcet.backend.dto.request.BranchRequest;
import com.rcet.backend.dto.response.BranchResponse;
import com.rcet.backend.entity.Branch;
import com.rcet.backend.entity.Department;
import com.rcet.backend.repository.BranchRepository;
import com.rcet.backend.repository.DepartmentRepository;
import com.rcet.backend.service.BranchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BranchServiceImpl implements BranchService {

    private final BranchRepository branchRepository;
    private final DepartmentRepository departmentRepository;

    public BranchServiceImpl(BranchRepository branchRepository,
                             DepartmentRepository departmentRepository) {

        this.branchRepository = branchRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public BranchResponse createBranch(BranchRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));

        if (branchRepository.existsByNameAndDepartment(request.getName(), department)) {
            throw new DuplicateResourceException("Branch already exists in this department.");
        }

        Branch branch = new Branch();
        branch.setName(request.getName());
        branch.setDescription(request.getDescription());
        branch.setDepartment(department);

        Branch savedBranch = branchRepository.save(branch);

        return mapToResponse(savedBranch);
    }

    @Override
    public List<BranchResponse> getAllBranches() {

        return branchRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BranchResponse getBranchById(Long id) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found."));

        return mapToResponse(branch);
    }

    @Override
    public BranchResponse updateBranch(Long id, BranchRequest request) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found."));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found."));

        branch.setName(request.getName());
        branch.setDescription(request.getDescription());
        branch.setDepartment(department);

        Branch updatedBranch = branchRepository.save(branch);

        return mapToResponse(updatedBranch);
    }

    @Override
    public void deleteBranch(Long id) {

        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found."));

        branchRepository.delete(branch);
    }

    private BranchResponse mapToResponse(Branch branch) {

        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getDescription(),
                branch.getDepartment().getId(),
                branch.getDepartment().getName()
        );
    }
}