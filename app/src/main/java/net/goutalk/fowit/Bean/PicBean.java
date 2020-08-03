package net.goutalk.fowit.Bean;

import java.util.List;

public class PicBean {
    /**
     * img : //img.alicdn.com/imgextra/i4/2182728373/O1CN01dBwMqA2BisqnV9Q88_!!2182728373.jpg
     * hotAreaList : []
     * width : 790
     * height : 665
     */

    private String img;
    private String width;
    private String height;
    private List<?> hotAreaList;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public List<?> getHotAreaList() {
        return hotAreaList;
    }

    public void setHotAreaList(List<?> hotAreaList) {
        this.hotAreaList = hotAreaList;
    }
}
