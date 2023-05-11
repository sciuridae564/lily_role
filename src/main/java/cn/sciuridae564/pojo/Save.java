package cn.sciuridae564.pojo;

/**
 * @created 2023/5/11 14:50
 */
public class Save {
    private String save_dir;
    private String out_dir;

    public Save(String save_dir, String out_dir) {
        this.save_dir = save_dir;
        this.out_dir = out_dir;
    }

    public Save() {
    }

    public String getSave_dir() {
        return save_dir;
    }

    public void setSave_dir(String save_dir) {
        this.save_dir = save_dir;
    }

    public String getOut_dir() {
        return out_dir;
    }

    public void setOut_dir(String out_dir) {
        this.out_dir = out_dir;
    }
}
