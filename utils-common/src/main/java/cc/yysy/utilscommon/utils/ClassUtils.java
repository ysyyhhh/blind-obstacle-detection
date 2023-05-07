package cc.yysy.utilscommon.utils;



import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ClassUtils {


    public static Object MapToObject(Object obj,Map<String,Object> params){
        Object newObj = obj;
        Field[] fields = newObj.getClass().getDeclaredFields();//通过反射获取到该对象
        for (Field field : fields) {
            field.setAccessible(true);//当要读取的属性为私有时，要设置为true
            String fieldName = field.getName();//获取对象的属性名
            Object value = params.get(fieldName);
            System.out.println(fieldName);
            if(value != null){
                try {
                    if(fieldName.compareTo("date") == 0 || fieldName.compareTo("discoveryTime") == 0){
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date;
                        if(((String) value).compareTo("") == 0){
                            date = null;
                        }else{
                            date = simpleDateFormat.parse((String) value);
                        }
                        field.set(newObj,date);
                        continue;
                    }
                    if(fieldName.compareTo("alarm") == 0 || fieldName.compareTo("time") == 0 ){
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                        Date date;
                        if(((String) value).compareTo("") == 0){
                            date = null;
                        }else{
                            date = simpleDateFormat.parse((String) value);
                        }
                        field.set(newObj,date);
                        continue;
                    }
                    field.set(newObj,value);
                } catch (IllegalAccessException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return newObj;
    }


    private static Map<String,Object> switchTime(Map<String, Object> params, String field){
        String time = (String) params.get(field);
        if(time == null) return params;
        System.out.println(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            Date time_date  = sf.parse(time);
            params.put(field,time_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return params;
    }
    public static Map<String,Object> swtich(Map<String,Object> params){
        params = switchTime(params,"send_time");
        params = switchTime(params,"accept_time");
        params = switchTime(params,"order_create_time");
        return params;
    }
    /**
     * 判断 map对于类来说有没有缺少参数
     * @param obj
     * @param params
     * @return
     */
    public static boolean haveEmpty(Object obj,Map<String,Object> params){
        Object newObj = obj;
        Field[] fields = newObj.getClass().getDeclaredFields();//通过反射获取到该对象
        for (Field field : fields) {
            field.setAccessible(true);//当要读取的属性为私有时，要设置为true
            String fieldName = field.getName();//获取对象的属性名
            Object value = params.get(fieldName);
            if(value != null){
                try {
                    field.set(newObj,value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            else return true;
        }
        return false;
    }



}
