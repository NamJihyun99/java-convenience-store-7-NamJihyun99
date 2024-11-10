package store.domain;

import store.inventory.common.ExceptionCode;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private final String name;
    private final List<Inventory> inventories = new ArrayList<>();

    public Product(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(ExceptionCode.NAME_BLANK.message);
        }
    }

    public String name() {
        return name;
    }

    public void addInventory(Inventory inventory) {
        validateInventories(this.inventories);
        this.inventories.add(inventory);
    }

    private void validateInventories(List<Inventory> inventories) {
        if (inventories == null || inventories.size() == 2) {
            throw new IllegalArgumentException(ExceptionCode.PROMOTION_EXCEED.message);
        }
    }

    public List<Inventory> inventories() {
        return this.inventories;
    }

}
