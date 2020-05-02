package xyz.worldzhile.blog.domain;

public class FileMessage {

//    {
//        success : 0 | 1, //0表示上传失败;1表示上传成功
//                message : "提示的信息",
//            url     : "图片地址" //上传成功时才返回
//    }

    private int success;
    private String message;
    private String url;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
