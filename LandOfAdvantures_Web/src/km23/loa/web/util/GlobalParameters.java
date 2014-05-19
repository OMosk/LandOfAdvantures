package km23.loa.web.util;

import javax.servlet.ServletContext;

/**
 * Created by mosk on 19.05.14.
 */
public class GlobalParameters {
    protected static ServletContext servletContext;

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static void setServletContext(ServletContext servletContext) {
        GlobalParameters.servletContext = servletContext;
    }
}
