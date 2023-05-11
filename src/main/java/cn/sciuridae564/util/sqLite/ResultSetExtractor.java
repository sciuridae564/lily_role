package cn.sciuridae564.util.sqLite;

import java.sql.ResultSet;

public interface ResultSetExtractor<T> {

    T extractData(ResultSet rs);

}
