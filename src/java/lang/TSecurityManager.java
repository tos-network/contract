package java.lang;

import java.security.*;
import java.io.FileDescriptor;
import java.net.InetAddress;
import java.lang.reflect.ReflectPermission;

public final class TSecurityManager extends SecurityManager {

    /**
     * Constructs a new <code>SecurityManager</code>.
     */
    public TSecurityManager() {
        super();
    }

    @Override
    public boolean getInCheck() {
        return false;
    }

    @Override
    protected ClassLoader currentClassLoader() {
        return null;
    }

    @Override
    protected Class<?> currentLoadedClass() {
        return null;
    }

    @Override
    protected int classLoaderDepth() {
        return 0;
    }

    @Override
    protected boolean inClass(String name) {
        return false;   
    }

    @Override
    protected boolean inClassLoader() {
        return false;
    }

    @Override
    public Object getSecurityContext() {
        return null;
    }

    @Override
    public void checkPermission(Permission perm) {
        throw new SecurityException();
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        throw new SecurityException();
    }

    @Override
    public void checkCreateClassLoader() {
        throw new SecurityException();
    }

    @Override
    public void checkAccess(Thread t) {
        throw new SecurityException();
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        throw new SecurityException();
    }

    @Override
    public void checkExit(int status) {
        throw new SecurityException();
    }

    @Override
    public void checkExec(String cmd) {
        throw new SecurityException();
    }

    @Override
    public void checkLink(String lib) {
        throw new SecurityException();
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        throw new SecurityException();
    }

    @Override
    public void checkRead(String file) {
        throw new SecurityException();
    }

    @Override
    public void checkRead(String file, Object context) {
        throw new SecurityException();
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        throw new SecurityException(); 
    }

    @Override
    public void checkWrite(String file) {
        throw new SecurityException();
    }

    @Override
    public void checkDelete(String file) {
        throw new SecurityException();
    }

    @Override
    public void checkConnect(String host, int port) {
        throw new SecurityException();
    }

    @Override
    public void checkConnect(String host, int port, Object context) {
        throw new SecurityException();
    }

    @Override
    public void checkListen(int port) {
        throw new SecurityException();
    }

    @Override
    public void checkAccept(String host, int port) {
        throw new SecurityException();
    }

    @Override
    @Deprecated
    public void checkMulticast(InetAddress maddr) {
        throw new SecurityException();
    }

    @Override
    @Deprecated
    public void checkMulticast(InetAddress maddr, byte ttl) {
        throw new SecurityException();
    }

    @Override
    public void checkPropertiesAccess() {
        throw new SecurityException();
    }

    @Override
    public void checkPropertyAccess(String key) {
        throw new SecurityException();
    }

    @Override
    public boolean checkTopLevelWindow(Object window) {
        return false;
    }

    @Override
    public void checkPrintJobAccess() {
        throw new SecurityException();
    }

    @Override
    public void checkSystemClipboardAccess() {
        throw new SecurityException();
    }

    @Override
    public void checkAwtEventQueueAccess() {
        throw new SecurityException();
    }

    @Override
    public void checkPackageAccess(String pkg) {
        throw new SecurityException();
    }

    @Override
    public void checkPackageDefinition(String pkg) {
        throw new SecurityException();
    }

    @Override
    public void checkSetFactory() {
        throw new SecurityException();
    }

    @Override
    @Deprecated
    public void checkMemberAccess(Class<?> clazz, int which) {
        throw new SecurityException();
    }

    @Override
    public ThreadGroup getThreadGroup() {
        return Thread.currentThread().getThreadGroup();
    }

    @Override
    public void checkSecurityAccess(String target) {
        throw new SecurityException();
    }

    @Override
    @Deprecated
    protected int classDepth(String name) {
        return -1;
    }

    @Override
    @Deprecated
    protected Class<?>[] getClassContext() {
        return null;
    }
}