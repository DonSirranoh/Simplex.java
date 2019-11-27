public class Simplex {
	private float mat_A[][];
	private float vet_B [];
	private char vet_sinais[];
	private String[] lista_base;
	private int tam_mat_A[];   /// 0 - linha , 1 - coluna
	private int fun_z[];       // funcao z
	private float tablo1[][];
	private int tam_tamanho_tablo[];  /// 0 - linha , 1 -coluna
	
	public Simplex(int tam_mat_A[],float mat_A[][],float vet_B[],char vet_sinais[],int fun_z[]){
		this.mat_A = mat_A;
		this.vet_B = vet_B;
		this.vet_sinais = vet_sinais;
		this.tam_mat_A = tam_mat_A;
		this.fun_z = fun_z;
		
	}
	
	public float[][] runSimplex() {
		int pivor[] = null;
		boolean saida = false;
		float matResultado[][] = null;
		tablo1 = montarTabloInicial();
		while(saida == false) {
			pivor = testeRazao(tablo1);
			tablo1 = pivotear(tablo1,pivor);
			saida = solucao();	
		}	
		imprimirRelatorio();
		return matResultado;
	}
	
	private float[][] montarTabloInicial() {
		int qBases = 0;
		
		for(int i = 0; i<vet_sinais.length;i++ ) {        //Pega a quantidade de bases a serem adiconadas
			if(vet_sinais[i] == '<') {
				qBases++;
			}
		}
		int matBases[][] = indentidade(qBases);
		
		float matResultado[][] = new float [tam_mat_A[0]+1][tam_mat_A[1]+qBases+1]; // +1 é o espaço de B
		
		for(int i = 0; i < tam_mat_A[0]+1;i++) {
			int cont_aux = 0;
			if(i<tam_mat_A[0]) {
				for(int j = 0; j < tam_mat_A[1]+qBases+1 ;j++) {
					if(j < tam_mat_A[1]) {                         // Inserir A
						matResultado[i][j] = mat_A[i][j];
					}
					if(j >= tam_mat_A[1] && j<tam_mat_A[1]+qBases) {   //  Inserir matriz de v.folgas
						matResultado[i][j] = matBases[i][cont_aux];
						cont_aux++;
					}
					if(j== tam_mat_A[1]+qBases) {  // Inserir vetor B
						matResultado[i][j] = vet_B[i]; 
					}
				}
			}else {
				for( i = 0; i<tam_mat_A[1]+qBases+1;i++) {  // Inserir a a função z na ultima linha
					if (i<fun_z.length) {
						matResultado[tam_mat_A[0]][i] = fun_z[i];
					}else {
						matResultado[tam_mat_A[0]][i] = 0;
					}
				}
			}
		}
		System.out.println("\nMatriz inicial:");
		mostrarMatriz(matResultado,tam_mat_A[0]+1 ,  tam_mat_A[1]+qBases+1);
		
		int tamTablo[] = {tam_mat_A[0]+1,tam_mat_A[1]+qBases+1};
		tam_tamanho_tablo = tamTablo;
		
		inserirBasesInicial(qBases);
		
		return matResultado;
	}
	
	private int[][] indentidade(int tam){       // fazer matriz identidade para varias de folga
		int identidade[][] = new int[tam][tam];
		
		for(int i = 0; i < tam;i++) {
			for(int j = 0; j < tam ;j++) {
				if(i == j ) {
					identidade[i][j] = 1; 
				}else {
					identidade[i][j] = 0;
				}
			}
		}
		
		return identidade;
	}
	
	private void mostrarMatriz(float[][] matBases,int linha,int coluna) {
		for(int i = 0; i < linha;i++) {
			for(int j = 0; j < coluna ;j++) {
				System.out.printf("%.1f ",matBases[i][j]);
			}
			System.out.print("\n");
		}
	}
	
	private int[] testeRazao(float[][] tablo12) {
		int razao[] = new int [2];
		float menor = tablo12[tam_tamanho_tablo[0]-1][0];  // menor valor
		int coluna = 0; // qual a coluna
		for(int i = 1 ; i < tam_tamanho_tablo[1]-1 ; i++ ) {  // -1 por causa da posicao de z;
			if(menor > tablo12[tam_tamanho_tablo[0]-1][i]) {  // Pegar o indice da coluna de menor valor
				menor = tablo12[tam_tamanho_tablo[0]-1][i];
				coluna = i;
			}
		}
		razao[1] = coluna;
		
		for(int i = 0 ; i < tam_tamanho_tablo[0]-1;i++ ) {   // Colocar o primeiro valor aceitavel para comecar as comparaçoes
			float divisor = tablo12[i][tam_tamanho_tablo[1]-1];
			float dividendo = tablo12[i][coluna];
			if(dividendo > 0) {
				menor = divisor/dividendo;     
				razao[0] = i ;
				break;
			}
		}
				
		for(int i = 0 ; i < tam_tamanho_tablo[0]-1;i++ ) {   // comparaçoes
			float divisor = tablo12[i][tam_tamanho_tablo[1]-1];
			float dividendo = tablo12[i][coluna];
			
				if(dividendo <= 0) {
					System.out.println("\ntem 1 q n pode\n");
				}else {
					if((divisor/dividendo) < menor) {
						menor = tablo12[i][tam_tamanho_tablo[1]-1]/tablo12[i][coluna];
						razao[0] = i;
					}	
			}
		}
				
		int n_entra = razao[1]+1;
		String entra = "X"+ n_entra;
		lista_base[razao[0]] = entra; // atualizar Bases
		
		return razao;
	}
	
	private float[][] pivotear(float mat[][],int pivor[]){
		float mat_resultado[][] = mat;
		float vet_aux[] = new float[tam_tamanho_tablo[1]];
		float[] vet_aux_padrao = new float[tam_tamanho_tablo[1]];
		
		
		if(mat_resultado[pivor[0]][pivor[1]] != 1) {    // se o pivor nao for 1, dividir por ele mesmo para ficar 1
			float divisor = mat_resultado[pivor[0]][pivor[1]] ;
			for(int i = 0 ; i<tam_tamanho_tablo[1];i++) {
				mat_resultado[pivor[0]][i] = mat_resultado[pivor[0]][i] / divisor;
				vet_aux[i] = mat_resultado[pivor[0]][i];
				vet_aux_padrao[1] = vet_aux[1];
			}
		}else {
			for(int i = 0 ; i<tam_tamanho_tablo[1];i++) {
				vet_aux[i] = mat_resultado[pivor[0]][i];
				vet_aux_padrao[i] = vet_aux[i];
				
			}
		}
		
			
		for(int q = 0; q < tam_tamanho_tablo[0]; q++) {
			if(q != pivor[0]) {                          // pular a linha do pivor
				
				for(int i = 0 ; i<tam_tamanho_tablo[1];i++) {
					vet_aux[i] = mat_resultado[pivor[0]][i];
					vet_aux_padrao[i] = vet_aux[i];
					
				}
				
				if(mat_resultado[pivor[0]][pivor[1]] != mat[q][pivor[1]]) {      // comparar as outras linhas caso seja diferente de 1
					
					float x = 1;
					if(mat_resultado[q][pivor[1]] < 0) {
						x = -(mat_resultado[q][pivor[1]]) / mat_resultado[pivor[0]][pivor[1]];
					}
					
					if(mat_resultado[q][pivor[1]] > 0) {
						x = mat_resultado[q][pivor[1]] / mat_resultado[pivor[0]][pivor[1]];  // valor a ser multiplicado pela linha do pivor
					}
					
					if(mat_resultado[q][pivor[1]] == 0) {
						x = 1;
					}
					
					
					if(mat_resultado[q][pivor[1]] >= 0) {               // caso o valor da linha seja positivo , multiplicar a linha aux por -1
 						for(int j = 0;j < tam_tamanho_tablo[1];j++) {
							vet_aux[j] = vet_aux[j] * x * -1;
							mat_resultado[q][j] = mat_resultado[q][j] + vet_aux[j];
						}
 						
 						for (int e = 0 ; e<tam_tamanho_tablo[1];e++) {
 							vet_aux[e]=vet_aux_padrao[e];
 						}
 						
					}else {                       //caso ja seja negativa basta apenas somar
						for(int f = 0;f < tam_tamanho_tablo[1];f++) {
							vet_aux[f] = vet_aux[f] * x;
							mat_resultado[q][f] = mat_resultado[q][f] + vet_aux[f];
						}
					}
					
					for (int e = 0 ; e<tam_tamanho_tablo[1];e++) {
						vet_aux[e]=vet_aux_padrao[e];
					}
					
				}else {                // caso no seja igual de 1 , basta apenas somar
					if(mat_resultado[q][pivor[1]] >= 0) {               // caso o valor da linha seja positivo , multiplicar a linha aux por -1
 						for(int j = 0;j < tam_tamanho_tablo[1];j++) {
							vet_aux[j] = vet_aux[j] * -1;
							mat_resultado[q][j] = mat_resultado[q][j] + vet_aux[j];
						}
 						
					}else {                       //caso ja seja negativa basta apenas somar
						for(int p = 0;p < tam_tamanho_tablo[1] ; p++) {
							mat_resultado[q][p] = mat_resultado[q][p] + vet_aux[p];
						}
						
					}
					
					for (int e = 0 ; e <tam_tamanho_tablo[1] ; e++) {
							vet_aux[e] = vet_aux_padrao[e];
						}
				}
			}
		}
		return mat_resultado;
	}
	
	private boolean solucao() {
		boolean saida = false;
		int cont  = 0;
		for(int i = 0 ; i < tam_tamanho_tablo[1] - 1; i++) {
			if(tablo1[tam_tamanho_tablo[0]-1][i] < 0) {
				cont++;
			}
		}
		
		if(cont==0) {
			saida = true;
		}
		
		return saida;
	}
	
	
	private void inserirBasesInicial(int qBases) {
		String[] base = new String[qBases];
		String aux;
		int cont = 0;
		
		for(int i = 1; i <= tam_tamanho_tablo[1]-1; i++ ) {
			if(!(i <= (tam_tamanho_tablo[1]-1)-qBases)) {
				aux = "X"+i;
				base[cont] = aux;
				cont++;
			}
		}
		
		lista_base = base;
		System.out.println("\nDentro da Dentro:");
		for(String f :lista_base) {
			System.out.println(f);
		}
	}
	
	private void imprimirRelatorio() {
		System.out.println("\n\n---------Relatório---------\n");
		System.out.println("Tablô Final:");
		mostrarMatriz(tablo1, tam_tamanho_tablo[0],tam_tamanho_tablo[1]);
		
		System.out.println("\nValores das varaveis:");
		for(int i = 0; i < lista_base.length; i++) {
			System.out.printf("\n%s : %.1f\n",lista_base[i], tablo1[i][tam_tamanho_tablo[1]-1]);
		}
		float z =-tablo1[tam_tamanho_tablo[0]-1][tam_tamanho_tablo[1]-1];
		System.out.printf("\nZ : %.1f", z);
	}
	
}
