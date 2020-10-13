package com.kk.mybatis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 规则组与其下规则
 *
 * @Author kk.xie
 * @Date 2019/11/26 19:30
 * @Version 1.0
 **/
public class GroupRuler implements Serializable {
    private static final long serialVersionUID = -7270031390679253582L;
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_group.id
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_group.name
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    private String name;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_group.create_time
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_group.creator
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    private String creator;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_group.modify_time
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    private Date modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tbl_group.modifier
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    private String modifier;

    /**
     * 规则组下的规则
     */
    private List<Ruler> rulerList;

    /**
     * 某条规则
     */
    private Ruler ruler;

    public Ruler getRuler() {
        return ruler;
    }

    public void setRuler(Ruler ruler) {
        this.ruler = ruler;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_group.id
     *
     * @return the value of tbl_group.id
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_group.id
     *
     * @param id the value for tbl_group.id
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_group.name
     *
     * @return the value of tbl_group.name
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_group.name
     *
     * @param name the value for tbl_group.name
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_group.create_time
     *
     * @return the value of tbl_group.create_time
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_group.create_time
     *
     * @param createTime the value for tbl_group.create_time
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_group.creator
     *
     * @return the value of tbl_group.creator
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_group.creator
     *
     * @param creator the value for tbl_group.creator
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_group.modify_time
     *
     * @return the value of tbl_group.modify_time
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_group.modify_time
     *
     * @param modifyTime the value for tbl_group.modify_time
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tbl_group.modifier
     *
     * @return the value of tbl_group.modifier
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tbl_group.modifier
     *
     * @param modifier the value for tbl_group.modifier
     *
     * @mbg.generated Wed Nov 20 10:24:01 CST 2019
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public List<Ruler> getRulerList() {
        return rulerList;
    }

    public void setRulerList(List<Ruler> rulerList) {
        this.rulerList = rulerList;
    }

    @Override
    public String toString() {
        return "GroupRuler{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                ", creator='" + creator + '\'' +
                ", modifyTime=" + modifyTime +
                ", modifier='" + modifier + '\'' +
                ", rulerList=" + rulerList +
                ", ruler=" + ruler +
                '}';
    }
}