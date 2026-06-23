package utilities;

public class Mapping {
    public Class<?> clazz;
    public String meth_name;
    
    public Mapping(Class<?> clazz, String meth_name) {
        this.clazz = clazz;
        this.meth_name = meth_name;
    }
    
    public Class<?> getClazz() {
        return clazz;
    }
    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
    public String getMeth_name() {
        return meth_name;
    }
    public void setMeth_name(String meth_name) {
        this.meth_name = meth_name;
    }
   
}
