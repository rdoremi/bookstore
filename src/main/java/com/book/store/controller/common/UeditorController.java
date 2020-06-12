package com.book.store.controller.common;

import com.alibaba.fastjson.JSONException;
import com.baidu.ueditor.ActionEnter;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.ClassPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
@RequestMapping("/ueditor")
public class UeditorController {



        @RequestMapping("/ueditorConfig")
        public void getUEditorConfig(HttpServletResponse response, HttpServletRequest request, HttpSession session){
           // String rootPath = "src/main/resources/static/";

            response.setContentType("application/json");
            String rootPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+ "static/ueditor/jsp/";


            try {
                String exec = new ActionEnter(request, rootPath).exec();
                PrintWriter writer = response.getWriter();
                writer.write(exec);
                writer.flush();
                writer.close();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        @RequestMapping("/ueditor_config")
        public void ueditor(HttpServletRequest request, HttpServletResponse response) {
            System.out.println("进来了");
            try {
                request.setCharacterEncoding( "utf-8" );
                response.setHeader("Content-Type" , "text/html");
                response.setContentType("application/json");
                // 去加载config.json文件
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                PrintWriter out = response.getWriter();
                String exec = new ActionEnter( request, rootPath ).exec();
                System.out.println(rootPath+"\n"+exec);
                out.write( exec );
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @RequestMapping("/config")
        public String uploadConfig() {
            String s = "{\n" +

                    " \"imageActionName\": \"manage/file/file_download\",\n" +
                    " \"imageFieldName\": \"upImg\", \n" +
                    " \"imageMaxSize\": 2048000, \n" +
                    " \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], \n" +
                    " \"imageCompressEnable\": true, \n" +
                    " \"imageCompressBorder\": 1600, \n" +
                    " \"imageInsertAlign\": \"none\", \n" +
                    " \"imageUrlPrefix\": \"\",\n" +
                    " \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\" }";
            return s;
        }




}
