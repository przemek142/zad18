package com.example.demo.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ResponseModel {

    public enum Types {
        HTML, JSON,
        TABLE, MESSAGE, PRODUCT, LISTOFPRODUCTS, LISTOFSTRINGS,
        ERROR, SUCCESS
    }

    public enum Struct{
        HEADER, PAYLOAD,
        RETURN, INPUT, OUTPUT
    }


    private HashMap<Struct, Types> header;
    private HashMap<Integer, ArrayList<Object>> payload;
    private HashMap<Struct,Object> out;

    public HashMap<Struct, Types> getHeader() {
        return header;
    }

    public HashMap<Integer, ArrayList<Object>> getPayload() {
        return payload;
    }

    public ResponseModel(Types ret, Types input, Types output, HashMap<Integer, ArrayList<Object>> payload) {
        this.payload = payload;
        header = new HashMap<>();

        header.put(Struct.RETURN,ret);
        header.put(Struct.INPUT,input);
        header.put(Struct.OUTPUT, output);

        out =new HashMap<>();
        out.put(Struct.HEADER, header);
        out.put(Struct.PAYLOAD,payload);
    }

    public HashMap<Struct, Object> getOut() {
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseModel that = (ResponseModel) o;
        return Objects.equals(header, that.header) &&
                Objects.equals(payload, that.payload) &&
                Objects.equals(out, that.out);
    }

    @Override
    public int hashCode() {
        return Objects.hash(header, payload, out);
    }

    @Override
    public String toString() {
        return "ResponseModel{" +
                "out=" + out +
                '}';
    }
}


