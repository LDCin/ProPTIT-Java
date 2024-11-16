public class Student {

    private String name;
    private int age;
    private String address;
    private double gpaMath;
    private double gpaLiterature;
    private double gpaEnglish;
    private double gpaAverage;
    private String evaluation;

    public Student(String name, int age, String address, double gpaMath, double gpaLiterature, double gpaEnglish){
        this.name = name;
        this.age = age;
        this.address = address;
        this.gpaMath = gpaMath;
        this.gpaLiterature = gpaLiterature;
        this.gpaEnglish = gpaEnglish;
        this.gpaAverage = (gpaMath + gpaEnglish + gpaLiterature) / 3;
        if (this.gpaAverage >= 8) this.evaluation = "Xuất sắc";
        else if (this.gpaAverage >= 7) this.evaluation = "Giỏi";
        else if (this.gpaAverage >= 6) this.evaluation = "Khá";
        else if (this.gpaAverage >= 5) this.evaluation = "Trung bình";
        else this.evaluation = "Yếu";
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public double getGpaMath() {
        return gpaMath;
    }

    public double getGpaEnglish() {
        return gpaEnglish;
    }

    public double getGpaLiterature() {
        return gpaLiterature;
    }

    public double getGpaAverage() {
        return gpaAverage;
    }

    public String getEvaluation() {
        return evaluation;
    }

    @Override
    public String toString() {
        return String.format(
                "- Tên: %s\n- Tuổi: %d\n- Địa chỉ: %s\n- Điểm Toán: %.2f\n- Điểm Văn: %.2f\n- Điểm Anh: %.2f\n- Điểm Trung bình: %.2f\n- Học lực: %s",
                name, age, address, gpaMath, gpaLiterature, gpaEnglish, gpaAverage, evaluation
        );
    }

}
