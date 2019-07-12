package com.kuraki.concurrency.chapter10;

public class BrokerDelegateClassLoader extends MyClassLoader {

    public BrokerDelegateClassLoader() {
    }

    public BrokerDelegateClassLoader(String classDir, ClassLoader parent) {
        super(classDir, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        // 1.根据类的全路径名称进行加锁，确保每一个类在多线程的情况下只被加载一次
        synchronized (getClassLoadingLock(name)) {
            // 2.到已加载类的缓存中查看该类是否已经被加载，如果已经加载则直接返回
            Class<?> klass = findLoadedClass(name);
            // 3.若缓存中没有被加载的类，则需要对其进行首次加载
            if (klass == null) {
                // 4.如果类的全路径以java和javax开头，则直接委托给系统类加载器对其进行加载
                if (name.startsWith("java.") || name.startsWith("javax")) {
                    try {
                        klass = getSystemClassLoader().loadClass(name);
                    } catch (Exception e) {
                        //ignore
                    }
                } else {
                    // 5.如果类不是以java和javax开头，则尝试用我们自定义的类加载进行加载
                    try {
                        klass = this.findClass(name);
                    } catch (ClassNotFoundException e) {
                        // ignore
                    }
                    // 6.若自定义类加载仍旧没有完成对类的加载，则委托给其父加载器进行加载或者系统类加载器进行加载
                    if (klass == null) {
                        if (getParent() != null) {
                            klass = getParent().loadClass(name);
                        } else {
                            klass = getSystemClassLoader().loadClass(name);
                        }
                    }
                }
            }
            // 7.经过若干次的尝试之后，如果还是无法对类进行加载，则抛出异常
            if (null == klass) {
                throw new ClassNotFoundException("The class " + name + " not found.");
            }
            if (resolve) {
                resolveClass(klass);
            }
            return klass;
        }
    }
}
