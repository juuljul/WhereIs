package com.abcd.projetcnam;

import com.squareup.otto.Bus;

/**
 * Created by julien on 06/03/2016.
 */
public class BusStation {
    private static Bus bus = new Bus();

    public static Bus getBus() {
        return bus;
    }


}
