package cn.ncu.newmedia.backschool.typeHandler;

import cn.ncu.newmedia.backschool.Enumeration.BaseEnum;
import cn.ncu.newmedia.backschool.Enumeration.SexEnum;
import cn.ncu.newmedia.backschool.Utils.EnumUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 性别的枚举类转换器
 * @author maoalong
 * @date 2020/1/31 19:48
 * @description
 */
@MappedJdbcTypes(JdbcType.INTEGER)
@MappedTypes(value = BaseEnum.class)
public class EnumTypeHandler<T extends Enum<T>> extends BaseTypeHandler<T> {
    private Class<T> type;

    public EnumTypeHandler(Class<T> type) {
        if(type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        } else {
            this.type = type;
        }
    }


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {

    }

    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int id = rs.getInt(columnName);
        return EnumUtils.getEnumByCode(this.type, id);
    }

    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

        int id = rs.getInt(columnIndex);
        return EnumUtils.getEnumByCode(this.type, id);
    }

    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException{
        int id = cs.getInt(columnIndex);
        return EnumUtils.getEnumByCode(this.type, id);
    }


}