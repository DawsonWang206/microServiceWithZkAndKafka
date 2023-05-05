package com.dawson.client1.common.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;

/**
 * 异常处理器
 */
@RestControllerAdvice
public class RRExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RRException.class)
    public R handleRRException(RRException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        return R.error(RRExceptionEnum.SYSTEM_ERROR.getCode(), e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(Exception e) {
        return R.error(RRExceptionEnum.PATH_NOT_FOUND);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        return R.error(RRExceptionEnum.DATA_ALREADY_EXISTS);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return R.error(RRExceptionEnum.REQUEST_METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return R.error(RRExceptionEnum.INVALID_PARAMETER.getCode(), RRExceptionEnum.INVALID_PARAMETER.getMsg() + ":表单不是一个json");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String[] errorMessage = e.getMessage().split("default message \\[");
        errorMessage[0] = errorMessage[errorMessage.length - 1].split("]")[0];
        return R.error(RRExceptionEnum.INVALID_PARAMETER.getCode(), RRExceptionEnum.INVALID_PARAMETER.getMsg() + ":" + errorMessage[0]);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public R handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return R.error(RRExceptionEnum.INVALID_PARAMETER.getCode(), RRExceptionEnum.INVALID_PARAMETER.getMsg() + ":参数类型转换失败");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public R handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return R.error(RRExceptionEnum.INVALID_PARAMETER.getCode(), RRExceptionEnum.INVALID_PARAMETER.getMsg() + ":请检查Content-type是否正确");
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public R handleServletRequestBindingException(ServletRequestBindingException e) {
        logger.error(e.getMessage());
        return R.error(RRExceptionEnum.INVALID_PARAMETER);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public R handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        logger.error(e.getMessage());
        return R.error(RRExceptionEnum.INVALID_PARAMETER);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public R handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        logger.error(e.getMessage());
        return R.error(RRExceptionEnum.INVALID_PARAMETER);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public R handleConstraintViolationException(ConstraintViolationException e) {
        StringBuffer message = new StringBuffer(e.getMessage());
        logger.error(message.toString());
        String[] messageSplit = message.toString().split(": ");
        message = new StringBuffer();
        if (messageSplit.length >= 2) {
            for (int i = 1; i < messageSplit.length; i++) {
                message.append(messageSplit[i]);
            }
        }
        return R.error(RRExceptionEnum.INVALID_PARAMETER.getCode(), RRExceptionEnum.INVALID_PARAMETER.getMsg() + ":" + message);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public R handleDateTimeParseException(DateTimeParseException e) {
        return R.error(RRExceptionEnum.INVALID_PARAMETER.getCode(), RRExceptionEnum.INVALID_PARAMETER.getMsg() + ":时间格式错误");
    }

    @ExceptionHandler(NumberFormatException.class)
    public R handleNumberFormatException(NumberFormatException e) {
        logger.error(e.getMessage());
        return R.error(RRExceptionEnum.INVALID_PARAMETER);
    }

    @ExceptionHandler(NullPointerException.class)
    public R handlerNullPointerException(NullPointerException e) {
        logger.error(e.getMessage(), e);
        return R.error(RRExceptionEnum.NULL_POINTER);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        logger.error(e.getMessage(), e);
        return R.error(RRExceptionEnum.SYSTEM_ERROR);
    }

}
