package cc.yysy.utilscommon.result;

public enum ResultCode {

    /* 成功状态码 */
    SUCCESS(1, "成功"),


    /* 参数错误 */
    PARAM_IS_INVALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),

    /* 用户错误 2001-2999*/
    USER_NOTLOGGED_IN(2001, "用户未登录"),
    USER_LOGIN_ERROR(2002, "账号不存在或密码错误"),
    USER_NOT_ADDMISSION(2003, "用户权限不够"),
    USER_OLD_PASSWORD_ERROR(2004, "旧密码错误"),
    SYSTEM_ERROR(10000, "系统异常，请稍后重试"),

    /* 数据库操作失败 3001-3999*/
    DATABASE_ERROR(3001, "数据库操作失败");

    private Integer code;
    private String message;

    private ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }
    public String getMessage() {
        return this.message;
    }
}
