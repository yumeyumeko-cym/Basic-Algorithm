package test;

public class testExam {
	public static void main(String[] args) {
		fun(49);
	}
	public static int fun(int n) {
		int z = 1;
		if(n<=1) {
			z=z*7;
			System.out.println(n);
			return z;
		}
		else {
			System.out.println(n);
			z+=fun(n/7);
			for(int j=0;j<n*7;j=j+7) {
				z++;
			}
			z+=fun(n/7);
			System.out.println(n);
		}
		return z;
		
		
	}
}

