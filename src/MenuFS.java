import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MenuFS {

    private static FactureService factureService = new FactureService();
    private static StatistiqueService statistiqueService = new StatistiqueService();

    public static void menuPrincipal() {

        Scanner scanner = new Scanner(System.in);
        String choix;

        do {
            System.out.println("\n===== MENU PRINCIPAL =====");
            System.out.println("1: Gestion Factures");
            System.out.println("2: Statistiques");
            System.out.println("0: Quitter");

            choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    menuFacture(scanner);
                    break;
                case "2":
                    menuStatistique(scanner);
                    break;
            }

        } while (!choix.equals("0"));
    }

    // ================== MENU FACTURE ==================
    private static void menuFacture(Scanner scanner) {

        String choix;

        do {
            System.out.println("\n===== MENU FACTURE =====");
            System.out.println("1: Ajouter facture");
            System.out.println("2: Modifier facture");
            System.out.println("3: Lister factures");
            System.out.println("4: Rechercher facture");
            System.out.println("0: Retour");

            choix = scanner.nextLine();

            switch (choix) {

                case "1":
                    System.out.println("Montant:");
                    double montant = Double.parseDouble(scanner.nextLine());

                    Facture facture = new Facture(
                            0,
                            LocalDate.now(),
                            montant,
                            false,
                            null,
                            null
                    );

                    factureService.creerFacture(facture);
                    break;

                case "2":
                    System.out.println("ID facture:");
                    int id = Integer.parseInt(scanner.nextLine());

                    System.out.println("Nouveau montant:");
                    double newMontant = Double.parseDouble(scanner.nextLine());

                    System.out.println("Nouveau status (true/false):");
                    boolean status = Boolean.parseBoolean(scanner.nextLine());

                    factureService.modifierFacture(id, newMontant, status);
                    break;

                case "3":
                    List<Facture> factures = factureService.lister();
                    for (Facture f : factures) {
                        System.out.println("----------------------");
                        System.out.println("ID: " + f.getId());
                        System.out.println("Date: " + f.getDate());
                        System.out.println("Montant: " + f.getMontant());
                        System.out.println("Status: " + f.isStatus());
                    }
                    break;

                case "4":
                    System.out.println("ID facture:");
                    int searchId = Integer.parseInt(scanner.nextLine());

                    Facture f = factureService.findById(searchId);

                    if (f != null) {
                        System.out.println("Facture trouvée:");
                        System.out.println("Montant: " + f.getMontant());
                    } else {
                        System.out.println("Facture non trouvée");
                    }
                    break;
            }

        } while (!choix.equals("0"));
    }

    // ================== MENU STATISTIQUE ==================
    private static void menuStatistique(Scanner scanner) {

        String choix;

        do {
            System.out.println("\n===== MENU STATISTIQUE =====");
            System.out.println("1: Ajouter paiement");
            System.out.println("2: Afficher rapport");
            System.out.println("0: Retour");

            choix = scanner.nextLine();

            switch (choix) {

                case "1":
                    System.out.println("Montant paiement:");
                    double montant = Double.parseDouble(scanner.nextLine());

                    System.out.println("Commission:");
                    double commission = Double.parseDouble(scanner.nextLine());

                    System.out.println("Facture payée (true/false):");
                    boolean paye = Boolean.parseBoolean(scanner.nextLine());

                    statistiqueService.ajouterPaiement(montant, commission, paye);
                    break;

                case "2":
                    statistiqueService.afficherRapport();
                    break;
            }

        } while (!choix.equals("0"));
    }
}
