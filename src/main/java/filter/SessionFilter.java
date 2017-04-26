package filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by pierremarsot on 19/11/2016.
 */
public class SessionFilter implements Filter {

    private ArrayList<String> urlList;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
        boolean allowedRequest = false;

        if(urlList.contains(url)) {
            allowedRequest = true;
        }

       /* HttpSession session = request.getSession();
        if (session != null && !url.contains("WEB-INF"))
        {
            User user = (User) session.getAttribute(GestionController.USER_SESSION);
            if(user != null && allowedRequest)
            {
                response.sendRedirect("/projets");
            }
            else if(user != null && !allowedRequest)
            {
                chain.doFilter(req, res);
            }
            else if(user == null && allowedRequest)
            {
                chain.doFilter(req, res);
            }
            else if(user == null && !allowedRequest)
            {
                response.sendRedirect("/");
            }
        }*/

        chain.doFilter(req, res);
    }

    public void init(FilterConfig config) throws ServletException {
        urlList = new ArrayList<>();

        urlList.add("/");
        urlList.add("/connexion");
        urlList.add("/register");
    }
}
