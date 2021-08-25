package com.gaoqisen.basic;

public class House {

    private String desk;

    private String chest;

    private String chair;

    private String bed;

    // 静态工厂方法new类
    public static House.Builder getBuilder() {
        return new House.Builder();
    }

    public static class Builder{

        private String desk;

        private String chest;

        private String chair;

        private String bed;

        public Builder desk(String desk) {
            this.desk = desk;
            return this;
        }

        public Builder chest(String chest) {
            this.chest = chest;
            return this;
        }

        public Builder chair(String chair){
            this.chair = chair;
            return this;
        }

        public Builder bed(String bed) {
            this.bed = bed;
            return this;
        }

        public House build() {
            return new House(this);
        }
    }

    public House(Builder builder) {
        this.bed = builder.bed;
        this.chair = builder.chair;
        this.chest = builder.chest;
        this.desk = builder.desk;
    }


    @Override
    public String toString() {
        return "House{" +
                "desk='" + desk + '\'' +
                ", chest='" + chest + '\'' +
                ", chair='" + chair + '\'' +
                ", bed='" + bed + '\'' +
                '}';
    }


    public static void main(String[] args) {
        House house = House.getBuilder().bed("床").chair("椅子").desk("桌子").chest("柜子").build();
        System.out.println(house.toString());

        System.out.println(100%3);
        System.out.println(100%3.0);
    }


}
