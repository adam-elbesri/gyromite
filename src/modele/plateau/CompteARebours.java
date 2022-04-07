package modele.plateau;
public class CompteARebours implements Runnable{

	// VARIABLES
	public final int PAUSE = 1000;	
	public int compteurTemps;
	public String str;
	
	
	// CONSTRUCTEUR
	public CompteARebours(){

		this.compteurTemps = 100; 
		this.str = "TIME: 100";
		
		Thread compteARebours = new Thread(this);
		compteARebours.start();
	}

	
	// GETTERS	
	public int getCompteurTemps() {return compteurTemps;}
	
    public String getStr() {return str;}

	
    // SETTERS
    
    
	// METHODES	
	@Override
	public void run() {		
		
		while(true){ 										
		    try{Thread.sleep(PAUSE);}
			catch (InterruptedException e){}
			this.compteurTemps--;
			this.str = "TIME: " + this.compteurTemps;
		}		
	} 
	
}
