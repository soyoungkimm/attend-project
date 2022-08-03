package com.gamejigi.attend.util;


import com.gamejigi.attend.model.dto.LoginDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginServletFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession(false);
        String path = httpServletRequest.getRequestURI();

        if (path.contains("/login")) { // 제외
            chain.doFilter(request, response);
            return;
        }


        if (session == null) {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/login.do");
            return;
        }
        else {
            LoginDTO loginDTO = (LoginDTO) session.getAttribute("logined");

            if (loginDTO == null) {
                httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login/login.do");
                return;
            }
            else {
                int kind = loginDTO.getKind();

                // 직원
                if (path.contains("/main/ad.do") || path.contains("/control") || path.contains("/notice") ||
                        path.contains("/student") || path.contains("/teacher") || path.contains("/staff") ||
                        path.contains("/depart") || path.contains("/room") || path.contains("/building") ||
                        path.contains("/holidays") || path.contains("/adTimes") || path.contains("/TimeTeacher") ||
                        path.contains("/lecMove")) {
                    if (kind != 0) {
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error/permission.do");
                        return;
                    }

                }
                // 조교
                else if (path.contains("/main/as.do") || path.contains("/subject") || path.contains("/lectures") ||
                            path.contains("/astime") || path.contains("/s-subject-attend") || path.contains("/asLecMove")) {
                    if (kind != 1) {
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error/permission.do");
                        return;
                    }
                }
                // 교수
                else if (path.contains("/main/teacher.do") || path.contains("/time") || path.contains("/daily-attend") ||
                            path.contains("/subject-attend") || path.contains("/TeLecMove") || path.contains("/lecQa/te_")) {
                    if (kind != 2) {
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error/permission.do");
                        return;
                    }
                }
                // 학생
                else if (path.contains("/main/st.do") || path.contains("/student-attend") || path.contains("/student-time") ||
                            path.contains("/stLec") || path.contains("/student/sugang.do") || path.contains("/lecQa/st_")) {
                    if (kind != 3) {
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error/permission.do");
                        return;
                    }
                }

                chain.doFilter(request, response);
            }
        }

    }
}
