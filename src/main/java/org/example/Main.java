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
                processOperation(operation);
            }
        }
    }

    private static boolean isNotExitKey(String operation) {
        return !"q".equals(operation);
    }

    private static void processOperation(String operation) {
        switch (operation){
            case "l" -> createProduct();
            case "f" -> buyProduct();
            case "v" -> sellProduct();
        }
    }

    private static void sellProduct() {
        System.out.println("Add meg a vásárolni kívánt termék nevét:");
        String productName = sc.next();
        System.out.printf("Add meg a darabszámot: ");
        int amount = sc.nextInt();
        int soldAmount = storeRegister.sellProductItem(productName, amount);
        System.out.println(MessageFormat.format("Sikeresen megvásároltál {0} db terméket!", soldAmount));
    }

    private static void buyProduct() {
        System.out.println("Add meg a termék nevét: ");
        String productName = sc.next();
        System.out.println("Add meg a darabszámot: ");
        int amount = sc.nextInt();
        storeRegister.buyProductItem(productName, amount);
    }

    private static void createProduct() {
        System.out.println("Add meg a termék nevét:");
        String productName = sc.next();
        storeRegister.createProduct(productName);
        System.out.println("Termék sikeresen hozzáadva!");
    }

    private static String readOperation() {
        System.out.println("Válassz tevékenységet!");
        String operationKey = null;
        while(!isOperationKey(operationKey)) {
            System.out.println("Létrehozás (l), árufeltöltés (f), vásárlás (v):");
            operationKey = sc.next();
            if(!isOperationKey(operationKey))
                System.out.println("Érvénytelen tevékenység!");
        }
        return operationKey;
    }

    private static boolean isOperationKey(String operation) {
        return "l".equals(operation) || "f".equals(operation) || "v".equals(operation);
    }

    private static StorePersistenceType readPersistenceType() {
        String storeType = null;
        while(!isStorageTypeKey(storeType)) {
            System.out.println("Add meg az adattárolási módot!");
            System.out.println("Fájl alapú(f) vagy memória alapú (m):");
            storeType = sc.next();
            if(!isStorageTypeKey(storeType))
                System.out.println("Érvénytelen adattárolási mód!");
        }
        assert storeType != null;
        return switch (storeType){
            case "f" -> StorePersistenceType.File;
            case "m" -> StorePersistenceType.InMemory;
            default -> throw new RuntimeException();
        };
    }

    private static boolean isStorageTypeKey(String storageType) {
        return "f".equals(storageType) || "m".equals(storageType);
    }
}