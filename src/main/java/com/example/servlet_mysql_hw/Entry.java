package com.example.servlet_mysql_hw;

import java.sql.Date;

public class Entry {

    protected int id;
    protected Date date;
    protected String specification;
    protected double sum;
    protected MovementType movementType;

    public Entry(int id, Date date, String specification, Double sum, MovementType movementType) {
        this.id = id;
        this.date = date;
        this.specification = specification;
        this.sum = sum;
        this.movementType = movementType;
    }

    public Entry(Date date, String specification, double sum, MovementType movementType) {
        this.date = date;
        this.specification = specification;
        this.sum = sum;
        this.movementType = movementType;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getSpecification() {
        return specification;
    }

    public double getSum() {
        return sum;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    @Override
    public String toString() {
        return "{ id " + id +
                ", " + date +
                ", " + specification +
                ", " + sum +
                ", " + movementType +
                " }";
    }
}
