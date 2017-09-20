import java.util.Collections;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

// Expression.java found on https://github.com/opalkale/expression-evaluator/blob/master/Expression.java
// The above file was open-source and is used in compliance with the author's wishes
public class Exercise20_17 {

	public static void main(String[] args) throws NumberFormatException, ScriptException {
		ArrayList<Integer> inputArrayList = getInput(); // Get user input
		// Make an ArrayArrayList of ArrayArrayLists,
		// each inner ArrayArrayList contains permeations of numbers.
		Timer timer = new Timer();
		timer.start();
		ArrayList<ArrayList<Integer>> numberPermeations = new ArrayList<ArrayList<Integer>>();
		permute(inputArrayList, 0, numberPermeations);
		ArrayList<ArrayList<Character>> operatorCombinations = generatorOperatorArrayList();
		ArrayList<String[]> parenthesesCombinations = buildParentheseArrayList();
		// Build a ArrayList of strings with all combinations of expressions
		ArrayList<String> expressions = new ArrayList<>();
		for (ArrayList<Integer> number : numberPermeations) {
			for (ArrayList<Character> operator : operatorCombinations) {
				for (String[] parenthesis : parenthesesCombinations) {
					// Using string builder to avoid concatenation overhead
					// Pattern is as follows
					// (_(_A_+_(_B_)_+_(_C_)_+_D_)_)
					// 1_2_____3___4___5___6_____7_8
					StringBuilder exp = new StringBuilder();
					exp.append(parenthesis[0]);
					exp.append(parenthesis[1]);
					exp.append(number.get(0));
					exp.append(operator.get(0));
					exp.append(parenthesis[2]);
					exp.append(number.get(1));
					exp.append(parenthesis[3]);
					exp.append(operator.get(1));
					exp.append(parenthesis[4]);
					exp.append(number.get(2));
					exp.append(parenthesis[5]);
					exp.append(operator.get(2));
					exp.append(number.get(3));
					exp.append(parenthesis[6]);
					exp.append(parenthesis[7]);

					// expressions eventually has 24*256*9 = 43,008 entries.
					// System.out.println(exp.toString());
					expressions.add(exp.toString());
				}
			}
		}

		System.out.println("All " + expressions.size()
				+ " possible arrangements of numbers, operators, and parentheses generated in "
				+ (double) timer.stop() / 1000 + " seconds.");
		System.out.println("Beginning evaluation of these expressions.");
		timer.start();
		// Evaluate expressions
		ArrayList<String> validExpressions = evaluateExpression(expressions, 24);
		System.out.println("Results:");
		int i = 0;
		for (String current : validExpressions) {
			System.out.println(i + ": " + current);
			i++;
		}
		if (!validExpressions.isEmpty()) {
			System.out.println("All " + i + " solutions to puzzle found in " + timer.stop() / 1000. + " seconds.");
		} else {
			System.out.println("No solutions found. It took " + timer.stop() / 1000.
					+ " seconds of my precious time to figure this out for you.");
		}
	}

	// Get 4 integers from user as array
	static ArrayList<Integer> getInput() {
		System.out.println("Enter four integers");
		Scanner inputScanner = new Scanner(System.in);
		ArrayList<Integer> inputArrayList = new ArrayList<>();
		inputArrayList.add(inputScanner.nextInt());
		inputArrayList.add(inputScanner.nextInt());
		inputArrayList.add(inputScanner.nextInt());
		inputArrayList.add(inputScanner.nextInt());
		inputScanner.close();
		return inputArrayList;
	}

	static ArrayList<String> evaluateExpression(ArrayList<String> expressions, int targetNumber) {
		ArrayList<String> returnArray = new ArrayList<>();

		for (String expression : expressions) {
			Expression evaluateMe = new Expression(expression);
			BigDecimal result = new BigDecimal("0");
			try {
				result = evaluateMe.eval();
			} catch (Exception e) {
			}
			;
			if (result.compareTo(new BigDecimal(targetNumber + "")) == 0) {
				if (!returnArray.contains(expression)) {
					returnArray.add(expression);
				}
			}
		}
		return returnArray;
	}

