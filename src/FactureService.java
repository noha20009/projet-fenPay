import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FactureService {

    List<Facture> factures = new ArrayList<>();
    private int compteurId = 0;

    private Facture creerfacture(Client client, Prestataire prestataire, double montant) {
        Facture facture = new Facture(
                compteurId++,
                LocalDate.now(),
                montant,
                false,
                client,
                prestataire
        );

        factures.add(facture);

        return facture;
    }

    public List<Facture> lister() {
        return factures;
    }

    public Facture modifierFacture(int id, double nouveauMontant, boolean nouveauStatus) {
        for (Facture facture : factures) {
            if (facture.getId() == id && facture.isStatus() == false) {
                facture.setMontant(nouveauMontant);
                facture.setStatus(nouveauStatus);
                return facture;
            }
        }
        return null;
    }
    public Facture filterFacture(int id){
        List<Facture> listfactureFiltrer = factures.stream().filter(f->f.getId()==id).toList();
        listfactureFiltrer.forEach(System.out::println);
        return null;
    }

    }

