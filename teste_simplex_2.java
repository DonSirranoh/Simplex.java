package simplex;

public class teste {

	public static void main(String[] args) {
		/*
		int tam_mat_A[] = {2,3};
		float mat_A[][] ={{1,1,2},{1,4,-1}};
		float vet_B [] = {6,4};
		char[] vet_sinais = {'<','<'};
		int fun_z[] = {-2,-1,1};
		
		
		Simplex s = new Simplex(tam_mat_A,mat_A,vet_B,vet_sinais,fun_z);
		s.runSimplex();
		*/
		/*
		int tam_mat_A2[] = {2,2};
		float mat_A2[][] ={{3,6},{4,2}};
		float vet_B2 [] = {60,32};
		char[] vet_sinais2 = {'<','<'};
		int fun_z2[] = {-20,-24};
		
		
		Simplex s2 = new Simplex(tam_mat_A2,mat_A2,vet_B2,vet_sinais2,fun_z2);
		s2.runSimplex();
		
		*/
		//*/
		///*
		int tam_mat_A3[] = {2,3};
		float mat_A3[][] ={{1,2,3},{3,2,2}};
		float vet_B3 [] = {9,15};
		char[] vet_sinais3 = {'<','<'};
		int fun_z3[] = {1,9,1};
		int tipo = 1;
		
		Simplex s3 = new Simplex(tipo,tam_mat_A3,mat_A3,vet_B3,vet_sinais3,fun_z3);
		s3.runSimplex();
		//*/
		/*System.out.println("\n\nSegundo Teste !\n");
		int tam_mat_A1[] = {3,3};
		float mat_A1[][] ={{1,2,1},{1,4,4},{7,9,0}};
		float vet_B1 [] = {6,4,12};
		char[] vet_sinais1 = {'<','<','<'};
		int fun_z1[] = {2,-1,-9};
		
		Simplex p = new Simplex(tam_mat_A1,mat_A1,vet_B1,vet_sinais1,fun_z1);
		p.runSimplex();
		*/
		
	}

}
