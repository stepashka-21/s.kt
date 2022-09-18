import java.lang.Integer.min

/*
 * Реализуйте игру "Пьяница" (в простейшем варианте, на колоде в 36 карт)
 * https://ru.wikipedia.org/wiki/%D0%9F%D1%8C%D1%8F%D0%BD%D0%B8%D1%86%D0%B0_(%D0%BA%D0%B0%D1%80%D1%82%D0%BE%D1%87%D0%BD%D0%B0%D1%8F_%D0%B8%D0%B3%D1%80%D0%B0)
 * Рука — это набор карт игрока. Карты выкладываются на стол из начала "рук" и сравниваются
 * только по значениям (масть игнорируется). При равных значениях сравниваются следующие карты.
 * Набор карт со стола перекладывается в конец руки победителя. Шестерка туза не бьёт.
 *
 * Реализация должна сопровождаться тестами.
 */

// Размер колоды
const val DECK_SIZE = 36

// Масть
enum class Suit {
    diamonds, hearts, clubs, spades
}

// Значение
enum class Rank (val value: Int){
    six(1), seven(2), eight(3), nine(4), ten(5), jack(6), queen(7), king(8), ace(9)
}

// Карта
data class Card(val rank: Rank, val suit: Suit) {
    val type = suit
    val num = rank.value
}

val allrank = listOf("six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace")
val allcard = listOf("diamonds", "hearts", "clubs", "spades")

// Тип для обозначения игрока (первый, второй)
enum class Player {
    first, second
}


// Возвращается null, если значения карт совпадают
fun roundWinner(card1: Card, card2 : Card): Player? {
    if (card1.rank==card2.rank) {
        return null
    } else if (card1.rank<card2.rank) {
        return Player.second
    } else {
        return Player.first
    }
}

// Колода
typealias Deck = List<Card>

// Набор карт у игрока
typealias Hand = MutableList<Card>

// Набор карт, выложенных на стол
typealias Table = MutableList<Card>

// Наборы карт у игроков
typealias Hands = Map<Player, Hand>

// Возвращает полную колоду (36 карт) в фиксированном порядке
fun fullDeck() : Deck {
    var k: List<Card> = emptyList()
    for (rank in allrank)
        for (card in allcard)
            k+= Card(rank, card)
    return k
}

// Раздача карт: случайное перемешивание (shuffle) и деление колоды пополам
fun deal(deck: Deck) : Hands {
    val shuffleDeck = deck.shuffled()
    var hand1: Hand = mutableListOf<Card>()
    var hand2: Hand = mutableListOf<Card>()
    for (i in 1..deck.size)
        if (i%2==1) {
            hand1+=shuffleDeck[i-1]
        } else {
            hand2+=shuffleDeck[i-1]
        }
    return mapOf(Player.first to hand1, Player.second to hand2)
}

// Один раунд игры (в том числе спор при равных картах).
// Возвращается победитель раунда и набор карт, выложенных на стол.
fun round(hands: Hands): Pair<Player, List<Card>> {
    var k1 = hands.get(Player.first) as MutableList
    var k2 = hands.get(Player.second) as MutableList
    var table = emptyList<Card>()
    for (i in 0..min(k1.size, k2.size)) {
        k1 -= k1[i]
        k2 -= k2[i]
        table += k1[i]
        table += k2[i]
        if (k1[i].num < k1[i].num) {
            k1 += table
            return Pair(Player.first, table)
            break
        } else if (k1[0].num > k2[0].num) {
            k2 += table
            return Pair(Player.second, table)
            break
        }
    }
}

// Полный цикл игры (возвращается победивший игрок)
// в процессе игры печатаются ходы
fun game(hands: Hands): Player {
    var pl1 = hands.get(Player.first)
    var pl2 = hands.get(Player.second)
    var rund = 0
    while (true)
        rund+=1
        var (win,table) = round(hands)
        if (win!=null) {

        }


}

fun main() {
    val deck = fullDeck()
    val hands = deal(deck) // Подумайте, почему val?
    val winner = game(hands)
    println("Победитель: $winner")
}
