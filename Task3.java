// import java.util.Scanner;

// public class Task3 {
//     public static void main(String[] args) {
//         Scanner sc = new Scanner(System.in);
//         System.out.println("Input your N:");
//         if(sc.hasNextByte()){
//             System.out.println()
//         }
//     }
// }
import java.util.Scanner;
public class Task3 {
    public static void main(String[] args) throws Exception{
        char c;
        try (Scanner sc = new Scanner(System.in)) {
            c = (char)sc.nextInt();
        }
        System.out.println(c);
    }
}