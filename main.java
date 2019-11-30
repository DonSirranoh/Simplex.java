package simplex;
import java.util.Scanner;
public class main {

	public static void main(String[] args) {                                                                                                                                                                                                                                                                                                                                                                                                        
		Scanner sc = new Scanner(System.in);
		System.out.println("----------- Metodo Simplex -----------");
		System.out.println("0 - Minimizar\n1 - Maximizar");
		int tipo = sc.nextInt();
		
		System.out.print("\nInfomr o tamanho de linhas e colunas\nLinhas: ");
		int linhas = sc.nextInt();
		System.out.print("\nColunas: ");
		int colunas = sc.nextInt();
		
		float mat_A[][] = new float[linhas][colunas]; 
		
		System.out.println("Agora insira a matriz:");
		for (int i = 0 ; i < linhas ; i++) {
			for (int j = 0 ; j < colunas ; j++ ) {
				System.out.printf("X%d%d: ",i+1,j+1);
				mat_A[i][j] = sc.nextFloat();
				
			}
			System.out.printf("\n");
		}
		 
		System.out.println("Termos Indepentes:");
		float vet_B [] = new float[linhas];
		for (int i = 0 ; i < linhas ; i++ ) {
			System.out.printf("B%d: ",i+1);
			vet_B[i] = sc.nextFloat();
			
		}
		System.out.printf("\n");
		
		
		System.out.println("Insira a função: [Obs: 0 para parar de adiconar]");
		float fun_z[] = new float[colunas];
		for (int i = 0 ; i < colunas ; i++) {
			fun_z[i] = 0;
		}
		
		for (int i = 0 ; i < colunas ; i++) {
			float resp = sc.nextFloat();
			if(resp ==0) {
				break;
			}else {
				fun_z[i] = resp; 
				System.out.printf("\n");
			}
		}
		
		char [] vet_sinais = new char[linhas];
		for (int i = 0 ; i <linhas ;i++) {
			vet_sinais[i] = '<';
		}
		int [] tam_mat_A = {linhas,colunas}; 
		
		Simplex s= new Simplex(tipo,tam_mat_A,mat_A,vet_B,vet_sinais,fun_z);
		s.runSimplex();
		sc.close();
	}

}
