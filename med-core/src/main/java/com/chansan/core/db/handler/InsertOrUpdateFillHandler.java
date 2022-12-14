package com.chansan.core.db.handler;

import java.util.Date;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.chansan.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * mybatisPlus填充配置类
 *
 * @author yf
 */
@Slf4j
@Component
public class InsertOrUpdateFillHandler implements MetaObjectHandler {

    private static final String CREATE_TIME = "createTime";
    private static final String CREATE_BY = "createBy";
    private static final String UPDATE_TIME = "updateTime";
    private static final String UPDATE_BY = "updateBy";
    @Override
    public void insertFill(MetaObject metaObject) {
        //新增时策略
        //创建时间
        this.strictInsertFill(metaObject, CREATE_TIME, Date::new, Date.class);

        try {
            this.strictInsertFill(metaObject, CREATE_BY, Long.class, SecurityUtils.getUserId());
        } catch (Exception e) {
            log.error("insert 自动填充异常", e);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //修改时策略
        //修改时间
        this.strictUpdateFill(metaObject, UPDATE_TIME, Date::new, Date.class);
        //修改者
        try {
            this.strictUpdateFill(metaObject, UPDATE_BY, Long.class, SecurityUtils.getUserId());
        } catch (Exception e) {
            log.error("update 自动填充异常", e);
        }

    }
}
