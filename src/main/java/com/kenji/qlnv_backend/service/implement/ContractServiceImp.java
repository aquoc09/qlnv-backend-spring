package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.ContractRequest;
import com.kenji.qlnv_backend.dto.response.ContractResponse;
import com.kenji.qlnv_backend.entity.Contract;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.ContractMapper;
import com.kenji.qlnv_backend.repository.ContractRepository;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.service.ContractService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ContractServiceImp implements ContractService {
    ContractRepository contractRepository;
    EmployeeRepository employeeRepository;
    ContractMapper contractMapper;

    @Override
    public ContractResponse create(ContractRequest request) {
        Contract contract = contractMapper.toContract(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            contract.setEmployee(employee);
        }

        return contractMapper.toContractResponse(contractRepository.save(contract));
    }

    @Override
    public ContractResponse get(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_EXISTED));
        return contractMapper.toContractResponse(contract);
    }

    @Override
    public List<ContractResponse> getAll() {
        List<ContractResponse> responses = new ArrayList<>();
        contractRepository.findAll()
                .forEach(contract -> responses.add(contractMapper.toContractResponse(contract)));
        return responses;
    }

    @Override
    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    @Override
    public ContractResponse update(Long id, ContractRequest request) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CONTRACT_NOT_EXISTED));

        if (request.getEmployeeId() != null) {
            if (contract.getEmployee() == null ||
                    !Objects.equals(contract.getEmployee().getId(), request.getEmployeeId())) {
                Employee employee = employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                contract.setEmployee(employee);
            }
        }

        contractMapper.updateContract(contract, request);

        return contractMapper.toContractResponse(contractRepository.save(contract));
    }
}
