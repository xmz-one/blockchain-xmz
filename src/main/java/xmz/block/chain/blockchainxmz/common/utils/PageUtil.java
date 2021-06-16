package xmz.block.chain.blockchainxmz.common.utils;

import java.util.List;
import java.util.stream.Collectors;

public class PageUtil<T> {
    /**
     * 实体类列表
     */
    List<T> content;
    /**
     * 是否首页
     */
    boolean first;
    /**
     * 是否尾页
     */
    boolean last;
    /**
     * 总记录数
     */
    Integer totalElements;
    /**
     * 总页数
     */
    Integer totalPages;

    Integer numberOfElements;
    /**
     * 每页记录数
     */
    Integer size;
    /**
     * 当前页
     */
    Integer number;

    @Override
    public String toString() {
        return "PageUtil{" +
                "content=" + content +
                ", first=" + first +
                ", last=" + last +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", numberOfElements=" + numberOfElements +
                ", size=" + size +
                ", number=" + number +
                '}';
    }

    public List<T> getContent() {
        return content;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getNumber() {
        return number;
    }

    public void pageUtil(Integer page, Integer size, List<T> list){
        List<T> list1=list.stream ().skip (page*size).limit (size).collect(Collectors.toList());
        int length=list.size ();
        this.first=(page==0);//是否第一页
        this.last= (page==(length-1)/size);//是否最后一页
        this.totalPages =((length-1)/size+1);//总页数
        this.totalElements= (length);//总elements
        this.size= (size);//每页多少elements
        this.content= (list1);//内容
        this.numberOfElements =(list1.size ());//当前页elements
        this.number= (page);//当前页数，第一页是0
    }
}