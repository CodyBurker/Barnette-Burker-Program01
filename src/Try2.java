import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Try2 {

	public static void main(String[] args) throws NumberFormatException, ScriptException {
		Stack<Integer> inputList = getInput(); // Get user input
		// Make an ArrayList of ArrayLists,
		// each inner ArrayList contains permeations of numbers.
		Stack<Stack<Integer>> numberPermeations = permute(inputList, 0);
		Stack<char[]> operatorCombinations = generatorOperatorStack();
	}

	// Get 4 integers from user as array
	static Stack<Integer> getInput() {
		System.out.println("Enter four integers");
		Scanner inputScanner = new Scanner(System.in);
		Stack<Integer> inputList = new Stack<>();
		inputList.add(inputScanner.nextInt());
		inputList.add(inputScanner.nextInt());
		inputList.add(inputScanner.nextInt());
		inputList.add(inputScanner.nextInt());
		inputScanner.close();
		return inputList;
	}

	// Method to evaluate string to expression
	static int evaluate(String expression) throws NumberFormatException, ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine javascriptEngine = manager.getEngineByName("javascript");
		Object returnObject = javascriptEngine.eval(expression);
		// If return value is an integer return value
		if (returnObject instanceof Integer) {
			return (int) returnObject;
		}
		// Otherwise, return -1
		else {
			return -1;
		}
	}

	// Method to permute through list
	// Inspiration drawn from
	// https://stackoverflow.com/questions/2920315/permutation-of-array
	static <T> Stack<Stack<T>> permute(Stack<T> list, int bounds) {
		Stack<Stack<T>> returnList = new Stack<>();

		for (int i = bounds; i < list.size(); i++) {
			Collections.swap(list, i, bounds);
			// Call self recursively while adding all previous selves.
			returnList.addAll(permute(list, bounds + 1));
			Collections.swap(list, bounds, i);
		}
		if (bounds == list.size() - 1) {
			// add current permeation to return list
			returnList.add(list);
			// Optionally print to console
			// System.out.println(Arrays.toString(list.toArray()));
			// return returnList if at bottom of barrel...
			return returnList;
		}
		return returnList;
	}

	// Method to generate a stack with all possible combination of operators
	// I'm sure there's a more elegant way to accomplish this...
	// I just don't know what it is.
	static Stack<char[]> generatorOperatorStack() {
		Stack<char[]> operatorStack = new Stack<>();
		char[] operators = { '+', '-', '*', '/' };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {
						char[] pushMe = { operators[i], operators[j], operators[k], operators[l] };
						operatorStack.push(pushMe);
						// System.out.println(operators[i] + " " + operators[j]
						// + " " + operators[k] + " " + operators[l]);
					}
				}
			}
		}
		return operatorStack;
	}
}
