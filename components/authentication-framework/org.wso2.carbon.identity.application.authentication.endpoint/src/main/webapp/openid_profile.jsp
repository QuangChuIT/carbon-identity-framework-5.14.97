<%--
  ~ Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
  ~
  ~ WSO2 Inc. licenses this file to you under the Apache License,
  ~ Version 2.0 (the "License"); you may not use this file except
  ~ in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~ http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing,
  ~ software distributed under the License is distributed on an
  ~ "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
  ~ KIND, either express or implied.  See the License for the
  ~ specific language governing permissions and limitations
  ~ under the License.
  --%>
<%@ page import="org.owasp.encoder.Encode" %>
<%@ page import="java.io.File" %>
<%@include file="localize.jsp" %>
<%@include file="init-url.jsp" %>

<%
    String[] profiles = request.getParameterValues("profile");
    String[] claimTags = request.getParameterValues("claimTag");
    String[] claimValues = request.getParameterValues("claimValue");
    String openidreturnto = request.getParameter("openid.return_to");
    String openididentity = request.getParameter("openid.identity");
    if (openidreturnto != null && openidreturnto.indexOf("?") > 0) {
        openidreturnto = openidreturnto.substring(0, openidreturnto.indexOf("?"));
    }
%>

    <html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title><%=AuthenticationEndpointUtil.i18n(resourceBundle, "openid2.profile")%></title>

        <link rel="icon" href="images/favicon2.png" type="image/x-icon"/>
        <link href="libs/bootstrap_3.4.1/css/bootstrap.min.css" rel="stylesheet">
        <link href="css/custom-common.css" rel="stylesheet">

        <!--[if lt IE 9]>
        <script src="js/html5shiv.min.js"></script>
        <script src="js/respond.min.js"></script>
        <![endif]-->

        <script type="text/javascript">
            function submitProfileSelection() {
                document.profileSelection.submit();
            }

            function approved() {
                document.getElementById("hasApprovedAlways").value = "false";
                document.profile.submit();
            }

            function approvedAlways() {
                document.getElementById("hasApprovedAlways").value = "true";
                document.profile.submit();
            }
        </script>

    </head>

    <body>

    <!-- header -->
    <%
        File headerFile = new File(getServletContext().getRealPath("extensions/header.jsp"));
        if (headerFile.exists()) {
    %>
            <jsp:include page="extensions/header.jsp"/>
    <% } else { %>
            <jsp:directive.include file="includes/header.jsp"/>
    <% } %>

    <div class="container-fluid body-wrapper">

        <div class="row">
            <div class="col-md-12">

                <!-- content -->
                <div
                        class="container col-xs-12 col-sm-10 col-md-7 col-lg-5 ol-centered wr-content wr-login col-centered">
                    <div>
                        <h2 class="wr-title uppercase blue-bg padding-double white boarder-bottom-blue margin-none">
                            <%=AuthenticationEndpointUtil.i18n(resourceBundle, "openid.user.claims")%>
                        </h2>


                    </div>
                    <div class="boarder-all ">
                        <div class="clearfix"></div>

                        <div class="padding-double login-form">
                            <div>
                                <form action="<%=openidServerURL%>" id="profile" name="profile" class="form-horizontal">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 control-group">
                                        <div class="controls" style="margin-left: 0px !important;">

                                            <%
                                                if (claimTags != null && claimTags.length > 0) { %>
                                            <table class="table table-striped table-bordered">
                                                <tr>
                                                    <th><%=AuthenticationEndpointUtil.i18n(resourceBundle,
                                                            "claim.uri")%></th>
                                                    <th><%=AuthenticationEndpointUtil.i18n(resourceBundle,
                                                            "claim.value")%></th>
                                                </tr>
                                                <%
                                                    for (int i = 0; i < claimTags.length; i++) {
                                                        String claimTag = claimTags[i];
                                                        if ("MultiAttributeSeparator" .equals(claimTag)) {
                                                            continue;
                                                        }
                                                %>
                                                <tr>
                                                    <td><%=Encode.forHtmlContent(claimTag)%>
                                                    </td>
                                                    <td><%=Encode.forHtmlContent(claimValues[i])%>
                                                    </td>
                                                </tr>
                                                <%
                                                    } %>
                                            </table>
                                            <%
                                                }
                                            %>
                                        </div>
                                    </div>

                                    <div style="text-align:left;">
                                        <input type="button" class="btn  btn-primary" id="approve" name="approve"
                                               onclick="javascript: approved(); return false;"
                                               value="<%=AuthenticationEndpointUtil.i18n(resourceBundle,
                                                    "approve")%>"/>
                                        <input type="button" class="btn" id="chkApprovedAlways"
                                               onclick="javascript: approvedAlways();"
                                               value="<%=AuthenticationEndpointUtil.i18n(resourceBundle,
                                                    "approve.always")%>"/>
                                        <input type="hidden" id="hasApprovedAlways" name="hasApprovedAlways"
                                               value="false"/>
                                        <input class="btn" type="reset"
                                               value="<%=AuthenticationEndpointUtil.i18n(resourceBundle,"cancel")%>"
                                               onclick="javascript:document.location.href='<%=Encode.forJavaScript(openidreturnto)%>'"/>
                                    </div>
                                </form>

                            </div>
                            <div class="clearfix"></div>
                        </div>

                    </div>
                </div>
                <!-- /content -->

            </div>
        </div>
        <!-- /content/body -->

    </div>

    <!-- footer -->
    <%
        File footerFile = new File(getServletContext().getRealPath("extensions/footer.jsp"));
        if (footerFile.exists()) {
    %>
            <jsp:include page="extensions/footer.jsp"/>
    <% } else { %>
            <jsp:directive.include file="includes/footer.jsp"/>
    <% } %>

    <script src="libs/jquery_3.4.1/jquery-3.4.1.js"></script>
    <script src="libs/bootstrap_3.4.1/js/bootstrap.min.js"></script>
    </body>
    </html>