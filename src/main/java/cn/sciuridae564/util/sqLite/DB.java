package cn.sciuridae564.util.sqLite;

import cn.sciuridae564.pojo.Image;
import cn.sciuridae564.pojo.Role;
import cn.sciuridae564.pojo.Save;
import org.sqlite.SQLiteException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @created 2023/5/11 2:36
 */
public class DB {
    private static final DB Instance = new DB();
    private  SqliteHelper h;
    private DB() { }
    public static DB getInstance(){
        return Instance;
    }

    public void init() {
        try {
            h= new SqliteHelper("root.db");
            String initTable="CREATE TABLE if not exists _role(id integer PRIMARY KEY AUTOINCREMENT,china_name TEXT UNIQUE ,name TEXT);\n" +
                    "CREATE TABLE if not exists image( id integer PRIMARY KEY AUTOINCREMENT, source_file_name TEXT UNIQUE, role_name TEXT);\n" +
                    "CREATE TABLE if not exists save( id integer PRIMARY KEY AUTOINCREMENT, save_dir TEXT UNIQUE, out_dir TEXT);\n" +
                    "\n"+
                    "insert into _role(name,china_name) values('hitocuyanagi_riri','一柳梨璃')ON CONFLICT(china_name)DO UPDATE SET name='hitocuyanagi_riri';\n" +
                    "insert into _role(name,china_name) values('sirai_yuyu','白井梦结')ON CONFLICT(china_name)DO UPDATE SET name='sirai_yuyu';\n" +
                    "insert into _role(name,china_name) values('kaede_zyoan_nu_beru','枫·若昂·努韦勒')ON CONFLICT(china_name)DO UPDATE SET name='kaede_zyoan_nu_beru';\n" +
                    "insert into _role(name,china_name) values('hutagawa_humi','二川二水')ON CONFLICT(china_name)DO UPDATE SET name='hutagawa_humi';\n" +
                    "insert into _role(name,china_name) values('andou_tazusa','安藤鹤纱')ON CONFLICT(china_name)DO UPDATE SET name='andou_tazusa';\n" +
                    "insert into _role(name,china_name) values('yosimura_te_mai','吉村氏梅')ON CONFLICT(china_name)DO UPDATE SET name='yosimura_te_mai';\n" +
                    "insert into _role(name,china_name) values('ku_si_NriN','郭神琳')ON CONFLICT(china_name)DO UPDATE SET name='ku_si_NriN';\n" +
                    "insert into _role(name,china_name) values('wan_yu_zya','王雨嘉')ON CONFLICT(china_name)DO UPDATE SET name='wan_yu_zya';\n" +
                    "insert into _role(name,china_name) values('miriamu_hirudegarudo_v_gulopiusu','米莉亚姆·茜尔德加德·冯·格罗皮乌斯')ON CONFLICT(china_name)DO UPDATE SET name='miriamu_hirudegarudo_v_gulopiusu';\n" +
                    "insert into _role(name,china_name) values('aizawa_kazuha','相泽一叶')ON CONFLICT(china_name)DO UPDATE SET name='aizawa_kazuha';\n" +
                    "insert into _role(name,china_name) values('sasaki_ran','佐佐木蓝')ON CONFLICT(china_name)DO UPDATE SET name='sasaki_ran';\n" +
                    "insert into _role(name,china_name) values('iizima_renka','饭岛恋花')ON CONFLICT(china_name)DO UPDATE SET name='iizima_renka';\n" +
                    "insert into _role(name,china_name) values('hacukano_you','初鹿野瑶')ON CONFLICT(china_name)DO UPDATE SET name='hacukano_you';\n" +
                    "insert into _role(name,china_name) values('serizawa_cikaru','芹泽千香琉')ON CONFLICT(china_name)DO UPDATE SET name='serizawa_cikaru';\n" +
                    "insert into _role(name,china_name) values('kon_kanaho','今叶星')ON CONFLICT(china_name)DO UPDATE SET name='kon_kanaho';\n" +
                    "insert into _role(name,china_name) values('miyagawa_takane','宫川高岭')ON CONFLICT(china_name)DO UPDATE SET name='miyagawa_takane';\n" +
                    "insert into _role(name,china_name) values('toki_kureha','土岐红巴')ON CONFLICT(china_name)DO UPDATE SET name='toki_kureha';\n" +
                    "insert into _role(name,china_name) values('tanba_akari','丹羽灯莉')ON CONFLICT(china_name)DO UPDATE SET name='tanba_akari';\n" +
                    "insert into _role(name,china_name) values('sadamori_himeka','定盛姬歌')ON CONFLICT(china_name)DO UPDATE SET name='sadamori_himeka';\n" +
                    "insert into _role(name,china_name) values('masima_moyu','真岛百由')ON CONFLICT(china_name)DO UPDATE SET name='masima_moyu';\n" +
                    "insert into _role(name,china_name) values('hitocuyanagi_yuri','一柳结梨')ON CONFLICT(china_name)DO UPDATE SET name='hitocuyanagi_yuri';\n" +
                    "insert into _role(name,china_name) values('amano_soraha','天野天叶')ON CONFLICT(china_name)DO UPDATE SET name='amano_soraha';\n" +
                    "insert into _role(name,china_name) values('banshoya_ena','番匠谷依奈')ON CONFLICT(china_name)DO UPDATE SET name='banshoya_ena';\n" +
                    "insert into _role(name,china_name) values('egawa_kusumi','江川樟美')ON CONFLICT(china_name)DO UPDATE SET name='egawa_kusumi';\n" +
                    "insert into _role(name,china_name) values('endo_araya','远藤亚罗椰')ON CONFLICT(china_name)DO UPDATE SET name='endo_araya';\n" +
                    "insert into _role(name,china_name) values('kanabako_misora','金箱弥宙')ON CONFLICT(china_name)DO UPDATE SET name='kanabako_misora';\n" +
                    "insert into _role(name,china_name) values('tanaka_ichi','田中壹')ON CONFLICT(china_name)DO UPDATE SET name='tanaka_ichi';\n" +
                    "insert into _role(name,china_name) values('takasuga_tsukushi','高须贺月诗')ON CONFLICT(china_name)DO UPDATE SET name='takasuga_tsukushi';\n" +
                    "insert into _role(name,china_name) values('watanabe_akane','渡边茜')ON CONFLICT(china_name)DO UPDATE SET name='watanabe_akane';\n" +
                    "insert into _role(name,china_name) values('mori_tatsuki','森辰姬')ON CONFLICT(china_name)DO UPDATE SET name='mori_tatsuki';\n" +
                    "insert into _role(name,china_name) values('rokkaku_shiori','六角汐里')ON CONFLICT(china_name)DO UPDATE SET name='rokkaku_shiori';\n" +
                    "insert into _role(name,china_name) values('funada_kiito','船田纯')ON CONFLICT(china_name)DO UPDATE SET name='funada_kiito';\n" +
                    "insert into _role(name,china_name) values('funada_ui','船田初')ON CONFLICT(china_name)DO UPDATE SET name='funada_ui';\n" +
                    "insert into _role(name,china_name) values('fujita_asagao','藤田槿')ON CONFLICT(china_name)DO UPDATE SET name='fujita_asagao';\n" +
                    "insert into _role(name,china_name) values('kawamura_yuzuriha','川村楪')ON CONFLICT(china_name)DO UPDATE SET name='kawamura_yuzuriha';\n" +
                    "insert into _role(name,china_name) values('tsukioka_momiji','月冈椛')ON CONFLICT(china_name)DO UPDATE SET name='tsukioka_momiji';\n" +
                    "insert into _role(name,china_name) values('kishimoto_lucia_raimu','岸本·露西娅·来梦')ON CONFLICT(china_name)DO UPDATE SET name='kishimoto_lucia_raimu';\n" +
                    "insert into _role(name,china_name) values('fukuyama_jeanne_sachie','福山·让娜·幸惠')ON CONFLICT(china_name)DO UPDATE SET name='fukuyama_jeanne_sachie';\n" +
                    "insert into _role(name,china_name) values('amamiya_sophia_seren','天宫·索菲娅·圣恋')ON CONFLICT(china_name)DO UPDATE SET name='amamiya_sophia_seren';\n" +
                    "insert into _role(name,china_name) values('matsunaga_brigitta_kayo','松永·布丽吉塔·佳世')ON CONFLICT(china_name)DO UPDATE SET name='matsunaga_brigitta_kayo';\n" +
                    "insert into _role(name,china_name) values('kuroki_francesca_yuria','黑木·弗朗西丝卡·百合亚')ON CONFLICT(china_name)DO UPDATE SET name='kuroki_francesca_yuria';\n" +
                    "insert into _role(name,china_name) values('hata_matsuri','秦祀')ON CONFLICT(china_name)DO UPDATE SET name='hata_matsuri';\n" +
                    "insert into _role(name,china_name) values('ito_shizu','伊东闲')ON CONFLICT(china_name)DO UPDATE SET name='ito_shizu';\n" +
                    "insert into _role(name,china_name) values('tachihara_sayu','立原纱愈')ON CONFLICT(china_name)DO UPDATE SET name='tachihara_sayu';\n" +
                    "insert into _role(name,china_name) values('wang_lifen','王莉芬')ON CONFLICT(china_name)DO UPDATE SET name='wang_lifen';\n" +
                    "insert into _role(name,china_name) values('matsumura_fuka','松村优珂')ON CONFLICT(china_name)DO UPDATE SET name='matsumura_fuka';\n" +
                    "insert into _role(name,china_name) values('makino_mitake','牧野美岳')ON CONFLICT(china_name)DO UPDATE SET name='makino_mitake';\n" +
                    "insert into _role(name,china_name) values('shiosaki_suzume','盐崎铃梦')ON CONFLICT(china_name)DO UPDATE SET name='shiosaki_suzume';\n" +
                    "insert into _role(name,china_name) values('yokota_haruna','本间秋日')ON CONFLICT(china_name)DO UPDATE SET name='yokota_haruna';\n" +
                    "insert into _role(name,china_name) values('honma_akehi','横田悠夏')ON CONFLICT(china_name)DO UPDATE SET name='honma_akehi';\n" +
                    "insert into _role(name,china_name) values('ishitsuka_fujino','石塚藤乃')ON CONFLICT(china_name)DO UPDATE SET name='ishitsuka_fujino';";
            h.executeUpdate(initTable);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {

        }
    }

    //根据传入的领袖中文名查询所有的链接到的文件名
    public List<String> searchfileByname(String name) {
        String sql = "select source_file_name from image where role_name =\"" + name + "\"";
        RowMapper<String> rowMapper = (rs, index) -> rs.getString("source_file_name");
        List<String> list = null;
        try {
            list = h.executeQuery(sql, rowMapper);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    //传入文件名查询有没有对应的人物名
    public String searchnameByfile(String file) {
        String sql = "select role_name from image where source_file_name =\"" + file + "\"";
        RowMapper<String> rowMapper = (rs, index) -> rs.getString("role_name");
        List<String> list = null;
        String name = null;
        try {
            list = h.executeQuery(sql, rowMapper);
            if (list.size()!=0){
                name = list.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    //清除英文名与中文名的映射
    public void clearnameMapping(String china_name) {
        String clearTree = "delete from _role where china_name = \""+china_name + "\";";
        try {
            h.executeUpdate(clearTree);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
    }

    //清除文件名与中文名的映射
    public void clearfile_nameMapping(String source_file_name) {
        String clearTree = "delete from image where source_file_name = \"";
        try {
            h.executeUpdate(clearTree + source_file_name + "\"");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
    }

    //增加图片与名字的映射
    public void insertfile_nameMapping(String role_name,String source_file_name) {
        String sql = "insert into image(source_file_name,role_name) values(\""+source_file_name + "\",\""+role_name+ "\" )ON CONFLICT(source_file_name)DO UPDATE SET role_name=\""+role_name+ "\"; ";
        try {
            h.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
    }

    //增加名字与名字的映射
    public void insertnameMapping(String china_name,String name) {
        String sql = "insert into _role(china_name,name) values(\""+china_name + "\",\""+name+ "\" )ON CONFLICT(china_name)DO UPDATE SET name=\""+name+ "\"; ";
        try {
            h.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
    }

                    
    public void insertsave(String save_dir,String out_dir) {
        String sql = "insert into save(id,save_dir,out_dir) values(1,\""+save_dir+"\",\""+out_dir+"\")ON CONFLICT(id)DO UPDATE SET save_dir = \""+save_dir+"\",out_dir = \""+out_dir+"\";";
        try {
            h.executeUpdate(sql);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
    }
    public Save getSave() {
        String sql = "select save_dir,out_dir from save where id = 1";
        RowMapper<Save> rowMapper = (rs, index) -> {
            Save role = new Save(rs.getString("save_dir"),rs.getString("out_dir"));
            return role;
        };
        List<Save> list = null;
        try {
            list = h.executeQuery(sql, rowMapper);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {

        }
        if (list.size() > 0){
            return list.get(0);
        }else {
            return null;
        }
    }


    public List<Role> getAllleader() {
        String sql = "select china_name,name from _role";
        RowMapper<Role> rowMapper = (rs, index) -> {
            Role role = new Role(rs.getString("china_name"),rs.getString("name"));
            return role;
        };
        List<Role> list = null;
        try {
            list = h.executeQuery(sql, rowMapper);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
        return list;
    }

    public List<Image> getAllimage() {
        String sql = "select source_file_name,role_name from image";
        RowMapper<Image> rowMapper = (rs, index) -> {
            Image image = new Image(rs.getString("source_file_name"),rs.getString("role_name"));
            return image;
        };
        List<Image> list = null;
        try {
            list = h.executeQuery(sql, rowMapper);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            
        }
        return list;
    }





}
