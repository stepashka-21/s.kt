import java.lang.StringBuilder
import kotlin.math.*

/*
 * В этом задании необходимо написать программу, способную табулировать сразу несколько
 * функций одной вещественной переменной на одном заданном отрезке.
 */

typealias OneVariableFunction = (Double) -> Double
typealias FunctionName = String

// Сформируйте набор как минимум из десяти вещественных функций одной переменной
val availableFunctions : Map<FunctionName, OneVariableFunction> = mapOf(
    "square" to { x -> x*x },
    "sin" to { x -> sin(x) },
    "sqrt" to { x -> sqrt(abs(x)) },
    "cos" to { x -> cos(x) },
    "tan" to { x -> tan(x) },
    "log2" to { x -> log2(abs(x)) },
    "log10" to { x -> log10(abs(x)) },
    "atan" to { x -> atan(x) },
    "sum5" to { x -> x+5 },
    "lin4" to { x -> x*4 }
)

// Тип данных для представления входных данных
data class InputData(val fromX: Double, val toX: Double, val numberOfPoints: Int,
                     val functionNames: List<String>)

// Чтение входных данных из параметров командной строки
fun prepareData(args: Array<String>): InputData? {
    val start: Double = args[0].toDouble()
    val finish: Double = args[1].toDouble()
    val count: Int = args[2].toInt()
    val list = args.toMutableList().subList(3, args.size)
    return InputData(start, finish, count, list)
}

// Тип данных для представления таблицы значений функций
// с заголовками столбцов и строками (первый столбец --- значение x,
// остальные столбцы --- значения функций). Одно из полей --- количество знаков
// после десятичной точки.
data class FunctionTable (val colf: Int) {
    override fun toString(): String {
        // Код, возвращающий строковое представление таблицы (с использованием StringBuilder)
        // Столбец x выравнивается по левому краю, все остальные столбцы по правому.
        // Для форматирования можно использовать функцию format или класс DecimalFormat
        // (https://www.programiz.com/kotlin-programming/examples/round-number-decimal).
        var str: StringBuilder
        for (i in 0 until colf+1) {

        }

    }
}

/*
 * Возвращает таблицу значений заданных функций на заданном отрезке [fromX, toX]
 * с заданным количеством точек.
 */
fun tabulate(input: InputData): FunctionTable {
    var s: String = ""
    for (i in 0..k) {
        s+=toString()
    }


}

fun main(args: Array<String>) {
    // Входные данные принимаются в аргументах командной строки
    // fromX fromY numberOfPoints function1 function2 function3 ...

    val input = prepareData(args)

    // Собственно табулирование и печать результата (здесь ошибка компиляции, необходимо исправить):
    println(tabulate(input))
}
