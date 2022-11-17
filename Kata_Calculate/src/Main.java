import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {

        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        int [] arabianInt = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        boolean indAcept = false;
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение(каждый элемент через пробел):");

        String exp = scn.nextLine();
        //Определяем есть ли арифметическое действие:
        int actionIndex=-1;
        for (int i = 0; i < actions.length; i++) {
            if(exp.contains(actions[i])){
                actionIndex = i;
                break;
            }
        }
        //Делим строчку по пробелу
        String[] data = exp.split(" ");
        //Если не нашли арифметического действия и ввели больше двух элеиентов, завершаем программу
        if(actionIndex==-1 || data.length > 3){
            System.out.println("Некорректное выражение");
            return;
        }

        //Определяем, находятся ли числа в одном формате (оба римские или оба арабские)
        if(converter.isRoman(data[0]) == converter.isRoman(data[2])){
            int a,b;
            //Определяем, римские ли это числа
            boolean isRoman = converter.isRoman(data[0]);
            if(isRoman){
                //если римские, то конвертируем их в арабские
                a = converter.romanToInt(data[0]);
                b = converter.romanToInt(data[2]);

            }else{
                //если арабские, конвертируем их из строки в число
                a = Integer.parseInt(data[0]);
                b = Integer.parseInt(data[2]);
            }


            for (int i = 0; i<arabianInt.length; i++){
                for (int j = 0; j<arabianInt.length; j++)
                    if (arabianInt[j]==a & arabianInt[i]==b){
                        indAcept = true;

                        //выполняем с числами арифметическое действие
                        int result;
                        switch (data[1]){
                            case "+":
                                result = a+b;
                                break;
                            case "-":
                                result = a-b;
                                break;
                            case "*":
                                result = a*b;
                                break;
                            default:
                                result = a/b;
                                break;
                        }
                        if(isRoman){
                            //если числа были римские, возвращаем результат в римском числе
                            if(result>0){
                                System.out.println(converter.intToRoman(result));
                            }
                            else{
                                System.out.println("Результат вычислений римских чисел не может быть меньне 1");
                            }

                        }
                        else{
                            //если числа были арабские, возвращаем результат в арабском числе
                            System.out.println(result);
                        }
                    }
            }
        }else{
            System.out.println("Числа должны быть в одном формате");
        }
        if (indAcept == false){
            System.out.println("Ты ввел больше 10 или меньше 1");
        }
    }
}



class Converter {
    TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
    TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

    Converter() {
        romanKeyMap.put('I', 1);
        romanKeyMap.put('V', 5);
        romanKeyMap.put('X', 10);
        romanKeyMap.put('L', 50);
        romanKeyMap.put('C', 100);


        arabianKeyMap.put(100, "C");
        arabianKeyMap.put(90, "XC");
        arabianKeyMap.put(50, "L");
        arabianKeyMap.put(40, "XL");
        arabianKeyMap.put(10, "X");
        arabianKeyMap.put(9, "IX");
        arabianKeyMap.put(5, "V");
        arabianKeyMap.put(4, "IV");
        arabianKeyMap.put(1, "I");

    }


    boolean isRoman(String number){
        return romanKeyMap.containsKey(number.charAt(0));
    }


    String intToRoman(int number) {
        String roman = "";
        int arabianKey;
        do {
            arabianKey = arabianKeyMap.floorKey(number);
            roman += arabianKeyMap.get(arabianKey);
            number -= arabianKey;
        } while (number != 0);
        return roman;


    }

    int romanToInt(String s) {
        int end = s.length() - 1;
        char[] arr = s.toCharArray();
        int arabian;
        int result = romanKeyMap.get(arr[end]);
        for (int i = end - 1; i >= 0; i--) {
            arabian = romanKeyMap.get(arr[i]);

            if (arabian < romanKeyMap.get(arr[i + 1])) {
                result -= arabian;
            } else {
                result += arabian;
            }
        }
        return result;
    }
}

