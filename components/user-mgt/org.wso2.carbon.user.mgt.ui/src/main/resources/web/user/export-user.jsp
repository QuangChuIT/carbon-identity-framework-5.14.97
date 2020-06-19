<%@ page import="org.apache.axis2.context.ConfigurationContext" %>
<%@ page import="org.wso2.carbon.CarbonConstants" %>
<%@ page import="org.wso2.carbon.ui.CarbonUIUtil" %>
<%@ page import="org.wso2.carbon.utils.ServerConstants" %>
<%@ page import="java.util.*" %>
<%@ page import="javax.swing.*" %>
<%@ page import="org.wso2.carbon.identity.claim.metadata.mgt.dto.LocalClaimDTO" %>
<%@ page import="org.wso2.carbon.identity.claim.metadata.mgt.dto.AttributeMappingDTO" %>
<%@ page import="org.wso2.carbon.identity.claim.metadata.mgt.dto.ClaimPropertyDTO" %>
<%@ page import="org.wso2.carbon.identity.claim.metadata.mgt.util.ClaimConstants" %>
<%@ page import="org.wso2.carbon.identity.claim.metadata.mgt.ClaimMetadataManagementAdminService" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
    LocalClaimDTO[] localClaims = null;
    try {

        ClaimMetadataManagementAdminService adminService = new ClaimMetadataManagementAdminService();
        localClaims = adminService.getLocalClaims();
    } catch (Exception e) {

    }
%>

<fmt:bundle basename="org.wso2.carbon.userstore.ui.i18n.Resources">
    <carbon:breadcrumb label="users"
                       resourceBundle="org.wso2.carbon.userstore.ui.i18n.Resources"
                       topPage="false" request="<%=request%>"/>

    <script type="text/javascript">
        jQuery(document).ready(function () {
        });
        function doExport(domain) {
            if(domain) {
                var attributes = [];
                var displayNames = [];
                $(':checkbox:checked').each(function(i){
                    attributes[i] = $(this).val();
                    displayNames[i] = $(this).attr('name');
                });

                if(typeof attributes !== 'undefined' && attributes.length > 0) {
                    document.location.href='<%=request.getContextPath()%>/exportuser?domain=' +
                        domain + '&attributes=' + attributes + '&displayNames=' + displayNames;
                } else {
                    alert("No attribute selected!")
                }
            }
        }
    </script>
    <div id="middle">
        <h2><fmt:message key="export.user"/></h2>
        <div id="workArea">
            <div style="width:900px; height:730px;">
                <table class="styledLeft noBorders">
                    <thead>
                    <tr>
                        <th colspan="8"><fmt:message key="user.select.attribute"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <%
                        if(localClaims!= null && localClaims.length > 0) {
                            for (int i = 0; i < localClaims.length; i=i+4) {
                    %>
                    <tr>
                        <%
                            for(int column = i; (column < i + 4) && column < localClaims.length; column++) {
                                LocalClaimDTO localClaim = localClaims[column];
                                String localClaimURI = localClaim.getLocalClaimURI();
                                AttributeMappingDTO[] attributeMappings = localClaim.getAttributeMappings();
                                ClaimPropertyDTO[] claimPropertyDTOs = localClaim.getClaimProperties();

                                if (attributeMappings == null) {
                                    attributeMappings = new AttributeMappingDTO[0];
                                }

                                if (claimPropertyDTOs == null) {
                                    claimPropertyDTOs = new ClaimPropertyDTO[0];
                                }

                                Properties claimProperties = new Properties();
                                for (int j = 0; j < claimPropertyDTOs.length; j++) {
                                    claimProperties.put(claimPropertyDTOs[j].getPropertyName(),
                                            claimPropertyDTOs[j].getPropertyValue());
                                }

                                String displayName = localClaimURI;
                                if (claimProperties.containsKey(ClaimConstants.DISPLAY_NAME_PROPERTY)) {
                                    displayName = claimProperties.getProperty(ClaimConstants.DISPLAY_NAME_PROPERTY);
                                }

                                String attributeName = "";

                                for (int attributeCounter = 0; attributeCounter < attributeMappings.length; attributeCounter++) {
                                    AttributeMappingDTO attributeMapping = attributeMappings[attributeCounter];
                                    attributeName = attributeMapping.getAttributeName();
                                }
                        %>
                        <td width="3%"><input type="checkbox" value="<%=attributeName %>" name="<%=displayName %>"></td>
                        <td width="22%"><%=displayName%></td>
                        <%
                            }
                        %>

                    </tr>
                    <%
                            }
                        }
                    %>
                    </tbody>
                </table>
            </div>
            <input type="button" class="button" value="<fmt:message key="export.user"/>"
                   onclick="doExport('<%=request.getParameter("domain") %>');"/>
        </div>
    </div>
</fmt:bundle>
