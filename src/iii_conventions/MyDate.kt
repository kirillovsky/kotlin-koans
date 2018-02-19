package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }

}

operator fun MyDate.plus(interval: TimeInterval): MyDate =
        addTimeIntervals(interval, 1)

operator fun MyDate.plus(repeatedInterval: RepeatedTimeInterval): MyDate =
        addTimeIntervals(repeatedInterval.ti, repeatedInterval.n)

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;
}

operator fun TimeInterval.times(n: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, n)

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    operator fun contains(date: MyDate): Boolean = start <= date && date <= endInclusive
}

class DateIterator(private val range: DateRange) : Iterator<MyDate> {
    private var currentDate: MyDate = range.start

    override fun hasNext(): Boolean = currentDate <= range.endInclusive

    override fun next(): MyDate {
        val tmpDate = currentDate
        currentDate = currentDate.addTimeIntervals(TimeInterval.DAY, 1)
        return tmpDate
    }
}
