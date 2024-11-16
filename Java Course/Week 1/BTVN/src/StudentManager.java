import java.util.ArrayList;

public class StudentManager {

    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(){
        System.out.print("- Nhập tên học sinh: ");
        String name = ScannerManage.scanner.nextLine();
        System.out.print("- Nhập tuổi học sinh: ");
        int age = Integer.parseInt(ScannerManage.scanner.nextLine());
        System.out.print("- Nhập địa chỉ học sinh: ");
        String address = ScannerManage.scanner.nextLine();
        System.out.print("- Nhập điểm Toán: ");
        double gpaMath = Double.parseDouble(ScannerManage.scanner.nextLine());
        System.out.print("- Nhập điểm Văn: ");
        double gpaLiterature = Double.parseDouble(ScannerManage.scanner.nextLine());
        System.out.print("- Nhập điểm Anh: ");
        double gpaEnglish = Double.parseDouble(ScannerManage.scanner.nextLine());

        Student student = new Student(name, age, address, gpaMath, gpaLiterature, gpaEnglish);
        students.add(student);

    }

    public void addStudentList(){
        System.out.print("Nhập vào số học sinh cần thêm: ");
        int numberOfStudent = Integer.parseInt(ScannerManage.scanner.nextLine());
        for (int i = 0; i < numberOfStudent; i++){
            System.out.println("Nhập học sinh thứ " + (i + 1) + ": ");
            addStudent();
        }
    }

    public void showAllStudents(){
        for (int i = 0; i < students.size(); i++){
            System.out.println("Học sinh thứ " + (i + 1) + ": ");
            System.out.println(students.get(i));
        }
    }

    public void findStudent(){
        int numberOfTest = ScannerManage.scanner.nextInt();
        while (numberOfTest > 0){
            int test = ScannerManage.scanner.nextInt();
            System.out.println(students.get(test - 1));
            numberOfTest--;
        }
    }
}
