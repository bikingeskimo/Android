package android.oving6server;
import java.io.Serializable;


// Object for sending Expression from client to  server
class Expression implements Serializable {
    private int a, b;
    Operand op;

    public Expression(int a, int b, Operand op) {
        this.a 		= a;
        this.b 		= b;
        this.op 	= op;
    }

    public Expression(Operand op) {
        // Short hand constructor for FIN creation
        this.op = op;
    }

    int evaluate() {
        if(op == Operand.ADD) return a + b;
        else return a - b;
    }
}


