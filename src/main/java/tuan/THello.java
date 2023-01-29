package tuan;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class THello {
    public static void main(String[] args) {
        // Function function = new Function();
        ClassPool pool = ClassPool.getDefault();
        try {
            pool.insertClassPath("/home/tuandau/Workspace/AndroidPlugin/build/classes");
            CtClass outerClass = pool.get("tuan.OuterClass");
            CtClass innerClass = pool.get("tuan.OuterClass$InnerClass");
            CtMethod outerMethod = outerClass.getDeclaredMethod("outerMethod");
            CtMethod innerMethod = innerClass.getDeclaredMethod("innerMethod");
            Function function = new Function();
            System.out.println(function.execute(outerMethod, innerMethod));
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }
}
