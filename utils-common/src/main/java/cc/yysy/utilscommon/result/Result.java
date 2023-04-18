package cc.yysy.utilscommon.result;

import lombok.Data;

import java.io.Serializable;


@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String message;
    private T data;
    public Result() {

    }
    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }
    public Result(int code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 成功
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }
    /**
     * 失败
     */
    public static <T> Result<T> error(int code, String message) {
        return new Result(code, message);
    }

    /**
     * 失败
     */
    public static <T> Result<T> error( String message) {
        return new Result(0, message);
    }

    /**
     * 失败
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMessage());
    }

    public static  <T> Result<T> resultDB(int databaseCode) {
        if(databaseCode == 1){
            return (Result<T>) Result.success(databaseCode);
        }else{
            return Result.error(databaseCode,"数据库操作失败");
        }
    }
}