	// Method to evaluate ArrayList<String> to expression
	// Was slow so this was replaced with the "Expression.java class" in the src
	// folder
	static ArrayList<String> evaluate(ArrayList<String> expressions, int targetNumber)
			throws NumberFormatException, ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine javascriptEngine = manager.getEngineByName("javascript");
		ArrayList<String> returnList = new ArrayList<>();
		for (String expression : expressions) {
			Object returnObject = javascriptEngine.eval(expression);
			// If return value is an integer and
			if (returnObject instanceof Integer) {
				// if that integer is equal to 24 (the target value) and

				if ((int) returnObject == targetNumber) {
					// Then add it to the list.
					if (!returnList.contains(expression)) {
						returnList.add(expression);
					}
				}
			} else if (returnObject instanceof Double) {
				if ((Double) returnObject == (double) targetNumber) {

					returnList.add(expression);
				}
			}
		}
		return returnList;
	}

	// Method to permute through ArrayList
	// Inspiration drawn from
	// https://ArrayListoverflow.com/questions/2920315/permutation-of-array
	static void permute(ArrayList<Integer> ArrayList, int bounds, ArrayList<ArrayList<Integer>> output) {

		for (int i = bounds; i < ArrayList.size(); i++) {
			Collections.swap(ArrayList, i, bounds);
			// Call self recursively while adding all previous selves.
			permute(ArrayList, bounds + 1, output);
			Collections.swap(ArrayList, bounds, i);
		}
		if (bounds == ArrayList.size() - 1) {
			// add current permeation to return ArrayList
			ArrayList<Integer> tmpLst = new ArrayList<>();
			tmpLst.add(ArrayList.get(0));
			tmpLst.add(ArrayList.get(1));
			tmpLst.add(ArrayList.get(2));
			tmpLst.add(ArrayList.get(3));
			output.add(tmpLst);
			// Optionally print to console
			// return returnArrayList if at bottom of barrel...
		}
	}

	// Method to generate a ArrayList with all possible combination of operators
	// I'm sure there's a more elegant way to accomplish this...
	// I just don't know what it is.
	static ArrayList<ArrayList<Character>> generatorOperatorArrayList() {
		ArrayList<ArrayList<Character>> operatorArrayList = new ArrayList<>();
		char[] operators = { '+', '-', '*', '/' };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {
						ArrayList<Character> tempArrayList = new ArrayList<>();
						tempArrayList.add(operators[i]);
						tempArrayList.add(operators[j]);
						tempArrayList.add(operators[k]);
						tempArrayList.add(operators[l]);
						operatorArrayList.add(tempArrayList);
						// System.out.println(tempArrayList.toString());
					}
				}
			}
		}
		return operatorArrayList;
	}

	// Method to build a ArrayList of ArrayLists of strings that represent
	// parentheses
	public static ArrayList<String[]> buildParentheseArrayList() {
		// Long, tedious, ugly code that could be better.

		// PARENTHESE FORMAT IS AS FOLLOWS
		// (_(_A_+_(_B_)_+_(_C_)_+_D_)_)
		// 1_2_____3___4___5___6_____7_8
		// Above is position in byte represented by parentheses

		ArrayList<String[]> parenPossibilities = new ArrayList<>();
		// A + B + C + D
		String[] variable1 = { "", "", "", "", "", "", "", "" };
		// (A + B)+ C + D
		String[] variable2 = { "(", "", "", ")", "", "", "", "" };
		// (A + B + C) + D
		String[] variable3 = { "(", "", "", "", "", ")", "", "" };
		// A + (B + C) + D
		String[] variable4 = { "", "", "(", "", "", ")", "", "" };
		// A + (B+ C + D)
		String[] variable5 = { "", "", "(", "", "", "", ")", "" };
		// A + B+ (C + D)
		String[] variable6 = { "", "", "", "", "(", "", ")", "" };
		// (A + B) + (C + D)
		String[] variable7 = { "(", "", "", ")", "(", "", "", ")" };
		// ((A+B)+C) + D
		String[] variable8 = { "(", "(", "", ")", "", ")", "", "" };
		// A+(B+(C+D))
		String[] variable9 = { "", "", "(", "", "(", "", ")", ")" };

		parenPossibilities.add(variable1);
		parenPossibilities.add(variable2);
		parenPossibilities.add(variable3);
		parenPossibilities.add(variable4);
		parenPossibilities.add(variable5);
		parenPossibilities.add(variable6);
		parenPossibilities.add(variable7);
		parenPossibilities.add(variable8);
		parenPossibilities.add(variable9);

		return parenPossibilities;
	}
}

class Timer {
	long startMillis;
	long stopMillis;

	Timer() {

	}

	void start() {
		startMillis = System.currentTimeMillis();
	}

	long stop() {
		stopMillis = System.currentTimeMillis();
		return stopMillis - startMillis;
	}
}
