package com.example.roger.oving6;

import java.io.Serializable;

enum Operand {
    ADD,
    SUB,
    FIN
}

// Object for sending Expression from client to  server
public class Expression implements Serializable {
    private int a, b;
    public Operand op;

    public Expression(int a, int b, Operand op) {
        this.a 		= a;
        this.b 		= b;
        this.op 	= op;
    }

    public Expression(Operand op) {
        // Short hand constructor for FIN creation
        this.op = op;
    }

    public int evaluate() {
        if(op == Operand.ADD) return a + b;
        else return a - b;
    }
}


