package com.example.LabsM.entity;

public class Transaction {
    private String cardnum;
    private String code;
    private Float amount;
    private String airpacc;

    public Transaction(String cardnum, String code, Float amount, String airpacc) {
        this.cardnum = cardnum;
        this.code = code;
        this.amount = amount;
        this.airpacc = airpacc;
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getAirpacc() {
        return airpacc;
    }

    public void setAirpacc(String airpacc) {
        this.airpacc = airpacc;
    }
}
