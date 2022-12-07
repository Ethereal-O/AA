package com.example.bookstore.Utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    public static void SetSession(int user_id,int type){
        ServletRequestAttributes requestAttributes=(ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            HttpServletRequest request=requestAttributes.getRequest();
            HttpSession session=request.getSession();
            session.setAttribute("user_id",user_id);
            session.setAttribute("type",type);
        }
    }

    public static boolean RemoveSession(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // Session
        if(requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            HttpSession session = request.getSession(false);

            if(session != null) {
                session.invalidate();
                return true;
            }
        }
        return true;
    }

    public static boolean Check(){
        ServletRequestAttributes requestAttributes=(ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            HttpServletRequest request=requestAttributes.getRequest();
            HttpSession session=request.getSession(false);
            if(session!=null){
                Integer type=(Integer) session.getAttribute("type");
                if(type!=null&&type>0) return true;
            }
        }
        return false;
    }

}
