package task4;

class MyIllegalInputException extends Exception{
    private Object nullPointer;
    //无参数构造器
    public MyIllegalInputException(){}
    //一个字符串参数构造器用于打印信息
    public MyIllegalInputException(String msg){
        super(msg);
    }
    //定义包含非法输入的对象（Object类型）的构造器
    public MyIllegalInputException(String msg,Object nullPointer){
        super(msg);
        this.nullPointer = nullPointer;//将非法对象保存起来
    }
    //提供方法使处理异常的程序能get到它。
    public Object getIllegal(){
        return nullPointer;
    }

}

public class TryError{
    static Object _gcd2(Object a, Object b) throws MyIllegalInputException {
        try{
        if((Integer)b==0) return a;
        return _gcd2(b,(Integer)a%(Integer)b);
        }
        catch(ClassCastException ex){
            if(!(b instanceof Integer)){
                throw new MyIllegalInputException("b不是integer对象",b);
            }
            if(!(a instanceof Integer)){
                throw new MyIllegalInputException("a不是integer对象",a);
            }
            return _gcd2(b, null);
        }
        
        
    }

    public static void main(String[] args) {
        try{
        System.out.println(TryError._gcd2("0", 8));
        }
        catch(MyIllegalInputException myex){
            myex.printStackTrace();
            
        }
    }
}
