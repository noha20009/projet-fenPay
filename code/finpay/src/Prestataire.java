import java.util.ArrayList;
import java.util.Scanner;

 public class Prestataire {
 private static int conpteur=0;
 private int idPrestat;
 private String nom;
 private String type;
 private static ArrayList<Prestataire> listePrestat=new ArrayList<>();


 public Prestataire(String nom, String type) {
  conpteur++;
  this.idPrestat =conpteur;
  this.nom = nom;
  this.type = type;

                    }




    //geter et setter

    public int getIdPrestat() {
        return idPrestat;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public void setIdPrestat(int idPrestat) {
        this.idPrestat = idPrestat;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<Prestataire> getListePrestat() {
        return listePrestat;
    }




    // FONCTION



    public static void  menuPrestataire(){

        Scanner scanner = new Scanner(System.in);
        String choix;

        do {
            System.out.println("entrer votre choix \n 1:ajouter prestataire  \n 2:modifier prestataire  \n 3:lister prestataire \n 4:rechercher prestataire  \n 5:supprimer prestataire ");

            choix = scanner.nextLine();
            if (choix.equals("1")) {
                Prestataire.ajouterPrestataire(scanner);
            }
            else if (choix.equals("2")) {
                Prestataire.ModifierPrestataire(scanner);
            }
            else if (choix.equals("3")) {
                Prestataire.ListerPrestataire();
            }
            else if (choix.equals("4")) {
                Prestataire.RercherPrestataire(scanner);
            }
            else if (choix.equals("5")) {
                Prestataire.SuprimerPrestataire(scanner);
            }
            else if (choix.equals("6")) {
                System.out.println("choix invalide");
            }
        }while(!choix.equals("0"));

    }



public static void ajouterPrestataire(Scanner scanner) {
    System.out.println("entrer nom du prestataire");
    String nom;
    while(true){
            nom= scanner.nextLine().trim();
            if(nom.isEmpty()){
                System.out.println("nom ne pas etre vide ");
            }else {
                break;
            }
    }
    System.out.println("entrer type du prestataire");
    String type;
    while(true){
    type = scanner.nextLine().trim();
    if(type.isEmpty()){
        System.out.println("type ne pas etre vide ");
    }else  {
        break;
    }
    }
  Prestataire prestataire = new Prestataire(nom,type);
  getListePrestat().add(prestataire);
    System.out.println("prestataire "+prestataire.getNom() + "   ajouter avec seccus  " );

}




public static void ModifierPrestataire(Scanner scanner) {
    System.out.println("entrer votre ID");
    int IDprest=scanner.nextInt();
    scanner.nextLine();
    Prestataire prestatTrouver = null;
    for(Prestataire prestataire: Prestataire.getListePrestat()){
        if(prestataire.getIdPrestat()==IDprest){
            prestatTrouver=prestataire;
        }
    }
    if(prestatTrouver==null){
        System.out.println("prestataire introvable ");
        return;
    }else {

     System.out.println("choisir le parametre a modifier \n 1: nom  \n 2: type");
 String choix = scanner.nextLine();
 if(choix.equals("1")){
     System.out.println("entrer le nouveau nom");
     String nom = scanner.nextLine();
          prestatTrouver.setNom(nom);
     System.out.println("nom prestataire modifier");
 }else if(choix.equals("2")){
     System.out.println("entrer le nouveau type");
     String type = scanner.nextLine();
     prestatTrouver.setType(type);
     System.out.println("type prestataire modifier");
 }else{
     System.out.println("    choix invalid     ");
 }
    }

}



public static void SuprimerPrestataire(Scanner scanner) {
    System.out.println("entrer ID du prestataire a suprimer ");
     int choix;
     choix= scanner.nextInt();
     Prestataire prestatTrouver=null;
for (Prestataire prestataire:getListePrestat()){
    if(prestataire.getIdPrestat()==choix){
        prestatTrouver = prestataire;
        break;
    }
}
if(prestatTrouver==null){
    System.out.println("prestataire introvable ");
    return ;
}
if (prestatTrouver!=null) {
    listePrestat.remove(prestatTrouver);
    System.out.println("prestataire suprimer ");
}

 }



public static void  ListerPrestataire() {

    System.out.println("liste des prestataire");

    for (Prestataire prestataire:getListePrestat()){
        System.out.println("id : "+prestataire.getIdPrestat()  + " nom : "+prestataire.getNom() + " type : "+prestataire.getType());

    }

 }



public static  Prestataire  RercherPrestataire(Scanner scanner) {
    System.out.println("entrer votre ID");
    int IDprest=scanner.nextInt();
    Prestataire prestatTrouver=null;
    for(Prestataire prestataire:getListePrestat()){
        if(prestataire.getIdPrestat()==IDprest){
            prestatTrouver=prestataire;
             break;
        }

    }
if(prestatTrouver==null) {
        System.out.println("prestataire introvable ");
        return null;
    }
    System.out.println("prestataire trouver "+prestatTrouver.getNom());
  return prestatTrouver;

    }







}
