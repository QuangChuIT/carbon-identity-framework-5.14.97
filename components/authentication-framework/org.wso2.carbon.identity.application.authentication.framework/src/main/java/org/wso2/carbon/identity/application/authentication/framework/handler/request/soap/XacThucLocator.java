/**
 * XacThucLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.wso2.carbon.identity.application.authentication.framework.handler.request.soap;

public class XacThucLocator extends org.apache.axis.client.Service implements org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.XacThuc {

    public XacThucLocator() {
    }


    public XacThucLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public XacThucLocator(String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BasicHttpBinding_IXacThuc
    private String BasicHttpBinding_IXacThuc_address = "http://lienthong.quangninh.gov.vn:9000/XacThucEmailQN/XacThuc.svc";

    public String getBasicHttpBinding_IXacThucAddress() {
        return BasicHttpBinding_IXacThuc_address;
    }

    // The WSDD service name defaults to the port name.
    private String BasicHttpBinding_IXacThucWSDDServiceName = "BasicHttpBinding_IXacThuc";

    public String getBasicHttpBinding_IXacThucWSDDServiceName() {
        return BasicHttpBinding_IXacThucWSDDServiceName;
    }

    public void setBasicHttpBinding_IXacThucWSDDServiceName(String name) {
        BasicHttpBinding_IXacThucWSDDServiceName = name;
    }

    public org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.IXacThuc getBasicHttpBinding_IXacThuc() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BasicHttpBinding_IXacThuc_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBasicHttpBinding_IXacThuc(endpoint);
    }

    public org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.IXacThuc getBasicHttpBinding_IXacThuc(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.BasicHttpBinding_IXacThucStub _stub = new org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.BasicHttpBinding_IXacThucStub(portAddress, this);
            _stub.setPortName(getBasicHttpBinding_IXacThucWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBasicHttpBinding_IXacThucEndpointAddress(String address) {
        BasicHttpBinding_IXacThuc_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.IXacThuc.class.isAssignableFrom(serviceEndpointInterface)) {
                org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.BasicHttpBinding_IXacThucStub _stub = new org.wso2.carbon.identity.application.authentication.framework.handler.request.soap.BasicHttpBinding_IXacThucStub(new java.net.URL(BasicHttpBinding_IXacThuc_address), this);
                _stub.setPortName(getBasicHttpBinding_IXacThucWSDDServiceName());
                return _stub;
            }
        }
        catch (Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("BasicHttpBinding_IXacThuc".equals(inputPortName)) {
            return getBasicHttpBinding_IXacThuc();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "XacThuc");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "BasicHttpBinding_IXacThuc"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

if ("BasicHttpBinding_IXacThuc".equals(portName)) {
            setBasicHttpBinding_IXacThucEndpointAddress(address);
        }
        else
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
