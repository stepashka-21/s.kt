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
    for (rank in Rank.values())
        for (suit in Suit.values())
            k+= Card(rank, suit)
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
fun round(hands: Hands): Pair<Player?, List<Card>> {
    var k1 = hands[Player.first] as MutableList
    var k2 = hands[Player.second] as MutableList
    val table = emptyList<Card>().toMutableList()
    while (k1.size > 0 && k2.size > 0) {
        table +=k1[0]
        table += k2[0]
        if (roundWinner(k1[0],k2[0]) != null) {
            return Pair(roundWinner(k1[0], k2[0]), table)
        }
        k1.removeAt(0)
        k2.removeAt(0)
    }
    return if (k1.size>k2.size) {
        Pair(Player.first, table)
    } else {
        Pair(Player.second, table)
    }
}

// Полный цикл игры (возвращается победивший игрок)
// в процессе игры печатаются ходы
fun game(hands: Hands): Player {
    var q1 = hands[Player.first] as MutableList
    var q2 = hands[Player.second] as MutableList
    while (q1.size * q2.size > 0) {
        var a = round(hands)
        if (a.first == Player.first) {
            val w1 = emptyList<Card>().toMutableList()
            for (i in 0..a.second.size / 2) {
                w1 += q1[0]
                w1 += q2[0]
                q1.removeAt(0)
                q2.removeAt(0)
                q1 += w1
            }
        } else {
            val w2 = emptyList<Card>().toMutableList()
            for (i in 0..a.second.size / 2) {
                w2 += q1[0]
                w2 += q2[0]
                q1.removeAt(0)
                q2.removeAt(0)
                q1 += w2
            }
        }
    }
    return if (q1.size>q2.size) {
        Player.first
    } else {
        Player.second
    }
}

fun main() {
    val deck = fullDeck()
    val hands = deal(deck) // Подумайте, почему val?
    val winner = game(hands)
    println("Победитель: $winner")
}
