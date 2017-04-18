package tag;


import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.*;

/**
 * Created by vkarassouloff on 18/04/17.
 */
public class LinkTag extends SimpleTagSupport {

    private String test;

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        out.println("Hello Custom Tag!");
        //this.getJspContext().getELContext();
        getJspContext().setAttribute(test, test);
        for(int i = 0 ; i < 3 ; i++) {
            //getJspBody().invoke(out);
            getJspBody().invoke(null);
        }

    }





    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}