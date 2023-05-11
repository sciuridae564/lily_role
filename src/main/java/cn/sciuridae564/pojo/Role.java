package cn.sciuridae564.pojo;

/**
 * @created 2023/5/10 23:58
 */
public class Role {
    private String china_name;//中文名
    private String name;//角色英文代码

    public Role() {
    }

    public Role(String china_name, String name) {
        this.china_name = china_name;
        this.name = name;
    }

    public String getChina_name() {
        return china_name;
    }

    public void setChina_name(String china_name) {
        this.china_name = china_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "china_name='" + china_name + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
