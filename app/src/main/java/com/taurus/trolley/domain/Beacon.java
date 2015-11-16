package com.taurus.trolley.domain;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by semih on 07.11.2015.
 */

@ParseClassName("Beacon")
public class Beacon extends ParseObject {
    public static final String BLUETOOTH_ADDRESS = "bluetoothAddress";
    public static final String SHELF = "shelf";

    public Beacon() {
    }

    public Beacon(String bluetoothAddress, Shelf shelf) {
        setBluetoothAddress(bluetoothAddress);
        setShelf(shelf);
    }

    public String getBluetoothAddress() {
        return getString(BLUETOOTH_ADDRESS);
    }

    public void setBluetoothAddress(String bluetoothAddress) {
        put(BLUETOOTH_ADDRESS, bluetoothAddress);
    }

    public Shelf getShelf() {
        return (Shelf) getParseObject(SHELF);
    }

    public void setShelf(Shelf shelf) {
        put(SHELF, shelf);
    }
}
