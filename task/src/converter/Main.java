package converter;

import java.util.Scanner;

public class Main {
    static boolean flagAfterDot = false;
    static int whereIsDot = 0;
    static int precision = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int inputRadix = 0;
        int outputRadix = 0;
        String inputNumber = "";

        System.out.println("Input:");
        try {
            inputRadix = scanner.nextInt();
            inputNumber = scanner.next();
            outputRadix = scanner.nextInt();
            inputNumber = inputNumber.toLowerCase();
            if (inputRadix > 36 || outputRadix > 36 ||
                inputRadix < 1 || outputRadix < 1) {
                System.out.println("error");
                System.exit(0);
            }
        } catch (Exception ex) {
            System.out.println("error");
            System.exit(0);
        }

        whereIsDot(inputNumber);

        System.out.println("Output:");
        int[] beforeDot = convertStringToIntBeforeDot(convertStringBeforeDot(inputNumber));
        int[] afterDot = convertStringToIntAfterDot(convertStringAfterDot(inputNumber));
        double decimalNumber;
        if (inputRadix == 10) {
            decimalNumber = Double.parseDouble(inputNumber);
        } else {
            decimalNumber = convertInputToDecimal(beforeDot, afterDot, inputRadix);
        }
        if (outputRadix != 1) {
            System.out.println(convertDecimalToRadixBeforeDot(decimalNumber, outputRadix) + "." + convertDecimalToRadixAfterDot(decimalNumber, outputRadix));
        } else {
            for (int i = 0; i < (int) decimalNumber; i++) {
                System.out.print("1");
            }
        }
    }

    static boolean tryParseInt(char value) {
        try {
            Integer.parseInt(value + "");
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static int convertCharToInt(char value) {
        return value - 87;
    }

    static char convertIntToChar(int value) {
        return (char) (87 + value);
    }

    static void whereIsDot(String inputString) {
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) == '.') {
                flagAfterDot = true;
                whereIsDot = i;
            }
        }
    }

    static String convertStringBeforeDot(String inputString) {
        if (whereIsDot == 0 && flagAfterDot == false) {
            return inputString;
        } else {
            return inputString.substring(0, whereIsDot);
        }
    }

    static String convertStringAfterDot(String inputString) {
        if (whereIsDot == 0 && flagAfterDot == false) {
            return "";
        } else {
            return inputString.substring(whereIsDot + 1, inputString.length());
        }
    }

    static int[] convertStringToIntBeforeDot(String inputString) {
        int[] outputMassive = new int[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            if (inputString.charAt(i) != '.') {
                if (tryParseInt(inputString.charAt(i))) {
                    outputMassive[inputString.length() - 1 - i] = Integer.parseInt(inputString.charAt(i) + "");
                } else {
                    outputMassive[inputString.length() - 1 - i] = convertCharToInt(inputString.charAt(i));
                }
            } else {
                return outputMassive;
            }
        }
        return outputMassive;
    }

    static int[] convertStringToIntAfterDot(String inputString) {
        int[] outputMassive = new int[inputString.length()];
        for (int i = 0; i < inputString.length(); i++) {
            if (tryParseInt(inputString.charAt(i))) {
                outputMassive[i] = Integer.parseInt(inputString.charAt(i) + "");
            } else {
                outputMassive[i] = convertCharToInt(inputString.charAt(i));
            }
        }
        return outputMassive;
    }

    static double convertInputToDecimal(int[] beforeDot, int[] afterDot, int inputRadix) {
        double outputNumber = 0.0;
        for (int i = 0; i < beforeDot.length; i++) {
            outputNumber += beforeDot[i] * Math.pow(inputRadix, i);
        }
        for (int i = 0; i < afterDot.length; i++) {
            outputNumber += afterDot[i] * Math.pow(inputRadix, ( -1 * i - 1));
        }
        return outputNumber;
    }

    static String convertDecimalToRadixBeforeDot(double inputNumber, int outputRadix) {
        StringBuilder outputNumber = new StringBuilder();
        int beforeDot = (int) inputNumber;
        int tempNumber = 0;
        int tempRadixNumber;
        int tempBeforeDot;
        do {
            try {
                tempNumber = beforeDot / outputRadix;
            } catch (Exception ex) {
                System.out.println("error");
                System.exit(0);
            }
            if (beforeDot - tempNumber * outputRadix > 9 && outputRadix > 10) {
                outputNumber.append(convertIntToChar(beforeDot - tempNumber * outputRadix));
            } else {
                outputNumber.append(beforeDot - tempNumber * outputRadix);
            }
            beforeDot = tempNumber;
        } while (beforeDot > outputRadix || beforeDot != 0);

        StringBuilder tempOutputNumber = new StringBuilder();

        for (int i = outputNumber.length() - 1; i >= 0; i--) {
            tempOutputNumber.append(outputNumber.charAt(i));
        }
        return tempOutputNumber.toString();
    }

    static String convertDecimalToRadixAfterDot(double inputNumber, int outputRadix) {
        StringBuilder outputNumber = new StringBuilder();
        inputNumber = inputNumber - (int) inputNumber;
        do {
            inputNumber = inputNumber * outputRadix;
            if ((int) inputNumber > 9) {
                outputNumber.append(convertIntToChar((int) inputNumber));
            } else {
                outputNumber.append((int) inputNumber);
            }
            inputNumber = inputNumber - (int) inputNumber;
        } while (outputNumber.length() < precision);
        return outputNumber.toString();
    }
}
