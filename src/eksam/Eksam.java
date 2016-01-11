package eksam;

public class Eksam { 

	public static void main(String[] args) {
		System.out.println ("\n == asenda == \n"); 
	  	String s = "Tere, TUDENG,   tore ARVUTI sul!";
     	String t = asenda (s); // "Tere,-TUDENG,---tore-ARVUTI-sul!"
     	System.out.println (s + " > " + t);

     	System.out.println ("\n == negElArv == \n"); 
     	System.out.println (negElArv (new int[]{0}));

     	System.out.println ("\n == allaKeskmise == \n"); 
	  	System.out.println (allaKeskmise (new double[]{0.}));
   

	  	System.out.println ("\n == reaMinid == \n"); 
	  	int[] res = reaMinid (new int[][] { {1,2,3}, {4,5,6} }); // {1, 4}
	  	System.out.println ("{"+res[0]+","+res[1]+"}"); // {1, 4}

	  	System.out.println ("\n == sortByAvg == \n"); 
	  	int[] rese = sortByAvg (new int[][] { {4, 0, 0}, {1, 2, 0}, {4, 0, 0} }); // {1,0}
	  	//{5,3,1},{4,3,5}}
	  	//{{4, 0, 0}, {1, 2, 0}, {4, 0, 0}}
	  	//{{1, 2, 3}, {4, 5}, {2}}
	  	for (int i=0; i < rese.length; i++) {
	  		System.out.print (rese[i] + " ");
	  	}

	  	System.out.println ("\n == greatestPrimeFactor == \n"); 
	  	System.out.println (greatestPrimeFactor (1234)); // 617
	  	System.out.println (greatestPrimeFactor (2)); // 2
	  	System.out.println (greatestPrimeFactor (3)); // 3
	  	System.out.println (greatestPrimeFactor (1)); // 1
	  	System.out.println (greatestPrimeFactor (120)); // 5
	  	System.out.println (greatestPrimeFactor (123456789)); // 3803
   	}

	public static int greatestPrimeFactor (int n) {
		/*
		 * HOIATUS: mul pani timeouti, arvatavasti l�henemine vale
		 */

		int prime = 0;
		
		for(int i = n; i > 1; i--){
			if(n%i == 0){
				int bool = 0;
				for(int j = 2; j <= i; j++){
					if(i%j == 0 && j != i) bool = 1;
				}
				if(bool == 0) prime = i;
			}	
			if(prime != 0) break;
		}

		return prime;
	}

   	public static int[] sortByAvg (int[][] g) {
   		int[] pingerida = new int[g.length];
   		double[] keskmised = new double[g.length];
		double keskmine = 0;
		for (int i = 0; i < g.length; i++) {
			keskmine = 0;
			for (double hinne : g[i]) keskmine += hinne;
			keskmine = keskmine / g[i].length;
			keskmised[i] = keskmine;
		}
		
		for (int j = 0; j < g.length; j++){
			keskmine = 0;
			for (double hinn : g[j]) keskmine += hinn;
			keskmine = keskmine / g[j].length;
				
			int suuremad = 0;
			for (int x = 0; x < keskmised.length; x++){
				if (keskmised[x] > keskmine) suuremad++;
				else if(keskmised[x] == keskmine){
					if(j > x) suuremad++;
				}
			}
			pingerida[suuremad] = j;
		}
   		return pingerida; 
   	} 

	public static int[] reaMinid (int[][] m) { //Loeb ja väljastab iga massiivi kõige madalama numbri
		int[] min = new int[m.length];
		for (int l = 0; l < m.length; l++) {
			min[l] = m[l][0];
			for (int i = 0; i < m[l].length; i++) {
				if (m[l][i] < min[l]) {
					min[l] = m[l][i];
				}
			}
		}
		return min;
	}

   	public static int allaKeskmise (double[] d) { //loeb kokku kõik alla keskmise olevaid arve
		double keskmine = 0.0;
		for (double arv : d) keskmine += arv;
		keskmine = keskmine / d.length;

		int k = 0;
		for (double arv : d) if (arv < keskmine) k++;
			
		return k;
   	}
   
	public static int negElArv (int[] m) { //loeb kõik negatiivsed arvud kokku
		int k = 0;
		for(int arv : m) if(arv < 0) k++;
		return k;
	}

	public static String asenda (String s) { //asendab kõik tühikud "-" märgiga
		return s.replaceAll(" ", "-");
	}
  
}
