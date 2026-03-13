package com.kenji.qlnv_backend.service.implement;

import com.kenji.qlnv_backend.dto.request.AttendanceRequest;
import com.kenji.qlnv_backend.dto.response.AttendanceResponse;
import com.kenji.qlnv_backend.entity.Attendance;
import com.kenji.qlnv_backend.entity.Employee;
import com.kenji.qlnv_backend.entity.User;
import com.kenji.qlnv_backend.enums.AttendanceStatus;
import com.kenji.qlnv_backend.enums.WorkingTime;
import com.kenji.qlnv_backend.exception.AppException;
import com.kenji.qlnv_backend.exception.ErrorCode;
import com.kenji.qlnv_backend.mapper.AttendanceMapper;
import com.kenji.qlnv_backend.repository.AttendanceRepository;
import com.kenji.qlnv_backend.repository.EmployeeRepository;
import com.kenji.qlnv_backend.service.AttendanceService;
import com.kenji.qlnv_backend.util.UserUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AttendanceServiceImp implements AttendanceService {
    AttendanceRepository attendanceRepository;
    EmployeeRepository employeeRepository;
    AttendanceMapper attendanceMapper;

    @Override
    public AttendanceResponse create(AttendanceRequest request) {
        Attendance attendance = attendanceMapper.toAttendance(request);

        if (request.getEmployeeId() != null) {
            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
            attendance.setEmployee(employee);
        }

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse get(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_NOT_EXISTED));
        return attendanceMapper.toAttendanceResponse(attendance);
    }

    @Override
    public List<AttendanceResponse> getAll() {
        List<AttendanceResponse> responses = new ArrayList<>();
        attendanceRepository.findAll()
                .forEach(attendance ->
                        responses.add(attendanceMapper.toAttendanceResponse(attendance)));
        return responses;
    }

    private boolean isCheckInLate(LocalTime checkIn, LocalTime time) {
        return time.isAfter(checkIn.plusMinutes(15));
    }

    private boolean isCheckOutEarly(LocalTime checkOut, LocalTime time) {
        return time.isBefore(checkOut.minusMinutes(15));
    }

    @Override
    public AttendanceResponse checkIn() {
        LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        User user = UserUtil.getCurrentUser();
        Employee emp = employeeRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        boolean isLate = isCheckInLate(nowTime, WorkingTime.CHECK_IN.getTime());

        Attendance attendance = Attendance.builder()
                .checkIn(nowTime)
                .workDate(today)
                .employee(emp)
                .isCheckOut(false)
                .status(isLate ? AttendanceStatus.CHECK_IN_LATE : AttendanceStatus.CHECK_IN_ON_TIME)
                .build();

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceResponse checkOut() {
        LocalDate today = LocalDate.now();
        LocalTime nowTime = LocalTime.now();
        User user = UserUtil.getCurrentUser();
        Employee emp = employeeRepository.findByUser(user)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));

        Attendance attendance = attendanceRepository.findByEmployeeAndWorkDate(emp, today)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_NOT_EXISTED));

        if (attendance.isCheckOut())
            throw new AppException(ErrorCode.ATTENDANCE_CHECKED_OUT);
        if (!today.equals(attendance.getWorkDate()))
            throw new AppException(ErrorCode.ATTENDANCE_INVALID_DAY);
        if (isCheckOutEarly(nowTime, WorkingTime.CHECK_OUT.getTime()))
            attendance.setStatus(AttendanceStatus.CHECK_OUT_EARLY);

        attendance.setCheckOut(nowTime);
        attendance.setCheckOut(true);
        int timeWorked = Math.toIntExact(
                Duration.between(attendance.getCheckIn(), attendance.getCheckOut())
                        .toHours());
        attendance.setTimeWorked(timeWorked);

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }

    @Override
    public int getSumWorkingTimeOfEmployee(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        List<Attendance> attendances = attendanceRepository.findAllByEmployee(employee);
        int sum = 0;
        for (Attendance attendance : attendances) {
            if (attendance.isCheckOut()) {
                sum += attendance.getTimeWorked();
            }
        }
        return sum;
    }

    @Override
    public int getCountCheckInLateOfEmployee(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        List<Attendance> attendances = attendanceRepository.findAllByEmployee(employee);
        int count = 0;
        for (Attendance attendance : attendances) {
            if (attendance.isCheckOut() && attendance.getStatus().equals(AttendanceStatus.CHECK_IN_LATE)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<AttendanceResponse> findAllByEmployee(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
        List<Attendance> attendances = attendanceRepository.findAllByEmployee(employee);
        return attendances.stream().map(attendanceMapper::toAttendanceResponse).toList();
    }

    @Override
    public List<AttendanceResponse> findAllByDate(LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findAllByWorkDate(date);
        return attendances.stream().map(attendanceMapper::toAttendanceResponse).toList();
    }

    @Override
    public void delete(Long id) {
        attendanceRepository.deleteById(id);
    }

    @Override
    public AttendanceResponse update(Long id, AttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ATTENDANCE_NOT_EXISTED));

        if (request.getEmployeeId() != null) {
            if (attendance.getEmployee() == null ||
                    !Objects.equals(attendance.getEmployee().getId(), request.getEmployeeId())) {
                Employee employee = employeeRepository.findById(request.getEmployeeId())
                        .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXISTED));
                attendance.setEmployee(employee);
            }
        }

        attendanceMapper.updateAttendance(attendance, request);

        return attendanceMapper.toAttendanceResponse(attendanceRepository.save(attendance));
    }
}
