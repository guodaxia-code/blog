package xyz.worldzhile.blog.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xyz.worldzhile.blog.domain.FileMessage;
import xyz.worldzhile.blog.util.UuidUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;

@Controller
public class FileController {


    public void dealSuccessJson(FileMessage fileMessage,HttpServletRequest request,String fileName,String uuid){
        fileMessage.setSuccess(1);
        fileMessage.setMessage("success");
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        fileMessage.setUrl("http://"+hostAddress+"/"+request.getContextPath()+"/upload/"+uuid+fileName);
    }

    public void createFile(String path){
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
    }

    @ResponseBody
    @RequestMapping("upload")
    public FileMessage uploads(@RequestParam MultipartFile file, HttpServletRequest request) {
        System.out.println(1);
        //路径设置
        String path=request.getServletContext().getRealPath("/upload");
        createFile(path);
        FileMessage fileMessage = new FileMessage();
        String uuid = UuidUtil.getUuid();

        try {
            file.transferTo(new File(path+"/"+  uuid+file.getOriginalFilename()));
        } catch (IOException e) {
            fileMessage.setSuccess(0);
            fileMessage.setMessage("failed");
            System.out.println(e.getMessage());
            return fileMessage;
        }
        dealSuccessJson(fileMessage,request,file.getOriginalFilename(),uuid);
        return fileMessage;
    }

    @ResponseBody
    @RequestMapping("upload1")
    public FileMessage upload1s(@RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request)   {
        String uploadFileName = file.getOriginalFilename();
        System.out.println("上传文件的名称："+uploadFileName);
        //路径设置
        String path=request.getServletContext().getRealPath("/upload");
        createFile(path);

        FileMessage fileMessage = new FileMessage();
        String uuid = UuidUtil.getUuid();

        try{
            InputStream is = file.getInputStream(); //文件输入流
            OutputStream os = new FileOutputStream(new File(path, uuid+uploadFileName));//文件输入流

            //读写
            int length=0;
            byte[] buffer=new byte[1024];
            while ((length=is.read(buffer))!=-1){
                os.write(buffer,0,length);
                os.flush();
            }
            os.close();
            is.close();
        }catch (Exception e){
            fileMessage.setSuccess(0);
            fileMessage.setMessage("failed");
            System.out.println(e.getMessage());
            return fileMessage;
        }
        dealSuccessJson(fileMessage,request,file.getOriginalFilename(),uuid);
        return fileMessage;
    }

    @ResponseBody
    @GetMapping("download/{fileName}")
    public String download(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //路径设置
        String path=request.getServletContext().getRealPath("/upload");
        System.out.println(fileName);

        //设置response和响应头
        response.reset();//设置页面不缓存
        response.setCharacterEncoding("UTF-8");//字符编码
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(fileName,"UTF-8"));

        //读写
        File file = new File(path,fileName);
        InputStream is = new FileInputStream(file);
        OutputStream os = response.getOutputStream();
        int length=0;
        byte[] buffer=new byte[1024];
        while ((length=is.read(buffer))!=-1){
            os.write(buffer,0,length);
            os.flush();
        }
        os.close();
        is.close();
        return "ok";
    }



}
