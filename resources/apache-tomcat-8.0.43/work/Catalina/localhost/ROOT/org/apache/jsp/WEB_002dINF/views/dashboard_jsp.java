/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.43
 * Generated at: 2017-04-20 09:34:14 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.WEB_002dINF.views;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class dashboard_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(2);
    _jspx_dependants.put("/WEB-INF/lib/jstl-1.2.jar", Long.valueOf(1492178210000L));
    _jspx_dependants.put("jar:file:/home/vkarassouloff/Bureau/apache-tomcat-8.0.43/webapps/ROOT/WEB-INF/lib/jstl-1.2.jar!/META-INF/c.tld", Long.valueOf(1153377882000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems;

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.release();
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<title>Computer Database</title>\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("<meta charset=\"utf-8\">\n");
      out.write("<!-- Bootstrap -->\n");
      out.write("<link href=\"css/bootstrap.min.css\" rel=\"stylesheet\" media=\"screen\">\n");
      out.write("<link href=\"css/font-awesome.css\" rel=\"stylesheet\" media=\"screen\">\n");
      out.write("<link href=\"css/main.css\" rel=\"stylesheet\" media=\"screen\">\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<header class=\"navbar navbar-inverse navbar-fixed-top\">\n");
      out.write("\t\t<div class=\"container\">\n");
      out.write("\t\t\t<a class=\"navbar-brand\" href=\"dashboard.html\"> Application -\n");
      out.write("\t\t\t\tComputer Database </a>\n");
      out.write("\t\t</div>\n");
      out.write("\t</header>\n");
      out.write("\n");
      out.write("\t<section id=\"main\">\n");
      out.write("\t\t<div class=\"container\">\n");
      out.write("\t\t\t<h1 id=\"homeTitle\">121 Computers found</h1>\n");
      out.write("\t\t\t<div id=\"actions\" class=\"form-horizontal\">\n");
      out.write("\t\t\t\t<div class=\"pull-left\">\n");
      out.write("\t\t\t\t\t<form id=\"searchForm\" action=\"#\" method=\"GET\" class=\"form-inline\">\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t<input type=\"search\" id=\"searchbox\" name=\"search\"\n");
      out.write("\t\t\t\t\t\t\tclass=\"form-control\" placeholder=\"Search name\" /> <input\n");
      out.write("\t\t\t\t\t\t\ttype=\"submit\" id=\"searchsubmit\" value=\"Filter by name\"\n");
      out.write("\t\t\t\t\t\t\tclass=\"btn btn-primary\" />\n");
      out.write("\t\t\t\t\t</form>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<div class=\"pull-right\">\n");
      out.write("\t\t\t\t\t<a class=\"btn btn-success\" id=\"addComputer\" href=\"addComputer.jsp\">Add\n");
      out.write("\t\t\t\t\t\tComputer</a> <a class=\"btn btn-default\" id=\"editComputer\" href=\"#\"\n");
      out.write("\t\t\t\t\t\tonclick=\"$.fn.toggleEditMode();\">Edit</a>\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\n");
      out.write("\t\t<form id=\"deleteForm\" action=\"#\" method=\"POST\">\n");
      out.write("\t\t\t<input type=\"hidden\" name=\"selection\" value=\"\">\n");
      out.write("\t\t</form>\n");
      out.write("\n");
      out.write("\t\t<div class=\"container\" style=\"margin-top: 10px;\">\n");
      out.write("\t\t\t<table class=\"table table-striped table-bordered\">\n");
      out.write("\t\t\t\t<thead>\n");
      out.write("\t\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t\t<!-- Variable declarations for passing labels as parameters -->\n");
      out.write("\t\t\t\t\t\t<!-- Table header for Computer Name -->\n");
      out.write("\n");
      out.write("\t\t\t\t\t\t<th class=\"editMode\" style=\"width: 60px; height: 22px;\"><input\n");
      out.write("\t\t\t\t\t\t\ttype=\"checkbox\" id=\"selectall\" /> <span\n");
      out.write("\t\t\t\t\t\t\tstyle=\"vertical-align: top;\"> - <a href=\"#\"\n");
      out.write("\t\t\t\t\t\t\t\tid=\"deleteSelected\" onclick=\"$.fn.deleteSelected();\"> <i\n");
      out.write("\t\t\t\t\t\t\t\t\tclass=\"fa fa-trash-o fa-lg\"></i>\n");
      out.write("\t\t\t\t\t\t\t</a>\n");
      out.write("\t\t\t\t\t\t</span></th>\n");
      out.write("\t\t\t\t\t\t<th>Computer name</th>\n");
      out.write("\t\t\t\t\t\t<th>Introduced date</th>\n");
      out.write("\t\t\t\t\t\t<!-- Table header for Discontinued Date -->\n");
      out.write("\t\t\t\t\t\t<th>Discontinued date</th>\n");
      out.write("\t\t\t\t\t\t<!-- Table header for Company -->\n");
      out.write("\t\t\t\t\t\t<th>Company</th>\n");
      out.write("\n");
      out.write("\t\t\t\t\t</tr>\n");
      out.write("\t\t\t\t</thead>\n");
      out.write("\t\t\t\t<!-- Browse attribute computers -->\n");
      out.write("\t\t\t\t<tbody id=\"results\">\n");
      out.write("\t\t\t\t\t");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t\t\t\n");
      out.write("\n");
      out.write("\n");
      out.write("\t\t\t\t</tbody>\n");
      out.write("\t\t\t</table>\n");
      out.write("\t\t</div>\n");
      out.write("\t</section>\n");
      out.write("\n");
      out.write("\t<footer class=\"navbar-fixed-bottom\">\n");
      out.write("\t\t<div class=\"container text-center\">\n");
      out.write("\t\t\t<ul class=\"pagination\">\n");
      out.write("\t\t\t\t<li><a href=\"#\" aria-label=\"Previous\"> <span\n");
      out.write("\t\t\t\t\t\taria-hidden=\"true\">&laquo;</span>\n");
      out.write("\t\t\t\t</a></li>\n");
      out.write("\t\t\t\t<li><a href=\"#\">1</a></li>\n");
      out.write("\t\t\t\t<li><a href=\"#\">2</a></li>\n");
      out.write("\t\t\t\t<li><a href=\"#\">3</a></li>\n");
      out.write("\t\t\t\t<li><a href=\"#\">4</a></li>\n");
      out.write("\t\t\t\t<li><a href=\"#\">5</a></li>\n");
      out.write("\t\t\t\t<li><a href=\"#\" aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span>\n");
      out.write("\t\t\t\t</a></li>\n");
      out.write("\t\t\t</ul>\n");
      out.write("\n");
      out.write("\t\t\t<div class=\"btn-group btn-group-sm pull-right\" role=\"group\">\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\">10</button>\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\">50</button>\n");
      out.write("\t\t\t\t<button type=\"button\" class=\"btn btn-default\">100</button>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t</footer>\n");
      out.write("\t<script src=\"js/jquery.min.js\"></script>\n");
      out.write("\t<script src=\"js/bootstrap.min.js\"></script>\n");
      out.write("\t<script src=\"js/dashboard.js\"></script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    boolean _jspx_th_c_005fforEach_005f0_reused = false;
    try {
      _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005fforEach_005f0.setParent(null);
      // /WEB-INF/views/dashboard.jsp(72,5) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setVar("computer");
      // /WEB-INF/views/dashboard.jsp(72,5) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
      _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/WEB-INF/views/dashboard.jsp(72,5) '${computers}'",_jsp_getExpressionFactory().createValueExpression(_jspx_page_context.getELContext(),"${computers}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
      int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
      try {
        int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
        if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
          do {
            out.write("\n");
            out.write("\t\t\t\t\t\t<tr>\n");
            out.write("\t\t\t\t\t\t<td class=\"editMode\"><input type=\"checkbox\" name=\"cb\"\n");
            out.write("\t\t\t\t\t\t\tclass=\"cb\" value=\"0\"></td>\n");
            out.write("\t\t\t\t\t\t<td><a href=\"editComputer.jsp\" onclick=\"\">");
            out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${computer.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
            out.write("MacBook Pro</a></td>\n");
            out.write("\t\t\t\t\t\t<td>2006-01-10</td>\n");
            out.write("\t\t\t\t\t\t<td></td>\n");
            out.write("\t\t\t\t\t\t<td>Apple Inc.</td>\n");
            out.write("\n");
            out.write("\t\t\t\t\t</tr>\n");
            out.write("\t\t\t\t\t");
            int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
            if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
              break;
          } while (true);
        }
        if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
          return true;
        }
      } catch (java.lang.Throwable _jspx_exception) {
        while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
          out = _jspx_page_context.popBody();
        _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
      } finally {
        _jspx_th_c_005fforEach_005f0.doFinally();
      }
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
      _jspx_th_c_005fforEach_005f0_reused = true;
    } finally {
      org.apache.jasper.runtime.JspRuntimeLibrary.releaseTag(_jspx_th_c_005fforEach_005f0, _jsp_getInstanceManager(), _jspx_th_c_005fforEach_005f0_reused);
    }
    return false;
  }
}
