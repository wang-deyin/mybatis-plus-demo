package org.example.test.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableFieldInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

import java.util.List;
import java.util.function.Predicate;

/**
 * 增强版的批量插入，解决mp 中批量插入null 属性不能使用默认值的问题
 *
 * @see com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn
 */
 
@SuppressWarnings("serial")
public class InsertBatchSomeColumn extends AbstractMethod {
    private Predicate<TableFieldInfo> predicate;

    public InsertBatchSomeColumn() {
        super("insertBatchSomeColumn");
    }

    public InsertBatchSomeColumn(Predicate<TableFieldInfo> predicate) {
        super("insertBatchSomeColumn");
        this.predicate = predicate;
    }

    public InsertBatchSomeColumn(String name, Predicate<TableFieldInfo> predicate) {
        super(name);
        this.predicate = predicate;
    }

    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
        SqlMethod sqlMethod = SqlMethod.INSERT_ONE;
        List<TableFieldInfo> fieldList = tableInfo.getFieldList();
        String insertSqlColumn = tableInfo.getKeyInsertSqlColumn(true, false) + this.filterTableFieldInfo(fieldList, this.predicate, TableFieldInfo::getInsertSqlColumn, "");
        String columnScript = "(" + insertSqlColumn.substring(0, insertSqlColumn.length() - 1) + ")";
        //String insertSqlProperty = tableInfo.getKeyInsertSqlProperty(true, "et.", false) + this.filterTableFieldInfo(fieldList, this.predicate, (i) -> i.getInsertSqlProperty("et."), "");
        String insertSqlProperty = tableInfo.getKeyInsertSqlProperty(true, "et.", false) + this.filterTableFieldInfo(fieldList, this.predicate, i -> getInsertSqlProperty(i,ENTITY_DOT), "");
        insertSqlProperty = "(" + insertSqlProperty.substring(0, insertSqlProperty.length() - 1) + ")";
        String valuesScript = SqlScriptUtils.convertForeach(insertSqlProperty, "list", (String)null, "et", ",");
        String keyProperty = null;
        String keyColumn = null;
        if (tableInfo.havePK()) {
            if (tableInfo.getIdType() == IdType.AUTO) {
                keyGenerator = Jdbc3KeyGenerator.INSTANCE;
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            } else if (null != tableInfo.getKeySequence()) {
                keyGenerator = TableInfoHelper.genKeyGenerator(this.methodName, tableInfo, this.builderAssistant);
                keyProperty = tableInfo.getKeyProperty();
                keyColumn = tableInfo.getKeyColumn();
            }
        }

        String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
        SqlSource sqlSource = this.languageDriver.createSqlSource(this.configuration, sql, modelClass);
        return this.addInsertMappedStatement(mapperClass, modelClass, this.getMethod(sqlMethod), sqlSource, (KeyGenerator)keyGenerator, keyProperty, keyColumn);
    }

    public InsertBatchSomeColumn setPredicate(final Predicate<TableFieldInfo> predicate) {
        this.predicate = predicate;
        return this;
    }

    private String getInsertSqlProperty(TableFieldInfo tableFieldInfo,final String prefix) {
        String newPrefix = prefix == null ? "" : prefix;
        String elPart = SqlScriptUtils.safeParam(newPrefix + tableFieldInfo.getEl());
        //属性为空时使用默认值
        String result =  SqlScriptUtils.convertIf(elPart,
                String.format("%s != null", newPrefix + tableFieldInfo.getEl()),false)
                + SqlScriptUtils.convertIf("default",
                String.format("%s == null", newPrefix + tableFieldInfo.getEl()),false);
        return result + ",";
    }
}