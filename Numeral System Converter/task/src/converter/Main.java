package converter;

import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            int sourceRadix = Integer.parseInt(scanner.nextLine());
            String sourceNumber = scanner.nextLine();
            int targetRadix = Integer.parseInt(scanner.nextLine());

            String[] number = String.valueOf(sourceNumber).split("\\.");

            ArrayList<Integer> wholeNumberPartInArray = new ArrayList<>();
            for (int i = 0; i < number[0].length(); i++) {
                wholeNumberPartInArray.add(Character.getNumericValue(number[0].charAt(i)));
            }
            ArrayList<Integer> fractionalPartInArray = new ArrayList<>();
            if (number.length == 2) {
                for (int i = 0; i < number[1].length(); i++) {
                    fractionalPartInArray.add(Character.getNumericValue(number[1].charAt(i)));
                }
            }

            int wholeNumberPart = 0;
            if (!number[0].equals("")) {
                int multiplier = 1;
                for (int i = number[0].length() - 1; i >= 0; i--) {
                    wholeNumberPart += Character.getNumericValue(number[0].charAt(i)) * multiplier;
                    multiplier *= sourceRadix;
                }
            }

            if (sourceRadix == 1) {
                String result = convertFromBaseOne(wholeNumberPartInArray, targetRadix);
                System.out.println(result);
            } else if (targetRadix == 1) {
                String result = convertToBaseOne(wholeNumberPartInArray);
                System.out.println(result);
            } else if (targetRadix > 36 || targetRadix < 1) {
                System.out.println("error");
            } else {
                String wholePart = Integer.toString(wholeNumberPart, targetRadix);
                String fractionalResult = convertFractionalToNewBase(fractionalPartInArray, sourceRadix, targetRadix);
                System.out.println(wholePart + "." + fractionalResult);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public static String convertToBaseOne(ArrayList<Integer> num) {
        for (int i = 0; i < num.size(); i++) {
            num.set(i, Character.getNumericValue(Character.forDigit(num.get(i), 10)));
        }

        int totalInDecimal = 0;
        for (int number : num) {
            totalInDecimal += number;
        }

        return "1".repeat(totalInDecimal);
    }

    public static String convertFromBaseOne(ArrayList<Integer> num, int radix) {
        int numberInDecimal = num.size();
        return Integer.toString(numberInDecimal, radix);
    }

    public static double fractionalPartToDecimal(ArrayList<Integer> num, int radix) {
        double result = 0;
        double divisor = radix;
        for (Integer integer : num) {
            double temp = integer / divisor;
            result += temp;
            divisor *= radix;
        }
        return result;
    }

    public static String convertDecimalFractionalToNewBase(double fraction, int radix) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            fraction *= radix;
            String stringForm = Double.toString(fraction);
            int indexOfDecimal = stringForm.indexOf(".");
            int wholePart = Integer.parseInt(stringForm.substring(0, indexOfDecimal));
            char wholePartInChar = Character.forDigit(wholePart, radix);
            sb.append(wholePartInChar);
            fraction -= Character.getNumericValue(wholePartInChar);
        }
        return sb.toString();
    }

    public static String convertFractionalToNewBase(ArrayList<Integer> num, int sourceRadix, int newRadix) {
        double result = fractionalPartToDecimal(num, sourceRadix);
        return convertDecimalFractionalToNewBase(result, newRadix);
    }
}
