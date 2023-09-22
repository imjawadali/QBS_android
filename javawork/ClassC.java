public class ClassC {
    public static void main(String[] args) {
        int n = 5;
        int m = 5;
        // for(int i=1;i<=n;i++){   
        //     for(int k=1;k<=n-i;k++){
        //         System.out.print(" ");
        //     }
        //         for(int j=1;j<=m;j++){
        //             System.out.print("*");
        //             }
        //             System.out.println();
        //     }
        // for(int i=1;i<=n;i++){
        //     for(int j=1;j<=n-i;j++){
        //     System.out.print(" ");
        //     }
        //     for(int k=1;k<=i;k++){
        //         System.out.print(i);
        //     }
        //     System.out.println();
        // }
            
        for(int i=1;i<=n;i++){
            for(int k=1;k<=n;k++){
                System.out.print(" ");
            }
           for(int j=1;j<=i+1;j++){
            System.out.print(i);
           }
           System.out.println();
        }
           System.out.println("");
        }
    }
