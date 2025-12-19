package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class Items {
     private Float item1;
    private Float item2;

    public Items() {
    }

    public Items(Float item1, Float item2) {
        this.item1 = item1;
        this.item2 = item2;
    }

    public Float getItem1() {
        return item1;
    }

    public void setItem1(Float item1) {
        this.item1 = item1;
    }

    public Float getItem2() {
        return item2;
    }

    public void setItem2(Float item2) {
        this.item2 = item2;
    }
}
