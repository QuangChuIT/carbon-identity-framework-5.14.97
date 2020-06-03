/**
 * IXacThuc.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.wso2.carbon.identity.application.authentication.framework.handler.request.soap;

public interface IXacThuc extends java.rmi.Remote {
    public String authentication(String userName, String passWord, String author) throws java.rmi.RemoteException;
    public Boolean logout(String toKen, String author) throws java.rmi.RemoteException;
    public Boolean isTokenValid(String toKen, String author) throws java.rmi.RemoteException;
    public String[] attributes(String toKen, String author) throws java.rmi.RemoteException;
    public String[][] listUser(String OU, String author) throws java.rmi.RemoteException;
}
