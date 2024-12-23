package com.self.model.exception;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author weiye.li
 */
@Slf4j
@Accessors(chain = true)
@Data
public class KeKaiMallException extends RuntimeException {

    private String code;
    private String msg;

    public KeKaiMallException() {

    }

    public KeKaiMallException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public KeKaiMallException(String code, String msg, Exception e) {
        super(e);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return String.format("%s: %s", code, msg);
    }

    public KeKaiMallException clone(String msg) {
        return new KeKaiMallException().setCode(this.code).setMsg(msg);
    }

    public static <T> T runtime(Runnable runnable) {
        try {
            return (T) runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void runtime(RunnableNoReturn runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ignore(RunnableNoReturn runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static <T> T ignore(Runnable runnable) {
        try {
            return (T) runnable.run();
        } catch (Exception e) {
            log.error(e.getMessage(), e);

        }
        return null;
    }

    public interface Runnable<T> {
        T run() throws Exception;
    }

    public interface RunnableNoReturn {
        void run() throws Exception;
    }

}
