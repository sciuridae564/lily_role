package cn.sciuridae564.pojo;

/**
 * @created 2023/5/10 23:58
 */
public class Image {
    private String source_file_name;//源文件名
    private String role_name;//角色名

    public Image() {
    }

    public Image(String source_file_name, String role_name) {
        this.source_file_name = source_file_name;
        this.role_name = role_name;
    }

    public String getSource_file_name() {
        return source_file_name;
    }

    public void setSource_file_name(String source_file_name) {
        this.source_file_name = source_file_name;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "Image{" +
                "source_file_name='" + source_file_name + '\'' +
                ", role_name='" + role_name + '\'' +
                '}';
    }
}
