package org.example;

import hu.fenyvesvolgyimate.storeregister.SlimStoreRegister;
import hu.fenyvesvolgyimate.storeregister.StorePersistenceType;
import hu.fenyvesvolgyimate.storeregister.StoreRegister;

import java.text.MessageFormat;
import java.util.Scanner;

public class Main {
    private static Scanner sc;
    private static StoreRegister storeRegister;
    public static void main(String[] args) {
        sc = new Scanner(System.in);
        System.out.println("Üdvözlet!");
        storeRegister = new SlimStoreRegister();
        storeRegister.setPersistanceType(readPersistenceType());

        String operation = null;
        while (isNotExitKey(operation)){
            operation = readOperation();
            if(isNotExitKey(operation)) {
                try {
                    processOperation(operation);
                }catch (Exception ex){
                    System.out.println("Sikeretelen művelet!");
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private static StorePersistenceType readPersistenceType() {
        String storeType = null;
        while(isNotStorageTypeKey(storeType)) {
            System.out.println("Add meg az adattárolási módot!");
            System.out.print("Fájl alapú(f) vagy memória alapú (m): ");
            storeType = sc.next();
            if(isNotStorageTypeKey(storeType))
                System.out.println("Érvénytelen adattárolási mód!");
        }
        assert storeType != null;
        return switch (storeType){
            case "f" -> StorePersistenceType.File;
            case "m" -> StorePersistenceType.InMemory;
            default -> throw new RuntimeException();
        };
    }

    private static boolean isNotStorageTypeKey(String storageType) {
        return !("f".equals(storageType) || "m".equals(storageType));
    }

    private static boolean isNotExitKey(String operation) {
        return !"q".equals(operation);
    }

    private static String readOperation() {
        System.out.println("Válassz tevékenységet!");
        String operationKey = null;
        while(isNotOperationKey(operationKey)) {
            System.out.print("Létrehozás (l), árufeltöltés (f), vásárlás (v), kilépés(q): ");
            operationKey = sc.next();
            if(isNotOperationKey(operationKey))
                System.out.println("Érvénytelen tevékenység!");
        }
        return operationKey;
    }

    private static boolean isNotOperationKey(String operation) {
        return !("l".equals(operation) || "f".equals(operation) || "v".equals(operation) || "q".equals(operation));
    }

    private static void processOperation(String operation) {
        switch (operation){
            case "l" -> createProduct();
            case "f" -> buyProduct();
            case "v" -> sellProduct();
        }
    }

    private static void sellProduct() {
        System.out.print("Add meg a vásárolni kívánt termék nevét: ");
        String productName = sc.next();
        System.out.print("Add meg a darabszámot: ");
        int amount = sc.nextInt();
        int soldAmount = storeRegister.sellProductItem(productName, amount);
        System.out.println(MessageFormat.format("Sikeresen megvásároltál {0} db terméket!", soldAmount));
    }

    private static void buyProduct() {
        System.out.print("Add meg a termék nevét: ");
        String productName = sc.next();
        System.out.print("Add meg a darabszámot: ");
        int amount = sc.nextInt();
        storeRegister.buyProductItem(productName, amount);
        System.out.println("Sikeres feltöltés!");
    }

    private static void createProduct() {
        System.out.print("Add meg a termék nevét: ");
        String productName = sc.next();
        storeRegister.createProduct(productName);
        System.out.println("Termék sikeresen hozzáadva!");
    }
}