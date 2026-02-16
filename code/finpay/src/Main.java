import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
         int choix;
         do{
             System.out.println("enter votre choix \n 1:prestataire  \n 2:payment");
             choix = scanner.nextInt();
             scanner.nextLine();
             if(choix==1){
                 Prestatairedb.menuPrestataire(scanner);
             }
             else if(choix==2){
                 Paiementdb.paimentDBservice(scanner);
             }
             else {
                 System.out.println("choix invalide");
             }
         }while(choix!=0);







    }
}