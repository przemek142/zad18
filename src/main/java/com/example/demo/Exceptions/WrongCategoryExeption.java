package com.example.demo.Exceptions;

public class WrongCategoryExeption extends Exception{
    public WrongCategoryExeption(){
        super("Cstegory doesn't exist!");
    }
}
