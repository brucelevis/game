package com.nemo.log;

import com.nemo.log.annotation.Column;
import com.nemo.log.annotation.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Table(cycle = TableCycle.SINGLE, primaryKey = "id")
public abstract class AbstractLog implements Runnable{
    private static Logger LOGGER = LoggerFactory.getLogger(AbstractLog.class);
    private static final Map<Class<?>, TableDesc> tableDescMap = new HashMap<>();

    public AbstractLog() {
    }

    void init() throws Exception {
        Class<?> clazz = this.getClass();
        Table table = clazz.getAnnotation(Table.class);
        if(table != null) {
            TableDesc desc = new TableDesc();
            desc.setCycle(table.cycle());
            String tableName = table.tableName();
            if(tableName == null || tableName.equals("")) {
                tableName = clazz.getSimpleName();
            }

            desc.setName(tableName);
            desc.setPrimaryKey(table.primaryKey());

            ArrayList clazzList;
            for(clazzList = new ArrayList(); clazz.getSuperclass() != null && clazz != Object.class; clazz = clazz.getSuperclass()) {
                clazzList.add(0, clazz);
            }

            Iterator<Class<?>> var6 = clazzList.iterator();
            while (var6.hasNext()) {
                Class<?> cl = var6.next();
                Field[] fields = cl.getDeclaredFields();
                Field[] var9 = fields;
                int var10 = fields.length;

                for(int var11 = 0; var11 < var10; ++var11) {
                    Field field = var9[var11];
                    Column column = field.getAnnotation(Column.class);
                    if(column != null) {
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cl);
                        Method readMethod = pd.getReadMethod();
                        if(readMethod != null) {
                            ColumnDesc colDesc = new ColumnDesc();
                            colDesc.setAllowNull(column.autoIncrement());
                            colDesc.setAutoIncrement(column.autoIncrement());
                            String colName = column.colName();
                            if(colName == null || colName.equals("")) {
                                colName = field.getName();
                            }

                            colDesc.setName(colName.toLowerCase());
                            colDesc.setReadMethod(readMethod);
                            colDesc.setSize(column.size());
                            colDesc.setType(column.fieldType().name().toLowerCase());
                            colDesc.setCommit(column.commit());
                            desc.addCol(colDesc);
                        }
                    }
                }
            }

            desc.init();
            tableDescMap.put(this.getClass(), desc);
//            this.checkTable();
        }
    }


    @Override
    public void run() {

    }
}
