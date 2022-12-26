package app.trial.warehouse.gr;

import java.io.Serializable;

public class additems implements Serializable {
    public String selmat, material, quantity;

    public additems() {
    }

    public additems(String selmat, String Material, String Quantity) {
        this.selmat = selmat;
        this.material = Material;
        this.quantity = Quantity;
    }

    public String getselmat() {
        return selmat;
    }

    public void setselmat(String mselmat) {
        this.selmat = mselmat;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String mMaterial) {
        this.material = mMaterial;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String mQuantity) {
        this.quantity = mQuantity;
    }
}
