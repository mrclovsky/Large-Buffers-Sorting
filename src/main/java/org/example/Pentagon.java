package org.example;

class Pentagon {
    double side1, side2, side3, side4, side5;

    public Pentagon(double s1, double s2, double s3, double s4, double s5) {
        this.side1 = s1;
        this.side2 = s2;
        this.side3 = s3;
        this.side4 = s4;
        this.side5 = s5;
    }

    public double calculatePerimeter() {
        return side1 + side2 + side3 + side4 + side5;
    }

    @Override
    public String toString() {
        return "Pentagon { sides: " + side1 + ", " + side2 + ", " + side3 + ", " + side4 + ", " + side5 + ", PERIMETER: " + calculatePerimeter() +" }";
    }
}
