package com.kuraki.chapter07;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceTest {

    public static AtomicReference<User> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {

        User user = new User("conan", 15);
        atomicUserRef.set(user);
        User updateUser = new User("kuraki", 17);
        atomicUserRef.compareAndSet(user, updateUser);
        System.out.println(atomicUserRef.get().getName());
        System.out.println(atomicUserRef.get().getOld());
    }

    static class User {
        private String name;
        private int old;

        User(String name, int old) {
            this.name = name;
            this.old = old;
        }

        String getName() {
            return name;
        }

        int getOld() {
            return old;
        }
    }
}
