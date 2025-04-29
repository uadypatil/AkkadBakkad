package com.example.demo;

public class MyData {
    private int id;
    private String col1;
    private String col2;
    private String col3;

    // Constructor
    public MyData(int id, String col1, String col2, String col3) {
        this.id = id;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
    }

    // Getters
    public int getId() { return id; }
    public String getCol1() { return col1; }
    public String getCol2() { return col2; }
    public String getCol3() { return col3; }
}
