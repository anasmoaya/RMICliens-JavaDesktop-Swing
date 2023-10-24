package entities;

import java.io.Serializable;

public class Salle implements Serializable {
    int id;
    String code;

    public Salle() {
    }


    public Salle(int id, String code) {
        this.id = id;
        this.code = code;
    }
    public Salle( String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}
