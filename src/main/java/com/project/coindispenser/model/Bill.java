package com.project.coindispenser.model;

public enum Bill {
    ONE(1), TWO(2), FIVE(5), TEN(10), TWENTY(20), FIFTY(50), ONE_HUNDRED(100);

    int value;

    Bill(int v) {
        value = v;
    }

    public int value() {
        return value;
    }

    public static boolean isValid(int v) {
        for (Bill  bill : Bill.values()) {
            if (bill.value == v) {
                return true;
            }
        }
        return false;
    }

    public int[] intValues() {
        Bill[] bills = Bill.values();
        int[] values = new int[bills.length];
        
        for (int i = 0; i < bills.length; i++) {
            values[i] = bills[i].value;
        }
        return values;
    }
}
