package br.com.alura.mvc.mudi.controller;

import java.util.HashMap;
import static java.util.Collections.nCopies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private static final int[] INTS = {
            1000, 900, 500,
            400, 100, 90,
            50, 40, 10,
            9, 5, 4,
            1
    };
    private static final String[] ROMANOS = {
            "M", "CM", "D",
            "CD", "C", "XC",
            "L", "XL", "X",
            "IX", "V", "IV",
            "I"
    };

    @GetMapping("/")
    public String main(Model model) {
        return "home";
    }

    @GetMapping("/home")
    public String home(Model model) {

        return "home";
    }

    @PostMapping("/api")
    public String api(@RequestParam String valor, Model model) {
        Object resultado = "Numero invalido";
        try {
            // caso seja informado um valor inteiro, vai dar a exceção e cair no segundo try
            resultado = RomanoParaInt(valor);
        } catch (Exception e) {
            try {
                // caso seja informado um valor invalido, dispara uma exceção e o valor passado
                // para view é o valor inicial da variavel resultado (Numero invalido)
                resultado = IntParaRomano(Integer.parseInt(valor));
            } catch (Exception ec) {
            }

        }
        // adiciona o valor da variavel resultado para view
        model.addAttribute("resultado", resultado);
        // redireciona para page HOME
        return "home";
    }

    public String IntParaRomano(Integer valorInformado) {
        if (String.valueOf(valorInformado).length() > 5) {
            return "Numero > 5 digitos";
        }
        // instancia o contrutor de strings
        StringBuilder resultado = new StringBuilder();
        // percorre o array de inteiros
        for (int i = 0; i < INTS.length; i++) {
            // exemplo: 59
            // 59/1000 = 0 (truncamento)
            int inteiro = valorInformado / INTS[i];
            // subtrai 0 do valor informado
            valorInformado -= INTS[i] * inteiro;
            // concatena ( 59/1000 = 0) vezes o elemento i (0) do array
            resultado.append(nCopies(inteiro, ROMANOS[i]));
            // a cada iteração, adiciona x vezes cada char romano,
            // caso seja 5000 a entrada, 5000/1000 = 5
            // concatena 5 vezes M
            // e o valor informado é reduzido em 5000 virando 0
        }
        // remove caracteres especiais da saida
        String resultadoFinal = resultado.toString().replace("[", "");
        resultadoFinal = resultadoFinal.replace("]", "");
        resultadoFinal = resultadoFinal.replace(",", "");
        resultadoFinal = resultadoFinal.replace(" ", "");
        return resultadoFinal;
    }

    public int RomanoParaInt(String romano) throws Exception {
        romano = romano.toUpperCase();
        HashMap<Character, Integer> romanInt = new HashMap<>();

        romanInt.put('I', 1);
        romanInt.put('V', 5);
        romanInt.put('X', 10);
        romanInt.put('L', 50);
        romanInt.put('C', 100);
        romanInt.put('D', 500);
        romanInt.put('M', 1000);

        int result = 0;

        for (int i = 0; i < romano.length(); i++) {
            // exemplo: DLIV
            // i = 0 NAO ENTRA NO PRIMEIRO IF POR CAUSA DISSO
            // result = D = 500
            // fim da primeira iteração
            // i = 1
            // L = 50
            // 50 > 500? false
            // result = DL = 550
            // fim da segunda iteração
            // i = 2
            // I = 1
            // 1 > 50? false
            // result = DLI = 551
            // fim da terceira iteração
            // i = 3
            // V = 5
            // 5 > 1? true
            // result = result + 5 -(2x 1) [2 vezes Ja que foi somado 1 vez ao inves de ser
            // subtraido]
            // result = 551 + 5
            // result = 556 -2
            // result = 554
            if (i > 0 && romanInt.get(romano.charAt(i)) > romanInt.get(romano.charAt(i - 1))) {
                // caso o char anterior seja menor que o atual, retira o valor do char anterior
                // 2 vezes
                result += romanInt.get(romano.charAt(i)) - 2 * romanInt.get(romano.charAt(i - 1));
            } else {
                // senao, acrescenta o valor atual ao resultado
                result += romanInt.get(romano.charAt(i));
            }
        }
        return result;
    }
}
