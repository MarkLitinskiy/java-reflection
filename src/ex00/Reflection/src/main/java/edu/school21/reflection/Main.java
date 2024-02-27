package edu.school21.reflection;

import edu.school21.reflection.car.Car;
import edu.school21.reflection.user.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        User user = null;
        Car car = null;
        System.out.println("Classes:");
        System.out.println("    - User");
        System.out.println("    - Car");
        System.out.println("---------------------");
        System.out.println("Enter class ");
        String className = scanner.nextLine();
        try{
            Class clazz;
            if(className.equals("User")){
                clazz = Class.forName(User.class.getName());
                user = (User)clazz.newInstance();
            } else if(className.equals("Car")){
                clazz = Class.forName(Car.class.getName());
                car = (Car)clazz.newInstance();
            }
            System.out.println("---------------------");
            checkMethodsAndFields(user, car);
            System.out.println("---------------------");
            System.out.println("Let’s create an object.");
            if (user != null) {
                creatingObject(user);
                System.out.println("---------------------");
                changeValue(user);
                System.out.println("---------------------");
                callMethod(user);
            } else if (car != null){
                creatingObject(car);
                System.out.println("---------------------");
                changeValue(car);
                System.out.println("---------------------");
                callMethod(car);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private static void checkMethodsAndFields(User user, Car car) {
        Field[] fields = new Field[0];
        Method[] methods = new Method[0];
        if (user != null) {
            fields = user.getClass().getDeclaredFields();
            methods = user.getClass().getDeclaredMethods();
        } else if (car != null) {
            fields = car.getClass().getDeclaredFields();
            methods = car.getClass().getDeclaredMethods();
        }
        // Выводим названия полей
        System.out.println("Fields:");
        for (Field field : fields) {
            System.out.println("\t" + field.getType().getSimpleName() + " " + field.getName());
        }
        // Выводим названия методов
        System.out.println("Methods:");
        for (Method method : methods) {
            if (!method.getName().equals("toString")) {
                System.out.print("\t" + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(parameterTypes[i].getSimpleName());
                }
                System.out.println(")");
            }
        }
    }

    private static void creatingObject(Object object) throws IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                // Разрешаем доступ к private полям
                field.setAccessible(true);
                System.out.println(field.getName() + ":");
                // Устанавливаем значения для полей (в данном случае, просто значения по умолчанию)
                if (field.getType() == String.class) {
                    String stringValue = scanner.nextLine();
                    field.set(object, stringValue);
                } else if (field.getType() == int.class) {
                    int intValue = scanner.nextInt();
                    scanner.nextLine();
                    field.set(object, intValue);
                }

                // Сбрасываем флаг доступа после установки значения
                field.setAccessible(false);
            }
            System.out.println("Object created:" + object);
    }
    private static void changeValue(Object object) throws IllegalAccessException {
        System.out.println("Enter name of the field for changing:");
        Field field = null;
        try {
                String fieldForChanging = scanner.nextLine();
                field = object.getClass().getDeclaredField(fieldForChanging);
                int lastDotIndex = field.getType().toString().lastIndexOf(".");
                System.out.println("Enter " + field.getType().toString().substring(lastDotIndex + 1) + " value:");
            field.setAccessible(true);
                if(field.getType().toString().substring(lastDotIndex + 1).equals("String")){
                    String newValue = scanner.nextLine();
                    field.set(object, newValue);
                } else {
                    int newValue = scanner.nextInt();
                    scanner.nextLine();
                    field.set(object, newValue);
                }
                field.setAccessible(false);
                System.out.println("Object updated: " + object);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    private static void callMethod(Object object) throws IllegalAccessException {
        System.out.println("Enter name of the method for call:");
        Method method = null;
        try {
            String methodForCall = scanner.nextLine();
            methodForCall = methodForCall.substring(0, methodForCall.indexOf("("));
            // Получаем все методы, включая унаследованные
            Method[] methods = object.getClass().getMethods();
            // Ищем метод с заданным именем
            for (Method m : methods) {
                if (m.getName().equals(methodForCall)) {
                    method = m;
                    break;
                }
            }
            if (method == null) {
                throw new NoSuchMethodException("Method not found: " + methodForCall);
            }
            int lastDotIndex = method.getParameterTypes()[0].toString().lastIndexOf(".");
            System.out.println("Enter " + method.getParameterTypes()[0].toString().substring(lastDotIndex + 1) + " value:");
            int newValue = scanner.nextInt();
            System.out.println("Method returned:");
            method.setAccessible(true);
            System.out.println(method.invoke(object, newValue));
        } catch (NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}