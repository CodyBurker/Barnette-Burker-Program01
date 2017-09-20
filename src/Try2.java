import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
		Stack<Stack<Character>> operatorCombinations = generatorOperatorStack();
		Stack<Stack<String>> parenthesesCombinations = buildParentheseStack();

		// Build a list of strings with all combinations of expressions
		LinkedList<String> expressions = new LinkedList<>();
		for (Stack<Integer> numbers : numberPermeations) {
			for (Stack<Character> character : operatorCombinations) {
				for (Stack<String> parenthesis : parenthesesCombinations) {
					// Using string builder to avoid concatenation overhead
					// Pattern is as follows
					// (1+(2)+(3)+4)
					StringBuilder exp = new StringBuilder();
					exp.append(parenthesis.pop());
					exp.append(numbers.pop());
					exp.append(character.pop());
					exp.append(parenthesis.pop());
					exp.append(numbers.pop());
					exp.append(parenthesis.pop());
					exp.append(character.pop());
					exp.append(parenthesis.pop());
					exp.append(numbers.pop());
					exp.append(parenthesis.pop());
					exp.append(character.pop());
					exp.append(numbers.pop());
					exp.append(parenthesis.pop());
					System.out.println(exp.toString());
					expressions.add(exp.toString());

				}
			}
		}

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
	static Stack<Stack<Character>> generatorOperatorStack() {
		Stack<Stack<Character>> operatorStack = new Stack<>();
		char[] operators = { '+', '-', '*', '/' };
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {
						Stack<Character> tempStack = new Stack<>();
						tempStack.push(operators[i]);
						tempStack.push(operators[j]);
						tempStack.push(operators[k]);
						tempStack.push(operators[l]);
						operatorStack.push(tempStack);
						// System.out.println(tempStack.toString());
					}
				}
			}
		}
		return operatorStack;
	}

	// Method to build a stack of stacks of strings that represent parentheses
	public static Stack<Stack<String>> buildParentheseStack() {
		// Long, tedious, ugly code that could be better.

		// PARENTHESE FORMAT IS AS FOLLOWS
		// (_A_+_(_B_)_+_(_C_)_+_D_)
		// 1_____2___3___4___5_____6
		// 0b00123456
		// Above is position in byte represented by parentheses

		Stack<Stack<String>> parenPossibilities = new Stack<>();
		// A + B + C + D
		String[] variable1 = { "", "", "", "", "", "" };
		// (A + B)+ C + D
		String[] variable2 = { "(", "", ")", "", "", "" };
		// (A + B + C) + D
		String[] variable3 = { "(", "", "", "", ")", "" };
		// A + (B + C) + D
		String[] variable4 = { "", "(", "", "", ")", "" };
		// A + (B+ C + D)
		String[] variable5 = { "", "(", "", "", "", ")" };
		// A + B+ (C + D)
		String[] variable6 = { "", "", "", "(", "", ")" };
		// (A + B) + (C + D)
		String[] variable7 = { "(", "", ")", "(", "", ")" };

		List<String> tempList = Arrays.asList(variable1);
		Stack<String> tempStack = new Stack<>();
		tempStack.addAll(tempList);
		parenPossibilities.push(tempStack);

		List<String> tempList2 = Arrays.asList(variable2);
		Stack<String> tempStack2 = new Stack<>();
		tempStack2.clear();
		tempStack2.addAll(tempList2);
		parenPossibilities.push(tempStack2);

		List<String> tempList3 = Arrays.asList(variable3);
		Stack<String> tempStack3 = new Stack<>();
		tempStack3.clear();
		tempStack3.addAll(tempList3);
		parenPossibilities.push(tempStack3);

		List<String> tempList4 = Arrays.asList(variable4);
		Stack<String> tempStack4 = new Stack<>();
		tempStack4.clear();
		tempStack4.addAll(tempList4);
		parenPossibilities.push(tempStack4);

		List<String> tempList5 = Arrays.asList(variable5);
		Stack<String> tempStack5 = new Stack<>();
		tempStack5.clear();
		tempStack5.addAll(tempList5);
		parenPossibilities.push(tempStack5);

		List<String> tempList6 = Arrays.asList(variable6);
		Stack<String> tempStack6 = new Stack<>();
		tempStack6.clear();
		tempStack6.addAll(tempList6);
		parenPossibilities.push(tempStack6);

		List<String> tempList7 = Arrays.asList(variable7);
		Stack<String> tempStack7 = new Stack<>();
		tempStack7.clear();
		tempStack7.addAll(tempList7);
		parenPossibilities.push(tempStack7);

		return parenPossibilities;
	}
}
