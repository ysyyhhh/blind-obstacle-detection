package cc.yysy.utilscommon.constant;
/**
 * 系统级静态变量
 */
public class SystemConstant {

	/**
	 * 解析后的用户唯一标识在header中的name
	 */
	public static final String HEADER_KEY_OF_USER_PHONE = "X-user-phone";

	/**
	 * 解析后的用户唯一标识在header中的name
	 */
	public static final String HEADER_KEY_OF_USER = "X-user";

	public static final String HEADER_KEY_OF_DATA_SOURCE = "X-data-source";


	/**
	 * 超级管理员ID
	 */
	public static final String SUPER_ADMIN = "admin";
	
	/**
	 * 数据标识
	 */
	public static final String DATA_ROWS = "rows";
	
	/**
	 * 真
	 */
	public static final String TRUE = "true";
	/**
	 * 假
	 */
	public static final String FALSE = "false";
	
	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private final int value;

        private MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 通用字典
     */
    public enum MacroType {
    	
    	/**
    	 * 类型
    	 */
    	TYPE(0),
    	
    	/**
    	 * 参数
    	 */
    	PARAM(1);
    	
    	private final int value;
    	
    	private MacroType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    	
    }
    
    /**
     * 通用变量，表示可用、禁用、显示、隐藏、是、否
     */
    public enum StatusType {
    	
    	/**
    	 * 禁用，隐藏
    	 */
    	DISABLE(0),
    	
    	/**
    	 * 可用，显示
    	 */
    	ENABLE(1),
    	
    	/**
    	 * 显示
    	 */
    	SHOW(1),
    	
    	/**
    	 * 隐藏
    	 */
    	HIDDEN(0),
    	
    	/**
    	 * 是
    	 */
    	YES(1),
    	
    	/**
    	 * 否
    	 */
    	NO(0);
    	
    	private final int value;
    	
    	private StatusType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    	
    }
}
