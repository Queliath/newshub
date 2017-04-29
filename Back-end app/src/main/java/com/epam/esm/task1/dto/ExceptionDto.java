package com.epam.esm.task1.dto;

import java.io.Serializable;

/**
 * DTO class which is used to transfer the exception info.
 *
 * @author Uladzislau Kastsevich
 */
public class ExceptionDto implements Serializable {
    private static final long serialVersionUID = -2180658175923699705L;

    private Integer code;
    private String message;

    public ExceptionDto() {
    }

    public ExceptionDto(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExceptionDto that = (ExceptionDto) o;

        if (code != null ? !code.equals(that.code) : that.code != null) {
            return false;
        }
        return !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExceptionDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
