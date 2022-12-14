package com.chansan.common;

import java.util.Arrays;
import java.util.List;

import com.chansan.common.data.EntityUtil;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @name: data
 * @author: leihuangyan
 * @classPath: com.chansan.common.data
 * @date: 2022/12/14
 * @description:
 */
public class EntityTest {

    public static void main(String[] args) throws Exception {



        EntityUtil.mergeListField(sexes,users);

        EntityUtil.mergeListField(roles,users);

        System.out.println();
        users.forEach(System.out::println);

    }


    public static void main1(String[] args) throws Exception {

        EntityUtil.mergeListField(sexes,users, Sex::getId,User::getSexId, EntityUtil.MapperField.gen(Sex::getSex, User::getSex));
        System.out.println();
        System.out.println();

        users.forEach(System.out::println);

        EntityUtil.mergeListField(roles,users, Role::getId, User::getRoleId,  EntityUtil.MapperField.gen(Role::getRole, User::getRole),  EntityUtil.MapperField.gen(Role::getLv, User::getLv) );

        System.out.println();
        System.out.println();
        users.forEach(System.out::println);

    }

    @Data
    @Accessors(chain = true)
    public static class User {

        private String name;

        @EntityUtil.TargetId(mark = "sexId")
        private Integer sexId;

        @EntityUtil.TargetId(mark = "roleId")
        private Integer roleId;

        @EntityUtil.TargetVal
        private String sex;

        @EntityUtil.TargetVal
        private String role;

        @EntityUtil.TargetVal("lv")
        private String lv;

    }


    @Data
    @Accessors(chain = true)
    public  static  class  Sex{

        @EntityUtil.SourceId(mark = "sexId")
        private Integer id;

        @EntityUtil.SourceVal
        private String sex;
    }

    @Data
    @Accessors(chain = true)
    public static  class Role {

        @EntityUtil.SourceId(mark = "roleId")
        private Integer id;

        @EntityUtil.SourceVal
        private String role;

        @EntityUtil.SourceVal("lv")
        private String lv;

    }


    private static final List<User> users;
    private static final List<Sex> sexes;
    private static final List<Role> roles;

    static {

        users = Arrays.asList(
                new User().setName("??????").setSexId(1).setRoleId(1),
                new User().setName("??????").setSexId(2).setRoleId(1),
                new User().setName("??????").setSexId(1).setRoleId(2),
                new User().setName("??????").setSexId(1).setRoleId(2),
                new User().setName("?????????").setSexId(3).setRoleId(2),
                new User().setName("?????????").setSexId(3).setRoleId(1),
                new User().setName("??????").setSexId(2).setRoleId(2)
        );

        sexes = Arrays.asList(
                new Sex().setSex("???").setId(1),
                new Sex().setSex("???").setId(2),
                new Sex().setSex("??????").setId(3)
        );

        roles = Arrays.asList(
                new Role().setRole("??????").setId(1).setLv("??????"),
                new Role().setRole("??????").setId(2).setLv("??????")
        );

    }



}
