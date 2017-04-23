package bean;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by vkarassouloff on 19/04/17.
 */
public class BeanParamUtils implements Serializable {

    private HashMap<String, String> paramsHash;


    /**
     * Bean providing utils function for building urls with get parameters.
     */
    public BeanParamUtils() {
        paramsHash = new HashMap<>();
    }

    /**
     * Bean providing utils function for building urls with get parameters.
     * @param request copy get parameter from request
     */
    public BeanParamUtils(HttpServletRequest request) {
        paramsHash = new HashMap<>();
        this.copyGetParameterFromRequest(request);
    }

    /**
     * Use request to get all parameter by name and build an URL saving all the values of GET parameters.
     *
     * @return url
     */
    public String buildUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("?");

        Iterator<String> it = paramsHash.keySet().iterator();
        while (it.hasNext()) {
            String paramName = it.next();
            sb.append(paramName + "=" + paramsHash.get(paramName));
            if (it.hasNext()) {
                sb.append("&");
            }
        }

        return sb.toString();
    }

    /**
     * Copy all parameter, excluding non string and integer.
     *
     * @param request source copy
     */
    public void copyGetParameterFromRequest(HttpServletRequest request) {
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paramName = enu.nextElement();
            paramsHash.put(paramName, request.getParameter(paramName));
        }
    }

    /**
     * Change value in hashmap.
     *
     * @param key to change
     * @param val to change
     */
    public void overrideParam(String key, String val) {
        paramsHash.put(key, val);
    }

    /**
     * Change value in hashmap.
     *
     * @param key to change
     * @param val to change
     */
    public void overrideParam(String key, Integer val) {
        paramsHash.put(key, String.valueOf(val.intValue()));
    }

}
