//Exercise20_17.java
//David G Barnette & Cody Burker

import java.util.*;
public class Exercise20_17 {

	public static void main(String[] args) {
	long startTimeM = System.currentTimeMillis();
	Stack<Character> op1 = new Stack<>();
	Stack<Character> op2 = new Stack<>();
	Stack<Character> op3 = new Stack<>();
	Stack<Integer> numbers = new Stack<>();
	int result = 0;

	enterNumbers(numbers);
	stackNumbers(numbers);

	while(!numbers.isEmpty()){
	loadOperatorStacks(op1, op2, op3);	
	result += combineStacks(op1, op2, op3, numbers);
	}
	if(result == 0){
		System.out.println("No solutions were found.");
	}
	long endTimeM = System.currentTimeMillis();
	System.out.println("The computations were completed in " + (endTimeM - startTimeM) + " milliseconds.");
	}
	public static void enterNumbers(Stack numbers){
		Scanner input = new Scanner(System.in);
			System.out.print("Enter 4 numbers (1-13): ");
		for(int i = 0; i < 4; i++){
		int num = input.nextInt();
		if(num > 0 && num < 14){
			numbers.push(num);
		}
		else{
			System.out.println("The entry number " + num + " is out of range.");
		}
		}
		if(numbers.size() < 4){
			System.out.println("Please enter " + (4 - numbers.size()) + " more number(s) (1-13): " ); 
		while(numbers.size() < 4){
			int num = input.nextInt();
			if(num > 0 && num < 14){
				numbers.push(num);
			}
			else{
				System.out.println("The entry number " + num + " is also out of range.  Please start over.  Good Bye.");
			System.exit(1);
			}
		}
		}
		input.close();
		}	
	public static void stackNumbers(Stack<Integer> numbers){
		
	int A = numbers.pop();
	int B = numbers.pop();
	int C = numbers.pop();
	int D = numbers.pop();
	
	numbers.push(A); numbers.push(B); numbers.push(C); numbers.push(D);
	numbers.push(A); numbers.push(B); numbers.push(D); numbers.push(C);
	numbers.push(A); numbers.push(C); numbers.push(B); numbers.push(D);
	numbers.push(A); numbers.push(C); numbers.push(D); numbers.push(B);
	numbers.push(A); numbers.push(D); numbers.push(B); numbers.push(C);
	numbers.push(A); numbers.push(D); numbers.push(C); numbers.push(B);
	numbers.push(B); numbers.push(A); numbers.push(C); numbers.push(D);
	numbers.push(B); numbers.push(A); numbers.push(D); numbers.push(C);
	numbers.push(B); numbers.push(C); numbers.push(A); numbers.push(D);
	numbers.push(B); numbers.push(C); numbers.push(D); numbers.push(A);
	numbers.push(B); numbers.push(D); numbers.push(A); numbers.push(C);
	numbers.push(B); numbers.push(D); numbers.push(C); numbers.push(A);
	numbers.push(C); numbers.push(A); numbers.push(B); numbers.push(D);
	numbers.push(C); numbers.push(A); numbers.push(D); numbers.push(B);
	numbers.push(C); numbers.push(B); numbers.push(A); numbers.push(D);
	numbers.push(C); numbers.push(B); numbers.push(D); numbers.push(A);
	numbers.push(C); numbers.push(D); numbers.push(A); numbers.push(B);
	numbers.push(C); numbers.push(D); numbers.push(B); numbers.push(A);
	numbers.push(D); numbers.push(A); numbers.push(B); numbers.push(C);
	numbers.push(D); numbers.push(A); numbers.push(C); numbers.push(B);
	numbers.push(D); numbers.push(B); numbers.push(A); numbers.push(C);
	numbers.push(D); numbers.push(B); numbers.push(C); numbers.push(A);
	numbers.push(D); numbers.push(C); numbers.push(A); numbers.push(B);
	numbers.push(D); numbers.push(C); numbers.push(B); numbers.push(A);
	}	
	public static int loadOperatorStacks(Stack op1, Stack op2, Stack op3){
	char sum = '+'; char dif = '-'; char prod = '*'; char quo = '/';

	for(int i = 0; i < 4; i++){
		for(int j = 0; j < 16; j ++){
			if(i == 0)
				op1.push(prod);
			else if(i == 1)
				op1.push(quo);
			else if(i == 2)
				op1.push(sum);
			else if(i == 3)
				op1.push(dif);
		}
	}
	for(int i = 0; i < 4; i++){
		for(int j = 0; j < 4; j++){
			if(j == 0){
				op2.push(prod);
				op2.push(prod);
				op2.push(prod);
				op2.push(prod);
			}
			else if(j == 1){
				op2.push(quo);
				op2.push(quo);
				op2.push(quo);
				op2.push(quo);
			}
			else if(j == 2){
				op2.push(sum);
				op2.push(sum);
				op2.push(sum);
				op2.push(sum);
			}
			else if(j == 3){
				op2.push(dif);
				op2.push(dif);
				op2.push(dif);
				op2.push(dif);
			}
		}	
	}
		for(int i = 0; i < 16; i++){
		op3.push(prod);
		op3.push(quo);
		op3.push(sum);
		op3.push(dif);		
	}
	return 0;
	}
	public static int combineStacks(Stack<Character> op1, Stack<Character> op2, Stack<Character> op3, Stack<Integer> numbers){
		int num1 = numbers.pop();
		int num2 = numbers.pop();
		int num3 = numbers.pop();
		int num4 = numbers.pop();
		int result = 0;
		
	while(!op3.isEmpty()){
		int carry = 0;
		char opar = '(';
		char cpar = ')';
		char o1 = op1.pop();
		char o2 = op2.pop();
		char o3 = op3.pop();

		if(o1 == '*'){
			carry = num1 * num2;
			}	
			else if(o1 == '/'){
			carry = num1 / num2;
			}	
			else if(o1 == '+'){
			carry = num1 + num2;
			}	
			else if(o1 == '-'){
			carry = num1 - num2;
			}	
		if(o2 == '*'){
			carry *= num3;
			}	
			else if(o2 == '/'){
			carry /= num3;
			}	
			else if(o2 == '+'){
			carry += num3;
			}	
			else if(o2 == '-'){
			carry -= num3;
			}	
		if(o3 == '*'){
			carry *= num4;
			}	
			else if(o3 == '/'){
			carry /= num4;
			}	
			else if(o3 == '+'){
			carry += num4;
			}	
			else if(o3 == '-'){
			carry -= num4;
			}
		if(carry == 24){
		System.out.println("(" + num1 + "" + o1 + "" + num2 + ")" + o2 + "" + num3 + "" + o3 + "" + num4 + " = " + carry);
		result++;
		}
	}
	return result;	
	}
}