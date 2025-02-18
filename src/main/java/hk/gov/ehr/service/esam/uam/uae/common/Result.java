package hk.gov.ehr.service.esam.uam.uae.common;

public class Result{
    public static final Integer SUCCES_CODE=200;
    private static final Integer EXIST_CODE=300;
    private static final Integer FAIL_CODE=500;
    public static final String SUCCESS_MSG="SUCCESS";
    private static final String FAIL_MSG="FAIL";
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    private Integer code;
    private String msg;
    private Object data;

    public static Result success(String msg){
        Result result = new Result();
        result.setCode(SUCCES_CODE);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result success(String msg, Object data){
    	Result result = new Result();
        result.setCode(SUCCES_CODE);
        if(msg == null){
        	msg = SUCCESS_MSG;
        }
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result success(Object data){
    	Result result = new Result();
        result.setCode(SUCCES_CODE);
        result.setMsg(SUCCESS_MSG);
        result.setData(data);
        return result;
    }
    
    public static Result exist(String msg){
    	Result result = new Result();
        result.setCode(EXIST_CODE);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static Result error(String msg){
        Result result = new Result();
        result.setCode(FAIL_CODE);
        if(msg == null){
        	msg = FAIL_MSG;
        }
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

}

