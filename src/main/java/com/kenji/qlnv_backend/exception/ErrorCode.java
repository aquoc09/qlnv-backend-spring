package com.kenji.qlnv_backend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid key exception.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    INVALID_USERNAME(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),

    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You have no permission", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED(1010, "Token has expired", HttpStatus.UNAUTHORIZED),

    INVALID_DOB(1008, "Invalid age, you must be at least {min} y.o", HttpStatus.BAD_REQUEST),
    INVALID_MAIL(1009, "Invalid mail, example: example@gmail.com", HttpStatus.BAD_REQUEST),

    PERMISSION_EXISTED(2001, "Permission existed", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(2002, "Permission not existed", HttpStatus.NOT_FOUND),

    ROLE_EXISTED(3001, "Role existed", HttpStatus.BAD_REQUEST),
    ROLE_NOT_EXISTED(3002, "Role not existed", HttpStatus.NOT_FOUND),
    ROLES_EMPTY(3003, "Role request is empty", HttpStatus.BAD_REQUEST),

    EMPLOYEE_NOT_EXISTED(4001, "Employee not existed", HttpStatus.NOT_FOUND),

    DEPARTMENT_NOT_EXISTED(5001, "Department not existed", HttpStatus.NOT_FOUND),

    ATTENDANCE_NOT_EXISTED(5100, "Attendance is not existed", HttpStatus.NOT_FOUND),
    ATTENDANCE_INVALID_DAY(5101, "Attendance's day is invalid day", HttpStatus.BAD_REQUEST),
    ATTENDANCE_CHECKED_OUT(5102, "Attendance has already checked out", HttpStatus.BAD_REQUEST),

    CONTRACT_NOT_EXISTED(5200, "Contract not existed", HttpStatus.NOT_FOUND ),
    LEAVE_NOT_EXISTED(5300, "Leave not existed", HttpStatus.NOT_FOUND ),
    SALARY_NOT_EXISTED(5400, "Salary not existed", HttpStatus.NOT_FOUND ),
    REWARD_DISCIPLINE_NOT_EXISTED(5500, "Reward discipline not existed", HttpStatus.NOT_FOUND ),
    LEAVE_RECORD_NOT_EXISTED(5600, "Leave record not existed", HttpStatus.NOT_FOUND ),

    LEAVE_BALANCE_NOT_EXISTED(5700, "Leave balance is not existed", HttpStatus.NOT_FOUND ),
    LEAVE_RECORD_OVER_TOTAL_DAYS(5701, "Num of day off has over total days", HttpStatus.BAD_REQUEST ),

    ;

    ErrorCode(int code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.statusCode = status;
    }

    int code;
    String message;
    HttpStatusCode statusCode;
}
