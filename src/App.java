import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        compteBancaire cb1= new compteBancaire("fedi boukari",-300,500,100);
        cb1.setDecouvertMaximal(700);
        System.out.println(cb1.getSolde());

        Operation operation = new Operation(LocalDateTime.of(2026, 10, 5, 14, 35),TypeOperation.DEPOT,500,"Dépôt en espèces");

        System.out.println(operation);
    }
    public static class compteBancaire {
            private static int compteurGlobal = 0;
            private final int numero;
            private final String titulaire;

            private double solde;
            private double decouvert_Max;
            private double debit_Max;


            private List<Operation> operations;
        
                
            public double getSolde(){
                return solde;
            }
            public void setDecouvertMaximal(double decouvertMaximal){
                if((decouvertMaximal >= 0) && (solde >= -decouvertMaximal)){
                    decouvert_Max = decouvertMaximal;
                }
                else{
                    throw new IllegalArgumentException("decouvert max ne peut pas etre cette valeur ");
                }
                        
            }

            public void setDebitMaximal(double debitMaximal){
                if(debitMaximal>0){
                   debit_Max = debitMaximal; 
                }
                else{
                    throw new IllegalArgumentException("debitmax ne peut pas etre negatifs ");
                }
                    
            }
            public void crediter(double montant){
                if(montant > 0){
                    solde+=montant;
                }
            }
            public boolean debiter(double montant){
                if(solde-montant>-decouvert_Max){
                    solde-=montant;
                    return true;
                }
                else{
                    return false;
                }
            }

            public boolean effectuerRetrait(double montant){

                    if((montant<debit_Max)&&(solde-montant>-decouvert_Max)){
                            solde-=montant;
                        return true;
                    }
                return false;
            }
            public boolean effectuerVirement(compteBancaire destinataire,double montant){

                boolean x=debiter(montant);
                if (x){
                    destinataire.crediter(montant); 
                    return true;
                }
                return false;
            }
            
            public compteBancaire(String titulaire,double solde,double decouvertMaximal,double debitMaximal){

                    numero = ++compteurGlobal;
                    if(titulaire != null && !titulaire.trim().isEmpty()){
                        this.titulaire=titulaire;
                    }else{
                        throw new IllegalArgumentException("titulaire n est pas intialiser");
                    }
                    this.solde=solde;
                    this.decouvert_Max=decouvertMaximal;
                    this.debit_Max=debitMaximal;
                    this.operations = new ArrayList<>();
                    
                
            }
            public compteBancaire(double solde,double decouvertMaximal,double debitMaximal){
                    numero=++compteurGlobal;
                    titulaire = "Inconnu";
                    this.solde=solde;
                    this.decouvert_Max=decouvertMaximal;
                    this.debit_Max=debitMaximal;
                    this.operations = new ArrayList<>();
            }




            
        
            public List<Operation> getOperations(){
                      return operations;  
                    }
            private void ajouterOperation(Operation operation){
                if (operation != null) {
                    operations.add(operation);
                }
            }

        
        }

        public enum TypeOperation {
                    DEPOT,
                    RETRAIT,
                    VIREMENT_ENTRANT,
                    VIREMENT_SORTANT
                }
            
        
    
    
        public static class Operation {
                private LocalDateTime date; // date et heure de l'opération
                private TypeOperation type; // type de l'opération
                private double montant; // montant de l'opération
                private String libelle; // libellé de l'opération

                public boolean existe(TypeOperation type){
                    return type != null;
                }
                

                public Operation(LocalDateTime date,TypeOperation type,double montant,String libelle){
                    if (date != null && libelle != null && !libelle.trim().isEmpty()){
                            this.date = date;
                            this.libelle = libelle;
                    }

                    if (existe(type)){
                            this.type = type;
                    }
                    if(montant > 0){
                        this.montant = montant;
                    }
                
                }

                // ajouter les getters necessaires
                public Operation(TypeOperation type,double montant,String libelle){
                    if (existe(type)){
                            this.type = type;
                    }
                    if (montant > 0){
                        this.montant = montant;
                    }
                    if (libelle != null && !libelle.trim().isEmpty()){
                            this.libelle = libelle;
                    }
                    date = LocalDateTime.now();
                    
                }

                @Override
                public String toString(){
                        return "Operation [date=" + date + ", type=" + type + ", montant=" + montant + ", libelle=" + libelle + "]";
                }

                


                


            }
}
