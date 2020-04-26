package xyz.worldzhile.blog.util;

import java.util.List;

public class PageBean<T> {

    /**
     * 当前页
     */
    private Integer currentPage;
    /**
     * 每页显示条数
     */
    private Integer pageCount;
    /**
     * 总条数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 分页查询的数据
     */
    private List<T> list;

    public PageBean(Integer currentPage, Integer pageCount) {
        this.currentPage = currentPage;
        this.pageCount = pageCount;
        // 如果没有指定当前查哪页, 则默认查第1页
        if(currentPage == null||currentPage<=0){
            this.currentPage = 1;
        }
        // 如果没有指定每页多少条数据, 则默认为5条
        if(pageCount == null){
            this.pageCount = 8;
        }

    }

    public PageBean(Integer currentPage, Integer pageCount, Integer totalCount) {
        this.currentPage = currentPage;
        this.pageCount = pageCount;
        this.totalCount = totalCount;

        // 如果没有指定当前查哪页, 则默认查第1页
        if(currentPage == null||currentPage<=0){
            this.currentPage = 1;
        }
        // 如果没有指定每页多少条数据, 则默认为5条
        if(pageCount == null){
            this.pageCount = 8;
        }


        update();


    }

    /**
     * mybatis获取分页查询的起始参数
     * @return
     */
    public int getStart(){
        return (this.currentPage - 1) * this.pageCount;
    }

    /*
        前台thymeleaf获取起始页  分页用的 只显示五页 防百度
     */
    public int getStartPage(){
        if (currentPage<=3)
            return 1;
        else  return currentPage-2;
    }
    public int getEndPage(){
        return Math.min(getStartPage()+4,totalPage);
    }


    void update(){
        // 获取总页数
        this.totalPage = (int) Math.ceil(1.0 * this.totalCount / this.pageCount);
        if (totalPage==0){
            totalPage=1;
        }
        // 当前页不能小于1
        if(this.currentPage < 1){
            this.currentPage = 1;
        }
        // 当前页不能大于总页数
        if(this.currentPage > this.totalPage ){
            this.currentPage = this.totalPage;
        }

    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
        update();
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
