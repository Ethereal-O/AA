package com.example.bookstore.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "Filter", urlPatterns = "/*")
public class Filter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        System.out.println("doFilter begin=========================");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //获取客户端所请求的脚本文件的文件路径
        String servletPath = httpServletRequest.getServletPath();


        System.out.println("当前的地址是:======>"+servletPath);

        //设置不拦截登录页面、JS文件、CSS文件、点击登录之后发送的请求和提示错误的页面
        if ("/tryloginservice".equals(servletPath)
                || "/trylogoutservice".equals(servletPath)
                || "/checkloginservice".equals(servletPath)
                || "/registerservice".equals(servletPath)
                || "/getallbookdataservice".equals(servletPath)
                || "/addbooktypedataservice".equals(servletPath)){

            System.out.println("success  ======>");
            //放行
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            //从session作用域获取session对象查看是否为空
            Integer usertype = (Integer) httpServletRequest.getSession().getAttribute("type");
            //如果session对象为空，则用户未登录
//            System.out.println(usertype);
            if (usertype==null ||
                    usertype!=2&&("/adminchangedataservice".equals(servletPath)
                    ||"/admindeletebookservice".equals(servletPath)
                    ||"/getalluserdataservice".equals(servletPath)
                    ||"/adminchangeuserdataservice".equals(servletPath)
                    ||"/admindeleteuserdataservice".equals(servletPath)
                    ||"/getallorderdataservice".equals(servletPath)
                    ||"/adminaddorderservice".equals(servletPath)
                    ||"/admindeleteorderservice".equals(servletPath))
            ){
                System.out.println("fail =========>");
            }else{
                System.out.println("success =========>");
                filterChain.doFilter(servletRequest, servletResponse);
            }

//            if (usertype!=null){
//                //放行
//
//                filterChain.doFilter(servletRequest, servletResponse);
//            }else {
//                System.out.println("fail =========>");
//                //重定向到错误的展示页面 让用户登录了再进行访问
////                httpServletResponse.sendRedirect("https://www.baidu.com");
//            }
        }

    }


    //容器销毁的时候执行的方法
    @Override
    public void destroy() {

    }
}