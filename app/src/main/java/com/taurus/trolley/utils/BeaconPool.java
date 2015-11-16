package com.taurus.trolley.utils;

import com.orhanobut.hawk.Hawk;

import org.altbeacon.beacon.Beacon;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by semih on 16.11.2015.
 */
public class BeaconPool {
    private static final int CAPACITY = 10;
    private static final String KEY_BEACON_POOL = "beaconPool";
    private static volatile BeaconPool pool = null;

    private Set<Beacon> beacons;

    private BeaconPool() {
        beacons = Hawk.get(KEY_BEACON_POOL, new HashSet<Beacon>(CAPACITY));
    }

    public static synchronized BeaconPool getPool() {
        if (pool == null) {
            pool = new BeaconPool();
        }

        return pool;
    }

    public void add(Beacon beacon) {
        beacons.add(beacon);
    }

    public void remove(Beacon beacon) {
        beacons.remove(beacon);
    }

    public boolean contains(Beacon beacon) {
        return beacons.contains(beacon);
    }

    public int size() {
        return beacons.size();
    }

    public void clear() {
        beacons.clear();
    }

    public void isEmpty() {
        beacons.isEmpty();
    }

    public void save() {
        Hawk.put(KEY_BEACON_POOL, beacons);
    }
}
