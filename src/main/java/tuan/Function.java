package tuan;

import java.util.Iterator;
import java.util.List;

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.Modifier;
import javassist.bytecode.AttributeInfo;
import javassist.bytecode.NestHostAttribute;

public class Function {
    public boolean hasNestHostAttribute(CtClass cc) {
        List<AttributeInfo>  attrList = cc.getClassFile2().getAttributes();
        Iterator<AttributeInfo> i = attrList.iterator();
        while (i.hasNext()) {
            if (i.next().getName().equals(NestHostAttribute.tag)) return true;
        }
        return false;
    }
    public String getNestHost(CtClass cc) {
        NestHostAttribute attr = (NestHostAttribute) cc.getClassFile2().getAttribute(NestHostAttribute.tag);
        int index = attr.hostClassIndex();
        return cc.getClassFile2().getConstPool().getClassInfo(index);
    }
    public boolean execute(CtBehavior x, CtBehavior y) {
        CtClass xClass = x.getDeclaringClass();
        CtClass yClass = y.getDeclaringClass();
        String xPackage = xClass.getPackageName();
        String yPackage = yClass.getPackageName();
        String xNestHost = hasNestHostAttribute(xClass) ? getNestHost(xClass): xClass.getName();
        String yNestHost = hasNestHostAttribute(yClass) ? getNestHost(yClass): yClass.getName();
        if (xNestHost.equals(yNestHost)) return true;
        if (xPackage.equals(yPackage)) 
            return !(Modifier.isPrivate(y.getModifiers()) || Modifier.isProtected(y.getModifiers()));
        else {
            if (!Modifier.isPublic(yClass.getModifiers())) return false;
            return Modifier.isPublic(y.getModifiers());
        }
    }
}
