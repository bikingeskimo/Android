package android.oving3;

import java.io.Serializable;


/**
 * Created by Roger on 22.09.2017.
 */


public class Friend implements Serializable {
    private String name = "";
    private String birthDate = "";
    private final int ID;
    private static int global_ID = 0;

    public Friend() {
        ID = global_ID++;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return name;
    }
}

